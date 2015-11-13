package com.company.project.webservice.implementations;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.persistence.entities.User;
import com.company.project.services.interfaces.UserService;
import com.company.project.services.utils.AuthUtils;
import com.company.project.webservice.implementations.base.BaseRestServiceImpl;
import com.company.project.webservice.interfaces.UserRestService;
import com.google.common.base.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestServiceImpl extends BaseRestServiceImpl<User, UserService>implements UserRestService {
    final static Logger log = Logger.getLogger(UserRestServiceImpl.class);

    @Autowired
    public UserRestServiceImpl(UserService baseService) {
        super(baseService);
        System.out.println("construyendo");
    }

    @Override
    public void create(User entity) {
        entity.setRole("user");
        // baseService.create(entity);
        super.create(entity);
    }

    @Override
    public ResponseEntity<User> findByUserName(String name) {
        Optional<User> foundUser = baseService.findByUsername(name);
        return handleFoundEntity(foundUser);
    }

    @Override
    public ResponseEntity<User> findByEmail(String email) {
        Optional<User> foundUser = baseService.findByEmail(email);
        return handleFoundEntity(foundUser);
    }

    @Override
    public ResponseEntity<User> getUser(HttpServletRequest request) throws HttpAuthenticationException {
        Long idUser = getAuthUser(request);

        return findById(idUser);
    }

    /*
     * Helper methods
     */
    private Long getAuthUser(HttpServletRequest request) throws HttpAuthenticationException {
        String authorizationHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
        Long userId = AuthUtils.getSubject(authorizationHeader);
        return userId;
    }

}
