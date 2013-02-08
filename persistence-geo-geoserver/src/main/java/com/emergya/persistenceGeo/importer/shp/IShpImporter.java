/* IShpImporter.java
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

import com.emergya.persistenceGeo.exceptions.ShpImporterException;
import com.emergya.persistenceGeo.utils.GeographicDatabaseConfiguration;

/**
 * An <code>IShpImporter</code> can read a Shapefile set of files and import
 * these into a database.
 * 
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public interface IShpImporter {
	/**
	 * Check if all required files of the Shapefile exists in the directory. At
	 * least must exists the following files:
	 * <ul>
	 * <li><code>filename</code><strong>.shp</strong>. Shape format; the feature
	 * geometry itself.</li>
	 * <li><code>filename</code><strong>.dbf</strong>. Attribute format;
	 * columnar attributes for each shape, in dBase IV format.</li>
	 * <li><code>filename</code><strong>.shx</strong>. Shape index format; a
	 * positional index of the feature geometry to allow seeking forwards and
	 * backwards quickly.</li>
	 * </ul>
	 * 
	 * <h1>WARNING:</h1> This function only looks for lowercase file extensions.
	 * 
	 * @param folder
	 *            where is located the shapefile.
	 * @param filename
	 *            prefix of the filename (name without extension).
	 * @return
	 */
	public boolean checkIfAllFilesExist(String folder, String filename);

	/**
	 * Read a shapefile and import its content into tableName. It uses the
	 * provided {@link GeographicDatabaseConfiguration}
	 * 
	 * @param filePath
	 *            .shp file path
	 * @param tableName
	 *            table where import file contents
	 * @param dropExistingTable
	 *            <code>true</code> if should drop existing table with
	 *            <tableName> before import.
	 * @return <code>true</code> if no errors are reported.
	 * @throws ShpImporterException
	 *             if can not complete the import process.
	 */
	public boolean importShpToDb(String filePath, String tableName,
			boolean dropExistingTable);


}
