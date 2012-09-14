/**
 * 
 */
package com.emergya.persistenceGeo.utils;

import java.io.File;
import java.io.IOException;

/**
 * @author adiaz
 *
 */
public class FileUtils {

	private static final String PUNTO = ".";

	/**
	 * Create a temporal file 
	 * 
	 * @param fileName
	 * @param type 
	 * 
	 * @return temporal file
	 * 
	 * @throws IOException
	 */
	public static File createFileTemp(String fileName, String type) throws IOException{
		String extension = null;
		String name = null;
		try{
			extension = type != null ? type.toLowerCase() : getDocumentExtension(fileName);
			name = type != null ? fileName : getDocumentName(fileName);
		}catch (Exception e){
			return null;
		}
		return File.createTempFile(name, extension);
	}

	/**
	 * Obtiene a extension
	 * 
	 * @param path
	 * @return extension del documento 
	 */
	public static String getDocumentExtension(String path) {
		String extension = null;
		try{
			extension = path.substring(path.lastIndexOf(PUNTO)+1);
		}catch (Exception e){
			return null;
		}
		return extension;
	}

	/**
	 * Obtiene el nombre del documento
	 * 
	 * @param path
	 * @return nombre del documento 
	 */
	public static String getDocumentName(String path) {
		String name = null;
		try{
			name = path.substring(0, path.lastIndexOf(PUNTO));
		}catch (Exception e){
			return null;
		}
		return name;
	}

}
