/*
 * RestUserContextController.java Copyright (C) 2013 This file is part of persistenceGeo project
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import com.emergya.persistenceGeo.dto.FeatureDto;
import com.emergya.persistenceGeo.dto.LayerDto;
import com.emergya.persistenceGeo.service.UserContextService;

@Repository
public class UserContextServiceTemporalImplementation implements UserContextService, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3057980118934019000L;
	
	/**
	 * Loaded features by user
	 */
	private Map<Long, Map<Long, FeatureDto>> loadedFeatures = new ConcurrentHashMap<Long, Map<Long, FeatureDto>>();
	
	/**
	 * Loaded layers by user
	 */
	private Map<Long, Map<Long, LayerDto>> loadedLayers = new ConcurrentHashMap<Long, Map<Long, LayerDto>>();
	
	/**
	 * Clean temporary files
	 */
	@Scheduled(cron="0 15 04 * * ? *")
	public void clearTemporalMap() {
		loadedFeatures.clear();
		loadedLayers.clear();
	}

	@Override
	public Map<Long, FeatureDto> getUserFeatures(Long idUser) {
		return loadedFeatures.get(idUser);
	}

	@Override
	public Map<Long, LayerDto> getUserLayers(Long idUser) {
		return loadedLayers.get(idUser);
	}

	@Override
	public Map<Long, FeatureDto> saveFeature(FeatureDto feature, Long idUser) {
		Map<Long, FeatureDto> features = loadedFeatures.containsKey(idUser) 
				? loadedFeatures.get(idUser) 
						: new ConcurrentHashMap<Long, FeatureDto>();
		features.put(feature.getId(), feature);
		loadedFeatures.put(idUser, features);
		return features;
	}

	@Override
	public Map<Long, FeatureDto> removeFeature(Long idFeature, Long idUser) {
		Map<Long, FeatureDto> features = loadedFeatures.containsKey(idUser) 
				? loadedFeatures.get(idUser) 
						: new ConcurrentHashMap<Long, FeatureDto>(); 
		if(features.containsKey(idFeature)){
			features.remove(idFeature);
		}
		loadedFeatures.put(idUser, features);
		return features;
	}

	@Override
	public Map<Long, LayerDto> saveLayer(LayerDto layer, Long idUser) {
		Map<Long, LayerDto> layers = loadedLayers.containsKey(idUser) 
				? loadedLayers.get(idUser) : new ConcurrentHashMap<Long, LayerDto>();
		layers.put(layer.getId(), layer);
		loadedLayers.put(idUser, layers);
		return layers;
	}

	@Override
	public Map<Long, LayerDto> removeLayer(Long idLayer, Long idUser) {
		Map<Long, LayerDto> layers = loadedLayers.containsKey(idUser) 
				? loadedLayers.get(idUser) 
						: new ConcurrentHashMap<Long, LayerDto>(); 
		if(layers.containsKey(idLayer)){
			layers.remove(idLayer);
		}
		loadedLayers.put(idUser, layers);
		return layers;
	}
	
	@Override
	public Boolean clearTemporalElements(){
		loadedFeatures.clear();
		loadedLayers.clear();
		return true;
	}

}
