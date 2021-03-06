/*
 * AbstractFolderTypeEntity.java
 * 
 * Copyright (C) 2011
 * 
 * This file is part of Proyecto persistenceGeo
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
 * Authors:: Moisés Arcos Santiago (mailto:marcos@emergya.com)
 */
package com.emergya.persistenceGeo.metaModel;

/**
 * Entidad del tipo de carpeta
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
public abstract class AbstractFolderTypeEntity extends AbstractEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4950765436808392406L;
	
	protected Long id;
	protected String type;
	protected String title;
	protected AbstractFolderTypeEntity parent;

	public AbstractFolderTypeEntity(){
		
	}
	
	/**
	 * @return the id
	 */
	public abstract Long getId();
	
	/**
	 * @return the parent
	 */
	public abstract AbstractFolderTypeEntity getParent();
	
	/**
	 * @return the type
	 */
	public abstract String getType();
	
	/**
	 * @return the title
	 */
	public abstract String getTitle();
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(AbstractFolderTypeEntity parent) {
		this.parent = parent;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
