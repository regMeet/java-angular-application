package com.company.project.webservice.implementations.base;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.project.services.interfaces.base.BaseService;
import com.company.project.webservice.interfaces.base.BaseRestService;

public class BaseRestServiceImpl<E extends Serializable, S extends BaseService<E>> extends BaseReadOnlyRestServiceImpl<E, S> implements
		BaseRestService<E> {

	@Autowired
	public BaseRestServiceImpl(S baseService) {
		super(baseService);
	}

	@Override
	public void create(E entity) {
		baseService.create(entity);
	}

	@Override
	public void update(E entity) {
		baseService.update(entity);
	}

	@Override
	public void delete(E entity) {
		baseService.delete(entity);
	}

	@Override
	public void delete(Long id) {
		baseService.delete(id);
	}
}