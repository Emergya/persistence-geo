/*
 * StyleEntity.java
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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad de estilo
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@Entity
@Table(name = "styles")
public class StyleEntity extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8414310574032934037L;
	
	private Long id;
	
	private String name;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	
	private List<LayerEntity> layerList;
	private List<RuleEntity> ruleList;
	private List<PrivateLayerEntity> privateLayerList;

	public StyleEntity(){
		
	}
	
	public StyleEntity(String styleString){
		name = styleString;
	}

	@Column(name = "name_style")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@OneToMany(mappedBy = "style")
	public List<LayerEntity> getLayerList() {
		return layerList;
	}

	public void setLayerList(List<LayerEntity> layerList) {
		this.layerList = layerList;
	}

	@OneToMany(mappedBy = "style")
	public List<RuleEntity> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<RuleEntity> ruleList) {
		this.ruleList = ruleList;
	}

	@OneToMany(mappedBy = "style")
	public List<PrivateLayerEntity> getPrivateLayerList() {
		return privateLayerList;
	}

	public void setPrivateLayerList(List<PrivateLayerEntity> privateLayerList) {
		this.privateLayerList = privateLayerList;
	}

}
