package com.emergya.persistenceGeo.utils;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class GeoserverUtilsTest {

	@Test
	public void testCreateName() {
		
		String cleanName = "test_Name";
		
		String resultName = GeoserverUtils.createName(cleanName);
		
		assertEquals(String.format("Clean name was modified! %s -> %s", cleanName, resultName), cleanName, resultName);
		
		String badName = "943_óñ dfjlkdfj";
		resultName = GeoserverUtils.createName(badName);
		
		assertFalse(
				"The result starts with a number! "+resultName,
				StringUtils.isNumeric(resultName.substring(0,1)));
		
		assertFalse(
				"The result contains non bad characters! "+ resultName, 
				resultName.matches("\\w"));	
	}

	@Test
	public void testCreateUniqueName() {
		
		String testName = "9á dlkfj ldf loeññññ";
		
		assertFalse("Generated names are the same!", 
				GeoserverUtils.createUniqueName(testName).equals(GeoserverUtils.createUniqueName(testName)));
		
	}

}
