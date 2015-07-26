package com.company.project.services.implementations;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.VO.AuthEntityVO;
import com.company.project.VO.SatellizerPayloadVO;
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
import com.company.project.api.exception.HttpStatusException;
import com.company.project.persistence.dao.interfaces.UserDAO;
import com.company.project.persistence.entities.User;
import com.company.project.persistence.entities.User.Provider;
import com.company.project.services.interfaces.AuthService;
import com.company.project.services.utils.AuthUtils;
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
	public AuthEntityVO linkFacebook(SatellizerPayloadVO p, Optional<Long> userId, String remoteHost) throws HttpStatusException {
		FacebookAccessToken facebookAccessToken = facebookDAO.getAccessTokenFromCode(p.getCode(), p.getClientId(), p.getRedirectUri());
		FacebookUser facebookUser = facebookDAO.getMe(facebookAccessToken.getAccessToken());

		return processProviderUser(userId, remoteHost, facebookUser, Provider.FACEBOOK);
	}

	@Override
	public AuthEntityVO linkGoogle(SatellizerPayloadVO p, Optional<Long> userId, String remoteHost) throws HttpStatusException {
		GoogleAccessToken googleAccessToken = googleDAO.getAccessTokenFromCode(p.getCode(), p.getClientId(), p.getRedirectUri());
		GoogleUser googleUser = googleDAO.getMe(googleAccessToken.getAccessToken());

		return processProviderUser(userId, remoteHost, googleUser, Provider.GOOGLE);
	}

	private AuthEntityVO processProviderUser(Optional<Long> userId, String remoteHost, ProviderUser providerUser, Provider provider)
			throws HttpConflictException, HttpContentNotFoundException, HttpAuthenticationException {
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

		AuthEntityVO authEntityVO;

		try {
			authEntityVO = AuthUtils.createToken(remoteHost, userToSave);
		} catch (JOSEException e) {
			log.error("There has been an error creating token " + e.getMessage());
			throw new HttpAuthenticationException(HttpError.UNAUTHORIZED_API);
		}

		return authEntityVO;
	}
}
