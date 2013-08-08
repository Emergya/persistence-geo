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
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emergya.persistenceGeo.dto.AuthorityDto;
import com.emergya.persistenceGeo.dto.FolderDto;
import com.emergya.persistenceGeo.dto.UserDto;
import com.emergya.persistenceGeo.service.FoldersAdminService;
import com.emergya.persistenceGeo.service.LayerAdminService;
import com.emergya.persistenceGeo.service.ToolPermissionService;
import com.emergya.persistenceGeo.service.UserAdminService;
import com.emergya.persistenceGeo.service.ZoneAdminService;
import com.google.common.base.Strings;

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
	
	@Resource
	private ZoneAdminService zoneAdminService;
	
	@Resource
	private LayerAdminService layerAdminService;
	
	@Resource
	private FoldersAdminService foldersAdminService;
	
	@Resource
	private ToolPermissionService toolPermissionService;
	
	protected final String RESULTS= "results";
	protected final String ROOT= "data";
	protected final String SUCCESS= "success";

	@RequestMapping(value = "/persistenceGeo/admin/createUser", method = RequestMethod.POST)
	public @ResponseBody
		UserDto createUser(
			@RequestParam("username") String username,
			@RequestParam("userGroup") String userGroup,
			@RequestParam(value="userZone", required=false) String userZone) {
		
		UserDto user = userAdminService.obtenerUsuario(username, username);
		if(user == null){
			user = new UserDto();
			user.setUsername(username);
			user.setPassword(username);
		}
		
		boolean changed = false;

		if(!userGroup.equals(user.getAuthority())){
			user.setAuthority(userGroup);	
			changed = true;
		}
		
		if(userZone != null 
				&& user.getAuthority() != null
				&& user.getAuthority().split("_").length == 2
				&& user.getAuthority().split("_")[0].equals(userGroup)
				&& user.getAuthority().split("_")[1].equals(userZone)){
			user.setAuthority(userGroup + "_" + userZone);
		}else if(userZone != null){
			checkAndCreateAuth(userGroup, userZone);
			user.setAuthority(userGroup + "_" + userZone);
			changed = true;
		}else{
			checkAndCreateAuth(userGroup, null);
		}
		
		if(user.getId() == null){
			user = (UserDto) userAdminService.create(user);
		}else if(changed){
			user = (UserDto) userAdminService.update(user);
		}
		
		FolderDto folder = foldersAdminService.getRootFolder(user.getId());
		if(folder == null){
			//Create default user folder
			folder = new FolderDto();
			folder.setIdUser(user.getId());
			folder.setName("");
			foldersAdminService.saveFolder(folder);
		}
		
		return user;
	}

	@RequestMapping(value = "/persistenceGeo/admin/modifyUser", method = RequestMethod.POST)
	public @ResponseBody
		UserDto modifyUser(
			@RequestParam("username") String username,
			@RequestParam("userGroup") String userGroup,
			@RequestParam("userAuth") String userAuth,
			@RequestParam(value="userZone", required=false) String userZone) {

		UserDto user = userAdminService.obtenerUsuario(username, username);
		if(user != null){
			user.setAuthority(userGroup);
			
			if(userZone != null){
				checkAndCreateAuth(userGroup, userZone);
				user.setAuthority(userGroup + "_" + userZone);
			}
			
			user = (UserDto) userAdminService.update(user);
		}
		
		return user;
	}

	@RequestMapping(value = "/persistenceGeo/admin/createGroup", method = RequestMethod.POST)
	public @ResponseBody
		AuthorityDto createGroup(
			@RequestParam("userGroup") String userGroup,
			@RequestParam(value="userZone", required=false) String userZone) {
		return checkAndCreateAuth(userGroup, userZone);
	}
	
	private static String SUPERADMIN_AUTH = "SUPERADMIN";
	private static String AUTH_WITHOUT_ZONE = "NO_ZONE";
	private static String AUTH_WITH_ZONE = "ZONE_AUTHS";
	private static Map<String, Long> DEFAULT_AUTH_TREE;
	
	static{
		DEFAULT_AUTH_TREE = new HashMap<String, Long>();
		DEFAULT_AUTH_TREE.put(SUPERADMIN_AUTH, new Long(1));
		DEFAULT_AUTH_TREE.put(AUTH_WITHOUT_ZONE, new Long(2));
		DEFAULT_AUTH_TREE.put(AUTH_WITH_ZONE, new Long(3));
	}
	
	private AuthorityDto checkAndCreateAuth(String name, String zone){
		AuthorityDto dto = null;
		List<AuthorityDto> groups = (List<AuthorityDto>) userAdminService.obtenerGruposUsuarios();
		for(AuthorityDto group: groups){
			if(!StringUtils.isEmpty(name) 
					&& name.equals(group.getNombre())
					&& ((!StringUtils.isEmpty(zone)
							&& zone.equals(group.getZone())) 
							|| 
						(StringUtils.isEmpty(zone) && StringUtils.isEmpty(group.getZone()))
					)){
				dto = group;
				break;
			}
		}
		if(dto == null){
			dto = new AuthorityDto();
			dto.setNombre(name);
			dto.setZone(zone);
			dto.setParentId(DEFAULT_AUTH_TREE.get(AUTH_WITHOUT_ZONE));
			if(zone != null){
				dto.setNombre(name + "_" + zone);
				dto.setParentId(DEFAULT_AUTH_TREE.get(AUTH_WITH_ZONE));
			}
			dto.setId(userAdminService.crearGrupoUsuarios(dto));
		}
		return dto;
	}
	
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
		List<AuthorityDto> groups = (List<AuthorityDto>) userAdminService.obtenerGruposUsuarios();
		
		result.put(RESULTS, groups != null ? groups.size() : 0);
		result.put(ROOT, groups != null ? groups : ListUtils.EMPTY_LIST);
		
		return result;
	}
	
	/**
	 * Obtain user logged info
	 *
	 * @return json with user info or null if is not logged 
	 */
	@RequestMapping(value = "/persistenceGeo/getUserInfo", 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> getUserInfo(){
		Map<String, Object> result = new HashMap<String, Object>();
		UserDto user = null;
		try{
			// Secure with logged user
			Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			String userLogged = (principal instanceof String) ? (String) principal :
				((UserDetails) principal).getUsername();
			if(userLogged != null){
				user = userAdminService.obtenerUsuario(userLogged);
				if (user != null) {
					user.setPassword("");
					// save user permissions. always by user
					user.setPermissions(toolPermissionService.getPermissionsByUser(user.getId())); 
				}
			}
			
			if(user == null){
				user = new UserDto();
				user.setUsername(userLogged);
				user.setAdmin(false);
				// save user permissions. default permissions
				user.setPermissions(toolPermissionService.getPermissionsByUser(null));
			}
			
			result.put(SUCCESS, true);
		}catch (Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		
		result.put(RESULTS, user != null ? 1: 0);
		
		// We dont want return the password.
		result.put(ROOT, user);
		

		return result;
	}

	/**
	 * Returns the user's zone geometry.
	 *
	 * @return json with user info or null if is not logged 
	 */
	@RequestMapping(value = "/persistenceGeo/getUserZoneGeom/{userId}/{projectionName}", 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody
	Map<String, Object> getUserZoneGeom (@PathVariable Long userId, 
			@PathVariable String projectionName){
		
		Map<String, Object> result = new HashMap<String, Object>();
		UserDto user = null;
		String zoneGeom ="";
		try{
			// Secure with logged user
			user = (UserDto) userAdminService.getById(userId);
			
			if(user.getAuthorityZoneId()!=null){
				zoneGeom = zoneAdminService.getZoneGeomAsText(user.getAuthorityZoneId(),projectionName);
			}
			
			result.put(SUCCESS, true);
		}catch (Exception e){
			e.printStackTrace();
			result.put(SUCCESS, false);
		}
		
		result.put(RESULTS, Strings.isNullOrEmpty(zoneGeom)? 0: 1);	
		
		result.put(ROOT, zoneGeom);

		return result;
	}
}
