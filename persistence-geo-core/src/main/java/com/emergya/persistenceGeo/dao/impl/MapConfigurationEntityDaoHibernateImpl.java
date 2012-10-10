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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.LayerEntityDao;
import com.emergya.persistenceGeo.dao.MapConfigurationEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;
import com.emergya.persistenceGeo.metaModel.AbstractMapConfigurationEntity;
import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;
import com.emergya.persistenceGeo.model.MapConfigurationEntity;

/**
 * Layer DAO Hibernate Implementation
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Repository("mapConfigurationEntityDao")
public class MapConfigurationEntityDaoHibernateImpl extends GenericHibernateDAOImpl<AbstractMapConfigurationEntity, Long> implements MapConfigurationEntityDao {

	@Resource
	private Instancer instancer;

	@Autowired
    public void init(SessionFactory sessionFactory) {
        super.init(sessionFactory);
		this.persistentClass = (Class<AbstractMapConfigurationEntity>) instancer.createMapConfiguration().getClass();
    }

	@Override
	public void updateMapConfiguration(Long mapConfigurationID, String bbox, String projection, String resolutions) {
		
		AbstractMapConfigurationEntity mc = (AbstractMapConfigurationEntity) getSession().createCriteria(persistentClass)
				.add(Restrictions.eq("id", mapConfigurationID)).uniqueResult();
		mc.setBbox(bbox);
		mc.setProjection(projection);
		mc.setResolutions(resolutions);
		getHibernateTemplate().update(mc);
		
	}

	@Override
	public AbstractMapConfigurationEntity loadConfiguration() {
	
	 return (AbstractMapConfigurationEntity) getSession().createCriteria(persistentClass).uniqueResult();
		
//	 if (mc!=null && mc.size()!=0)
//			return mc.get(0);
//	 
//	else
//			return null;
		
	}
	
	
	public void removeMapConfiguration(Long mapID){
		AbstractMapConfigurationEntity mc = findById(mapID, false);
		getHibernateTemplate().delete(mc);
	}

}
