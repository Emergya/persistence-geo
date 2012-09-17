var saveLayerUrl = "rest/persistenceGeo/saveLayerByUser/userTest";

var loadLayersUrl = "rest/persistenceGeo/loadLayers/";

Ext.onReady(function(){
	Ext.QuickTips.init();

	var saveLayerForm = new Ext.FormPanel({
			url: saveLayerUrl,
	        title: 'Save layer Form',
			renderTo: Ext.getBody(),
			frame: true,
			cls: 'my-form-class',
			width: 350,
			items: [{
					xtype: 'textfield',
					fieldLabel: 'Name',
					name: 'name'
			},{
					xtype: 'textfield',
					fieldLabel: 'Type',
					name: 'type'
			}],
		    buttons: [{
				id: 'mf.btn.load',
				text: 'Save',
				handler: function() {
					fnLoadForm(saveLayerForm, saveLayerUrl);
				}
			}]
	});
	
	
	
	
	 var window = new Ext.Window({
	        title: 'Example Forms',
	        width: 350,
	        height:200,
	        minWidth: 200,
	        minHeight: 200,
	        layout: 'fit',
	        plain:true,
	        bodyStyle:'padding:5px;',
	        buttonAlign:'center',
	        items: saveLayerForm
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