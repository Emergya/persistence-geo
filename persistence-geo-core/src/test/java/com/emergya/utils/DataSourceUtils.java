/* 
 * DataSourceUtils.java
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
package com.emergya.utils;

import java.lang.reflect.Method;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.util.ReflectionUtils;

/**
 * Simple DataSource generator
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
public class DataSourceUtils {

	private Properties dataSourceProperties;

	/**
	 * Genera un nuevo data source con las propiedades
	 * {@link DataSourceUtils#dataSourceProperties}
	 * 
	 * @return {@link BasicDataSource}
	 */
	public DataSource getDataSource() {
		DataSource dataSource = new BasicDataSource();

		// Se escriben las propiedades por reflexion
		for (Object key : dataSourceProperties.keySet()) {
			if (key instanceof String) {
				try {
					String property = (String) key;
					String setter = "set"
							+ property.substring(0, 1).toUpperCase()
							+ property.substring(1);
					Method setterMethod = ReflectionUtils.findMethod(
							BasicDataSource.class, setter, String.class);
					ReflectionUtils.makeAccessible(setterMethod);
					ReflectionUtils.invokeMethod(setterMethod, dataSource,
							dataSourceProperties.getProperty(property));
				} catch (Exception e) {
					// Error al poner la propiedad
				}
			}
		}

		return dataSource;
	}

	public Properties getDataSourceProperties() {
		return dataSourceProperties;
	}

	public void setDataSourceProperties(Properties dataSourceProperties) {
		this.dataSourceProperties = dataSourceProperties;
	}

}
