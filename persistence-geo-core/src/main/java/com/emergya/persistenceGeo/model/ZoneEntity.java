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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.emergya.persistenceGeo.metaModel.AbstractZoneEntity;

/**
 * Entidad de ámbito territorial
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Entity
@Table(name = "gis_zone")
public class ZoneEntity extends AbstractZoneEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2343822232321013323L;

	public ZoneEntity(){
		
	}

	public ZoneEntity(String zoneName){
		name = zoneName;
	}

	@Column(name = "code")
	public String getCode() {
		return code;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	@Column(name = "extension")
	public String getExtension() {
		return extension;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}
 
	@Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gis_zone_seq")
    @SequenceGenerator(name="gis_zone_seq", sequenceName = "gis_zone_seq", initialValue=100)
	public Long getId() {
		return id;
	}
	
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	@OneToMany(targetEntity = ZoneEntity.class,
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "gis_zone_in_zone",
	joinColumns =
	@JoinColumn(name = "zone_id"),
	inverseJoinColumns =
	@JoinColumn(name = "subzone_id"))
	public List<ZoneEntity> getZoneList() {
		return zoneList;
	}
	
	@ManyToMany(targetEntity = FolderEntity.class,
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "gis_folder_in_zone",
	joinColumns =
	@JoinColumn(name = "folder_id"),
	inverseJoinColumns =
	@JoinColumn(name = "zone_id"))
	public List<FolderEntity> getFolderList() {
		return folderList;
	}

	@OneToMany(mappedBy = "zone")
	public List<AuthorityEntity> getAuthList() {
		return authList;
	}

}
