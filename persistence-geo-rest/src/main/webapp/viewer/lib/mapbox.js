var saveLayerBaseUrl = "rest/persistenceGeo/saveLayerByUser/";
var saveLayerGroupBaseUrl = "rest/persistenceGeo/saveLayerByGroup/";
var saveLayerUrl;

var createUserUrl = "rest/persistenceGeo/admin/createUser";

var loadLayersUrl = "rest/persistenceGeo/loadLayers/";

var allGroupsUrl = "rest/persistenceGeo/getAllGroups";

var allUsersUrl = "rest/persistenceGeo/getAllUsers";

var allLayerTypesUrl = "rest/persistenceGeo/getLayerTypes";

var loadLayerTypeUrl = "rest/persistenceGeo/getLayerTypeProperties/";

var loadFoldersBaseUrl = "rest/persistenceGeo/loadFolders/";

var loadFoldersGroupBaseUrl = "rest/persistenceGeo/loadFoldersByGroup/";

var user;
var store;
var toRemove = new Array();
var paramsToSend = {};
var app;
var userOrGroupToSaveFolder;
var typeToSaveFolder;
var saveFolderForm;

function getFoldersUrl(){
	if(typeToSaveFolder == PersistenceGeoParser.SAVE_FOLDER_TYPES.GROUP){
		return loadFoldersGroupBaseUrl + userOrGroupToSaveFolder;
	}else{
		return loadFoldersBaseUrl + userOrGroupToSaveFolder;
	}
}

