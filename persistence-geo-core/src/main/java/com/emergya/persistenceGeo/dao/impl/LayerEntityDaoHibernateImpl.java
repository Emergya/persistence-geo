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
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.LayerEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;
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

	
	/**
	 * Get a layers list by authority
	 * 
	 * @param <code>id</code>
	 * 
	 * @param <code>isChannel</code> compare with entity property and filter by this. False value get null values too
	 * 
	 * @return Entities list associated with the identifier or null if not found 
	 */
	public List<AbstractLayerEntity> findByAuthorityId(Long id,
			Boolean isChannel){
		Criteria criteria = getSession().createCriteria(persistentClass)
						.createAlias("auth", "auth")
						.add(Restrictions.eq("auth.id", id));
		if(isChannel == null){
			criteria.add(Restrictions.isNull("isChannel"));
		}else if(isChannel){
			criteria.add(Restrictions.eq("isChannel", isChannel));
		}else{
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.isNull("isChannel"));
			dis.add(Restrictions.eq("isChannel", isChannel));
			criteria.add(dis);
		}
		
		return criteria.list();
	}

	@Override
	public List<AbstractLayerEntity> getLayersByFolder(Long folderId) {
		Criteria criteria = getSession().createCriteria(persistentClass)
				.createAlias("folder", "folder")
				.add(Restrictions.eq("folder.id", folderId));

		return criteria.list();
	}
	
	/**
	 * Get a layers list by authority
	 * 
	 * @param <code>id</code>
	 * @param <code>isChannel</code> compare with entity property and filter by this. False value get null values too
	 * 
	 * @return Entities list associated with the identifier or null if not found 
	 */
	public List<AbstractLayerEntity> getLayersByFolder(Long folderId,
			Boolean isChannel) {
		Criteria criteria = getSession().createCriteria(persistentClass)
				.createAlias("folder", "folder")
				.add(Restrictions.eq("folder.id", folderId));
		if(isChannel == null){
			criteria.add(Restrictions.isNull("isChannel"));
		}else if(isChannel){
			criteria.add(Restrictions.eq("isChannel", isChannel));
		}else{
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.isNull("isChannel"));
			dis.add(Restrictions.eq("isChannel", isChannel));
			criteria.add(dis);
		}
		return criteria.list();
	}
	
	/**
	 * Get layers by folder
	 * 
	 * @param folderId
	 * @param isChannel
	 * @param isEnabled
	 * 
	 * @return all layers in a folder mark as channel
	 */
	public List<AbstractLayerEntity> getLayersByFolder(Long folderId, Boolean isChannel, Boolean isEnabled){
		Criteria criteria = getSession().createCriteria(persistentClass)
				.createAlias("folder", "folder")
				.add(Restrictions.eq("folder.id", folderId));
		if(isChannel != null){
			if(isChannel){
				criteria.add(Restrictions.eq("isChannel", isChannel));
			}else{
				Disjunction dis = Restrictions.disjunction();
				dis.add(Restrictions.isNull("isChannel"));
				dis.add(Restrictions.eq("isChannel", isChannel));
				criteria.add(dis);
			}
		}
		if(isEnabled == null){
			criteria.add(Restrictions.isNull("enabled"));
		}else if(isEnabled){
			criteria.add(Restrictions.eq("enabled", isEnabled));
		}else{
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.isNull("enabled"));
			dis.add(Restrictions.eq("enabled", isEnabled));
			criteria.add(dis);
		}
		
		return criteria.list();
	}

}
