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
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.emergya.persistenceGeo.dto.ResourceDto;
import com.emergya.persistenceGeo.service.ResourceService;

/**
 * Rest controller to upload and obtain resources
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
@Controller
public class RestResourcesController extends RestPersistenceGeoController
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7390174279611387100L;
	
	@Resource
	private ResourceService resourceService;
	
	private Map<Long, ResourceDto> loadFiles = new ConcurrentHashMap<Long, ResourceDto>();
	private static final Random RANDOM = new Random();
	
	/**
	 * Clean temporary files
	 */
	@Scheduled(cron="0 15 04 * * ? *")
	public void clearLoadFiles() {
		for(ResourceDto resource: loadFiles.values()){
			resource.getData().delete();
		}
		loadFiles.clear();
	}
	
	/**
	 * This method upload a resource to server
	 * 
	 * @param uploadResource
	 */
	@RequestMapping(value = "/persistenceGeo/uploadResource", method = RequestMethod.POST)
	public ModelAndView uploadFile(
			@RequestParam(value="uploadfile") MultipartFile uploadfile){
		ModelAndView model = new ModelAndView();
		String result = null;
		if(uploadfile != null){
			try {
				Long id = RANDOM.nextLong();
				result = "{\"results\": 1, \"data\": \""+ id + "_" + uploadfile.getOriginalFilename() + "\", \"success\": true}";
				ResourceDto resource = multipartFileToResource(uploadfile, id);
				loadFiles.put(id, resource);
				resourceService.create(resource); //FIXME: only when a layer has been saved!!
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			result = "{\"results\": 0, \"data\": \"\", \"success\": false}";
		}
		model.addObject("resultado", result);
		model.setViewName("resultToJSON");
		return model;
	}

	/**
	 * This method loads a resource from server
	 * 
	 * @param resourceId
	 * 
	 * @return JSON file with resource
	 */
	@RequestMapping(value = "/persistenceGeo/getResource/{resourceId}", method = RequestMethod.GET, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public void getResource(@PathVariable String resourceId,
					HttpServletResponse response){
		try{
			Long accessId = Long.decode(resourceId.split("_")[0]);
			ResourceDto resource = loadFiles.get(accessId);
			if(resource == null){ // not loaded yet
				resource = resourceService.getByAccessId(accessId);
				loadFiles.put(accessId, resource);
			}
			response.setContentType(resource.getType());
			response.setHeader("Content-Length", new Long(resource.getSize()).toString());
			response.setHeader("Content-Disposition",
					"inline; filename="+resource.getName());
			IOUtils.copy(new FileInputStream(resource.getData()), response
					.getOutputStream());
			response.flushBuffer();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Obtain a resource from a multipart file 
	 * 
	 * @param file
	 * @param resourceId
	 * 
	 * @return resource
	 */
	private ResourceDto multipartFileToResource(MultipartFile file, Long resourceId){

		//
		ResourceDto resource = new ResourceDto();

		// simple properties
		resource.setName(file.getOriginalFilename());
		resource.setSize(file.getSize());
		resource.setType(file.getContentType());
		resource.setAccessId(resourceId);

		// obtain data
		byte[] data;
		String extension = "png";
		if(resource.getType() != null){
			if(resource.getType().split("/").length > 0){
				extension = resource.getType().split("/")[resource.getType().split("/").length-1];
			}else{
				extension = resource.getType();
			}
		}
		try {
			data = IOUtils.toByteArray(file.getInputStream());
			File temp = com.emergya.persistenceGeo.utils.FileUtils
					.createFileTemp(resource.getName(), extension);
			org.apache.commons.io.FileUtils.writeByteArrayToFile(temp, data);
			resource.setData(temp);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return resource;
	}

}
