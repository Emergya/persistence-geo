/*
 * LayerTypeEntityDaoHibernateImpl.java
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

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.LayerTypeEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractLayerTypeEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerTypePropertyEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Layer Type DAO Hibernate Implementation
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
@SuppressWarnings("unchecked")
@Repository("layerTypeEntityDao")
public class LayerTypeEntityDaoHibernateImpl extends
		MultiSirDatabaseGenericHibernateDAOImpl<AbstractLayerTypeEntity, Long>
		implements LayerTypeEntityDao {

	@Resource
	private Instancer instancer;

	@Autowired
	public void init(SessionFactory sessionFactory) {
		super.init(sessionFactory);
		this.persistentClass = (Class<AbstractLayerTypeEntity>) instancer
				.createLayerType().getClass();
	}

	public List<AbstractLayerTypePropertyEntity> getLayerTypeProperties(
			String layerTypeName) {
		List<AbstractLayerTypeEntity> result = getSession()
				.createCriteria(persistentClass)
				.add(Restrictions.eq("name", layerTypeName)).list();
		return (result != null && result.size() > 0) ? result.get(0)
				.getDefaultProperties() : ListUtils.EMPTY_LIST;
	}

	public AbstractLayerTypeEntity getLayerType(String layerTypeName) {
		List<AbstractLayerTypeEntity> result = getSession()
				.createCriteria(persistentClass)
				.add(Restrictions.eq("name", layerTypeName)).list();
		return (result != null && result.size() > 0) ? result.get(0) : null;
	}
}
