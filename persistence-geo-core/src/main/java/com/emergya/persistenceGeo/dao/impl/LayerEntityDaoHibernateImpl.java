/*
 * LayerEntityDaoHibernateImpl.java
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

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.LayerEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;
import com.emergya.persistenceGeo.metaModel.AbstractStyleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Layer DAO Hibernate Implementation
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Repository("layerEntityDao")
public class LayerEntityDaoHibernateImpl extends GenericHibernateDAOImpl<AbstractLayerEntity, Long> implements LayerEntityDao {

	@Resource
	private Instancer instancer;

	@Autowired
    public void init(SessionFactory sessionFactory) {
        super.init(sessionFactory);
		this.persistentClass = (Class<AbstractLayerEntity>) instancer.createLayer().getClass();
    }
	
	/**
	 * Create a new layer in the system
	 * 
	 * @param <code>layerName</code>
	 * 
	 * @return Entity from the new layer
	 */
	public AbstractLayerEntity createLayer(String layerName) {
		AbstractLayerEntity entity = instancer.createLayer();
		entity.setName(layerName);
		this.save(entity);
		return entity;
	}
	
	/**
	 * Save the layer in the system
	 * 
	 * @param <code>layerEntity</code>
	 * 
	 * @return Entity identifier from the save layer
	 */
	public Long save(AbstractLayerEntity layerEntity) {
		return (Long) getHibernateTemplate().save(layerEntity);
	}

	/**
	 * Get a layers list by the private layer name 
	 * 
	 * @param <code>layerName</code>
	 * 
	 * @return Entities list associated with the layer name or null if not found 
	 */
	public List<AbstractLayerEntity> getLayers(String layerName) {
		return findByCriteria(Restrictions.eq("name", layerName));
	}

	/**
	 * Delete a layer by the layer identifier 
	 * 
	 * @param <code>layerID</code>
	 * 
	 */
	public void delete(Long layerID) {
		AbstractLayerEntity entity = findById(layerID, false);
		if(entity != null){
			getHibernateTemplate().delete(entity);
		}
	}

	/**
	 * Get a users list by a layer id
	 * 
	 * @param layerID
	 * 
	 * @return Entities list associated with the layer identifier or null if not found 
	 */
	public AbstractUserEntity findByLayer(Long layerID) {
		AbstractLayerEntity entity = findById(layerID, false);
		return entity.getUser();
	}

	/**
	 * Get a folders list by the layer identifier
	 * 
	 * @param <code>layerID</code>
	 * 
	 * @return Entities list associated with the layer identifier or null if not found 
	 */
	public List<AbstractFolderEntity> findFolderByLayer(Long layerID) {
		AbstractLayerEntity entity = findById(layerID, false);
		return entity.getFolderList();
	}

	/**
	 * Get a style list by the layer identifier
	 * 
	 * @param <code>layerID</code>
	 * 
	 * @return Entities list associated with the layer identifier or null if not found 
	 */
	public List<AbstractStyleEntity> findStyleByLayer(Long layerID) {
		AbstractLayerEntity entity = findById(layerID, false);
		return entity.getStyleList();
	}

	@Override
	public List<AbstractLayerEntity> findByUserId(Long id) {
		
		Criteria criteria = getSession().createCriteria(persistentClass)
						.createAlias("user", "user")
						.add(Restrictions.eq("user.id", id));
		
		return criteria.list();
	}

	@Override
	public List<AbstractLayerEntity> findByAuthorityId(Long id) {
		
		Criteria criteria = getSession().createCriteria(persistentClass)
						.createAlias("auth", "auth")
						.add(Restrictions.eq("auth.id", id));
		
		return criteria.list();
	}

}
