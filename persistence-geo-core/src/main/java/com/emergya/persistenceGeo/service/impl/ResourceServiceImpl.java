/*
 * ResourceServiceImpl.java
 * 
 * Copyright (C) 2013
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
package com.emergya.persistenceGeo.service.impl;

import java.io.File;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.AbstractGenericDao;
import com.emergya.persistenceGeo.dao.GenericDAO;
import com.emergya.persistenceGeo.dao.ResourceEntityDao;
import com.emergya.persistenceGeo.dto.ResourceDto;
import com.emergya.persistenceGeo.metaModel.AbstractResourceEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;
import com.emergya.persistenceGeo.service.ResourceService;

/**
 * ResourceService transactional implementation based on daos uses
 * {@link AbstractGenericDao}
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
@Repository
@Transactional
public class ResourceServiceImpl extends AbstractServiceImpl<ResourceDto, AbstractResourceEntity>
		implements ResourceService {

	@Resource
	private Instancer instancer;
	@Resource
	private ResourceEntityDao resourceDao;
	
	public ResourceServiceImpl(){
		super();
	}

	@Override
	protected GenericDAO<AbstractResourceEntity, Long> getDao() {
		return resourceDao;
	}

	@Override
	public ResourceDto getByAccessId(Long accessId) {
		return entityToDto(resourceDao.findByAccessId(accessId));
	}

	@Override
	public void removeByAccessId(Long accessId) {
		resourceDao.deleteByAccessId(accessId);
	}

	protected ResourceDto entityToDto(AbstractResourceEntity entity) {
		ResourceDto dto = null;
		if(entity != null){
			dto = new ResourceDto();
			// Add own parameters
			dto.setId((Long) entity.getId());
			dto.setName(entity.getName());
			dto.setSize(entity.getSize());
			dto.setType(entity.getType());
			dto.setAccessId(entity.getAccessId());
			
			if(entity.getData() != null){
				try {
					String extension = "png";
					if(entity.getType() != null){
						if(entity.getType().split("/").length > 0){
							extension = entity.getType().split("/")[entity.getType().split("/").length-1];
						}else{
							extension = entity.getType();
						}
					}
					File file = com.emergya.persistenceGeo.utils.FileUtils.createFileTemp(entity.getName(), extension);
					FileUtils.writeByteArrayToFile(file, entity.getData());
					dto.setData(file);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return dto;
	}

	protected AbstractResourceEntity dtoToEntity(ResourceDto dto) {
		AbstractResourceEntity entity = null;
		if(dto != null){
			if(dto.getId() != null && dto.getId() > 0){
				entity = (AbstractResourceEntity) resourceDao.findById(dto.getId(), false);
			}else{
				entity =  instancer.createResourceEntity();
			}
			// Add own parameters
			entity.setName(dto.getName());
			entity.setSize(dto.getSize());
			entity.setType(dto.getType());
			entity.setAccessId(dto.getAccessId());
			
			//Layer data
			if(dto.getData() != null){
				try {
					entity.setData(FileUtils.readFileToByteArray(dto.getData()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return entity;
	}
}
