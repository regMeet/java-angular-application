package com.company.project.webservice.implementations;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.Auth.AuthUtils;
import com.company.project.persistence.dao.implementations.exceptions.NonExistentEntityException;
import com.company.project.persistence.entities.User;
import com.company.project.services.interfaces.UserService;
import com.company.project.webservice.implementations.base.BaseRestServiceImpl;
import com.company.project.webservice.interfaces.UserRestService;
import com.google.common.base.Optional;
import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/api/users")
public class UserRestServiceImpl extends BaseRestServiceImpl<User, UserService> implements UserRestService {
	final static Logger log = Logger.getLogger(UserRestServiceImpl.class);

	
	@Autowired
    public UserRestServiceImpl(UserService baseService) {
        super(baseService);
    }

    @Override
    public ResponseEntity<User> findByUserName(@PathVariable("name") String name) {
        Optional<User> foundUser = baseService.findByUsername(name);
        return handleFoundEntity(foundUser);
    }

    @Override
    public ResponseEntity<User> findByEmail(String email) {
        Optional<User> foundUser = baseService.findByEmail(email);
        return handleFoundEntity(foundUser);
    }

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<User> getUser(HttpServletRequest request) throws ParseException, JOSEException {
		int idUser = getAuthUser(request);
		return findById(idUser);
	}

	/*
	 * Helper methods
	 */
	private int getAuthUser(HttpServletRequest request) throws ParseException, JOSEException {
		String subject = AuthUtils.getSubject(request.getHeader(AuthUtils.AUTH_HEADER_KEY));
		int idUser = Integer.parseInt(subject);
		return idUser;
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public void handleTodoNotFound(NonExistentEntityException ex) {

	}
}
