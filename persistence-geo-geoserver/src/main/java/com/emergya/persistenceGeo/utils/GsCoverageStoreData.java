package com.emergya.persistenceGeo.utils;

import it.geosolutions.geoserver.rest.decoder.utils.JDOMBuilder;

import org.jdom.Element;

/**
 * Parse <TT>CoverageStore</TT>s returned as XML REST objects.
 * <P>
 * This is the XML document returned by GeoServer when requesting a CoverageStore:
 * <PRE>
 * {@code 
 * <coverageStore>
 *      <name>testRESTStoreGeotiff</name>
 *      <type>GeoTIFF</type>
 *      <enabled>true</enabled>
 *      <workspace>
 *          <name>it.geosolutions</name>
 *          <href>http://localhost:8080/geoserver/rest/workspaces/it.geosolutions.xml</href>
 *      </workspace>
 *      <url>file:/home/geosolutions/prj/git/gman/target/test-classes/testdata/resttestdem.tif</url>
 *      <coverages>
 *          <atom:link xmlns:atom="http://www.w3.org/2005/Atom" rel="alternate"
 *              href="http://localhost:8080/geoserver/rest/workspaces/it.geosolutions/coveragestores/testRESTStoreGeotiff/external/coverages.xml"
 *              type="application/xml"/>
 *      </coverages>
 * </coverageStore>
 * }
 * </PRE>
 *
 * <I>Note: the whole XML fragment is stored in memory. At the moment, there are
 * methods to retrieve only the more useful data.
 * 
 * @author  <a href="mailto:lroman@emergya.com">lroman</a>
 */
public class GsCoverageStoreData  {
	private final Element cs;


	public GsCoverageStoreData(Element cs) {
		this.cs = cs;
	}

    public static GsCoverageStoreData build(String response) {
        if(response == null)
            return null;
        if(response.isEmpty())
        	return new GsCoverageStoreData(new Element("coverageStore")); 
        
        Element pb = JDOMBuilder.buildElement(response);
        if(pb != null)
            return new GsCoverageStoreData(pb);
        else
            return null;
	}

	public String getName() {
		return cs.getChildText("name");
	}

	public String getWorkspaceName() {
		return cs.getChild("workspace").getChildText("name");
	}
	
	public String getURL() {
		return cs.getChildText("url");
	}

    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName())
                .append('[');
        if(cs == null)
            sb.append("null");
        else
            sb.append("name:").append(getName())
                .append(" wsname:").append(getWorkspaceName());

        return sb.toString();
    }
}
