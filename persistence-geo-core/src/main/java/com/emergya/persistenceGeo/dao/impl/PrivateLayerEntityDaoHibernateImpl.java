/*
 * PrivateLayerEntityDaoHibernateImpl.java
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
 * Authors:: Moisés Arcos Santiago (mailto:marcos@emergya.com)
 */
package com.emergya.persistenceGeo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.PrivateLayerEntityDao;
import com.emergya.persistenceGeo.metaModel.PrivateLayerEntity;

/**
 * Private Layer DAO Hibernate Implementation
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@Repository("privateLayerEntityDao")
public class PrivateLayerEntityDaoHibernateImpl extends
		GenericHibernateDAOImpl<PrivateLayerEntity, Long> implements PrivateLayerEntityDao {

	/**
	 * Save the private layer in the system
	 * 
	 * @param <code>privateLayerEntity</code>
	 * 
	 * @return Entity identifier from the save private layer
	 */
	public Long save(PrivateLayerEntity privateLayerEntity) {
		return (Long) getHibernateTemplate().save(privateLayerEntity);
	}

	/**
	 * Get a private layers list by the private layer name 
	 * 
	 * @param <code>privateLayerName</code>
	 * 
	 * @return Entities list associated with the private layer name or null if not found 
	 */
	public List<PrivateLayerEntity> getPrivateLayers(String privateLayerName) {
		return findByCriteria(Restrictions.eq("name", privateLayerName));
	}

	/**
	 * Delete a private layer by the private layer identifier 
	 * 
	 * @param <code>privateLayerID</code>
	 * 
	 */
	public void delete(Long privateLayerID) {
		PrivateLayerEntity entity = findById(privateLayerID, false);
		if(entity != null){
			getHibernateTemplate().delete(entity);
		}
	}

}
