package com.company.project.webservice.interfaces.base;

import java.io.Serializable;

public interface BaseRestService<E extends Serializable> extends BaseReadOnlyRestService<E> {

	public abstract void create(E entity);

	public abstract void update(E entity);

	public abstract void delete(E entity);

	public abstract void delete(Integer id);
}