Ext.onReady(function() {

	var loadLayersUrl = "rest/persistenceGeo/loadLayers/";
	var allUsersUrl = "rest/persistenceGeo/getAllUsers";
	
	var fileCombo = {
			xtype : 'fileuploadfield',
			emptyText : "Select layer file...",
			fieldLabel : "File",
			name : 'uploadfile',
			cls : 'upload-button',
			buttonText : '',
			buttonCfg : {
				iconCls : 'upload-icon'
			}
		};

	saveFolderForm = new Ext.FormPanel({
			url: createUserUrl,
	        title: 'Save Folder Form',
			cls: 'my-form-class',
			width: 350,
			height: 200,
			items: [{
		         fieldLabel:'Select group'
				,xtype:'combo'
				,name: 'userGroup'
		        ,displayField:'nombre'
		        ,valueField:'id'
		        ,store: new Ext.data.JsonStore({
		             url: allGroupsUrl,
		             remoteSort: false,
		             autoLoad:true,
		             idProperty: 'id',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['id','nombre']
		         })
		        ,triggerAction:'all'
		        ,mode:'local'
		        ,listeners:{select:{fn:function(combo, value) {
		        	typeToSaveFolder = PersistenceGeoParser.SAVE_FOLDER_TYPES.GROUP;
		        	userOrGroupToSaveFolder = value.id;
		        	saveFolderForm.remove(saveFolderForm.items.items[6]);
		        	saveFolderForm.add({
				         fieldLabel:'Select parent'
								,xtype:'combo'
								,name: 'parentFolder'
						        ,displayField:'name'
						        ,valueField:'id'
						        ,store: new Ext.data.JsonStore({
						             url: getFoldersUrl(),
						             remoteSort: false,
						             autoLoad:true,
						             idProperty: 'id',
						             root: 'data',
						             totalProperty: 'results',
						             fields: ['id','name']
						         })
						        ,triggerAction:'all'
						        ,mode:'local'
							});
		        	saveFolderForm.doLayout();
		            }}
		        }
			},
			{
		         fieldLabel:'Select user'
				,xtype:'combo'
		        ,displayField:'username'
		        ,valueField:'id'
		        ,store: new Ext.data.JsonStore({
		             url: allUsersUrl,
		             remoteSort: false,
		             autoLoad:true,
		             idProperty: 'username',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['id','username']
		         })
		        ,triggerAction:'all'
		        ,mode:'local'
		        ,listeners:{select:{fn:function(combo, value) {
		        	typeToSaveFolder = PersistenceGeoParser.SAVE_FOLDER_TYPES.USER;
		        	userOrGroupToSaveFolder = value.id;
		        	saveFolderForm.remove(saveFolderForm.items.items[6]);
		        	saveFolderForm.add({
				         fieldLabel:'Select parent'
								,xtype:'combo'
								,name: 'parentFolder'
						        ,displayField:'name'
						        ,valueField:'id'
						        ,store: new Ext.data.JsonStore({
						             url: getFoldersUrl(),
						             remoteSort: false,
						             autoLoad:true,
						             idProperty: 'id',
						             root: 'data',
						             totalProperty: 'results',
						             fields: ['id','name']
						         })
						        ,triggerAction:'all'
						        ,mode:'local'
							});
		        	saveFolderForm.doLayout();
		            }}
		        }
			},
			{
				xtype: 'textfield',
				fieldLabel: 'Name',
				name: 'name'
			},
			{
				xtype: 'textfield',
				fieldLabel: 'Enabled',
				name: 'enabled'
			},
			{
				xtype: 'textfield',
				fieldLabel: 'Is Channel',
				name: 'isChannel'
			},
			{
				xtype: 'textfield',
				fieldLabel: 'Is Plain',
				name: 'isPlain'
			},
			{
		         fieldLabel:'Select parent'
				,xtype:'combo'
				,name: 'parentFolder'
		        ,displayField:'name'
		        ,valueField:'id'
		        ,store: new Ext.data.JsonStore({
		             url: getFoldersUrl(),
		             remoteSort: false,
		             autoLoad:false,
		             idProperty: 'id',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['id','name']
		         })
		        ,triggerAction:'all'
		        ,mode:'local'
			}],
		    buttons: [{
				text: 'Save',
				handler: function() {
					var params = {
							name: saveFolderForm.items.items[2].getValue(),
							enabled: saveFolderForm.items.items[3].getValue(),
							isChannel: saveFolderForm.items.items[4].getValue(),
							isPlain: saveFolderForm.items.items[5].getValue(),
							parentFolder: saveFolderForm.items.items[6].getValue()
					};
					
					PersistenceGeoParser.saveFolder(typeToSaveFolder,userOrGroupToSaveFolder, params, 
							function(form, action) {
								Ext.Msg.alert('Folder saved', action.response.responseText);
							},
							function(form, action) {
								if(!!action
										&& !!action.response
										&& !!action.response.status
										&& action.response.status == "200"
										&& !!action.response.responseText){
									Ext.Msg.alert('Folder saved', action.response.responseText);
								}else{
									Ext.Msg.alert('Warning', 'Error Unable to Load Form Data.');
								}
							});
				}
			}]
	});

	var saveUserForm = new Ext.FormPanel({
			url: createUserUrl,
	        title: 'Save User Form',
			cls: 'my-form-class',
			width: 350,
			height: 200,
			items: [{
					xtype: 'textfield',
					fieldLabel: 'Name',
					name: 'username'
			},
			{
		         fieldLabel:'Select group'
				,xtype:'combo'
				,name: 'userGroup'
		        ,displayField:'nombre'
		        ,valueField:'id'
		        ,store: new Ext.data.JsonStore({
		             url: allGroupsUrl,
		             remoteSort: false,
		             autoLoad:true,
		             idProperty: 'id',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['id','nombre']
		         })
		        ,triggerAction:'all'
		        ,mode:'local'
		        ,listeners:{select:{fn:function(combo, value) {
		        	console.log(value);
		            }}
		        }
			}],
		    buttons: [{
				text: 'Save',
				handler: function() {
					fnLoadForm(saveUserForm, createUserUrl);
					saveLayerForm.refresh();
				}
			}]
	});
//
	var saveLayerForm = new Ext.FormPanel({
			url: saveLayerUrl,
	        title: 'Save layer Form',
	        fileUpload: true,	   
			width: 350,
			height: 200,
			items: [{
					xtype: 'textfield',
					fieldLabel: 'Name',
					name: 'name'
			},
			{
					xtype: 'textfield',
					fieldLabel: 'Url',
					name: 'server_resource'
			},
			{
		         fieldLabel:'Select type'
				,xtype:'combo'
				,name: 'type'
		        ,displayField:'name'
		        ,valueField:'name'
		        ,store: new Ext.data.JsonStore({
		             url: allLayerTypesUrl,
		             remoteSort: false,
		             autoLoad:true,
		             idProperty: 'name',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['name']
		         })
		        ,triggerAction:'all'
		        ,mode:'local'
		        ,listeners:{select:{fn:function(combo, value) {
		        	loadLayerType(saveLayerForm, value);
		            }}
		        }
			},
			{
		         fieldLabel:'Select group'
				,xtype:'combo'
				,name: 'userGroup'
		        ,displayField:'nombre'
		        ,valueField:'id'
		        ,store: new Ext.data.JsonStore({
		             url: allGroupsUrl,
		             remoteSort: false,
		             autoLoad:true,
		             idProperty: 'id',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['id','nombre']
		         })
		        ,triggerAction:'all'
		        ,mode:'local'
		        ,listeners:{select:{fn:function(combo, value) {
		        	saveLayerUrl = saveLayerGroupBaseUrl + value.id;
		        	user = value.id;
		        	typeToSaveFolder = PersistenceGeoParser.SAVE_FOLDER_TYPES.GROUP;
		        	userOrGroupToSaveFolder = value.id;
		        	toRemove.push(saveLayerForm.add({
				         fieldLabel:'Select parent'
								,xtype:'combo'
								,name: 'folderId'
						        ,displayField:'name'
						        ,valueField:'id'
						        ,store: new Ext.data.JsonStore({
						             url: getFoldersUrl(),
						             remoteSort: false,
						             autoLoad:true,
						             idProperty: 'id',
						             root: 'data',
						             totalProperty: 'results',
						             fields: ['id','name']
						         })
						        ,triggerAction:'all'
						        ,mode:'local',
	           					 listeners:{
	     					        change: function(field, newValue, oldValue){
	     					        	paramsToSend["folderId"] = newValue;
	     					        }
	     					     }
							}));
		        	saveLayerForm.doLayout();
		            }}
		        }
			},
			{
		         fieldLabel:'Select user'
				,xtype:'combo'
		        ,displayField:'username'
		        ,valueField:'id'
		        ,store: new Ext.data.JsonStore({
		             url: allUsersUrl,
		             remoteSort: false,
		             autoLoad:true,
		             idProperty: 'username',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['id','username']
		         })
		        ,triggerAction:'all'
		        ,mode:'local'
		        ,listeners:{select:{fn:function(combo, value) {
		        	saveLayerUrl = saveLayerBaseUrl + value.id;
		        	user = value.id;
		        	typeToSaveFolder = PersistenceGeoParser.SAVE_FOLDER_TYPES.USER;
		        	userOrGroupToSaveFolder = value.id;
		        	toRemove.push(saveLayerForm.add({
				         fieldLabel:'Select parent'
								,xtype:'combo'
								,name: 'folderId'
						        ,displayField:'name'
						        ,valueField:'id'
						        ,store: new Ext.data.JsonStore({
						             url: getFoldersUrl(),
						             remoteSort: false,
						             autoLoad:true,
						             idProperty: 'id',
						             root: 'data',
						             totalProperty: 'results',
						             fields: ['id','name']
						         })
						        ,triggerAction:'all'
						        ,mode:'local',
	           					 listeners:{
	     					        change: function(field, newValue, oldValue){
	     					        	paramsToSend["folderId"] = newValue;
	     					        }
	     					     }
							}));
		        	saveLayerForm.doLayout();
		            }}
		        }
			}
			,fileCombo],
		    buttons: [{
				text: 'Save',
				handler: function() {
					fnLoadForm(saveLayerForm, saveLayerUrl);
				}
			}]
	});
	
	var loadLayersForm = new Ext.FormPanel({
        title: 'Load layers By user or group',
		cls: 'my-form-class',
		width: 350,
		height: 200,
		items: [
			{
		         fieldLabel:'Select user'
				,xtype:'combo'
		        ,displayField:'username'
		        ,valueField:'id'
		        ,store: new Ext.data.JsonStore({
		             url: allUsersUrl,
		             remoteSort: false,
		             autoLoad:true,
		             idProperty: 'username',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['id','username']
		         })
		        ,triggerAction:'all'
		        ,mode:'local'
		        ,listeners:{select:{fn:function(combo, value) {
		        	PersistenceGeoParser.loadLayersByUser(value.id, function(layers){
		        		console.log(layers);
		        		app.mapPanel.map.addLayers(layers);
		        	});
		            }}
		        }
			},
			{
		         fieldLabel:'Select group'
				,xtype:'combo'
				,name: 'userGroup'
		        ,displayField:'nombre'
		        ,valueField:'id'
		        ,store: new Ext.data.JsonStore({
		             url: allGroupsUrl,
		             remoteSort: false,
		             autoLoad:true,
		             idProperty: 'id',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['id','nombre']
		         })
		        ,triggerAction:'all'
		        ,mode:'local'
		        ,listeners:{select:{fn:function(combo, value) {
		        	PersistenceGeoParser.loadLayersByGroup(value.id, function(layers){
		        		console.log(layers);
		        		app.mapPanel.map.addLayers(layers);
		        	});
		            }}
		        }
			}]
	});
	
	 var window = new Ext.Window({
	        title: 'Example Forms',
	        width: 600,
	        height:400,
	        minWidth: 200,
	        minHeight: 200,
	        layout: 'fit',
	        plain:true,
	        bodyStyle:'padding:5px;',
	        buttonAlign:'center',
	        items: [{
	        	xtype: "tabpanel",
	            anchor: "95%",
	            activeTab: 0,
	            items: [saveUserForm, loadLayersForm, saveLayerForm, saveFolderForm]
	        	}]
	    });
	 
	 window.show();
	
    app = new gxp.Viewer({
        portalConfig: {
            layout: "border",
            width: 600,
            height: 400,
            border: false,
            items: [{
                xtype: "panel",
                region: "center",
                border: false,
                layout: "fit",
                items: ["map-viewport"]
            }, {
                id: "tree-container",
                xtype: "container",
                layout: "fit",
                region: "west",
                width: 200
            },]
        },
        
        // configuration of all tool plugins for this application
        tools: [{
            ptype: "gxp_layertree",
            outputConfig: {
                id: "tree",
                border: true,
                tbar: [] // we will add buttons to "tree.bbar" later
            },
            outputTarget: "tree-container"
        }, {
            ptype: "gxp_addlayers",
            actionTarget: "tree.tbar"
        }, {
            ptype: "gxp_removelayer",
            actionTarget: ["tree.tbar", "tree.contextMenu"]
        }, {
            ptype: "gxp_zoomtoextent",
            actionTarget: "map.tbar"
        }, {
            ptype: "gxp_zoom",
            actionTarget: "map.tbar"
        }, {
            ptype: "gxp_navigationhistory",
            actionTarget: "map.tbar"
        }],
        
        // layer sources
        sources: {
            mapbox: {
                ptype: "gxp_mapboxsource"
            },
            mapquest: {
                ptype: "gxp_mapquestsource"
            }
        },
        
        // map and layers
        map: {
            id: "map-viewport", // id needed to reference map in items above
            title: "Map",
            projection: "EPSG:900913",
            units: "m",
            maxResolution: 156543.03390625,
            center: [0, 0],
            zoom: 1,
            layers: [{
                source: "mapbox",
                name: "blue-marble-topo-bathy-jan",
                group: "background"
            }],
            items: [{
                xtype: "gx_zoomslider",
                vertical: true,
                height: 100
            },]
        }
    });
});



