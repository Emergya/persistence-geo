/*
 * WFSLoader.js Copyright (C) 2012 This file is part of PersistenceGeo project
 * 
 * This software is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 * 
 * Authors: Alejandro Diaz Torres (mailto:adiaz@emergya.com)
 */

/** api: (define)
 *  module = PersistenceGeo
 */
Ext.namespace("PersistenceGeo.loaders");

/** api: (define)
 *  module = PersistenceGeo.loaders
 *  class = WSLoader
 */
Ext.namespace("PersistenceGeo.loaders.WFSLoader");

/**
 * Class: PersistenceGeoParser.laoders.WFSLoader
 * 
 * Loader for WFS Layers
 */ 
PersistenceGeo.loaders.WFSLoader 
	= Ext.extend(PersistenceGeo.loaders.AbstractLoader,{
		load: function (layerData, layerTree){

			var _strategies = [
					new OpenLayers.Strategy.BBOX(),
					new OpenLayers.Strategy.Refresh({
						interval : 5000
					}) ];

			var maxFeatures = this.toNumber(layerData['properties']['maxFeatures']);
			var visibility = this.toBoolean(layerData['visibility']);
			
			var renderer = OpenLayers.Util
				.getParameters(window.location.href).renderer;

			var options = {
				url : OpenLayers.ProxyHost + (layerData['properties']['url'] ? 
						layerData['properties']['url'] : layerData.server_resource), 
	            maxFeatures: maxFeatures,
	            featureType: layerData['properties']['featureType'],
	            featureNS: layerData['properties']['featureNS'],
	            featurePrefix: layerData['properties']['featurePrefix'],
	            geometryName: layerData['properties']['geometryName'],
	            schema: layerData['properties']['schema']
			};

			this.copyAllPosibleProperties(layerData['properties'], options);
			var layer = new OpenLayers.Layer.Vector(
					layerData['name'],
					{
						'groupLayers' : layerData['groupLayers'],
						'visibility' : visibility,
						'strategies' : _strategies,
						'protocol' : new OpenLayers.Protocol.WFS(options)
						// ,
						// 'renderers' : renderer
					});
			
			this.postFunctionsWrapper(layerData, layer, layerTree);
			
			if(true){ //TODO from group
				layer.groupLayers = "editables";
				layer.subgroupLayers = "editables";
			}
			
			return layer;
		}
});
