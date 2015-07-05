package com.company.project.services.interfaces.base;

import java.io.Serializable;

public interface BaseService<E extends Serializable> extends BaseReadOnlyService<E> {

	public abstract void create(E entity);

	public abstract void update(E entity);

	public abstract void delete(E entity);

	public abstract void delete(Integer id);
}