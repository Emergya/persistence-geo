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
public class GsRestApiConfigurationResumeImpl implements GsRestApiConfigurationResume {

	/** Log */
	private static Log LOG = LogFactory
			.getLog(GsRestApiConfigurationResumeImpl.class);

	public String serverUrl;
	public String adminUsername;
	public String adminPassword;

	public GsRestApiConfigurationResumeImpl() {
		super();
	}

	public GsRestApiConfigurationResumeImpl(String serverUrl, String adminUsername,
			String adminPassword) {
		super();
		this.serverUrl = serverUrl;
		this.adminUsername = adminUsername;
		this.adminPassword = adminPassword;
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

	public Properties getPropertiesGeoserverRest(String dbName,
			String jndiReferenceName) {
		Properties p = new Properties();

		try {
			InputStream inStream = GsRestApiConfigurationResumeImpl.class
					.getResourceAsStream("/geoserver.properties");
			p.load(inStream);
		} catch (Exception e) {
			LOG.error("Error al obtener las propiedades del geoserver : " + e);
		}

		return p;
	}
}
