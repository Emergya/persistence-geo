var saveLayerBaseUrl = "../rest/persistenceGeo/saveLayerByUser/";
var saveLayerUrl;

var createUserUrl = "../rest/persistenceGeo/admin/createUser";

var loadLayersUrl = "../rest/persistenceGeo/loadLayers/";

var allGroupsUrl = "../rest/persistenceGeo/getAllGroups";

var allUsersUrl = "../rest/persistenceGeo/getAllUsers";

var allLayerTypesUrl = "../rest/persistenceGeo/getLayerTypes";

var loadLayerTypeUrl = "../rest/persistenceGeo/getLayerTypeProperties/";

var store;
var toRemove = new Array();
var paramsToSend = {};
var app;

Ext.onReady(function() {

	var loadLayersUrl = "../rest/persistenceGeo/loadLayers/";
	var allUsersUrl = "../rest/persistenceGeo/getAllUsers";
	
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
		            }}
		        }
			}
//			,
//			fileCombo
			],
		    buttons: [{
				text: 'Save',
				handler: function() {
					fnLoadForm(saveLayerForm, saveLayerUrl);
				}
			}]
	});
	
	var loadLayersForm = new Ext.FormPanel({
        title: 'Load layers By user',
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
		        		//console.log(layers);
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
	            items: [
	                    saveUserForm
	                    ,
	                    loadLayersForm
	                    ,saveLayerForm
	                    ]
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

//	if(url == saveLayerUrl && !!!theForm.items.items[3].getValue()){
		fnSaveLayerForm(theForm, url);
//	}else{
//		theForm.getForm().getEl().dom.action = url;
//		theForm.getForm().getEl().dom.method = 'POST';
//		theForm.getForm().submit();
	}
	
} //end fnLoadForm
function fnSaveLayerForm(theForm, url)
{
	
	theForm.getForm().getEl().dom.action = url;
	theForm.getForm().getEl().dom.method = 'POST';
	
	var params = {
			name: theForm.items.items[0].getValue(), 
			type: theForm.items.items[1].getValue()
		 };
	
	if(!!theForm.items.items[3].getValue()){ //TODO
		params.uploadfile = theForm.items.items[3].getValue();
	}
	
	if(true){ //if properties != null
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
			}
			aux--;
		}
	}
	
	theForm.getForm().load({
		url: url,
		headers: {Accept: 'application/json, text/javascript, */*; q=0.01'},
		waitMsg: 'loading...',
		params : params,
        fileUpload: true,
		success: function(form, action) {
			Ext.Msg.alert('OK', 'OK.');
		},
		failure: function(form, action) {
			if(!!action
					&& !!action.response
					&& !!action.response.status
					&& action.response.status == "200"
					&& !!action.response.responseText){
				Ext.Msg.alert('Layer saved', action.response.responseText);
			}else{
				Ext.Msg.alert('Warning', 'Error Unable to Load Form Data.');
			}
		}
	});
	
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
