/* GeoserverServiceImpl.java
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
package com.emergya.persistenceGeo.service.impl;

import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.emergya.persistenceGeo.dao.GeoserverDao;
import com.emergya.persistenceGeo.exceptions.GeoserverException;
import com.emergya.persistenceGeo.service.GeoserverService;
import com.emergya.persistenceGeo.utils.BoundingBox;
import com.emergya.persistenceGeo.utils.GsFeatureDescriptor;
import com.emergya.persistenceGeo.utils.GsLayerDescriptor;
import com.emergya.persistenceGeo.utils.GsLayerDescriptor.GeometryType;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public class GeoserverServiceImpl implements GeoserverService {
	private final static Log LOG = LogFactory
			.getLog(GeoserverServiceImpl.class);
	private final static String DATASTORE_SUFFIX = "_datastore";

	@Resource
	private GeoserverDao gsDao;
	/**
	 * @return the gsDao
	 */
	public GeoserverDao getGsDao() {
		return gsDao;
	}

	/**
	 * @param gsDao the gsDao to set
	 */
	public void setGsDao(GeoserverDao gsDao) {
		this.gsDao = gsDao;
	}

	/**
	 * @return the namespaceBaseUrl
	 */
	public String getNamespaceBaseUrl() {
		return namespaceBaseUrl;
	}

	/**
	 * @param namespaceBaseUrl the namespaceBaseUrl to set
	 */
	public void setNamespaceBaseUrl(String namespaceBaseUrl) {
		this.namespaceBaseUrl = namespaceBaseUrl;
	}

	@Resource
	private String namespaceBaseUrl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emergya.persistenceGeo.service.GeoserverService#
	 * createGsWorkspaceWithDatastore(java.lang.String)
	 */
	@Override
	public boolean createGsWorkspaceWithDatastore(String workspaceName) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Creating Geoserver workspace [workspaceName="
					+ workspaceName + "]");
		}
		boolean result = false;
		String nsUrl;
		if (this.namespaceBaseUrl.endsWith("/")) {
			nsUrl = this.namespaceBaseUrl + workspaceName;
		} else {
			nsUrl = this.namespaceBaseUrl + "/" + workspaceName;
		}
		URI uri;
		try {
			uri = new URI(nsUrl);
			result = gsDao.createNamespace(workspaceName, uri);

			if (!result) {
				// Can't create Namespace. Stop here.
				if (LOG.isInfoEnabled()) {
					LOG.info("Couln't create the namespace and his asocciated "
							+ "workspace [workspaceName=" + workspaceName + "]");
				}
				return result;
			}
			String datastoreName = workspaceName + DATASTORE_SUFFIX;
			result = gsDao.createDatastoreJndi(workspaceName, datastoreName);
			if (!result) {
				// Can't create Datastore. Try to delete workspace.
				if (LOG.isInfoEnabled()) {
					LOG.info("Couln't create the datastore " + datastoreName
							+ " in workspace " + workspaceName
							+ ". Trying to delete workspace...");
				}
				boolean deleteResult = gsDao.deleteWorkspace(workspaceName);

				if (LOG.isInfoEnabled()) {
					if (deleteResult) {
						LOG.info("Workspace " + workspaceName
								+ " successfully deleted");
					} else {
						LOG.warn("Couldn't delete workspace " + workspaceName);
					}
				}
			}

		} catch (URISyntaxException e) {
			throw new GeoserverException("Illegal URL syntax detected when"
					+ "preparing for creating a geoserver workspace [URI="
					+ nsUrl + "]", e);
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emergya.persistenceGeo.service.GeoserverService#deleteGsWorkspace
	 * (java.lang.String)
	 */
	@Override
	public boolean deleteGsWorkspace(String workspaceName) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Deleting Geoserver workspace [workspaceName="
					+ workspaceName + "]");
		}
		return gsDao.deleteWorkspace(workspaceName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emergya.persistenceGeo.service.GeoserverService#publishGsDbLayer()
	 */
	@Override
	public boolean publishGsDbLayer(String workspaceName, String tableName,
			String layerName, String title,  BoundingBox nativeBoundingBox, GeometryType geomType) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Publising geoserver database layer [workspaceName="
					+ workspaceName + ", tableName=" + tableName + ", layerName="
					+ layerName + ", geometryType=" + geomType + "]");
		}
		boolean result = false;
		GsFeatureDescriptor fd = new GsFeatureDescriptor();
		fd.setNativeName(tableName);
		fd.setTitle(title);
		fd.setName(layerName);
		fd.setSRS(nativeBoundingBox.getSrs());
		fd.setNativeCRS(nativeBoundingBox.getSrs());		
		if (nativeBoundingBox != null) {
			fd.setNativeBoundingBox(nativeBoundingBox);
			fd.setLatLonBoundingBox(nativeBoundingBox);
		}
		
		GsLayerDescriptor ld = new GsLayerDescriptor();
		
		
		ld.setType(geomType);
		String datastoreName = workspaceName + DATASTORE_SUFFIX;
		result = gsDao
				.publishPostgisLayer(workspaceName, datastoreName, fd, ld);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emergya.persistenceGeo.service.GeoserverService#unpublishGsDbLayer
	 * (boolean)
	 */
	@Override
	public boolean unpublishGsDbLayer(String workspaceName, String layerName,
			boolean deletePostgisTable) {
		if (LOG.isInfoEnabled()) {
			LOG.info("Unpublishig geoserver database layer");
		}
		boolean result = false;
		result = gsDao.deletePostgisFeatureTye(workspaceName, workspaceName
				+ DATASTORE_SUFFIX, layerName);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.service.GeoserverService#existsLayerInWorkspace(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean existsLayerInWorkspace(String layerName, String workspaceName) {	
		return gsDao.existsLayerInWorkspace(layerName, workspaceName);
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.service.GeoserverService#createDatastoreJndi(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean createDatastoreJndi(String workspaceName,
			String datastoreName) {
		return gsDao.createDatastoreJndi(workspaceName, datastoreName);
	}

	@Override
	public boolean publishGeoTIFF(String workspace, String storeName,
			File geotiff) {
		return gsDao.publishGeoTIFF(workspace, storeName, geotiff);
	}
	
	

}
