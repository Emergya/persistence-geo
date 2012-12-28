/*
 * Context.js Copyright (C) 2012 This file is part of PersistenceGeo project
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
Ext.namespace("PersistenceGeo");

/**
 * Class: PersistenceGeo.Context
 * 
 * The PersistenceGeo.Context is designed to store user information
 *
 */
PersistenceGeo.Context = Ext.extend(Ext.util.Observable,{

	userLogin: null,
	authUser: null,
    userInfo: null,
	activeStore: false,
	map: null,
    treeManager: null,

    loadedLayers: {},
    cleanAll: true,
    loadedChannel: null,

    /** i18n **/
    defaultAuthGroup: "'{0}' authority layers",
    defaultUsersGroup: "'{0}' users layers",
    channelGroupText: "Channel '{0}' layers",

    SAVE_MODES:{
        GROUP: 1,
        USER: 2,
        ANONYMOUS: 3
    },

    /*
     * private: [parser]
     * 
     * Parser to load OpenLayers layers
     */
    parser: null,

    defaultRestUrl: "rest",

    /*
     * Default save as user
     */
    saveModeActive: 2,

   /**
    * @event onLayerSave
    * Fires after layer saved succesfully
    * @Param {OpenLayers.Layer} layer saved
    */
    onLayerSave: function (layer){
       if(!!this.map
            && !!this.map.addLayer){
            this.map.addLayer(layer);
        }
    },

   /**
    * @event onSaveLayerException
     * Fires after layer saved failure
    * @Param {Exception} saving layer
    */
    onSaveLayerException: function (e){
        // TOOD: handle exception
    },
    
    constructor: function(config) {

        Ext.apply(this,config); 

        PersistenceGeo.Context.superclass.constructor.call(this, config);

        // get baseurl
        baseUrl = this.defaultRestUrl;
        this.parser = new PersistenceGeo.Parser({
            map: this.map,
            getRestBaseUrl: function(){
                return baseUrl;
            }
        });
    },
    
    /** private: method[initComponent]
     */
    initComponent: function() {
        PersistenceGeo.Context.superclass.initComponent.apply(this, arguments);
    },

    canBeBaseLayer: function(){
        //TODO integrate
        return false;
    },

    /**
     * api: method[load]
     * Load user or group layers in the context.
     */ 
    load: function(){
        var this_ = this;
        this.clearLayers();
        if(!!this.SAVE_MODES.GROUP == this.saveModeActive){
            this.parser.loadLayersByGroup(this.authUser, function(layers, layerTree){
                this_.onLoadLayers(layers, layerTree);
            });
        }else if(!!this.SAVE_MODES.USER == this.saveModeActive){
            this.parser.loadLayersByUser(this.userLogin, function(layers, layerTree){
                this_.onLoadLayers(layers, layerTree);
            });
        }
    },

    /**
     * api: method[loadChannel]
     * Load all channel layers marked as channel.
     */ 
    loadChannel: function(idChannel, nameChannel, onLoadLayers, onFailure){
        var this_ = this;
        // clear group
        if(!!this.loadedChannel){
            this.clearGroup(this.loadedChannel);
        }
        // add group
        var channelGroup = "channel_group";
        var channelText = String.format(this.channelGroupText, nameChannel ? nameChannel: '');
        this.loadedLayers[channelGroup] = new Array();
        this.treeManager.addGroup({
            group:channelText,
            groupIndex: channelGroup
        });
        this.loadedChannel = channelGroup;
        this.parser.loadFolderById(idChannel, "ONLY_CHANNEL_MARK", 
            function(form, action){
                /*
                 * ON SUCCESS
                 */
                if(!!onLoadLayers){
                    onLoadLayers(form, action);
                }else{
                    this_.parseLayers(form, action, {groupLayers: channelGroup}, channelGroup);
                }
            },
            function(form, action){
                /*
                 * ON FAILURE 
                 */
                if(!!onFailure){
                    onFailure(form, action);
                }else{
                    this_.onSaveLayerException(e);
                }
        });
    },

    /**
     * api: method[getChannelData]
     * Load all folders of a channel or channel layers not marked as channel.
     */ 
    getChannelData: function(idChannel, onLoadLayers, onFailure){
        this.parser.loadFolderById(idChannel, false,
            function(form, action){
                /*
                 * ON SUCCESS
                 */
                if(!!onLoadLayers){
                    onLoadLayers(form, action);
                }else{
                    this_.parseLayers(form, action);
                }
            },
            function(form, action){
                /*
                 * ON FAILURE 
                 */
                if(!!onFailure){
                    onFailure(form, action);
                }else{
                    this_.onSaveLayerException(e);
                }
        });
    },

    removeLayer: function(layer){
        this.getParser().deleteLayerByLayerId(layer.layerID);
        if(!!layer.groupID 
            && !!this.loadedLayers[layer.groupID]){
            this.removeLayerFromGroup(layer, this.loadedLayers[layer.groupID]);
        }else if(!!layer.userID 
            && !!this.loadedLayers[layer.userID]){
            this.removeLayerFromGroup(layer, this.loadedLayers[layer.userID]);
        }
    },

    removeLayerFromGroup: function(layer, group){
        for (var i=0; i<group.length; i++){
            if(!!group[i]
                && (group[i].layerID == layer.layerID)){
                // clear layer to be remobed
                group[i] = null;
            }
        }
    },

    /**
     * api: method[clearLayers]
     * Clear all layers defined in map by user context.
     */ 
    clearLayers: function(){
        if(this.cleanAll){
            for(var group in this.loadedLayers){
                this.clearGroup(group);
            }
            this.loadedLayers = {};
        }
    },

    /**
     * api: method[clearGroup]
     * Clear all layers defined in a group.
     */ 
    clearGroup: function (group){
        for (var i=0; i<this.loadedLayers[group].length; i++){
            if(!!this.loadedLayers[group][i]){
                this.map.removeLayer(this.loadedLayers[group][i]);
            }// else already removed on this.removeLayer
        }
        this.treeManager.removeGroup(group);
    },

    onLoadLayers: function(layers, layerTree){
        var groupLayers = null;
        if(!!this.SAVE_MODES.GROUP == this.saveModeActive){
            groupLayers = String.format(this.defaultAuthGroup, this.userInfo.authority);
        }else if(!!this.SAVE_MODES.USER == this.saveModeActive){
            groupLayers = String.format(this.defaultUsersGroup, this.userLogin);
        }
        this.loadedLayers[this.authUser] = [];
        this.treeManager.addGroup({
            group:groupLayers,
            groupIndex: this.authUser
        });
        if(!!layers){
            for(var i = 0; i<layers.length; i++){
                try{
                    var layer = layers[i];
                    layer.groupLayers = this.authUser;
                    this.map.addLayer(layer);
                    this.loadedLayers[this.authUser].push(layer);
                }catch (e){
                    // TODO: handle
                }
            }
        }
    },

    addLayer: function(layer, nameLayer, folderID, params){
        var groupLayers = null;
        if(!!this.SAVE_MODES.GROUP == this.saveModeActive){
            groupLayers = String.format(this.defaultAuthGroup, this.userInfo.authority);
        }else if(!!this.SAVE_MODES.USER == this.saveModeActive){
            groupLayers = String.format(this.defaultUsersGroup, this.userLogin);
        }
        layer.groupLayers = groupLayers;

    	if(this.activeStore){
    		this.saveLayer(layer, nameLayer, folderID, params);
    	}else{
    		if(!!this.map
                && !!this.map.addLayer){
                this.map.addLayer(layer);
            }
    	}
    },

    saveLayer: function(layer, nameLayer, folderID, params){

        //Init default params
        if(!params){
            // Add the folder id to the layer
            layer.groupLayersIndex = folderID;
            // Get the layer params to save them
            var properties = {
                    transparent: true,
                    buffer: 0,
                    visibility: true,
                    opacity: 0.5,
                    format: layer.params.FORMAT,
                    maxExtent: layer.maxExtent.toString(),
                    layers: layer.params.LAYERS,
                    order: this.map.layers.length
            };
            
            var url = layer.url;
            
            if(url.indexOf(OpenLayers.ProxyHost) > -1){
                url = url.substring(url.indexOf(OpenLayers.ProxyHost) + OpenLayers.ProxyHost.length);
            }
            
            params = {
                    name: nameLayer,
                    server_resource: url,
                    type: layer.params.SERVICE,
                    folderId: folderID,
                    properties: properties
            };
        }

        this.saveLayerFromParams(params);
    },

    getParser: function(){
        return this.parser;
    },

    saveLayerFromParams: function(params){
        var this_ = this;

        //Layer save
        if(!!this.SAVE_MODES.GROUP == this.saveModeActive){
            this.parser.saveLayerByGroup(this.authUser, params,
                    function(form, action){
                        /*
                         * ON SUCCESS
                         */
                        this_.parseLayer(form, action);
                    },
                    function(form, action){
                        /*
                         * ON FAILURE 
                         */
                        this_.parseLayer(form, action);
            });
        }else if(!!this.SAVE_MODES.USER == this.saveModeActive){
            this.parser.saveLayerByUser(this.userLogin, params,
                    function(form, action){
                        /*
                         * ON SUCCESS
                         */
                        this_.parseLayer(form, action);
                    },
                    function(form, action){
                        /*
                         * ON FAILURE 
                         */
                        this_.parseLayer(form, action);
            });
        }else{
            this.parser.saveLayerAnonymous(params,
                    function(form, action){
                        /*
                         * ON SUCCESS
                         */
                        this_.parseLayer(form, action);
                    },
                    function(form, action){
                        /*
                         * ON FAILURE 
                         */
                        this_.parseLayer(form, action);
            });
        }
    },

    parseLayers: function (form, action, overrideProperties, storeOn){
        try{
            var json = Ext.util.JSON.decode(action.response.responseText);
            if(!!json 
                && !!json.results
                && !!json.data
                && json.results > 0){
                for(var i = 0; i < json.results; i++){
                    var layerData = json.data[i];
                    if(!layerData.server_resource
                        && !!layerData.data
                        && !!layerData.data.server_resource){
                        layerData = layerData.data;
                    }
                    var layer = this.getLayerFromData(layerData);
                    if(!!overrideProperties){
                        for(var key in overrideProperties){
                            layer[key] = overrideProperties[key];
                        }
                    }
                    if(!!storeOn){
                        this.loadedLayers[storeOn].push(layer);
                    }
                    this.onLayerSave(layer);       
                }
            }
        }catch(e){
            this.onSaveLayerException(e);
        }
    }, 

    parseLayer: function (form, action){
        try{
            var json = Ext.util.JSON.decode(action.response.responseText);
            var layer = this.getLayerFromData(json);
            this.onLayerSave(layer);
        }catch(e){
            this.onSaveLayerException(e);
        }
    }, 

    getLayerFromData: function(json){
        var type = "WMS";
        if(!!json.data && !!json.data.type){
            // normal (layer on 'data' of json)
            type = json.data.type;
            layer = this.parser.LOADERS_CLASSES[type].load(json.data);
        }else if(!!json.type){
            // layer on json root
            type = json.type;
            layer = this.parser.LOADERS_CLASSES[type].load(json);
        }else{
            // unknown json. Try WMS loader
            layer = this.parser.LOADERS_CLASSES[type].load(json);
        }
        var groupLayers;
        if(!!this.SAVE_MODES.GROUP == this.saveModeActive){
            groupLayers = String.format(this.defaultAuthGroup, this.userInfo.authority);
        }else if(!!this.SAVE_MODES.USER == this.saveModeActive){
            groupLayers = String.format(this.defaultUsersGroup, this.userLogin);
        }
        layer.groupLayers = groupLayers;
        return layer;
    }

});

