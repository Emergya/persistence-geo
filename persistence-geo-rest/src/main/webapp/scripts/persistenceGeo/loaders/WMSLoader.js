/*
 * WMSLoader.js Copyright (C) 2012 This file is part of PersistenceGeo project
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
 *  class = WMSLoader
 */
Ext.namespace("PersistenceGeoParser.loaders.WMSLoader");

/**
 * Class: PersistenceGeoParser.WMSLoader
 * 
 * Loader for WMS Layers
 * 
 */
PersistenceGeoParser.loaders.WMSLoader =
{

	load: function (layerData){
		var visibility = PersistenceGeoParser.AbstractLoader.toBoolean(layerData.properties.visibility);
		var transparent = PersistenceGeoParser.AbstractLoader.toBoolean(layerData.properties.transparent);
		var isBaseLayer = PersistenceGeoParser.AbstractLoader.toBoolean(layerData.properties.isBaseLayer);
		var opacity = PersistenceGeoParser.AbstractLoader.toNumber(layerData.properties.opacity);
		var buffer = PersistenceGeoParser.AbstractLoader.toNumber(layerData.properties.buffer);
		
		
		var layer = new OpenLayers.Layer.WMS(
				layerData.name,
				layerData.server_resource,
				{
					layers: layerData.properties.layers,
	    			transparent: transparent
				},
				{
					format: layerData.properties.format,
	    			isBaseLayer: isBaseLayer,
	    			visibility: visibility,
	     			opacity: opacity,
	    			buffer : buffer
				});
		
		//TODO: Wrap 
		PersistenceGeoParser.AbstractLoader.postFunctionsWrapper(layerData, layer);
		
		return layer;
	}
};
