/* 
 * GeoNetworkDaoImpl.java
 * 
 * Copyright (C) 2013
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
 * Authors:: Alejnadro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.dao.impl;

import it.geosolutions.geonetwork.GNClient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.emergya.persistenceGeo.dao.MetadataDao;

/**
 * Dao connector to store metadata
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
public class GeoNetworkDaoImpl implements MetadataDao {
	
	private static final Log LOG = LogFactory
			.getLog(GeoNetworkDaoImpl.class);
	
    private static final String gnServiceURL = "http://localhost:8080/geonetwork";
    private static final String gnUsername = "admin";
    private static final String gnPassword = "admin";

    // Create a GeoNetwork client pointing to the GeoNetwork service
    GNClient client = new GNClient(gnServiceURL);

    // Perform a login into GN
    boolean logged = client.login(gnUsername, gnPassword);
}
