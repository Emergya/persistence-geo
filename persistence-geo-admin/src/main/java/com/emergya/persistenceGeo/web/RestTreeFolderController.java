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
import com.emergya.persistenceGeo.dto.TreeNode;
import com.emergya.persistenceGeo.dto.Treeable;
import com.emergya.persistenceGeo.service.FoldersAdminService;

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
	private static final String NODE_TYPE_ZONE = "zone";
	private static final String NODE_TYPE_CHANNELS_ROOT = "channelsRoot";
	private static final String NODE_TYPE_CHANNELS_BY_ZONE = "zonesRoot";
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
			if (!StringUtils.isEmpty(type) && StringUtils.isNumeric(type)) {
				// is a folder type request
				List<FolderDto> folders = foldersAdminService
						.findFoldersByType(Long.decode(type));
				if (!folders.isEmpty()) {
					for (FolderDto folder : folders) {
						nodes.add((Treeable) new TreeNode(folder, false));
					}
				}
			} else if (NODE_TYPE_ZONE.equals(type)) {

				nodes = (List<Treeable>) restFoldersAdminController
						.loadFoldersByZone(nodeId, null).get(ROOT);

			} else if (NODE_TYPE_CHANNELS_BY_ZONE.equals(type)
					|| NODE_TYPE_CHANNELS_ROOT.equals(type)) {

				Map<String, Object> result = restFoldersAdminController
						.loadChannels(filter);

				if (((Boolean) result.get(SUCCESS)) && filter != null
						&& filter.contains(SHOW_UNASSIGNED_FOLDER_FILTER)) {

					List<TreeFolderDto> folders = (List<TreeFolderDto>) result
							.get(ROOT);

					FolderDto unassingedLayersFolder = new FolderDto();
					unassingedLayersFolder
							.setId(RestFoldersAdminController.UNASSIGNED_LAYERS_VIRTUAL_FOLDER_ID);
					unassingedLayersFolder.setName("Otros");

					folders.add(new TreeFolderDto(unassingedLayersFolder));
					Collections.sort(folders);
					nodes.addAll(folders);
				}else{
					nodes = (List<Treeable>) result.get(ROOT);
				}
			} else {

				// The rest of types are consider like folders
				nodes = (List<Treeable>) restFoldersAdminController
						.loadFoldersById(nodeId, filter).get(ROOT);
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
}
