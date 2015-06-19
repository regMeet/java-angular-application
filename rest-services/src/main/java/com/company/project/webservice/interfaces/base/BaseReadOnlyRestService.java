package com.company.project.webservice.interfaces.base;

import java.io.Serializable;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Optional;

public interface BaseReadOnlyRestService<E extends Serializable> {

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Response findAll();

	public abstract List<E> find(int maxResults, int firstResult);

	public abstract Optional<E> findById(Object id);

	public abstract int getCount();
}