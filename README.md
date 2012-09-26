persistence-geo
===============

This component saves map configuration (at this time only layers) in a database and implements a Parser to generate [OpenLayers](http://openlayers.org) layers to be load in a OpenLayers map or another OpenLayers extension as [GeoExt](http://geoext.org/) or [GXP](https://github.com/opengeo/gxp)

# Tested on / Dependencies

* Jetty plugin
* Tomcat 6, 7 (with openjdk 6)
* PostgreSQL (9.1)

# Test it

Steps to use this library

## Database init

You need to create a database and a schema on PostgreSQL. You can see [create.sql](https://github.com/emergya/persistence-geo/blob/master/persistence-geo-core/src/main/resources/sql/create.sql)

You still need to edit [local.properties](https://github.com/emergya/persistence-geo/blob/master/persistence-geo-core/src/main/filters/local.properties) to your database configuration.

Then you need to launch database initializer with persistence-geo-core:

<pre>
~persistence-geo/persistence-geo-core$ mvn clean test -Pfirst -DfilterToApply=local
</pre>

## Running and testing

You can test it with jetty, but it's recommended to launch it on tomcat

<pre>
~persistence-geo$ mvn clean install -Plocal
</pre>

### Run on jetty

Run jetty in persistence-geo-rest directory:
<pre>
~persistence-geo/persistence-geo-rest$ mvn clean jetty:run -Plocal
</pre>

### Run on tomcat

Only copy war on webapps directory:
<pre>
~persistence-geo/persistence-geo-rest$ sudo cp target/persistence-geo-rest.war /var/lib/tomcat7/webapps/
</pre>

### Testing

Now you can see a demo with GXP mapbox on http://localhost:8080/persistence-geo-rest/

# Limitations

This software is an alpha snapshot:

* Only loads WMS layers
* Integration with layer tree not implemented yet

# Next steps

We're working in the next features:

* Load WFS, KML, GML layers.
* Save and resque map configuration by admin.
* Save and load layers by group.
* Save and load folders.
