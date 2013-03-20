package com.emergya.persistenceGeo.dao.impl;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList;
import it.geosolutions.geoserver.rest.decoder.RESTWorkspaceList.RESTShortWorkspace;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emergya.persistenceGeo.dao.GeoserverDao;
import com.emergya.persistenceGeo.exceptions.GeoserverException;
import com.emergya.persistenceGeo.utils.GsFeatureDescriptor;
import com.emergya.persistenceGeo.utils.GsLayerDescriptor;
import com.emergya.persistenceGeo.utils.GsLayerDescriptor.GeometryType;
import com.emergya.persistenceGeo.utils.GsRestApiConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:geoserverTestContext.xml" })
public class GeoserverGsManagerDaoImplTest {
	private static final String TABLE_NAME = "test_geometry_table";
	private final static String WORKSPACE_NAME = "workspace_test";
	private static final String DATASTORE_NAME = "datastore_test";
	private static final String LAYER_TITLE = "new layer title";

	private final static Log LOG = LogFactory
			.getLog(GeoserverGsManagerDaoImplTest.class);

	@Autowired
	private GeoserverDao geoserverDao;
	@Autowired
	private GsRestApiConfiguration configuration;

	@Before
	public void checkGeoserverPresence() {
		LOG.info("Using " + configuration.getServerUrl()
				+ " Geoserver instance for tests");
		try {
			boolean correct = geoserverDao.checkGeoserverConfiguration();
			if (!correct) {
				fail("All test will fail. Geoserver instance not accesible using"
						+ " provided configuration [serverUrl="
						+ configuration.getServerUrl() + "].");
			}
		} catch (GeoserverException gse) {
			fail("All test will fail because wrong geoserver client configuration");
		}

		// Clean test elements
		geoserverDao.deleteWorkspace(WORKSPACE_NAME);
	}

	@Test
	public void createGsUserTest() {
		// La API Rest no proporciona un método para crear/modificar/eliminar un
		// usuario
		fail("Not yet implemented");
	}

	@Test
	public void createGsWorkspaceTest() {
		String name = WORKSPACE_NAME;
		boolean result = geoserverDao.createWorkspace(name);
		RESTWorkspaceList list = geoserverDao.getWorkspaceList();
		boolean encontrado = false;
		for (RESTShortWorkspace workspace : list) {
			String nameWS = workspace.getName();
			if (name.equals(nameWS)) {
				encontrado = true;
				break;
			}
		}
		assertTrue("Wrong value returned", result);
		assertTrue("Created workspace not found", encontrado);
	}

	@Test
	public void createPostgisDatasourceTest() {
		boolean result = geoserverDao.createWorkspace(WORKSPACE_NAME);
		assertTrue("Couldn't create workspace", result);

		result = geoserverDao.createDatastore(WORKSPACE_NAME, DATASTORE_NAME);
		assertTrue("Couldn't create datasource", result);

		// Create a new postgis layer from this datastore
		GsFeatureDescriptor featureDescriptor = new GsFeatureDescriptor();
		GsLayerDescriptor layerDescriptor = new GsLayerDescriptor();
		featureDescriptor.setName(TABLE_NAME);
		featureDescriptor.setTitle(LAYER_TITLE);

		layerDescriptor.setType(GeometryType.POINT);
		result = geoserverDao.publishPostgisLayer(WORKSPACE_NAME,
				DATASTORE_NAME, featureDescriptor, layerDescriptor);
		assertTrue("Couldn't create test layer", result);
	}

	@Test
	public void deletePostgisDatasourceTest() {
		boolean result;

		createPostgisDatasourceTest();

		result = geoserverDao.deletePostgisFeatureType(WORKSPACE_NAME,
				DATASTORE_NAME, TABLE_NAME);
		assertTrue("Couldn't delete test feature type", result);
	}
	
	@Test
	public void publishGeoTIFFTest() {
		createGsWorkspaceTest();
		boolean result;
		
		File geotiff = new File(this.getClass().getResource("/ficheros/raster/prueba_rcgua.tif").getFile());
		String storeName = "testGeotiff_" + System.currentTimeMillis();
		String crs = "EPSG:32719";
		
		result = geoserverDao.publishGeoTIFF(WORKSPACE_NAME, storeName, geotiff, crs);
		assertTrue("Couldn't create a GeoTIFF layer", result);		
	}
	@Test
	public void publishWorldImage() {
		createGsWorkspaceTest();
		boolean result;
		File imageFile = new File(this.getClass().getResource("/ficheros/raster/testWorldImage.zip").getFile());
		
		String storeName = "imageWorld_" + System.currentTimeMillis();
		String crs = "EPSG:32719";
		
		result = geoserverDao.publishWorldImage(WORKSPACE_NAME, storeName, imageFile, crs);
		assertTrue("Couldn't create a WorldImage layer", result);
	}
	
	
}
