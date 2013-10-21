/*
 * AuthorityDto.java
 * 
 * Copyright (C) 2012
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
package com.emergya.persistenceGeo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Dto de grupo de usuarios
 *
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public class AuthorityDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6021644235856556432L;

    private Long id;
    private String nombre;

    private Date createDate;
    private Date updateDate;

    private List<String> usuarios;
    private String authType;
    private List<String> layerList;
    private List<String> privateLayerList;
    private String zone;
    private Long parentId;
    private String workspaceName;

    public Long getParentId() {
	return parentId;
    }

    public void setParentId(Long parentId) {
	this.parentId = parentId;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getNombre() {
	return nombre;
    }

    public void setNombre(String nombre) {
	this.nombre = nombre;
    }

    public List<String> getUsuarios() {
	return usuarios;
    }

    public void setUsuarios(List<String> usuarios) {
	this.usuarios = usuarios;
    }

    public Date getCreateDate() {
	return createDate;
    }

    public void setCreateDate(Date createDate) {
	this.createDate = createDate;
    }

    public Date getUpdateDate() {
	return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
	this.updateDate = updateDate;
    }

    public String getAuthType() {
	return authType;
    }

    public void setAuthType(String authType) {
	this.authType = authType;
    }

    public List<String> getLayerList() {
	return layerList;
    }

    public void setLayerList(List<String> layerList) {
	this.layerList = layerList;
    }

    public List<String> getPrivateLayerList() {
	return privateLayerList;
    }

    public void setPrivateLayerList(List<String> privateLayerList) {
	this.privateLayerList = privateLayerList;
    }

    public String getZone() {
	return zone;
    }

    public void setZone(String zone) {
	this.zone = zone;
    }

    public String getWorkspaceName() {
	return this.workspaceName;
    }

    public void setWorkspaceName(String workspace) {
	this.workspaceName = workspace;
    }
}
