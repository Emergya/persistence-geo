/* 
 * LayerAdminServiceImplTest.java
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
package com.emergya.persistenceGeo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;

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
import com.emergya.persistenceGeo.dto.UserDto;
import com.emergya.persistenceGeo.service.LayerAdminService;
import com.emergya.persistenceGeo.service.UserAdminService;


/**
 * Test para LayerAdminService
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

	@Resource
	protected Properties testProperties;
	
	protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");

	// Propiedades a ser utilizada en los test procedentes de testProperties: testCreateUser
	protected static final String PR_1_PARAM_1 = "pr1.username";
	
	@Resource
	private LayerAdminService layerAdminService;
	
	@Resource
	private UserAdminService userAdminService;
	
	
	@Test
	public void testCreateFolder() {
		try{
			FolderDto folder = new FolderDto();
			folder.setName("test");
			folder.setEnabled(true);
			folder = layerAdminService.saveFolder(folder);
			Assert.assertNotNull(folder);
			Assert.assertNotNull(folder.getId());
			Assert.assertEquals(folder.getName(), "test");
		}catch (Exception e){
			LOG.error(e);
			Assert.fail();
		}
	}
	
	@Test
	public void testCreateFolderInFolder() {
		try{
			FolderDto folder = new FolderDto();
			folder.setName("test");
			folder.setEnabled(true);
			folder = layerAdminService.saveFolder(folder);
			Assert.assertNotNull(folder);
			Assert.assertNotNull(folder.getId());
			Assert.assertEquals(folder.getName(), "test");
			FolderDto child = new FolderDto();
			child.setName("test_child");
			child.setEnabled(true);
			child.setIdParent(folder.getId());
			child = layerAdminService.saveFolder(child);
			Assert.assertNotNull(child);
			Assert.assertNotNull(child.getId());
			Assert.assertEquals(child.getName(), "test_child");
			Assert.assertEquals(folder.getId(), child.getIdParent());
		}catch (Exception e){
			LOG.error(e);
			Assert.fail();
		}
	}
	
	@Test
	public void testGetFolderChildren() {
		try{
			UserDto user = createUser();
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getId());
			Long idUser = user.getId();
			FolderDto rootFolder = new FolderDto();
			rootFolder.setName("root test");
			rootFolder.setEnabled(true);
			rootFolder.setIdUser(idUser);
			rootFolder = layerAdminService.saveFolder(rootFolder);
			Assert.assertNotNull(rootFolder);
			Assert.assertNotNull(rootFolder.getId());
			Assert.assertEquals(rootFolder.getName(), "root test");
			rootFolder = layerAdminService.getRootFolder(idUser);
			Assert.assertNotNull(rootFolder);
			Assert.assertNotNull(rootFolder.getId());
			Assert.assertEquals(rootFolder.getName(), "root test");
			Assert.assertEquals(rootFolder.getIdUser(), idUser);
			FolderDto folder = new FolderDto();
			folder.setName("test");
			folder.setEnabled(true);
			folder.setIdParent(rootFolder.getId());
			folder = layerAdminService.saveFolder(folder);
			Assert.assertNotNull(folder);
			Assert.assertNotNull(folder.getId());
			Assert.assertEquals(folder.getName(), "test");
			FolderDto child = new FolderDto();
			child.setName("test_child");
			child.setEnabled(true);
			child.setIdParent(rootFolder.getId());
			child = layerAdminService.saveFolder(child);
			Assert.assertNotNull(child);
			Assert.assertNotNull(child.getId());
			Assert.assertEquals(child.getName(), "test_child");
			Assert.assertEquals(rootFolder.getId(), child.getIdParent());
			rootFolder = layerAdminService.getRootFolder(idUser);
			Assert.assertNotNull(rootFolder);
			Assert.assertNotNull(rootFolder.getFolderList());
			Assert.assertEquals(rootFolder.getFolderList().size(), 2);
		}catch (Exception e){
			LOG.error(e);
			Assert.fail();
		}
	}
	
	public UserDto createUser() {
		try {
			UserDto usuario = userAdminService.obtenerUsuario(testProperties.getProperty(PR_1_PARAM_1), testProperties.getProperty(PR_1_PARAM_1));
			Assert.assertEquals(testProperties.getProperty(PR_1_PARAM_1), usuario.getUsername());
			return usuario;
		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
		return null;
	}

}
