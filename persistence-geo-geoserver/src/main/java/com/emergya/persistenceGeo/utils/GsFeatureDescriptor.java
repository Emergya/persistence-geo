/* GsFeatureDescriptor.java
 * 
 * Copyright (C) 2012
 * 
 * This file is part of project persistence-geo-core
 * 
 * This software is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 * 
 * Authors:: Juan Luis Rodríguez Ponce (mailto:jlrodriguez@emergya.com)
 */
package com.emergya.persistenceGeo.utils;

import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public class GsFeatureDescriptor {
	private static final String EPSG_4326 = "EPSG:4326";
	private boolean enabled;
	private Set<String> keywords;
	private ProjectionPolicy projectionPolicy;
	private String name;
	private String title;
	private String srs;
	private String nativeCRS;
	private BoundingBox latLonBoundingBox;
	private BoundingBox nativeBoundingBox;
	private String nativeName;

	public GsFeatureDescriptor() {
		this.keywords = new HashSet<String>();
		this.enabled = true;
		this.srs = EPSG_4326;
		this.nativeCRS = EPSG_4326;
		this.projectionPolicy = ProjectionPolicy.REPROJECT_TO_DECLARED;
		this.latLonBoundingBox = new BoundingBox();
		this.latLonBoundingBox.setMaxx(180.0d);
		this.latLonBoundingBox.setMaxy(90.0d);
		this.latLonBoundingBox.setMinx(-180.0d);
		this.latLonBoundingBox.setMiny(-90.0d);
		this.latLonBoundingBox.setSrs(EPSG_4326);
		this.nativeBoundingBox = (BoundingBox) latLonBoundingBox.clone();
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return the projectionPolicy
	 */
	public ProjectionPolicy getProjectionPolicy() {
		return projectionPolicy;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @param nativeCRS
	 *            the nativeCRS to set
	 */
	public void setNativeCRS(String nativeCRS) {
		this.nativeCRS = nativeCRS;
	}

	/**
	 * @param latLonBoundingBox
	 *            the latLonBoundingBox to set
	 */
	public void setLatLonBoundingBox(BoundingBox latLonBoundingBox) {
		this.latLonBoundingBox = latLonBoundingBox;
	}

	/**
	 * @param nativeBoundingBox
	 *            the nativeBoundingBox to set
	 */
	public void setNativeBoundingBox(BoundingBox nativeBoundingBox) {
		this.nativeBoundingBox = nativeBoundingBox;
	}

	public void setEnabled(boolean enabled) {

	}

	public void addKeyword(String keyword) {
		keywords.add(keyword);
	}

	public Set<String> getKeywords() {
		return keywords;
	}

	/**
	 * NONE, REPROJECT_TO_DECLARED, FORCE_DECLARED
	 */
	public void setProjectionPolicy(ProjectionPolicy policy) {
		this.projectionPolicy = policy;
	}

	/**
	 * Get the name of
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Get the 'title'.
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Get the SRS-.
	 */
	public String getSRS() {
		return this.srs;
	}

	/**
	 * Get the Native CRS;
	 */
	public String getNativeCRS() {
		return this.nativeCRS;
	}

	public BoundingBox getLatLonBoundingBox() {
		return this.latLonBoundingBox;
	}

	public BoundingBox getNativeBoundingBox() {
		return this.nativeBoundingBox;
	}

	/**
	 * @return the nativeName
	 */
	public String getNativeName() {
		return nativeName;
	}

	/**
	 * @param nativeName the nativeName to set
	 */
	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

}
