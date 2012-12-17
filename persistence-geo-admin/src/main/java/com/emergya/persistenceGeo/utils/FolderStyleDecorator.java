/*
 * FolderStyleDecorator.java
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

import org.apache.commons.lang.StringUtils;

import com.emergya.persistenceGeo.dto.FolderDto;
import com.emergya.persistenceGeo.dto.TreeFolderDto;

/**
 * Folder decorator to generate tree
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
@SuppressWarnings("rawtypes")
public class FolderStyleDecorator implements IFolderDecorator {

	private static final String TREE_LEVEL = "-";
	
	private static final FolderStyle DEFAULT_STYLE = FolderStyle.TREE;

	public FolderDto applyStyle(FolderDto toApply) {
		return applyStyle(toApply, DEFAULT_STYLE);
	}

	public FolderDto applyStyle(FolderDto toApply, FolderStyle style) {	
		return applyStyle(toApply, null, style);
	}

	public FolderDto applyStyle(FolderDto toApply, String parent) {
		return applyStyle(toApply, null, DEFAULT_STYLE);
	}
	
	public FolderDto applyStyle(FolderDto toApply, String parent, FolderStyle style) {
		return applyStyle(toApply, parent, style, new Integer (0));
	}

	public FolderDto applyStyle(FolderDto toApply, String parent, FolderStyle style, Integer level) {
		FolderDto result = null;
		try {
			if(FolderStyle.NORMAL.equals(style)){
				result = new TreeFolderDto(toApply);
			}else{
				result = (FolderDto) toApply.clone();
				result.setId(toApply.getId());
				if(FolderStyle.STRING.equals(style)){
					result.setName(getFolderNameString(toApply.getName(), parent));
				}else {
					result.setName(getFolderNameTree(toApply.getName(), parent, level));
				}
			}
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * Build folder name on tree
	 * 
	 * @param name default name on tree
	 * @param parent
	 * 
	 * @return tree folder '' 
	 */
	private static String getFolderNameTree(String name, String parent, Integer level) {
		String result = name;
		for(int i = 0; i < level; i++){
			result = TREE_LEVEL + result;
		}
		result = result.replace(name, " " + name);
		return result;
	}	

	/**
	 * Build folder name on string
	 * 
	 * @param name default name on string
	 * @param parent
	 * 
	 * @return string folder 'parent-name' 
	 */
	private static String getFolderNameString (String name, String parent){
		String result;
		if(!StringUtils.isEmpty(parent)){
			result = parent + TREE_LEVEL + name;
		}else{
			result = name;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void applyStyle(FolderDto folder, List tree,
			String parent, FolderStyle style, Integer level) {
		if(folder != null){
			if(FolderStyle.NORMAL.equals(style)){
				if(folder.getFolderList() != null 
						&& folder.getFolderList().size() > 0){
					for(FolderDto child: folder.getFolderList()){
						tree.add(this.applyStyle(child, FolderStyle.NORMAL));
					}
				}
			}else{
				FolderDto folderMod = this.applyStyle(folder, parent, style, level);
				tree.add(folderMod);
				if(folderMod.getFolderList() != null){
					String previus = parent;
					parent = folderMod.getName();
					level++;
					for(FolderDto subFolder: folderMod.getFolderList()){
						this.applyStyle(subFolder, tree, parent, style, level);
					}
					level--;
					parent = previus;
				}
			}
		}
	}

}
