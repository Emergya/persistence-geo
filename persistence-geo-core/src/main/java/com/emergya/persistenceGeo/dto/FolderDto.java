/*
 * FolderDto.java
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
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.emergya.persistenceGeo.dto.AbstractDto;

/**
 * Folder DTO
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public class FolderDto extends AbstractDto implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2055062929230056891L;
	
	protected Long id;
	protected String name;
	protected Boolean enabled;
	protected Boolean isChannel;
	protected Boolean isPlain;
	protected Date createDate;
	protected Date updateDate;
	protected Long idParent;
	protected Long idAuth;
	protected Long idUser;
	protected Integer order; 
	
	protected List<FolderDto> folderList;
    protected Long zoneId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return enabled;
	}
	/**
	 * @return the es_canal
	 */
	public Boolean getIsChannel() {
		return isChannel;
	}
	/**
	 * @return the es_instrumento_planificacion
	 */
	public Boolean getIsPlain() {
		return isPlain;
	}
	/**
	 * @return the fechaCreacion
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @return the folderList
	 */
	public List<FolderDto> getFolderList() {
		return folderList;
	}
	/**
	 * @return the zoneId
	 */
	public Long getZoneId() {
		return zoneId;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @param es_canal the es_canal to set
	 */
	public void setIsChannel(Boolean es_canal) {
		this.isChannel = es_canal;
	}
	/**
	 * @param es_instrumento_planificacion the es_instrumento_planificacion to set
	 */
	public void setIsPlain(Boolean es_instrumento_planificacion) {
		this.isPlain = es_instrumento_planificacion;
	}
	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setCreateDate(Date fechaCreacion) {
		this.createDate = fechaCreacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setUpdateDate(Date fechaActualizacion) {
		this.updateDate = fechaActualizacion;
	}
	/**
	 * @param folderList the folderList to set
	 */
	public void setFolderList(List<FolderDto> folderList) {
		this.folderList = folderList;
	}
	/**
	 * @param zoneId the zoneId to set
	 */
	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}
	/**
	 * @return the idParent
	 */
	public Long getIdParent() {
		return idParent;
	}
	/**
	 * @param idParent the idParent to set
	 */
	public void setIdParent(Long idParent) {
		this.idParent = idParent;
	}
	/**
	 * @return the idAuth
	 */
	public Long getIdAuth() {
		return idAuth;
	}
	/**
	 * @return the idUser
	 */
	public Long getIdUser() {
		return idUser;
	}
	/**
	 * @param idAuth the idAuth to set
	 */
	public void setIdAuth(Long idAuth) {
		this.idAuth = idAuth;
	}
	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}
	
	/**
	 * @return the order
	 */
	public Integer getOrder() {
		return order;
	}
	/**
	 * @param order the order to set
	 */
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	/** 
	 * Clone folder (id is always nulled)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		FolderDto result = (FolderDto) super.clone();
		
		result.id = null; // not clone id 
		//result.id = this.id != null ? new Long(this.id) : null; // clone id
		result.name = this.name != null ? new String(this.name) : null;
		result.enabled = this.enabled != null ? new Boolean(this.enabled)
				: null;
		result.isChannel = this.isChannel != null ? new Boolean(this.isChannel)
				: null;
		result.isPlain = this.isChannel != null ? new Boolean(this.isChannel)
				: null;
		result.createDate = this.createDate != null ? (Date) this.createDate
				.clone() : null;
		result.updateDate = this.updateDate != null ? (Date) this.updateDate
				.clone() : null;
		result.idParent = this.idParent != null ? new Long(this.idParent)
				: null;
		result.idAuth = this.idAuth != null ? new Long(this.idAuth) : null;
		result.idUser = this.idUser != null ? new Long(this.idUser) : null;
		result.order = this.order != null ? new Integer(this.order) : null;
		
		// clone folder list
		if(this.folderList != null){
			result.folderList = new LinkedList<FolderDto>();
			for(FolderDto child: this.folderList){
				result.folderList.add((FolderDto) child.clone());
			}
		}

		// clone zone
		result.zoneId = this.zoneId != null ? new Long(this.zoneId) : null;

		return result;
	}


}
