/*
 * RestUserContextController.java Copyright (C) 2013 This file is part of persistenceGeo project
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
package com.emergya.persistenceGeo.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emergya.persistenceGeo.dto.FeatureDto;
import com.emergya.persistenceGeo.service.UserContextService;

public class RestUserContextController extends RestPersistenceGeoController{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4323734192360939291L;
	
	@Resource
	private UserContextService userContextService;
	
	//FIXME: Trust with spring security context
	
	/**
	 * Load features by user
	 * 
	 * @param idUser
	 * 
	 * @return 
	 */
	@RequestMapping("/persistenceGeo/userContext/loadFeatures/{idUser}")
	public @ResponseBody
	Map<String, Object> loadFeatures(@PathVariable String idUser) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Long, FeatureDto> features = null;
		
		try{
			//TODO: get user features
			features = (Map<Long, FeatureDto>) userContextService.getUserFeatures(Long.decode(idUser));
			result.put(SUCCESS, true);
		}catch(Exception e){
			result.put(SUCCESS, false);
		}
		
		result.put(RESULTS, features != null ? features.size() : 0);
		result.put(ROOT, features != null ? features : MapUtils.EMPTY_MAP);
		
		return result;
	}
	
	/**
	 * Obtain layers stored by user
	 * 
	 * @param idUser
	 * 
	 * @return 
	 */
	@RequestMapping("/persistenceGeo/userContext/loadLayers/{idUser}")
	public @ResponseBody
	Map<String, Object> loadLayers(@PathVariable String idUser) {
		Map<String, Object> result = new HashMap<String, Object>();
		//TODO: Implement!!
		return result;
	}

	/**
	 * Save feature by user
	 * 
	 * @param idUser
	 * @param feature
	 * 
	 * @return Map of stored features by user updated
	 */
	@RequestMapping("/persistenceGeo/userContext/saveFeature/{idUser}")
	public @ResponseBody
	Map<String, Object> saveFeature(@PathVariable String idUser,   
			@RequestParam("feature") Map<String, String> feature) {
		Map<String, Object> result = new HashMap<String, Object>();
		//TODO: Implement!!
		return result;
	}


	/**
	 * Remove feature by user
	 * 
	 * @param idFeature
	 * @param idUser
	 * 
	 * @return Map of stored features by user updated
	 */
	@RequestMapping("/persistenceGeo/userContext/removeFeature/{idUser}/{idFeature}")
	public @ResponseBody
	Map<String, Object> removeFeature(@PathVariable String idUser,  
			@RequestParam("idFeature") String idFeature) {
		Map<String, Object> result = new HashMap<String, Object>();
		//TODO: Implement!!
		return result;
	}

	/**
	 * Save layer by user
	 * 
	 * @param layer
	 * @param idUser
	 * 
	 * @return Map of stored layers by user
	 */
	@RequestMapping("/persistenceGeo/userContext/saveLayer/{idUser}")
	public @ResponseBody
	Map<String, Object> saveLayer(@PathVariable String idUser, 
			@RequestParam("layer") Map<String, String> layer) {
		Map<String, Object> result = new HashMap<String, Object>();
		//TODO: Implement!!
		return result;
	}

	/**
	 * Remove layer by user
	 * 
	 * @param idLayer
	 * @param idUser
	 * 
	 * @return Map of stored layers by user
	 */	
	@RequestMapping("/persistenceGeo/userContext/removeLayer/{idUser}/{idLayer}")
	public @ResponseBody
	Map<String, Object> removeLayer(@PathVariable String idUser,  
			@RequestParam("idLayer") String idLayer) {
		Map<String, Object> result = new HashMap<String, Object>();
		//TODO: Implement!!
		return result;
	}
	
}
