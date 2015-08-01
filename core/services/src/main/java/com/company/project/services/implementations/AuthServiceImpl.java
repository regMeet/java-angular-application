package com.company.project.services.implementations;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.company.project.api.exception.HttpPreconditionFailedException;
import com.company.project.api.exception.HttpStatusException;
import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.Provider;
import com.company.project.services.interfaces.AuthService;
import com.company.project.services.utils.AuthUtils;
import com.company.project.transformers.UserTransformer;
import com.company.project.transformers.UserVOTransformer;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

@Transactional
@Service
public class AuthServiceImpl implements AuthService {
	private static final String DEFAULT_USER_ROLE = "user";

	final static Logger log = Logger.getLogger(AuthServiceImpl.class);

	private FacebookDAO facebookDAO;
	private GoogleDAO googleDAO;
	private UserDAO userDAO;

	@Autowired
	public AuthServiceImpl(FacebookDAO facebookDAO, UserDAO userDAO, GoogleDAO googleDAO) {
		this.facebookDAO = facebookDAO;
		this.userDAO = userDAO;
		this.googleDAO = googleDAO;
	}

	@Override
	public AuthEntityResponseVO login(AuthLogInUserVO logInUser, String remoteHost) throws HttpAuthenticationException {

		String emailOrUsername = logInUser.getEmailOrUsername();
		Optional<User> foundUser = userDAO.findByEmail(emailOrUsername);
		if (!foundUser.isPresent()) {
			foundUser = userDAO.findByUsername(emailOrUsername);
		}

		if (foundUser.isPresent()) {
			String passwordFound = foundUser.get().getPassword();
			boolean checkPassword = PasswordService.checkPassword(logInUser.getPassword(), passwordFound);
			if (checkPassword) {
				return createToken(foundUser.get(), remoteHost);
			}
		}

		throw new HttpAuthenticationException(HttpError.UNAUTHORIZED_API);
	}

	@Override
	public AuthEntityResponseVO signup(AuthSignUpUserVO signUpUser, String remoteHost) throws HttpAuthenticationException {
		final Optional<User> existingUserWithSameEmail = userDAO.findByEmail(signUpUser.getEmail());
		final Optional<User> existingUserWithSameUsername = userDAO.findByUsername(signUpUser.getUsername());

		if (!existingUserWithSameEmail.isPresent() && !existingUserWithSameUsername.isPresent()) {
			User userToSave = UserTransformer.transformTo(signUpUser);
			userToSave.setPassword(PasswordService.hashPassword(userToSave.getPassword()));
			userToSave.setRole(DEFAULT_USER_ROLE);
			userDAO.create(userToSave);
			return createToken(userToSave, remoteHost);
		}

		throw new HttpAuthenticationException(HttpError.CONFLICT_ACCOUNT);
	}

	@Override
	public AuthEntityResponseVO linkFacebook(SatellizerPayloadVO p, Optional<Long> userId, String remoteHost) throws HttpStatusException {
		FacebookAccessToken facebookAccessToken = facebookDAO.getAccessTokenFromCode(p.getCode(), p.getClientId(), p.getRedirectUri());
		FacebookUser facebookUser = facebookDAO.getMe(facebookAccessToken.getAccessToken());

		User user = processProviderUser(userId, facebookUser, Provider.FACEBOOK);
		return createToken(user, remoteHost);
	}

	@Override
	public AuthEntityResponseVO linkGoogle(SatellizerPayloadVO p, Optional<Long> userId, String remoteHost) throws HttpStatusException {
		GoogleAccessToken googleAccessToken = googleDAO.getAccessTokenFromCode(p.getCode(), p.getClientId(), p.getRedirectUri());
		GoogleUser googleUser = googleDAO.getMe(googleAccessToken.getAccessToken());

		User user = processProviderUser(userId, googleUser, Provider.GOOGLE);
		return createToken(user, remoteHost);
	}

	@Override
	public UserVO unlink(String provider, Long userId) throws HttpStatusException {
		Optional<User> foundUser = userDAO.findById(userId);

		if (!foundUser.isPresent()) {
			throw new HttpContentNotFoundException(HttpError.ACCOUNT_NOT_FOUND_API);
		}

		final User userToUnlink = foundUser.get();

		// check that the user is not trying to unlink the only sign-in method
		if (!userToUnlink.allowToUnlinkAMethodAccount()) {
			throw new HttpPreconditionFailedException(HttpError.ACCOUNT_LINKED_NEEDED_PRECONDITION_FAILED_API);
		}

		userToUnlink.setProviderId(Provider.valueOf(provider.toUpperCase()), null);
		userDAO.update(userToUnlink);

		UserVO userVO = UserVOTransformer.transformTo(userToUnlink);

		return userVO;

	}

	private User processProviderUser(Optional<Long> userId, ProviderUser providerUser, Provider provider) throws HttpStatusException {
		Optional<User> linkedUserFound = userDAO.findByProvider(provider, providerUser.getId());

		User userToSave;
		// If user is already signed in then link accounts.
		if (userId.isPresent()) {

			// If exist another account that is already linked with the FB account
			if (linkedUserFound.isPresent()) {
				throw new HttpConflictException(HttpError.CONFLICT_ACCOUNT);
			}

			Optional<User> foundUser = userDAO.findById(userId);
			if (!foundUser.isPresent()) {
				throw new HttpContentNotFoundException(HttpError.ACCOUNT_NOT_FOUND_API);
			}

			userToSave = foundUser.get();
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
				userToSave.setRole(DEFAULT_USER_ROLE);
				userDAO.create(userToSave);
			}
		}

		return userToSave;
	}

	private AuthEntityResponseVO createToken(User user, String remoteHost) throws HttpAuthenticationException {
		AuthEntityResponseVO authEntityVO;

		try {
			authEntityVO = AuthUtils.createToken(remoteHost, user);
		} catch (JOSEException e) {
			log.error("There has been an error creating token " + e.getMessage());
			throw new HttpAuthenticationException(HttpError.CONTACT_ADMIN_API);
		}

		return authEntityVO;
	}

	public static class PasswordService {
		public static String hashPassword(String plaintext) {
			return BCrypt.hashpw(plaintext, BCrypt.gensalt());
		}

		/**
		 * 
		 * @param plaintext
		 *            from webservices
		 * @param hashed
		 *            from database
		 * @return
		 */
		public static boolean checkPassword(String plaintext, String hashed) {
			return BCrypt.checkpw(plaintext, hashed);
		}
	}
}
