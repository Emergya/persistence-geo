/*
 * LayerAdminServiceImpl.java
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
package com.emergya.persistenceGeo.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.emergya.persistenceGeo.dao.MultiSirDatabaseGenericDAO;
import com.emergya.persistenceGeo.dao.RuleEntityDao;
import com.emergya.persistenceGeo.dao.StyleEntityDao;
import com.emergya.persistenceGeo.dto.RuleDto;
import com.emergya.persistenceGeo.dto.StyleDto;
import com.emergya.persistenceGeo.metaModel.AbstractRuleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractStyleEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;
import com.emergya.persistenceGeo.service.StyleAdminService;

/**
 * StyleAdminServiceImpl transactional implementation based on daos uses
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
@SuppressWarnings("unchecked")
@Service
public class StyleAdminServiceImpl extends
		AbstractServiceImpl<StyleDto, AbstractStyleEntity> implements
		StyleAdminService {

	@Resource
	private Instancer instancer;

	@Resource
	private StyleEntityDao styleDao;

	@Resource
	private RuleEntityDao ruleDao;

	@Override
	public StyleDto createStyle(StyleDto style) {
		return (StyleDto) this.create(style);
	}

	@Override
	public void modifyStyle(StyleDto style) {
		this.update(style);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public StyleDto addRuleToStyle(RuleDto rule, Long idStyle) {
		AbstractStyleEntity style = styleDao.findById(idStyle, true);

		AbstractRuleEntity ruleEntity = null;
		if (rule.getRule_id() == null) {
			ruleEntity = ruleDao.makePersistent(dtoRuleToEntity(rule));
		} else {
			ruleEntity = dtoRuleToEntity(rule);
		}

		if (style.getRuleList() == null) {
			style.setRuleList(new LinkedList());
		}
		style.getRuleList().add(ruleEntity);
		styleDao.makePersistent(style);

		return entityToDto(style);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public StyleDto removeRuleFromStyle(Long idRule, Long idStyle) {
		AbstractStyleEntity style = styleDao.findById(idStyle, true);

		if (style.getRuleList() != null) {
			List<AbstractRuleEntity> rules = style.getRuleList();
			List rulesToSave = new LinkedList();
			for (AbstractRuleEntity rule : rules) {
				if (!rule.getId().equals(idRule)) {
					rulesToSave.add(rule);
				}
			}
			style.setRuleList(rulesToSave);
		}
		styleDao.makePersistent(style);

		return entityToDto(style);
	}

	@Override
	public void modifyRule(RuleDto rule) {
		ruleDao.makePersistent(dtoRuleToEntity(rule));
	}

	@Override
	public void linkStyleToLayer(Long idStyle, Long idLayer) {
		// TODO: Save at Layer entity
	}

	@Override
	public void unlinkStyleToLayer(Long idStyle, Long idLayer) {
		// TODO: Save at Layer entity
	}

	@Override
	protected StyleDto entityToDto(AbstractStyleEntity entity) {
		StyleDto dto = null;
		if (entity != null) {
			dto = new StyleDto();
			dto.setId(entity.getId());
			dto.setCreateDate(entity.getCreateDate());
			// TODO: dto.setLayerList(layerList)
			dto.setName(entity.getName());
			// TODO: dto.setRuleList(ruleList);
			dto.setUpdateDate(entity.getUpdateDate());
		}

		return dto;
	}

	@Override
	protected AbstractStyleEntity dtoToEntity(StyleDto dto) {
		Date now = new Date();
		AbstractStyleEntity entity = null;
		if (dto != null) {
			entity = instancer.createStyle();
			entity.setId(dto.getId());
			entity.setCreateDate(dto.getCreateDate() != null ? dto
					.getCreateDate() : now);
			// TODO: entity.setLayerList(layerList)
			entity.setName(dto.getName());
			// TODO: entity.setRuleList(ruleList);
			entity.setUpdateDate(now);
		}

		return entity;
	}

	protected RuleDto entityRuleToDto(AbstractRuleEntity entity) {
		RuleDto dto = null;
		if (entity != null) {
			dto = new RuleDto();
			// dto.setId(entity.getId());
			dto.setCreateDate(entity.getCreateDate());
			// TODO: dto.setLayerList(layerList)
			// dto.setName(entity.getName());
			// TODO: dto.setRuleList(ruleList);
			dto.setUpdateDate(entity.getUpdateDate());
		}

		return dto;
	}

	protected AbstractRuleEntity dtoRuleToEntity(RuleDto dto) {
		Date now = new Date();
		AbstractRuleEntity entity = null;
		if (dto != null) {
			entity = instancer.createRule();
			// entity.setId(dto.getId());
			entity.setCreateDate(dto.getCreateDate() != null ? dto
					.getCreateDate() : now);
			// TODO: entity.setLayerList(layerList)
			// entity.setName(dto.getName());
			// TODO: entity.setRuleList(ruleList);
			entity.setUpdateDate(now);
		}

		return entity;
	}

	@Override
	protected MultiSirDatabaseGenericDAO<AbstractStyleEntity, Long> getDao() {
		return styleDao;
	}

}
