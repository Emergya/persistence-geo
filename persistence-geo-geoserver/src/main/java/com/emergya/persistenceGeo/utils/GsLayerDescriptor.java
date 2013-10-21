/* GsLayerDescriptor.java
 * 
 * Copyright (C) 2012
 * 
 * This file is part of project persistence-geo-core
 * 
 * This software is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * As a special exception, if you link this library with other files to
 * produce an executable, this library does not by itself cause the
 * resulting executable to be covered by the GNU General Public License.
 * This exception does not however invalidate any other reasons why the
 * executable file might be covered by the GNU General Public License.
 * 
 * Authors:: Juan Luis Rodríguez Ponce (mailto:jlrodriguez@emergya.com)
 */
package com.emergya.persistenceGeo.utils;

import java.io.Serializable;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public class GsLayerDescriptor implements Serializable {

	private static final long serialVersionUID = -92357716567203712L;
	private static final String DEFAULT_STYLE_POINT = "point";
	private static final String DEFAULT_STYLE_LINE = "line";
	private static final String DEFAULT_STYLE_POLYGON = "polygon";
	private static final String DEFAULT_WMS_PATH = "/geoserver/wms";

	

	private String wmsPath;
	private String defaultStyle;
	private GeometryType type;

	public GsLayerDescriptor() {
		this.wmsPath = DEFAULT_WMS_PATH;
	}

	/**
	 * @return the wmsPath
	 */
	public String getWmsPath() {
		return wmsPath;
	}

	/**
	 * @param wmsPath
	 *            the wmsPath to set
	 */
	public void setWmsPath(String wmsPath) {
		this.wmsPath = wmsPath;
	}

	/**
	 * @return the defaultStyle. If not defaultStyle is defined and geometry
	 *         type is known returns a default style for the geometry type.
	 */
	public String getDefaultStyle() {
		String result = defaultStyle;
		if ((defaultStyle == null || defaultStyle.isEmpty()) && type != null) {
			switch (type) {
			case POINT:
				result = DEFAULT_STYLE_POINT;
				break;
			case LINESTRING:
				result = DEFAULT_STYLE_LINE;
				break;
			case POLYGON:
				result = DEFAULT_STYLE_POLYGON;
			default:
				break;
			}
		}

		return result;
	}

	/**
	 * @param defaultStyle
	 *            the defaultStyle to set
	 */
	public void setDefaultStyle(String defaultStyle) {
		this.defaultStyle = defaultStyle;
	}

	public GeometryType getType() {
		return type;
	}

	public void setType(GeometryType type) {
		this.type = type;
	}

}
