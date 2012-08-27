/*
 * RestLayersAdminController.java
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
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.emergya.persistenceGeo.dto.LayerDto;

/**
 * Rest controller to admin and load layer and layers context
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
@Controller
public class RestLayersAdminController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method loads layers.json related with a user
	 * 
	 * @param username
	 * 
	 * @return JSON file with layers
	 */
	@RequestMapping(value = "/rest/loadLayers/{username}", method = RequestMethod.GET)
	public @ResponseBody 
	List<LayerDto> loadLayers(@PathVariable String username){
		List<LayerDto> layers = null;
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			//TODO: Implementar servicios, daos...
		}catch (Exception e){
			return null;
		}
		return layers;
	}

	/**
	 * This method loads layers.json related with a group
	 * 
	 * @param username
	 * 
	 * @return JSON file with layers
	 */
	@RequestMapping(value = "/rest/loadLayersGroup/{group}", method = RequestMethod.GET)
	public @ResponseBody 
	List<LayerDto> loadLayersGroup(@PathVariable String group){
		List<LayerDto> layers = null;
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			//TODO: Implementar servicios, daos...
		}catch (Exception e){
			return null;
		}
		return layers;
	}

	/**
	 * This method saves a layer related with a user
	 * 
	 * @param username
	 * @param uploadfile
	 */
	@RequestMapping(value = "/rest/saveLayerByUser/{username}", method = RequestMethod.POST)
	public @ResponseBody 
	void saveLayerByUser(@PathVariable String username,
			//TODO: Parametros obligatorios y opcionales de la capa
			@RequestParam("uploadfile") MultipartFile uploadfile){
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			//TODO: Implementar servicios, daos...
		}catch (Exception e){
			//TODO
		}
	}

	/**
	 * This method saves a layer related with a group
	 * 
	 * @param group
	 * @param uploadfile
	 */
	@RequestMapping(value = "/rest/saveLayerByGroup/{group}", method = RequestMethod.POST)
	public @ResponseBody 
	void saveLayerByGroup(@PathVariable String group,
			//TODO: Parametros obligatorios y opcionales de la capa
			@RequestParam("uploadfile") MultipartFile uploadfile){
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			//TODO: Implementar servicios, daos...
		}catch (Exception e){
			//TODO
		}
	}

}
