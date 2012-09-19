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

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.emergya.persistenceGeo.dto.AuthorityDto;
import com.emergya.persistenceGeo.dto.LayerDto;
import com.emergya.persistenceGeo.dto.UserDto;
import com.emergya.persistenceGeo.service.LayerAdminService;
import com.emergya.persistenceGeo.service.UserAdminService;

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
	
	@Resource
	private UserAdminService userAdminService;
	@Resource
	private LayerAdminService layerAdminService;

	/**
	 * This method loads layers.json related with a user
	 * 
	 * @param username
	 * 
	 * @return JSON file with layers
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/persistenceGeo/loadLayers/{username}", method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	List<LayerDto> loadLayers(@PathVariable String username){
		List<LayerDto> layers = null;
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			if(username != null){
				layers = new LinkedList<LayerDto>();
				UserDto userDto = userAdminService.obtenerUsuario(username);
				if(userDto.getId() != null){
					layers = layerAdminService.getLayersByUser(userDto.getId());
				}else{
					layers = ListUtils.EMPTY_LIST;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return layers;
	}

	/**
	 * This method loads layers.json related with a user
	 * 
	 * @param username
	 * 
	 * @return JSON file with layers
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/persistenceGeo/getLayerResource/{layer_id}", method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	List<LayerDto> loadLayer(@PathVariable String layerId){
		List<LayerDto> layers = null;
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
		}catch (Exception e){
			e.printStackTrace();
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
	@RequestMapping(value = "/persistenceGeo/loadLayersGroup/{group}", method = RequestMethod.GET)
	public @ResponseBody 
	List<LayerDto> loadLayersGroup(@PathVariable String group){
		List<LayerDto> layers = null;
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			if(group != null){
				layers = new LinkedList<LayerDto>();
				List<AuthorityDto> authosDto = userAdminService.obtenerGruposUsuarios();
				List<String> namesList = null;
				if(authosDto != null){
					for(AuthorityDto authoDto: authosDto){
						if(authoDto.getNombre().equals(group)){
							namesList = authoDto.getLayerList();
							break;
						}
					}
					if(namesList != null){
						layers = layerAdminService.getLayersByName(namesList);
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return layers;
	}

	/**
	 * This method loads layers.json related with a folder
	 * 
	 * @param username
	 * 
	 * @return JSON file with layers
	 */
	@RequestMapping(value = "/persistenceGeo/loadLayersFolder/{folder}", method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	List<LayerDto> loadLayersFolder(@PathVariable String folder){
		List<LayerDto> layers = null;
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
		}catch (Exception e){
			e.printStackTrace();
		}
		return layers;
	}

	/**
	 * This method loads layers.json related with a folder
	 * 
	 * @param username
	 * 
	 * @return JSON file with layers
	 */
	@RequestMapping(value = "/persistenceGeo/moveLayerTo", method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	List<LayerDto> moveLayerTo(@RequestParam("toFolder") String toFolder,
			@RequestParam(value="toOrder",required=false) String toOrder){
		List<LayerDto> layers = null;
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
		}catch (Exception e){
			e.printStackTrace();
		}
		return layers;
	}

	/**
	 * This method saves a layer related with a user
	 * 
	 * @param username
	 * @param uploadfile
	 */
	@RequestMapping(value = "/persistenceGeo/saveLayerByUser/{username}/{name}/{type}", method = RequestMethod.GET)
	public @ResponseBody 
	Boolean saveLayerByUser(@PathVariable String username,
			@PathVariable("name") String name,
			@PathVariable("type") String type){
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			// Create the layerDto
			LayerDto layer = new LayerDto();
			// Assign the user
			layer.setUser(username);
			// Add request parameter
			layer.setName(name);
			layer.setType(type);
			
			// Save the layer
			layerAdminService.create(layer);
			
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * This method saves a layer related with a user
	 * 
	 * @param username
	 * @param uploadfile
	 */
	@RequestMapping(value = "/persistenceGeo/saveLayerByUser/{username}", method = RequestMethod.POST)
	public @ResponseBody 
	LayerDto saveLayerByUser(@PathVariable String username,
			@RequestParam("name") String name,
			@RequestParam("type") String type,
			@RequestParam(value="properties", required=false) Map<String, String> properties,
			@RequestParam(value="uploadfile", required=false) MultipartFile uploadfile){
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			// Create the layerDto
			LayerDto layer = new LayerDto();
			// Assign the user
			layer.setUser(username);
			// Add request parameter
			layer.setName(name);
			layer.setType(type);
			//Layer properties
			layer.setProperties(properties);

			//Layer data
			if(uploadfile != null){
				byte[] data = IOUtils.toByteArray(uploadfile.getInputStream());
				File temp = com.emergya.persistenceGeo.utils.FileUtils.createFileTemp(layer.getName(), layer.getType());
				org.apache.commons.io.FileUtils.writeByteArrayToFile(temp, data);
				layer.setData(temp);
			}
			
			// Save the layer
			layer = (LayerDto) layerAdminService.create(layer);
			
			return layer;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method saves a layer related with a group
	 * 
	 * @param group
	 * @param uploadfile
	 */
	@RequestMapping(value = "/persistenceGeo/saveLayer/{group}", method = RequestMethod.POST)
	public @ResponseBody 
	void saveLayerByGroup(@PathVariable Long group,
			@RequestParam("name") String name,
			@RequestParam("type") String type,
			@RequestParam(value="layerData", required=false) LayerDto layerData,
			@RequestParam(value="uploadfile", required=false) MultipartFile uploadfile){
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			// Get the group and his layers
			AuthorityDto auth = userAdminService.obtenerGrupoUsuarios(group);
			List<String> layersFromGroup = auth.getLayerList();
			// Add the new layer
			if(layersFromGroup != null){
				layersFromGroup.add(name);
				auth.setLayerList(layersFromGroup);
			}
			// Save the group
			userAdminService.modificarGrupoUsuarios(auth);
			// Create the layerDto
			LayerDto layer = new LayerDto();
			// Assign the authority
			layer.setAuth(auth.getNombre());
			// Add the request parameters
			layer.setName(name);
			layer.setType(type);
			// Load the layer depend on the layer type 
			// Save the layer
			layerAdminService.create(layer);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * This method saves a layer related with a group
	 * 
	 * @param group
	 * @param uploadfile
	 */
	@RequestMapping(value = "/persistenceGeo/saveFolder", method = RequestMethod.POST)
	public @ResponseBody 
	void saveFolder(@RequestParam("name") String name,
			@RequestParam("type") String type,
			@RequestParam("enabled") Boolean enabled,
			@RequestParam("isChannel") Boolean isChannel,
			@RequestParam("isPlain") Boolean isPlain){
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
