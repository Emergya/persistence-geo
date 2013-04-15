package com.emergya.persistenceGeo.utils;

import it.geosolutions.geoserver.rest.decoder.utils.JDOMBuilder;

import org.jdom.Element;

public class GsCoverageDetails {
	private final Element cs;
	private final static String NATIVE_BOUNDING_BOX = "nativeBoundingBox";
	private final static String LAT_LON_BOUNDING_BOX = "latLonBoundingBox";
	private final static String MINX = "minx";
	private final static String MINY = "miny";
	private final static String MAXX = "maxx";
	private final static String MAXY = "maxy";
	private final static String CRS = "crs";

	private BoundingBox nativeBoundingBox = null;
	private BoundingBox latLonBoundingBox = null;

	public GsCoverageDetails(Element cs) {
		this.cs = cs;
		this.nativeBoundingBox = new BoundingBox();
		this.latLonBoundingBox = new BoundingBox();

		Element nativeBBNode = cs.getChild(NATIVE_BOUNDING_BOX);
		Element latlonBBNode = cs.getChild(LAT_LON_BOUNDING_BOX);

		if (nativeBBNode != null) {
			nativeBoundingBox.setSrs(nativeBBNode.getChildTextTrim(CRS));
			nativeBoundingBox.setMinx(Double.valueOf(nativeBBNode
					.getChildTextTrim(MINX)));
			nativeBoundingBox.setMiny(Double.valueOf(nativeBBNode
					.getChildTextTrim(MINY)));
			nativeBoundingBox.setMaxx(Double.valueOf(nativeBBNode
					.getChildTextTrim(MAXX)));
			nativeBoundingBox.setMaxy(Double.valueOf(nativeBBNode
					.getChildTextTrim(MAXY)));
		}
		if (latlonBBNode != null) {
			latLonBoundingBox.setSrs(latlonBBNode.getChildTextTrim(CRS));
			latLonBoundingBox.setMinx(Double.valueOf(latlonBBNode
					.getChildTextTrim(MINX)));
			latLonBoundingBox.setMiny(Double.valueOf(latlonBBNode
					.getChildTextTrim(MINY)));
			latLonBoundingBox.setMaxx(Double.valueOf(latlonBBNode
					.getChildTextTrim(MAXX)));
			latLonBoundingBox.setMaxy(Double.valueOf(latlonBBNode
					.getChildTextTrim(MAXY)));
		}
	}

	public static GsCoverageDetails build(String response) {
		if (response == null) {
			return null;
		}
		if (response.isEmpty()) {
			return new GsCoverageDetails(new Element("coverageStore"));
		}

		Element pb = JDOMBuilder.buildElement(response);
		if (pb != null) {
			return new GsCoverageDetails(pb);
		} else {
			return null;
		}
	}

	public String getName() {
		return cs.getChildText("name");
	}

	public String getWorkspaceName() {
		return cs.getChild("workspace").getChildText("name");
	}

	public String getNativeCRS() {
		return cs.getChild("nativeBoundingBox").getChildText("crs");
	}

	public String getDeclaredSRS() {
		return cs.getChildText("srs");
	}

	public BoundingBox getLatLonBoundingBox() {
		return (BoundingBox) latLonBoundingBox.clone();
	}

	public BoundingBox getNativeBoundingBox() {
		return (BoundingBox) nativeBoundingBox.clone();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder(getClass().getSimpleName())
				.append('[');
		if (cs == null)
			sb.append("null");
		else
			sb.append("name:").append(getName()).append(" wsname:")
					.append(getWorkspaceName());

		return sb.toString();
	}
}
