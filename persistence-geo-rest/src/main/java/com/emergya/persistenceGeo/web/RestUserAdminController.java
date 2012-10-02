/*
 * RestUserAdminController.java
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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emergya.persistenceGeo.dto.AuthorityDto;
import com.emergya.persistenceGeo.dto.UserDto;
import com.emergya.persistenceGeo.service.UserAdminService;

/**
 * Simple REST controller for user admin
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
@Controller
public class RestUserAdminController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1811767661679593998L;
	
	@Resource
	private UserAdminService userAdminService;

	@RequestMapping(value = "/persistenceGeo/admin/createUser", method = RequestMethod.POST)
	public @ResponseBody
		UserDto createUser(
			@RequestParam("username") String username,
			@RequestParam("userGroup") String userGroup,
			@RequestParam(value="userZone", required=false) String userZone) {
		
		UserDto user = new UserDto();
		user.setUsername(username);
		user.setPassword(username);
		user.setAuthority(userGroup);
		if(userZone != null){
			//TODO:
		}
		
		return (UserDto) userAdminService.create(user);
	}

	@RequestMapping(value = "/persistenceGeo/admin/modifyUser", method = RequestMethod.POST)
	public @ResponseBody
		UserDto modifyUser(
			@RequestParam("username") String username,
			@RequestParam("userGroup") String userGroup,
			@RequestParam("userAuth") String userAuth,
			@RequestParam(value="userZone", required=false) String userZone) {

		//TODO: Core call 
		
		return null;
	}

	@RequestMapping(value = "/persistenceGeo/admin/createGroup", method = RequestMethod.POST)
	public @ResponseBody
		AuthorityDto createGroup(
			@RequestParam("userGroup") String userGroup,
			@RequestParam(value="userZone", required=false) String userZone) {

		//TODO: Core call 
		
		return null;
	}
	
	protected final String RESULTS= "results";
	protected final String ROOT= "data";
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/persistenceGeo/getAllUsers", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getAllUsers() {
		Map<String, Object> result = new HashMap<String, Object>();
		//TODO: get user by authority group of user logged
		List<UserDto> users = (List<UserDto>) userAdminService.getAll();
		
		result.put(RESULTS, users != null ? users.size() : 0);
		result.put(ROOT, users != null ? users : ListUtils.EMPTY_LIST);
		
		return result;
	}
	
	@RequestMapping(value = "/persistenceGeo/getUsersByGroup/{idGroup}", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getUsersByGroup(@PathVariable String idGroup) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<UserDto> users = null;
		
		try{
			//TODO: get user by authority group of user logged
			users = (List<UserDto>) userAdminService.getUsersByGroup(Long.decode(idGroup));
		}catch(Exception e){
			//Nothing
		}
		
		result.put(RESULTS, users != null ? users.size() : 0);
		result.put(ROOT, users != null ? users : ListUtils.EMPTY_LIST);
		
		return result;
	}
	
	@RequestMapping(value = "/persistenceGeo/getAllGroups", method = RequestMethod.GET)
	public @ResponseBody
	Map<String, Object> getAllGroups() {
		Map<String, Object> result = new HashMap<String, Object>();
		//TODO: get user by authority group of user logged
		List<AuthorityDto> groups = (List<AuthorityDto>) userAdminService.obtenerGruposUsuarios();
		
		result.put(RESULTS, groups != null ? groups.size() : 0);
		result.put(ROOT, groups != null ? groups : ListUtils.EMPTY_LIST);
		
		return result;
	}

}
