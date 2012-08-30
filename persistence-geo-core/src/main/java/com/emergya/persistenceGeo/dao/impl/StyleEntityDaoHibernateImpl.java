/*
 * StyleEntityDaoHibernateImpl.java
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
 * Authors:: Moisés Arcos Santiago (mailto:marcos@emergya.com)
 */
package com.emergya.persistenceGeo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dao.StyleEntityDao;
import com.emergya.persistenceGeo.model.StyleEntity;

/**
 * Style DAO Hibernate Implementation
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
@Repository("styleEntityDao")
public class StyleEntityDaoHibernateImpl extends GenericHibernateDAOImpl<StyleEntity, Long> implements StyleEntityDao {

	/**
	 * Create a new style in the system
	 * 
	 * @param <code>style</code>
	 * 
	 * @return Entity from the created style
	 */
	public StyleEntity createStyle(String style) {
		StyleEntity styleEntity = new StyleEntity(style);
		getHibernateTemplate().save(styleEntity);
		return styleEntity;
	}

	/**
	 * Get a styles list by the style name
	 * 
	 * @param <code>styleName</code>
	 * 
	 * @return Entities list associated with the style name or null if not found 
	 */
	public List<StyleEntity> getStyles(String styleName) {
		return findByCriteria(Restrictions.eq("name", styleName));
	}

	/**
	 * Delete a style by the style identifier 
	 * 
	 * @param <code>styleID</code>
	 * 
	 */
	public void deleteStyle(Long styleID) {
		StyleEntity styleEntity = findById(styleID, false);
		if(styleEntity != null){
			getHibernateTemplate().delete(styleEntity);
		}
	}

}
