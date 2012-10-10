/*
 * AbstractLayerEntity.java
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
import java.util.List;

/**
 * Entidad de capa
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@SuppressWarnings("rawtypes")
public abstract class AbstractMapConfigurationEntity extends AbstractEntity {

	protected Long id;
	protected String PDFServer;
	protected String uploadServletURL;
	protected String downloadServletURL;
	protected String defaultUserLogo;
	protected String defaultWMSServer;
	protected String OpenLayersProxyHost;
	protected String defaultIdioma;
	protected String numZoomLevels;
	protected String displayProjection;
	protected String projection;
	protected String initalBbox;
	protected String maxScale;
	protected String minScale;
	protected String resolutions;
	protected String maxResolution;
	protected String version;
	protected String minResolution;
	protected String bbox;
	

	
	
	/**
	 * @return the pDFServer
	 */
	public abstract String getPDFServer() ;
	/**
	 * @return the uploadServletURL
	 */
	public abstract String getUploadServletURL() ;
	/**
	 * @return the downloadServletURL
	 */
	public abstract String getDownloadServletURL() ;
	/**
	 * @return the defaultUserLogo
	 */
	public abstract String getDefaultUserLogo() ;
	/**
	 * @return the defaultWMSServer
	 */
	public abstract String getDefaultWMSServer() ;
	/**
	 * @return the openLayersProxyHost
	 */
	public abstract String getOpenLayersProxyHost() ;
	/**
	 * @return the defaultIdioma
	 */
	public abstract String getDefaultIdioma() ;
	/**
	 * @return the numZoomLevels
	 */
	public abstract String getNumZoomLevels() ;
	/**
	 * @return the displayProjection
	 */
	public abstract String getDisplayProjection();
	/**
	 * @return the projection
	 */
	public abstract String getProjection() ;
	/**
	 * @return the initalBbox
	 */
	public abstract String getInitalBbox() ;
	/**
	 * @return the maxScale
	 */
	public abstract String getMaxScale();
	/**
	 * @return the minScale
	 */
	public abstract String getMinScale();
	/**
	 * @return the resolutions
	 */
	public abstract String getResolutions() ;
	/**
	 * @return the maxResolution
	 */
	public abstract String getMaxResolution() ;
	/**
	 * @return the version
	 */
	public abstract String getVersion() ;
	/**
	 * @return the minResolution
	 */
	public abstract String getMinResolution();
	/**
	 * @return the bbox
	 */
	public abstract String getBbox();
	
	
	/**
	 * @param id the id to set
	 */
	public void setId(Serializable id) {
		this.id = (Long) id;
	}
	/**
	 * @param pDFServer the pDFServer to set
	 */
	public void setPDFServer(String pDFServer) {
		PDFServer = pDFServer;
	}
	/**
	 * @param uploadServletURL the uploadServletURL to set
	 */
	public void setUploadServletURL(String uploadServletURL) {
		this.uploadServletURL = uploadServletURL;
	}
	/**
	 * @param downloadServletURL the downloadServletURL to set
	 */
	public void setDownloadServletURL(String downloadServletURL) {
		this.downloadServletURL = downloadServletURL;
	}
	/**
	 * @param defaultUserLogo the defaultUserLogo to set
	 */
	public void setDefaultUserLogo(String defaultUserLogo) {
		this.defaultUserLogo = defaultUserLogo;
	}
	/**
	 * @param defaultWMSServer the defaultWMSServer to set
	 */
	public void setDefaultWMSServer(String defaultWMSServer) {
		this.defaultWMSServer = defaultWMSServer;
	}
	/**
	 * @param openLayersProxyHost the openLayersProxyHost to set
	 */
	public void setOpenLayersProxyHost(String openLayersProxyHost) {
		OpenLayersProxyHost = openLayersProxyHost;
	}
	/**
	 * @param defaultIdioma the defaultIdioma to set
	 */
	public void setDefaultIdioma(String defaultIdioma) {
		this.defaultIdioma = defaultIdioma;
	}
	/**
	 * @param numZoomLevels the numZoomLevels to set
	 */
	public void setNumZoomLevels(String numZoomLevels) {
		this.numZoomLevels = numZoomLevels;
	}
	/**
	 * @param displayProjection the displayProjection to set
	 */
	public void setDisplayProjection(String displayProjection) {
		this.displayProjection = displayProjection;
	}
	/**
	 * @param projection the projection to set
	 */
	public void setProjection(String projection) {
		this.projection = projection;
	}
	/**
	 * @param initalBbox the initalBbox to set
	 */
	public void setInitalBbox(String initalBbox) {
		this.initalBbox = initalBbox;
	}
	/**
	 * @param maxScale the maxScale to set
	 */
	public void setMaxScale(String maxScale) {
		this.maxScale = maxScale;
	}
	/**
	 * @param minScale the minScale to set
	 */
	public void setMinScale(String minScale) {
		this.minScale = minScale;
	}
	/**
	 * @param resolutions the resolutions to set
	 */
	public void setResolutions(String resolutions) {
		this.resolutions = resolutions;
	}
	/**
	 * @param maxResolution the maxResolution to set
	 */
	public void setMaxResolution(String maxResolution) {
		this.maxResolution = maxResolution;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @param minResolution the minResolution to set
	 */
	public void setMinResolution(String minResolution) {
		this.minResolution = minResolution;
	}
	/**
	 * @param bbox the bbox to set
	 */
	public void setBbox(String bbox) {
		this.bbox = bbox;
	}
}