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

import com.emergya.persistenceGeo.bean.RegionBean;
import com.emergya.persistenceGeo.dao.DBManagementDao;

import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList.RESTShortWorkspace;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geotools.referencing.CRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.NoSuchAuthorityCodeException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;

import com.emergya.persistenceGeo.dao.GeoserverDao;
import com.emergya.persistenceGeo.exceptions.GeoserverException;
import com.emergya.persistenceGeo.service.GeoserverService;
import com.emergya.persistenceGeo.utils.BoundingBox;
import com.emergya.persistenceGeo.utils.GsCoverageDetails;
import com.emergya.persistenceGeo.utils.GsCoverageStoreData;
import com.emergya.persistenceGeo.utils.GsFeatureDescriptor;
import com.emergya.persistenceGeo.utils.GsLayerDescriptor;
import com.emergya.persistenceGeo.utils.GeometryType;

import it.geosolutions.geoserver.rest.decoder.RESTLayer;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 *
 */
@Service
public class GeoserverServiceImpl implements GeoserverService {

    private final static Log LOG = LogFactory
            .getLog(GeoserverServiceImpl.class);
    private String DATASTORE_SUFFIX = "_datastore";

    @Resource
    private GeoserverDao gsDao;

    @Value("#{webProperties['geoserver.data.basePath']}")
    private String GEOSERVER_BASE_PATH;
    @Resource
    private DBManagementDao dbManagementDao;
    
    private RegionBean region;
    
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
    
    public GeoserverServiceImpl() {
		super();
	}
    
    public GeoserverServiceImpl(RegionBean region) {
		super();
		this.region = region;
		DATASTORE_SUFFIX = "_".concat(region.getPrefix_wks().toLowerCase()).concat(DATASTORE_SUFFIX);
    }

	/*
     * (non-Javadoc)
     * 
     * @see com.emergya.persistenceGeo.service.GeoserverService#
     * createGsWorkspaceWithDatastore(java.lang.String)
     */
    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
    public boolean publishGsDbLayer(String workspaceName, String tableName,
            String layerName, String title, BoundingBox nativeBoundingBox,
            GeometryType geomType) {
        if (LOG.isInfoEnabled()) {
            LOG.info(String.format("Publising geoserver database layer [workspaceName=%s, tableName=%s, layerName=%s, geometryType=%s]",
                    workspaceName, tableName, layerName, geomType));
        }
        boolean result = false;

        // Transform native bounding box to EPSG:4326
        String nativeSrs = nativeBoundingBox.getSrs();
        BoundingBox declaredBBox = new BoundingBox();
        declaredBBox.setSrs(DEFAULT_SRS);
        boolean declaredSrsTransformed = false;
        try {
            CoordinateReferenceSystem nativeCRS = CRS.decode(nativeSrs);
            CoordinateReferenceSystem targetCRS = CRS.decode(DEFAULT_SRS);
            MathTransform transform = CRS.findMathTransform(nativeCRS,
                    targetCRS);
            double[] sourceCoords = new double[4];
            double[] coordTransformed = new double[4];

            // Fill the array with the bounding box
            sourceCoords[0] = nativeBoundingBox.getMinx();
            sourceCoords[1] = nativeBoundingBox.getMiny();
            sourceCoords[2] = nativeBoundingBox.getMaxx();
            sourceCoords[3] = nativeBoundingBox.getMaxy();
            transform.transform(sourceCoords, 0, coordTransformed, 0, 2);

            declaredBBox.setMinx(coordTransformed[0]);
            declaredBBox.setMiny(coordTransformed[1]);
            declaredBBox.setMaxx(coordTransformed[2]);
            declaredBBox.setMaxy(coordTransformed[3]);
            declaredSrsTransformed = true;

        } catch (NoSuchAuthorityCodeException e) {
            LOG.error(
                    "No se ha encontrado la autoridad especificada en el Sistema de Referencia Nativo",
                    e);
        } catch (FactoryException e) {
            LOG.error(
                    "No se ha podido crear la factoría de SRS en GeoserverServiceImpl",
                    e);
        } catch (TransformException e) {
            LOG.error(
                    "Error transformando las coordenadas del nativo al delcarado. Se usará como declarado el mismo que el nativo",
                    e);
        }

        GsFeatureDescriptor fd = new GsFeatureDescriptor();
        fd.setNativeName(tableName);
        fd.setTitle(title);
        fd.setName(layerName);
        if (nativeBoundingBox != null) {
            if (declaredSrsTransformed) {
                fd.setLatLonBoundingBox(declaredBBox);

                // this is not an error. You should assign the native SRS to the
                // declared SRS.
                fd.setSRS(nativeBoundingBox.getSrs());

            }
            fd.setNativeCRS(nativeBoundingBox.getSrs());
            fd.setNativeBoundingBox(nativeBoundingBox);
        }

        GsLayerDescriptor ld = new GsLayerDescriptor();

        ld.setType(geomType);

        String datastoreName = workspaceName + DATASTORE_SUFFIX;
        result = gsDao
                .publishPostgisLayer(workspaceName, datastoreName, fd, ld);

        return result;
    }

