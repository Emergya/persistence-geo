/*
 * AbstractService.java
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
 * 
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.service;

import java.io.Serializable;
import java.util.List;

/**
 * Interfaz con los metodos necesarios para la paginacion, creacion, actualización y eliminacion de dtos 
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public interface AbstractService {
	
	/**
	 * 
	 * @return
	 */
	public List<? extends Serializable> getAll();
	
	/**
	 * 
	 * @param first
	 * @param last
	 * @return
	 */
	public List<? extends Serializable> getFromTo(Integer first, Integer last);
	
	/**
	 * Gets all results from the specified first to the specified last, ordered by the given field.
	 * @param first
	 * @param last
	 * @param fieldName
	 * @param asc
	 * @return
	 */
	public List<? extends Serializable> getOrdered(Integer first, Integer last, String fieldName, boolean asc);
	
	/**
	 * 
	 * @return
	 */
	public Long getResults();
	
	/**
	 * Obtain a object by Id
	 * 
	 * @param id
	 * @return
	 */
	public Serializable getById(Long id);
	
	/**
	 * Create a nuew object
	 * 
	 * @param dto
	 * @return
	 */
	public Serializable create(Serializable dto);
	
	/**
	 * Update an object
	 * 
	 * @param dto
	 * 
	 * @return dto updated
	 */
	public Serializable update(Serializable dto);
	
	/**
	 * Remove a object
	 * 
	 * @param dto to be removed
	 */
	public void delete(Serializable dto);
	
}
