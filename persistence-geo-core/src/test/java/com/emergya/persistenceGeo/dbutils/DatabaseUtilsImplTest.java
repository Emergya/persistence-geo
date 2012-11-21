/* DatabaseUtilsImplTest.java
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

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.collections.map.HashedMap;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.exceptions.DbUtilsException;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dbUtilsTestContext.xml" })
public class DatabaseUtilsImplTest {

	@Autowired
	private IDatabaseUtils dbUtils;
	@Autowired
	private DataSource ds;
	private boolean created = false;

	@Before
	public void init() throws SQLException {
		Connection con = ds.getConnection();
		try {
			after();
		} catch (Exception e) {
			
		}
		String sql = "CREATE TABLE test_table(col1 varchar(20) primary key, col2 varchar(30), "
				+ " col3 varchar(15))";
		con.createStatement().executeUpdate(sql);
	}
	
	@After
	public void after() throws SQLException {
		Connection con = ds.getConnection();
		String sql = "DROP TABLE test_table";
		con.createStatement().executeUpdate(sql);
	}
 	


	/**
	 * Test method for
	 * {@link com.emergya.persistenceGeo.dbutils.DatabaseUtilsImpl#getTableColumns(java.lang.String)}
	 * .
	 * @throws SQLException 
	 */
	@Test
	public final void testGetTableColumns() throws SQLException {
		
		
		List<String> columns = dbUtils.getTableColumns("test_table");
		assertEquals("Wrong number of columns returned", 3, columns.size());
		String columnPrefix = "COL";
		for (int i = 0; i < columns.size(); i++) {
			assertEquals("Wrong column name returned", columnPrefix + (i + 1),
					columns.get(i).toUpperCase());
		}
	}

	@Test
	public final void testChangeName() throws SQLException {		

		Map<String, String> columns = new HashMap<String, String>();
		columns.put("COL1", "NEW_COL1");
		columns.put("COL2", "NEW_COL2");
		columns.put("COL3", "NEW_COL3");

		dbUtils.changeColumnNames("test_table", columns);
		List<String> newNames = dbUtils.getTableColumns("test_table");
		assertEquals("Wrong number of columns", 3, newNames.size());

		for (int i = 0; i < newNames.size(); i++) {
			assertEquals("Wrong new column name found", "NEW_COL" + (i + 1),
					newNames.get(i).toUpperCase());
		}
	}
	
	@Test(expected=DbUtilsException.class)
	public final void testClashColumns() {
		Map<String, String> columns = new HashMap<String, String>();
		columns.put("COL1", "NEW_COL1");
		columns.put("COL2", "COL2");
		columns.put("COL3", "NEW_COL3");

		dbUtils.changeColumnNames("test_table", columns);
	}

}
