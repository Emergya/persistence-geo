/* 
 * AbstractInitDatabase.java
 * 
 * Copyright (C) 2011
 * 
 * This file is part of Proyecto persistenceGeo
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
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.dao.impl;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.impl.SessionFactoryImpl;
import org.junit.Assert;

import com.emergya.persistenceGeo.dao.ExecuterSQL;
import com.emergya.utils.SQLFileutils;


/**
 * Test padre encargado de inicializar  los datos necesarios para el sistema al crear o actualizar el esquema
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public abstract class AbstractInitDatabase{

	private static final Log LOG = LogFactory.getLog(AbstractInitDatabase.class);
	private static final String HBM2DDL = "hibernate.hbm2ddl.auto";
	private static final String HBM2DDL_create = "create";
	private static final String SQL_EXCLUDED = "create.sql";
	private static final String SQL_TEST = "authorities.sql";
	private static final String sqlDirectory = "target/classes/sql";
	private static final String sqlUniques = "target/classes/sql_uniques";
	
	@Resource
	private SessionFactory sessionFactory;
	
	@Resource
	private ExecuterSQL executerSQL;
	
	public void initDatabase() {
		try {
			if(isNeededUpdate()){
				String[] sqls = SQLFileutils.getSubDirectories(sqlDirectory);
				if(sqls != null){
					for (String file : sqls) {
						if(!file.equals(SQL_EXCLUDED)
								&& !file.equals(SQL_TEST)){
							try{ 
								executerSQL.execute(SQLFileutils.getSqlString(sqlDirectory
									+ File.separator + file));
							}catch (Exception e) {
								LOG.error("Error  al ejecutar un sql:\n", e);
							}
						}
					}
				}
				sqls = SQLFileutils.getSubDirectories(sqlUniques);
				if(sqls != null){
					for (String file : sqls) {
						try{ 
							LOG.info("Resultado : " + executerSQL.uniqueResult(SQLFileutils.getSqlString(sqlUniques
								+ File.separator + file)));
						}catch (Exception e) {
							LOG.error("Error  al ejecutar un sql:\n", e);
						}
					}
				}
			}
		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
	}
	
	private boolean isNeededUpdate(){
		boolean ret = false;
		if(sessionFactory instanceof SessionFactoryImpl
				&& HBM2DDL_create.equals(((SessionFactoryImpl)sessionFactory).getProperties().getProperty(HBM2DDL))){
			try{ 
				executerSQL.execute(SQLFileutils.getSqlString(sqlDirectory
					+ File.separator + SQL_TEST));
				ret = true;
			}catch (Exception e) {
			}
		}
		return ret;
	}

}