function loadLayerType(theForm, layerType){
	
	paramsToSend = {};
	
	while (toRemove.length>0){
		theForm.remove(toRemove.pop());
	}
	
    store = new Ext.data.JsonStore({
		             url: loadLayerTypeUrl + layerType.id,
		             remoteSort: false,
		             autoLoad:true,
		             idProperty: 'name',
		             root: 'data',
		             totalProperty: 'results',
		             fields: ['name'],
		             listeners: {
		                 load: function(store, records, options) {
		                	 var i = 0; 
		                	 while (i<records.length){
		                		 toRemove.push(theForm.add({
		             					xtype: 'textfield',
		            					fieldLabel: records[i].id,
		            					name: records[i++].id,
		            					 listeners:{
	            					        change: function(field, newValue, oldValue){
	            					        	paramsToSend[field.name] = newValue;
	            					        }
	            					     }
			                		 }));
		                	 }
		                	 theForm.doLayout();
		                 }
		             }
		         });
}

function fnLoadForm(theForm, url)
{

	if(url == saveLayerUrl || url == saveLayerGroupBaseUrl && !!!theForm.items.items[4].getValue()){
		fnSaveLayerForm(theForm, url);
	}else{
		theForm.getForm().getEl().dom.action = url;
		theForm.getForm().getEl().dom.method = 'POST';
		theForm.getForm().submit();
	}
	
} //end fnLoadForm
function fnSaveLayerForm(theForm, url)
{
	var params = {
			name: theForm.items.items[0].getValue(),
			server_resource: theForm.items.items[1].getValue(),
			type: theForm.items.items[2].getValue(),
			properties: paramsToSend
		 };
	
	if(url.indexOf(saveLayerGroupBaseUrl) != -1){
		PersistenceGeoParser.saveLayerByGroup(user, params, 
				function(form, action) {
					Ext.Msg.alert('Layer saved', action.response.responseText);
				},
				function(form, action) {
					if(!!action
							&& !!action.response
							&& !!action.response.status
							&& action.response.status == "200"
							&& !!action.response.responseText){
						Ext.Msg.alert('Layer saved', action.response.responseText);
					}else{
						Ext.Msg.alert('Warning', 'Error Unable to Load Form Data.');
					}
				});
	}else{
		PersistenceGeoParser.saveLayerByUser(user, params, 
				function(form, action) {
					Ext.Msg.alert('Layer saved', action.response.responseText);
				},
				function(form, action) {
					if(!!action
							&& !!action.response
							&& !!action.response.status
							&& action.response.status == "200"
							&& !!action.response.responseText){
						Ext.Msg.alert('Layer saved', action.response.responseText);
					}else{
						Ext.Msg.alert('Warning', 'Error Unable to Load Form Data.');
					}
				});
	}
} //end fnLoadForm
function fnUpdateForm(theForm)
{
	theForm.getForm().submit({
		success: function(form, action) {
			Ext.Msg.alert('Success', 'Data is stored in session.');
			form.reset();
		},
		failure: function(form, action) {
			Ext.Msg.alert('Warning', action.result.errorMessage);
		}
	});
} //end fnUpdateForm
function fnResetForm(theForm)
{
	theForm.getForm().reset();
	Ext.getCmp('mf.btn.add').setDisabled(true);
	Ext.getCmp('mf.btn.reset').setDisabled(true);
} //end fnResetForm
