/*
 * AuthorityTypeEntity.java
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

import com.emergya.persistenceGeo.metaModel.AbstractAuthorityTypeEntity;

/**
 * Entity that represents the types users group
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Entity
@Table(name = "authority_type")
public class AuthorityTypeEntity extends AbstractAuthorityTypeEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2090474619237255968L;

	public AuthorityTypeEntity(){
		
	}
	
	public AuthorityTypeEntity(String auth_type_str){
		name = auth_type_str;
	}

	@Column(name = "name_auth_type")
	public String getName() {
		return name;
	}

	@Column(name = "createDate")
	public Date getCreateDate() {
		return createDate;
	}

    @Column(name = "updateDate")
	public Date getUpdateDate() {
		return updateDate;
	}

	@Id
    @Column(name = "auth_type_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return auth_type_id;
	}
	
	@OneToMany(mappedBy = "authType")
	public List<AuthorityEntity> getAuthList() {
		return authList;
	}

	@ManyToMany(targetEntity = PermissionEntity.class,
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "permission_by_authType",
	joinColumns =
	@JoinColumn(name = "auth_type_id"),
	inverseJoinColumns =
	@JoinColumn(name = "permission_id"))
	public List<PermissionEntity> getPermissionList() {
		return permissionList;
	}

}
