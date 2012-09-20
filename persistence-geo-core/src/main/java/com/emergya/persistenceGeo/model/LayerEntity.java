/*
 * LayerEntity.java
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;

/**
 * Entidad de capa
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Entity
@Table(name = "layer")
public class LayerEntity extends AbstractLayerEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2844502275010469666L;
	
	public LayerEntity(){
		
	}
	
	public LayerEntity(String layerName){
		name = layerName;
	}

	@Column(name = "name_layer")
	public String getName() {
		return name;
	}
	
	@Column(name = "order_layer")
	public String getOrder() {
		return order;
	}

	@ManyToOne
    @JoinColumn(name = "layer_typ_id")
	public LayerTypeEntity getType() {
		return (LayerTypeEntity) type;
	}

	@Column(name = "server_resource")
	public String getServer_resource() {
		return server_resource;
	}

	@Column(name = "publicized")
	public Boolean getPublicized() {
		return publicized;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	@Column(name = "pertenece_a_canal")
	public Boolean getPertenece_a_canal() {
		return pertenece_a_canal;
	}

	@Column(name = "fechaCreacion")
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	@Column(name = "fechaActualizacion")
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	public UserEntity getUser() {
		return (UserEntity) user;
	}

	@ManyToOne
    @JoinColumn(name = "auth_id")
	public AuthorityEntity getAuth() {
		return (AuthorityEntity) auth;
	}

	@ManyToMany(targetEntity = StyleEntity.class,
	cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "layer_with_style",
	joinColumns =
	@JoinColumn(name = "style_id", insertable = false, updatable = false),
	inverseJoinColumns =
	@JoinColumn(name = "layer_id"))
	public List<StyleEntity> getStyleList() {
		return styleList;
	}

	@OneToMany(targetEntity = FolderEntity.class)
	public List<FolderEntity> getFolderList() {
		return folderList;
	}

	@Column(name = "data", nullable=true)
	public byte[] getData() {
		return data;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}
	
	@OneToMany(targetEntity=LayerPropertyEntity.class, orphanRemoval = true,
			cascade = {CascadeType.ALL})
	public List<LayerPropertyEntity> getProperties() {
		return this.properties;
	}

}
