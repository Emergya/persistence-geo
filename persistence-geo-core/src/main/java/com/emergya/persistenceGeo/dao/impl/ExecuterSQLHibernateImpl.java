/*
 * ExecuterSQLHibernateImpl.java
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
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.dao.impl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.ExecuterSQL;

/**
 * Implementacion de ExecuterSQL dao para hibernate
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@Repository
public class ExecuterSQLHibernateImpl extends HibernateDaoSupport implements ExecuterSQL{
	
	@Autowired
	public void init(SessionFactory sessionFactory){
		setSessionFactory(sessionFactory);
	}
	
	/**
	 * Ejecuta el sql pasado como argumento
	 */
	public void execute(String sql){
		getSession().createSQLQuery(sql).executeUpdate();
	}
	
	/**
	 * Ejecuta el sql pasado como argumento
	 * 
	 * @return resultado de la consulta
	 */
	public Object uniqueResult(String sql){
		return getSession().createSQLQuery(sql).uniqueResult();
	}
	
	/**
	 * Ejecuta el sql pasado como argumento
	 * 
	 * @return resultado de la consulta
	 */
	@SuppressWarnings("rawtypes")
	public List list(String sql){
		return getSession().createSQLQuery(sql).list();
	}

}
