package com.company.project.persistence.dao.implementations.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.company.project.persistence.dao.interfaces.base.BaseDAO;

@SuppressWarnings("unchecked")
public class BaseDAOImpl<E extends Serializable> extends BaseReadOnlyDAOImpl<E>	implements BaseDAO<E> {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(BaseDAOImpl.class);

	private Class<E> clazz;

	public BaseDAOImpl() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType pzedType = (ParameterizedType) type;
		clazz = (Class<E>) pzedType.getActualTypeArguments()[0];

		em = getEntityManager();
	}

	@Override
	public void create(E entity) {
		log.debug("creating City instance" + entity.getClass());
		em.persist(entity);
	}

	@Override
	public void update(E entity) {
		log.debug("updating City instance");
		em.merge(entity);
	}

//	@Override
//	public void delete(Object id) {
//		log.debug("deleting City instance");
//		em.remove(em.getReference(clazz, id));
//		em.remove(em.getReference(clazz, id));
//	}
	
	@Override
	public void delete(E entity) {
		log.debug("deleting 2 City instance");
//		em.remove(entity);
		em.remove(em.merge(entity));
	}
	
	@Override
	public void delete(Integer id) {
		E entity = findById(id);
		em.remove(entity);
	}
	
//	public City merge(City detachedInstance) {
//		log.debug("merging City instance");
//		try {
//			City result = em.merge(detachedInstance);
//			log.debug("merge successful");
//			return result;
//		} catch (RuntimeException re) {
//			log.error("merge failed", re);
//			throw re;
//		}
//	}

}
