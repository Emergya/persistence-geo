/*
 * ZoneEntityDaoHibernateImplTest.java
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

import com.emergya.persistenceGeo.dao.ZoneEntityDao;
import com.emergya.persistenceGeo.metaModel.AbstractZoneEntity;


/**
 * Test para ZoneEntityDao
 *
 * @author <a href="mailto:ahernandez@emergya.com">ahernandez</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:modelContext.xml"})
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class ZoneEntityDaoHibernateImplTest {

	private static final Log LOG = LogFactory.getLog(ZoneEntityDaoHibernateImplTest.class);

	@Resource
	protected Properties testProperties;

	protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");

	@Resource
    private ZoneEntityDao zoneDao;

	@Test
	public void testCreateZone() {
		try {

            String zoneName = "zone1.test";
            AbstractZoneEntity zone = zoneDao.createZone(zoneName);

			List<AbstractZoneEntity> zones = zoneDao.getZones(zoneName);

			for (AbstractZoneEntity zoneEntity: zones) {
			    Assert.assertEquals(zoneName, zoneEntity.getName());
            }

		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
	}

	@Test
	public void testFindZonesByType() {

		try {

            AbstractZoneEntity zone1 = zoneDao.createZone("zone1.test");
            zone1.setType("R.test");
            zone1 = zoneDao.makePersistent(zone1);

            AbstractZoneEntity zone2 = zoneDao.createZone("zone2.test");
            zone2.setType("R.test");
            zone2 = zoneDao.makePersistent(zone2);

            AbstractZoneEntity zone3 = zoneDao.createZone("zone3.test");
            zone3.setType("R.test");
            zone3 = zoneDao.makePersistent(zone3);

            AbstractZoneEntity zone4 = zoneDao.createZone("zone4.test");
            zone4.setType("M.test");
            zone4 = zoneDao.makePersistent(zone4);


            List<AbstractZoneEntity> zones;

			zones = zoneDao.findByType("R.test");
			for (AbstractZoneEntity zoneEntity: zones) {
			    Assert.assertEquals(zoneEntity.getType(), "R.test");
            }
            Assert.assertEquals(zones.size(), 3);


			zones = zoneDao.findByType("M.test");
			for (AbstractZoneEntity zoneEntity: zones) {
			    Assert.assertEquals(zoneEntity.getType(), "M.test");
            }
            Assert.assertEquals(zones.size(), 1);


			zones = zoneDao.findByType("X");
			for (AbstractZoneEntity zoneEntity: zones) {
			    Assert.assertEquals(zoneEntity.getType(), "X");
            }
            Assert.assertEquals(zones.size(), 0);

		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
	}

}
