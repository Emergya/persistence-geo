/*
 * TreeLayerDto.java
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

import java.util.Date;

/**
 * Layer DTO for show in folder tree
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public class TreeLayerDto extends LayerDto implements Treeable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7848354150705096968L;
	
	/**
	 * Default is true
	 */
	protected boolean leaf = true;
	
	public TreeLayerDto(LayerDto origin){

		super();
		 
		this.id = origin.id != null ? new Long(origin.id) : null; // clone id
		this.name = origin.name != null ? new String(origin.name) : null;
		this.enabled = origin.enabled != null ? new Boolean(origin.enabled)
				: null;
		this.createDate = origin.createDate != null ? (Date) origin.createDate
				.clone() : null;
		this.updateDate = origin.updateDate != null ? (Date) origin.updateDate
				.clone() : null;
		this.order = origin.order != null ? new String(origin.order) : null;
		this.type = origin.type != null ? new String(origin.type) : null;
		this.server_resource = origin.server_resource != null ? new String(
				origin.server_resource) : null;
		this.user = origin.user != null ? new String(origin.user) : null;
		this.published = origin.published ? new Boolean(origin.published)
				: null;
		this.enabled = origin.enabled ? new Boolean(enabled) : null;
		this.pertenece_a_canal = origin.pertenece_a_canal ? new Boolean(
				pertenece_a_canal) : null;
		this.folderId = origin.folderId != null ? new Long(origin.folderId)
				: null;
		this.authId = origin.authId != null ? new Long(origin.authId) : null;

		this.data = origin.data; // clone data?
		this.styles = origin.styles; // clone styles?
		this.properties = origin.properties; // clone properties?
	}

	public boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		// always is leaf
		this.leaf = true;
	}
	
	public String getText() {
		return getName();
	}

}
