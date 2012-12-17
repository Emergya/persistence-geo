/*
 * FolderEntityDao.java
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
package com.emergya.persistenceGeo.dao;

import java.util.List;

import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;

/**
 * DAO that represents the folder
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
public interface FolderEntityDao extends GenericDAO<AbstractFolderEntity, Long> {

	/**
	 * Create a new folder in the system
	 * 
	 * @param <code>nameFolder</code>
	 * 
	 * @return Entity from the created folder
	 */
	public AbstractFolderEntity createFolder(String nameFolder);
	
	/**
	 * Get a folders list by the folder name 
	 * 
	 * @param <code>folderName</code>
	 * 
	 * @return Entities list associated with the folder name or null if not found 
	 */
	public List<AbstractFolderEntity> getFolders(String folderName);
	
	/**
	 * Get a folders list by the parentFolder 
	 * 
	 * @param <code>parentFolder</code>
	 * 
	 * @return Entities list associated with the folder parentFolder or null if not found 
	 */
	public List<AbstractFolderEntity> getFolders(Long parentFolder);
	
	/**
	 * Delete a folder by the folder identifier 
	 * 
	 * @param <code>folderID</code>
	 * 
	 */
	public void deleteFolder(Long folderID);
	
	/**
	 * Get a folders list by the names folders list
	 * 
	 * @param <code>names</code>
	 * 
	 * @return Entities list associated with the names folders list or null if not found 
	 */
	public List<AbstractFolderEntity> findByName(List<String> names);
	
	/**
	 * Get a folders root for a user
	 * 
	 * @param <code>idUser</code>
	 * 
	 * @return Entity without parent folder for the user 
	 */
	public AbstractFolderEntity findRootByUser(Long idUser);
	
	/**
	 * Get a folders root for a group
	 * 
	 * @param <code>idGroup</code>
	 * 
	 * @return Entity without parent folder for the group 
	 */
	public AbstractFolderEntity findRootByGroup(Long idGroup);

	/**
	 * Get all channel folders filtered
	 * 
	 * @param inZone indicates if obtain channel folders with a zone. If this parameter is null only obtain not zoned channels
	 * @param idZone filter by zone. Obtain only channels of the zone identified by <code>idZone</code>
	 * 
	 * @return folder list
	 */
	public List<AbstractFolderEntity> getChannelFolders(Boolean inZone, Long idZone);

    /**
     * Get a folders list by zones. If zoneId is NULL returns all the
     * folder not associated to any zone.
     *
     * @params <code>zoneId</code>
     *
     * @return Entities list associated with the zoneId or null if not found
     */
    public List<AbstractFolderEntity> findByZone(Long zoneId);

    /**
     * Get a folders list by zones with an specific parent. If zoneId is NULL
     * returns all the folder not associated to any zone. If parentId is NULL
     * the returned folders are root folders.
     *
     * @params <code>zoneId</code>
     * @params <code>parentId</code>
     *
     * @return Entities list associated with the zoneId or null if not found
     */
    public List<AbstractFolderEntity> findByZone(Long zoneId, Long parentId);

    /**
     * Get a folders list by zones with an specific parent. If zoneId is NULL
     * returns all the folder not associated to any zone. If parentId is NULL
     * the returned folders are root folders.
     *
     * @param <code>zoneId</code>
     * @param <code>parentId</code>
     * @param <code>isEnabled</code>
     *
     * @return Entities list associated with the zoneId or null if not found
     */
    public List<AbstractFolderEntity> findByZone(Long zoneId, Long parentId, Boolean isEnable);

    /**
     * Get a folders list by zones. If zoneId is NULL returns all the
     * folder not associated to any zone.
     *
     * @param <code>zoneId</code>
     * @param <code>isEnabled</code>
     *
     * @return Entities list associated with the zoneId or null if not found
     */
    public List<AbstractFolderEntity> findByZone(Long zoneId, Boolean isEnable);

	/**
	 * Get all channel folders filtered
	 * 
	 * @param inZone indicates if obtain channel folders with a zone. If this parameter is null only obtain not zoned channels
	 * @param idZone filter by zone. Obtain only channels of the zone identified by <code>idZone</code>
     * @param <code>isEnabled</code>
	 * 
	 * @return folder list
	 */
	public List<AbstractFolderEntity> getChannelFolders(Boolean inZone, Long idZone, Boolean isEnable);
}
