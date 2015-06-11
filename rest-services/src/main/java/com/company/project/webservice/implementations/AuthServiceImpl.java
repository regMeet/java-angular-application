package com.company.project.webservice.implementations;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.NotBlank;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.Auth.AuthUtils;
import com.company.project.Auth.ErrorMessage;
import com.company.project.Auth.PasswordService;
import com.company.project.Auth.Token;
import com.company.project.Auth.Provider.FacebookUtil;
import com.company.project.Auth.Provider.GoogleUtil;
import com.company.project.persistence.entities.Users;
import com.company.project.persistence.entities.Users.Provider;
import com.company.project.services.interfaces.UserService;
import com.company.project.webservice.interfaces.AuthService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/auth")
public class AuthServiceImpl implements AuthService {
	final static Logger log = Logger.getLogger(AuthServiceImpl.class);

	public static final String CLIENT_ID_KEY = "client_id";
	public static final String REDIRECT_URI_KEY = "redirect_uri";
	public static final String CLIENT_SECRET = "client_secret";
	public static final String CODE_KEY = "code";
	public static final String GRANT_TYPE_KEY = "grant_type";
	public static final String AUTH_CODE = "authorization_code";

	public static final String CONFLICT_MSG = "There is already a %s account that belongs to you";
	public static final String NOT_FOUND_MSG = "User not found";
	public static final String EXISTING_ACCOUNT_ERROR_MSG = "The email already exists";
	public static final String LOGING_ERROR_MSG = "Wrong email and/or password";
	public static final String UNLINK_ERROR_MSG = "Could not unlink %s account because it is your only sign-in method";
	public static final String FACEBOOK_ERROR_MSG = "An error happened when trying to log in by Facebook";

	public static final ObjectMapper MAPPER = new ObjectMapper();

	private final UserService userService;
	private final Client client = ClientBuilder.newClient();
	@Autowired
	private FacebookUtil facebookUtil;
	@Autowired
	private GoogleUtil googleUtil;

	@Autowired
	public AuthServiceImpl(UserService userService) {
		this.userService = userService;
	}

	@Override
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody Response login(@RequestBody @Valid Users user, HttpServletRequest request) throws JOSEException {
		if (user.getEmail() != null) {
			final Optional<Users> userDto = userService.findByEmail(user.getEmail());
			if (userDto.isPresent()) {
				String passwordDto = userDto.get().getPassword();
				boolean checkPassword = PasswordService.checkPassword(user.getPassword(), passwordDto);
				if (checkPassword) {
					final Token token = AuthUtils.createToken(request.getRemoteHost(), userDto.get().getIdUser());
					return Response.ok().entity(token).build();
				}
			}
		}
		return Response.status(Status.UNAUTHORIZED).entity(new ErrorMessage(LOGING_ERROR_MSG)).build();
	}

	@Override
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public @ResponseBody Response signup(@RequestBody @Valid Users user, HttpServletRequest request) throws JOSEException {
		final Optional<Users> existingUser = userService.findByEmail(user.getEmail());
		if (!existingUser.isPresent()) {
			user.setPassword(PasswordService.hashPassword(user.getPassword()));
			userService.create(user);
			final Token token = AuthUtils.createToken(request.getRemoteHost(), user.getIdUser());
			return Response.status(Status.CREATED).entity(token).build();
		}

		return Response.status(Status.CONFLICT).entity(new ErrorMessage(EXISTING_ACCOUNT_ERROR_MSG)).build();
	}

