/*
 * UserEntityDao.java
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
package com.emergya.persistenceGeo.dao;

import java.util.List;

import com.emergya.persistenceGeo.metaModel.AbstractAuthorityEntity;
import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;

/**
 * Dao para usuarios
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public interface UserEntityDao extends GenericDAO<AbstractUserEntity, Long>{
	
	/**
	 * Crea un nuevo usuario en el sistema
	 * 
	 * @param <code>userName</code>
	 * @param <code>password</code>
	 * 
	 * @return entidad del usuario creado 
	 */
	public AbstractUserEntity createUser(String userName, String password);
	
	/**
	 * Obtiene un usuario por nombre de usuario
	 * 
	 * @param userName
	 * @param password
	 * 
	 * @return entidad asociada al nombre de usuario o null si no se encuentra
	 */
	public AbstractUserEntity getUser(String userName, String password);
	
	/**
	 * Obtiene un usuario por nombre de usuario
	 * 
	 * @param userName
	 * 
	 * @return entidad asociada al nombre de usuario o null si no se encuentra
	 */
	public AbstractUserEntity getUser(String userName);
	
	/**
	 * Get a users list by a names users list
	 * 
	 * @param names
	 * 
	 * @return Entities list associated with the names users list or null if not found 
	 */
	public List<AbstractUserEntity> findByName(List<String> names);

	/**
	 * Get an authority by a user identifier
	 * 
	 * @param user_id
	 * 
	 * @return Entity associated with the user identifier or null if not found
	 */
	public AbstractAuthorityEntity findByUserID(Long user_id);
	
	/**
	 * Get user list by a auth id
	 * 
	 * @param authId
	 * 
	 * @return users of the group
	 */
	public List<AbstractUserEntity> findByAuthID(Long authId);

}
