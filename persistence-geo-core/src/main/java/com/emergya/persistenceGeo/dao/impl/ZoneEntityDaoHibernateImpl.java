/*
 * ZoneEntityDaoHibernateImpl.java
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

import com.emergya.persistenceGeo.dao.ZoneEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractZoneEntity;

/**
 * Zone DAO Hibernate Implementation
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@Repository("zoneEntityDao")
public class ZoneEntityDaoHibernateImpl extends GenericHibernateDAOImpl<AbstractZoneEntity, Long> implements ZoneEntityDao {

	/**
	 * Create a new zone in the system
	 * 
	 * @param <code>zone</code>
	 * 
	 * @return Entity from the created zone
	 */
	public AbstractZoneEntity createZone(String zone) {
		AbstractZoneEntity zoneEntity = new AbstractZoneEntity(zone);
		getHibernateTemplate().save(zoneEntity);
		return zoneEntity;
	}

	/**
	 * Get a zones list by the zone name 
	 * 
	 * @param <code>zoneName</code>
	 * 
	 * @return Entities list associated with the zone name or null if not found 
	 */
	public List<AbstractZoneEntity> getZones(String zoneName) {
		return findByCriteria(Restrictions.eq("name", zoneName));
	}

	/**
	 * Delete a zone by the zone identifier 
	 * 
	 * @param <code>zoneID</code>
	 * 
	 */
	public void deleteZone(Long zoneID) {
		AbstractZoneEntity zoneEntity = findById(zoneID, false);
		if(zoneEntity != null){
			getHibernateTemplate().delete(zoneEntity);
		}
	}

}
