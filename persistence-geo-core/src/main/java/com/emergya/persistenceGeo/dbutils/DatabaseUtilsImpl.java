/* DatabaseUtilsImpl.java
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
package com.emergya.persistenceGeo.dbutils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.collections.iterators.EntrySetMapIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.emergya.persistenceGeo.exceptions.DbUtilsException;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
@Component
public class DatabaseUtilsImpl implements IDatabaseUtils {

	@Autowired
	@Qualifier("dataSourceHibernate")
	private DataSource ds;

	@Override
	public List<String> getTableColumns(String tableName) {
		ResultSet rs = null;
		List<String> result = new ArrayList<String>();
		try {
			Connection con = ds.getConnection();
			DatabaseMetaData md = con.getMetaData();
			rs = md.getColumns(null, null, tableName, null);
			while (rs.next()) {
				result.add(rs.getString("COLUMN_NAME"));
			}
			con.close();

		} catch (SQLException e) {
			throw new DbUtilsException("Error changing column names", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();

				} catch (SQLException e) {
					throw new DbUtilsException("Error accessing database", e);
				}
			}
		}
		return result;
	}

	public void changeColumnNames(String tableName, Map<String, String> columns) {
		Set<String> oldColumnNames = columns.keySet();
		Collection<String> newColumnNames = columns.values();
		if (checkClashingColumnNames(oldColumnNames, newColumnNames)) {
			EntrySetMapIterator it = new EntrySetMapIterator(columns);
			while (it.hasNext()) {
				String oldName = (String) it.next();
				String newName = (String) it.getValue();
				try {
					changeColumnName(tableName, oldName, newName);
				} catch (SQLException e) {
					throw new DbUtilsException(
							"Error while change column name. [tableName="
									+ tableName + ", oldColumn= " + oldName
									+ ", newName=" + newName + "]", e);
				}

			}

		} else {
			throw new DbUtilsException(
					"An old column name clash with a new name. [tableName="
							+ tableName + "]");
		}

	}

	public String getColumnType(String tableName, String columnName) {
		ResultSet rs = null;
		String result = null;
		try {
			Connection con = ds.getConnection();
			DatabaseMetaData md = con.getMetaData();
			rs = md.getColumns(null, null, tableName, columnName);
			int i = 0;
			while (rs.next()) {
				i++;
				result = rs.getString("TYPE_NAME");
				if (i > 1) {
					throw new DbUtilsException(
							"Found more than one column when looking for column's type. [tableName="
									+ tableName + ", columnName=" + columnName
									+ "]");
				}
			}
			con.close();

		} catch (SQLException e) {
			throw new DbUtilsException("Error getting type of column", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					throw new DbUtilsException("Error accessing database", e);
				}
			}
		}
		return result;
	}

	private void changeColumnName(String tableName, String oldName,
			String newName) throws SQLException {
		Connection con = ds.getConnection();
		Statement stm = con.createStatement();
		String sql = "ALTER TABLE " + tableName + " RENAME COLUMN " + oldName
				+ " TO " + newName;
		stm.executeUpdate(sql);
		stm.close();
		con.close();

	}

	/**
	 * Check if newColumnNames contains any item of oldColumnNames
	 * 
	 * @param oldColumnNames
	 * @param newColumnNames
	 * @return
	 */
	private boolean checkClashingColumnNames(Collection<String> oldColumnNames,
			Collection<String> newColumnNames) {
		boolean result = true;

		for (String oldName : oldColumnNames) {

			for (String newName : newColumnNames) {
				if (oldName.toUpperCase().equals(newName.toUpperCase())) {
					result = false;
					break;
				}
			}
			if (!result) {
				break;
			}

		}

		return result;
	}

}
