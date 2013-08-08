package com.emergya.persistenceGeo.utils;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class FileUtilsTest {
	
	private static final int ZIP_FILE_COUNT = 3;
	private static final String FILE_CONTENT= "Hola k ase!";

	@Test
	public void testCreateTmpDirectoryZip() throws IOException {
		
		File contentDir = File.createTempFile("testDir", "");
		contentDir.delete();
		contentDir.mkdir();
		
		
		for(int i=0; i<ZIP_FILE_COUNT; i++) {
			File contentFile = new File(contentDir, String.format("testContentFile-%s.txt",i));
			org.apache.commons.io.FileUtils.writeStringToFile(contentFile, FILE_CONTENT);
		}		
		
		
		File zipFile = FileUtils.createTmpDirectoryZip(contentDir, "testZip");
		
		ZipFile openZip = new ZipFile(zipFile);
		
		int zipEntries = 0;
		
		while(openZip.entries().hasMoreElements()) {
			zipEntries++;
		}
		
		
		
		assertEquals("The number of files is not equal to the number of created files!", ZIP_FILE_COUNT, zipEntries);
		
		Random r = new Random();
		
		
		// We randomly get one entry.
		ZipEntry entry = openZip.getEntry(String.format("testContentFile-%s.txt",r.nextInt(ZIP_FILE_COUNT)));
		
		String fileContent = IOUtils.toString(openZip.getInputStream(entry));
		
		assertEquals("The file content is not equal to the contents of the source files!", FILE_CONTENT, fileContent);
		
		openZip.close();
		
	}

}
