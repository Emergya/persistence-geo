package com.emergya.persistenceGeo.utils;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.emergya.persistenceGeo.dto.FolderDto;

public class FoldersUtils {

	private static final String TREE_LEVEL = "-";
	
	/**
	 * Recursive tree build
	 * 
	 * @param folder parent at tree
	 * @param tree building
	 * @param level level on tree
	 */
	public static void getFolderTree(FolderDto folder, List<FolderDto> tree, String parent){
		if(folder != null){
			String name = getFolderName(folder.getName(), parent);
			folder.setName(name);
			tree.add(folder);
			if(folder.getFolderList() != null){
				String previus = parent;
				parent = name;
				for(FolderDto subFolder: folder.getFolderList()){
					getFolderTree(subFolder, tree, parent);
				}
				parent = previus;
			}
		}
	}

	/**
	 * Build folder name on tree
	 * 
	 * @param name default name on tree
	 * @param parent
	 * 
	 * @return tree folder '' 
	 */
	public static String getFolderName(String name, String parent) {
		String result;
		if(!StringUtils.isEmpty(parent)){
			result = parent + TREE_LEVEL + name;
		}else{
			result = name;
		}
		return result;
	}
}
