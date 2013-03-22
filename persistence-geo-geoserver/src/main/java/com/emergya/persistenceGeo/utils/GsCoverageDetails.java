package com.emergya.persistenceGeo.utils;

import it.geosolutions.geoserver.rest.decoder.RESTCoverageStore;
import it.geosolutions.geoserver.rest.decoder.utils.JDOMBuilder;

import org.jdom.Element;

public class GsCoverageDetails {
	private final Element cs;


	public GsCoverageDetails(Element cs) {
		this.cs = cs;
	}

    public static GsCoverageDetails build(String response) {
        if(response == null)
            return null;
        if(response.isEmpty())
        	return new GsCoverageDetails(new Element("coverageStore")); 
        
        Element pb = JDOMBuilder.buildElement(response);
        if(pb != null)
            return new GsCoverageDetails(pb);
        else
            return null;
	}

	public String getName() {
		return cs.getChildText("name");
	}

	public String getWorkspaceName() {
		return cs.getChild("workspace").getChildText("name");
	}
	
	public String getNativeCRS(){
		return cs.getChild("nativeBoundingBox").getChildText("crs");
	}
	
	public String getDeclaredSRS(){
		return cs.getChildText("srs");
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
