/*
 * ToolPermissionService.java
 * 
 * Copyright (C) 2013
 * 
 * This file is part of persistenceGeo project
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
package com.emergya.persistenceGeo.service;

import java.util.List;

import com.emergya.persistenceGeo.dto.ToolPermissionDto;

/**
 * CRUD service for tool permissions
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public interface ToolPermissionService extends AbstractService{
	
	/**
	 * Authority type id to store citizen permissions
	 */
	public static final Long CITIZEN_AUTHORITY_TYPE_ID = new Long(4);
	
	/**
	 * Authority type id to store admin permissions
	 */
	public static final Long ADMIN_AUTHORITY_TYPE_ID = new Long(5);

	/**
	 * Obtain permissions by authority
	 * 
	 * @param authorityId
	 * 
	 * @return permissions for the authority
	 */
	public List<ToolPermissionDto> getPermissionsByAuthority(Long authorityId);
	
	/**
	 * Permissions for an user
	 * 
	 * @param userId
	 * 
	 * @return permissions for an user
	 */
	public List<ToolPermissionDto> getPermissionsByUser(Long userId);
	
}
