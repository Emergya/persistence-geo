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
 *  module = PersistenceGeoParser
 */
Ext.namespace("PersistenceGeoParser.loaders");

/** api: (define)
 *  module = PersistenceGeoParser.loaders
 *  class = WSLoader
 */
Ext.namespace("PersistenceGeoParser.loaders.WFSLoader");

/**
 * Class: PersistenceGeoParser.laoders.WFSLoader
 * 
 * Loader for WFS Layers
 */ 
PersistenceGeoParser.loaders.WFSLoader =
	{
		load: function (layerData){
			var renderer = OpenLayers.Util
				.getParameters(window.location.href).renderer;
			var layer = new OpenLayers.Layer.Vector(
					layerData['name'],
					{
						'groupLayers' : layerData['groupLayers'],
						'visibility' : layerData['visibility'],
//TODO						'strategies' : _strategies,
						'protocol' : new OpenLayers.Protocol.WFS(
								{
									url : OpenLayers.ProxyHost + layerData['properties']['url'], 
						            maxFeatures: layerData['properties']['maxFeatures'],
						            featureType: layerData['properties']['featureType'],
						            featureNS: layerData['properties']['featureNS'],
						            featurePrefix: layerData['properties']['featurePrefix'],
						            geometryName: layerData['properties']['geometryName'],
						            schema: layerData['properties']['schema']
								}),
						'renderers' : renderer
					});
			
			//TODO: Wrap 
			PersistenceGeoParser.AbstractLoader.postFunctionsWrapper(layerData, layer);
			
			return layer;
		}
};
