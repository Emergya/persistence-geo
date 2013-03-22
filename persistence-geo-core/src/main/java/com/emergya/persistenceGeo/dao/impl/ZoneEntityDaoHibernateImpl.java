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

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.ZoneEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractZoneEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Zone DAO Hibernate Implementation
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 * 
 */
@SuppressWarnings("unchecked")
@Repository("zoneEntityDao")
public class ZoneEntityDaoHibernateImpl extends
		GenericHibernateDAOImpl<AbstractZoneEntity, Long> implements
		ZoneEntityDao {

	@Resource
	private Instancer instancer;

	@Autowired
	public void init(SessionFactory sessionFactory) {
		super.init(sessionFactory);
		this.persistentClass = (Class<AbstractZoneEntity>) instancer
				.createZone().getClass();
	}

	/**
	 * Create a new zone in the system
	 * 
	 * @param <code>zone</code>
	 * 
	 * @return Entity from the created zone
	 */
	public AbstractZoneEntity createZone(String zone) {
		AbstractZoneEntity zoneEntity = instancer.createZone();
		zoneEntity.setName(zone);
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
	 * Get a zones list by its type
	 * 
	 * @param <code>zoneType</code>
	 * 
	 * @return Entities list associated with the zone type or null if not found
	 */
	public List<AbstractZoneEntity> findByType(String zoneType) {
		return findByCriteria(Restrictions.eq("type", zoneType));
	}

	/**
	 * Delete a zone by the zone identifier
	 * 
	 * @param <code>zoneID</code>
	 * 
	 */
	public void deleteZone(Long zoneID) {
		AbstractZoneEntity zoneEntity = findById(zoneID, false);
		if (zoneEntity != null) {
			getHibernateTemplate().delete(zoneEntity);
		}
	}

	/**
	 * Get a zones list by the zone name
	 * 
	 * @param <code>zoneName</code>
	 * @param isEnabled
	 * 
	 * @return Entities list associated with the zone name or null if not found
	 */
	public List<AbstractZoneEntity> getZones(String zoneName, Boolean isEnabled) {

		Criteria criteria = getSession().createCriteria(persistentClass).add(
				Restrictions.eq("name", zoneName));

		if (isEnabled == null) {
			criteria.add(Restrictions.isNull("enabled"));
		} else if (isEnabled) {
			criteria.add(Restrictions.eq("enabled", isEnabled));
		} else {
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.isNull("enabled"));
			dis.add(Restrictions.eq("enabled", isEnabled));
			criteria.add(dis);
		}

		return criteria.list();
	}

	/**
	 * Get a zones list by its type
	 * 
	 * @param <code>zoneType</code>
	 * @param isEnabled
	 * 
	 * @return Entities list associated with the zone type or null if not found
	 */
	public List<AbstractZoneEntity> findByType(String zoneType,
			Boolean isEnabled) {

		Criteria criteria = getSession().createCriteria(persistentClass).add(
				Restrictions.eq("type", zoneType));

		if (isEnabled == null) {
			criteria.add(Restrictions.isNull("enabled"));
		} else if (isEnabled) {
			criteria.add(Restrictions.eq("enabled", isEnabled));
		} else {
			Disjunction dis = Restrictions.disjunction();
			dis.add(Restrictions.isNull("enabled"));
			dis.add(Restrictions.eq("enabled", isEnabled));
			criteria.add(dis);
		}

		return criteria.list();
	}

	/**
	 * Get all zones enabled
	 * 
	 * @return Entities
	 */
	public List<AbstractZoneEntity> findAllEnabled() {
		return findByCriteria(Restrictions.eq("enabled", Boolean.TRUE));
	}

	/**
	 * Find zone by id
	 * 
	 * @param idParent
	 * @param isEnabled
	 * 
	 * @return zones
	 */
	public List<AbstractZoneEntity> findByParent(Long idParent) {
		// Criteria criteria = getSession().createCriteria(persistentClass)
		// .add(Restrictions.eq("id", idParent))
		// .createAlias("zoneList", "child");
		//
		// if(isEnabled == null){
		// criteria.add(Restrictions.isNull("enabled"));
		// }else if(isEnabled){
		// criteria.add(Restrictions.eq("enabled", isEnabled));
		// }else{
		// Disjunction dis = Restrictions.disjunction();
		// dis.add(Restrictions.isNull("enabled"));
		// dis.add(Restrictions.eq("enabled", isEnabled));
		// criteria.add(dis);
		// }
		//
		// return criteria.list();

		Criteria criteria = getSession()
				.createCriteria(persistentClass)
				.createAlias("nivelPadre", "parent")
				.add(Restrictions.eq("parent.id", idParent))
				.add(Restrictions.or(Restrictions.eq("enabled", true),
						Restrictions.isNull("enabled")))
				.add(Restrictions.or(Restrictions.eq("parent.enabled", true),
						Restrictions.isNull("parent.enabled")));

		return criteria.list();
	}
}
