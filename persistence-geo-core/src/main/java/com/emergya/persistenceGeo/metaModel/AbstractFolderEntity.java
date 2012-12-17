/*
 * AbstractFolderEntity.java
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

/**
 * Entidad de carpeta
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
public abstract class AbstractFolderEntity extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5833974857352652727L;

	protected Long id;
	
	protected String name;
	protected Boolean enabled;
	protected Boolean isChannel;
	protected Boolean isPlain;
	protected Date createDate;
	protected Date updateDate;
	protected AbstractZoneEntity zone;
	protected AbstractUserEntity user;
	protected AbstractAuthorityEntity authority;
	protected AbstractFolderEntity parent;
	protected Integer folderOrder;

	public AbstractFolderEntity(){
		
	}
	
	public AbstractFolderEntity(String folderName){
		name = folderName;
	}

	/**
	 * @return the id
	 */
	public abstract Long getId();

	/**
	 * @return the name
	 */
	public abstract String getName();
	/**
	 * @return the enabled
	 */
	public abstract Boolean getEnabled();
	/**
	 * @return the isPlain
	 */
	public abstract Boolean getIsPlain();
	/**
	 * @return the isChannel
	 */
	public abstract Boolean getIsChannel();
	/**
	 * @return the fechaCreacion
	 */
	public abstract Date getCreateDate();
	/**
	 * @return the fechaActualizacion
	 */
	public abstract Date getUpdateDate();
	/**
	 * @return the zoneList
	 */
	public abstract AbstractZoneEntity getZone();
	/**
	 * @return the user
	 */
	public abstract AbstractUserEntity getUser();
	/**
	 * @return the authority
	 */
	public abstract AbstractAuthorityEntity getAuthority();
	/**
	 * @return the parent
	 */
	public abstract AbstractFolderEntity getParent();
	/**
	 * @return the order
	 */
	public abstract Integer getFolderOrder();
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param order the order to set
	 */
	public void setFolderOrder(Integer order) {
		this.folderOrder = order;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param isChannel the isChannel to set
	 */
	public void setIsChannel(Boolean isChannel) {
		this.isChannel = isChannel;
	}

	/**
	 * @param isPlain the isPlain to set
	 */
	public void setIsPlain(Boolean isPlain) {
		this.isPlain = isPlain;
	}

	/**
	 * @param fechaCreacion the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param zoneList the zoneList to set
	 */
	public void setZone(AbstractZoneEntity zone) {
		this.zone = zone;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(AbstractUserEntity user) {
		this.user = user;
	}

	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(AbstractAuthorityEntity authority) {
		this.authority = authority;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(AbstractFolderEntity parent) {
		this.parent = parent;
	}
}
