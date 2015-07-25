package com.company.project.services.implementations.base;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.persistence.dao.interfaces.base.BaseDAO;
import com.company.project.services.interfaces.base.BaseService;

@Transactional
public class BaseServiceImpl<E extends Serializable, D extends BaseDAO<E>> extends BaseReadOnlyServiceImpl<E, D> implements BaseService<E> {

	@Autowired
	public BaseServiceImpl(D baseDao) {
		super(baseDao);
	}

	@Override
	public void create(E entity) {
		baseDao.create(entity);
	}

	@Override
	public E update(E entity) {
		return baseDao.update(entity);
	}

	@Override
	public void delete(E entity) {
		baseDao.delete(entity);
	}

	@Override
	public void delete(Long id) {
		baseDao.delete(id);
	}
}