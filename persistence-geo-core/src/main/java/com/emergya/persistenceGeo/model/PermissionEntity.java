/*
 * PermissionEntity.java
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

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.emergya.persistenceGeo.metaModel.AbstractPermissionEntity;

/**
 * Entidad de permisos
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Entity
@Table(name = "gis_permission")
public class PermissionEntity extends AbstractPermissionEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8185264482816302475L;
	
	public PermissionEntity(){
		
	}
	
	public PermissionEntity(String permissionName){
		name = permissionName;
	}

	@Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "gis_permission_seq")
    @SequenceGenerator(name="gis_permission_seq", sequenceName = "gis_permission_seq", initialValue=100)
	public Long getId() {
		return id;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	@Column(name = "filter")
	public String getFilter() {
		return filter;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "ptype")
	public String getPtype() {
		return ptype;
	}

	@Column(name = "config")
	public String getConfig() {
		return config;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissionList")
	public List<AuthorityTypeEntity> getAuthTypeList() {
		return authTypeList;
	}

}
