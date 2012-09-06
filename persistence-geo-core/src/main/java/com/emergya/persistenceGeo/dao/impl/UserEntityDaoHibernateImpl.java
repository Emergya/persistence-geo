/*
 * UserEntityDaoHibernateImpl.java
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
package com.emergya.persistenceGeo.dao.impl;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.UserEntityDao;
import com.emergya.persistenceGeo.model.AuthorityEntity;
import com.emergya.persistenceGeo.model.LayerEntity;
import com.emergya.persistenceGeo.model.PrivateLayerEntity;
import com.emergya.persistenceGeo.model.UserEntity;

/**
 * Implementacion de Usuario dao para hibernate
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@Repository("userEntityDao")
public class UserEntityDaoHibernateImpl extends GenericHibernateDAOImpl<UserEntity, Long> implements UserEntityDao{
	
	/**
	 * Crea un nuevo usuario en el sistema
	 * 
	 * @param <code>userName</code>
	 * @param <code>password</code>
	 * 
	 * @return entidad del usuario creado 
	 */
	public UserEntity createUser(String userName, String password){
		UserEntity entity = new UserEntity(userName);
		entity.setPassword(password);
		this.makePersistent(entity);
		return entity;
	}
	
	/**
	 * Obtiene un usuario por nombre de usuario
	 * 
	 * @param userName
	 * @param password
	 * 
	 * @return entidad asociada al nombre de usuario o null si no se encuentra
	 */
	public UserEntity getUser(String userName, String password){
		List<UserEntity> res = findByCriteria(
				Restrictions.eq("username", userName),
				Restrictions.eq("password", password));
		if(res != null && res.size()>0){
			return res.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * Obtiene un usuario por nombre de usuario
	 * 
	 * @param userName
	 * 
	 * @return entidad asociada al nombre de usuario o null si no se encuentra
	 */
	public UserEntity getUser(String userName){
		List<UserEntity> res = findByCriteria(
				Restrictions.eq("username", userName));
		if(res != null && res.size()>0){
			return res.get(0);
		}else{
			return null;
		}
	}

	/**
	 * Get a users list by a names users list
	 * 
	 * @param names
	 * 
	 * @return Entities list associated with the names users list or null if not found 
	 */
	public List<UserEntity> findByName(List<String> names) {
		List<UserEntity> res = new LinkedList<UserEntity>();
		if(names != null){
			for(String name: names){
				List<UserEntity> entityList = findByCriteria(Restrictions.eq("username", name));
				if(entityList != null && entityList.size()>0){
					res.addAll(entityList);
				}
			}
		}
		return res;
	}

	/**
	 * Get an authority by a user identifier
	 * 
	 * @param user_id
	 * 
	 * @return Entity associated with the user identifier or null if not found
	 */
	public AuthorityEntity findByUserID(Long user_id) {
		List<UserEntity> res = findByCriteria(Restrictions.eq("user_id", user_id));
		AuthorityEntity auth = null;
		if(res != null && res.size()>0){
			auth = res.get(0).getAuthority();
		}
		return auth;
	}

	/**
	 * Get a layer by a user identifier
	 * 
	 * @param user_id
	 * 
	 * @return Entity associated with the user identifier or null if not found
	 */
	public List<LayerEntity> findLayerByUserID(Long user_id) {
		List<UserEntity> res = findByCriteria(Restrictions.eq("user_id", user_id));
		List<LayerEntity> layer = null;
		if(res != null && res.size()>0){
			layer = res.get(0).getLayerList();
		}
		return layer;
	}
	
	/**
	 * Get a private layer by a user identifier
	 * 
	 * @param user_id
	 * 
	 * @return Entity associated with the user identifier or null if not found
	 */
	public List<PrivateLayerEntity> findPrivateLayerByUserID(Long user_id) {
		List<UserEntity> res = findByCriteria(Restrictions.eq("user_id", user_id));
		List<PrivateLayerEntity> layer = null;
		if(res != null && res.size()>0){
			layer = res.get(0).getPrivateLayerList();
		}
		return layer;
	}
}
