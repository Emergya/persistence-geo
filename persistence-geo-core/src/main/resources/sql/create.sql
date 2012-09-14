-- Role: persistence_geo

-- DROP ROLE persistence_geo;
 CREATE ROLE persistence_geo LOGIN
  NOSUPERUSER INHERIT NOCREATEDB CREATEROLE NOREPLICATION
  PASSWORD 'persistence_geo';

-- Tablespace: persistence_geo_db

-- mkdir /etc/postgresql/9.1/main/persistence_geo
-- mkdir /etc/postgresql/9.1/main/persistence_geo/persistence_geo_db
-- DROP TABLESPACE persistence_geo_db
 CREATE TABLESPACE persistence_geo_db
  OWNER "persistence_geo"
  LOCATION '/etc/postgresql/9.0/main/persistence_geo/persistence_geo_db';

-- Database: persistence_geo_db

-- Database: persistence_geo_db

-- DROP DATABASE persistence_geo_db;

 CREATE DATABASE persistence_geo_db
  WITH OWNER = "persistence_geo"
       ENCODING = 'UTF8'
       TABLESPACE = "persistence_geo_db"
       LC_COLLATE = 'es_ES.UTF-8'
       LC_CTYPE = 'es_ES.UTF-8'
       CONNECTION LIMIT = -1;

-- Schema: persistence_geo_db (ya como usuario persistence_geo)

-- DROP SCHEMA persistence_geo CASCADE;

CREATE SCHEMA persistence_geo
  AUTHORIZATION persistence_geo;
