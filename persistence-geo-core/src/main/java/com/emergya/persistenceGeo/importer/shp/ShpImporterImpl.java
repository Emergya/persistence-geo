/* ShpImporterImpl.java
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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emergya.persistenceGeo.exceptions.ShpImporterException;
import com.emergya.persistenceGeo.utils.GeographicDatabaseConfiguration;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
@Service
public class ShpImporterImpl implements IShpImporter {
	private static final String[] EXTENSIONS_TO_CHECK = { ".shp", ".shx",
			".dbf" };
	private static final Log LOG = LogFactory.getLog(ShpImporterImpl.class);
	private static final String BASH_COMMAND = "bash";
	private static final String BASH_COMMAND_FIRST_ARGUMENT = "-c";
	private static final String DROP_TABLE_OPTION = "-d";
	private static final String SHP2PGSQL_COMMAND = "shp2pgsql {0} -s {1}{2} -g geom -k -i -I {3} "
			+ "{4}.{5} | psql -h {6} -p {7} -d {8} -U {9}";
	private static final String GUESS_PROJECTION_COMMAND = "guessEPSG.py";

	public ShpImporterImpl() {
		checkForCommandLineUtils();
	}

	@Autowired(required = false)
	private DataSource dataSource;

	@Autowired
	private GeographicDatabaseConfiguration dbConfig;

	public void setDbConfig(GeographicDatabaseConfiguration config) {
		this.dbConfig = config;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emergya.persistenceGeo.importer.shp.IShpImporter#checkIfAllFilesExist
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkIfAllFilesExist(String folder, String filename) {
		return checkForExtensions(folder, filename, EXTENSIONS_TO_CHECK);
	}

	private boolean checkIfPrjFileExist(String folder, String filename) {
		return checkForExtensions(folder, filename, new String[] { ".shp" });
	}

	private boolean checkForExtensions(String folder, String filename,
			String[] extensions) {
		String basename = folder;
		if (!folder.endsWith(File.separator)) {
			basename += File.separator;
		}
		// Check if directory exists && can read
		File fFolder = new File(basename);
		if (!fFolder.exists() || !fFolder.isDirectory() || !fFolder.canRead()) {
			return false;
		}
		// Look for mandatory files.
		for (String extension : extensions) {
			File file = new File(basename + filename + extension);
			if (!file.exists() || !file.canRead() || !file.isFile()) {
				LOG.info("SHP verificacion return false. File "
						+ file.getAbsolutePath()
						+ " not found, not readable or is a directory.");
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emergya.persistenceGeo.importer.shp.IShpImporter#importShpToDb(java
	 * .lang.String, java.lang.String, boolean)
	 */
	@Override
	public boolean importShpToDb(String pathToShp, String tableName,
			boolean dropExistingTable) {
		boolean result = true;
		String srcProjection = "";
		Runtime rt = Runtime.getRuntime();
		if (pathToShp == null || !pathToShp.endsWith(".shp")) {
			throw new IllegalArgumentException(
					"pathToShp does not end with .shp");
		}
		int lastIndexOfSlash = pathToShp.lastIndexOf(File.separatorChar);
		int lastIndexOfShp = pathToShp.lastIndexOf('.');
		String path = pathToShp.substring(0, lastIndexOfSlash);
		String file = pathToShp.substring(lastIndexOfSlash + 1, lastIndexOfShp);
		boolean exists = checkIfAllFilesExist(path, file);
		if (!exists) {
			throw new ShpImporterException(
					"Not all mandatory shape file components could be found");
		}

		// If exists a .prj file try to guess the EPSG code. If not code found
		// defaults to EPSG:4326
		if (checkIfPrjFileExist(path, file)) {
			String prjPath = pathToShp.substring(0, pathToShp.lastIndexOf('.'))
					+ ".prj";
			try {
				Process proc = rt.exec(new String[] { GUESS_PROJECTION_COMMAND,
						prjPath });
				BufferedReader standarStream = new BufferedReader(
						new InputStreamReader(proc.getInputStream()));

				int projStatus = proc.waitFor();
				String line;
				if (projStatus == 0) {
					while ((line = standarStream.readLine()) != null) {
						if (!"EPSG:-1".equals(line)) {
							srcProjection = line.split(":")[1] + ":";
						}
					}
				} else {
					if (LOG.isWarnEnabled()) {
						LOG.warn(GUESS_PROJECTION_COMMAND + " return code was "
								+ projStatus);
					}
					if (LOG.isDebugEnabled()) {
						BufferedReader errorStream = new BufferedReader(
								new InputStreamReader(proc.getErrorStream()));
						while ((line = errorStream.readLine()) != null) {
							LOG.debug(GUESS_PROJECTION_COMMAND + ": " + line);
						}
					}

				}

			} catch (IOException e) {
				if (LOG.isErrorEnabled()) {
					LOG.error("guessEPSG.py not found. Please put this file "
							+ " in the path");
				}
			} catch (InterruptedException e) {
				LOG.error(GUESS_PROJECTION_COMMAND + " thread interrupted", e);
			}

		}

		File shp = new File(pathToShp);
		String dropTableParameter = "";
		if (dropExistingTable) {
			dropTableParameter = DROP_TABLE_OPTION;
		}

		String command = MessageFormat.format(SHP2PGSQL_COMMAND,
				dropTableParameter, srcProjection, dbConfig.getDestSrid(),
				shp.getAbsolutePath(), dbConfig.getSchema(), tableName,
				dbConfig.getPostgresHost(), dbConfig.getPostgresPort(),
				dbConfig.getDatabaseName(), dbConfig.getPostgresUser());
		if (LOG.isDebugEnabled()) {
			LOG.debug("SHPIMPORTER Command: " + command);
		}
		try {
			Process proc = rt.exec(
					new String[] { BASH_COMMAND, BASH_COMMAND_FIRST_ARGUMENT,
							command },
					new String[] { "PGPASSWORD="
							+ dbConfig.getPostgresPassword() });

			BufferedReader standarStream = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));

			BufferedReader errorStream = new BufferedReader(
					new InputStreamReader(proc.getErrorStream()));

			String line;
			while ((line = standarStream.readLine()) != null) {
				if (line.contains("ROLLBACK")) {
					result = false;
				}
				if (LOG.isDebugEnabled()) {
					LOG.debug("SHPIMPORTER standar output: " + line);
				}
			}

			while ((line = errorStream.readLine()) != null) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("SHPIMPORTER error output: ");
				}
			}
			standarStream.close();
			errorStream.close();
			int exitStatus = proc.waitFor();

			// Am I checking the real subcommand parameter exit status or only
			// bash command exit status?
			if (exitStatus != 0) {
				throw new ShpImporterException(
						"Exit status is not 0 after executing " + command);
			}
		} catch (IOException e) {
			throw new ShpImporterException(
					"IOExcption thrown while executing SHP import", e);
		} catch (InterruptedException e) {
			throw new ShpImporterException(
					"Another thread interrumped this when it "
							+ " was waiting for SHP import command ending.", e);
		}

		return result;

	}

	private void checkForCommandLineUtils() {
		Runtime runtime = Runtime.getRuntime();
		Process proc;
		try {
			if (LOG.isTraceEnabled()) {
				LOG.trace("Checking psql command line tool existence");
			}

			proc = runtime.exec(new String[] { "psql", "--help" });
			int returnValue = proc.waitFor();
			if (returnValue != 0) {
				throw new ShpImporterException(
						"psql command line tool not found"
								+ "Please, check if postgresql-client package is installed.");
			}
		} catch (Exception e) {
			throw new ShpImporterException(
					"psql command line tool not found. "
							+ "Please, check if postgresql-client package is installed.",
					e);
		}
		try {
			if (LOG.isTraceEnabled()) {
				LOG.trace("Checking shp2pgsql command line tool existence");
			}

			proc = runtime.exec("shp2pgsql");
			int returnValue = proc.waitFor();
			if (returnValue != 0) {
				throw new ShpImporterException(
						"shp2pgsql command line tool not found"
								+ "Please, check if postgis package is installed.");
			}
		} catch (Exception e) {
			throw new ShpImporterException(
					"shp2pgsql command line tool not found. "
							+ "Please, check if postgis package is installed.",
					e);
		}

	}
}
