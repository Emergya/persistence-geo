/*
 * UserContextService.java Copyright (C) 2013 This file is part of persistenceGeo project
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
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com))
 */
package com.emergya.persistenceGeo.service;

import java.util.Map;

import com.emergya.persistenceGeo.dto.FeatureDto;
import com.emergya.persistenceGeo.dto.LayerDto;

/**
 * User context service 
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public interface UserContextService{
	
	/**
	 * Obtain features stored by user
	 * 
	 * @param idUser
	 * 
	 * @return Map of stored features by user
	 */
	public Map<Long, FeatureDto> getUserFeatures(Long idUser);

	/**
	 * Obtain layers stored by user
	 * 
	 * @param idUser
	 * @param idUser
	 * 
	 * @return Map of stored layers by user
	 */
	public Map<Long, LayerDto> getUserLayers(Long idUser);

	/**
	 * Save feature by user
	 * 
	 * @param feature
	 * 
	 * @return Map of stored features by user updated
	 */
	public Map<Long, FeatureDto> saveFeature(FeatureDto feature, Long idUser);


	/**
	 * Remove feature by user
	 * 
	 * @param idFeature
	 * @param idUser
	 * 
	 * @return Map of stored features by user updated
	 */
	public Map<Long, FeatureDto> removeFeature(Long idFeature, Long idUser);

	/**
	 * Save layer by user
	 * 
	 * @param layer
	 * @param idUser
	 * 
	 * @return Map of stored layers by user
	 */
	public Map<Long, LayerDto> saveLayer(LayerDto layer, Long idUser);

	/**
	 * Remove layer by user
	 * 
	 * @param idLayer
	 * @param idUser
	 * 
	 * @return Map of stored layers by user
	 */	
	public Map<Long, LayerDto> removeLayer(Long idLayer, Long idUser);
	
	/**
	 * Remove all temporal elements
	 * 
	 */
	public Boolean clearTemporalElements();
    
}
