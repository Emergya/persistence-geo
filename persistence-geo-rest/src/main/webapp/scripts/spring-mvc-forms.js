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
//	theForm.submit();
//	console.log(theForm);
//	console.log(theForm.items.items[0].getValue());
//	//for the purpose of this tutorial, load 1 record.
//	theForm.getForm().load({
//		url: loadUrl,
//		headers: {Accept: 'application/json, text/javascript, */*; q=0.01'},
//		Params: {name: theForm.items.items[0].getValue(), 
//				 type: theForm.items.items[1].getValue()},
//    waitMsg: 'loading...',
//		params : {
//			id: 1
//		},
//		success: function(form, action) {
//			Ext.getCmp('mf.btn.add').setDisabled(false);
//			Ext.getCmp('mf.btn.reset').setDisabled(false);
//			Ext.getCmp('mf.btn.load').setDisabled(true);
//		},
//		failure: function(form, action) {
//			Ext.Msg.alert('Warning', 'Error Unable to Load Form Data.');
//		}
//	});
	
	theForm.getForm().getEl().dom.action = url;
	theForm.getForm().getEl().dom.method = 'POST';
	theForm.getForm().submit();
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