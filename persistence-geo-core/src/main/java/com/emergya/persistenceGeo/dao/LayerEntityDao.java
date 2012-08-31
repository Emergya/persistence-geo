/*
 * LayerEntityDao.java
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
package com.emergya.persistenceGeo.dao;

import java.util.List;

import com.emergya.persistenceGeo.model.FolderEntity;
import com.emergya.persistenceGeo.model.LayerEntity;
import com.emergya.persistenceGeo.model.StyleEntity;
import com.emergya.persistenceGeo.model.UserEntity;

/**
 * DAO that represents the layer
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 *
 */
public interface LayerEntityDao extends GenericDAO<LayerEntity, Long> {

	/**
	 * Create a new layer in the system
	 * 
	 * @param <code>layerName</code>
	 * 
	 * @return Entity from the new layer
	 */
	public LayerEntity createLayer(String layerName);
	
	/**
	 * Save the layer in the system
	 * 
	 * @param <code>layerEntity</code>
	 * 
	 * @return Entity identifier from the save layer
	 */
	public Long save(LayerEntity layerEntity);
	
	/**
	 * Get a layers list by the private layer name 
	 * 
	 * @param <code>layerName</code>
	 * 
	 * @return Entities list associated with the layer name or null if not found 
	 */
	public List<LayerEntity> getLayers(String layerName);
	
	/**
	 * Delete a layer by the layer identifier 
	 * 
	 * @param <code>layerID</code>
	 * 
	 */
	public void delete(Long layerID);
	
	/**
	 * Get a users list by a layer id
	 * 
	 * @param layerID
	 * 
	 * @return Entities list associated with the layer identifier or null if not found 
	 */
	public UserEntity findByLayer(Long layerID);
	
	/**
	 * Get a folders list by the layer identifier
	 * 
	 * @param <code>layerID</code>
	 * 
	 * @return Entities list associated with the layer identifier or null if not found 
	 */
	public List<FolderEntity> findFolderByLayer(Long layerID);
	
	/**
	 * Get a style list by the layer identifier
	 * 
	 * @param <code>layerID</code>
	 * 
	 * @return Entities list associated with the layer identifier or null if not found 
	 */
	public List<StyleEntity> findStyleByLayer(Long layerID);
}
