package com.company.project.persistence.dao.interfaces.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

public interface BaseReadOnlyDAO<E extends Serializable> extends Serializable{

	public abstract List<E> findAll();

	public abstract List<E> find(int maxResults, int firstResult);

	public abstract E findById(Object id);

	public abstract int getCount();
	
	public abstract Query createQuery(String query);

}