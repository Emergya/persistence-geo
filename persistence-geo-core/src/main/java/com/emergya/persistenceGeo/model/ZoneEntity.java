/*
 * ZoneEntity.java
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad de ámbito territorial
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 * 
 */
@Entity
@Table(name = "zones")
public class ZoneEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7702334870312919540L;

	private Long id;

	private String code;
	private String name;
	private String type;
	private String extension;
	private Date fechaCreacion;
	private Date fechaActualizacion;

	private ZoneEntity zone;
	private List<FolderEntity> folderList;
	private List<AuthorityEntity> authList;

	public ZoneEntity() {

	}

	public ZoneEntity(String zoneName) {
		name = zoneName;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "name_zone")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type_zone")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "extension")
	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	@Column(name = "fechaCreacion")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Column(name = "fechaActualizacion")
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	@Id
	@Column(name = "zone_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	@Column(name = "zone_parent")
	public ZoneEntity getZone() {
		return zone;
	}

	public void setZone(ZoneEntity zone) {
		this.zone = zone;
	}

	@ManyToMany(targetEntity = FolderEntity.class, cascade = {
			CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "folder_in_zone", joinColumns = @JoinColumn(name = "folder_id"), inverseJoinColumns = @JoinColumn(name = "zone_id"))
	public List<FolderEntity> getFolderList() {
		return folderList;
	}

	public void setFolderList(List<FolderEntity> folderList) {
		this.folderList = folderList;
	}

	@OneToMany(mappedBy = "zone")
	public List<AuthorityEntity> getAuthList() {
		return authList;
	}

	public void setAuthList(List<AuthorityEntity> authList) {
		this.authList = authList;
	}

}
