(function() {

    var jsfiles = new Array(
        "persistenceGeo/lib/loaders/AbstractLoader.js",
        "persistenceGeo/lib/loaders/WMSLoader.js",
        "persistenceGeo/lib/loaders/WFSLoader.js",
        "persistenceGeo/lib/loaders/KMLLoader.js",
        "persistenceGeo/lib/loaders/GMLLoader.js",
        "persistenceGeo/lib/Parser.js",
        "persistenceGeo/lib/PersistenceGeoParser.js"
    );
    
    var scripts = document.getElementsByTagName("script");
    var parts = scripts[scripts.length-1].src.split("/");
    parts.pop();
    var path = parts.join("/");

    var len = jsfiles.length;
    var pieces = new Array(len);

    for (var i=0; i<len; i++) {
        pieces[i] = "<script src='" + path + "/" + jsfiles[i] + "'></script>"; 
    }
    document.write(pieces.join(""));

})();
