/*
 * SourceToolServiceImpl.java
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

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.emergya.persistenceGeo.dao.MultiSirDatabaseGenericDAO;
import com.emergya.persistenceGeo.dao.SourceToolEntityDao;
import com.emergya.persistenceGeo.dto.SourceToolDto;
import com.emergya.persistenceGeo.metaModel.AbstractSourceToolEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * SourceToolService transactional implementation based on daos uses
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
@Service("sourceToolService")
public class SourceToolServiceImpl extends
		AbstractServiceImpl<SourceToolDto, AbstractSourceToolEntity> {

	@Resource
	private Instancer instancer;
	@Resource
	private SourceToolEntityDao dao;

	public SourceToolServiceImpl() {
		super();
	}

	@Override
	protected MultiSirDatabaseGenericDAO<AbstractSourceToolEntity, Long> getDao() {
		return dao;
	}

	protected SourceToolDto entityToDto(AbstractSourceToolEntity entity) {
		SourceToolDto dto = null;
		if (entity != null) {
			dto = new SourceToolDto();
			// Add own parameters
			dto.setId((Long) entity.getId());
			dto.setName(entity.getName());
			dto.setPtype(entity.getPtype());
			dto.setConfig(entity.getConfig());
			dto.setUpdateDate(entity.getUpdateDate());
			dto.setCreateDate(entity.getCreateDate());
			dto.setUrl(entity.getUrl());
		}
		return dto;
	}

	protected AbstractSourceToolEntity dtoToEntity(SourceToolDto dto) {
		AbstractSourceToolEntity entity = null;
		if (dto != null) {
			if (dto.getId() != null && dto.getId() > 0) {
				entity = (AbstractSourceToolEntity) dao.findById(dto.getId(),
						false);
			} else {
				entity = instancer.createSourceToolEntity();
			}
			// Add own parameters
			entity.setName(dto.getName());
			entity.setPtype(dto.getPtype());
			entity.setConfig(dto.getConfig());
			entity.setUpdateDate(dto.getUpdateDate());
			entity.setCreateDate(dto.getCreateDate());
			entity.setUrl(dto.getUrl());
		}
		return entity;
	}

}
