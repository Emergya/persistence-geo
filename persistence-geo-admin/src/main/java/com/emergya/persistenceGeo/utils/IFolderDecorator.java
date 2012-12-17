/*
 * IFolderDecorator.java
 * 
 * Copyright (C) 2012
 * 
 * This file is part of persistenceGeo project
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
package com.emergya.persistenceGeo.utils;

import java.io.Serializable;
import java.util.List;

import com.emergya.persistenceGeo.dto.FolderDto;

/**
 * Folder decorator to generate tree
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
public interface IFolderDecorator {
	
	/**
	 * Decorate a folder with a FolderStyle
	 * @param toApply
	 * @param parent
	 * @return FolderDto decorated
	 */
	public FolderDto applyStyle(FolderDto toApply, String parent);
	
	/**
	 * Decorate a folder with a FolderStyle
	 * @param toApply
	 * @param parent
	 * @param style
	 * @return FolderDto decorated
	 */
	public FolderDto applyStyle(FolderDto toApply, String parent, FolderStyle style);
	
	/**
	 * Decorate a folder with a FolderStyle
	 * @param toApply
	 * @param parent
	 * @param style
	 * @param level
	 * @return FolderDto decorated
	 */
	public FolderDto applyStyle(FolderDto toApply, String parent, FolderStyle style, Integer level);
	
	/**
	 * Decorate root folder and root folder children with a FolderStyle
	 * 
	 * @param rootFolder
	 * @param tree
	 * @param parent
	 * @param style
	 * @param level
	 */
	public void applyStyle(FolderDto rootFolder, List<? extends Serializable> tree, String parent, FolderStyle style, Integer level);
	
	/**
	 * Decorate a folder with default FolderStyle
	 * 
	 * @param toApply
	 * 
	 * @return FolderDto decorated
	 */
	public FolderDto applyStyle(FolderDto toApply);
	
	/**
	 * Decorate a folder with a FolderStyle
	 * 
	 * @param toApply
	 * @param style
	 * 
	 * @return FolderDto decorated
	 */
	public FolderDto applyStyle(FolderDto toApply, FolderStyle style);

}
