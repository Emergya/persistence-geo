/* 
 * CreateLayersTest.java Copyright (C) 2012 This file is part of Proyecto persistenceGeo
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

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

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
import com.emergya.persistenceGeo.dto.LayerDto;
import com.emergya.persistenceGeo.dto.UserDto;
import com.emergya.persistenceGeo.service.FoldersAdminService;
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
public class CloneUserContextTest{

	private static final Log LOG = LogFactory.getLog(CloneUserContextTest.class);

	@Resource
	protected Properties testProperties;
	
	protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");
	
	@Resource
	private LayerAdminService layerAdminService;
	
	@Resource
	private FoldersAdminService foldersAdminService;
	
	@Resource
	private UserAdminService userAdminService;

	protected static final String PR_2_LAYER_NAME = "tmpLayer" + new Random().nextInt();
	protected static final String PR_2_LAYER_NAME_UPDATE = "tmpLayer" + new Random().nextInt();
	protected static final String PR_2_STYLE_NAME = "testStyle";
	protected static final String PR_2_STYLE_PROPERTY_NAME = "testName";
	protected static final String PR_2_STYLE_PROPERTY_VALUE = "valueTest";
	protected static final String PR_2_LAYER_DATA = "target/classes/test-classes/ficheros/barcelona_city_drive_4326.kml";
	protected static final String PR_1_PARAM_1 = "pr1.username";
	
	@Test
	public void testCloneUserContextLayer() {
		try{
			UserDto user = createUser();
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getId());
			createFolder(user);
			UserDto userTarget = createUser();
			Assert.assertNotNull(userTarget);
			Assert.assertNotNull(userTarget.getId());
			Assert.assertFalse(userTarget.getId().equals(user.getId()));
			FolderDto originFolder = foldersAdminService.getRootFolder(user.getId());
			Assert.assertNotNull(originFolder.getFolderList());
			FolderDto rootFolder = foldersAdminService.getRootFolder(userTarget.getId());
			Assert.assertNull(rootFolder);
			Assert.assertNotNull(originFolder);
			rootFolder = foldersAdminService.copyUserContext(user.getId(), userTarget.getId(), false);
			AssertCloned(originFolder, rootFolder);
		}catch (Exception e){
			e.printStackTrace();
			LOG.error(e);
			Assert.fail();
		}
	}
	
	@Test
	public void testCloneUserContextLayerNotEmpty() {
		try{
			UserDto user = createUser();
			Assert.assertNotNull(user);
			Assert.assertNotNull(user.getId());
			createFolder(user);
			UserDto userTarget = createUser();
			Assert.assertNotNull(userTarget);
			Assert.assertNotNull(userTarget.getId());
			Assert.assertFalse(userTarget.getId().equals(user.getId()));
			createFolder(userTarget);
			FolderDto originFolder = foldersAdminService.getRootFolder(user.getId());
			Assert.assertNotNull(originFolder.getFolderList());
			FolderDto rootFolder = foldersAdminService.getRootFolder(userTarget.getId());
			Assert.assertNotNull(rootFolder);
			Assert.assertNotNull(originFolder);
			rootFolder = foldersAdminService.copyUserContext(user.getId(), userTarget.getId(), false);
			AssertCloned(originFolder, rootFolder);
		}catch (Exception e){
			e.printStackTrace();
			LOG.error(e);
			Assert.fail();
		}
	}
	
	private void AssertCloned(FolderDto originFolder, FolderDto folderCloned){
		// id not equals and folder size
		Assert.assertNotNull(folderCloned);
		Assert.assertNotNull(folderCloned.getId());
		Assert.assertFalse(folderCloned.getId().equals(originFolder.getId()));
		
		// layers
		List<LayerDto> layers = layerAdminService.getLayersByFolder(originFolder.getId());
		if(layers != null){
			List<LayerDto> layersCloned = layerAdminService.getLayersByFolder(folderCloned.getId());
			Assert.assertEquals(layers.size(), layersCloned.size());
			for(LayerDto layer: layers){
				AssertClonedIn(layer, layersCloned);
			}
		}
		
		// has children?
		if(folderCloned.getFolderList() != null){
			Assert.assertNotNull(originFolder.getFolderList());
			Assert.assertEquals(folderCloned.getFolderList().size(), originFolder.getFolderList().size());
			// children
			for(FolderDto child: originFolder.getFolderList()){
				AssertClonedInChild(child, folderCloned);
			}
		}else{
			// without children
			Assert.assertNull(originFolder.getFolderList());
		}
	}

	private void AssertClonedInChild(FolderDto child, FolderDto folderCloned){
		boolean found = false;
		for(FolderDto childCloned: folderCloned.getFolderList()){
			if(child.getName().equals(childCloned.getName())){
				if(child.getId().equals(childCloned.getId())){
					Assert.fail("Child " + child.getName() + " not cloned!!");
				}else{
					AssertCloned(child, childCloned);
					found = true;
					break;
				}
			}
		}
		Assert.assertTrue(found);
	}
	
	private void AssertClonedIn(LayerDto layer, List<LayerDto> layersCloned) {
		boolean found = false;
		for(LayerDto layerCloned: layersCloned){
			if(layer.getName().equals(layerCloned.getName())){
				if(layer.getId().equals(layerCloned.getId())){
					Assert.fail("Layer " + layer.getName() + " not cloned!!");
				}else{
					found = true;
					break;
				}
			}
		}
		Assert.assertTrue(found);
	}

	public void createFolder(UserDto user) {
		try{
			Long idUser = user.getId();
			FolderDto rootFolder = new FolderDto();
			rootFolder.setName(nextRandomName("root test"));
			rootFolder.setEnabled(true);
			rootFolder.setIdUser(idUser);
			rootFolder = foldersAdminService.saveFolder(rootFolder);
			Assert.assertNotNull(rootFolder);
			Assert.assertNotNull(rootFolder.getId());
			Assert.assertEquals(rootFolder.getName(), actualRandomName("root test"));
			rootFolder = foldersAdminService.getRootFolder(idUser);
			Assert.assertNotNull(rootFolder);
			Assert.assertNotNull(rootFolder.getId());
			Assert.assertEquals(rootFolder.getName(), actualRandomName("root test"));
			Assert.assertEquals(rootFolder.getIdUser(), idUser);
			FolderDto folder = new FolderDto();
			folder.setName(nextRandomName("test"));
			folder.setEnabled(true);
			folder.setIdParent(rootFolder.getId());
			folder = foldersAdminService.saveFolder(folder);
			Assert.assertNotNull(folder);
			Assert.assertNotNull(folder.getId());
			Assert.assertEquals(folder.getName(), actualRandomName("test"));
			FolderDto child = new FolderDto();
			child.setName(nextRandomName("test_child"));
			child.setEnabled(true);
			child.setIdParent(rootFolder.getId());
			child = foldersAdminService.saveFolder(child);
			Assert.assertNotNull(child);
			Assert.assertNotNull(child.getId());
			Assert.assertEquals(child.getName(), actualRandomName("test_child"));
			Assert.assertEquals(rootFolder.getId(), child.getIdParent());
			rootFolder = foldersAdminService.getRootFolder(idUser);
			Assert.assertNotNull(rootFolder);
			Assert.assertNotNull(rootFolder.getFolderList());
			Assert.assertEquals(rootFolder.getFolderList().size(), 2);
			LayerDto layer = new LayerDto();
			layer.setName(nextRandomName(PR_2_LAYER_NAME));
			layer.setType(LayerAdminService.TYPE_KML);
			layer.setData(new File(PR_2_LAYER_DATA));
			layer.setType("KML");
			layer.setFolderId(child.getId());
			layer = (LayerDto) layerAdminService.create(layer);
			List<LayerDto> layers = layerAdminService.getLayersByName(actualRandomName(PR_2_LAYER_NAME));
			Assert.assertNotNull(layers);
			Assert.assertEquals(layers.size(), 1);
			Assert.assertEquals(layers.get(0).getId(), layer.getId());
		}catch (Exception e){
			LOG.error(e);
			Assert.fail();
		}
	}
	
	public UserDto createUser() {
		try {
			String nameUser = PR_1_PARAM_1 + random.nextInt();
			UserDto usuario = userAdminService.obtenerUsuario(nameUser, nameUser);
			Assert.assertEquals(nameUser, usuario.getUsername());
			return usuario;
		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
		return null;
	}
	
	private static Random random = new Random();
	private static Map<String, String> actualRandomNames = new HashMap<String, String>();
	
	private static String nextRandomName(String name){
		String value = name + random.nextInt();
		actualRandomNames.put(name, value);
		return value;
	}
	
	private static String actualRandomName(String name){
		return actualRandomNames.get(name);
	}

}
