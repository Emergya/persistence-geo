/*
 * UserEntity.java
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
 * Entidad de usuario
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@Entity
@Table(name = "usuario")
public class UserEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6272520927189358861L;
	
	private Long user_id;
    private String username;
    
    private String password;
    private String nombreCompleto;
	private String apellidos;
    private String email;
    private String telefono;
    private Boolean admin;
    private Boolean valid;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    
    private AuthorityEntity authority;
    private LayerEntity layer;
    private PrivateLayerEntity privateLayer;

    public UserEntity() {

    }

    public UserEntity(String name) {
        username = name;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
	@Column(name = "nombreCompleto")
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

    @Column(name = "apellidos")
	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

    @Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    @Column(name = "telefono")
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

    @Column(name = "admin")
	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

    @Column(name = "valid")
	public Boolean getValid() {
		return valid;
	}

	public void setValid(Boolean valid) {
		this.valid = valid;
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
	
    @ManyToOne
    @JoinColumn(name = "user_id")
	public AuthorityEntity getAuthority() {
		return authority;
	}

	public void setAuthority(AuthorityEntity authority) {
		this.authority = authority;
	}

	@ManyToOne
    @JoinColumn(name = "user_id")
	public LayerEntity getLayer() {
		return layer;
	}

	public void setLayer(LayerEntity layer) {
		this.layer = layer;
	}

	@ManyToOne
    @JoinColumn(name = "user_id")
	public PrivateLayerEntity getPrivateLayer() {
		return privateLayer;
	}

	public void setPrivateLayer(PrivateLayerEntity privateLayer) {
		this.privateLayer = privateLayer;
	}
}