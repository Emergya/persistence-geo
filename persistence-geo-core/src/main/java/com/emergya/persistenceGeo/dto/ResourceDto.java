/*
 * ResourceDto.java
 * 
 * Copyright (C) 2013
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

import java.io.File;
import java.io.Serializable;

/**
 * Resource Transfer Object 
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public class ResourceDto extends AbstractDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -400854637374906062L;
	
	protected Long id;
	protected String name;
	protected Long size;
	protected String type;
	protected Long accessId;
	protected File data;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getAccessId() {
		return accessId;
	}
	public void setAccessId(Long accessId) {
		this.accessId = accessId;
	}
	public File getData() {
		return data;
	}
	public void setData(File data) {
		this.data = data;
	}
	

}