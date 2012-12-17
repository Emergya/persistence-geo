/*
 * FoldersUtils.java
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

import java.util.List;

import com.emergya.persistenceGeo.dto.FolderDto;

/**
 * Utils class to parse folder names
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
@SuppressWarnings("rawtypes")
public class FoldersUtils {
	
	
	private static IFolderDecorator folderDecorator;
	static{
		folderDecorator = new FolderStyleDecorator();
	}
	
	/**
	 * @return default style decorator
	 */
	public static IFolderDecorator getFolderDecorator() {
		return folderDecorator;
	}
	
	/**
	 * Recursive tree build
	 * 
	 * @param folder parent at tree
	 * @param tree building
	 * @param style style to generate folder tree
	 * 
	 * @see FolderStyle
	 */
	public static void getFolderTree(FolderDto folder, List tree, FolderStyle style, Boolean onlyNotEmpty) {
		getFolderTree(folder, tree, new String(), new Integer (0), style, onlyNotEmpty);
	}
	
	/**
	 * Recursive tree build
	 * 
	 * @param folder parent at tree
	 * @param tree building
	 * @param style style to generate folder tree
	 * 
	 * @see FolderStyle
	 */
	public static void getFolderTree(FolderDto folder, List tree, String parent, FolderStyle style, Boolean onlyNotEmpty) {
		getFolderTree(folder, tree, parent, new Integer (0), style, onlyNotEmpty);
	}
	
	/**
	 * Recursive tree build
	 * 
	 * @param folder parent at tree
	 * @param tree building
	 * @param level level on tree
	 */
	@SuppressWarnings("unchecked")
	public static void getFolderTree(FolderDto folder, List tree, String parent, Integer level, FolderStyle style, Boolean canHaveLayers) {
		if(folder != null){
			if(canHaveLayers == null){
				folderDecorator.applyStyle(folder, tree, parent, style, level);
			}else{
				folder = folderDecorator.applyStyle(folder, parent, style, level);
				if(canHaveLayers && 
						(folder.getIsChannel() 
							|| (folder.getFolderList() == null
								|| folder.getFolderList().isEmpty()))){
					// only can have layers folders without children
					tree.add(folder);
				}else if(!canHaveLayers && !folder.getIsChannel()){
					// only can have folders not layer channels (without children and without layers)
					tree.add(folder);
				}

				if(folder.getFolderList() != null){
					String previus = parent;
					parent = folder.getName();
					level++;
					for(FolderDto subFolder: folder.getFolderList()){
						getFolderTree(subFolder, tree, parent, level, style, canHaveLayers);
					}
					level--;
					parent = previus;
				}
			}
		}
	}
	
	/**
	 * Recursive tree build
	 * 
	 * @param folder parent at tree
	 * @param tree building
	 * @param level level on tree
	 */
	public static void getFolderTree(FolderDto folder, List tree){
		getFolderTreeFiltered(folder, tree, null);
	}
	
	/**
	 * Recursive tree build
	 * 
	 * @param folder parent at tree
	 * @param tree building
	 * @param level level on tree
	 */
	public static void getFolderTreeFiltered(FolderDto folder, List tree, Boolean onlyNotEmpty){
		getFolderTree(folder, tree, new String(), onlyNotEmpty);
	}
	
	/**
	 * Recursive tree build
	 * 
	 * @param folder parent at tree
	 * @param tree building
	 * @param level level on tree
	 */
	public static void getFolderTree(FolderDto folder, List tree, String parent, Boolean onlyNotEmpty){
		getFolderTree(folder, tree, parent, FolderStyle.STRING, onlyNotEmpty);
	}
}