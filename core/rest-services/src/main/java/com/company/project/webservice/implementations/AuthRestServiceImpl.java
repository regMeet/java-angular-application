package com.company.project.webservice.implementations;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.Auth.ErrorMessage;
import com.company.project.Auth.PasswordService;
import com.company.project.Auth.Provider.FacebookUtil;
import com.company.project.Auth.Provider.GoogleUtil;
import com.company.project.VO.AuthEntityVO;
import com.company.project.VO.SatellizerPayloadVO;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpStatusException;
import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.Provider;
import com.company.project.services.interfaces.AuthService;
import com.company.project.services.interfaces.UserService;
import com.company.project.services.utils.AuthUtils;
import com.company.project.webservice.interfaces.AuthRestService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/auth")
public class AuthRestServiceImpl implements AuthRestService {
	final static Logger log = Logger.getLogger(AuthRestServiceImpl.class);

	public static final String CONFLICT_MSG = "There is already a %s account that belongs to you";
	public static final String NOT_FOUND_MSG = "User not found";
	public static final String EXISTING_ACCOUNT_ERROR_MSG = "The email already exists";
	public static final String LOGING_ERROR_MSG = "Wrong email and/or password";
	public static final String UNLINK_ERROR_MSG = "Could not unlink %s account because it is your only sign-in method";
	public static final String FACEBOOK_ERROR_MSG = "An error happened when trying to log in by Facebook";

	public static final ObjectMapper MAPPER = new ObjectMapper();

	private final UserService userService;
	private final AuthService authService;

	@Autowired
	public AuthRestServiceImpl(UserService userService, AuthService authService) {
		this.userService = userService;
		this.authService = authService;
	}

	@Override
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity login(@RequestBody @Valid User user, HttpServletRequest request) throws JOSEException {
		if (user.getEmail() != null) {
			final Optional<User> userDto = userService.findByEmail(user.getEmail());
			if (userDto.isPresent()) {
				String passwordDto = userDto.get().getPassword();
				boolean checkPassword = PasswordService.checkPassword(user.getPassword(), passwordDto);
				if (checkPassword) {
					final AuthEntityVO token = AuthUtils.createToken(request.getRemoteHost(), userDto.get());
					return new ResponseEntity(token, HttpStatus.OK);
				}
			}
		}
		return new ResponseEntity(new ErrorMessage(LOGING_ERROR_MSG), HttpStatus.UNAUTHORIZED);
	}

	@Override
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<User> signup(@RequestBody @Valid User user, HttpServletRequest request) throws JOSEException {
		final Optional<User> existingUser = userService.findByEmail(user.getEmail());
		if (!existingUser.isPresent()) {
			user.setPassword(PasswordService.hashPassword(user.getPassword()));
			userService.create(user);
			final AuthEntityVO token = AuthUtils.createToken(request.getRemoteHost(), user);
			return new ResponseEntity(token, HttpStatus.CREATED);
		}

		return new ResponseEntity(new ErrorMessage(EXISTING_ACCOUNT_ERROR_MSG), HttpStatus.CONFLICT);
	}

	@Override
	@RequestMapping(value = "/facebook", method = RequestMethod.POST)
	public @ResponseBody AuthEntityVO loginFacebook(@RequestBody @Valid SatellizerPayloadVO payload, HttpServletRequest request)
			throws HttpStatusException {

		Long subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));

		return authService.linkFacebook(payload, Optional.fromNullable(subject), request.getRemoteHost());
	}

	@Override
	@RequestMapping(value = "/google", method = RequestMethod.POST)
	public @ResponseBody AuthEntityVO loginGoogle(@RequestBody @Valid SatellizerPayloadVO payload, HttpServletRequest request)
			throws HttpStatusException {

		Long subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		return authService.linkGoogle(payload, Optional.fromNullable(subject), request.getRemoteHost());
	}

	@Override
	@RequestMapping(value = "/unlink/{provider}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<User> unlink(@PathVariable("provider") String provider, HttpServletRequest request) throws ParseException,
			IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException, JOSEException, HttpAuthenticationException {

		final Long subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		final Optional<User> foundUser = userService.findById(subject);

		if (!foundUser.isPresent()) {
			return new ResponseEntity(new ErrorMessage(NOT_FOUND_MSG), HttpStatus.NOT_FOUND);
		}

		final User userToUnlink = foundUser.get();

		// check that the user is not trying to unlink the only sign-in method
		if (!userToUnlink.allowToUnlinkAMethodAccount()) {
			String message = String.format(UNLINK_ERROR_MSG, provider);
			ErrorMessage errorMessage = new ErrorMessage(message);
			return new ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST);
		}

		try {
			userToUnlink.setProviderId(Provider.valueOf(provider.toUpperCase()), null);
		} catch (final IllegalArgumentException e) {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}

		userService.create(userToUnlink);

		return new ResponseEntity(HttpStatus.OK);
	}
}
