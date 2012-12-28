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
PersistenceGeo.tree.RemoveLayer = Ext.extend(gxp.plugins.RemoveLayer, {
    
    /** api: ptype = pgeo_removelayer */
    ptype: "pgeo_removelayer",
    
    /** api: method[addActions]
     */
    addActions: function() {
        var selectedLayer;
        var actions = gxp.plugins.RemoveLayer.superclass.addActions.apply(this, [{
            menuText: this.removeMenuText,
            iconCls: "gxp-icon-removelayers",
            disabled: true,
            tooltip: this.removeActionTip,
            handler: function() {
                var record = selectedLayer;
                if(record) {
                    if(this.target.isAuthorized() 
                        && !!record.getLayer().layerID){
                        this.target.persistenceGeoContext.removeLayer(record.getLayer());
                    }
                    Ext.Ajax.request({
                        url: "http://gofre.emergya.es:8080/geoserver/j_spring_security_check",
                        method: "POST",
                        params: {
                            username: "gore",
                            password: "gore"
                        }
                    });

                    this.target.mapPanel.layers.remove(record);
                }
            },
            scope: this
        }]);
        var removeLayerAction = actions[0];

        this.target.on("layerselectionchange", function(record) {
            selectedLayer = record;
            removeLayerAction.setDisabled(
                this.target.mapPanel.layers.getCount() <= 1 || !record
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
    }
        
});

Ext.preg(PersistenceGeo.tree.RemoveLayer.prototype.ptype, PersistenceGeo.tree.RemoveLayer);
