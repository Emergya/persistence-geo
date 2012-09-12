/*
 * FolderEntity.java
 * 
 * Copyright (C) 2011
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
package com.emergya.persistenceGeo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;

/**
 * Entidad de carpeta
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Entity
@Table(name = "folders")
public class FolderEntity extends AbstractFolderEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7230829079248633279L;

	public FolderEntity(){
		
	}
	
	public FolderEntity(String folderName){
		name = folderName;
	}
	
	@Column(name = "name_folder")
	public String getName() {
		return name;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	@Column(name = "canal")
	public Boolean getEs_canal() {
		return es_canal;
	}

	@Column(name = "instrumento_planificacion")
	public Boolean getEs_instrumento_planificacion() {
		return es_instrumento_planificacion;
	}

	@Column(name = "createDate")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	@Column(name = "updateDate")
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	@OneToMany(targetEntity = FolderEntity.class,
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "folders_in_folder",
	joinColumns =
	@JoinColumn(name = "folder_id"),
	inverseJoinColumns =
	@JoinColumn(name = "subfolder_id"))
	public List<FolderEntity> getFolderList() {
		return folderList;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "zoneList")
	public List<ZoneEntity> getZoneList() {
		return zoneList;
	}

	@ManyToOne
    @JoinColumn(name = "id", insertable = false, updatable = false)
	public LayerEntity getLayer() {
		return (LayerEntity) layer;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}
}
