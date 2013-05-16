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

import java.io.File;

import com.emergya.persistenceGeo.utils.BoundingBox;
import com.emergya.persistenceGeo.utils.GsCoverageDetails;
import com.emergya.persistenceGeo.utils.GsCoverageStoreData;
import com.emergya.persistenceGeo.utils.GsLayerDescriptor.GeometryType;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public interface GeoserverService {

	public final String DEFAULT_SRS = "EPSG:4326";

	public boolean createGsWorkspaceWithDatastore(String workspaceName);

	public boolean deleteGsWorkspace(String workspaceName);

	public boolean unpublishGsDbLayer(String workspaceName, String layer);

	/**
	 * Publishes a vectorial layer with data in a PostGis database with the
	 * default styles and given parameters.
	 * 
	 * @param workspaceName
	 * @param table_name
	 * @param layerName
	 * @param title
	 * @param nativeBoundingBox
	 * @param type
	 * @return
	 */
	boolean publishGsDbLayer(String workspaceName, String table_name,
			String layerName, String title, BoundingBox nativeBoundingBox,
			GeometryType type);

	/**
	 * Check if a layer with <code>layerName</code> exists in the workspace
	 * <code>workspaceName</code>
	 * 
	 * @param layerName
	 *            layer name.
	 * @param workspaceName
	 *            workspace name.
	 * @return <code>true</code> if a layer named <code>layerName</code> exists
	 *         in the workspace named <code>workspaceName</code>.
	 */
	public boolean existsLayerInWorkspace(String layerName, String workspaceName);

	/**
	 * Create a Postgis datastore using a JNDI connection.
	 * 
	 * @param workspaceName
	 *            the workspace where datastore will be created.
	 * @param datastoreName
	 *            the name of the new datastore.
	 * @return
	 */
	public boolean createDatastoreJndi(String workspaceName,
			String datastoreName);

	/**
	 * Upload and publish a GeoTIFF image.
	 * 
	 * @param workspace
	 *            workspace to use.
	 * @param layerName
	 *            the layer name to be created.
	 * @param geotiff
	 *            the GeoTIFF file.
	 * @param crs
	 *            the image native SRS.
	 * @return <code>true</code> if success.
	 */
	public boolean publishGeoTIFF(String workspace, String layerName,
			File geotiff, String crs);

	/**
	 * Upload and publish an Image Mosaic image.
	 * 
	 * @param workspaceName
	 *            workspace to use.
	 * @param layerName
	 *            the store name to be created. It will also be the created 
	 *            coverage store
	 * @param imageFile
	 *            a ZIP file with an image mosaic.
	 * @param crs
	 *            the image mosaic coordinates system.
	 * @return <code>true</code> if success.
	 */
	public boolean publishImageMosaic(String workspaceName, String layerName,
			File imageFile, String crs);

	/**
	 * Upload and publish an World Image file.
	 * 
	 * @param workspaceName
	 *            workspace to use.
	 * @param layerName
	 *            the layer name to be created.
	 * @param imageFile
	 *            a ZIP file with an World Image.
	 * @param crs
	 *            the world image coordinates system.
	 * @return <code>true</code> if success.
	 */
	public boolean publishWorldImage(String workspaceName, String layerName,
			File imageFile, String crs);

	/**
	 * Gets info about a coverage store.
	 * 
	 * @param workspaceName
	 * @param coverageStoreName
	 * @return
	 */
	public GsCoverageStoreData getCoverageStoreData(String workspaceName,
			String coverageStoreName);

	/**
	 * Unpublishes a layer stored in a coverage store from the geoserver. Also,
	 * it deletes the
	 * 
	 * @param workspaceName
	 * @param layerName
	 * @return
	 */
	public boolean unpublishGsCoverageLayer(String adminWorkspaceName,
			String layerName);

	/**
	 * Retrieves the details of a coverage store: bbox, projection, etc.
	 * 
	 * @param workspaceName
	 * @param coverageStore
	 * @param coverageName
	 * @return
	 */
	public GsCoverageDetails getCoverageDetails(String workspaceName,
			String coverageStore, String coverageName);

	/**
	 * Copies the style of the source layer with a new name, including the sdl
	 * file stored in the server.
	 * 
	 * @param sourceLayerName
	 * @param newLayerName
	 * @return
	 */
	public boolean copyLayerStyle(String sourceLayerName, String newLayerName);

	/**
	 * Changes a layer style. The style must exist in geoserver.
	 * 
	 * @param workspaceName
	 * @param layerName
	 * @param newStyleName
	 * @return
	 */
	public boolean setLayerStyle(String workspaceName, String layerName,
			String newStyleName);

	/**
	 * Removes an style from GeoServer.
	 */
	public boolean deleteStyle(String styleName);
	
	public boolean reset();
	/**
	 * Checks if a workspace exists on the server.
	 * 
	 * @param workspaceName
	 *            name to check.
	 * @return <code>true</code> if the workspace exists, <code>false</code> if
	 *         not.
	 */
	public boolean existsWorkspace(String workspaceName);
}
