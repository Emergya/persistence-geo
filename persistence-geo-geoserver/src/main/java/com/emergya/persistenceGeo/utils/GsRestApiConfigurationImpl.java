/* GsRestApiConfigurationImpl.java
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

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.emergya.persistenceGeo.bean.RegionBean;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 *
 */
public class GsRestApiConfigurationImpl implements GsRestApiConfiguration {

	/** Log */
	private static Log LOG = LogFactory
			.getLog(GsRestApiConfigurationImpl.class);

	public String serverUrl;
	public String adminUsername;
	public String adminPassword;
	public String dbHost;
	public int dbPort;
	public String dbName;
	public String dbSchema;
	public String dbUser;
	public String dbPassword;
	public String dbType;
	public String jndiReferenceName;
	public String datasourceType;

	public GsRestApiConfigurationImpl() {
		super();
	}

	public GsRestApiConfigurationImpl(String serverUrl, String adminUsername,
			String adminPassword, String dbHost, int dbPort, String dbSchema,
			String dbName, String dbUser, String dbPassword, String dbType,
			String jndiReferenceName, String datasourceType, RegionBean region) {
		super();
		this.serverUrl = serverUrl;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
		this.dbHost = dbHost;
		this.dbPort = dbPort;
		this.dbSchema = dbSchema;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
		this.dbType = dbType;
		this.datasourceType = datasourceType;
		this.inicializaMultiConfiguracion(region,
				getPropertiesGeoserverRest(dbName, jndiReferenceName));
	}

	/**
	 * @return the datasourceType
	 */
	public String getDatasourceType() {
		return datasourceType;
	}

	/**
	 * @param datasourceType
	 *            the datasourceType to set
	 */
	public void setDatasourceType(String datasourceType) {
		this.datasourceType = datasourceType;
	}

	/**
	 * @return the dbType
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * @param dbType
	 *            the dbType to set
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	/**
	 * @return the jndiReferenceName
	 */
	public String getJndiReferenceName() {
		return jndiReferenceName;
	}

	/**
	 * @param jndiReferenceName
	 *            the jndiReferenceName to set
	 */
	public void setJndiReferenceName(String jndiReferenceName) {
		this.jndiReferenceName = jndiReferenceName;
	}

	/**
	 * @param serverUrl
	 *            the serverUrl to set
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	/**
	 * @param adminUsername
	 *            the adminUsername to set
	 */
	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}

	/**
	 * @param adminPassword
	 *            the adminPassword to set
	 */
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emergya.persistenceGeo.metaModel.GsRestApiConfiguration#getServerUrl
	 * ()
	 */
	@Override
	public String getServerUrl() {
		return this.serverUrl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emergya.persistenceGeo.metaModel.GsRestApiConfiguration#getAdminUsername
	 * ()
	 */
	@Override
	public String getAdminUsername() {
		return this.adminUsername;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emergya.persistenceGeo.metaModel.GsRestApiConfiguration#getAdminPassword
	 * ()
	 */
	@Override
	public String getAdminPassword() {
		return this.adminPassword;
	}

	@Override
	public String getDbHost() {
		return this.dbHost;
	}

	@Override
	public int getDbPort() {
		return this.dbPort;
	}

	@Override
	public String getDbName() {
		return this.dbName;
	}

	@Override
	public String getDbSchema() {
		return this.dbSchema;
	}

	@Override
	public String getDbUser() {
		return this.dbUser;
	}

	@Override
	public String getDbPassword() {
		return this.dbPassword;
	}

	/**
	 * @param dbHost
	 *            the dbHost to set
	 */
	public void setDbHost(String dbHost) {
		this.dbHost = dbHost;
	}

	/**
	 * @param dbPort
	 *            the dbPort to set
	 */
	public void setDbPort(int dbPort) {
		this.dbPort = dbPort;
	}

	/**
	 * @param dbName
	 *            the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @param dbSchema
	 *            the dbSchema to set
	 */
	public void setDbSchema(String dbSchema) {
		this.dbSchema = dbSchema;
	}

	/**
	 * @param dbUser
	 *            the dbUser to set
	 */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}

	/**
	 * @param dbPassword
	 *            the dbPassword to set
	 */
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}

	public Properties getPropertiesGeoserverRest(String dbName,
			String jndiReferenceName) {
		Properties p = new Properties();

		try {
			InputStream inStream = GsRestApiConfigurationImpl.class
					.getResourceAsStream("/geoserver.properties");
			p.load(inStream);
		} catch (Exception e) {
			LOG.error("Error al obtener las propiedades del geoserver : " + e);
			this.setDbName(dbName);
			this.setJndiReferenceName(jndiReferenceName);
		}

		return p;
	}

	public void inicializaMultiConfiguracion(RegionBean region, Properties p) {

		if (p != null && region != null && region.getPrefix_wks() != null) {
			this.setDbName(String.valueOf(p.getProperty(("geoserver.db.name"
					.concat(".").concat(region.getPrefix_wks().toLowerCase())))));
			this.setJndiReferenceName(String.valueOf(p
					.getProperty(("geoserver.db.jndiReferenceName".concat(".")
							.concat(region.getPrefix_wks().toLowerCase())))));
		}
	}
}
