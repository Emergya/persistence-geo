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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;

/**
 * Entidad de usuario
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@Entity
@Table(name = "gis_user")
public class UserEntity extends AbstractUserEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = -6272520927189358861L;

    public UserEntity() {

    }

    public UserEntity(String name) {
        username = name;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq")
    @SequenceGenerator(name="user_seq", sequenceName = "user_seq", initialValue=100)
    public Long getId() {
        return id;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
		return password;
	}
    
	@Column(name = "nombreCompleto")
	public String getNombreCompleto() {
		return nombreCompleto;
	}

    @Column(name = "apellidos")
	public String getApellidos() {
		return apellidos;
	}

    @Column(name = "email")
	public String getEmail() {
		return email;
	}

    @Column(name = "telefono")
	public String getTelefono() {
		return telefono;
	}

    @Column(name = "admin_user")
	public Boolean getAdmin() {
		return admin;
	}

    @Column(name = "valid_user")
	public Boolean getValid() {
		return valid;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}
	
    @ManyToOne
    @JoinColumn(name = "user_authority_id")
	public AuthorityEntity getAuthority() {
		return (AuthorityEntity) authority;
	}
}