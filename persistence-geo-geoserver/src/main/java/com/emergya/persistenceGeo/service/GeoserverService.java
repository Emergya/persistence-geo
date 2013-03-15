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
import com.emergya.persistenceGeo.utils.GsLayerDescriptor.GeometryType;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public interface GeoserverService {
	public boolean createGsWorkspaceWithDatastore(String workspaceName);

	public boolean deleteGsWorkspace(String workspaceName);

	public boolean unpublishGsLayer(String workspaceName, String layer);

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
	 * @param storeName
	 *            the store name to be used or created.
	 * @param geotiff
	 *            the GeoTIFF file.
	 * @param crs
	 *            the image native SRS.
	 * @return <code>true</code> if success.
	 */
	public boolean publishGeoTIFF(String workspace, String storeName,
			File geotiff, String crs);
}
