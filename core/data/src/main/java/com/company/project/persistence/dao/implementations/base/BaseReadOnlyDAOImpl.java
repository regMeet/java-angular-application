package com.company.project.persistence.dao.implementations.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import com.company.project.persistence.dao.interfaces.base.BaseReadOnlyDAO;
import com.google.common.base.Optional;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class BaseReadOnlyDAOImpl<E extends Serializable> implements BaseReadOnlyDAO<E> {

	private static final long serialVersionUID = 1L;
	private final static Logger log = Logger.getLogger(BaseReadOnlyDAOImpl.class);

	@PersistenceContext
	protected EntityManager em;

	private Class<E> clazz;

	public BaseReadOnlyDAOImpl() {
		Type type = getClass().getGenericSuperclass();
		ParameterizedType pzedType = (ParameterizedType) type;
		clazz = (Class) pzedType.getActualTypeArguments()[0];
		System.out.println("loaded into context " + clazz);
	}

	protected EntityManager getEntityManager() {
		return em;
	}

	public List<E> findAll() {
		return find(true, -1, -1);
	}

	public List<E> find(int maxResults, int firstResult) {
		return find(false, maxResults, firstResult);
	}

	private List<E> find(boolean all, int maxResults, int firstResult) {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(clazz));
		Query q = em.createQuery(cq);
		if (!all) {
			q.setMaxResults(maxResults);
			q.setFirstResult(firstResult);
		}
		return q.getResultList();
	}

	public Optional<E> findById(Object id) {
		log.debug("getting "+ clazz +" instance with id: " + id);
		E entity = em.find(clazz, id);
		return Optional.fromNullable(entity);
	}
	
//	public City findById(Integer id) {
//		log.debug("getting City instance with id: " + id);
//		try {
//			City instance = em.find(City.class, id);
//			log.debug("get successful");
//			return instance;
//		} catch (RuntimeException re) {
//			log.error("get failed", re);
//			throw re;
//		}
//	}

	public int getCount() {
		CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
		Root<E> rt = cq.from(clazz);
		cq.select(em.getCriteriaBuilder().count(rt));
		Query q = em.createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	public Query createQuery(String query){
		return em.createQuery(query);
	}
}
