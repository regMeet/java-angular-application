package com.company.project.services.implementations;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.VO.AuthEntityResponseVO;
import com.company.project.VO.AuthLogInUserVO;
import com.company.project.VO.AuthSignUpUserVO;
import com.company.project.VO.SatellizerPayloadVO;
import com.company.project.VO.UserVO;
import com.company.project.api.dao.intefaces.FacebookDAO;
import com.company.project.api.dao.intefaces.GoogleDAO;
import com.company.project.api.entities.ProviderUser;
import com.company.project.api.entities.facebook.FacebookAccessToken;
import com.company.project.api.entities.facebook.FacebookUser;
import com.company.project.api.entities.google.GoogleAccessToken;
import com.company.project.api.entities.google.GoogleUser;
import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.api.exception.HttpConflictException;
import com.company.project.api.exception.HttpContentNotFoundException;
import com.company.project.api.exception.HttpError;
import com.company.project.api.exception.HttpFailedDependencyException;
import com.company.project.api.exception.HttpPreconditionFailedException;
import com.company.project.api.exception.HttpStatusException;
import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.Provider;
import com.company.project.security.PasswordEncoderimpl;
import com.company.project.security.TokenManagerImpl.TokenType;
import com.company.project.security.UserContext;
import com.company.project.security.interfaces.TokenManager;
import com.company.project.services.interfaces.AuthService;
import com.company.project.services.interfaces.EmailService;
import com.company.project.services.utils.InternationalizationService;
import com.company.project.services.utils.URLFactory;
import com.company.project.transformers.UserTransformer;
import com.company.project.transformers.UserVOTransformer;
import com.company.project.utils.LocalDateUtils;
import com.google.common.base.Optional;

