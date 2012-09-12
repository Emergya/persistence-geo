/*
 * UserAdminService.java
 * 
 * Copyright (C) 2012
 * 
 * This file is part of Proyecto persistenceGeo
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
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.service;

import com.emergya.persistenceGeo.dto.RuleDto;
import com.emergya.persistenceGeo.dto.StyleDto;

/**
 * Admin interface for styles management 
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public interface StyleAdminService {
	
	/**
	 * Create a new style
	 * 
	 * @param style
	 * 
	 * @return style created
	 */
	public StyleDto createStyle(StyleDto style);
	
	/**
	 * Modify a style
	 * 
	 * @param style
	 */
	public void modifyStyle(StyleDto style);
	
	/**
	 * Adds a new rule to a style
	 * 
	 * @param rule
	 * @param idStyle
	 * 
	 * @return syle modified
	 */
	public StyleDto addRuleToStyle(RuleDto rule, Long idStyle);
	
	/**
	 * Removes a rule to a style
	 * 
	 * @param idRule
	 * @param idStyle
	 * 
	 * @return syle modified
	 */
	public StyleDto removeRuleFromStyle(Long idRule, Long idStyle);
	
	/**
	 * Modify a rule 
	 * 
	 * @param rule to modify
	 */
	public void modifyRule(RuleDto rule);
	
	/**
	 * Adds the association
	 * 
	 * @param idStyle
	 * @param idLayer
	 */
	public void linkStyleToLayer(Long idStyle, Long idLayer);
	
	/**
	 * Remove the association
	 * 
	 * @param idStyle
	 * @param idLayer
	 */
	public void unlinkStyleToLayer(Long idStyle, Long idLayer);

}
