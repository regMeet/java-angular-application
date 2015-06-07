package com.company.project.webservice.interfaces.base;

import java.io.Serializable;
import java.util.List;

import com.google.common.base.Optional;

public interface BaseReadOnlyRestService<E extends Serializable> {

	public abstract List<E> findAll();

	public abstract List<E> find(int maxResults, int firstResult);

	public abstract Optional<E> findById(Object id);

	public abstract int getCount();
}