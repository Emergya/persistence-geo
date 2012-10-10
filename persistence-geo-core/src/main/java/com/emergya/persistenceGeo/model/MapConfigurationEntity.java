/*
 * LayerEntity.java
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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;
import com.emergya.persistenceGeo.metaModel.AbstractMapConfigurationEntity;

/**
 * Entidad de capa
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("unchecked")
@Entity
@Table(name = "map_configuration")
public class MapConfigurationEntity extends AbstractMapConfigurationEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2844502275010469666L;
	
	public MapConfigurationEntity(){
		
	}
	
	

	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO,generator = "mapConfiguration_seq")
	@SequenceGenerator(name ="mapConfiguration_seq",sequenceName = "mapConfiguration_seq", initialValue = 0 , allocationSize=200)
	public Long getId() {
		return id;
	}
	
	
	

	@Override
	public void setId(Serializable id) {
		this.id = (Long) id;
		
	}


	@Column (name="bbox")
	public String getBbox() {
		return bbox;
	}


	@Column(name = "PDFServer")
	public String getPDFServer() {
		return PDFServer;
	}


	@Column(name="uploadServletURL")
	public String getUploadServletURL() {
		return uploadServletURL;
	}


	
	@Column (name = "downloadServletURL")
	public String getDownloadServletURL() {
		return downloadServletURL;
	}


	@Column(name="defaultUserLogo")
	public String getDefaultUserLogo() {
		return defaultUserLogo;
	}


	@Column (name="defaultWMSServer")
	public String getDefaultWMSServer() {
		return defaultWMSServer;
	}


	@Column (name="openLayersProxyHost")
	public String getOpenLayersProxyHost() {
		return OpenLayersProxyHost;
	}


	@Column (name = "defaultIdioma")
	public String getDefaultIdioma() {
		return defaultIdioma;
	}


	@Column (name = "numZoomLevels")
	public String getNumZoomLevels() {
		return numZoomLevels;
	}


	@Column(name="displayProjection")
	public String getDisplayProjection() {
		return displayProjection;
	}


	@Column(name="projection")
	public String getProjection() {
		return projection;
	}


	@Column(name="initialBbox")
	public String getInitalBbox() {
		return initalBbox;
	}


	@Column(name="maxScale")
	public String getMaxScale() {
		return maxScale;
	}


	@Column (name="minScale")
	public String getMinScale() {
		return minScale;
	}

	@Column (name = "resolutions")
	public String getResolutions(){
		return resolutions;
	}

	@Column (name="maxResolution")
	public String getMaxResolution() {
		return maxResolution;
	}


	@Column (name="version")
	public String getVersion() {
		return version;
	}


	@Column (name="minResolution")
	public String getMinResolution() {
		return minResolution;
	}


	

	
}
