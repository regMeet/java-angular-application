package com.company.project.webservice.interfaces;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.company.project.api.exception.HttpAuthenticationException;
import com.company.project.persistence.entities.User;
import com.company.project.webservice.interfaces.base.BaseRestService;

@RequestMapping("/api/users")
public interface UserRestService extends BaseRestService<User> {

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<User> findByUserName(@PathVariable("name") String name);

    @RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
    public ResponseEntity<User> findByEmail(@PathVariable("email") String email);

    // @RolesAllowed("ADMIN")
    // @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<User> getUser(HttpServletRequest request) throws HttpAuthenticationException;

}
