/* GeographicDatabaseConfiguration.java
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
package com.emergya.persistenceGeo.utils;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public class GeographicDatabaseConfiguration {
	private String schema;
	private String destSrid;
	private String geomColumn;
	private String postgresHost;
	private String postgresPort;
	private String postgresUser;
	private String postgresPassword;
	private String databaseName;

	/**
	 * @return the schema
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * @param schema
	 *            the schema to set
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * @return the destSrid
	 */
	public String getDestSrid() {
		return destSrid;
	}

	/**
	 * @param destSrid
	 *            the destSrid to set
	 */
	public void setDestSrid(String destSrid) {
		this.destSrid = destSrid;
	}

	/**
	 * @return the geomColumn
	 */
	public String getGeomColumn() {
		return geomColumn;
	}

	/**
	 * @param geomColumn
	 *            the geomColumn to set
	 */
	public void setGeomColumn(String geomColumn) {
		this.geomColumn = geomColumn;
	}

	/**
	 * @return the postgresHost
	 */
	public String getPostgresHost() {
		return postgresHost;
	}

	/**
	 * @param postgresHost
	 *            the postgresHost to set
	 */
	public void setPostgresHost(String postgresHost) {
		this.postgresHost = postgresHost;
	}

	/**
	 * @return the postgresPort
	 */
	public String getPostgresPort() {
		return postgresPort;
	}

	/**
	 * @param postgresPort
	 *            the postgresPort to set
	 */
	public void setPostgresPort(String postgresPort) {
		this.postgresPort = postgresPort;
	}

	/**
	 * @return the postgresUser
	 */
	public String getPostgresUser() {
		return postgresUser;
	}

	/**
	 * @param postgresUser
	 *            the postgresUser to set
	 */
	public void setPostgresUser(String postgresUser) {
		this.postgresUser = postgresUser;
	}

	/**
	 * @return the postgresPassword
	 */
	public String getPostgresPassword() {
		return postgresPassword;
	}

	/**
	 * @param postgresPassword
	 *            the postgresPassword to set
	 */
	public void setPostgresPassword(String postgresPassword) {
		this.postgresPassword = postgresPassword;
	}

	/**
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @param databaseName
	 *            the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

}
