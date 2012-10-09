/*
 * GMLLoader.js Copyright (C) 2012 This file is part of PersistenceGeo project
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
 * Authors: Moisés Arcos Santiago (mailto:marcos@emergya.com)
 */
/**
 * api: (define) module = PersistenceGeoParser
 */
Ext.namespace("PersistenceGeoParser.loaders");

/**
 * api: (define) module = PersistenceGeoParser.loaders class = GMLLoader
 */
Ext.namespace("PersistenceGeoParser.loaders.GMLLoader");

/**
 * Class: PersistenceGeoParser.KMLLoader
 * 
 * Loader for GML Layers
 * 
 */
PersistenceGeoParser.loaders.GMLLoader = {
	/*
	 * Method: formatType
	 * 
	 * Type format of the layer to Load (KML)
	 */
	formatType : function() {
		var internalProjection = new OpenLayers.Projection(this.map.projection);
		var externalProjection = new OpenLayers.Projection(
				this.externalProjection);

		var formatType;
		if (this.type == this.TYPE_GML_V3) {
			formatType = new OpenLayers.Format.GML.v3({
				internalProjection : internalProjection,
				externalProjection : externalProjection,
				featureType : "feature",
				featureNS : "http://example.com/feature",
				extractStyles : true,
				extractAttributes : true,
				maxDepth : 2
			});
		} else {
			formatType = new OpenLayers.Format.GML({
				internalProjection : internalProjection,
				externalProjection : externalProjection,
				extractStyles : true,
				extractAttributes : true,
				maxDepth : 2
			});
		}

		return formatType;
	},

	load : function(layerData, layerTree) {

		var layer = new OpenLayers.Layer.Vector(layerData.name, {
			strategies : [ new OpenLayers.Strategy.Fixed() ],
			protocol : new OpenLayers.Protocol.HTTP({
				url : layerData.server_resource,
				format : this
						.formatType(layerData.properties.externalProjection),
				srsName : map.projection
			})
		});

		// TODO: Wrap
		PersistenceGeoParser.AbstractLoader.postFunctionsWrapper(layerData,
				layer, layerTree);

		return layer;
	}
};