/*
 * RestSourceToolController.java
 * 
 * Copyright (C) 2013
 * 
 * This file is part of persistenceGeo project
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emergya.persistenceGeo.dto.SourceToolDto;
import com.emergya.persistenceGeo.service.AbstractService;

/**
 * Rest controller to upload and obtain source tools
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
@Controller
public class RestSourceToolController extends RestPersistenceGeoController
		implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1342640867929110704L;
	
	@Resource(name="sourceToolService")
	private AbstractService service;

	/**
	 * This method loads loadAllSourceTools
	 * 
	 * @return JSON file with source tools list
	 */
	@RequestMapping(value = "/persistenceGeo/loadAllSourceTools", 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> loadMapConfiguration(){
		Map<String, Object> result = new HashMap<String, Object>();
		List<? extends Serializable> list = null; 
		try{
			list = service.getAll(); // source tool list
			result.put(SUCCESS, list != null);
		}catch (Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		result.put(RESULTS, list != null ? list.size(): 0);
		result.put(ROOT, list);
		return result;
	}

	/**
	 * Save source tool
	 * 
	 * @return JSON file with source tools saved
	 */
	@RequestMapping(value = "/persistenceGeo/saveSourceTool", 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> saveSourceTool(@RequestParam("name") String name,
			@RequestParam("ptype") String ptype,
			@RequestParam("sourceUrl") String url,
			@RequestParam("config") String config){
		Map<String, Object> result = new HashMap<String, Object>();
		if(isAdmin(Boolean.TRUE)){
			SourceToolDto source = new SourceToolDto();
			try{
				source.setName(name);
				source.setPtype(ptype);
				source.setCreateDate(new Date());
				source.setConfig(config);
				source.setUrl(url);
				source = (SourceToolDto) service.create(source); // source tool create
				result.put(SUCCESS, true);
			}catch (Exception e){
				e.printStackTrace();
				result.put(SUCCESS, false);
				source = null;
			}
			result.put(RESULTS, source != null ? 1: 0);
			result.put(ROOT, source);
		}else{
			result.put(SUCCESS, false);
			result.put(RESULTS, 0);
			result.put(ROOT, NOT_ACCESS_MSG);
		}
		
		
		return result;
	}

	/**
	 * Delete source tool
	 * 
	 * @return JSON file with source tools saved
	 */
	@RequestMapping(value = "/persistenceGeo/deleteSourceTool/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> deleteSourceTool(@PathVariable("id") String id){
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(isAdmin(Boolean.TRUE)){
			try{
				service.delete(service.getById(Long.decode(id))); // source tool delete
				result.put(SUCCESS, true);
			}catch (Exception e){
				e.printStackTrace();
				result.put(SUCCESS, false);
			}
			result.put(RESULTS, 1);
			result.put(ROOT, SUCCESS);
		}else{
			result.put(SUCCESS, false);
			result.put(RESULTS, 0);
			result.put(ROOT, NOT_ACCESS_MSG);
		}
		
		return result;
	}
	
	/**
	 * Update source tool
	 * 
	 * @return JSON file with source tools saved
	 */
	@RequestMapping(value = "/persistenceGeo/updateSourceTool/{id}", 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> updateSourceTool(@PathVariable("id") String id,
			@RequestParam("name") String name,
			@RequestParam("ptype") String ptype,
			@RequestParam("sourceUrl") String url,
			@RequestParam("config") String config){
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(isAdmin(Boolean.TRUE)){
			SourceToolDto source = null;
			try{
				source = (SourceToolDto) service.getById(Long.decode(id));
				source.setName(name);
				source.setPtype(ptype);
				source.setCreateDate(new Date());
				source.setConfig(config);
				source.setUrl(url);
				source = (SourceToolDto) service.update(source); // source tool create
				result.put(SUCCESS, true);
			}catch (Exception e){
				e.printStackTrace();
				result.put(SUCCESS, false);
				source = null;
			}
			result.put(RESULTS, source != null ? 1: 0);
			result.put(ROOT, source);
		}else{
			result.put(SUCCESS, false);
			result.put(RESULTS, 0);
			result.put(ROOT, NOT_ACCESS_MSG);
		}
		
		return result;
	}

}
