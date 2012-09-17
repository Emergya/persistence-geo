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
import java.util.List;

import org.springframework.stereotype.Controller;
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
	
	private UserAdminService userAdminService;

	@RequestMapping(value = "/persistenceGeo/admin/createUser", method = RequestMethod.POST)
	public @ResponseBody
		UserDto createUser(
			@RequestParam("username") String username,
			@RequestParam("userGroup") String userGroup,
			@RequestParam("userAuth") String userAuth,
			@RequestParam(value="userZone", required=false) String userZone) {

		//TODO: Core call 
		
		return null;
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
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/persistenceGeo/getAllUsers", method = RequestMethod.GET)
	public @ResponseBody
	List<UserDto> getAllUsers() {
		//TODO: get user by authority group of user 
		return (List<UserDto>) userAdminService.getAll();
	}

}
