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

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.AbstractGenericDao;
import com.emergya.persistenceGeo.dao.AuthorityEntityDao;
import com.emergya.persistenceGeo.dao.FolderEntityDao;
import com.emergya.persistenceGeo.dao.LayerEntityDao;
import com.emergya.persistenceGeo.dao.RuleEntityDao;
import com.emergya.persistenceGeo.dao.StyleEntityDao;
import com.emergya.persistenceGeo.dao.UserEntityDao;
import com.emergya.persistenceGeo.dto.LayerDto;
import com.emergya.persistenceGeo.dto.RuleDto;
import com.emergya.persistenceGeo.dto.StyleDto;
import com.emergya.persistenceGeo.metaModel.AbstractAuthorityEntity;
import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;
import com.emergya.persistenceGeo.metaModel.AbstractRuleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractStyleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;
import com.emergya.persistenceGeo.service.LayerAdminService;

/**
 * LayerAdminService transactional implementation based on daos uses
 * {@link AbstractGenericDao}
 * 
 * @author <a href="mailto:marcos@emergya.com">marcos</a>
 * 
 */
@Repository
@Transactional
public class LayerAdminServiceImpl extends AbstractServiceImpl<LayerDto, AbstractLayerEntity>
		implements LayerAdminService {

	@Resource
	private Instancer instancer;
	@Resource
	private LayerEntityDao layerDao;
	@Resource
	private StyleEntityDao styleDao;
	@Resource
	private RuleEntityDao ruleDao;
	@Resource
	private UserEntityDao userDao;
	@Resource
	private AuthorityEntityDao authDao;
	@Resource
	private FolderEntityDao folderDao;
	
	public LayerAdminServiceImpl(){
		super();
	}

	/**
	 * Get a layer list by name
	 * 
	 * @param layerName
	 * 
	 * @return If not found, it's created
	 */
	public List<LayerDto> getLayersByName(String layerName) {
		List<LayerDto> layersDto = new LinkedList<LayerDto>();
		LayerDto dto = null;
		List<AbstractLayerEntity> layers = layerDao.getLayers(layerName);
		for(AbstractLayerEntity l: layers){
			dto = entityToDto(l);
			if(dto == null){
				dto = entityToDto(layerDao.createLayer(layerName));
			}
			layersDto.add(dto);
		}
		return layersDto;
	}
	
	/**
	 * Get a layer list by names list
	 * 
	 * @param namesList
	 * 
	 * @return If not found, it's created
	 */
	public List<LayerDto> getLayersByName(List<String> namesList) {
		List<LayerDto> layersDto = new LinkedList<LayerDto>();
		for(String name: namesList){
			layersDto.addAll(this.getLayersByName(name));
		}
		return layersDto;
	}

	/**
	 * Get a styles list by layer
	 * 
	 * @param layer
	 * 
	 * @return If not found, it's created
	 */
	public List<StyleDto> getStylesByLayer(LayerDto layer) {
		List<StyleDto> stylesDto = new LinkedList<StyleDto>();
		StyleDto dto = null;
		List<String> stylesString = layer.getStyleList();
		List<AbstractStyleEntity> stylesEntity = null;
		for(String s: stylesString){
			stylesEntity = styleDao.getStyles(s);
			for(AbstractStyleEntity se: stylesEntity){
				dto = styleEntityToDto(se);
				if(dto == null){
					dto = styleEntityToDto(styleDao.createStyle(s));
				}
				stylesDto.add(dto);
			}
		}
		return stylesDto;
	}

	/**
	 * Get a rules list by layer style
	 * 
	 * @param style
	 * 
	 * @return If not found, it's created
	 */
	public List<RuleDto> getRulesByStyle(StyleDto style) {
		List<RuleDto> rulesDto = new LinkedList<RuleDto>();
		RuleDto dto = null;
		List<Long> rulesString = style.getRuleList();
		List<AbstractRuleEntity> rulesEntity = null;
		for(Long s: rulesString){
			rulesEntity = ruleDao.findAll();
			for(AbstractRuleEntity re: rulesEntity){
				if(s.equals(re.getId())){
					dto = ruleEntityToDto(re);
					if(dto == null){
						dto = ruleEntityToDto(ruleDao.createRule());
					}
					rulesDto.add(dto);
				}
			}
		}
		return rulesDto;
	}

	/**
	 * Add a rule to a layer style
	 * 
	 * @param styleID
	 * @param ruleID
	 * 
	 */
	public void addRuleToStyleLayer(Long styleID, Long ruleID) {
		AbstractStyleEntity styleEntity = styleDao.findById(styleID, false);
		List<AbstractRuleEntity> rules = styleEntity.getRuleList();
		if(rules == null){
			rules = new LinkedList<AbstractRuleEntity>();
		}
		boolean enc = false;
		for(AbstractRuleEntity re: rules){
			if(re.getId().equals(ruleID)){
				enc = true;
				break;
			}
		}
		if(!enc){
			rules.add(ruleDao.findById(ruleID, false));
			styleEntity.setRuleList(rules);
			styleDao.makePersistent(styleEntity);
		}
	}

	/**
	 * Add a style to a layer
	 * 
	 * @param layerID
	 * @param styleID
	 * 
	 */
	public void addStyleToLayer(Long layerID, Long styleID) {
		AbstractLayerEntity layerEntity = layerDao.findById(layerID, false);
		List<AbstractStyleEntity> styles = layerEntity.getStyleList();
		if(styles == null){
			styles = new LinkedList<AbstractStyleEntity>();
		}
		boolean enc = false;
		for(AbstractStyleEntity se: styles){
			if(se.getId().equals(styleID)){
				enc = true;
				break;
			}
		}
		if(!enc){
			styles.add(styleDao.findById(styleID, false));
			layerEntity.setStyleList(styles);
			layerDao.save(layerEntity);
		}

	}
	
	/**
	 * Add authorities to a layer
	 * 
	 * @param auth_id
	 * @param styleID
	 * 
	 */
	public void addAuthoritiesToLayer(Long auth_id, Long layer_id) {
		AbstractLayerEntity entity = layerDao.findById(layer_id, false);
		AbstractAuthorityEntity authority = entity.getAuth();
		if(authority == null){
			authority = instancer.createAuthority();
		}
		boolean enc = false;
		if(authority.getId().equals(auth_id)){
			enc = true;
		}
		
		if(!enc){
			entity.setAuth(authDao.findById(auth_id, false));
			layerDao.save(entity);
		}
	}

	/**
	 * Add a user to a layer
	 * 
	 * @param user_id
	 * @param layer_id
	 * 
	 */
	public void addUserToLayer(Long user_id, Long layer_id) {
		AbstractLayerEntity entity = layerDao.findById(layer_id, false);
		AbstractUserEntity user = entity.getUser();
		if(user == null){
			user = instancer.createUser();
		}
		boolean enc = false;
		if(user.getId().equals(user_id)){
			enc = true;
		}
		
		if(!enc){
			entity.setUser(userDao.findById(user_id, false));
			layerDao.save(entity);
		}
	}
	
	/**
	 * Add a folder to a layer
	 * 
	 * @param folder_id
	 * @param layer_id
	 * 
	 */
	public void addFolderToLayer(Long folder_id, Long layer_id){
		AbstractLayerEntity entity = layerDao.findById(layer_id, false);
		List<AbstractFolderEntity> folders = entity.getFolderList();
		if(folders == null){
			folders = new LinkedList<AbstractFolderEntity>();
		}
		boolean enc = false;
		for(AbstractFolderEntity fe: folders){
			if(fe.getId().equals(folder_id)){
				enc = true;
				break;
			}
		}
		if(!enc){
			folders.add(folderDao.findById(folder_id, false));
			entity.setFolderList(folders);
			layerDao.save(entity);
		}
	}

	protected LayerDto entityToDto(AbstractLayerEntity entity) {
		LayerDto dto = null;
		if(entity != null){
			dto = new LayerDto();
			// Add own parameters
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setOrder(entity.getOrder());
			dto.setType(entity.getType());
			dto.setServer_resource(entity.getServer_resource());
			dto.setPublicized(entity.getPublicized());
			dto.setEnabled(entity.getEnabled());
			dto.setPertenece_a_canal(entity.getPertenece_a_canal());
			dto.setCreateDate(entity.getFechaCreacion());
			dto.setUpdateDate(entity.getFechaActualizacion());
			
			// Add relational parameters
			// Add users
			AbstractUserEntity user = layerDao.findByLayer(entity.getId());
			if(user != null){
				dto.setUser(user.getNombreCompleto());
			}
			// Add authorities
			List<AbstractAuthorityEntity> authorities = authDao.findByLayer(entity.getId());
			if(authorities != null){
				// Authorities have just one element
				dto.setAuth(authorities.get(0).getAuthority());
			}
			// Add style
			List<String> styleDto = new LinkedList<String>();
			List<AbstractStyleEntity> styles = layerDao.findStyleByLayer(entity.getId());
			if(styles != null){
				for(AbstractStyleEntity styleEntity: styles){
					styleDto.add(styleEntity.getName());
				}
			}
			dto.setStyleList(styleDto);
			// Add folder
			List<String> folderDto = new LinkedList<String>();
			List<AbstractFolderEntity> folders = layerDao.findFolderByLayer(entity.getId());
			if(folders != null){
				for(AbstractFolderEntity folderEntity: folders){
					folderDto.add(folderEntity.getName());
				}
			}
			dto.setFolderList(folderDto);
		}
		return dto;
	}

	protected AbstractLayerEntity dtoToEntity(LayerDto dto) {
		AbstractLayerEntity entity = null;
		if(dto != null){
			Date now = new Date();
			if(dto.getId() != null && dto.getId() > 0){
				entity = (AbstractLayerEntity) layerDao.findById(dto.getId(), true);
				//Grupos
				authDao.clearUser(dto.getId());
			}else{
				entity =  layerDao.createLayer(dto.getName());
				entity.setFechaCreacion(now);
			}
			// Add own parameters
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setOrder(dto.getOrder());
			entity.setType(dto.getType());
			entity.setServer_resource(dto.getServer_resource());
			entity.setPublicized(dto.getPublicized());
			entity.setEnabled(dto.getEnabled());
			entity.setPertenece_a_canal(dto.getPertenece_a_canal());
			entity.setFechaCreacion(dto.getCreateDate());
			entity.setFechaActualizacion(dto.getUpdateDate());
			// Add relational parameters
			// Add users
			String usersDto = dto.getUser();
			if(usersDto != null){
				AbstractUserEntity user = userDao.getUser(usersDto);
				if(user != null){
					this.addUserToLayer((Long) user.getId(), dto.getId());
				}
			}
			// Add authorities
			String authDto = dto.getAuth();
			if(authDto != null){
				List<AbstractAuthorityEntity> authorities = authDao.findByName(authDto);
				if(authorities != null){
					for(AbstractAuthorityEntity authEntity: authorities){
						this.addAuthoritiesToLayer(authEntity.getId(), dto.getId());
					}
				}
			}
			// Add style
			List<String> styleDto = dto.getStyleList();
			if(styleDto  != null){
				List<AbstractStyleEntity> styles = styleDao.findByName(styleDto);
				if(styles != null){
					for(AbstractStyleEntity styleEntity: styles){
						this.addStyleToLayer(dto.getId(), styleEntity.getId());
					}
				}
			}
			// Add folder
			List<String> folderDto = dto.getFolderList();
			if(folderDto != null){
				List<AbstractFolderEntity> folders = folderDao.findByName(folderDto);
				if(folders != null){
					for(AbstractFolderEntity folderEntity: folders){
						this.addFolderToLayer(folderEntity.getId(), dto.getId());
					}
				}
			}
		}
		return entity;
	}

	private StyleDto styleEntityToDto(AbstractStyleEntity entity){
		StyleDto dto = null;
		if(entity != null){
			dto = new StyleDto();
			// Add own attributes
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setCreateDate(entity.getFechaCreacion());
			dto.setUpdateDate(entity.getFechaActualizacion());
			// Add relational attributes
			// Add layers
			List<String> layersDto = new LinkedList<String>();
			List<AbstractLayerEntity> layers = entity.getLayerList();
			if(layers != null){
				for(AbstractLayerEntity layerEntity: layers){
					layersDto.add(layerEntity.getName());
				}
			}
			dto.setLayerList(layersDto);
			// Add rules
			List<Long> rulesDto = new LinkedList<Long>();
			List<AbstractRuleEntity> rules = entity.getRuleList();
			if(rules != null){
				for(AbstractRuleEntity ruleEntity: rules){
					rulesDto.add((Long) ruleEntity.getId());
				}
			}
			dto.setRuleList(rulesDto);
		}
		return dto;
	}
	
	private RuleDto ruleEntityToDto(AbstractRuleEntity entity){
		RuleDto dto = null;
		if(entity != null){
			dto = new RuleDto();
			// Add own attributes
			dto.setRule_id((Long )entity.getId());
			dto.setSymbolizer(entity.getSymbolizer());
			dto.setFilter(entity.getFilter());
			dto.setCreateDate(entity.getFechaCreacion());
			dto.setUpdateDate(entity.getFechaActualizacion());
			// Add relational attributes
			// Add style
			dto.setStyle(entity.getStyle().getName());
		}
		return dto;
	}

	

}
