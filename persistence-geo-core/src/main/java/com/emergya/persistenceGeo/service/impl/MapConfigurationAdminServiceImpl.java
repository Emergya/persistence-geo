/*
 * LayerAdminServiceImpl.java
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
 * Authors:: Moisés Arcos Santiago (mailto:marcos@emergya.com)
 */
package com.emergya.persistenceGeo.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.AbstractGenericDao;
import com.emergya.persistenceGeo.dao.AuthorityEntityDao;
import com.emergya.persistenceGeo.dao.FolderEntityDao;
import com.emergya.persistenceGeo.dao.GenericDAO;
import com.emergya.persistenceGeo.dao.LayerEntityDao;
import com.emergya.persistenceGeo.dao.LayerTypeEntityDao;
import com.emergya.persistenceGeo.dao.MapConfigurationEntityDao;
import com.emergya.persistenceGeo.dao.RuleEntityDao;
import com.emergya.persistenceGeo.dao.StyleEntityDao;
import com.emergya.persistenceGeo.dao.UserEntityDao;
import com.emergya.persistenceGeo.dto.FolderDto;
import com.emergya.persistenceGeo.dto.LayerDto;
import com.emergya.persistenceGeo.dto.MapConfigurationDto;
import com.emergya.persistenceGeo.dto.RuleDto;
import com.emergya.persistenceGeo.dto.StyleDto;
import com.emergya.persistenceGeo.metaModel.AbstractAuthorityEntity;
import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerPropertyEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerTypeEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerTypePropertyEntity;
import com.emergya.persistenceGeo.metaModel.AbstractMapConfigurationEntity;
import com.emergya.persistenceGeo.metaModel.AbstractRuleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractStyleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;
import com.emergya.persistenceGeo.service.LayerAdminService;
import com.emergya.persistenceGeo.service.MapConfigurationAdminService;

/**
 * LayerAdminService transactional implementation based on daos uses
 * {@link AbstractGenericDao}
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 * 
 */
@SuppressWarnings("unchecked")
@Repository
@Transactional
public class MapConfigurationAdminServiceImpl extends AbstractServiceImpl<MapConfigurationDto, AbstractMapConfigurationEntity>
		implements MapConfigurationAdminService {

	
	@Resource
	private MapConfigurationEntityDao mapConfigurationDao;
	
	public MapConfigurationAdminServiceImpl(){
		super();
	}

	@Override
	public void createMapConfiguration(String minX, String minY, String maxX,
			String maxY, String iProy, String res) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMapConfiguration(Long mapConfigurationID, String bbox, String projection, String resolutions) {
		mapConfigurationDao.updateMapConfiguration(mapConfigurationID, bbox, projection, resolutions);
		
	}

	@Override
	public void removeMapConfiguration(Long mapConfigurationID) {
		mapConfigurationDao.removeMapConfiguration(mapConfigurationID);
		
	}

	@Override
	protected GenericDAO<AbstractMapConfigurationEntity, Long> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MapConfigurationDto entityToDto(
			AbstractMapConfigurationEntity entity) {
		
		MapConfigurationDto mc = null;
			if(entity!=null)
			{
				mc = new MapConfigurationDto();
				mc.setBbox(entity.getBbox());
				mc.setDefaultIdioma(entity.getDefaultIdioma());
				mc.setDefaultUserLogo(entity.getDefaultUserLogo());
				mc.setDefaultWMSServer(entity.getDefaultWMSServer());
				mc.setDisplayProjection(entity.getDisplayProjection());
				mc.setDownloadServletURL(entity.getDownloadServletURL());
				//mc.setId(entity.getId());
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
			
		return null;
	}

	@Override
	protected AbstractMapConfigurationEntity dtoToEntity(MapConfigurationDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapConfigurationDto loadConfiguration() {
		
		return entityToDto(mapConfigurationDao.loadConfiguration());
	}

	
	
	
	
	
	
	
	
	
//	/**
//	 * Get a layer list by name
//	 * 
//	 * @param layerName
//	 * 
//	 * @return If not found, it's created
//	 */
//	public List<LayerDto> getLayersByName(String layerName) {
//		List<LayerDto> layersDto = new LinkedList<LayerDto>();
//		LayerDto dto = null;
//		List<AbstractLayerEntity> layers = layerDao.getLayers(layerName);
//		for(AbstractLayerEntity l: layers){
//			dto = entityToDto(l);
//			if(dto == null){
//				dto = entityToDto(layerDao.createLayer(layerName));
//			}
//			layersDto.add(dto);
//		}
//		return layersDto;
//	}
//	
//	/**
//	 * Get a layer list by names list
//	 * 
//	 * @param namesList
//	 * 
//	 * @return If not found, it's created
//	 */
//	public List<LayerDto> getLayersByName(List<String> namesList) {
//		List<LayerDto> layersDto = new LinkedList<LayerDto>();
//		for(String name: namesList){
//			layersDto.addAll(this.getLayersByName(name));
//		}
//		return layersDto;
//	}


}
