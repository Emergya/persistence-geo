var saveLayerBaseUrl = "rest/persistenceGeo/saveLayerByUser/";
var saveLayerUrl;

var createUserUrl = "rest/persistenceGeo/admin/createUser";

var loadLayersUrl = "rest/persistenceGeo/loadLayers/";

var allGroupsUrl = "rest/persistenceGeo/getAllGroups";

var allUsersUrl = "rest/persistenceGeo/getAllUsers";

Ext.onReady(function(){
	Ext.QuickTips.init();
	
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
			renderTo: Ext.getBody(),
			frame: true,
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

	var saveLayerForm = new Ext.FormPanel({
			url: saveLayerUrl,
	        title: 'Save layer Form',
			renderTo: Ext.getBody(),
	        fileUpload: true,
			frame: true,
			cls: 'my-form-class',
			width: 350,
			height: 200,
			items: [{
					xtype: 'textfield',
					fieldLabel: 'Name',
					name: 'name'
			},{
					xtype: 'textfield',
					fieldLabel: 'Type',
					name: 'type'
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
			},
			fileCombo],
		    buttons: [{
				text: 'Save',
				handler: function() {
					fnLoadForm(saveLayerForm, saveLayerUrl);
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
	            items: [saveUserForm,saveLayerForm]
	        	}]
	    });
	 
	 window.show();

});

function fnLoadForm(theForm, url)
{

	if(url == saveLayerUrl && !!!theForm.items.items[3].getValue()){
		fnSaveLayerForm(theForm, url);
	}else{
		theForm.getForm().getEl().dom.action = url;
		theForm.getForm().getEl().dom.method = 'POST';
		theForm.getForm().submit();
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
		params.properties = "test===test,,,other===other";
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
