(function() {

    var jsfiles = new Array(
        "persistenceGeo/loaders/WMSLoader.js",
        "persistenceGeo/loaders/WFSLoader.js",
        "persistenceGeo/loaders/KMLLoader.js",
        "persistenceGeo/loaders/GMLLoader.js",
        "persistenceGeo/PersistenceGeoParser.js"
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

