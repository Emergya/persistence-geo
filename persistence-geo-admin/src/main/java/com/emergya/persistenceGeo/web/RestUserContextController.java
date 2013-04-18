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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emergya.persistenceGeo.dto.FeatureDto;
import com.emergya.persistenceGeo.dto.LayerDto;
import com.emergya.persistenceGeo.service.UserContextService;

/**
 * Simple REST controller for user context
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
@Controller
public class RestUserContextController extends RestPersistenceGeoController{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4323734192360939291L;
	
	@Resource
	private UserContextService userContextService;
	
	protected final String RESULTS= "results";
	protected final String ROOT= "data";
	protected final String SUCCESS= "success";
	
	//FIXME: Trust with spring security context
	
	/**
	 * Load features by user
	 * 
	 * @param idUser
	 * 
	 * @return 
	 */
	@RequestMapping(value = "/persistenceGeo/userContext/loadFeatures/{idUser}",
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> loadFeatures(@PathVariable String idUser) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Long, FeatureDto> features = null;
		try{
			features = (Map<Long, FeatureDto>) userContextService.getUserFeatures(Long.decode(idUser));
			result.put(SUCCESS, true);
		}catch(Exception e){
			e.printStackTrace();
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
	@RequestMapping(value = "/persistenceGeo/userContext/loadLayers/{idUser}",
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> loadLayers(@PathVariable String idUser) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Long, LayerDto> layers = null;
		try{
			layers = (Map<Long, LayerDto>) userContextService.getUserLayers(Long.decode(idUser));
			result.put(SUCCESS, true);
		}catch(Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		result.put(RESULTS, layers != null ? layers.size() : 0);
		result.put(ROOT, layers != null ? layers : MapUtils.EMPTY_MAP);
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
	@RequestMapping(value = "/persistenceGeo/userContext/saveFeature/{idUser}", 
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> saveFeature(@PathVariable String idUser,   
			@RequestParam("feature") String feature, 
			@RequestParam("featureID") String featureID) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Long, FeatureDto> mapFeatures = new HashMap<Long, FeatureDto>();
		FeatureDto featureDto = null;
		try{
			featureDto = new FeatureDto();
			featureDto.setId(Long.decode(featureID));
			featureDto.setJsonFeature(feature);
			mapFeatures = userContextService.saveFeature(featureDto, Long.decode(idUser));
			if(mapFeatures.containsKey(Long.decode(featureID))){
				result.put(SUCCESS, true);
			}else{
				result.put(SUCCESS, false);
			}
		}catch (Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		result.put(RESULTS, featureDto != null ? 1: 0);
		result.put(ROOT, featureDto);
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
	@RequestMapping(value = "/persistenceGeo/userContext/removeFeature/{idUser}",
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> removeFeature(@PathVariable String idUser,  
			@RequestParam("idFeature") String idFeature) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Long, FeatureDto> mapFeatures = new HashMap<Long, FeatureDto>();
		FeatureDto featureDto = null;
		try{
			mapFeatures = userContextService.removeFeature(Long.decode(idFeature), Long.decode(idUser));
			if(mapFeatures.containsKey(Long.decode(idFeature))){
				result.put(SUCCESS, false);
			}else{
				result.put(SUCCESS, true);
			}
		}catch (Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		result.put(RESULTS, featureDto != null ? 1: 0);
		result.put(ROOT, featureDto);
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
	@RequestMapping(value = "/persistenceGeo/userContext/saveLayer/{idUser}",
			method = RequestMethod.POST, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> saveLayer(@PathVariable String idUser, 
			@RequestParam("layer") String layer,
			@RequestParam("layerID") String layerID,
			@RequestParam("layerOrder") String layerOrder,
			@RequestParam("layerName") String layerName) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Long, LayerDto> mapLayers = new HashMap<Long, LayerDto>();
		LayerDto layerDto = null;
		try{
			layerDto = new LayerDto();
			layerDto.setId(Long.decode(layerID));
			layerDto.setOrder(layerOrder);
			layerDto.setName(layerName);
			mapLayers = userContextService.saveLayer(layerDto, Long.decode(idUser));
			if(mapLayers.containsKey(Long.decode(layerID))){
				result.put(SUCCESS, true);
			}else{
				result.put(SUCCESS, false);
			}
		}catch (Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		result.put(RESULTS, layerDto != null ? 1: 0);
		result.put(ROOT, layerDto);
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
	@RequestMapping("/persistenceGeo/userContext/removeLayer/{idUser}")
	public @ResponseBody
	Map<String, Object> removeLayer(@PathVariable String idUser,  
			@RequestParam("idLayer") String idLayer) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<Long, LayerDto> mapLayers = new HashMap<Long, LayerDto>();
		LayerDto layerDto = null;
		try{
			mapLayers = userContextService.removeLayer(Long.decode(idLayer), Long.decode(idUser));
			if(mapLayers.containsKey(Long.decode(idLayer))){
				result.put(SUCCESS, false);
			}else{
				result.put(SUCCESS, true);
			}
		}catch (Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		result.put(RESULTS, layerDto != null ? 1: 0);
		result.put(ROOT, layerDto);
		return result;
	}
	
	/**
	 * Remove all
	 * 
	 * @return Map with the elements removed
	 */	
	@RequestMapping("/persistenceGeo/userContext/removeAll")
	public @ResponseBody
	Map<String, Object> removeAll() {
		Map<String, Object> result = new HashMap<String, Object>();
		Boolean clean = false;
		try{
			clean = userContextService.clearTemporalElements();
			if(clean){
				result.put(SUCCESS, true);
			}else{
				result.put(SUCCESS, false);
			}
		}catch (Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		result.put(RESULTS, clean != null ? 1: 0);
		result.put(ROOT, clean);
		return result;
	}
}
