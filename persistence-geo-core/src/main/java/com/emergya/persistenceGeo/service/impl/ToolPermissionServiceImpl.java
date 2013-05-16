/*
 * ToolPermissionService.java
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
package com.emergya.persistenceGeo.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.GenericDAO;
import com.emergya.persistenceGeo.dao.PermissionEntityDao;
import com.emergya.persistenceGeo.dto.ToolPermissionDto;
import com.emergya.persistenceGeo.metaModel.AbstractPermissionEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;
import com.emergya.persistenceGeo.service.ToolPermissionService;

/**
 * ToolPermissionService transactional implementation based on daos uses
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
@Repository("toolPermissionService")
@Transactional
public class ToolPermissionServiceImpl extends AbstractServiceImpl<ToolPermissionDto, AbstractPermissionEntity> 
	implements ToolPermissionService{

	@Resource
	private Instancer instancer;
	@Resource
	private PermissionEntityDao dao;
	
	public ToolPermissionServiceImpl(){
		super();
	}

	@Override
	public Map<String, ToolPermissionDto> getPermissionsByAuthority(
			Long authorityId) {
		Map<String, ToolPermissionDto> result = null;
		List<AbstractPermissionEntity> permissions = dao.getPermissionsByAuthorithy(authorityId);
		if(permissions != null){
			result = new HashMap<String, ToolPermissionDto>();
			for(AbstractPermissionEntity permission: permissions){
				result.put(permission.getName(), entityToDto(permission));
			}
		}
		return result;
	}

	@Override
	public Map<String, ToolPermissionDto> getPermissionsByUser(Long userId) {
		// TODO: Store and get permissions by user
		Map<String, ToolPermissionDto> result = null;
		List<AbstractPermissionEntity> permissions = dao.getPermissionsByAuthorithy(null);
		if(permissions != null){
			result = new HashMap<String, ToolPermissionDto>();
			for(AbstractPermissionEntity permission: permissions){
				result.put(permission.getName(), entityToDto(permission));
			}
		}
		return result;
	}

	@Override
	protected GenericDAO<AbstractPermissionEntity, Long> getDao() {
		return dao;
	}

	protected ToolPermissionDto entityToDto(AbstractPermissionEntity entity) {
		ToolPermissionDto dto = null;
		if(entity != null){
			dto = new ToolPermissionDto();
			// Add own parameters
			dto.setId((Long) entity.getId());
			dto.setName(entity.getName());
			dto.setPtype(entity.getPtype());
			dto.setConfig(entity.getConfig());
			dto.setUpdateDate(entity.getUpdateDate());
			dto.setCreateDate(entity.getCreateDate());
			dto.setFilter(entity.getFilter());
		}
		return dto;
	}

	protected AbstractPermissionEntity dtoToEntity(ToolPermissionDto dto) {
		AbstractPermissionEntity entity = null;
		if(dto != null){
			if(dto.getId() != null && dto.getId() > 0){
				entity = (AbstractPermissionEntity) dao.findById(dto.getId(), false);
			}else{
				entity =  instancer.createPermission();
			}
			// Add own parameters
			entity.setName(dto.getName());
			entity.setPtype(dto.getPtype());
			entity.setConfig(dto.getConfig());
			entity.setUpdateDate(dto.getUpdateDate());
			entity.setCreateDate(dto.getCreateDate());
			entity.setFilter(dto.getFilter());
		}
		return entity;
	}
	
}
