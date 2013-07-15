/*
 * RestTreeFolderController.java
 * 
 * Copyright (C) 2013
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
package com.emergya.persistenceGeo.web;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.emergya.persistenceGeo.dto.FolderDto;
import com.emergya.persistenceGeo.dto.FolderTypeDto;
import com.emergya.persistenceGeo.dto.TreeFolderDto;
import com.emergya.persistenceGeo.dto.Treeable;
import com.emergya.persistenceGeo.service.FoldersAdminService;
import com.emergya.persistenceGeo.utils.FolderStyle;
import com.emergya.persistenceGeo.utils.FoldersUtils;

/**
 * Rest controller to show trees with folders
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
@Controller
public class RestTreeFolderController extends RestPersistenceGeoController
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5233652723673416229L;

	/** Log */
	private static final Log LOG = LogFactory
			.getLog(RestTreeFolderController.class);
	@Resource
	private FoldersAdminService foldersAdminService;

	@Resource
	private RestFoldersAdminController restFoldersAdminController;

	// TODO: Those constants should be defined in a more concrete implementation
	// of the tree service.
	private static final String SHOW_UNASSIGNED_FOLDER_FILTER = "SHOW_UNASSIGNED_FOLDER";

	/**
	 * Returns the node types
	 * 
	 * @param parentType
	 *            The condition node type the returned nodes has to meet
	 * 
	 * @return JSON file with success
	 */
	@RequestMapping(value = "/persistenceGeo/tree/getNodeTypes/{parentType}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody
	List<FolderTypeDto> getNodeTypes(@PathVariable String parentType) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<FolderTypeDto> iptFolderTypes = new LinkedList<FolderTypeDto>();
		try {
			Long parentId = null;
			if (parentType != null && StringUtils.isNumeric(parentType)) {
				parentId = Long.decode(parentType);
			}
			iptFolderTypes = foldersAdminService.getFolderTypes(parentId);
		} catch (Exception e) {
			LOG.error(e);
			result.put(SUCCESS, false);
		}
		return iptFolderTypes;
	}

	/**
	 * Returns the children of a specific container node. The container type is
	 * specified using the type parameter. The condition the returned nodes has
	 * to meet is specified using the filter parameter.
	 * 
	 * @param node
	 *            The node id
	 * @param type
	 *            The type of the node (zone, folderType(Long), folder)
	 * @param filter
	 *            The condition the returned nodes has to meet
	 * 
	 * @return JSON node children of selected node
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/persistenceGeo/tree/treeService", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody
	List<Treeable> treeService(
			@RequestParam(value = "node", required = false) String nodeId,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "filter", required = false) String filter) {

		List<Treeable> nodes = new LinkedList<Treeable>();

		try {

			Long typeId = FoldersAdminService.DEFAULT_FOLDER_TYPE;
			if (!StringUtils.isEmpty(type) && StringUtils.isNumeric(type)) {
				typeId = Long.decode(type);
			}

			if (!StringUtils.isEmpty(nodeId)
					&& (StringUtils.isNumeric(nodeId) || RestFoldersAdminController.UNASSIGNED_LAYERS_VIRTUAL_FOLDER_ID
							.toString().equals(typeId))) {
				// The rest of types are consider like folders
				nodes = (List<Treeable>) restFoldersAdminController
						.loadFoldersById(nodeId, filter).get(ROOT);
			} else {
				// get root folder types
				nodes.addAll(getFoldersByType(typeId, filter));
			}

		} catch (Exception e) {
			LOG.error(e);
		}

		return nodes;
	}

	/**
	 * Returns the children of a specific container node. The container type is
	 * specified using the type parameter. The condition the returned nodes has
	 * to meet is specified using the filter parameter.
	 * 
	 * @param node
	 *            The node id
	 * @param type
	 *            The type of the node (zone, folder)
	 * @param filter
	 *            The condition the returned nodes has to meet
	 * 
	 * @return JSON file with success
	 */
	@RequestMapping(value = "/persistenceGeo/tree/treeServiceMap", produces = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody
	Map<String, Object> treeServiceMap(
			@RequestParam(value = "node", required = false) String nodeId,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "filter", required = false) String filter) {

		Map<String, Object> result = new HashMap<String, Object>();
		List<Treeable> nodes = new LinkedList<Treeable>();

		try {
			nodes = treeService(nodeId, type, filter);
			result.put(SUCCESS, true);
		} catch (Exception e) {
			LOG.error(e);
			result.put(SUCCESS, false);
		}

		result.put(RESULTS, nodes != null ? nodes.size() : 0);
		result.put(ROOT, nodes != null ? nodes : ListUtils.EMPTY_LIST);

		return result;
	}

	/**
	 * Obtain folders by zone
	 * 
	 * @param zoneId
	 *            zone id
	 * @param folderType
	 *            to obtain
	 * 
	 * @return all folders of the zone
	 */
	protected List<TreeFolderDto> getFoldersByZone(Long zoneId, Long folderType) {
		List<FolderDto> serviceFolders = foldersAdminService.getChannelFolders(
				zoneId == null ? null : Boolean.FALSE, zoneId, Boolean.TRUE,
				folderType);
		List<TreeFolderDto> folders = getFolderDecoration(serviceFolders, true);
		return folders;
	}

	/**
	 * Obtain folders by type
	 * 
	 * @param typeId
	 *            folder type
	 * @param filter
	 *            to decorate the result
	 * 
	 * @return all folders of the type
	 */
	protected List<TreeFolderDto> getFoldersByType(Long typeId, String filter) {
		return getFoldersByType(
				typeId,
				filter,
				filter != null
						&& filter
								.contains(RestFoldersAdminController.SHOW_FOLDER_LAYERS));
	}

	/**
	 * Obtain folders by type
	 * 
	 * @param typeId
	 *            folder type
	 * @param filter
	 *            to decorate the result
	 * @param showLayers
	 *            flag to show layers in tree
	 * 
	 * @return all folders of the type
	 */
	protected List<TreeFolderDto> getFoldersByType(Long typeId, String filter,
			boolean showLayers) {
		List<FolderDto> serviceFolders = foldersAdminService
				.rootFoldersByType(typeId);
		List<TreeFolderDto> folders = getFolderDecoration(serviceFolders,
				showLayers);

		if (filter != null && filter.contains(SHOW_UNASSIGNED_FOLDER_FILTER)) {

			FolderDto unassingedLayersFolder = new FolderDto();
			unassingedLayersFolder
					.setId(RestFoldersAdminController.UNASSIGNED_LAYERS_VIRTUAL_FOLDER_ID);
			unassingedLayersFolder.setName("Otros");

			folders.add(new TreeFolderDto(unassingedLayersFolder));
			Collections.sort(folders);
		}

		return folders;
	}

	/**
	 * Obtain a folder list decorated with filter
	 * 
	 * @param serviceFolders
	 *            folders to decorate
	 * @param showLayers
	 *            flag to show layers in tree
	 * 
	 * @return folders decorated
	 */
	protected List<TreeFolderDto> getFolderDecoration(
			List<FolderDto> serviceFolders, boolean showLayers) {
		List<TreeFolderDto> folders = new LinkedList<TreeFolderDto>();
		for (FolderDto subRes : serviceFolders) {
			TreeFolderDto folder = (TreeFolderDto) FoldersUtils
					.getFolderDecorator()
					.applyStyle(subRes, FolderStyle.NORMAL);
			if (showLayers) {
				folder.setLeaf(false);
			} else {
				folder.setLeaf(true);
			}
			folders.add(folder);
		}
		return folders;
	}
}
