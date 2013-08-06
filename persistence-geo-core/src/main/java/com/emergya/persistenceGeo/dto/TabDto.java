/*
 * TabDto.java. Copyright (C) 2013. This file is part of Proyecto persistenceGeo
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
package com.emergya.persistenceGeo.dto;

import java.io.Serializable;

/**
 * Tab DTO for tabs and subtabs
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
public class TabDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1300637121255364735L;
	
	private Long id;
	private Long parentId;
	private String text;
	private String target;
	private String [] neededAuths;
	private String cls;

	/**
	 * Constructor with parameters
	 * 
	 * @param id identifier of the tab
	 * @param parentId parent tab id or null if it's a root tab
	 * @param text message to show in the tab
	 * @param target link for the tab
	 * @param neededAuths authorities needed to show the tab
	 */
	public TabDto(Long id, Long parentId, String text, String target, String [] neededAuths, String cls) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.text = text;
		this.target = target;
		this.neededAuths = neededAuths;
		this.cls = cls;
	}

	/**
	 * Constructor with parameters
	 * 
	 * @param parentId parent tab id or null if it's a root tab
	 * @param text message to show in the tab
	 * @param target link for the tab
	 * @param neededAuths authorities needed to show the tab
	 */
	public TabDto(Long parentId, String text, String target, String [] neededAuths, String cls) {
		super();
		this.parentId = parentId;
		this.text = text;
		this.target = target;
		this.neededAuths = neededAuths;
		this.cls = cls;
	}

	/**
	 * @return the cls
	 */
	public String getCls() {
		return cls;
	}

	/**
	 * @param cls the cls to set
	 */
	public void setCls(String cls) {
		this.cls = cls;
	}

	/**
	 * Constructor without parameter
	 */
	public TabDto() {
		super();
	}



	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the target
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * @param target the target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * @return the neededAuths
	 */
	public String[] getNeededAuths() {
		return neededAuths;
	}

	/**
	 * @return the neededAuths
	 */
	public String getAuthRoles() {
		String ret = "";
		if(neededAuths != null){
			for(String st: neededAuths){
				ret += st + ",";
			}
			ret = ret.substring(0, ret.length()-1); // remove last ','
		}
		return ret;
	}

	/**
	 * @param neededAuths the neededAuths to set
	 */
	public void setNeededAuths(String[] neededAuths) {
		this.neededAuths = neededAuths;
	}

}
