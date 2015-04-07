/*
 * MapConfigurationAdminServiceImpl.java Copyright (C) 2012 This file is part of persistenceGeo project
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

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.emergya.persistenceGeo.dao.AbstractGenericDao;
import com.emergya.persistenceGeo.dao.MapConfigurationEntityDao;
import com.emergya.persistenceGeo.dao.MultiSirDatabaseGenericDAO;
import com.emergya.persistenceGeo.dto.MapConfigurationDto;
import com.emergya.persistenceGeo.metaModel.AbstractMapConfigurationEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;
import com.emergya.persistenceGeo.service.MapConfigurationAdminService;

/**
 * MapConfigurationAdminServiceImpl transactional implementation based on daos
 * uses {@link AbstractGenericDao}
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
@Service
public class MapConfigurationAdminServiceImpl
		extends
		AbstractServiceImpl<MapConfigurationDto, AbstractMapConfigurationEntity>
		implements MapConfigurationAdminService {

	@Resource
	private MapConfigurationEntityDao mapConfigurationDao;

	@Resource
	private Instancer instancer;

	public MapConfigurationAdminServiceImpl() {
		super();
	}

	private static final String BBOX_SEPARATOR = ",";

	@Override
	public void createMapConfiguration(String minX, String minY, String maxX,
			String maxY, String iProy, String res) {
		MapConfigurationDto dto = new MapConfigurationDto();
		String bbox = minX + BBOX_SEPARATOR + minY + BBOX_SEPARATOR + maxX
				+ BBOX_SEPARATOR + maxY;
		dto.setBbox(bbox);
		dto.setInitalBbox(bbox);
		dto.setDisplayProjection(iProy);
		dto.setResolutions(res);
	}

	@Override
	public void updateMapConfiguration(Long mapConfigurationID, String bbox,
			String projection, String resolutions) {
		mapConfigurationDao.updateMapConfiguration(mapConfigurationID, bbox,
				projection, resolutions);
	}

	@Override
	public void removeMapConfiguration(Long mapConfigurationID) {
		mapConfigurationDao.removeMapConfiguration(mapConfigurationID);

	}

	@Override
	protected MultiSirDatabaseGenericDAO<AbstractMapConfigurationEntity, Long> getDao() {
		return mapConfigurationDao;
	}

	@Override
	protected MapConfigurationDto entityToDto(
			AbstractMapConfigurationEntity entity) {

		MapConfigurationDto mc = null;
		if (entity != null) {
			mc = new MapConfigurationDto();
			mc.setBbox(entity.getBbox());
			mc.setDefaultIdioma(entity.getDefaultIdioma());
			mc.setDefaultUserLogo(entity.getDefaultUserLogo());
			mc.setDefaultWMSServer(entity.getDefaultWMSServer());
			mc.setDisplayProjection(entity.getDisplayProjection());
			mc.setDownloadServletURL(entity.getDownloadServletURL());
			mc.setId((Long) entity.getId());
			mc.setInitalBbox(entity.getInitalBbox());
			mc.setMaxResolution(entity.getMaxResolution());
			mc.setMaxScale(entity.getMaxScale());
			mc.setMinResolution(entity.getMinResolution());
			mc.setMinScale(entity.getMinScale());
			mc.setNumZoomLevels(entity.getNumZoomLevels());
			mc.setOpenLayersProxyHost(entity.getOpenLayersProxyHost());
			mc.setPDFServer(entity.getPDFServer());
			mc.setProjection(entity.getProjection());
			mc.setResolutions(entity.getResolutions());
			mc.setUploadServletURL(entity.getUploadServletURL());
			mc.setVersion(entity.getVersion());
		}

		return mc;
	}

	@Override
	protected AbstractMapConfigurationEntity dtoToEntity(MapConfigurationDto dto) {

		AbstractMapConfigurationEntity entity = null;
		if (dto != null) {
			entity = instancer.createMapConfiguration();
			entity.setBbox(dto.getBbox());
			entity.setDefaultIdioma(dto.getDefaultIdioma());
			entity.setDefaultUserLogo(dto.getDefaultUserLogo());
			entity.setDefaultWMSServer(dto.getDefaultWMSServer());
			entity.setDisplayProjection(dto.getDisplayProjection());
			entity.setDownloadServletURL(dto.getDownloadServletURL());
			entity.setId(dto.getId());
			entity.setInitalBbox(dto.getInitalBbox());
			entity.setMaxResolution(dto.getMaxResolution());
			entity.setMaxScale(dto.getMaxScale());
			entity.setMinResolution(dto.getMinResolution());
			entity.setMinScale(dto.getMinScale());
			entity.setNumZoomLevels(dto.getNumZoomLevels());
			entity.setOpenLayersProxyHost(dto.getOpenLayersProxyHost());
			entity.setPDFServer(dto.getPDFServer());
			entity.setProjection(dto.getProjection());
			entity.setResolutions(dto.getResolutions());
			entity.setUploadServletURL(dto.getUploadServletURL());
			entity.setVersion(dto.getVersion());
		}

		return entity;
	}

	@Override
	@Cacheable("persistenceGeo")
	public MapConfigurationDto loadConfiguration() {
		return entityToDto(mapConfigurationDao.loadConfiguration());
	}

}
