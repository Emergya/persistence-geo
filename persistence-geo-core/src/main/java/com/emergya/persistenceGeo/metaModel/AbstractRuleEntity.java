/*
 * AbstractRuleEntity.java
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

import java.io.Serializable;
import java.util.Date;

/**
 * Entidad de regla
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
public abstract class AbstractRuleEntity extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6186512869484565458L;

	protected Long rule_id;
	
	protected String symbolizer;
	protected String filter;
	protected Date fechaCreacion;
	protected Date fechaActualizacion;
	
	protected AbstractStyleEntity style;

	public AbstractRuleEntity(){
		
	}

	/**
	 * @return the symbolizer
	 */
	public abstract String getSymbolizer();

	/**
	 * @return the filter
	 */
	public abstract String getFilter();

	/**
	 * @return the fechaCreacion
	 */
	public abstract Date getFechaCreacion();

	/**
	 * @return the fechaActualizacion
	 */
	public abstract Date getFechaActualizacion();

	/**
	 * @return the style
	 */
	public abstract AbstractStyleEntity getStyle();

	/**
	 * @param rule_id the rule_id to set
	 */
	public void setId(Serializable rule_id) {
		this.rule_id = (Long) rule_id;
	}

	/**
	 * @param symbolizer the symbolizer to set
	 */
	public void setSymbolizer(String symbolizer) {
		this.symbolizer = symbolizer;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(AbstractStyleEntity style) {
		this.style = style;
	}
}
