package com.company.project.webservice.implementations.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.company.project.services.interfaces.base.BaseReadOnlyService;
import com.company.project.webservice.interfaces.base.BaseReadOnlyRestService;
import com.google.common.base.Optional;

public class BaseReadOnlyRestServiceImpl<E extends Serializable, S extends BaseReadOnlyService<E>> implements BaseReadOnlyRestService<E> {

	protected S baseService;

	@Autowired
	public BaseReadOnlyRestServiceImpl(S baseService) {
		this.baseService = baseService;
	}

	@Override
	public List<E> findAll() {
		return baseService.findAll();
	}

	@Override
	public List<E> find(int maxResults, int firstResult) {
		return baseService.find(maxResults, firstResult);
	}

	@Override
	public Optional<E> findById(Object id) {
		return baseService.findById(id);
	}

	@Override
	public int getCount() {
		return baseService.getCount();
	}
}