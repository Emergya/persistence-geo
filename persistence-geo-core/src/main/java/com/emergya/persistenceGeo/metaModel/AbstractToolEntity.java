/*
 * AbstractToolEntity.java Copyright (C) 2013. This file is part of persistenceGeo project
 * 
 * This software is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General public abstract License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General public abstract License for more
 * details.
 * 
 * You should have received a copy of the GNU General public abstract License along with
 * this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 * As a special exception, if you link this library with other files to produce
 * an executable, this library does not by itself cause the resulting executable
 * to be covered by the GNU General public abstract License. This exception does not
 * however invalidate any other reasons why the executable file might be covered
 * by the GNU General public abstract License.
 * 
 * Authors:: Alejandro Diaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.metaModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Tool entity
 * 
 * @author <a href="mailto:adiaz@emergya.com">Alejandro Diaz</a>
 *
 */
public abstract class AbstractToolEntity extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1236430723660399592L;
	
	protected Long id;
	protected String name;
	protected String ptype;
	protected String config;
	protected Date createDate;
	protected Date updateDate;

	/**
	 * @return the name
	 */
	public abstract String getName();
	/**
	 * @return the createDate
	 */
	public abstract Date getCreateDate();
	/**
	 * @return the updateDate
	 */
	public abstract Date getUpdateDate();
	/**
	 * @return the ptype
	 */
	public abstract String getPtype();
	/**
	 * @return the config
	 */
	public abstract String getConfig();

	/**
	 * @param id the id to set
	 */
	public void setId(Serializable id) {
		this.id = (Long) id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @param ptype the ptype to set
	 */
	public void setPtype(String ptype) {
		this.ptype = ptype;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(String config) {
		this.config = config;
	}


}
