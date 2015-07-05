package com.company.project.services.implementations.base;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.company.project.persistence.dao.interfaces.base.BaseReadOnlyDAO;
import com.company.project.services.interfaces.base.BaseReadOnlyService;
import com.google.common.base.Optional;

@Transactional
public class BaseReadOnlyServiceImpl<E extends Serializable, D extends BaseReadOnlyDAO<E>> implements BaseReadOnlyService<E> {

	protected D baseDao;

	@Autowired
	public BaseReadOnlyServiceImpl(D baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public List<E> findAll() {
		return baseDao.findAll();
	}

	@Override
	public List<E> find(int maxResults, int firstResult) {
		return baseDao.find(maxResults, firstResult);
	}

	@Override
	public Optional<E> findById(Object id) {
		return baseDao.findById(id);
	}

	@Override
	public int getCount() {
		return baseDao.getCount();
	}
}