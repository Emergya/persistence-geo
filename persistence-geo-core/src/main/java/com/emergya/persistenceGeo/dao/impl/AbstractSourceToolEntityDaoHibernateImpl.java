/*
 * AbstractSourceToolEntityDaoHibernateImpl.java. Copyright (C) 2013. This file is part of persistenceGeo project.
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

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.SourceToolEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractSourceToolEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Dao implementation for source
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@SuppressWarnings("unchecked")
@Repository
public class AbstractSourceToolEntityDaoHibernateImpl extends GenericHibernateDAOImpl<AbstractSourceToolEntity, Long> implements SourceToolEntityDao{

	@Resource
	private Instancer instancer;

	@Autowired
    public void init(SessionFactory sessionFactory) {
        super.init(sessionFactory);
		this.persistentClass = (Class<AbstractSourceToolEntity>) instancer.createSourceToolEntity().getClass();
    }
}