	@Override
	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	public Response loginFacebook(@RequestBody @Valid Payload payload, HttpServletRequest request) throws JsonParseException, JsonMappingException,
			IOException, ParseException, JOSEException {
		Response response;

		// Step 1. Exchange authorization code for access token.
		response = client.target(FacebookUtil.ACCESS_TOKEN_URL)
				.queryParam(CLIENT_ID_KEY, payload.getClientId())
				.queryParam(REDIRECT_URI_KEY, payload.getRedirectUri())
				.queryParam(CLIENT_SECRET, facebookUtil.getSecret())
				.queryParam(CODE_KEY, payload.getCode())
				.request("text/plain")
				.accept(MediaType.TEXT_PLAIN)
				.get();

		String readEntity = response.readEntity(String.class);
		final String paramStr = Preconditions.checkNotNull(readEntity);

		JSONObject json = null;
		try {
			// {"access_token":"token","token_type":"bearer","expires_in":5178920}
			json = (JSONObject) new JSONParser().parse(paramStr);
		} catch (org.json.simple.parser.ParseException e) {
			log.error("An exception has been thrown while parsing json from facebook response "+ e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(new ErrorMessage(FACEBOOK_ERROR_MSG)).build();
		}

		String accessToken = (String) json.get("access_token");
		String tokenType = (String) json.get("token_type"); // bearer
		Long expiresIn = (Long) json.get("expires_in");

		// Step 2. Retrieve profile information about the current user.
		response = client.target(FacebookUtil.GRAPH_API_URL)
				.queryParam("access_token", accessToken)
				.queryParam("expiresIn", expiresIn.toString())
				.request("text/plain")
				.get();

		final Map<String, Object> userInfo = getResponseEntity(response);

		// Step 3. Process the authenticated the user.
		return processUser(request, Provider.FACEBOOK, userInfo.get("id").toString(), userInfo.get("name").toString());
	}

	@Override
	@RequestMapping(value = "/google", method = RequestMethod.POST)
	public Response loginGoogle(@RequestBody @Valid Payload payload, HttpServletRequest request) throws JOSEException, ParseException,
			JsonParseException, JsonMappingException, IOException {
		final String accessTokenUrl = "https://accounts.google.com/o/oauth2/token";
		final String peopleApiUrl = "https://www.googleapis.com/plus/v1/people/me/openIdConnect";
		Response response;

		// Step 1. Exchange authorization code for access token.
		final MultivaluedMap<String, String> accessData = new MultivaluedHashMap<String, String>();
		accessData.add(CLIENT_ID_KEY, payload.getClientId());
		accessData.add(REDIRECT_URI_KEY, payload.getRedirectUri());
		accessData.add(CLIENT_SECRET, googleUtil.getSecret());
		accessData.add(CODE_KEY, payload.getCode());
		accessData.add(GRANT_TYPE_KEY, AUTH_CODE);
		response = client.target(accessTokenUrl).request().post(Entity.form(accessData));
		accessData.clear();

		// Step 2. Retrieve profile information about the current user.
		final String accessToken = (String) getResponseEntity(response).get("access_token");
		response = client.target(peopleApiUrl)
				.request("text/plain")
				.header(AuthUtils.AUTH_HEADER_KEY, String.format("Bearer %s", accessToken))
				.get();
		final Map<String, Object> userInfo = getResponseEntity(response);

		// Step 3. Process the authenticated the user.
		return processUser(request, Provider.GOOGLE, userInfo.get("sub").toString(), userInfo.get("name").toString());
	}

	@Override
	@RequestMapping(value = "/unlink/{provider}", method = RequestMethod.GET)
	public Response unlink(@PathVariable("provider") String provider, HttpServletRequest request) throws ParseException,
			IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, JOSEException {

		final String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		final Optional<Users> foundUser = userService.findById(Long.parseLong(subject));

		if (!foundUser.isPresent()) {
			return Response.status(Status.NOT_FOUND).entity(new ErrorMessage(NOT_FOUND_MSG)).build();
		}

		final Users userToUnlink = foundUser.get();

		// check that the user is not trying to unlink the only sign-in method
		if (!userToUnlink.allowToUnlinkAMethodAccount()) {
			return Response.status(Status.BAD_REQUEST).entity(new ErrorMessage(String.format(UNLINK_ERROR_MSG, provider))).build();
		}

		try {
			userToUnlink.setProviderId(Provider.valueOf(provider.toUpperCase()), null);
		} catch (final IllegalArgumentException e) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		userService.create(userToUnlink);

		return Response.ok().build();
	}

	/*
	 * Inner classes for entity wrappers
	 */
	public static class Payload {
		@NotBlank
		String clientId;

		@NotBlank
		String redirectUri;

		@NotBlank
		String code;

		public String getClientId() {
			return clientId;
		}

		public String getRedirectUri() {
			return redirectUri;
		}

		public String getCode() {
			return code;
		}
	}

	/*
	 * Helper methods
	 */
	private Map<String, Object> getResponseEntity(final Response response) throws JsonParseException, JsonMappingException, IOException {
		return MAPPER.readValue(response.readEntity(String.class), new TypeReference<Map<String, Object>>() {
		});
	}

	private Response processUser(final HttpServletRequest request, final Provider provider, final String id, final String username)
			throws JOSEException, ParseException {
		final Optional<Users> user = userService.findByProvider(provider, id);

		// Step 3a. If user is already signed in then link accounts.
		Users userToSave;
		final String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
		if (StringUtils.isNotBlank(authHeader)) {
			if (user.isPresent()) {
				return Response.status(Status.CONFLICT).entity(new ErrorMessage(String.format(CONFLICT_MSG, provider.capitalize())))
						.build();
			}

			final String subject = AuthUtils.getSubject(authHeader);
			final Optional<Users> foundUser = userService.findById(Long.parseLong(subject));
			if (!foundUser.isPresent()) {
				return Response.status(Status.NOT_FOUND).entity(new ErrorMessage(NOT_FOUND_MSG)).build();
			}

			userToSave = foundUser.get();
			userToSave.setProviderId(provider, id);
			if (userToSave.getUsername() == null) {
				userToSave.setUsername(username);
			}
//			userService.create(userToSave);
			userService.update(userToSave);
		} else {
			// Step 3b. Create a new user account or return an existing one.
			if (user.isPresent()) {
				userToSave = user.get();
			} else {
				userToSave = new Users();
				userToSave.setProviderId(provider, id);
				userToSave.setUsername(username);
				userService.create(userToSave);
			}
		}

		final Token token = AuthUtils.createToken(request.getRemoteHost(), userToSave.getIdUser());
		return Response.ok().entity(token).build();
	}
}
