package com.company.project.persistence.dao.implementations.base;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.company.project.persistence.dao.interfaces.base.BaseDAO;
import com.google.common.base.Optional;

public class BaseDAOImpl<E extends Serializable> extends BaseReadOnlyDAOImpl<E> implements BaseDAO<E> {

	private static final long serialVersionUID = -6022932080430514386L;
	private final static Logger log = Logger.getLogger(BaseDAOImpl.class);

	public BaseDAOImpl() {
	}

	@Override
	public void create(E entity) {
		log.debug("creating City instance" + entity.getClass());
		em.persist(entity);
	}

	@Override
	public E update(E entity) {
		log.debug("updating City instance");
		E entityMerged = em.merge(entity);
//		em.flush();
		return entityMerged;
	}

	// @Override
	// public void delete(Object id) {
	// log.debug("deleting City instance");
	// em.remove(em.getReference(clazz, id));
	// em.remove(em.getReference(clazz, id));
	// }

	@Override
	public void delete(E entity) {
		log.debug("deleting 2 City instance");
		// em.remove(entity);
		em.remove(em.merge(entity));
	}

	@Override
	public void delete(Long id) {
		Optional<E> entity = findById(id);
		em.remove(entity.get());
	}

}
