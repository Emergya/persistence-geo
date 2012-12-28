/**
 * Copyright (c) 2008-2011 The Open Planning Project
 * 
 * Published under the GPL license.
 * See https://github.com/opengeo/gxp/raw/master/license.txt for the full text
 * of the license.
 */

/**
 * @require plugins/LayerManager.js
 */

/** api: (define)
 *  module = PersistenceGeo.plugins
 *  class = LayerManager
 */

/** api: (extends)
 *  plugins/LayerManager.js
 */
Ext.namespace("PersistenceGeo.tree");

/** api: constructor
 *  .. class:: LayerManager(config)
 *
 *    Plugin for adding a tree of layers with their legend to a
 *    :class:`gxp.Viewer`. Also provides a context menu on layer nodes.
 */   
/** api: example
 *  If you want to change the vendor-specific legend_options parameter that 
 *  is sent to the WMS for GetLegendGraphic you can use ``baseAttrs`` on the
 *  ``loader`` config:
 *
 *  .. code-block:: javascript
 *
 *    var layerManager = new gxp.plugins.LayerManager({
 *        loader: {
 *            baseAttrs: {
 *                baseParams: {
 *                    legend_options: "fontAntiAliasing:true;fontSize:11;fontName:Arial;fontColor:#FFFFFF"
 *                }
 *            }
 *        }
 *    });
 *
 */
PersistenceGeo.tree.LayerManager = Ext.extend(gxp.plugins.LayerManager, {
    
    /** api: ptype = pgeo_layermanager */
    ptype: "pgeo_layermanager",

    addGroup: function(groupProperties){
        var group = groupProperties.group, 
            groupIndex = groupProperties.groupIndex, 
            filter = groupProperties.filter;

        var filterFunction;
        if (!!filter){
            filterFunction = filter;
        }else{
            filterFunction = (function(group, groupIndex) {
                return function(record) {
                    if(!!record.getLayer().groupLayers){
                        return !!groupIndex && 
                            (
                                (record.getLayer().groupLayers == groupIndex)
                                || (record.getLayer().groupID == groupIndex)
                            );
                    }else{
                        return (record.get("group") || defaultGroup) == group &&
                            record.getLayer().displayInLayerSwitcher == true;
                    }
                };
            })(group, groupIndex);
        }

        var baseAttrs;
        if (this.initialConfig.loader && this.initialConfig.loader.baseAttrs) {
            baseAttrs = this.initialConfig.loader.baseAttrs;
        }

        var defaultGroup = this.defaultGroup,
            plugin = this,
            groupConfig,
            exclusive;

        groupConfig = typeof this.groups[group] == "string" ?
            {title: this.groups[group]} : this.groups[group] 
                ? this.groups[group] : {title: group};
        exclusive = groupConfig.exclusive;

        var child = new GeoExt.tree.LayerContainer(Ext.apply({
                text: groupConfig.title,
                iconCls: "gxp-folder",
                expanded: true,
                group: group == this.defaultGroup ? undefined : group,
                loader: new GeoExt.tree.LayerLoader({
                    baseAttrs: exclusive ?
                        Ext.apply({checkedGroup: Ext.isString(exclusive) ? exclusive : group}, baseAttrs) :
                        baseAttrs,
                    store: this.target.mapPanel.layers,
                    filter: filterFunction,
                    createNode: function(attr) {
                        plugin.configureLayerNode(this, attr);
                        return GeoExt.tree.LayerLoader.prototype.createNode.apply(this, arguments);
                    }
                }),
                singleClickExpand: true,
                allowDrag: false,
                listeners: {
                    append: function(tree, node) {
                        node.expand();
                    }
                }
            }, groupConfig));
        
        if (!!groupIndex){
            this.addedChildren[groupIndex] = this.treeRoot.appendChild(child);
        }else{ 
            this.addedChildren[group] = this.treeRoot.appendChild(child);
        }
    },

    addedChildren: {},

    removeGroup: function (group){
        //this.treeRoot.removeChild(this.addedChildren[group]);
        var child = this.addedChildren[group];
        child.destroy();
    },
    
    /** private: method[createOutputConfig]
     *  :returns: ``Object`` Configuration object for an Ext.tree.TreePanel
     */
    createOutputConfig: function() {
        var treeRoot = new Ext.tree.TreeNode({
            text: this.rootNodeText,
            expanded: true,
            isTarget: false,
            allowDrop: false
        });

        this.treeRoot = treeRoot;

        for (var group in this.groups) {
            this.addGroup({group: group});
        }
        
        var treePanel = {
            xtype: "treepanel",
            cls: "gxp-layermanager-tree",
            root: treeRoot,
            rootVisible: false,
            shortTitle: this.shortTitle,
            border: false,
            enableDD: true,
            selModel: new Ext.tree.DefaultSelectionModel({
                listeners: {
                    beforeselect: this.handleBeforeSelect,
                    scope: this
                }
            }),
            listeners: {
                contextmenu: this.handleTreeContextMenu,
                beforemovenode: this.handleBeforeMoveNode,                
                scope: this
            },
            contextMenu: new Ext.menu.Menu({
                items: []
            }),
            plugins: [{
                ptype: "gx_treenodecomponent"
            }]
        };
        Ext.applyIf(treePanel, Ext.apply({
            cls: "gxp-layermanager-tree",
            lines: false,
            useArrows: true,
            groups:{
                "default": this.overlayNodeText,
                "background": {
                    title: "back",
                    exclusive: true
                }
            },
            plugins: [{
                ptype: "gx_treenodecomponent"
            }]
        }, this.treeConfig));
        return treePanel;
    },

    /** private: method[handleTreeContextMenu]
     */
    handleTreeContextMenu: function(node, e) {
        if(node && node.layer) {
            node.select();
            var tree = node.getOwnerTree();
            var itemElementArray = null;
            if (tree.getSelectionModel().getSelectedNode() === node) {
                var c = tree.contextMenu;
                if(node.layer.url 
                    && node.layer.url.indexOf(this.target.sources.local.url.replace("/ows", "")) != -1){
                        itemElementArray = this.searchItemOnTree(this.target.itemsActivateToGeoserverArray, tree);
                        if(itemElementArray != null){
                            for(var i=0; i<itemElementArray.length; i++){
                                if(itemElementArray[i].disabled == true){
                                    itemElementArray[i].enable();
                                }
                            }
                        }
                }else{
                    itemElementArray = this.searchItemOnTree(this.target.itemsActivateToGeoserverArray, tree);
                    if(itemElementArray != null){
                        for(var i=0; i<itemElementArray.length; i++){
                            if(itemElementArray[i].disabled == false){
                                itemElementArray[i].disable();
                            }
                        }
                    } 
                }
                c.contextNode = node;
                c.items.getCount() > 0 && c.showAt(e.getXY());
            }
        }
    },

    /** privat: method[searchItemOnTree]*/
    searchItemOnTree: function(idItemArray, tree){
        var ret = [];
        var items = [];
        if(tree.contextMenu && tree.contextMenu.items && tree.contextMenu.items.items){
             items = tree.contextMenu.items.items
             if(items != null){
                for(var i=0; i<items.length; i++){
                    for(var j=0; j<idItemArray.length; j++){
                        if(items[i].scope && items[i].scope.id && items[i].scope.id == idItemArray[j]){
                            ret.push(items[i]);
                        }
                    }
                }
             }
        }
        return ret;
    }
});

Ext.preg(PersistenceGeo.tree.LayerManager.prototype.ptype, PersistenceGeo.tree.LayerManager);
