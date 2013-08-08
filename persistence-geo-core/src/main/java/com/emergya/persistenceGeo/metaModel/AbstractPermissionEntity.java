/*
 * AbstractPermissionEntity.java
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

import java.util.Date;
import java.util.List;

/**
 * Entidad de permisos
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractPermissionEntity extends AbstractToolEntity {

	/**
	 * 
	 */
	protected static final long serialVersionUID = 8185264482816302475L;
	
	protected Date createDate;
	protected Date updateDate;
	
	protected String filter;
	
	protected List authTypeList;

	public AbstractPermissionEntity(){
		
	}
	
	public AbstractPermissionEntity(String permissionName){
		name = permissionName;
	}
	
	/**
	 * @return the authTypeList
	 */
	public abstract List getAuthTypeList();

	/**
	 * @return the filter
	 */
	public abstract String getFilter();

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @param authTypeList the authTypeList to set
	 */
	public void setAuthTypeList(List authTypeList) {
		this.authTypeList = authTypeList;
	}

}
