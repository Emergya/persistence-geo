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

import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTLayer;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.DBManagementDao;
import com.emergya.persistenceGeo.dao.GeoserverDao;
import com.emergya.persistenceGeo.service.GeoserverCleanService;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 *
 */
@Service
@Transactional(value = "multiSIRDatabaseTransactionManager")
public class GeoserverCleanServiceImpl implements GeoserverCleanService {

    private final static Log LOG = LogFactory
            .getLog(GeoserverCleanServiceImpl.class);

    @Resource
    private GeoserverDao gsDao;

    @Value("#{webProperties['geoserver.data.basePath']}")
    private String GEOSERVER_BASE_PATH;
    @Resource
    private DBManagementDao dbManagementDao;
    
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
    
    /**
     * Unpublish layer identified by layerName, workspace and workspace
     *
     * @param workspaceName
     * @param datastoreName
     *
     * @return true if can be unpublish and false otherwise
     */
    @Override
    public boolean unpublishLayer(String workspaceName, String datastoreName,
            String layerName) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Unpublishig geoserver layer");
        }

        boolean result = gsDao.deletePostgisFeatureType(workspaceName, datastoreName,
                layerName);
        return result;
    }

    @Override
    public boolean unpublishGsCoverageLayer(String workspaceName,
            String coverageLayer) {

        if (!gsDao.deleteCoverage(workspaceName, coverageLayer)) {
            return false;
        }

        if (!gsDao.deleteGsCoverageStore(workspaceName, coverageLayer)) {
            return false;
        }

        return true;
    }

    /**
     * Retrieves a layer's datastore associated with the layer.
     *
     * @param layerName
     *
     * @return
     */
    @Override
    public RESTDataStore getDatastore(String layerName) {
        return getGsDao().getDatastore(layerName);
    }
    
    /**
     * Retrieves all layers' name in geoserver
     *
     * @return
     */
    @Override
    public List<String> getLayersNamesByRegion(String prefixRegion) {   
        return gsDao.getLayersNamesByRegion("_".concat(prefixRegion.toLowerCase()).concat("_datastore"));
    }

    /**
     * Obtain native name of a layerName
     *
     * @param layerName
     *
     * @return native name of the layer
     */
    @Override
    public String getNativeName(String layerName) {
        return gsDao.getNativeName(layerName);
    }

    /**
     * Retrieves all styles' names in geoserver
     *
     * @return styles' names
     */
    @Override
    public List<String> getStyleNames() {
        return gsDao.getStyleNames();
    }

    /**
     * Clean styles unused in style list
     *
     * @param styleNames name of styles to be deleted
     *
     * @return list of deleted styles
     */
    @Override
    public List<String> cleanUnusedStyles(List<String> styleNames) {
        return gsDao.cleanUnusedStyles(styleNames);
    }

    /**
     * Gets the workspace a layer is in. given its name.
     *
     * @param layerName
     * @return
     */
    @Override
    public String getLayerWorkspace(String layerName) {
        return gsDao.getLayerWorkspace(layerName);
    }

    /**
     * Gets geoserver's info on a layer given its name.
     *
     * @param layerName
     * @return
     */
    @Override
    public RESTLayer getLayerInfo(String layerName) {
        return gsDao.getLayerInfo(layerName);
    }
}
