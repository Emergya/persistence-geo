/*
 * PersistenceGeoParser.js Copyright (C) 2012 This file is part of PersistenceGeo project
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

/**
 * Class: PersistenceGeoParser
 * 
 * The PersistenceGeoParser is designed to parse data behind persistenceGeo 
 * library and sicecat viewer
 */
PersistenceGeoParser = 
				{
					
					INDEX_LAYER: 1,
					SQL_INSERT_LAYER: "INSERT INTO layer(id, enabled, name_layer," +
									"order_layer, pertenece_a_canal, publicized, server_resource," +
									"auth_id, layer_typ_id, user_id)" +
									"VALUES ({0}, {1}, '{2}', {3}, {4}, {5},'{6}', 1, {7},1); ",
					SQL_INSERT_LAYER_PROPERTY: "INSERT INTO persistence_geo.layer_type_layer_type_property(layer_type_id, defaultproperties_id)" 
						+ "VALUES ({0}, {1});",
					SQL_LAYER_PROPERTIES:{
						1: 'projection',2: 'url',3: 'testLayer',
						4: 'layers',5: 'transparent',6: 'format',
						7: 'isBaseLayer',8: 'opacity',9: 'visibility',
						10: 'resolutions',11: 'buffer',12: 'minX',
						13: 'minY',14: 'maxX',15: 'maxY'
					},
					INDEX_PROPERTY: 1,
					SQL_INSERT_PROPERTY: "INSERT INTO layer_property(id, name, value) VALUES ({0}, '{1}', '{2}');",
					SQL_INSERT_PROPERTY_REL: "INSERT INTO layer_layer_property(layer_id, properties_id) VALUES ({0}, {1});",
					
					LAYER_TYPES: {"WMS":1,"WFS":2,"KML":3,"GML":5,"TEXT":6,"WMST":7},
					
					insertSQL: "",
					
					LOADERS:{
						"WMS":1,
						"WFS":2,
						"KML":3,
						"GML":5,
						"TEXT":6,
						"WMST":7
					},
					
					SAVE_LAYER_BASE_URL:"rest/persistenceGeo/saveLayerByUser/",
					
					LOAD_LAYERS_BY_USER_BASE_URL:"../rest/persistenceGeo/loadLayers/",
					
					getIdLayerProperty: function(nameProperty){
						for (var key in this.SQL_LAYER_PROPERTIES){
							if(this.SQL_LAYER_PROPERTIES[key] == nameProperty)
								return key;
						}
						return null;
					},
					
					getLayerTypeFromJson: function(layerType){
						return this.LAYER_TYPES[layerType];
					},
					
					generateLayerFromJson: function(layerToLoad, group, subGroup){
						var name = layerToLoad['name'];
						var url = layerToLoad['url'];
						var layerOp1 = layerToLoad['layerOp1'];
						var layerOp2 = layerToLoad['layerOp2'];
						while (name.indexOf("'") > 0){
							name = name.replace("'","\"");
						}
						//console.log(name);
						var insert = String.format(this.SQL_INSERT_LAYER, this.INDEX_LAYER, "true", 
								name, "1", "false", "true", url, 
								"" + this.getLayerTypeFromJson(layerToLoad['type']));
						this.insertSQL += insert + "\n";
						this.generateLayerPropertiesFromJson(layerOp1, layerOp2, this.INDEX_LAYER++);
					},
					
					parseValueFromJson: function(value){
							return value + "";
					},
					
					generateLayerPropertiesFromJson: function(layerOp1, layerOp2, idLayer){
						for (var key in layerOp1){
							var insert = String.format(this.SQL_INSERT_PROPERTY, this.INDEX_PROPERTY, 
									key, this.parseValueFromJson(layerOp1[key]));
							this.insertSQL += insert + "\n";
							insert = String.format(this.SQL_INSERT_PROPERTY_REL, idLayer, this.INDEX_PROPERTY++);
							this.insertSQL += insert + "\n";
						}
						for (var key in layerOp2){
							var insert = String.format(this.SQL_INSERT_PROPERTY, this.INDEX_PROPERTY, 
									key, this.parseValueFromJson(layerOp2[key]));
							this.insertSQL += insert + "\n";
							insert = String.format(this.SQL_INSERT_PROPERTY_REL, idLayer, this.INDEX_PROPERTY++);
							this.insertSQL += insert + "\n";
						}
					},
					
					loadLayersByUser: function(user, onload){
						store = new Ext.data.JsonStore({
				             url: this.LOAD_LAYERS_BY_USER_BASE_URL + user,
				             remoteSort: false,
				             autoLoad:true,
				             idProperty: 'id',
				             root: 'data',
				             totalProperty: 'results',
				             fields: ['id','name','properties',
				                      'type','auth','order',
				                      'user','folderList','styleList',
				                      'createDate','server_resource',
				                      'publicized','enabled','updateDate'],
				             listeners: {
				                 load: function(store, records, options) {
				                	 if(!!onload){
				                		 PersistenceGeoParser.getLayers(records, onload);
				                	 }else{
				                		 PersistenceGeoParser.getLayers(records); 
				                	 }
				                 }
				             }
				         });
					},
					
					getLayers: function(records, onload){
						var layers = new Array();
						var i = 0; 
	                	while (i<records.length){
	                		if(records[i].data.type == "WMS"){
	                			layers.push(PersistenceGeoParser.WMSLoader.load(records[i].data));
	                		}else if(records[i].data.type == "WMST"){
//	                			layers.push(PersistenceGeoParser.WMSTLoader.load(records[i].data));
	                		}
	                		i++;
	                	}
	                	if(!!onload){
	                		 onload(layers);
	                	 }else{
	                		 PersistenceGeoParser.defaultOnLoad(layers); 
	                	 }
					},
					
					defaultOnLoad: function(layers){
						map.addLayers(layers);
					},


					fromTextArrayToNumberArray: function (textArray){
						var result = new Array();
						for (var i = 0; i < textArray.length; i++){
							result.push(Number(textArray[i]));
						}
						return result;
					},

					fromTextArrayToBound: function (textArray){
						var result = PersistenceGeoParser.fromTextArrayToNumberArray(textArray); 
						return new OpenLayers.Bounds(result[0],result[1],result[2],result[3]);
					}
					
			};

/**
 * Class: PersistenceGeoParser.WMSLoader
 * 
 * Loader for WMS Layers
 * 
 */
PersistenceGeoParser.WMSLoader ={
		load: function (layerData){
			var layer = new OpenLayers.Layer.WMS(
					layerData.name,
					(OpenLayers.ProxyHost
							.indexOf("url2") != -1 ? OpenLayers.ProxyHost
							: "")
							+ layerData.server_resource,
					{
						layers: layerData.properties.layers,
		    			transparent: layerData.properties.transparent
					},
					{
						format: layerData.properties.format,
		    			isBaseLayer: layerData.properties.isBaseLayer,
		    			visibility: layerData.properties.visibility,
		     			opacity: layerData.properties.opacity,
		    			buffer : layerData.properties.buffer
					});
			//TODO: group
			layer.subgroupLayers = "overlays";
			return layer;
		}
};



/**
 * Class: PersistenceGeoParser.WMSTLoader
 * 
 * Loader for WMST Layers
 * 
 */
PersistenceGeoParser.WMSTLoader ={
		load: function (layerData){
			//TODO
		}
};