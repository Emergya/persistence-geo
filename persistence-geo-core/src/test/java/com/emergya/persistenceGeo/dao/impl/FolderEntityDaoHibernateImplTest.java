/*
 * FolderEntityDaoHibernateImplTest.java
 *
 * Copyright (C) 2011
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
 * Authors:: Antonio Hernández (mailto:ahernandez@emergya.com)
 */
package com.emergya.persistenceGeo.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.List;

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

import com.emergya.persistenceGeo.dao.FolderEntityDao;
import com.emergya.persistenceGeo.dao.ZoneEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;
import com.emergya.persistenceGeo.metaModel.AbstractZoneEntity;


/**
 * Test para FolderEntityDao
 *
 * @author <a href="mailto:ahernandez@emergya.com">ahernandez</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:modelContext.xml"})
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class FolderEntityDaoHibernateImplTest {

	private static final Log LOG = LogFactory.getLog(FolderEntityDaoHibernateImplTest.class);

	@Resource
	protected Properties testProperties;

	protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");

	// Propiedades a ser utilizada en los test procedentes de testProperties: testCreateFolder
	protected static final String PR_1_PARAM_1 = "pr1.foldername";
	protected static final String PR_1_PARAM_2 = "pr2.foldername";
	protected static final String PR_1_PARAM_3 = "pr3.foldername";

	@Resource
	private FolderEntityDao folderDao;
	@Resource
    private ZoneEntityDao zoneDao;

	@Test
	public void testCreateFolder() {
		try {
			String foldername = testProperties.getProperty(PR_1_PARAM_1) + "test";
			folderDao.createFolder(foldername);
			List<AbstractFolderEntity> folders = folderDao.getFolders(foldername);
			for (AbstractFolderEntity folderEntity: folders) {
			    Assert.assertEquals(foldername, folderEntity.getName());
            }
		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
	}

	@Test
	public void testFindFoldersInZone() {

		try {

            AbstractFolderEntity f;

            AbstractZoneEntity zone1 = zoneDao.createZone("zone1.test");
            zone1 = zoneDao.makePersistent(zone1);

            AbstractZoneEntity zone2 = zoneDao.createZone("zone2.test");
            zone2 = zoneDao.makePersistent(zone2);

			String foldername1 = testProperties.getProperty(PR_1_PARAM_1) + "test";
			f = folderDao.createFolder(foldername1);
            f.setZone(zone1);
            folderDao.makePersistent(f);

			String foldername2 = testProperties.getProperty(PR_1_PARAM_2) + "test";
            f = folderDao.createFolder(foldername2);
            f.setZone(zone1);
            folderDao.makePersistent(f);

			String foldername3 = testProperties.getProperty(PR_1_PARAM_3) + "test";
			f = folderDao.createFolder(foldername3);
            f.setZone(zone2);
            folderDao.makePersistent(f);

			List<AbstractFolderEntity> folders = folderDao.findByZone(zone1.getId());
			for (AbstractFolderEntity folderEntity: folders) {
			    Assert.assertEquals(zone1.getId(), folderEntity.getZone().getId());
            }

            Assert.assertEquals(folders.size(), 2);

		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
	}

	@Test
	public void testFindFoldersInZoneAndParent() {

		try {

            AbstractFolderEntity parent;
            AbstractFolderEntity f;

            AbstractZoneEntity zone1 = zoneDao.createZone("zone1.test");
            zone1 = zoneDao.makePersistent(zone1);

            AbstractZoneEntity zone2 = zoneDao.createZone("zone2.test");
            zone2 = zoneDao.makePersistent(zone2);

			String foldername1 = testProperties.getProperty(PR_1_PARAM_1) + "test";
			parent = folderDao.createFolder(foldername1);
            parent.setZone(zone1);
            parent = folderDao.makePersistent(parent);

			String foldername2 = testProperties.getProperty(PR_1_PARAM_2) + "test";
            f = folderDao.createFolder(foldername2);
            f.setZone(zone1);
            f.setParent(parent);
            folderDao.makePersistent(f);

			String foldername3 = testProperties.getProperty(PR_1_PARAM_3) + "test";
			f = folderDao.createFolder(foldername3);
            f.setZone(zone2);
            f.setParent(parent);
            folderDao.makePersistent(f);

			List<AbstractFolderEntity> folders = folderDao.findByZone(zone1.getId(), parent.getId());
			for (AbstractFolderEntity folderEntity: folders) {
			    Assert.assertEquals(zone1.getId(), folderEntity.getZone().getId());
			    Assert.assertEquals(parent.getId(), folderEntity.getParent().getId());
            }

            Assert.assertEquals(folders.size(), 1);

		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
	}

}