    /**
     * Unpublish layer identified by layerName, workspace and workspace
     *
     * @param workspaceName
     * @param datastoreName
     *
     * @return true if can be unpublish and false otherwise
     */
    @Override
    @Transactional
    public boolean unpublishLayer(String workspaceName, String datastoreName,
            String layerName) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Unpublishig geoserver layer");
        }

        boolean result = gsDao.deletePostgisFeatureType(workspaceName, datastoreName,
                layerName);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.emergya.persistenceGeo.service.GeoserverService#unpublishGsLayer
     * (boolean)
     */
    @Override
    @Transactional
    public boolean unpublishGsDbLayer(String workspaceName, String layerName) {
        return unpublishLayer(workspaceName, workspaceName + DATASTORE_SUFFIX,
                layerName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.emergya.persistenceGeo.service.GeoserverService#existsLayerInWorkspace
     * (java.lang.String, java.lang.String)
     */
    @Override
    public boolean existsLayerInWorkspace(String layerName, String workspaceName) {
        return gsDao.existsLayerInWorkspace(layerName, workspaceName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.emergya.persistenceGeo.service.GeoserverService#createDatastoreJndi
     * (java.lang.String, java.lang.String)
     */
    @Override
    @Transactional
    public boolean createDatastoreJndi(String workspaceName,
            String datastoreName) {
        return gsDao.createDatastoreJndi(workspaceName, datastoreName);
    }

    @Override
    @Transactional
    public boolean publishGeoTIFF(String workspace, String layerName,
            File geotiff, String crs) {
        return gsDao.publishGeoTIFF(workspace, layerName, geotiff, crs);
    }

    @Override
    @Transactional
    public boolean publishImageMosaic(String workspaceName, String layerName,
            File imageFile, String crs) {
        return gsDao.publishImageMosaic(workspaceName, layerName, imageFile,
                crs);
    }

    @Override
    @Transactional
    public boolean publishWorldImage(String workspaceName, String layerName,
            File imageFile, String crs) {

        return gsDao
                .publishWorldImage(workspaceName, layerName, imageFile, crs);
    }

    @Override
    @Transactional
    public GsCoverageStoreData getCoverageStoreData(String workspaceName,
            String coverageStoreName) {
        return gsDao.getCoverageStoreData(workspaceName, coverageStoreName);
    }

    @Override
    @Transactional
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

    @Override
    public GsCoverageDetails getCoverageDetails(String workspaceName,
            String coverageStore, String coverageName) {
        return gsDao.getCoverageDetails(workspaceName, coverageStore,
                coverageName);
    }

    @Override
    @Transactional
    public boolean copyLayerStyle(String sourceLayerName, String newStyleName) {
        String layerSDLContent = gsDao.getLayerStyle(sourceLayerName);

        if (layerSDLContent == null) {
            return false;
        }

        return gsDao.createStyle(newStyleName, layerSDLContent);
    }

    @Override
    public boolean setLayerStyle(String workspaceName, String layerName,
            String newLayerStyleName) {

        return gsDao.setLayerStyle(workspaceName, layerName, newLayerStyleName);
    }

    @Override
    public boolean deleteStyle(String styleName) {
        return gsDao.deleteStyle(styleName);
    }

    @Override
    public boolean reset() {
        return gsDao.reset();
    }

    @Override
    public boolean existsWorkspace(String workspaceName) {
        RESTWorkspaceList workspaceList = gsDao.getWorkspaceList();
        for (RESTShortWorkspace workspace : workspaceList) {
            String currentName = workspace.getName();
            if (StringUtils.equals(workspaceName, currentName)) {
                return true;
            }
        }
        return false;
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
     * Retrieves the geoserver url configured.
     *
     * @return baseUrl to geoserver
     */
    @Override
    public String getGeoserverUrl() {
        return getGsDao().getGeoserverUrl();
    }

    @Override
    @Transactional
    public void copyLayer(String workspaceName, String datastoreName,
            String layerName, String tableName, String title, BoundingBox bbox,
            GeometryType type, String targetWorkspaceName,
            String targetDatastoreName, String targetLayerName) {
        publishGsDbLayer(workspaceName, tableName, targetLayerName, title,
                bbox, type);
        copyLayerStyle(layerName, targetLayerName);
        setLayerStyle(workspaceName, targetLayerName, targetLayerName);
    }

    /**
     * Retrieves all layers' name in geoserver
     *
     * @return
     */
    @Override
    public List<String> getLayersNames() {
        return gsDao.getLayersNamesByRegion(region.getPrefix_wks());
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

    @Override
    @Transactional
    public DuplicationResult duplicateGeoServerLayer(
            String sourceWorkspace, String sourceLayerType,
            String sourceLayerName, String sourceLayerTable,
            String targetWorkspace, String newLayerName, String newLayerTitle) {

        if (sourceLayerType.contains(WFS_LAYER_TYPE_NAME)
                || sourceLayerType.contains(VECTORIAL_LAYER_TYPE)
                && StringUtils.isEmpty(sourceLayerName)) {
            // We don't need to do anything for WFS layers or remote WMS layers
            // As we don't have data stored neither in the database or
            // geoserver.
            return DuplicationResult.SUCCESS_REMOTE;
        }

        if (!copyLayerStyle(sourceLayerName, newLayerName)) {
            return DuplicationResult.FAILURE;
        }

        DuplicationResult result;
        if (sourceLayerType.contains(VECTORIAL_LAYER_TYPE)) {
            result = duplicateVectorLayer(
                    sourceLayerName,
                    sourceLayerTable,
                    targetWorkspace,
                    newLayerTitle, newLayerName);
        } else {
           
                result = duplicateRasterLayer(
                        sourceWorkspace, sourceLayerName, sourceLayerType,
                        targetWorkspace, newLayerName);
            
        }

        if (result == DuplicationResult.FAILURE) {
            return result;
        }
        // The new created style for the layer is named after the own layer.
        if (!this.setLayerStyle(
                targetWorkspace,
                newLayerName, newLayerName)) {
            result = DuplicationResult.FAILURE;
        }

        return result;
    }

    private DuplicationResult duplicateRasterLayer(
            String sourceWorkspace, String sourceLayerName, String type, String targetWorkspace, String newLayerName){

        // Layers should be in the authority workspace...
        GsCoverageStoreData coverageStoreData
                = getCoverageStoreData(sourceWorkspace, sourceLayerName);

        if (coverageStoreData == null) {
            return DuplicationResult.FAILURE;
        }

        GsCoverageDetails coverageDetails
                = getCoverageDetails(sourceWorkspace, sourceLayerName,
                        sourceLayerName);
        if (coverageDetails == null) {
            return DuplicationResult.FAILURE;
        }

        String srs = coverageDetails.getDeclaredSRS();

        // So we can retrieve the file's path.
        String filePath = GEOSERVER_BASE_PATH
                + coverageStoreData.getURL().replace("file:", "/");

        File rasterSourceFile = new File(filePath);

        // We need to compress the folder containing the files.
        boolean result = false;
        if (type.contains(GEOTIFF_LAYER_TYPE_NAME)) {
            result = publishGeoTIFF(targetWorkspace, newLayerName, rasterSourceFile, srs);
        } else {
            File zipFile;
            try {
                zipFile = compressSourceFolder(rasterSourceFile.getParentFile());
            } catch (IOException ex) {
                LOG.error("Couldn't create zip file!", ex);
                return DuplicationResult.FAILURE;
            }
            if (type.contains(IMAGEWORLD_LAYER_TYPE_NAME)) {
                result = publishWorldImage(targetWorkspace, newLayerName, zipFile, srs);
            } else if (type.contains(IMAGEMOSAIC_LAYER_TYPE_NAME)) {
                result = publishImageMosaic(targetWorkspace, newLayerName, zipFile, srs);
            } else {
                throw new IllegalArgumentException(
                        "Unsupported publication of raster layers of type: " + type);
            }
        }

        if (!result) {
            return DuplicationResult.FAILURE;
        }

        return DuplicationResult.SUCCESS_RASTER;
    }

    private DuplicationResult duplicateVectorLayer(
            String sourceLayerName,
            String sourceLayerTable,
            String targetWorkspace,
            String layerTitle,
            String newLayerName) {

        if (StringUtils.isEmpty(sourceLayerName)) {
            // Its a remote WFS layer, we have to do nothing here.
            return DuplicationResult.SUCCESS_REMOTE;
        }
        BoundingBox bbox;
        GeometryType type;
        // We copy the table with all data include directly in PostGis
        try {
            dbManagementDao.duplicateLayerTable(sourceLayerTable, newLayerName);
            bbox = dbManagementDao.getTableBoundingBox(newLayerName);
            type = dbManagementDao.getTableGeometryType(newLayerName);
        } catch (RuntimeException e) {
            LOG.error("Error!", e);
            return DuplicationResult.FAILURE;
        }

        // We tell geoserver that a new layer is avalaible.
        if (!publishGsDbLayer(targetWorkspace, newLayerName, newLayerName, layerTitle, bbox, type)) {
            return DuplicationResult.FAILURE;
        }

        return DuplicationResult.SUCCESS_VECTORIAL;
    }

    /**
     * Deletes a layer from Geoserver, deleting also the data backing it.
     *
     * @param workspace
     * @param layerName
     * @param layerType
     * @param tableName
     * @return
     */
    @Override
    @Transactional
    public boolean deleteGeoServerLayer(String workspace, String layerName, String layerType, String tableName) {

        if (layerType.contains(WFS_LAYER_TYPE_NAME)
                || layerType.contains(VECTORIAL_LAYER_TYPE)
                && StringUtils.isEmpty(layerName)) {
            // We don't need to do nothing in the database or geoserver for WFS
            // or a remote Vectorial layer (which don't have table name)
            // layers as we don't store its data, just reference it.
            return true;
        }

        if (!existsWorkspace(workspace)) {
            return true;
        }

        // First we delete the current geoserver data
        if (layerType.contains(VECTORIAL_LAYER_TYPE)) {
            // For vectorial layers we need to remove the data table backing the
            // layer.
            try {
                dbManagementDao.deleteLayerTable(tableName);
            } catch (SQLGrammarException ge) {
                // This launches if the table was already deleted, not an actual
                // grammar error.
            }

            if (!unpublishGsDbLayer(workspace, layerName)) {
                return false;
            }
        } else {

            if (!deleteCoverageData(workspace, layerName)) {
                return false;
            }

            if (!unpublishGsCoverageLayer(workspace, layerName)) {
                LOG.error("Coverage store's was already unpublished");
                return false;
            }
        }

        // We remove the tmp layer's style.
        return deleteStyle(layerName);
    }

    private boolean deleteCoverageData(String workspace, String layerName) {
        // Raster layer types. WFS layers don't neede removal of geoserver
        // data.
        GsCoverageStoreData coverageStoreData = getCoverageStoreData(workspace, layerName);

        if (coverageStoreData == null) {
            return false;
        }

        // So we can retrieve the file's path.
        String filePath = GEOSERVER_BASE_PATH
                + coverageStoreData.getURL().replace("file:", "/");

        // We remove the file and folder.
        File file = new File(filePath);
        File folder = new File(file.getParent());

        for (File folderFile : folder.listFiles()) {
            if (!folderFile.delete()) {
                LOG.error("The coverage's data file couldn't be deleted!");
                return false;
            }
        }

        if (!folder.delete()) {
            LOG.error("The coverage's data folder couldn't be deleted!");
            return false;
        }

        return true;
    }

    private File compressSourceFolder(File folder) throws IOException {
        File zipFile;
        ZipOutputStream zipOutput = null;

        zipFile = File.createTempFile("tmpRaster", ".zip");
        zipOutput = new ZipOutputStream(new FileOutputStream(zipFile));

        for (File f : folder.listFiles()) {
            zipOutput.putNextEntry(new ZipEntry(f.getName()));

            IOUtils.copy(new FileInputStream(f), zipOutput);
            zipOutput.closeEntry();
        }

        zipOutput.close();

        return zipFile;
    }

}
