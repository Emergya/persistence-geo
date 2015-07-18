/*
 * AbstractResourceEntityDaoHibernateImpl.java
 * 
 * Copyright (C) 2013
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

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.ResourceEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractResourceEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Dao implementation for5 resources
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class AbstractResourceEntityDaoHibernateImpl extends
		MultiSirDatabaseGenericHibernateDAOImpl<AbstractResourceEntity, Long>
		implements ResourceEntityDao {

	@Resource
	private Instancer instancer;

	private static final String ACCESS_ID = "accessId";

	@Override
	@Autowired
	@Qualifier("sessionFactoryMultiSIRDataSource")	
	public void init(SessionFactory sessionFactory) {
		super.init(sessionFactory);
		this.persistentClass = (Class<AbstractResourceEntity>) instancer
				.createResourceEntity().getClass();
	}

	@Override
	public AbstractResourceEntity findByAccessId(Long accessId) {
		return (AbstractResourceEntity) findByCriteria(
				Restrictions.eq(ACCESS_ID, accessId)).get(0);
	}

	@Override
	public void deleteByAccessId(Long accessId) {
		makePersistent(findByAccessId(accessId));
	}
}
