/*
 * GeoserverUtils.java
 * 
 * Copyright (C) 2013
 * 
 * This file is part of persistence-geo-geoserver
 * 
 * This software is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 * As a special exception, if you link this library with other files to produce
 * an executable, this library does not by itself cause the resulting executable
 * to be covered by the GNU General Public License. This exception does not
 * however invalidate any other reasons why the executable file might be covered
 * by the GNU General Public License.
 * 
 * Authors:: Juan Luis Rodríguez Ponce (mailto:jlrodriguez@emergya.com)
 */
package com.emergya.persistenceGeo.utils;

import com.google.common.base.Strings;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:jlrodriguez@emergya.com">jlrodriguez</a>
 * 
 */
public class GeoserverUtils {

	/**
	 * Sanitizes a string so its content contains characters that forms a 
	 * good Geoserver name. This means the resulting string will not contain
	 * non-ascii characters nor whitespaces and if it starts with a number,
	 * a "_" will be prefixed.
	 * 
	 * @param text The string in which the returned name will be based.
     * @param maxchars The max number of chars of the returned string.
	 * @return
	 */
	public static String createName(String text, int maxchars) {
		if(StringUtils.isEmpty(text)) {
			throw new IllegalArgumentException("A geoserver name cannot be empty!");
		}
        
		String name = text.replaceAll("[\\W ]", "_");
		
		if(StringUtils.isNumeric(name.substring(0, 1))) {
			// First char is a number.
			name = "_"+name;
		}
		
		// Fix for #83218, geoserver don't like names too long.
		if(maxchars > 0 && name.length() > maxchars) {
			name = name.substring(0, maxchars);
		}
		
		return name.toLowerCase();
	}
    
	/**
	 * Sanitizes a string so its content contains characters that forms a 
	 * good Geoserver name. This means the resulting string will not contain
	 * non-ascii characters nor whitespaces and if it starts with a number,
	 * a "_" will be prefixed.
	 * @param text
	 * @return
	 */
	public static String createName(String text) {
	    return createName(text, -1);
	}
	
	/**
	 * Creates an valid unique Geoserver name by sanitizing the received text and removing 
	 * non-ascii characters and whitespaces and ensuring that the resulting name doesn't start with
	 * a number.
	 * 
	 * Also, it appends a randomized suffix (created from an UUID).
	 * 
	 * NOTE: THE CLIENT CODE MUST CHECK IF THE GENERATED NAME IS ACTUALLY UNIQUE!
	 * 
	 * @param text
	 * @return
	 */
	public static String createUniqueName(String text) {
		UUID uuid = UUID.randomUUID();
		return createName(text, 20)+"_"+uuid.toString().replaceAll("-", "_");
	}
	
	/**
	 * Sanitizes columns name for use with geoserver.
	 * @param columnName
	 * @return
	 */
	public static String sanitizeColumnName(String columnName) {
        if(Strings.isNullOrEmpty(columnName)) {
            return columnName;
        }
        
		String sanitizedName =  columnName.replaceAll("[^\\p{L}\\p{N}]", "_");
		if(Character.isDigit(sanitizedName.charAt(0))) {
			sanitizedName = "_"+sanitizedName;
		}
		
		return sanitizedName;
	}
}
