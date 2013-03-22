/*
 * UserAdminServiceImpl.java
 * 
 * Copyright (C) 2011
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

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.AbstractGenericDao;
import com.emergya.persistenceGeo.dao.GenericDAO;
import com.emergya.persistenceGeo.dao.ZoneEntityDao;
import com.emergya.persistenceGeo.dto.ZoneDto;
import com.emergya.persistenceGeo.metaModel.AbstractZoneEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;
import com.emergya.persistenceGeo.service.ZoneAdminService;

/**
 * Implementacion transacional de ZoneAdminService basada en el uso de daos
 * {@link AbstractGenericDao}
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
@Repository
@Transactional
public class ZoneAdminServiceImpl extends AbstractServiceImpl<ZoneDto, AbstractZoneEntity> implements ZoneAdminService {
	
	@Resource
	private ZoneEntityDao zoneDao;
	
	@Resource
	private Instancer instancer;

	@Override
	protected GenericDAO<AbstractZoneEntity, Long> getDao() {
		return zoneDao;
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.service.impl.AbstractServiceImpl#getAll()
	 */
	@Cacheable("persistenceGeo")
	public List<? extends Serializable> getAll() {
		// TODO Auto-generated method stub
		return super.getAll();
	}

	/**
	 * Find zone by id
	 * 
	 * @param type
	 * @param isEnabled
	 * 
	 * @return zones
	 */
    public List<ZoneDto> findByType(String type, Boolean isEnabled){
    	List<ZoneDto> zonesDto = new LinkedList<ZoneDto>();
        List<AbstractZoneEntity> zones = zoneDao.findByType(type);
        for (AbstractZoneEntity zoneEntity: zones) {
            zonesDto.add(entityToDto(zoneEntity));
        }
        return zonesDto;
    }


	/**
	 * Find zone by id
	 * 
	 * @param type
	 * 
	 * @return zones
	 */
    public List<ZoneDto> findByType(String type) {
        return findByType(type, null);
    }

	/**
	 * Find all enabled
	 * 
	 * @return zones enabled
	 */
    @SuppressWarnings("unchecked")
	public List<ZoneDto> findAllEnabled(){
    	return (List<ZoneDto>) entitiesToDtos(zoneDao.findAllEnabled());
    }

	/**
	 * Find zone by id
	 * 
	 * @param idParent
	 * @param isEnabled
	 * 
	 * @return zones
	 */
    @SuppressWarnings("unchecked")
    public List<ZoneDto> findByParent(Long idZone){
    	return (List<ZoneDto>) entitiesToDtos(zoneDao.findByParent(idZone));	
    }

	@Override
	protected ZoneDto entityToDto(AbstractZoneEntity entity) {
		ZoneDto dto = null;
		if(entity != null){
			dto = new ZoneDto();
			dto.setCreateDate(entity.getCreateDate());
			dto.setCode(entity.getCode());
			dto.setExtension(entity.getExtension());
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setType(entity.getType());
			dto.setUpdateDate(entity.getUpdateDate());
            dto.setEnabled(entity.getEnabled());
		}
		return dto;
	}

	@Override
	protected AbstractZoneEntity dtoToEntity(ZoneDto dto) {
		AbstractZoneEntity entity = null;
		if(dto != null){
			if(dto.getId()!= null){
				zoneDao.findById(dto.getId(), false);
			}else{
				entity = instancer.createZone();
			}
			entity.setCreateDate(dto.getCreateDate());
			entity.setCode(dto.getCode());
			entity.setExtension(dto.getExtension());
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setType(dto.getType());
			entity.setUpdateDate(dto.getUpdateDate());
            entity.setEnabled(dto.getEnabled());
		}
		return entity;
	}

}
