/*
 * AbstractGenericDao.java
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
package com.emergya.persistenceGeo.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

import com.emergya.persistenceGeo.model.AbstractEntity;

/**
 * Generic Dao to extend
 * 
 * @author <a href="mailto:adiaz@emergya.es">adiaz</a>
 */
public interface AbstractGenericDao<T extends AbstractEntity> {
	
	/**
	 * @return clase que mantiene este dao 
	 */
	Class<T> getClazz();
	
	/**
	 * Inserta un objeto en la base de datos haciendolo persistente
	 * @param entity entidad a insertar
	 * @return 
	 */
	void insert(T entity);
	
	/**
	 * actualiza un objeto
	 * @param entity
	 */
	void update(T entity) throws HibernateException;
	
	/**
	 * elimina un objeto
	 * @param entity
	 */
	void delete(T entity);
	
	/**
	 * obtiene un objeto por su identificador
	 * @param id
	 * @return
	 */
	T get(Class<T> type, Serializable id);

	/**
	 * Lista todos las entidades de la clases
	 * 
	 * @return todas las entidades en session
	 */
    List<T> findAll();

	/**
	 * Lista todos con los elementos iguales a los pasados como argumento
	 * 
	 * @param filtros a ejecutar
	 * 
	 * @return todas las entidades en session que cumplan los filtros
	 */
    List<T> findExact(Map<String, Object> filtros);
    
    /**
	 * Lista todos con los elementos like a los pasados como argumento
	 * 
	 * @param filtros a ejecutar
	 * 
	 * @return todas las entidades en session que cumplan los filtros
	 */
    List<T> findLike(Map<String, Object> filtros);
	
	/**
	 * Refresca objetos de la sesion
	 * @param obj
	 */
	void refresh(T obj);
	
}
