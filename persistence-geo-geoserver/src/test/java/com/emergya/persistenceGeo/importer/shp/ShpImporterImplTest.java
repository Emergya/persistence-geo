/* ShpImporterImplTest.java
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
package com.emergya.persistenceGeo.importer.shp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.emergya.persistenceGeo.exceptions.ShpImporterException;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:shpTestContext.xml" })
public class ShpImporterImplTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private IShpImporter importer;

	/**
	 * Test method for
	 * {@link com.emergya.persistenceGeo.importer.shp.ShpImporterImpl#checkIfAllFilesExist(java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public void testCheckIfAllFilesExist() {
		ShpImporterImpl importer = new ShpImporterImpl();
		// test a directory
		boolean result = importer.checkIfAllFilesExist(
				"target/test-classes/ficheros", "shapes");
		assertFalse("Se ha devuelto true para un directorio", result);
		// test a directory ending with .shp
		result = importer.checkIfAllFilesExist(
				"target/test-classes/ficheros/shapes", "emptyfolder");
		assertFalse("Se ha devuelto true para un directorio", result);
		// test an incomplete shapefile
		result = importer.checkIfAllFilesExist(
				"target/test-classes/ficheros/shapes", "incomplete");
		assertFalse("se ha devuelto true para un SHP incompleto", result);
		// test a complete shapefile
		result = importer.checkIfAllFilesExist(
				"target/test-classes/ficheros/shapes", "points");
		assertTrue("Se ha devuelto false para un SHP completo", result);
	}

	/**
	 * Test method for
	 * {@link com.emergya.persistenceGeo.importer.shp.ShpImporterImpl#importShpToDb(String, String)}
	 * . .
	 */
	@Test
	public void testImportShpToBdRecreatingTable() {
		boolean result = importer.importShpToDb(
				"target/test-classes/ficheros/shapes/points.shp", "points",
				true);
		assertTrue("Couldn't import shp", result);
	}

	@Test
	public void testImportShpToBdWithoutRecreatingTable() {
		boolean result = importer.importShpToDb(
				"target/test-classes/ficheros/shapes/points.shp", "points",
				false);
		assertFalse("I was expecting that couldn't import the shp", result);
	}

	@Test
	public void testProblemWithShpToBd() {
		thrown.expect(ShpImporterException.class);
		boolean result = importer
				.importShpToDb("target/test-classes/ficheros/shapes/fdsdf.shp",
						"points", true);
		assertTrue("Couldn't import shp", result);

	}

	@Test
	public void testImportAndReproject() {
		boolean result = importer.importShpToDb(
				"target/test-classes/ficheros/shapes/points_23030.shp",
				"points_23030", true);
		assertTrue("Couldn't import shp", result);
		result = importer.importShpToDb(
				"target/test-classes/ficheros/shapes/points_32719.shp",
				"points_32719", true);
		assertTrue("Couldn't import shp", result);
	}

}
