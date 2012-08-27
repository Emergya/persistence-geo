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
import javax.persistence.Table;

/**
 * Entidad de grupo de usuarios
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@Entity
@Table(name = "institucion")
public class AuthorityEntity extends AbstractEntity{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6293606419664405344L;
	
	private Long id;
    private String authority;
    private List<UserEntity> people;
    private Date fechaCreacion;
    private Date fechaActualizacion;

    public AuthorityEntity() {
    }

    public AuthorityEntity(String authString) {
        authority = authString;
    }

    @Column(name = "authority", nullable = false)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
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

    @ManyToMany(targetEntity = UserEntity.class,
    cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "usuarios_por_institucion",
    joinColumns =
    @JoinColumn(name = "intitucion_id"),
    inverseJoinColumns =
    @JoinColumn(name = "usuario_id"))
    public List<UserEntity> getPeople() {
        return people;
    }

    public void setPeople(List<UserEntity> people) {
        this.people = people;
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

}