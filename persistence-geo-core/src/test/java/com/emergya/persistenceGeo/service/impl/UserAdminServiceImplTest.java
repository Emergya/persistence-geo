/* 
 * UserEntityDaoHibernateImplTest.java
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
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
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

import com.emergya.persistenceGeo.dto.AuthorityDto;
import com.emergya.persistenceGeo.dto.UserDto;
import com.emergya.persistenceGeo.service.UserAdminService;


/**
 * Test para UserEntityDao
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:modelContext.xml"})
@TransactionConfiguration(defaultRollback = true, transactionManager = "transactionManager")
@Transactional
public class UserAdminServiceImplTest{

	private static final Log LOG = LogFactory.getLog(UserAdminServiceImplTest.class);

	@Resource
	protected Properties testProperties;
	
	protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");

	// Propiedades a ser utilizada en los test procedentes de testProperties: testCreateUser
	protected static final String PR_1_PARAM_1 = "pr1.username";
	
	@Resource
	private UserAdminService userAdminService;

	@Test
	public void testCreateUser() {
		try {
			UserDto usuario = userAdminService.obtenerUsuario(testProperties.getProperty(PR_1_PARAM_1), testProperties.getProperty(PR_1_PARAM_1));
			Assert.assertEquals(testProperties.getProperty(PR_1_PARAM_1), usuario.getUsername());
		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
	}

	@Test
	public void testCreateGroup() {
		try {
			AuthorityDto dto = new AuthorityDto();
			dto.setNombre("grupoTest");
			dto.setZone("Sevilla");
			Long id = userAdminService.crearGrupoUsuarios(dto);
			Assert.assertEquals(dto.getNombre(), userAdminService.obtenerGrupoUsuarios(id).getNombre());
		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
	}

	@Test
	public void testModifyCreateGroup() {
		try {
			testCreateUser();
			AuthorityDto dto = new AuthorityDto();
			dto.setNombre("grupoTest");
			dto.setZone("Sevilla");
			Long id = userAdminService.crearGrupoUsuarios(dto);
			Assert.assertEquals(dto.getNombre(), userAdminService.obtenerGrupoUsuarios(id).getNombre());
			dto.setId(id);
			List<String> usuarios = new LinkedList<String>();
			usuarios.add(testProperties.getProperty(PR_1_PARAM_1));
			dto.setUsuarios(usuarios);
			userAdminService.modificarGrupoUsuarios(dto);
			Assert.assertEquals(testProperties.getProperty(PR_1_PARAM_1), userAdminService.obtenerGrupoUsuarios(id).getUsuarios().get(0));
		} catch (Exception e) {
			LOG.error("Error  \n", e);
			Assert.fail();
		}
	}

}
