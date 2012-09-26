/*
 * FolderEntityDaoHibernateImpl.java
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

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.FolderEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Folder DAO Hibernate Implementation
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Repository("folderEntityDao")
public class FolderEntityDaoHibernateImpl extends GenericHibernateDAOImpl<AbstractFolderEntity, Long> implements FolderEntityDao {

	@Resource
	private Instancer instancer;

	@Autowired
    public void init(SessionFactory sessionFactory) {
        super.init(sessionFactory);
		this.persistentClass = (Class<AbstractFolderEntity>) instancer.createFolder().getClass();
    }

	/**
	 * Create a new folder in the system
	 * 
	 * @param <code>nameFolder</code>
	 * 
	 * @return Entity from the created folder
	 */
	public AbstractFolderEntity createFolder(String nameFolder) {
		AbstractFolderEntity entity = instancer.createFolder();
		entity.setName(nameFolder);
		getHibernateTemplate().save(entity);
		return entity;
	}

	/**
	 * Get a folders list by the folder name 
	 * 
	 * @param <code>folderName</code>
	 * 
	 * @return Entities list associated with the folder name or null if not found 
	 */
	public List<AbstractFolderEntity> getFolders(String folderName) {
		return findByCriteria(Restrictions.eq("name", folderName));
	}

	/**
	 * Delete a folder by the folder identifier 
	 * 
	 * @param <code>folderID</code>
	 * 
	 */
	public void deleteFolder(Long folderID) {
		AbstractFolderEntity entity = findById(folderID, false);
		if(entity != null){
			getHibernateTemplate().delete(entity);
		}
	}

	/**
	 * Get a folders list by the names folders list
	 * 
	 * @param <code>names</code>
	 * 
	 * @return Entities list associated with the names folders list or null if not found 
	 */
	public List<AbstractFolderEntity> findByName(List<String> names) {
		List<AbstractFolderEntity> folderList = new LinkedList<AbstractFolderEntity>();
		if(names != null){
			for(String name: names){
				folderList.addAll(getFolders(name));
			}
		}
		return folderList;
	}

	@Override
	public AbstractFolderEntity findRootByUser(Long idUser) {
		return (AbstractFolderEntity) getSession().createCriteria(persistentClass)
				.add(Restrictions.isNull("parent"))
				.createAlias("user", "user")
				.add(Restrictions.eq("user.id", idUser)).uniqueResult();
	}

	@Override
	public AbstractFolderEntity findRootByGroup(Long idGroup) {
		return (AbstractFolderEntity) getSession().createCriteria(persistentClass)
				.add(Restrictions.isNull("parent"))
				.createAlias("authority", "authority")
				.add(Restrictions.eq("authority.id", idGroup)).uniqueResult();
	}

	@Override
	public List<AbstractFolderEntity> getFolders(Long parentFolder) {
		return getSession().createCriteria(persistentClass)
				.createAlias("parent", "parent")
				.add(Restrictions.eq("parent.id", parentFolder)).list();
	}

}
