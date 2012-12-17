/*
 * ZoneFoldersRelationTest.java Copyright (C) 2012 This file is part of Proyecto persistenceGeo
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
package com.emergya.persistenceGeo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
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
import com.emergya.persistenceGeo.dto.ZoneDto;
import com.emergya.persistenceGeo.service.FoldersAdminService;
import com.emergya.persistenceGeo.service.ZoneAdminService;


/**
 * Test para ZoneAdminService y FoldersAdminService
 *
 * @author <a href="mailto:ahernandez@emergya.com">ahernandez</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:modelContext.xml"})
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class ZoneFoldersRelationTest {

	private static final Log LOG = LogFactory.getLog(ZoneFoldersRelationTest.class);

	@Resource
	protected Properties testProperties;

	protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");

	@Resource
	private ZoneAdminService zoneAdminService;

	@Resource
	private FoldersAdminService foldersAdminService;

	@Test
	public void findZoneByType() {
		try {

            List<ZoneDto> zones;

			ZoneDto zone1 = new ZoneDto();
			zone1.setName("zone1.test");
			zone1.setType("R.test");
			zone1 = (ZoneDto) zoneAdminService.create(zone1);

			ZoneDto zone2 = new ZoneDto();
			zone2.setName("zone2.test");
			zone2.setType("R.test");
			zone2 = (ZoneDto) zoneAdminService.create(zone2);

			ZoneDto zone3 = new ZoneDto();
			zone3.setName("zone3.test");
			zone3.setType("M.test");
			zone3 = (ZoneDto) zoneAdminService.create(zone3);


            zones = zoneAdminService.findByType("R.test");
            for (ZoneDto zoneDto: zones) {
                Assert.assertEquals(zoneDto.getType(),"R.test");
            }
            Assert.assertEquals(zones.size(), 2);


            zones = zoneAdminService.findByType("M.test");
            for (ZoneDto zoneDto: zones) {
                Assert.assertEquals(zoneDto.getType(),"M.test");
            }
            Assert.assertEquals(zones.size(), 1);


            zones = zoneAdminService.findByType("X");
            for (ZoneDto zoneDto: zones) {
                Assert.assertEquals(zoneDto.getType(),"X");
            }
            Assert.assertEquals(zones.size(), 0);

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			Assert.fail();
		}
	}

    @Test
    public void findFoldersByZone() {
		try {

            List<FolderDto> folders;

			ZoneDto zone1 = new ZoneDto();
			zone1.setName("zone1.test");
			zone1.setType("R.test");
			zone1 = (ZoneDto) zoneAdminService.create(zone1);

			ZoneDto zone2 = new ZoneDto();
			zone2.setName("zone2.test");
			zone2.setType("R.test");
			zone2 = (ZoneDto) zoneAdminService.create(zone2);

			ZoneDto zone3 = new ZoneDto();
			zone3.setName("zone3.test");
			zone3.setType("M.test");
			zone3 = (ZoneDto) zoneAdminService.create(zone3);

            FolderDto folder1 = new FolderDto();
            folder1.setName("folder1.test");
            folder1.setZoneId(zone1.getId());
            folder1 = (FolderDto) foldersAdminService.create(folder1);

            FolderDto folder2 = new FolderDto();
            folder2.setName("folder2.test");
            folder2.setZoneId(zone1.getId());
            folder2 = (FolderDto) foldersAdminService.create(folder2);

            FolderDto folder3 = new FolderDto();
            folder3.setName("folder3.test");
            folder3.setZoneId(zone2.getId());
            folder3 = (FolderDto) foldersAdminService.create(folder3);


            folders = foldersAdminService.findByZone(zone1.getId());
            for (FolderDto folderDto: folders) {
                Assert.assertEquals(folderDto.getZoneId(), zone1.getId());
            }
            Assert.assertEquals(folders.size(), 2);


            folders = foldersAdminService.findByZone(zone2.getId());
            for (FolderDto folderDto: folders) {
                Assert.assertEquals(folderDto.getZoneId(), zone2.getId());
            }
            Assert.assertEquals(folders.size(), 1);


            folders = foldersAdminService.findByZone(zone3.getId());
            for (FolderDto folderDto: folders) {
                Assert.assertEquals(folderDto.getZoneId(), zone3.getId());
            }
            Assert.assertEquals(folders.size(), 0);


		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			Assert.fail();
		}
    }

    @Test
    public void findFoldersByZoneAndParent() {
		try {

            List<FolderDto> folders;

			ZoneDto zone1 = new ZoneDto();
			zone1.setName("zone1.test");
			zone1.setType("R.test");
			zone1 = (ZoneDto) zoneAdminService.create(zone1);

			ZoneDto zone2 = new ZoneDto();
			zone2.setName("zone2.test");
			zone2.setType("R.test");
			zone2 = (ZoneDto) zoneAdminService.create(zone2);

			ZoneDto zone3 = new ZoneDto();
			zone3.setName("zone3.test");
			zone3.setType("M.test");
			zone3 = (ZoneDto) zoneAdminService.create(zone3);

            FolderDto folder1 = new FolderDto();
            folder1.setName("folder1.test");
            folder1.setZoneId(zone1.getId());
            folder1 = (FolderDto) foldersAdminService.create(folder1);

            FolderDto folder2 = new FolderDto();
            folder2.setName("folder2.test");
            folder2.setZoneId(zone1.getId());
            folder2.setIdParent(folder1.getId());
            folder2 = (FolderDto) foldersAdminService.create(folder2);

            FolderDto folder3 = new FolderDto();
            folder3.setName("folder3.test");
            folder3.setZoneId(zone2.getId());
            folder3 = (FolderDto) foldersAdminService.create(folder3);


            folders = foldersAdminService.findByZone(zone1.getId(), null, Boolean.TRUE);
            for (FolderDto folderDto: folders) {
                Assert.assertEquals(folderDto.getZoneId(), zone1.getId());
			    Assert.assertEquals(folderDto.getIdParent(), null);
            }
            Assert.assertEquals(folders.size(), 1);


            folders = foldersAdminService.findByZone(zone1.getId(), folder1.getId());
            for (FolderDto folderDto: folders) {
                Assert.assertEquals(folderDto.getZoneId(), zone1.getId());
			    Assert.assertEquals(folderDto.getIdParent(), folder1.getId());
            }
            Assert.assertEquals(folders.size(), 1);


            folders = foldersAdminService.findByZone(zone1.getId(), folder3.getId());
            for (FolderDto folderDto: folders) {
                Assert.assertEquals(folderDto.getZoneId(), zone1.getId());
			    Assert.assertEquals(folderDto.getIdParent(), folder3.getId());
            }
            Assert.assertEquals(folders.size(), 0);


		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e);
			Assert.fail();
		}
    }

}
