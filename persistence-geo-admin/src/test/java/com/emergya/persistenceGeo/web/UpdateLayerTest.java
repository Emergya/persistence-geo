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
package com.emergya.persistenceGeo.web;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.emergya.persistenceGeo.dto.LayerDto;
import com.emergya.persistenceGeo.dto.RuleDto;
import com.emergya.persistenceGeo.dto.StyleDto;
import com.emergya.persistenceGeo.service.LayerAdminService;


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
public class UpdateLayerTest{

	private static final Log LOG = LogFactory.getLog(UpdateLayerTest.class);
	
	protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/mm/yyyy");
	
	@Resource
	private LayerAdminService layerAdminService;
	
	@Resource
	private RestLayersAdminController restLayerAdminService;

	protected static final String PR_2_LAYER_NAME = "tmpLayer" + new Random().nextInt();
	protected static final String PR_2_LAYER_NAME_UPDATE = "tmpLayer" + new Random().nextInt();
	protected static final String PR_2_STYLE_NAME = "testStyle";
	protected static final String PR_2_STYLE_PROPERTY_NAME = "testName";
	protected static final String PR_2_STYLE_PROPERTY_VALUE = "valueTest";
	protected static final String PR_2_LAYER_DATA = "target/test-classes/ficheros/barcelona_city_drive_4326.kml";
	
	@Test
	public void testUpdateLayer() {
		try{
			LayerDto layer = new LayerDto();
			layer.setName(PR_2_LAYER_NAME);
			layer.setType(LayerAdminService.TYPE_KML);
			layer.setData(new File(PR_2_LAYER_DATA));
			layer.setType("KML");
			layer = (LayerDto) layerAdminService.create(layer);
			List<LayerDto> layers = layerAdminService.getLayersByName(PR_2_LAYER_NAME);
			Assert.assertNotNull(layers);
			Assert.assertEquals(layers.size(), 1);
			Assert.assertEquals(layers.get(0).getId(), layer.getId());
			LayerDto layerUpdate = layers.get(0);
			layerUpdate.setName(PR_2_LAYER_NAME_UPDATE);
			StyleDto style = new StyleDto();
			style.setName(PR_2_STYLE_NAME);
			Map<RuleDto, Map<String, String>> rules = new HashMap<RuleDto, Map<String,String>>();
			RuleDto rule = new RuleDto();
			rule.setFilter("true");
			Map<String, String> properties = new HashMap<String, String>();
			properties.put(PR_2_STYLE_PROPERTY_NAME, PR_2_STYLE_PROPERTY_VALUE);
			rules.put(rule, properties);
			style.setRules(rules);
			Map<StyleDto, Map<RuleDto, Map<String, String>>> styles = new HashMap<StyleDto, Map<RuleDto,Map<String,String>>>();
			styles.put(style, rules);
			layerUpdate.setStyles(styles);
			layerAdminService.update(layerUpdate);
			layers = layerAdminService.getLayersByName(PR_2_LAYER_NAME);
			Assert.assertNotNull(layers);
			Assert.assertEquals(layers.size(), 0);
			layers = layerAdminService.getLayersByName(PR_2_LAYER_NAME_UPDATE);
			Assert.assertNotNull(layers);
			Assert.assertEquals(layers.size(), 1);
			Assert.assertEquals(layers.get(0).getId(), layer.getId());
			Assert.assertEquals(layers.get(0).getName(), PR_2_LAYER_NAME_UPDATE);
			Assert.assertNotNull(layers.get(0).getStyles());
			Assert.assertEquals(layers.get(0).getStyles().size(), 1);
			for (StyleDto styleDto: layers.get(0).getStyles().keySet()){
				//Only one
				Assert.assertNotNull(styleDto.getRules());
				Assert.assertEquals(styleDto.getRules().size(), 1);
				rules = styleDto.getRules();
				for(RuleDto ruleDto: rules.keySet()){
					//Only one
					Assert.assertEquals(ruleDto.getStyle(), PR_2_STYLE_NAME);
					Assert.assertTrue(rules.get(ruleDto).containsKey(PR_2_STYLE_PROPERTY_NAME));
					Assert.assertEquals(rules.get(ruleDto).get(PR_2_STYLE_PROPERTY_NAME), PR_2_STYLE_PROPERTY_VALUE);
				}
			}
			Map<String, Object> result = restLayerAdminService.saveLayerSimpleProperties(layerUpdate.getId()+ "", null, "inUse===false");
			Assert.assertNotNull(result);
			Assert.assertEquals(result.get(restLayerAdminService.SUCCESS), Boolean.TRUE);
		}catch (Exception e){
			e.printStackTrace();
			LOG.error(e);
			Assert.fail();
		}
	}

}
