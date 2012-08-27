/*
 * AbstractGenericDaoHibernateImpl.java
 * 
 * Copyright (C) 2012
 * 
 * This file is part of Proyecto persistenceGeo
 * 
 * This software is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 * As a special exception, if you link this library with other files to produce
 * an executable, this library does not by itself cause the resulting executable
 * to be covered by the GNU General Public License. This exception does not
 * however invalidate any other reasons why the executable file might be covered
 * by the GNU General Public License.
 */
package com.emergya.persistenceGeo.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.emergya.persistenceGeo.dao.AbstractGenericDao;
import com.emergya.persistenceGeo.model.AbstractEntity;

/**
 * Generic Dao to extend
 * 
 * @author <a href="mailto:adiaz@emergya.es">adiaz</a>
 */
public abstract class AbstractGenericDaoHibernateImpl<T extends AbstractEntity> extends HibernateDaoSupport implements AbstractGenericDao<T>{

	protected Class<T> persistentClass;
	
	protected static String ID="id";
	protected static String PUNTO=".";
	
	protected static final Log LOG = LogFactory
			.getLog(AbstractGenericDaoHibernateImpl.class);
    
    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

	@SuppressWarnings({"unchecked"})
    public AbstractGenericDaoHibernateImpl() {
        try {
            persistentClass = (Class<T>)
                    ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (ClassCastException e) {
            //can be raised when DAO is inherited twice
            persistentClass = (Class<T>)
                    ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
    }
    
    /**
	 * @return clase que mantiene este dao 
	 */
	public Class<T> getClazz(){
		return persistentClass;
	}
    
    /**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... detachedCriterias) {
		DetachedCriteria crit = DetachedCriteria.forClass(persistentClass);
		for (Criterion c : detachedCriterias) {
			crit.add(c);
		}
		return getHibernateTemplate().findByCriteria(crit);
	}

    
    /**
	 * Use this inside subclasses as a convenience method.
	 * 
	 * @param orders
	 * @param detachedCriterias
	 * 
	 * @return List with detachedCriterias ordered by orders[0..N]
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteriaOrdered(Order[] orders, Criterion... detachedCriterias) {
		DetachedCriteria crit = DetachedCriteria.forClass(persistentClass);
		for (Criterion c : detachedCriterias) {
			crit.add(c);
		}
		if(orders != null){
			for (Order order : orders) {
				crit.addOrder(order);
			}
		}
		return getHibernateTemplate().findByCriteria(crit);
	}
	
	 /**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(int firstResult, int maxResults, Order order, Criterion... detachedCriterias) {
		DetachedCriteria crit = DetachedCriteria.forClass(persistentClass);
		for (Criterion c : detachedCriterias) {
			crit.add(c);
		}
		crit.addOrder(order);
		return getHibernateTemplate().findByCriteria(crit, firstResult,maxResults);
	}

	
	 /**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(int firstResult, int maxResults, Order [] orders, Criterion... detachedCriterias) {
		DetachedCriteria crit = DetachedCriteria.forClass(persistentClass);
		for (Criterion c : detachedCriterias) {
			crit.add(c);
		}
		if(orders != null){
			for (Order order : orders) {
				crit.addOrder(order);
			}
		}
		return getHibernateTemplate().findByCriteria(crit, firstResult,maxResults);
	}
    
    /**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Set<Criterion> criterions) {
		DetachedCriteria crit = DetachedCriteria.forClass(persistentClass);
		for (Criterion c : criterions) {
			crit.add(c);
		}
		return getHibernateTemplate().findByCriteria(crit);
	}
    
    public List<T> findAll() {
		return findByCriteria();
	}
		
	/**
	 * Inserta un objeto en la base de datos haciéndolo persistente
	 * @param entity entidad a insertar
	 * @return 
	 */
	public void insert(T entity){
		getHibernateTemplate().persist(entity);
	}

	/**
	 * actualiza un objeto
	 * @param entity
	 */
	@Override
	public void update(T entity) throws HibernateException {
		getHibernateTemplate().merge(entity);
	}

	/**
	 * elimina un objeto
	 * @param entity
	 */
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	/**
	 * obtiene un objeto por su identificador
	 * @param type
	 * @param id
	 * @return
	 * 
	 * @throws HibernateException si hay mas de un resultado
	 */
	public T get(Class<T> type, Serializable id) throws HibernateException{
			return (T) getHibernateTemplate().get(persistentClass, id);
    }

	/**
	 * Refresca objetos de la sesion
	 * @param obj
	 * @return
	 */
	public void refresh(T entity) {
		getHibernateTemplate().refresh(entity);
	}
	
	/**
	 * Lista todos con los elementos iguales a los pasados como argumento
	 * 
	 * @param filtros a ejecutar
	 * 
	 * @return todas las entidades en session que cumplan los filtros
	 */
    @SuppressWarnings("unchecked")
	public List<T> findExact(Map<String, Object> filtros){
    	Criteria crit = getSession().createCriteria(persistentClass);
		
		for (String key: filtros.keySet()){
			if(filtros.get(key) == null){
				crit.add(Restrictions.isNull(key));
			}else{
				crit.add(Restrictions.eq(key, filtros.get(key)));
			}
		}
		
		return (List<T>) crit.list();
    }
    
    /**
	 * Lista todos con los elementos like a los pasados como argumento
	 * 
	 * @param filtros a ejecutar
	 * 
	 * @return todas las entidades en session que cumplan los filtros
	 */
    @SuppressWarnings("unchecked")
	public List<T> findLike(Map<String, Object> filtros){
    	Criteria crit = getSession().createCriteria(persistentClass);
		
		for (String key: filtros.keySet()){
			if(filtros.get(key) == null){
				crit.add(Restrictions.isNull(key));
			}else{
				//crit.add(new LikeSpecialCharsExpression(key, filtros.get(key).toString(), MatchMode.ANYWHERE));
				crit.add(Restrictions.like(key, filtros.get(key).toString(), MatchMode.ANYWHERE));
			}
		}
		
		return (List<T>) crit.list();
    }
}
