/*
 * AuthorityEntity.java
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
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.emergya.persistenceGeo.metaModel.AbstractAuthorityEntity;
import com.emergya.persistenceGeo.metaModel.AbstractAuthorityTypeEntity;
import com.emergya.persistenceGeo.metaModel.AbstractZoneEntity;

/**
 * Entity that represents the users group
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 * 
 */
@Entity
@Table(name = "authorities")
public class AuthorityEntity extends AbstractAuthorityEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2805722865035814185L;

	public AuthorityEntity() {
	}

	public AuthorityEntity(String authString) {
		authority = authString;
	}

	@Column(name = "authority", nullable = false)
	public String getAuthority() {
		return authority;
	}

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	@OneToMany(mappedBy = "authority")
	public Set<UserEntity> getPeople() {
		return (Set<UserEntity>) people;
	}

	@Column(name = "createDate")
	public Date getCreateDate() {
		return createDate;
	}

	@Column(name = "updateDate")
	public Date getUpdateDate() {
		return updateDate;
	}

	@ManyToOne
	@JoinColumn(name = "auth_type_id")
	public AuthorityTypeEntity getAuthType() {
		return (AuthorityTypeEntity) authType;
	}

	@OneToMany(mappedBy = "auth")
	public List<LayerEntity> getLayerList() {
		return (List<LayerEntity>) layerList;
	}

	@ManyToOne
	@JoinColumn(name = "id", insertable = false, updatable = false)
	public ZoneEntity getZone() {
		return (ZoneEntity) zone;
	}

}