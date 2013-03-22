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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;

/**
 * Entidad de capa
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Entity
@Table(name = "gis_layer")
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

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layer_layer_type_id")
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

	@Column(name = "is_channel")
	public Boolean getIsChannel() {
		return isChannel;
	}

	@Column(name = "creat_date")
	public Date getCreateDate() {
		return createDate;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "gis_layer_seq")
    @SequenceGenerator(name="gis_layer_seq", sequenceName = "gis_layer_seq", initialValue=20, allocationSize=200)
	public Long getId() {
		return id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layer_user_id")
	public UserEntity getUser() {
		return (UserEntity) user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "layer_auth_id")
	public AuthorityEntity getAuth() {
		return (AuthorityEntity) auth;
	}
	
	@OneToMany(targetEntity=StyleEntity.class, 
			cascade = {CascadeType.ALL},
			fetch = FetchType.LAZY)
	public List<StyleEntity> getStyleList() {
		return this.styleList;
	}

	@ManyToOne(fetch = FetchType.LAZY, 
			cascade = CascadeType.MERGE)
	@JoinColumn(name="layer_folder_id")
	public FolderEntity getFolder() {
		return (FolderEntity) folder;
	}

	@Column(name = "data", nullable=true)
	@Type(type="org.hibernate.type.PrimitiveByteArrayBlobType") //Needed for oracle/postgresql compatibility
	@Lob //Needed for oracle/postgresql compatibility
	public byte[] getData() {
		return data;
	}

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
	}
	
	@OneToMany(targetEntity=LayerPropertyEntity.class, orphanRemoval = true,
			cascade = {CascadeType.ALL},
			fetch = FetchType.LAZY)
	public List<LayerPropertyEntity> getProperties() {
		return this.properties;
	}

	@Column(name="layer_title")
	public String getLayerTitle() {
		return layerTitle;
	}
}
