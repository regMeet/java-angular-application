package com.company.project.webservice.interfaces.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Optional;

public interface BaseReadOnlyRestService<E extends Serializable> {

	@PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List<E> findAll();

    public List<E> find(int maxResults, int firstResult);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<E> findById(@PathVariable("id") Long id);

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public int getCount();

    @RequestMapping(value = "/ping/{message}", method = RequestMethod.GET)
    public @ResponseBody String ping(@PathVariable("message") String message);

    public ResponseEntity<E> handleFoundEntity(Optional<E> foundEntity);

}