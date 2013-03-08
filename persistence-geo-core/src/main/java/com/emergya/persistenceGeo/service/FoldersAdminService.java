/*
 * FoldersService.java
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
package com.emergya.persistenceGeo.service;

import java.util.List;

import com.emergya.persistenceGeo.dto.FolderDto;

/**
 * Layers Administration Interface 
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
public interface FoldersAdminService extends AbstractService{
	
	/**
	 * 
	 * 
	 * 
	 */
	public static final Long DEFAULT_FOLDER_TYPE = new Long(1);
	
	/**
	 * Get root folder for a user
	 * 
	 * @return root folder
	 */
	public FolderDto getRootFolder(Long idUser);
	
	/**
	 * Get root folder for a group
	 * 
	 * @return root folder
	 */
	public FolderDto getRootGroupFolder(Long idGroup);
	
	/**
	 * Saves a folder
	 * 
	 * @return saved folder
	 */
	public FolderDto saveFolder(FolderDto folder);
	
	/**
	 * Delete all user folders and layers 
	 * 
	 * @param userId user's <code>id</code>
	 */
	public void deleteUserContext(Long userId);
	
	/**
	 * Copy user folders and layers 
	 * from an user <code>origin</code> to a user <code>target</code>
	 * 
	 * @param originUserId origin user's <code>id</code>
	 * @param targetUserId target user's <code>id</code>
	 * @param merge indicate if target user folders must be maintained
	 * 
	 * @return folder copied
	 */
	public FolderDto copyUserContext(Long originUserId, Long targetUserId, boolean merge);
	
	/**
	 * Copy folder to an user
	 * 
	 * @param targetUserId
	 * @param originFolder
	 * 
	 * @return copied
	 */
	public FolderDto copyFolder(Long targetUserId, FolderDto originFolder);
	
	/**
	 * Get all channel folders filterd
	 * 
	 * @param inZone indicates if obtain channel folders with a zone. If this parameter is null only obtain not zoned channels
	 * @param idZone filter by zone. Obtain only channels of the zone identified by <code>idZone</code>
	 * 
	 * @return folder list
	 */
	public List<FolderDto> getChannelFolders(Boolean inZone, Long idZone);
	
	/**
	 * Get all channel folders filterd
	 * 
	 * @param inZone indicates if obtain channel folders with a zone. If this parameter is null only obtain not zoned channels
	 * @param idZone filter by zone. Obtain only channels of the zone identified by <code>idZone</code>
	 * @param isEnabled
	 * 
	 * @return folder list
	 */
	public List<FolderDto> getChannelFolders(Boolean inZone, Long idZone, Boolean isEnabled);

    /**
     * Get a folders list by zones. If zoneId is NULL returns all the
     * folder not associated to any zone.
     *
     * @params <code>zoneId</code>
     *
     * @return Entities list associated with the zoneId or null if not found
     */
    public List<FolderDto> findByZone(Long zoneId);

    /**
     * Get a folders list by zones. If zoneId is NULL returns all the
     * folder not associated to any zone.
     *
     * @param <code>zoneId</code>
     * @param <code>isEnabled</code>
     *
     * @return Entities list associated with the zoneId or null if not found
     */
    public List<FolderDto> findByZone(Long zoneId, Boolean isEnabled);

    /**
     * Get a folders list by zones with an specific parent. If zoneId is NULL
     * returns all the folder not associated to any zone. If parentId is NULL
     * the returned folders are root folders.
     *
     * @param <code>zoneId</code>
     * @param <code>parentId</code>
     *
     * @return Entities list associated with the zoneId or null if not found
     */
    public List<FolderDto> findByZone(Long zoneId, Long parentId);

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
    public List<FolderDto> findByZone(Long zoneId, Long parentId, Boolean isEnabled);
    
}
