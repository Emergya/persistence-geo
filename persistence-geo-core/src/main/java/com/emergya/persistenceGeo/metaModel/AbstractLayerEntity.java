/*
 * AbstractLayerEntity.java
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
 * Entidad de capa
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractLayerEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8436623536431960541L;

	protected Long id;
	
	protected String name;
	protected String order;
	protected String type;
	protected String server_resource;
	protected byte[] data;

	protected Boolean publicized;
	protected Boolean enabled;
	protected Boolean pertenece_a_canal;
	protected Date fechaCreacion;
	protected Date fechaActualizacion;
	
	protected AbstractUserEntity user;
	protected AbstractAuthorityEntity auth;
	protected List styleList;
	protected List folderList;
	protected List properties;

	public AbstractLayerEntity(){
		
	}
	
	public AbstractLayerEntity(String layerName){
		name = layerName;
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
	 * @return the order
	 */
	public abstract String getOrder();
	/**
	 * @return the type
	 */
	public abstract String getType();
	/**
	 * @return the server_resource
	 */
	public abstract String getServer_resource();
	/**
	 * @return the publicized
	 */
	public abstract Boolean getPublicized();
	/**
	 * @return the enabled
	 */
	public abstract Boolean getEnabled();
	/**
	 * @return the pertenece_a_canal
	 */
	public abstract Boolean getPertenece_a_canal();
	/**
	 * @return the fechaCreacion
	 */
	public abstract Date getFechaCreacion();
	/**
	 * @return the fechaActualizacion
	 */
	public abstract Date getFechaActualizacion();
	/**
	 * @return the user
	 */
	public abstract AbstractUserEntity getUser();
	/**
	 * @return the auth
	 */
	public abstract AbstractAuthorityEntity getAuth();
	/**
	 * @return the styleList
	 */
	public abstract List getStyleList();
	/**
	 * @return the folderList
	 */
	public abstract List getFolderList();
	
	/**
	 * @return the data
	 */
	public abstract byte[] getData();
	
	/**
	 * @return the properties
	 */
	public abstract List getProperties();

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
	 * @param order the order to set
	 */
	public void setOrder(String order) {
		this.order = order;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param server_resource the server_resource to set
	 */
	public void setServer_resource(String server_resource) {
		this.server_resource = server_resource;
	}

	/**
	 * @param publicized the publicized to set
	 */
	public void setPublicized(Boolean publicized) {
		this.publicized = publicized;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @param pertenece_a_canal the pertenece_a_canal to set
	 */
	public void setPertenece_a_canal(Boolean pertenece_a_canal) {
		this.pertenece_a_canal = pertenece_a_canal;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(AbstractUserEntity user) {
		this.user = user;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(AbstractAuthorityEntity auth) {
		this.auth = auth;
	}

	/**
	 * @param styleList the styleList to set
	 */
	public void setStyleList(List styleList) {
		this.styleList = styleList;
	}

	/**
	 * @param folderList the folderList to set
	 */
	public void setFolderList(List folderList) {
		this.folderList = folderList;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List properties) {
		this.properties = properties;
	}
}
