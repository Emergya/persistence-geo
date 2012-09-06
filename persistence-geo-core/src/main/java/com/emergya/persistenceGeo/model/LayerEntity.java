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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad de capa
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@Entity
@Table(name = "layers")
public class LayerEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2844502275010469666L;

	private Long id;
	
	private String name;
	private String order;
	private String type;
	private String server_resource;
	private Boolean publicized;
	private Boolean enabled;
	private Boolean pertenece_a_canal;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	
	private UserEntity user;
	private AuthorityEntity auth;
	private StyleEntity style;
	private FolderEntity folder;
	
	public LayerEntity(){
		
	}
	
	public LayerEntity(String layerName){
		name = layerName;
	}

	@Column(name = "name_layer")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "order_layer")
	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Column(name = "type_layer")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "server_resource")
	public String getServer_resource() {
		return server_resource;
	}

	public void setServer_resource(String server_resource) {
		this.server_resource = server_resource;
	}

	@Column(name = "published")
	public Boolean getPublicized() {
		return publicized;
	}

	public void setPublicized(Boolean publicized) {
		this.publicized = publicized;
	}

	@Column(name = "enabled")
	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "pertenece_a_canal")
	public Boolean getPertenece_a_canal() {
		return pertenece_a_canal;
	}

	public void setPertenece_a_canal(Boolean pertenece_a_canal) {
		this.pertenece_a_canal = pertenece_a_canal;
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
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = (Long) id;
	}
	
	@ManyToOne
    @JoinColumn(name = "user_id")
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	@ManyToOne
    @JoinColumn(name = "auth_id", insertable = false, updatable = false)
	public AuthorityEntity getAuth() {
		return auth;
	}

	public void setAuth(AuthorityEntity auth) {
		this.auth = auth;
	}

	@ManyToOne
    @JoinColumn(name = "style_id")
	public StyleEntity getStyle() {
		return style;
	}

	public void setStyle(StyleEntity style) {
		this.style = style;
	}

	@ManyToOne
    @JoinColumn(name = "folder_id")
	public FolderEntity getFolder() {
		return folder;
	}

	public void setFolder(FolderEntity folder) {
		this.folder = folder;
	}

}
