/**
 * Copyright (c) 2008-2011 The Open Planning Project
 * 
 * Published under the GPL license.
 * See https://github.com/opengeo/gxp/raw/master/license.txt for the full text
 * of the license.
 */

/**
 * @requires plugins/Tool.js
 */

/** api: (define)
 *  module = gxp.plugins
 *  class = RemoveLayer
 */

/** api: (extends)
 *  plugins/Tool.js
 */
Ext.namespace("PersistenceGeo.tree");

/** api: constructor
 *  .. class:: RemoveLayer(config)
 *
 *    Plugin for removing a selected layer from the map.
 *    TODO Make this plural - selected layers
 */
PersistenceGeo.tree.MakeLayerPersistent = Ext.extend(gxp.plugins.Tool, {
    
    /** api: ptype = pgeo_makelayerpersistent */
    ptype: "pgeo_makelayerpersistent",

    makePersistentText: "Make persistent",
    makePersistentTooltipText: "Make a layer persistent to logged user",
    
    /** api: method[addActions]
     */
    addActions: function() {
        var selectedLayer;
        var actions = PersistenceGeo.tree.MakeLayerPersistent.superclass.addActions.apply(this, [{
            menuText: this.makePersistentText,
            iconCls: "gxp-icon-savelayers",
            disabled: true,
            tooltip: this.makePersistentTooltipText,
            handler: function() {
                var record = selectedLayer;
                if(record) {
                    if(this.target.isAuthorized() 
                        && !record.getLayer().layerID){
                        this.showSaveLayerWindow(record)
                    }
                }
            },
            scope: this
        }]);
        var removeLayerAction = actions[0];

        this.target.on("layerselectionchange", function(record) {
            selectedLayer = record;
            removeLayerAction.setDisabled(
                this.target.isAuthorized() 
                    &&  !!record 
                    &&  record.getLayer().layerID
            );
        }, this);
        var enforceOne = function(store) {
            removeLayerAction.setDisabled(
                !selectedLayer || store.getCount() <= 1
            );
        };
        this.target.mapPanel.layers.on({
            "add": enforceOne,
            "remove": enforceOne
        });
        
        return actions;
    },



    /**
     * private: method[showSaveLayerWindow]
     * Show a dialog to save a layerRecord
     */
    showSaveLayerWindow: function (layerRecord){
        var saveWindow = new Ext.Window({
            title: this.makePersistentText,
            closeAction: 'hide',
            width:500,
        });
        var savePanel = new Viewer.widgets.SaveLayerPanel({
            layerRecord: layerRecord,
            authorized: this.target.isAuthorized(),
            target: this.target,
            saveWindow: saveWindow,
            outputTarget: false
        });
        saveWindow.add(savePanel);
        saveWindow.show();
    }
        
});

Ext.preg(PersistenceGeo.tree.MakeLayerPersistent.prototype.ptype, PersistenceGeo.tree.MakeLayerPersistent);
