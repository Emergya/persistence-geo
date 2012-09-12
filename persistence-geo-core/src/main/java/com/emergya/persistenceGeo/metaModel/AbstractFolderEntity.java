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
import java.util.List;

/**
 * Entidad de carpeta
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractFolderEntity extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5833974857352652727L;

	protected Long id;
	
	protected String name;
	protected Boolean enabled;
	protected Boolean es_canal;
	protected Boolean es_instrumento_planificacion;
	protected Date fechaCreacion;
	protected Date fechaActualizacion;
	
	protected List folderList;
	protected List zoneList;
	protected AbstractLayerEntity layer;

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
	 * @return the es_canal
	 */
	public abstract Boolean getEs_canal();
	/**
	 * @return the es_instrumento_planificacion
	 */
	public abstract Boolean getEs_instrumento_planificacion();
	/**
	 * @return the fechaCreacion
	 */
	public abstract Date getFechaCreacion();
	/**
	 * @return the fechaActualizacion
	 */
	public abstract Date getFechaActualizacion();
	/**
	 * @return the folderList
	 */
	public abstract List getFolderList();
	/**
	 * @return the zoneList
	 */
	public abstract List getZoneList();
	/**
	 * @return the layer
	 */
	public abstract AbstractLayerEntity getLayer();
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
	public void setEs_canal(Boolean es_canal) {
		this.es_canal = es_canal;
	}

	/**
	 * @param es_instrumento_planificacion the es_instrumento_planificacion to set
	 */
	public void setEs_instrumento_planificacion(Boolean es_instrumento_planificacion) {
		this.es_instrumento_planificacion = es_instrumento_planificacion;
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
	 * @param folderList the folderList to set
	 */
	public void setFolderList(List folderList) {
		this.folderList = folderList;
	}

	/**
	 * @param zoneList the zoneList to set
	 */
	public void setZoneList(List zoneList) {
		this.zoneList = zoneList;
	}

	/**
	 * @param layer the layer to set
	 */
	public void setLayer(AbstractLayerEntity layer) {
		this.layer = layer;
	}
}
