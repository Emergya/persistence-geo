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
package com.emergya.persistenceGeo.dao;

import com.emergya.persistenceGeo.utils.BoundingBox;
import com.emergya.persistenceGeo.utils.GeometryType;

/**
 * Interfaz para la ejecution de sentencias sql en database
 *
 *
 *
 */
public interface DBManagementDao {

    /**
     * Calcula cuanto ocupa una tabla en la base de datos
     *
     * @param table_name
     * @return espacio en bytes
     */
    public long getTableSize(String table_name);

    /**
     * Calcula cuanto ocupa una tabla en la base de datos
     *
     * @param table_name
     * @return resultado de la consulta como cadena de texto
     */
    public String getTableSizeText(String table_name);

    public void changeTableColumnName(String tableName, String oldColumnName,
	    String newColumnName);

    public GeometryType getTableGeometryType(String tableName);

    public BoundingBox getTableBoundingBox(String tableName);

    /**
     * Copies the data from the source table into a new table.
     *
     * @param sourceTable
     * @param tableName
     */
    public void duplicateLayerTable(String sourceTable, String tableName);

    /**
     * Drops the table backing a given layer. Use with extreme caution,
     * obviously.
     *
     * @param tableName
     */
    public void deleteLayerTable(String tableName);

    /**
     * Create a new table with a geometry column of the given
     * {@link GeometryType} and SRS code.
     *
     * @param tableName
     * @param srsCode
     * @param geometryType
     * @return <code>true</code> if the table could be created,
     * <code>false</code> if not.
     */
    public boolean createLayerTable(String tableName, int srsCode,
	    GeometryType geometryType);

    public BoundingBox getTableBoundingBoxGeoColumn(String geoColumnName, String tableName);

    public boolean tableExists(String ohiggins, String tableName);
}
