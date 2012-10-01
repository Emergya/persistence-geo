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

/** api: (define)
 *  module = PersistenceGeoParser
 */
Ext.namespace("PersistenceGeoParser");

/**
 * Class: PersistenceGeoParser
 * 
 * The PersistenceGeoParser is designed to parse data behind persistenceGeo 
 * library and sicecat viewer
 */
PersistenceGeoParser = 
				{
						
					FOLDERS_ADDED:{},
					
					insertSQL: "",
					
					LOADERS:{
						"WMS":1,
						"WFS":2,
						"KML":3,
						"GML":5,
						"TEXT":6,
						"WMST":7
					},
					
					LOADERS_CLASSES: {
						"WMS":PersistenceGeoParser.loaders.WMSLoader,
						"WFS":PersistenceGeoParser.loaders.WFSLoader
//						,
//						"KML":PersistenceGeoParser.KMLLoader,
//						"GML":PersistenceGeoParser.GMLLoader,
//						"TEXT":PersistenceGeoParser.TextLoader,
//						,"WMST":PersistenceGeoParser.WMSTLoader
					},
					
					REST_COMPONENT_URL: "rest",
					
					/**
					 * Function getRestBaseUrl
					 * 
					 * Default to '../rest'. Override it as needed
					 */
					getRestBaseUrl: function (){
						return this.REST_COMPONENT_URL;
					},
										
					SAVE_LAYER_GROUP_BASE_URL: function (){
						return this.getRestBaseUrl() + "/persistenceGeo/saveLayerByGroup/";
					},
										
					SAVE_LAYER_BASE_URL: function (){
						return this.getRestBaseUrl() + "/persistenceGeo/saveLayerByUser/";
					},
					
					LOAD_LAYERS_BY_USER_BASE_URL: function() {
						return this.getRestBaseUrl() + "/persistenceGeo/loadLayers/";
					},
					
					LOAD_LAYERS_BY_GROUP_BASE_URL: function() {
						return this.getRestBaseUrl() + "/persistenceGeo/loadLayersByGroup/";
					},
					
					SAVE_FOLDER_BASE_URL: function(){
						return this.getRestBaseUrl()+ "/persistenceGeo/saveFolder/";
					},
					
					SAVE_FOLDER_GROUP_BASE_URL: function(){
						return this.getRestBaseUrl()+ "/persistenceGeo/saveFolderByGroup/";
					},
					
					LOAD_FOLDERS_BASE_URL: function(){
						return this.getRestBaseUrl()+ "/persistenceGeo/loadFolders/";
					},
					
					LOAD_FOLDERS_GROUP_BASE_URL: function(){
						return this.getRestBaseUrl()+ "/persistenceGeo/loadFoldersByGroup/";
					},
					
					LOADED_FOLDERS:{},
					
					getFolder: function (nameFolder){
						return this.LOADED_FOLDERS[nameFolder];
					},
					
					LOADED_FOLDERS_NAMES:{},
					
					getFolderName: function (idFolder){
						return this.LOADED_FOLDERS_NAMES[idFolder];
					},
					
					initFoldersByUser: function(user){
						this.initFolders(user, this.LOAD_FOLDERS_BASE_URL() + user);
					},
					
					initFoldersByGroup: function(idGroup){
						this.initFolders(idGroup, this.LOAD_FOLDERS_GROUP_BASE_URL() + idGroup);
					},
					
					/**
					 * Function: loadLayers
					 * 
					 * Loads OpenLayers layers and call to onload callback function (layers). 
					 * Used to load all user layers. 
					 */
					initFolders: function(userOrGroup, url){
						this.LOADED_FOLDERS = {};
						this.LOADED_FOLDERS_NAMES = {};
						var this_ = this;
						store = new Ext.data.JsonStore({
				             url: url,
				             remoteSort: false,
				             autoLoad:true,
				             idProperty: 'id',
				             root: 'data',
				             totalProperty: 'results',
				             fields: ['id','name'],
				             listeners: {
				                 load: function(store, records, options) {
										var i = 0; 
					                	while (i<records.length){
					                		if(!!records[i].data.id 
					                				&& !!records[i].data.name){
					                			var folderName = records[i].data.name;
//					                			if(folderName.indexOf(".") > 0){
//					                				folderName = folderName.substring(folderName.indexOf(".") + 1);
//					                			}
//					                			console.log(folderName);
					                			this_.LOADED_FOLDERS[folderName] = records[i].data.id;
					                			this_.LOADED_FOLDERS_NAMES[records[i].data.id] = folderName;
					                		}
					                		i++;
					                	}
				                 }
				             }
				         });
					},
					
					/**
					 * Property: SAVE_FOLDER_TYPES
					 * 
					 * {Map} with save folder types
					 */
					SAVE_FOLDER_TYPES: {
						USER: "USER",
						GROUP: "GROUP"
					},
					
					getIdLayerProperty: function(nameProperty){
						for (var key in this.SQL_LAYER_PROPERTIES){
							if(this.SQL_LAYER_PROPERTIES[key] == nameProperty)
								return key;
						}
						return null;
					},
					
					getLayerTypeFromJson: function(layerType){
						return this.LAYER_TYPES[layerType.toUpperCase()];
					},
					
					/**
					 * Function: loadLayersByUser
					 * 
					 * Loads OpenLayers layers and call to onload callback function (layers). 
					 * Used to load all user layers. Call to onloadcallback with an array of ``OpenLayers.Layer`` result.
					 */
					loadLayersByUser: function(user, onload){
						this.initFoldersByUser(user); //Caution!! you haven't getFolderName function available befor storeload
						this.loadLayers(user, onload, this.LOAD_LAYERS_BY_USER_BASE_URL() + user);
					},
					
					/**
					 * Function: loadLayersByUser
					 * 
					 * Loads OpenLayers layers and call to onload callback function (layers). 
					 * Used to load all user layers. Call to onloadcallback with an array of ``OpenLayers.Layer`` result.
					 */
					loadLayersByGroup: function(group, onload){
						this.initFoldersByGroup(group);
						this.loadLayers(group, onload, this.LOAD_LAYERS_BY_GROUP_BASE_URL() + group);
					},
					
					/**
					 * Function: loadLayers
					 * 
					 * Loads OpenLayers layers and call to onload callback function (layers). 
					 * Used to load all user layers. 
					 */
					loadLayers: function(userOrGroup, onload, url){
						store = new Ext.data.JsonStore({
				             url: url,
				             remoteSort: false,
				             autoLoad:true,
				             idProperty: 'id',
				             root: 'data',
				             totalProperty: 'results',
				             fields: ['id','name','properties',
				                      'type','auth','order',
				                      'user','folderList','styleList',
				                      'createDate','server_resource',
				                      'publicized','enabled','updateDate', 
				                      'folderId', 'authId', 'userId'],
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
	                		if(!!records[i].data.type
	                				&& !! this.LOADERS_CLASSES[records[i].data.type]){
	                			try{
	                				var layer = this.LOADERS_CLASSES[records[i].data.type].load(records[i].data);
	                				layers.push(layer);
	                			}catch (e){
	                				//TODO: Log load layer error
	                				console.log(e);
	                			}
	                		}else{
	                			console.log("WTF!! " + records[i].data.type + " - " + this.LOADERS_CLASSES[records[i].data.type]);
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
					},
					
					/**
					 * Function: saveFolderByUser
					 * 
					 * Save a folder by user.
					 * 
					 * Parameters: 
					 * 		type - {<Text>} Type of save @see SAVE_FOLDER_TYPES
					 * 		userOrGroup - {<Text>} User name or group id
					 * 		params - {<Map>} with form parameters (needs: enabled, isChannel, isPlain)
					 * 		onsuccess - {<Function>} callback to be send to the success listener to be called 
					 * 		onfailure - {<Function>} callback to be send to the failure listener to be called
					 */
					saveFolder: function (type, userOrGroup, params, onsuccess, onfailure){
						var url;
						if(type == this.SAVE_FOLDER_TYPES.GROUP){
							url = this.SAVE_FOLDER_GROUP_BASE_URL() + userOrGroup;
						}else{
							url = this.SAVE_FOLDER_BASE_URL() + userOrGroup;
						}
						
						this.sendFormPostData(url, params, onsuccess, onfailure);
					},
					
					/**
					 * Function: saveFolderByUser
					 * 
					 * Save a folder by user.
					 * 
					 * Parameters: 
					 * 		username - {<Text>} User name
					 * 		enabled - {<Text>} 
					 * 		isChannel - {<Text>} 
					 * 		isPlain - {<Text>}
					 * 		parentFolder - {<Text>}
					 * 		onsuccess - {<Function>} callback to be send to the success listener to be called 
					 * 		onfailure - {<Function>} callback to be send to the failure listener to be called
					 */
					saveFolderByUser: function (username, foldername, enabled, isChannel, isPlain, parentFolder, onsuccess, onfailure){
						var url = this.SAVE_FOLDER_BASE_URL() + username;
						var params = {
								name: foldername,
								enabled: enabled,
								isChannel: isChannel,
								isPlain: isPlain
						};
						
						if(!!parentFolder){
							params.parentFolder = parentFolder;
						}
						
						this.sendFormPostData(url, params, onsuccess, onfailure);
					},
					
					/**
					 * Function: saveFolderByGroup
					 * 
					 * Save a folder by group
					 * 
					 * Parameters: 
					 * 		groupId - {<Text>} Group id
					 * 		enabled - {<Text>} 
					 * 		isChannel - {<Text>} 
					 * 		isPlain - {<Text>}
					 * 		parentFolder - {<Text>}
					 * 		onsuccess - {<Function>} callback to be send to the success listener to be called 
					 * 		onfailure - {<Function>} callback to be send to the failure listener to be called
					 */
					saveFolderByGroup: function (groupId, foldername, enabled, isChannel, isPlain, parentFolder, onsuccess, onfailure){
						var url = this.SAVE_FOLDER_GROUP_BASE_URL() + groupId;
						var params = {
								name: foldername,
								enabled: enabled,
								isChannel: isChannel,
								isPlain: isPlain
						};
						
						if(!!parentFolder){
							params.parentFolder = parentFolder;
						}
						
						this.sendFormPostData(url, params, onsuccess, onfailure);
					},
					
					/**
					 * Method: saveLayerByUser
					 * 
					 * Save a layer for a user and call to callbacks functions
					 */
					saveLayerByUser: function (userName, properties, onsuccess, onfailure){
						
						var url = this.SAVE_LAYER_BASE_URL() + userName
						
						var params = {};
						
						if(!! properties){
							params = properties;
						}
						
						if(!!properties.properties){ //if properties != null
							var paramsToSend = properties.properties;
							var aux = 0;
							params.properties = "";
							for (param in paramsToSend){aux++;}
							for (param in paramsToSend){
								if(!!param
										&& !!paramsToSend[param]){
									params.properties += param + "===" + paramsToSend[param];
									if(aux > 1){
										params.properties += ",,,"
									}
									params[param] = paramsToSend[param];
								}
								aux--;
							}
						}
						
						this.sendFormPostData(url, params, onsuccess, onfailure);
						
					},
					
					/**
					 * Method: saveLayerByGroup
					 * 
					 * Save a layer for a group and call to callbacks functions
					 */
					saveLayerByGroup: function (groupId, properties, onsuccess, onfailure){
						
						var url = this.SAVE_LAYER_GROUP_BASE_URL() + groupId
						
						var params = {};
						
						if(!! properties){
							params = properties;
						}
						
						if(!!properties.properties){ //if properties != null
							var paramsToSend = properties.properties;
							var aux = 0;
							params.properties = "";
							for (param in paramsToSend){aux++;}
							for (param in paramsToSend){
								if(!!param
										&& !!paramsToSend[param]){
									params.properties += param + "===" + paramsToSend[param];
									if(aux > 1){
										params.properties += ",,,"
									}
									params[param] = paramsToSend[param];
								}
								aux--;
							}
						}
						
						this.sendFormPostData(url, params, onsuccess, onfailure);
						
					},
					
					/**
					 * Private: sendFormPostData
					 * 
					 * Send a form and call to callbacks functions
					 */
					sendFormPostData: function (url, params, onsuccess, onfailure){
						var tempForm = new Ext.FormPanel({
							url: url,
							method: 'POST',
					        title: 'Save layer Form',
					        fileUpload: true,	   
							items: [],
						    buttons: []
						});
						
						tempForm.getForm().load({
							url: url,
							headers: {Accept: 'application/json, text/javascript, */*; q=0.01'},
							waitMsg: 'loading...',
							params : params,
					        fileUpload: true,
							success: onsuccess ? onsuccess : function(){},
							failure: onfailure ? onfailure: function(){}
						});
					}
					
};

/** api: (define)
 *  module = PersistenceGeoParser.AbstractLoader
 */
Ext.namespace("PersistenceGeoParser.AbstractLoader");

/**
 * Class: PersistenceGeoParser.AbstractLoader
 * 
 * Abstract loader for Layers
 * 
 */
PersistenceGeoParser.AbstractLoader = 
	{
		/**
		 * Method to be called for generate OpenLayers layer
		 * 
		 * @return OpenLayers.Layer
		 */
		load: null,

		parseStringToArrayNumbers: function (string){
			var numbers = new Array();
			var stringArray = string.split(",");
			for(var i = 0; i < stringArray.length; i++){
				numbers.push(this.toNumber(stringArray[i]));
			}
			return numbers;
		},
		
		toBoolean: function(string){
			return (new Boolean(string) == new Boolean("true"));
		},
		
		toNumber: function(string){
			return parseFloat(string);
		},
		
		getGroupSubGroupLayer: function (layerData){
			return {
				group: layerData.folderId,
				subGroup: layerData.folderId
			};
		},
		
		postFunctionsWrapper: function (layerData, layer){
			PersistenceGeoParser.AbstractLoader.postFunctionsGroups(layerData, layer);
		},
		
		postFunctionsGroups: function (layerData, layer){
			var groupSub = PersistenceGeoParser.AbstractLoader.getGroupSubGroupLayer(layerData);
			layer.groupLayers = groupSub.group;
			layer.subgroupLayers = groupSub.subGroup;
		}
};
