/*
 * PermissionEntityDaoHibernateImpl.java
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

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.AuthorityEntityDao;
import com.emergya.persistenceGeo.dao.AuthorityTypeEntityDao;
import com.emergya.persistenceGeo.dao.PermissionEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractAuthorityTypeEntity;
import com.emergya.persistenceGeo.metaModel.AbstractPermissionEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Permission DAO Hibernate Implementation
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Repository("permissionEntityDao")
public class PermissionEntityDaoHibernateImpl extends
		MultiSirDatabaseGenericHibernateDAOImpl<AbstractPermissionEntity, Long>
		implements PermissionEntityDao {

	@Resource
	private Instancer instancer;

	@Resource
	private AuthorityEntityDao authorityDao;

	@Resource
	private AuthorityTypeEntityDao authorityTypeDao;

	@Override
	@Autowired
	@Qualifier("sessionFactoryMultiSIRDataSource")
	public void init(SessionFactory sessionFactory) {
		super.init(sessionFactory);
		this.persistentClass = (Class<AbstractPermissionEntity>) instancer
				.createPermission().getClass();
	}

	/**
	 * Create a new permission in the system
	 * 
	 * @param <code>permission</code>
	 * 
	 * @return Entity from the created permission
	 */
	public AbstractPermissionEntity createPermission(String permission) {
		AbstractPermissionEntity permissionEntity = instancer
				.createPermission();
		permissionEntity.setName(permission);
		getHibernateTemplate().save(permissionEntity);
		return permissionEntity;
	}

	/**
	 * Get a permissions list by the permission name
	 * 
	 * @param <code>permissionName</code>
	 * 
	 * @return Entities list associated with the permission name or null if not
	 *         found
	 */
	public List<AbstractPermissionEntity> getPermissions(String permissionName) {
		return findByCriteria(Restrictions.eq("name", permissionName));
	}

	/**
	 * Delete a permission by the permission identifier
	 * 
	 * @param <code>permissionID</code>
	 * 
	 */
	public void deletePermission(Long permissionID) {
		AbstractPermissionEntity permissionEntity = findById(permissionID,
				false);
		if (permissionEntity != null) {
			getHibernateTemplate().delete(permissionEntity);
		}
	}

	/**
	 * Get a permissions list by authority
	 * 
	 * @param <code>authorithyId</code> if this parameter is null search
	 *        permissions without authority type
	 * 
	 * @return Entities list associated with the authorithyId name or null if
	 *         not found
	 */
	public List<AbstractPermissionEntity> getPermissionsByAuthorithy(
			Long authorithyId) {
		if (authorithyId != null) {
			AbstractAuthorityTypeEntity authorityType = authorityDao.findById(
					authorithyId, false).getAuthType();
			return authorityType.getPermissionList();
		} else {
			return findByCriteria(Restrictions.isEmpty("authTypeList"));
		}
	}

	/**
	 * Get a permissions list by authority type
	 * 
	 * @param <code>authorithyTypeId</code>
	 * 
	 * @return Entities list associated with the authorithyTypeId name or null
	 *         if not found
	 */
	public List<AbstractPermissionEntity> getPermissionsByAuthorithyType(
			Long authorithyTypeId) {
		if (authorithyTypeId != null) {
			AbstractAuthorityTypeEntity authorityType = authorityTypeDao
					.findById(authorithyTypeId, false);
			return authorityType.getPermissionList();
		} else {
			return findByCriteria(Restrictions.isEmpty("authTypeList"));
		}
	}

}
