/*
 * FolderTypeEntityDaoHibernateImpl.java
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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.FolderTypeEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractFolderTypeEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Folder Type DAO Hibernate Implementation
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 * 
 */
@SuppressWarnings("unchecked")
@Repository("folderTypeEntityDao")
public class FolderTypeEntityDaoHibernateImpl extends
		MultiSirDatabaseGenericHibernateDAOImpl<AbstractFolderTypeEntity, Long>
		implements FolderTypeEntityDao {

	private static final String PARENT = "parent";
	private static final String CHILD = "child";
	private static final String DOT = ".";
	private static final String ID = "id";

	@Resource
	private Instancer instancer;

	@Override
	@Autowired
	@Qualifier("sessionFactoryMultiSIRDataSource")
	public void init(SessionFactory sessionFactory) {
		super.init(sessionFactory);
		this.persistentClass = (Class<AbstractFolderTypeEntity>) instancer
				.createFolderType().getClass();
	}

	/**
	 * @return List<AbstractFolderTypeEntity> folder types without children
	 */
	public List<AbstractFolderTypeEntity> getNotParentFolderTypes() {
		Criteria crit = getSession().createCriteria(persistentClass);
		return crit.add(
				Subqueries.notExists(DetachedCriteria
						.forClass(persistentClass, CHILD)
						.add(Restrictions.eqProperty(CHILD + DOT + PARENT,
								crit.getAlias() + DOT + ID))
						.setProjection(Projections.id()))).list();
	}

	/**
	 * Obtain folder types by parent
	 * 
	 * @param parentId
	 * 
	 * @return List<AbstractFolderTypeEntity> folder types with parent
	 *         identified by parentId
	 */
	public List<AbstractFolderTypeEntity> getFolderTypes(Long parentId) {
		return getSession().createCriteria(persistentClass)
				.createAlias(PARENT, PARENT)
				.add(Restrictions.eq(PARENT + DOT + ID, parentId)).list();
	}
}