import io.jsonwebtoken.Claims;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {
	private static final Logger log = Logger.getLogger(AuthServiceImpl.class);

	private final FacebookDAO facebookDAO;
	private final GoogleDAO googleDAO;
	private final UserDAO userDAO;
	// AuthenticationManager comes from Spring
	private final AuthenticationManager authenticationManager;
	private final TokenManager tokenManager;
	private final EmailService emailService;
	private final InternationalizationService i18nService;
	private final URLFactory urlFactory;

	@Autowired
	public AuthServiceImpl(FacebookDAO facebookDAO, UserDAO userDAO, GoogleDAO googleDAO, AuthenticationManager authenticationManager,
			TokenManager tokenManager, EmailService emailService, InternationalizationService i18nService, URLFactory urlFactory) {
		this.facebookDAO = facebookDAO;
		this.userDAO = userDAO;
		this.googleDAO = googleDAO;
		this.authenticationManager = authenticationManager;
		this.tokenManager = tokenManager;
		this.emailService = emailService;
		this.i18nService = i18nService;
		this.urlFactory = urlFactory;
	}

	@Override
	public AuthEntityResponseVO login(AuthLogInUserVO logInUser) throws HttpStatusException {
		UserContext principal = authenticate(logInUser.getEmailOrUsername(), logInUser.getPassword());
		User user = principal.getUser();
		String newToken = tokenManager.createLoginToken(user);
		return new AuthEntityResponseVO(user, newToken);
	}

	@Override
	public AuthEntityResponseVO signup(AuthSignUpUserVO signUpUser) throws HttpAuthenticationException {
		final Optional<User> existingUserWithSameEmail = userDAO.findByEmail(signUpUser.getEmail());
		final Optional<User> existingUserWithSameUsername = userDAO.findByUsername(signUpUser.getUsername());

		if (!existingUserWithSameEmail.isPresent() && !existingUserWithSameUsername.isPresent()) {
			User userToSave = UserTransformer.transformTo(signUpUser);
			userToSave.setPassword(PasswordEncoderimpl.hashPassword(userToSave.getPassword()));
			userToSave.setDefaultValues();
			userToSave.setStatus(User.AccountStatus.TO_BE_VERIFIED);
			userDAO.create(userToSave);
			String newToken = tokenManager.createVerifyToken(userToSave);
			sendConfirmationEmail(userToSave, newToken);
			return new AuthEntityResponseVO(userToSave, newToken);
		}

		throw new HttpAuthenticationException(HttpError.CONFLICT_ACCOUNT);
	}

	private void sendConfirmationEmail(User userToSave, String newToken) {
		String language = userToSave.getLanguage();
		String name = getUserName(userToSave, language);

		String verifyUrl = urlFactory.getVerifyUrl() + newToken;
		emailService.sendConfirmationMessage(userToSave.getEmail(), language, name, verifyUrl);
	}

	private String getUserName(User userToSave, String language) {
		String firstname = userToSave.getFirstname();
		String lastname = userToSave.getLastname();
		String username = userToSave.getUsername();

		String name;
		if (StringUtils.isNotBlank(firstname)) {
			name = firstname;
		} else if (StringUtils.isNotBlank(lastname)) {
			name = lastname;
		} else if (StringUtils.isNotBlank(username)) {
			name = username;
		} else {
			name = i18nService.getMessage("email.default.name", language);
		}
		return name;
	}

	private void sendForgotPasswordEmail(User user, String newToken) {
		String name = getUserName(user, user.getLanguage());

		String verifyUrl = urlFactory.getForgotPassUrl() + newToken;
		emailService.sendForgotPasswordMessage(user.getEmail(), user.getLanguage(), name, verifyUrl);
	}

	@Override
	public AuthEntityResponseVO linkFacebook(SatellizerPayloadVO p) throws HttpStatusException {
		FacebookAccessToken facebookAccessToken = facebookDAO.getAccessTokenFromCode(p.getCode(), p.getClientId(), p.getRedirectUri());
		FacebookUser facebookUser = facebookDAO.getMe(facebookAccessToken.getAccessToken());

		User user = processProviderUser(facebookUser, Provider.FACEBOOK);
		String newToken = tokenManager.createLoginToken(user);
		return new AuthEntityResponseVO(user, newToken);
	}

	@Override
	public AuthEntityResponseVO linkGoogle(SatellizerPayloadVO p) throws HttpStatusException {
		GoogleAccessToken googleAccessToken = googleDAO.getAccessTokenFromCode(p.getCode(), p.getClientId(), p.getRedirectUri());
		GoogleUser googleUser = googleDAO.getMe(googleAccessToken.getAccessToken());

		User user = processProviderUser(googleUser, Provider.GOOGLE);
		String newToken = tokenManager.createLoginToken(user);
		return new AuthEntityResponseVO(user, newToken);
	}

	@Override
	public UserVO unlink(String provider) throws HttpStatusException {
		User userToUnlink = currentUser();

		// check that the user is not trying to unlink the only sign-in method
		if (!userToUnlink.allowToUnlinkAMethodAccount()) {
			throw new HttpPreconditionFailedException(HttpError.ACCOUNT_LINKED_NEEDED_PRECONDITION_FAILED_API);
		}

		userToUnlink.setProviderId(Provider.valueOf(provider.toUpperCase()), null);
		userDAO.update(userToUnlink);

		UserVO userVO = UserVOTransformer.transformTo(userToUnlink);

		return userVO;

	}

	private User processProviderUser(ProviderUser providerUser, Provider provider) throws HttpStatusException {
		Optional<User> linkedUserFound = userDAO.findByProvider(provider, providerUser.getId());

		// If user is already signed in then link accounts.
		User userToSave = currentUser();
		if (userToSave != null) {
			// If exist another account that is already linked with the FB account
			if (linkedUserFound.isPresent()) {
				throw new HttpConflictException(HttpError.CONFLICT_ACCOUNT);
			}

			userToSave.setProviderId(provider, providerUser.getId());

		} else {
			// Create a new user account or return an existing one.
			if (linkedUserFound.isPresent()) {
				userToSave = linkedUserFound.get();
			} else {
				userToSave = new User();
				userToSave.setProviderId(provider, providerUser.getId());
				userToSave.setUsername(providerUser.getName());
				userToSave.setFirstname(providerUser.getFirstname());
				userToSave.setLastname(providerUser.getLastname());
				userToSave.setEmail(providerUser.getEmail());
				userToSave.setDefaultValues();
				userDAO.create(userToSave);
			}
		}

		return userToSave;
	}

	@Override
	public UserContext authenticate(String emailOrUsername, String password) throws HttpStatusException {
		Authentication authentication = new UsernamePasswordAuthenticationToken(emailOrUsername, password);
		try {
			authentication = authenticationManager.authenticate(authentication);

		} catch (BadCredentialsException e) {
			// if the credentials are wrong
			Optional<User> foundUser = userDAO.findByEmailOrUsername(emailOrUsername);
			if (foundUser.isPresent()) {
				User user = foundUser.get();
				userDAO.updateFailAttempts(user);
			}
			throw new HttpAuthenticationException(HttpError.UNAUTHORIZED);
		} catch (LockedException e) {
			// if the account is suspended
			throw new HttpFailedDependencyException(HttpError.ACCOUNT_SUSPENDED);
		} catch (DisabledException e) {
			// if the account is not verified yet
			throw new HttpFailedDependencyException(HttpError.ACCOUNT_NOT_VERIFIED);
		}

		// Here principal=UserDetails (UserContext in our case), credentials=null (security reasons)
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserContext principal = (UserContext) authentication.getPrincipal();
		userDAO.resetFailAttempts(principal.getUser());

		return principal;
	}

	@Override
	public void checkLoadCredentials(String token) throws HttpStatusException {
		UserDetails userDetails = getUserDetails(token);

		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		UsernamePasswordAuthenticationToken securityToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(securityToken);
	}

	@Override
	public UserDetails getUserDetails(String accessToken) throws HttpStatusException {
		Claims claimsBody = tokenManager.decodeToken(accessToken);
		Long userId = Long.valueOf(claimsBody.getSubject());

		Optional<User> foundUser = userDAO.findById(userId);
		if (foundUser.isPresent()) {
			User user = foundUser.get();

			TokenType tokenType = TokenType.fromPayload(claimsBody.getAudience());

			if (tokenType.equals(TokenType.LOGIN)) {

				// Already managed by Spring security saying: User account is locked
				if (user.getStatus().equals(User.AccountStatus.SUSPENDED)) {
					log.warn("The account has been suspended");
					throw new HttpAuthenticationException(HttpError.UNAUTHORIZED);
				}
			}

			if (tokenType.equals(TokenType.FORGOT_PASS)) {
				if (user.getLastPasswordChanged() != null) {
					DateTime lastPasswordChanged = LocalDateUtils.getDateTimeFromDate(user.getLastPasswordChanged());
					DateTime issuedAt = LocalDateUtils.getDateTimeFromDate(claimsBody.getIssuedAt());
					if (issuedAt.isAfter(lastPasswordChanged)) {
						return new UserContext(user);
					} else {
						throw new HttpFailedDependencyException(HttpError.FORGET_PASSWORD_LINK_EXPIRED);
					}
				}

			}

			if (user.getLastLogout() != null) {
				DateTime lastLogout = LocalDateUtils.getDateTimeFromDate(user.getLastLogout());
				DateTime issuedAt = LocalDateUtils.getDateTimeFromDate(claimsBody.getIssuedAt());
				if (issuedAt.isAfter(lastLogout)) {
					return new UserContext(user);
				}
			} else {
				return new UserContext(user);
			}

		}

		throw new HttpAuthenticationException(HttpError.UNAUTHORIZED);
	}

	@Override
	public void logout() {
		User user = currentUser();
		user.setLastLogout(LocalDateUtils.getTodayDate());
		userDAO.update(user);

		SecurityContextHolder.clearContext();
	}

	@Override
	public UserDetails currentUserDetails() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication instanceof AnonymousAuthenticationToken) {
			return null;
		}

		return (UserDetails) authentication.getPrincipal();
	}

	@Override
	public User currentUser() {
		User user = null;

		UserDetails currentUserDetails = currentUserDetails();
		if (currentUserDetails instanceof UserContext) {
			user = ((UserContext) currentUserDetails).getUser();
		}

		return user;
	}

	@Override
	public boolean hasAuthority(String authority) {
		UserDetails currentUserDetails = currentUserDetails();
		return currentUserDetails != null ? currentUserDetails.getAuthorities().contains(new SimpleGrantedAuthority(authority)) : false;
	}

	@Override
	public void verify(String token) throws HttpStatusException {
		UserContext userContext = (UserContext) getUserDetails(token);
		User user = userContext.getUser();
		user.setStatus(User.AccountStatus.VERIFIED);
		userDAO.update(user);
	}

	@Override
	public void forgotPassword(String emailOrUsername) throws HttpContentNotFoundException {
		Optional<User> foundUser = userDAO.findByEmailOrUsername(emailOrUsername);
		if (foundUser.isPresent()) {
			// create forgot password token
			User user = foundUser.get();
			String newToken = tokenManager.createForgotPasswordToken(user);
			sendForgotPasswordEmail(user, newToken);
		} else {
			throw new HttpContentNotFoundException(HttpError.ACCOUNT_NOT_FOUND);
		}

	}

	@Override
	public void passwordForgotten(String token, String newPassword) throws HttpStatusException {
		UserContext userContext = (UserContext) getUserDetails(token);
		User user = userContext.getUser();
		user.setPassword(PasswordEncoderimpl.hashPassword(newPassword));
		user.setLastPasswordChanged(LocalDateUtils.getTodayDate());
		userDAO.update(user);
	}

}
