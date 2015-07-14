package com.company.project.webservice.interfaces.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Optional;

public interface BaseReadOnlyRestService<E extends Serializable> {

	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<E> findAll();

    public List<E> find(int maxResults, int firstResult);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<E> findById(Integer id);

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public int getCount();

    @RequestMapping(value = "/ping/{message}", method = RequestMethod.GET)
    public String ping(String message);

    public ResponseEntity<E> handleFoundEntity(Optional<E> foundEntity);
}