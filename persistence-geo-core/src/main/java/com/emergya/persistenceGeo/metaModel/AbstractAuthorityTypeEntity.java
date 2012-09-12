/*
 * AuthorityTypeEntity.java
 * 
 * Copyright (C) 2011
 * 
 * This file is part of Proyecto persistenceGeo
 * 
 * This software is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General public abstract License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General public abstract License for more
 * details.
 * 
 * You should have received a copy of the GNU General public abstract License along with
 * this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 * As a special exception, if you link this library with other files to produce
 * an executable, this library does not by itself cause the resulting executable
 * to be covered by the GNU General public abstract License. This exception does not
 * however invalidate any other reasons why the executable file might be covered
 * by the GNU General public abstract License.
 * 
 * Authors:: Moisés Arcos Santiago (mailto:marcos@emergya.com)
 */
package com.emergya.persistenceGeo.metaModel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Entity that represents the types users group
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
public abstract class AbstractAuthorityTypeEntity extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 619189479918775474L;

	protected Long auth_type_id;
	
	protected String name;
	protected Date createDate;
	protected Date updateDate;
	
	protected List authList;
	protected List permissionList;
	/**
	 * @return the name
	 */
	public abstract String getName();
	/**
	 * @return the createDate
	 */
	public abstract Date getCreateDate();
	/**
	 * @return the updateDate
	 */
	public abstract Date getUpdateDate();
	/**
	 * @return the authList
	 */
	public abstract List getAuthList();
	/**
	 * @return the permissionList
	 */
	public abstract List getPermissionList();
	
	/**
	 * @param auth_type_id the auth_type_id to set
	 */
	public void setId(Serializable auth_type_id) {
		this.auth_type_id = (Long) auth_type_id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @param authList the authList to set
	 */
	public void setAuthList(List authList) {
		this.authList = authList;
	}
	/**
	 * @param permissionList the permissionList to set
	 */
	public void setPermissionList(List permissionList) {
		this.permissionList = permissionList;
	}
	
}
