INSERT INTO persistence_geo.layer_type_property(
            id, name)
    VALUES (1, 'projection');
INSERT INTO persistence_geo.layer_type_property(
            id, name)
    VALUES (2, 'url');
    
INSERT INTO persistence_geo.layer_type_property_layer_type_property(
            layer_type_property_id, defaultproperties_id)
    VALUES (1, 1);
INSERT INTO persistence_geo.layer_type_property_layer_type_property(
            layer_type_property_id, defaultproperties_id)
    VALUES (1, 2);