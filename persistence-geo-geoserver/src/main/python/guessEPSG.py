#!/usr/bin/python
import sys
from osgeo import osr

def esriprj2standards(shapeprj_path):
   prj_file = open(shapeprj_path, 'r')
   prj_txt = prj_file.read()
   srs = osr.SpatialReference()
   srs.ImportFromESRI([prj_txt])
   #print 'Shape prj is: %s' % prj_txt
   #print 'WKT is: %s' % srs.ExportToWkt()
   #print 'Proj4 is: %s' % srs.ExportToProj4()
   srs.AutoIdentifyEPSG()
   code = srs.GetAuthorityCode(None)
   result = -1
   if code != None:
       result = code   
   print 'EPSG:%s' % result

esriprj2standards(sys.argv[1])
