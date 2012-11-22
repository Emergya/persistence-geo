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
PersistenceGeo = Ext.extend(Ext.Component,
				{
						
					FOLDERS_ADDED:{},
					
					logEnabled: true,
					
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
						"WFS":PersistenceGeoParser.loaders.WFSLoader,
						"KML":PersistenceGeoParser.loaders.KMLLoader,
						"GML":PersistenceGeoParser.loaders.GMLLoader
					},
					
					REST_COMPONENT_URL: "rest",
					REQUEST_METHOD_POST: "POST",
					REQUEST_METHOD_GET: "GET",
					
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

					DELETE_LAYER_BASE_URL: function(){
						return this.getRestBaseUrl()+ "/persistenceGeo/deleteLayerByLayerId/";
					},
					LOAD_USERS_BY_GROUP_BAE_URL: function(){
						return this.getRestBaseUrl()+ "/persistenceGeo/getUsersByGroup/";
					},
					LOGIN_URL: function(){
						return this.getRestBaseUrl()+ "/persistenceGeo/admin/createUser/";
					},
					
					LOAD_MAP_CONFIGURATION_BASE_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/loadMapConfiguration";
					},
					
					UPDATE_MAP_CONFIGURATION_BASE_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/updateMapConfiguration";
					},
					
					UPDATE_LAYER_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/updateLayer";
					},
					
					UPLOAD_STYLE_LAYER_BASE_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/uploadStyle/";
					},
					
					MOVE_LAYER_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/moveLayerTo";
					},
					
					MOVE_FOLDER_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/moveFolderTo";
					},
					
					SAVE_LAYER_PROPERTIES_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/saveLayerSimpleProperties";
					},
					
					DELETE_FOLDER_PROPERTIES_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/deleteFolder";
					},
					
					RENAME_FOLDER_PROPERTIES_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/renameFolder";
					},
					
					CLONE_USER_CONTEXT_URL: function(){
						return this.getRestBaseUrl() + "/persistenceGeo/cloneUserContext";
					},
					
					LOADED_FOLDERS:{},
					
					LOADED_FOLDERS_OBJECTS:{},
					
					LOADED_FOLDERS_NAMES:{},
					
					ROOT_FOLDER:null,
					
					getFolder: function (nameFolder){
						return this.LOADED_FOLDERS[nameFolder];
					},
					
					getFolderName: function (idFolder){
						return this.LOADED_FOLDERS_NAMES[idFolder];
					},
					
					initFoldersByUser: function(user){
						this.initFolders(user, this.LOAD_FOLDERS_BASE_URL() + user);
					},
					
					initFoldersByGroup: function(idGroup){
						this.initFolders(idGroup, this.LOAD_FOLDERS_GROUP_BASE_URL() + idGroup);
					},
					
					saveLayerName: function (layerId, name, onsuccess, onfailure){

						var url = this.SAVE_LAYER_PROPERTIES_URL();
						var params = {
								layerId: layerId,
								name: name
						};
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					saveLayerProperties: function (layerId, properties, onsuccess, onfailure){

						var url = this.SAVE_LAYER_PROPERTIES_URL();
						
						var params = {
								layerId: layerId,
								properties: this.getMapParse(properties)
						};
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					/**
					 * Function: login
					 * 
					 * Make a login with PersistenceGeo creating user and group if not exists 
					 */
					login: function(userName, userGroup, userZone, onsuccess, onfailure){
						
						var url = this.LOGIN_URL();
						var params = {
								username: userName,
								userGroup: userGroup
						};
						if(!!userZone){
							params.userZone = userZone;
						}
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					/**
					 * Function: cloneUserContext
					 * 
					 * Clone user context 
					 */
					cloneUserContext: function(originUser, targetUser, onsuccess, onfailure){
						
						var url = this.CLONE_USER_CONTEXT_URL();
						
						var params = {
								originUser: originUser,
								targetUser: targetUser
						};
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					/**
					 * Function: cloneUserContextMerged
					 * 
					 * Clone user context maintaining actual folders
					 */
					cloneUserContextMerged: function(originUser, targetUser, onsuccess, onfailure){
						
						var url = this.CLONE_USER_CONTEXT_URL();
						
						var params = {
								originUser: originUser,
								targetUser: targetUser,
								merged: true
						};
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					/**
					 * Function: moveLayerTo
					 * 
					 * Move a layer to a folder using PersistenceGeo 
					 */
					moveLayerTo: function(layerId, folderId, toOrder, onsuccess, onfailure){
						
						var url = this.MOVE_LAYER_URL();
						
						var params = {
								layerId: layerId,
								toFolder: folderId,
								toOrder: toOrder
						};
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					/**
					 * Function: moveFolderTo
					 * 
					 * Move a folder to a folder using PersistenceGeo 
					 */
					moveFolderTo: function(folderId, toFolderId, toOrder, onsuccess, onfailure){
						
						var url = this.MOVE_FOLDER_URL();
						
						var params = {
								folderId: folderId,
								toFolder: toFolderId,
								toOrder: toOrder
						};
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					/**
					 * Function: moveFolderTo
					 * 
					 * Delete a folder to a folder using PersistenceGeo 
					 */
					deleteFolder: function(folderId, onsuccess, onfailure){
						
						var url = this.DELETE_FOLDER_PROPERTIES_URL();
						
						var params = {
								folderId: folderId
						};
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					/**
					 * Function: renameFolder
					 * 
					 * Rename a folder using PersistenceGeo 
					 */
					renameFolder: function(folderId, newName, onsuccess, onfailure){
						
						var url = this.RENAME_FOLDER_PROPERTIES_URL();
						
						var params = {
								folderId: folderId,
								name: newName
						};
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					/**
					 * Function: uploadStyle
					 * 
					 * Upload styleMap of a layer using PersistenceGeo 
					 */
					uploadStyle: function(layerId, styleMap, onsuccess, onfailure){
						
						var url = this.UPLOAD_STYLE_LAYER_BASE_URL() + layerId;
						
						var styles = new Array();
						
						if(!!styleMap.styles){
							for (var style in styleMap.styles){
								var styleDto = {
										name: style
								};
							    styleDto.rules = this.getRulesFromStyle(styleMap.styles[style]);
							    styles.push(styleDto);
							}
						}
						
						var params = {
								data: JSON.stringify({styles:styles})
						};
						
						this.sendFormPostData(url, params, "POST", onsuccess, onfailure);
					},
					
					getRulesFromStyle: function(style){
						var rules = new Array();
					    if(!style.rules 
					        || style.rules.length > 0){
					    	var rulesOL = style.rules;
					    	for (var i = 0; i < rulesOL.length; i++){
					    		var filter = rulesOL[i].filter ? rulesOL[i].filter : "true";
						    	rules.push({
						    		name: filter,
						    		properties: this.getPropertiesFromMap(rulesOL[i].symbolizer)
						    	});
					    	}
					    }else{
					    	rules.push({
					    		name: 'true',
					    		properties: [{name: 'display', value: 'true'}]
					    	});
					    }
				    	return rules;
					},
					
					getPropertiesFromMap: function(styleProperties){
						var properties = new Array();
				        //styles[style]["true"] = this.parseMapToStringMap(styleMap.styles[style].defaultStyle);
				    	for(var property in styleProperties){
				    		properties.push({
				    			name: property,
				    			value: PersistenceGeoParser.AbstractLoader.parseValueStyle(property, styleProperties[property])
				    		});
				    	}
				    	return properties;
					},
					
					treeFolder: new Ext.util.MixedCollection(),
					
					/**
					 * Function: loadLayers
					 * 
					 * Loads OpenLayers layers and call to onload callback function (layers). 
					 * Used to load all user layers. 
					 */
					initFolders: function(userOrGroup, url){
						this.LOADED_FOLDERS = {};
						this.LOADED_FOLDERS_NAMES = {};
						this.ROOT_FOLDER = null;
						this.treeFolder = new Ext.util.MixedCollection();
						var this_ = this;
						store = new Ext.data.JsonStore({
				             url: url,
				             remoteSort: false,
				             autoLoad:true,
				             idProperty: 'id',
				             root: 'data',
				             totalProperty: 'results',
				             fields: ['id','name','idParent','order'],
				             listeners: {
				                 load: function(store, records, options) {
										var i = 0; 
										var lastParent = null;
										var parents = {};
					                	while (i<records.length){
					                		if(!!records[i].data.id 
					                				&& !!records[i].data.name){
					                			var folderName = records[i].data.name;
					                			this_.LOADED_FOLDERS_OBJECTS[records[i].data.id] = records[i].data;
					                			this_.LOADED_FOLDERS[folderName] = records[i].data.id;
					                			this_.LOADED_FOLDERS_NAMES[records[i].data.id] = folderName;
					                			// Root folder haven't '-'
					                			if(!this_.ROOT_FOLDER 
					                					&& folderName.indexOf("-") < 0){
					                				this_.ROOT_FOLDER = folderName;
					                			}
					                			if(!!records[i].data.idParent
					                					&& !!parents[records[i].data.idParent]){
					                				//Add child
					                				lastParent = parents[records[i].data.idParent];
				                					lastParent.element.add(records[i].data.id, new Ext.util.MixedCollection());
				                					var newParent = {};
				                					newParent.id = records[i].data.id;
				                					newParent.element = lastParent.element.item(records[i].data.id);
					                				parents[records[i].data.id] = newParent;
					                			}else{
					                				//Leaf
					                				this_.treeFolder.add(records[i].data.id, new Ext.util.MixedCollection());
				                					var newParent = {};
				                					newParent.id = records[i].data.id;
				                					newParent.element = this_.treeFolder.item(records[i].data.id);
					                				parents[records[i].data.id] = newParent;
					                			}
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
						this.initFoldersByUser(user); //Caution!! you haven't getFolderName function available before storeload
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
				                      'user','folderList','styles',
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
						var layerTree = new Ext.util.MixedCollection();
	                	while (i<records.length){
	                		if(!!records[i].data.type
	                				&& !! this.LOADERS_CLASSES[records[i].data.type]){
	                			try{
	                				var layer = this.LOADERS_CLASSES[records[i].data.type].load(records[i].data, layerTree);
	                				layers.push(layer);
	                			}catch (e){
	                				if(this.logEnabled)
	                					console.log(e.stack);
	                			}
	                		}else{
	                			console.log("ERROR loading " + records[i].data.type + " - " + this.LOADERS_CLASSES[records[i].data.type]);
	                		}
	                		i++;
	                	}
	                	if(!!onload){
	                		 onload(layers, layerTree, this.ROOT_FOLDER);
	                	 }else{
	                		 PersistenceGeoParser.defaultOnLoad(layers, layerTree); 
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
						
						this.sendFormPostData(url, params, this.REQUEST_METHOD_POST, onsuccess, onfailure);
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
						
						this.sendFormPostData(url, params, this.REQUEST_METHOD_POST, onsuccess, onfailure);
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
						
						this.sendFormPostData(url, params, this.REQUEST_METHOD_POST, onsuccess, onfailure);
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
						
						this.sendFormPostData(url, params, this.REQUEST_METHOD_POST, onsuccess, onfailure);
						
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
						
						this.sendFormPostData(url, params, this.REQUEST_METHOD_POST, onsuccess, onfailure);
						
					},
					
					/**
					 * Method: deleteLayerByLayerId
					 * 
					 * Delete a layer for a layer identifier and call to callbacks functions
					 */
					deleteLayerByLayerId: function(layerId, onsuccess, onfailure){
						
						var url = this.DELETE_LAYER_BASE_URL() + layerId;
						var params = {};
						
						this.sendFormPostData(url, params, this.REQUEST_METHOD_POST, onsuccess, onfailure);
					},
					
					/**
					 * Method: loadMapConfiguration
					 * 
					 * Load initial configuration from data base.
					 */
					loadMapConfiguration: function(onsuccess, onfailure){
						
						var url = this.LOAD_MAP_CONFIGURATION_BASE_URL();
						var params = {};
						
						this.sendFormPostData(url, params, this.REQUEST_METHOD_GET, onsuccess, onfailure);
					},
					
					/**
					 * Method: loadMapConfiguration
					 * 
					 * Load initial configuration from data base.
					 */
					updateMapConfiguration: function(properties, onsuccess, onfailure){
						
						var url = this.UPDATE_MAP_CONFIGURATION_BASE_URL();
						
						if(!! properties){
							params = properties;
						}
						
						this.sendFormPostData(url, params, this.REQUEST_METHOD_GET, onsuccess, onfailure);
					},
					
					/**
					 * Private: sendFormPostData
					 * 
					 * Send a form and call to callbacks functions
					 */
					sendFormPostData: function (url, params, method, onsuccess, onfailure){
						var tempForm = new Ext.FormPanel({
							url: url,
							method: method,
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
					},
					
					/**
					 * Private: getMapParse
					 * 
					 * Obtain map parse
					 * 
					 * @param properties
					 * @returns
					 */
					getMapParse: function(properties){
						var result = null;
						if(!!properties){ //if properties != null
							var paramsToSend = properties;
							var aux = 0;
							result = "";
							for (param in paramsToSend){aux++;}
							for (param in paramsToSend){
								if(!!param){
									result += param + "===" + paramsToSend[param];
									if(aux > 1){
										result += ",,,";
									}
								}
								aux--;
							}
						}
						return result;
					}	
});

var PersistenceGeoParser = new PersistenceGeo();

/** api: (define)
 *  module = PersistenceGeoParser.AbstractLoader
 */
Ext.namespace("PersistenceGeo.AbstractLoader");

/**
 * Class: PersistenceGeoParser.AbstractLoader
 * 
 * Abstract loader for Layers
 * 
 */
PersistenceGeo.AbstractLoader =  Ext.extend(Ext.Component,
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
			return (string === "true");
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
		
		postFunctionsWrapper: function (layerData, layer, layerTree){
			PersistenceGeoParser.AbstractLoader.postFunctionsGroups(layerData, layer, layerTree);
			PersistenceGeoParser.AbstractLoader.postFunctionsPermission(layerData, layer);
			PersistenceGeoParser.AbstractLoader.postFunctionsStyle(layerData, layer);
			PersistenceGeoParser.AbstractLoader.postFunctionsOrder(layerData, layer);
			PersistenceGeoParser.AbstractLoader.postFunctionsVisibility(layerData, layer);
		},
		
		postFunctionsVisibility: function (layerData, layer){
			if(!!layerData.properties){
				var visibility = layerData.properties.visibility ? this.toBoolean(layerData.properties.visibility) : false;
				if(layer.visibility != visibility){
					layer.setVisibility(visibility);
				}
			}
		},
		
		postFunctionsOrder: function (layerData, layer){
			if(!!layerData.properties
					&& !!layerData.properties.order){
				layer.order = layerData.properties.order;
			}
		},
		
		postFunctionsGroups: function (layerData, layer, layerTree){
			var groupSub = PersistenceGeoParser.AbstractLoader.getGroupSubGroupLayer(layerData);
			layer.groupLayers = groupSub.group;
			layer.subgroupLayers = groupSub.subGroup;

			//Get folder names
			var group = PersistenceGeoParser.getFolderName(layer.groupLayers);
			var subgroup = PersistenceGeoParser.getFolderName(layer.subgroupLayers);
			
			//Save folder ids
			layer.groupLayersIndex = layer.groupLayers;
			layer.subGroupLayersIndex = layer.subgroupLayers;
			
			//Group
			if(! group){
				group = subgroup;
			}
			//Hide root folder
			var group_label = subgroup;
			if(!!group_label && group_label.indexOf("-") > 0){
				group_label = group_label.split("-")[1];
			}
			// Adds to layerTree
			if (!!group_label
					&& !!layerTree
					&& !layerTree.containsKey(group_label)) {
				//console.log("Creating '"+group_label+"'");
				layerTree.add(group_label,
						new Ext.util.MixedCollection());
			}
			
			//Subgroup
			var subgroup_label = subgroup;
			//Hide root folder
			if(!!subgroup_label && subgroup_label.indexOf("-") > 0){
				subgroup_label = subgroup_label.split("-")[subgroup_label.split("-").length-1];
			}
			// Adds to layerTree
			if(!!group_label
					&& group_label != subgroup_label 
					&& !layerTree.item(group_label).containsKey(subgroup_label)){
				//console.log("Creating '"+group_label + "-"+subgroup_label+"'");
				layerTree.item(group_label).add(subgroup_label,
					subgroup_label);
			}
			
			//To save at layer
			layer.groupLayers = subgroup;
			if(!!subgroup && subgroup.indexOf("-") > 0){
				layer.groupLayers = subgroup.substring(subgroup.indexOf("-")+1);
			}
			layer.subgroupLayers = group;
			if(!!group && group.indexOf("-") > 0){
				layer.subgroupLayers = subgroup_label;
			}
		},
		
		postFunctionsPermission: function(layerData, layer){
			// Save layer ids
			layer.layerID = layerData.id;
			layer.userID = layerData.userId;
			layer.groupID = layerData.authId;
			layer.folderID = layerData.folderId;
		},
		
		parseValueStyle: function (name, original){
			if(name.indexOf('Opacity') >  -1){
				return PersistenceGeoParser.AbstractLoader.toNumber(original);
			}else{
				return original;
			}
		},
		
		postFunctionsStyle: function(layerData, layer){
			if(!!layerData.styles
					&& !!layerData.styles
					&& !!layerData.styles['default']){
				var styleMap = {};
				for(var styleName in layerData.styles){
					if(styleName == 'default'){
						var rules = new Array();
						for(var ruleFilter in layerData.styles[styleName]){
							var symbolizer = {};
							if(ruleFilter == 'true'){
								for(var property in layerData.styles[styleName][ruleFilter]){
									symbolizer[property] = this.parseValueStyle(property, layerData.styles[styleName][ruleFilter][property]);
								}
							}else{
								//TODO: Use OGC filter
							}
							rules.push(new OpenLayers.Rule({symbolizer: symbolizer}));
				        }
						styleMap[styleName] = new OpenLayers.Style(null, {
			                rules: rules
			            });
					}
				}
				styleMap = new OpenLayers.StyleMap(styleMap);
				layer.events.register("loadend", 
					{
						layer:layer, 
						symbolizer:symbolizer
					}, 
					function() {
						//Forze style
						for(var i = 0; i < this.layer.features.length; i++){
							var styleDefined = OpenLayers.Util
								.applyDefaults(
										this.symbolizer,
									OpenLayers.Feature.Vector.style["default"]);
							this.layer.features[i].style = styleDefined;
						}
						this.layer.redraw();
					}
	            );
				//window.setInterval(this.UpdateKmlLayer, 5000, layer, styleMap);
				layer.styleMap = styleMap;
			}
		}
		
});

PersistenceGeoParser.AbstractLoader = new PersistenceGeo.AbstractLoader();