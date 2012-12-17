/* 
 * FoldersTest.java
 * 
 * Copyright (C) 2012
 * 
 * This file is part of Proyecto persistenceGeo
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
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dto.FolderDto;
import com.emergya.persistenceGeo.service.FoldersAdminService;
import com.emergya.persistenceGeo.utils.FolderStyle;
import com.emergya.persistenceGeo.utils.FoldersUtils;


/**
 * Test for Folders admin
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:modelContext.xml"})
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class FoldersTest{

	private static final Log LOG = LogFactory.getLog(FoldersTest.class);
	
	static Map<Long, FolderDto> FOLDERS;
	static final Long ROOT_FOLDER = new Long(1);
	static Map<Long, FolderDto> LAYERS;
	
	static{
		FOLDERS = new ConcurrentHashMap<Long, FolderDto>();
		FolderDto rootFolder = new FolderDto();
		rootFolder.setId(ROOT_FOLDER);
		rootFolder.setName("ROOT");
		List<FolderDto> children = new LinkedList<FolderDto>();
		FolderDto child1 = new FolderDto();
		child1.setId(new Long(2));
		child1.setName("child1");;
		child1.setIdParent(ROOT_FOLDER);
		children.add(child1);
		FOLDERS.put(new Long(2), child1);
		FolderDto child2 = new FolderDto();
		child2.setId(new Long(3));
		child2.setName("child2");;
		child2.setIdParent(ROOT_FOLDER);
		children.add(child2);
		FolderDto child3 = new FolderDto();
		child3.setId(new Long(4));
		child3.setName("child3");;
		child3.setIdParent(new Long(3));
		List<FolderDto> children2 = new LinkedList<FolderDto>();
		children2.add(child3);
		child2.setFolderList(children2);
		FOLDERS.put(new Long(3), child2);
		FOLDERS.put(new Long(4), child3);
		rootFolder.setFolderList(children);
		FOLDERS.put(ROOT_FOLDER, rootFolder);
	}
	
	@Resource
	private FoldersAdminService foldersAdminService;
	
	@Test
	public void testCreateFolder() {
		try{
			List<FolderDto> folders = new LinkedList<FolderDto>();
			FolderDto rootFolder = foldersAdminService.getRootFolder(new Long(1));
			FoldersUtils.getFolderTree(rootFolder, folders, new String(""), null);
			for(FolderDto folder: folders){
				LOG.debug(folder.getName());
			}
		}catch (Exception e){
			LOG.error(e);
			Assert.fail();
		}
	}
	
	@Test
	public void testCreateNormalFolder() {
		try{
			List<FolderDto> folders = new LinkedList<FolderDto>();
			FolderDto rootFolder = FOLDERS.get(ROOT_FOLDER);
			FoldersUtils.getFolderTree(rootFolder, folders, null, FolderStyle.NORMAL, null);
			for(FolderDto folder: folders){
				LOG.debug(folder.getName());
			}
			Assert.assertEquals(2, folders.size());
		}catch (Exception e){
			LOG.error(e);
			Assert.fail();
		}
	}
}
