/*
 * 
 * 
 * Copyright (C) 2012
 * 
 * This file is part of Proyecto persistenceGeo
 * 
 * This software is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 * As a special exception, if you link this library with other files to produce
 * an executable, this library does not by itself cause the resulting executable
 * to be covered by the GNU General Public License. This exception does not
 * however invalidate any other reasons why the executable file might be covered
 * by the GNU General Public License.
 * 
 * 
 */
package com.emergya.persistenceGeo.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.DBManagementDao;
import com.emergya.persistenceGeo.service.DBManagementService;
import com.emergya.persistenceGeo.utils.BoundingBox;
import com.emergya.persistenceGeo.utils.GeometryType;

/**
 * Database access service
 */
@Service
@Transactional(value = "multiSIRDatabaseTransactionManager")
public class DBManagementServiceImpl implements DBManagementService {

	@Resource
	private DBManagementDao dbManagementDao;

	@Override
	public long getTableSize(String table_name) {
		return dbManagementDao.getTableSize(table_name);
	}

	@Override
	public String getTableSizeText(String table_name) {
		return dbManagementDao.getTableSizeText(table_name);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void changeTableColumnName(String tableName, String oldColumnName,
			String newColumnName) {
		dbManagementDao.changeTableColumnName(tableName, oldColumnName, newColumnName);
	}
	
	/**
	 * Gets a postgis table geometry type.
	 * 
	 * @param tableName
	 * @return
	 */
	@Override
	public GeometryType getTableGeometryType(String tableName) {
		return dbManagementDao.getTableGeometryType(tableName);
	}

	/**
	 * Gets a Postgis geometry table bounding box.
	 *
	 * @param tableName
	 * @return
	 */
	@Override
	public BoundingBox getTableBoundingBox(String tableName) {
		return dbManagementDao.getTableBoundingBox(tableName);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean createLayerTable(String tableName, int srsCode,
			GeometryType geometryType) {
		return dbManagementDao.createLayerTable(tableName, srsCode, geometryType);
	}

	@Override
	public boolean tableExists(String ohiggins, String tableName) {
		return dbManagementDao.tableExists(ohiggins, tableName);
	}

	@Override
	public BoundingBox getTableBoundingBoxGeoColumn(String geoColumnName,
			String tableName) {
		return dbManagementDao.getTableBoundingBoxGeoColumn(geoColumnName, tableName);
	}	
}
