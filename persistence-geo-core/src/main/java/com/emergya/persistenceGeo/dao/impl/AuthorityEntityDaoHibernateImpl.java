/*
 * AbstractAuthorityEntityDao.java
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

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.AuthorityEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractAuthorityEntity;
import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;

/**
 * Implementacion de authority dao para hibernate
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@Repository
public class AuthorityEntityDaoHibernateImpl extends GenericHibernateDAOImpl<AbstractAuthorityEntity, Long> implements AuthorityEntityDao{
	
	protected final String PEOPLE = "people";
	protected final String LAYER = "layer";
	protected final String USER_ID = "user_id";
	protected final String LAYER_ID = "id";
	protected final String PEOPLE_USER_ID = PEOPLE + "." + USER_ID;
	protected final String LAYER_LAYER_ID = LAYER + "." + LAYER_ID;
	protected final String AUTHORITY = "authority";

	public Long save(AbstractAuthorityEntity AbstractAuthorityEntity) {
		return (Long) getHibernateTemplate().save(AbstractAuthorityEntity);
	}

	public void delete(Long idgrupo) {
		AbstractAuthorityEntity entity = findById(idgrupo, false);
		if(entity != null){
			getHibernateTemplate().delete(entity);
		}
	}

	@SuppressWarnings("unchecked")
	public List<AbstractAuthorityEntity> findByUser(Long user_id) {
		return getSession().createCriteria(AbstractAuthorityEntity.class)
				.createAlias(PEOPLE, PEOPLE)
				.add(Restrictions.eq(PEOPLE_USER_ID, user_id)).list();
	}

	public void clearUser(Long user_id) {
		List<AbstractAuthorityEntity> authorities = findByUser(user_id);
		if(authorities != null){
			for (AbstractAuthorityEntity authority: authorities){
				for(AbstractUserEntity user: authority.getPeople()){
					if(user.getUser_id().equals(user_id)){
						authority.getPeople().remove(user);
						break;
					}
				}
				save(authority);
			}
		}
	}

	public List<AbstractAuthorityEntity> findByName(String name) {
		return findByCriteria(Restrictions.eq(AUTHORITY, name));
	}

	public List<AbstractAuthorityEntity> findByName(List<String> names) {
		return findByCriteria(Restrictions.in(AUTHORITY, names));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AbstractAuthorityEntity> findByLayer(Long layer_id) {
		return getSession().createCriteria(AbstractAuthorityEntity.class)
				.createAlias(LAYER, LAYER)
				.add(Restrictions.eq(LAYER_LAYER_ID, layer_id)).list();
	}
}
