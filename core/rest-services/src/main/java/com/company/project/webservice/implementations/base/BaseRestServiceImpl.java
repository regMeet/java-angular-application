package com.company.project.webservice.implementations.base;

import java.io.Serializable;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.company.project.services.interfaces.base.BaseService;
import com.company.project.webservice.interfaces.base.BaseRestService;

public class BaseRestServiceImpl<E extends Serializable, S extends BaseService<E>> extends BaseReadOnlyRestServiceImpl<E, S> implements
		BaseRestService<E> {

	@Autowired
	public BaseRestServiceImpl(S baseService) {
		super(baseService);
	}

	@Override
	public void create(@RequestBody @Valid E entity) {
		baseService.create(entity);
	}

	@Override
	public void update(@RequestBody @Valid E entity) {
		baseService.update(entity);
	}

	@Override
	public void delete(E entity) {
		baseService.delete(entity);
	}

	@Override
	public void delete(@PathVariable("id") Long id) {
		baseService.delete(id);
	}
}