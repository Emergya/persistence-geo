/* GeoserverDao.java
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
package com.emergya.persistenceGeo.dao;

import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList;

import java.io.File;
import java.net.URI;

import com.emergya.persistenceGeo.utils.GsCoverageDetails;
import com.emergya.persistenceGeo.utils.GsCoverageStoreData;
import com.emergya.persistenceGeo.utils.GsFeatureDescriptor;
import com.emergya.persistenceGeo.utils.GsLayerDescriptor;

/**
 * Dao para la consulta y modificación de datos en Geoserver.
 * 
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public interface GeoserverDao {

	public static final String DEFAULT_RASTER_STYLE = "raster";

	boolean createWorkspace(String name);

	RESTWorkspaceList getWorkspaceList();

	boolean deleteWorkspace(String name);

	/**
	 * Creates a new namespace. Geoserver will create the corresponding
	 * workspace. Prefix and URI are mandatory.
	 * 
	 * @param prefix
	 *            prefix for the namespace.
	 * @param uri
	 *            URI for the namespace.
	 * @return <code>true</code> if the Namespace was successfully created.
	 */
	public boolean createNamespace(String prefix, URI uri);

	/**
	 * Checks if a GeoServer instance is running at the given URL. Return true
	 * if the configured GeoServer is up and replies to REST requests. Send a
	 * HTTP GET request to the configured URL. Return true if a HTTP 200 code
	 * (OK) is read from the HTTP response; any other response code, or
	 * connection error, will return a false boolean.
	 * 
	 * @return <code>true</code> if a GeoServer instance was found at the
	 *         configured URL.
	 */
	public boolean checkGeoserverConfiguration();

	/**
	 * Creates a new Postgis datastore in workspace <code>workspaceName</code>
	 * with the name <code>datastoreName</code>.
	 * 
	 * @param workspaceName
	 *            the workspace where datastore will be created.
	 * @param datastoreName
	 *            the name of the new datastore.
	 * @return
	 */
	public boolean createDatastore(String workspaceName, String datastoreName);

	/**
	 * Create a layer in GeoServer using an existing datasource.
	 * 
	 * @param workspace
	 *            workspace name
	 * @param storeName
	 *            datastore name
	 * @param featureDescriptor
	 *            datastore descriptor inside the datastore
	 * @param layerDescriptor
	 *            layer descriptor
	 * @return <code>true</code> if the layer has been published. Otherwise
	 *         <code>false</code>.
	 */
	public boolean publishPostgisLayer(String workspace, String storeName,
			GsFeatureDescriptor featureDescriptor,
			GsLayerDescriptor layerDescriptor);

	/**
	 * Delete the feature type from the datastore and the associated layer. You
	 * could also want call deleteDatastore.
	 * 
	 * @param workspaceName
	 *            name of the workspace.
	 * @param datastoreName
	 *            name of the datastore.
	 * @param layerName
	 *            name of the layer.
	 * @return <code>true</code> if the feature type has been successfully
	 *         deleted. Otherwise return <code>false</code>.
	 */
	boolean deletePostgisFeatureType(String workspaceName, String datastoreName,
			String layerName);

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
	boolean existsLayerInWorkspace(String layerName, String workspaceName);

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
	 *            workspace to use
	 * @param storeName
	 *            the store name to be used or created.
	 * @param geotiff
	 *            the GeoTIFF file.
	 * @param crs
	 *            the image's native SRS.
	 * @return <code>true</code> if success.
	 * 
	 */
	public boolean publishGeoTIFF(String workspace, String storeName,
			File geotif, String crs);

	/**
	 * Upload and publish a Image Mosaic raster.
	 * 
	 * @param workspace
	 *            workspace to use
	 * @param storeName
	 *            the store name to be used or created.
	 * @param imageFile
	 *            a ZIP file with an image mosaic.
	 * @param crs
	 *            the image mosaic coordinates system.
	 * @return <code>true</code> if success.
	 * 
	 */
	boolean publishImageMosaic(String workspaceName, String storeName,
			File imageFile, String crs);

	/**
	 * Upload and publish a World Image raster.
	 * 
	 * @param workspace
	 *            workspace to use
	 * @param storeName
	 *            the store name to be used or created.
	 * @param imageFile
	 *            a ZIP file with a World Image.
	 * @param crs
	 *            the image mosaic coordinates system.
	 * @return <code>true</code> if success.
	 * 
	 */
	boolean publishWorldImage(String workspaceName, String storeName,
			File imageFile, String crs);

	/**
	 * Retrieves info about a coverage store.
	 * 
	 * @param workspaceName 
	 * 				The workspace the store is associated to.
	 * @param coverageStoreName 
	 * 				The name of the coverage store whose info we want to retrieve.
	 * @return A {@link GsCoverageStoreData} object if succesful or null if not.
	 */
	GsCoverageStoreData getCoverageStoreData(
			String workspaceName, String coverageStoreName);

	/**
	 * Removes a coverage store from the geoserver.
	 * 
	 * @param workspaceName
	 * @param coverageStoreName
	 * @return
	 */
	boolean deleteGsCoverageStore(String workspaceName, String coverageStoreName);

	/**
	 * Unpublishes a coverage layer from the geoserver.
	 * @param workspaceName
	 * @param coverageLayer
	 * @return
	 */
	boolean deleteCoverage(String workspaceName, String coverageLayer);

	/**
	 * Gets the details of a coverage layer.
	 * @param workspaceName
	 * @param coverageStoreName
	 * @param coverageName
	 * @return
	 */
	GsCoverageDetails getCoverageDetails(
			String workspaceName, String coverageStoreName, String coverageName); 
	
	/**
	 * Retrieves a layer's style associated SDL file's contents.
	 * @param layerName
	 * @return
	 */
	String getLayerStyle(String layerName);

	/**
	 * Creates a new style with the given name and contents.
	 * @param newLayerName
	 * @param layerSDLContent
	 * @return true if the style creation was successful.
	 */
	boolean createStyle(String newLayerName, String layerSDLContent);

	/**
	 * Sets a layer's style.
	 * @param workspaceName
	 * @param layerName
	 * @param newLayerStyleName
	 */
	boolean setLayerStyle(String workspaceName, String layerName, String newLayerStyleName);
	
	/**
	 * Removes a style.
	 * @param styleName
	 * @return
	 */
	boolean deleteStyle(String styleName);
	
	boolean reset();
}
