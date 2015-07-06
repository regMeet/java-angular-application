package com.company.project.webservice.implementations.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

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
	public ResponseEntity<E> findById(@PathVariable("id") Integer id) {
		Optional<E> foundEntity = baseService.findById(id);
		return handleFoundEntity(foundEntity);
	}

	@Override
	public int getCount() {
		return baseService.getCount();
	}

	@Override
	public String ping(@PathVariable("message") String message) {
		return message;
	}

	public ResponseEntity<E> handleFoundEntity(Optional<E> foundEntity) {
		if (!foundEntity.isPresent()) {
			return new ResponseEntity<E>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<E>(foundEntity.get(), HttpStatus.OK);
	}
}