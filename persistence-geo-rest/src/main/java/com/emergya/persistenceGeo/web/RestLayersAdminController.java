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
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.emergya.persistenceGeo.dto.SimplePropertyDto;
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
	
	private Map<Long, File> loadedLayers = new HashMap<Long, File>();
	
	protected final String RESULTS= "results";
	protected final String ROOT= "data";

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
	Map<String, Object> loadLayers(@PathVariable String username){
		Map<String, Object> result = new HashMap<String, Object>();
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
				for(LayerDto layer: layers){
					if(layer.getId() != null && layer.getData() != null){
						loadedLayers.put(layer.getId(), layer.getData());
						layer.setData(null);
						layer.setServer_resource("rest/persistenceGeo/getLayerResource/"+layer.getId());
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		
		result.put(RESULTS, layers.size());
		result.put(ROOT, layers);

		return result;
	}

	/**
	 * This method loads json file related with a user
	 * 
	 * @param username
	 * 
	 * @return JSON file with layer type properties
	 */
	@RequestMapping(value = "/persistenceGeo/getLayerTypeProperties/{layerType}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody
	Map<String, Object> getLayerTypeProperties(@PathVariable String layerType) {
		Map<String, Object> result = new HashMap<String, Object>();

		List<String> listRes = layerAdminService
				.getAllLayerTypeProperties(layerType);
		
		List<SimplePropertyDto> list = new LinkedList<SimplePropertyDto>();
		
		if(listRes != null){
			for(String property: listRes){
				list.add(new SimplePropertyDto(property));
			}
		}
		
		result.put(RESULTS, list.size());
		result.put(ROOT, list);

		return result;
	}

	/**
	 * This method loads json file with layer types
	 * 
	 * @param username
	 * 
	 * @return JSON file with layer types
	 */
	@RequestMapping(value = "/persistenceGeo/getLayerTypes", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody
	Map<String, Object> getLayerTypes() {
		Map<String, Object> result = new HashMap<String, Object>();

		List<String> listRes = layerAdminService.getAllLayerTypes();

		List<SimplePropertyDto> list = new LinkedList<SimplePropertyDto>();
		
		if(listRes != null){
			for(String property: listRes){
				list.add(new SimplePropertyDto(property));
			}
		}
		
		result.put(RESULTS, list.size());
		result.put(ROOT, list);

		return result;
	}

	/**
	 * This method loads layers.json related with a user
	 * 
	 * @param username
	 * 
	 * @return JSON file with layers
	 */
	@RequestMapping(value = "/persistenceGeo/getLayerResource/{layerId}", method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public void loadLayer(@PathVariable String layerId,
					HttpServletResponse response){
		try{
			/*
			//TODO: Secure with logged user
			String username = ((UserDetails) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal()).getUsername(); 
			 */
			response.setContentType("application/xml");
			response.setHeader("Content-Disposition",
					"attachment; filename=test.xml");
			IOUtils.copy(new FileInputStream(loadedLayers.get(Long.decode(layerId))), response
						.getOutputStream());
			response.flushBuffer();
		}catch (Exception e){
			e.printStackTrace();
		}
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
			@RequestParam(value="properties", required=false) String properties,
			@RequestParam(value="enabled", required=false) String enabled,
			@RequestParam(value="order_layer", required=false) String order_layer,
			@RequestParam(value="is_channel", required=false) String is_channel,
			@RequestParam(value="publicized", required=false) String publicized,
			@RequestParam(value="server_resource", required=false) String server_resource,
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
			layer.setServer_resource(server_resource);
			layer.setEnabled(enabled != null ? enabled.toLowerCase().equals("true"): false);
			layer.setOrder(order_layer);
			layer.setPertenece_a_canal(is_channel != null ? is_channel.toLowerCase().equals("true"): false);
			layer.setPublicized(publicized != null ? publicized.toLowerCase().equals("true"): false);
			layer.setServer_resource(server_resource);
			
			//Layer properties
			if(properties != null){
				layer.setProperties(getMapFromString(properties));
			}

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
	
	private static final String PROPERTIES_SEPARATOR = ",,,";
	private static final String PROPERTIES_NAM_VALUE_SEPARATOR = "===";
	
	/**
	 * Parse a string as 'test===valueTest,,,test2===value2' to map of values 
	 * 
	 * @param properties to be parsed
	 * 
	 * @return map with values
	 */
	private static Map<String, String> getMapFromString(String properties){
		Map<String,String> map = new HashMap<String, String>();
		if(properties.split(PROPERTIES_SEPARATOR) != null){
			for(String property: properties.split(PROPERTIES_SEPARATOR)){
				if(property != null 
						&& property.split(PROPERTIES_NAM_VALUE_SEPARATOR) != null
						&& property.split(PROPERTIES_NAM_VALUE_SEPARATOR).length == 2){
					map.put(property.split(PROPERTIES_NAM_VALUE_SEPARATOR)[0], property.split(PROPERTIES_NAM_VALUE_SEPARATOR)[1]);
				}
			}
		}
		return map;
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
