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

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.UserEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractAuthorityEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;
import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Implementacion de Usuario dao para hibernate
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@SuppressWarnings("unchecked")
@Repository("userEntityDao")
public class UserEntityDaoHibernateImpl extends GenericHibernateDAOImpl<AbstractUserEntity, Long> implements UserEntityDao{

	@Resource
	private Instancer instancer;

	@Autowired
    public void init(SessionFactory sessionFactory) {
        super.init(sessionFactory);
		this.persistentClass = (Class<AbstractUserEntity>) instancer.createUser().getClass();
    }
	
	/**
	 * Crea un nuevo usuario en el sistema
	 * 
	 * @param <code>userName</code>
	 * @param <code>password</code>
	 * 
	 * @return entidad del usuario creado 
	 */
	public AbstractUserEntity createUser(String userName, String password){
		AbstractUserEntity entity = instancer.createUser();
		entity.setUsername(userName);
		entity.setPassword(password);
		entity = this.makePersistent(entity);
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
	public AbstractUserEntity getUser(String userName, String password){
		List<AbstractUserEntity> res = findByCriteria(
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
	public AbstractUserEntity getUser(String userName){
		List<AbstractUserEntity> res = findByCriteria(
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
	public List<AbstractUserEntity> findByName(List<String> names) {
		List<AbstractUserEntity> res = new LinkedList<AbstractUserEntity>();
		if(names != null){
			for(String name: names){
				List<AbstractUserEntity> entityList = findByCriteria(Restrictions.eq("username", name));
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
	public AbstractAuthorityEntity findByUserID(Long user_id) {
		List<AbstractUserEntity> res = findByCriteria(Restrictions.eq("id", user_id));
		AbstractAuthorityEntity auth = null;
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
	public List<AbstractLayerEntity> findLayerByUserID(Long user_id) {
		List<AbstractUserEntity> res = findByCriteria(Restrictions.eq("id", user_id));
		List<AbstractLayerEntity> layer = null;
		if(res != null && res.size()>0){
			layer = res.get(0).getLayerList();
		}
		return layer;
	}
}
