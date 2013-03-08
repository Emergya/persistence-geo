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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;

/**
 * Entidad de carpeta
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@Entity
@Table(name = "gis_folder")
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
	
	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "folderOrder")
	public Integer getFolderOrder() {
		return folderOrder;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	@Column(name = "is_channel")
	public Boolean getIsChannel() {
		return isChannel;
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
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gis_folder_seq")
    @SequenceGenerator(name="gis_folder_seq", sequenceName = "gis_folder_seq", initialValue=100)
	public Long getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY,
			cascade = CascadeType.MERGE)
	@JoinColumn(name="folder_zone_id")
	public ZoneEntity getZone() {
		return (ZoneEntity) zone;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch  = FetchType.LAZY)
    @JoinColumn(name = "folder_auth_id")
	public AuthorityEntity getAuthority() {
		return (AuthorityEntity) authority;
	}

	@ManyToOne(cascade = CascadeType.MERGE, fetch  = FetchType.LAZY)
    @JoinColumn(name = "folder_user_id")
	public UserEntity getUser() {
		return (UserEntity) user;
	}

	@ManyToOne(cascade = CascadeType.MERGE, 
			fetch  = FetchType.LAZY)
    @JoinColumn(name = "folder_parent_id")
	public FolderEntity getParent() {
		return (FolderEntity) parent;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	@ManyToOne(fetch = FetchType.LAZY,
			cascade = CascadeType.MERGE)
	@JoinColumn(name="folder_type_id")
	public FolderTypeEntity getFolderType() {
		return (FolderTypeEntity) folderType;
	}
}
