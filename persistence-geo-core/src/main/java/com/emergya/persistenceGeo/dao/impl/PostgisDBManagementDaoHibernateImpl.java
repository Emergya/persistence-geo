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
package com.emergya.persistenceGeo.dao.impl;

import java.math.BigInteger;

import org.hibernate.SessionFactory;
import org.hibernate.exception.GenericJDBCException;
import org.hibernate.exception.SQLGrammarException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.DBManagementDao;
import com.emergya.persistenceGeo.utils.BoundingBox;
import com.emergya.persistenceGeo.utils.GeometryType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.LazyDynaBean;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.jdbc.Work;

/**
 * Implementacion de ExecuterSQL dao para hibernate
 *
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@Repository
public class PostgisDBManagementDaoHibernateImpl extends HibernateDaoSupport implements DBManagementDao {

    private final String getSize = "pg_total_relation_size";
    private final String getSizeText = "pg_size_pretty";

    private static final String ALTER_TABLE_SQL = "ALTER TABLE {0}.\"{1}\" RENAME COLUMN \"{2}\" TO \"{3}\"";
    private static final String GEOMETRY_TYPE_SQL = "select type from geometry_columns where f_table_name = :NAME";
    private static final String DUPLICATE_TABLE_SQL = "CREATE TABLE {0}.\"{1}\" AS SELECT * FROM {0}.\"{2}\"";
    private static final String ADD_TABLE_PK = "ALTER TABLE {0}.\"{1}\" ADD CONSTRAINT \"{1}_pk\" PRIMARY KEY ({2})";
    private static final String UPDATE_GEOMETRY_COLUMN_SQL = "INSERT INTO public.geometry_columns select f_table_catalog, f_table_schema, '%s', f_geometry_column, coord_dimension, srid, type from public.geometry_columns where f_table_name LIKE '%s'";
    private static final String DROP_TABLE_SQL = "DROP TABLE {0}.\"{1}\"";
    private static final String REMOVE_GEOMETRY_COLUMN_SQL = "DELETE FROM public.geometry_columns WHERE f_table_name LIKE '%s'";
    private static final String BBOX_SQL = "select cast(st_extent(geom) as varchar) from {0}.\"{1}\"";
    private static final String BBOX_SQL_GEOCOLUMN = "select cast(st_extent(\"{0}\") as varchar) from {1}.\"{2}\"";
    private static final String SRID_SQL = "select srid from geometry_columns where f_table_name = :NAME";
    private static final String CREATE_TABLE_SQL = "CREATE TABLE {0}.\"{1}\" ({2})";
    private static final String ADD_GEOMETRY_COLUMN_SQL = "SELECT AddGeometryColumn('%s', '%s', 'geom', %d, '%s', %d)";

    @Autowired
    public void init(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
    }

    /**
     * Calcula cuanto ocupa una tabla en la base de datos
     *
     * @param table_name
     * @return espacio en bytes
     */
    @Override
    public long getTableSize(String table_name) {

        String sql = String.format("SELECT %s('%s');", getSize, table_name);

        Long result;
        try {
            result = ((BigInteger) getSession().createSQLQuery(sql).uniqueResult()).longValue();
        } catch (GenericJDBCException ge) {
            result = -1L;
        } catch (SQLGrammarException e) {
            result = -1L;
        }

        return result;
    }

    /**
     * Calcula cuanto ocupa una tabla en la base de datos
     *
     * @param table_name
     * @return resultado de la consulta como cadena de texto
     */
    @Override
    public String getTableSizeText(String table_name) {

        String sql = String.format("SELECT %s(%s('%s'));", getSizeText, getSize, table_name);

        String result;
        try {
            result = (String) getSession().createSQLQuery(sql).uniqueResult();
        } catch (SQLGrammarException e) {
            result = "";
        }

        return result;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.emergya.persistenceGeo.importer.shp.IShpImporter#changeTableColumnName
     * (java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public void changeTableColumnName(String tableName, String oldColumnName,
            String newColumnName) {
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory();
        String schema = sfi.getSettings().getDefaultSchemaName();
        final String sql = MessageFormat.format(ALTER_TABLE_SQL, schema,
                tableName, oldColumnName, newColumnName);
        getSession().doWork(new Work() {

            @Override
            public void execute(Connection connection) throws SQLException {
                Statement stmt = connection.createStatement();
                stmt.execute(sql);
            }
        });
    }

    /**
     * Gets a postgis table geometry type.
     * @param tableName
     * @return 
     */
    @Override
    public GeometryType getTableGeometryType(String tableName) {
        SQLQuery query = getSession().createSQLQuery(GEOMETRY_TYPE_SQL);
        query.setString("NAME", tableName);
        
        String dbType = ((String) query.uniqueResult()).toUpperCase();
        
        
        if (StringUtils.contains(dbType, "POINT")) {
                return GeometryType.POINT;
        } else if (StringUtils.contains(dbType, "LINE")) {
                return GeometryType.LINESTRING;
        } else if (StringUtils.contains(dbType, "POLYGON")) {
                return GeometryType.POLYGON;
        } else {
                throw new IllegalStateException(String.format("Type %s is not a PersistenceGeo supported GeometryType.", dbType));
        }
    }

    /**
     * Gets a Postgis geometry table bounding box.
     *
     * @param tableName
     * @return
     */
    @Override
    public BoundingBox getTableBoundingBox(String tableName) {
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory();
        String schema = sfi.getSettings().getDefaultSchemaName();
        String sql = MessageFormat.format(BBOX_SQL, schema, tableName);
        BoundingBox bbox = calculateBBox(sql, tableName);
        return bbox;
    }

    @Override
    public BoundingBox getTableBoundingBoxGeoColumn(String geocolum,
            String tableName) {
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory();
        String schema = sfi.getSettings().getDefaultSchemaName();
        String sql = MessageFormat.format(BBOX_SQL_GEOCOLUMN, geocolum, schema,
                tableName);
        BoundingBox bbox = calculateBBox(sql, tableName);
        return bbox;
    }

    private BoundingBox calculateBBox(String sql, String tableName) {
        SQLQuery query = getSession().createSQLQuery(sql);

        String result = (String) query.uniqueResult();
        BoundingBox bbox = new BoundingBox();
        if (result == null) { // if layer is empty
            bbox.setMinx(-180);
            bbox.setMiny(-90);
            bbox.setMaxx(180);
            bbox.setMaxy(90);
        } else {
            result = result.replace("BOX", "");
            result = result.replace("(", "").replace(")", "");
            result = result.replace(" ", ",");
            String[] coords = result.split(",");
            if (coords[0].equals(coords[2]) && coords[1].equals(coords[3])) { // layer
                // store
                // one
                // point
                bbox.setMinx((Double.valueOf(coords[0]) / 2) - 0.75);
                bbox.setMiny((Double.valueOf(coords[1]) / 2) - 0.75);
                bbox.setMaxx((Double.valueOf(coords[2]) / 2) + 0.75);
                bbox.setMaxy((Double.valueOf(coords[3]) / 2) + 0.75);
            } else {
                bbox.setMinx(Double.valueOf(coords[0]));
                bbox.setMiny(Double.valueOf(coords[1]));
                bbox.setMaxx(Double.valueOf(coords[2]));
                bbox.setMaxy(Double.valueOf(coords[3]));
            }
        }

        query = getSession().createSQLQuery(SRID_SQL);
        query.setString("NAME", tableName);
        Integer srid = (Integer) query.uniqueResult();

        bbox.setSrs("EPSG:" + srid);

        return bbox;
    }

    @Override
    public boolean tableExists(String schema, String tableName) {
        String sql = "select * from pg_tables where schemaname='" + schema
                + "' and tablename = '" + tableName + "';";

        SQLQuery query = getSession().createSQLQuery(sql);
        return query.uniqueResult() != null;
    }

    @Override
    public void duplicateLayerTable(final String sourceTable,
            final String destinationTable) {
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory();
        final String schema = sfi.getSettings().getDefaultSchemaName();
        final String copyTableSql = MessageFormat.format(DUPLICATE_TABLE_SQL,
                schema, destinationTable, sourceTable);

        final String updateGeometryColumnSql = String.format(
                UPDATE_GEOMETRY_COLUMN_SQL, destinationTable, sourceTable);

        getSession().doWork(new Work() {

            @Override
            public void execute(Connection connection) throws SQLException {
                Statement stmt = connection.createStatement();
                stmt.execute(copyTableSql);
                stmt.execute(updateGeometryColumnSql);
                ResultSet pksRS = connection.getMetaData().getPrimaryKeys(null,
                        schema, sourceTable);

                if (pksRS.first()) {
                    String primaryKey = pksRS.getString("COLUMN_NAME");

                    String createPkSql = MessageFormat.format(ADD_TABLE_PK, schema,
                            destinationTable, primaryKey);

                    stmt.execute(createPkSql);
                }
            }
        });

        getSession().flush();
    }

    @Override
    public void deleteLayerTable(String tableName) {
        SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory();
        String schema = sfi.getSettings().getDefaultSchemaName();
        final String dropTableSql = MessageFormat.format(DROP_TABLE_SQL,
                schema, tableName);

        final String deleteGeometryColumnSql = String.format(
                REMOVE_GEOMETRY_COLUMN_SQL, tableName);

        getSession().doWork(new Work() {

            @Override
            public void execute(Connection connection) throws SQLException {
                Statement stmt = connection.createStatement();
                stmt.execute(dropTableSql);
                stmt.execute(deleteGeometryColumnSql);
            }
        });
    }

    /**
     * Creates a new database table with the columns specified by
     * <code>columns</code>. Also add and register a new geometry column «geom»
     * in the table.
     *
     * @param tableName name of the table.
     * @param columns list of columns definitions.
     * @param srsCode spatial system reference code.
     * @param geometryType geometry type (LINE, POINT or POLYGON).
     * @return <code>true</code> if successful, <code>false</code> if not.
     */
    private boolean createLayerTable(String tableName, List<DynaBean> columns,
            int srsCode, GeometryType geometryType) {
        boolean created = true;

        if (columns == null || columns.size() == 0) {
            throw new IllegalArgumentException(
                    "No se puede crear una tabla sin columnas. Indique las columnas a crear.");
        }

        StringBuilder columnsSQL = new StringBuilder();
        for (int i = 0; i < columns.size(); i++) {
            DynaBean col = columns.get(i);
            columnsSQL.append(col.get("name")).append(" ")
                    .append(col.get("type"));
            if (col.get("primaryKey") != null
                    && ((Boolean) col.get("primaryKey")) == true) {
                columnsSQL.append(" PRIMARY KEY");
            }
            if (i != columns.size() - 1) {
                columnsSQL.append(", ");
            }
        }

        SessionFactoryImplementor sfi = (SessionFactoryImplementor) getSessionFactory();
        String schema = sfi.getSettings().getDefaultSchemaName();
        final String sql = MessageFormat.format(CREATE_TABLE_SQL, schema,
                tableName, columnsSQL.toString());

        final String addGeometryColumnSql = String.format(
                ADD_GEOMETRY_COLUMN_SQL, schema, tableName, srsCode,
                geometryType, 2);

        try {

            getSession().doWork(new Work() {

                @Override
                public void execute(Connection connection) throws SQLException {
                    Statement stmt = connection.createStatement();
                    stmt.execute(sql);
                    stmt.execute(addGeometryColumnSql);
                }
            });

        } catch (Exception e) {
            logger.error(String.format("Error creando la tabla \"%s\" de tipo %s", tableName, GEOMETRY_TYPE_SQL), e);
            created = false;
        }

        return created;
    }

    @Override
    public boolean createLayerTable(String tableName, int srsCode, GeometryType geometryType) {
        List<DynaBean> columns = new ArrayList<DynaBean>();
        DynaBean fieldId = new LazyDynaBean();
        fieldId.set("name", "id");
        fieldId.set("type", "serial");
        fieldId.set("primaryKey", true);

        columns.add(fieldId);

        DynaBean fieldDesc = new LazyDynaBean();
        fieldDesc.set("name", "descripcion");
        fieldDesc.set("type", "character varying(512)");
        fieldDesc.set("primaryKey", false);
        columns.add(fieldDesc);

        return createLayerTable(tableName, columns, srsCode, geometryType);
    }

}
