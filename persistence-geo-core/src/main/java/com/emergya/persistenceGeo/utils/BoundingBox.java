/* BoundingBox.java
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
public class BoundingBox implements Serializable, Cloneable {

	private static final long serialVersionUID = 3521025070789082678L;
	private double minx;
	private double miny;
	private double maxx;
	private double maxy;
	private String srs;

	/**
	 * @return the minx
	 */
	public double getMinx() {
		return minx;
	}

	/**
	 * @param minx
	 *            the minx to set
	 */
	public void setMinx(double minx) {
		this.minx = minx;
	}

	/**
	 * @return the miny
	 */
	public double getMiny() {
		return miny;
	}

	/**
	 * @param miny
	 *            the miny to set
	 */
	public void setMiny(double miny) {
		this.miny = miny;
	}

	/**
	 * @return the maxx
	 */
	public double getMaxx() {
		return maxx;
	}

	/**
	 * @param maxx
	 *            the maxx to set
	 */
	public void setMaxx(double maxx) {
		this.maxx = maxx;
	}

	/**
	 * @return the maxy
	 */
	public double getMaxy() {
		return maxy;
	}

	/**
	 * @param maxy
	 *            the maxy to set
	 */
	public void setMaxy(double maxy) {
		this.maxy = maxy;
	}

	/**
	 * @return the srs
	 */
	public String getSrs() {
		return srs;
	}

	/**
	 * @param srs
	 *            the srs to set
	 */
	public void setSrs(String srs) {
		this.srs = srs;
	}

	@Override
	public Object clone() {
	    	BoundingBox result = new BoundingBox();
		result.setMaxx(maxx);
		result.setMinx(minx);
		result.setMaxy(maxy);
		result.setMiny(miny);
		result.setSrs(srs);
		return result;
	}

}
