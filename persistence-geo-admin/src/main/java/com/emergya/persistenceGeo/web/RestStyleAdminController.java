/*
 * RestStyleAdminController.java
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
package com.emergya.persistenceGeo.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emergya.persistenceGeo.dto.LayerDto;
import com.emergya.persistenceGeo.dto.MapEntryDto;
import com.emergya.persistenceGeo.dto.RuleDto;
import com.emergya.persistenceGeo.dto.RuleEntryDto;
import com.emergya.persistenceGeo.dto.StyleDto;
import com.emergya.persistenceGeo.dto.StyleEntryDto;
import com.emergya.persistenceGeo.dto.StyleMapDto;
import com.emergya.persistenceGeo.service.LayerAdminService;

/**
 * Simple REST controller for styles admin
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
@Controller
public class RestStyleAdminController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4397591353870510864L;
	
	protected final String RESULTS= "results";
	protected final String ROOT= "data";
	protected final String SUCCESS= "success";
	
	@Resource
	private LayerAdminService layerAdminService;

	/**
	 * This method uplado a style to a layer
	 * 
	 * @return JSON file with the layer updated
	 */
	@RequestMapping(value = "/persistenceGeo/uploadStyle/{layerId}", method = RequestMethod.POST,
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> uploadStyle(
			@PathVariable String layerId,
			@RequestParam String data
			){
		Map<String, Object> result = new HashMap<String, Object>();
		LayerDto layer = null;
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			Long idLayer = Long.decode(layerId);
			layer = (LayerDto) layerAdminService.getById(idLayer);
			
			ObjectMapper mapper = new ObjectMapper();
			StyleMapDto styleMap = mapper.readValue(data, StyleMapDto.class);
			layer.setStyles(getStyles(styleMap, idLayer));
			
			layer = (LayerDto) layerAdminService.update(layer);
			
			//Must already loaded in RestLayerAdminController
			if(layer.getId() != null && layer.getData() != null){
				layer.setData(null);
				layer.setServer_resource("rest/persistenceGeo/getLayerResource/"+layer.getId());
			}
			
			result.put(SUCCESS, true);
		}catch (Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		
		result.put(RESULTS, layer != null ? 1: 0);
		result.put(ROOT, layer);

		return result;
	}
	
	/**
	 * Parse StyleMap to Dto to be saved 
	 * 
	 * @param styleMap
	 * @param idLayer
	 * @return
	 */
	private Map<StyleDto,Map<RuleDto, Map<String, String>>> getStyles(StyleMapDto styleMap, Long idLayer){
		Map<StyleDto,Map<RuleDto, Map<String, String>>> styles = new HashMap<StyleDto, Map<RuleDto,Map<String,String>>>();
		//Styles
		for(StyleEntryDto style: styleMap.getStyles()){
			StyleDto styleDto = new StyleDto();
			styleDto.setName(style.getName());
			styleDto.setLayerId(idLayer);
			//Rules
			Map<RuleDto, Map<String, String>> rules = new HashMap<RuleDto, Map<String,String>>();
			for(RuleEntryDto rule: style.getRules()){
				RuleDto ruleDto = new RuleDto();
				ruleDto.setFilter(rule.getName());
				//Rule properties
				Map<String, String> ruleProperties = new HashMap<String, String>();
				for(MapEntryDto property: rule.getProperties()){
					ruleProperties.put(property.getName(), property.getValue());
				}
				rules.put(ruleDto, ruleProperties);
			}
			styleDto.setRules(rules);
			styles.put(styleDto, rules);
		}
		
		return styles;
		
	}
}