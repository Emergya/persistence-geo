/* GeoserverService.java
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
package com.emergya.persistenceGeo.service;

import it.geosolutions.geoserver.rest.decoder.RESTDataStore;

import java.io.File;
import java.util.List;

import com.emergya.persistenceGeo.utils.BoundingBox;
import com.emergya.persistenceGeo.utils.GsCoverageDetails;
import com.emergya.persistenceGeo.utils.GsCoverageStoreData;
import com.emergya.persistenceGeo.utils.GeometryType;

import it.geosolutions.geoserver.rest.decoder.RESTLayer;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public interface GeoserverCleanService {
	public static final String VECTORIAL_LAYER_TYPE = "postgis";
	public static final String GEOTIFF_LAYER_TYPE_NAME = "geotiff";
	public static final String IMAGEWORLD_LAYER_TYPE_NAME = "imageworld";
	public static final String IMAGEMOSAIC_LAYER_TYPE_NAME = "imagemosaic";
	public static final String WFS_LAYER_TYPE_NAME = "WFS";

	public final String DEFAULT_SRS = "EPSG:4326";

	/**
	 * Unpublish layer identified by layerName, workspace and workspace
	 * 
	 * @param workspaceName
	 * @param datastoreName
	 * @param layerName
	 * 
	 * @return true if can be unpublish and false otherwise
	 */
	public boolean unpublishLayer(String workspaceName, String datastoreName, String layerName);
	
	/**
	 * Retrieves all layers' names in geoserver by Region
	 * @param prefixRegion 
	 * 
	 * @return layers' names
	 */
	public List<String> getLayersNamesByRegion(String prefixRegion);

	/**
	 * Obtain native name of a layerName 
	 * 
	 * @param layerName
	 * 
	 * @return native name of the layer 
	 */
	public String getNativeName(String layerName);
	
	/**
	 * Retrieves all styles' names in geoserver
	 * 
	 * @return styles' names
	 */
	public List<String> getStyleNames();

	
	/**
	 * Clean styles unused in style list 
	 * 
	 * @param styleNames name of styles to be deleted
	 * 
	 * @return list of deleted styles
	 */
	public List<String> cleanUnusedStyles(List<String> styleNames);
	
	/**
	 * Unpublishes a layer stored in a coverage store from the geoserver. Also,
	 * it deletes the
	 * 
	 * @param workspaceName
	 * @param layerName
	 * @return
	 */
	public boolean unpublishGsCoverageLayer(String workspaceName,
			String layerName);
	
	/**
	 * Retrieves a layer's datastore associated with the layer.
	 * 
	 * @param layerName
	 * 
	 * @return
	 */
	public RESTDataStore getDatastore(String layerName);

	/**
	 * Gets the workspace a layer is in.
	 * given its name.
	 * @param layerName
	 * @return 
	 */
	public String getLayerWorkspace(String layerName);
	
	/**
	 * Gets Geoserver's info on the layer given its name.
	 * @param layerName
	 * @return 
	 */
        public RESTLayer getLayerInfo(String layerName);
	
	
	/**
	 * Enumerate type containing result codes for the <c>duplicateGeoServerLayer</c>
	 * method.
	 */
	public enum DuplicationResult {
	    SUCCESS_VECTORIAL,
	    SUCCESS_RASTER,
	    SUCCESS_REMOTE,
	    FAILURE
	}
}
