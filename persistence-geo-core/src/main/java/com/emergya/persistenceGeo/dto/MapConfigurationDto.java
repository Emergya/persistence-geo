/*
 * LayerDto.java
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

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Layer Data Transfer Object 
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
public class MapConfigurationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -488299965208355171L;
	
	private Long id;
	
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the pDFServer
	 */
	public String getPDFServer() {
		return PDFServer;
	}
	/**
	 * @param pDFServer the pDFServer to set
	 */
	public void setPDFServer(String pDFServer) {
		PDFServer = pDFServer;
	}
	/**
	 * @return the uploadServletURL
	 */
	public String getUploadServletURL() {
		return uploadServletURL;
	}
	/**
	 * @param uploadServletURL the uploadServletURL to set
	 */
	public void setUploadServletURL(String uploadServletURL) {
		this.uploadServletURL = uploadServletURL;
	}
	/**
	 * @return the downloadServletURL
	 */
	public String getDownloadServletURL() {
		return downloadServletURL;
	}
	/**
	 * @param downloadServletURL the downloadServletURL to set
	 */
	public void setDownloadServletURL(String downloadServletURL) {
		this.downloadServletURL = downloadServletURL;
	}
	/**
	 * @return the defaultUserLogo
	 */
	public String getDefaultUserLogo() {
		return defaultUserLogo;
	}
	/**
	 * @param defaultUserLogo the defaultUserLogo to set
	 */
	public void setDefaultUserLogo(String defaultUserLogo) {
		this.defaultUserLogo = defaultUserLogo;
	}
	/**
	 * @return the defaultWMSServer
	 */
	public String getDefaultWMSServer() {
		return defaultWMSServer;
	}
	/**
	 * @param defaultWMSServer the defaultWMSServer to set
	 */
	public void setDefaultWMSServer(String defaultWMSServer) {
		this.defaultWMSServer = defaultWMSServer;
	}
	/**
	 * @return the openLayersProxyHost
	 */
	public String getOpenLayersProxyHost() {
		return OpenLayersProxyHost;
	}
	/**
	 * @param openLayersProxyHost the openLayersProxyHost to set
	 */
	public void setOpenLayersProxyHost(String openLayersProxyHost) {
		OpenLayersProxyHost = openLayersProxyHost;
	}
	/**
	 * @return the defaultIdioma
	 */
	public String getDefaultIdioma() {
		return defaultIdioma;
	}
	/**
	 * @param defaultIdioma the defaultIdioma to set
	 */
	public void setDefaultIdioma(String defaultIdioma) {
		this.defaultIdioma = defaultIdioma;
	}
	/**
	 * @return the numZoomLevels
	 */
	public String getNumZoomLevels() {
		return numZoomLevels;
	}
	/**
	 * @param numZoomLevels the numZoomLevels to set
	 */
	public void setNumZoomLevels(String numZoomLevels) {
		this.numZoomLevels = numZoomLevels;
	}
	/**
	 * @return the displayProjection
	 */
	public String getDisplayProjection() {
		return displayProjection;
	}
	/**
	 * @param displayProjection the displayProjection to set
	 */
	public void setDisplayProjection(String displayProjection) {
		this.displayProjection = displayProjection;
	}
	/**
	 * @return the projection
	 */
	public String getProjection() {
		return projection;
	}
	/**
	 * @param projection the projection to set
	 */
	public void setProjection(String projection) {
		this.projection = projection;
	}
	/**
	 * @return the initalBbox
	 */
	public String getInitalBbox() {
		return initalBbox;
	}
	/**
	 * @param initalBbox the initalBbox to set
	 */
	public void setInitalBbox(String initalBbox) {
		this.initalBbox = initalBbox;
	}
	/**
	 * @return the maxScale
	 */
	public String getMaxScale() {
		return maxScale;
	}
	/**
	 * @param maxScale the maxScale to set
	 */
	public void setMaxScale(String maxScale) {
		this.maxScale = maxScale;
	}
	/**
	 * @return the minScale
	 */
	public String getMinScale() {
		return minScale;
	}
	/**
	 * @param minScale the minScale to set
	 */
	public void setMinScale(String minScale) {
		this.minScale = minScale;
	}
	/**
	 * @return the resolutions
	 */
	public String getResolutions() {
		return resolutions;
	}
	/**
	 * @param resolutions the resolutions to set
	 */
	public void setResolutions(String resolutions) {
		this.resolutions = resolutions;
	}
	/**
	 * @return the maxResolution
	 */
	public String getMaxResolution() {
		return maxResolution;
	}
	/**
	 * @param maxResolution the maxResolution to set
	 */
	public void setMaxResolution(String maxResolution) {
		this.maxResolution = maxResolution;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	/**
	 * @return the minResolution
	 */
	public String getMinResolution() {
		return minResolution;
	}
	/**
	 * @param minResolution the minResolution to set
	 */
	public void setMinResolution(String minResolution) {
		this.minResolution = minResolution;
	}
	/**
	 * @return the bbox
	 */
	public String getBbox() {
		return bbox;
	}
	/**
	 * @param bbox the bbox to set
	 */
	public void setBbox(String bbox) {
		this.bbox = bbox;
	}
	
	
	
	
	
	

}