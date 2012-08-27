/* 
 * SQLFileutils.java
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
package com.emergya.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Simple acces to files.
 * Used by {@link SectionExporterDAOReadSQLFilesImpl}
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 * @see {@link SectionExporterDAOReadSQLFilesImpl}
 */
public class SQLFileutils {
	
	//@Resource(name="characterEncoding")
	public static String characterEncoding = "UTF-8"; //TODO:Obtener 
    
	/**
	 * Obtain the id of a section as '1_nameSection'
	 * 
	 * @param section with the id
	 * 
	 * @return left side from section splited by '_'
	 */
    public static Integer getSection(String section){
    	return Integer.parseInt(section.split("_")[0]);
    }
    
    /**
	 * Obtain the parameter name of a sqlFile as '1_parameterName.sql'
	 * 
	 * @param sqlFile with the paameter name
	 * 
	 * @return right side from sqlFile splited by '_' and deleted last 4 characters
	 */
    public static String getParameterName(String sqlFile){
    	String subFileName = sqlFile.split("_")[1];
    	String subFileName2 = subFileName.substring(0,subFileName.length()-4);
    	return subFileName2;
    }
	
    /**
     * Obtain sql query from file
     * 
     * @param filePath of the sql file
     * 
     * @return the file contain as string
     */
	public static final String getSqlString(String filePath){
		String result = null;
		
		File file  = new File(filePath);
		
		try{
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			copy(new FileInputStream(file), bao);
			result = bao.toString(characterEncoding);
		}catch (Exception e){
			//TODO
		}
		
		return result;
	}
	
	/**
	 * List all subdirectories of a file
	 * 
	 * @param filePath
	 * 
	 * @return subdirectories
	 */
	public static final String [] getSubDirectories(String filePath){
		try{
			File file  = new File(filePath);
			return file.list();
		}catch (Exception e){
			//TODO
		}
		
		return null;
	}

	/** Auxiliary methods and properties copies from fr.opensagres.xdocreport.core.io.IOUtils **/
	
	/**
	 * The default buffer size to use.
	 */
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

	// copy from InputStream
	// -----------------------------------------------------------------------
	/**
	 * Copy bytes from an <code>InputStream</code> to an
	 * <code>OutputStream</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * <p>
	 * Large streams (over 2GB) will return a bytes copied value of
	 * <code>-1</code> after the copy has completed since the correct number of
	 * bytes cannot be returned as an int. For large streams use the
	 * <code>copyLarge(InputStream, OutputStream)</code> method.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @return the number of bytes copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @throws ArithmeticException
	 *             if the byte count is too large
	 * @since Commons IO 1.1
	 */
	public static int copy(InputStream input, OutputStream output)
			throws IOException {
		long count = copyLarge(input, output);
		if (count > Integer.MAX_VALUE) {
			return -1;
		}
		return (int) count;
	}

	/**
	 * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
	 * <code>OutputStream</code>.
	 * <p>
	 * This method buffers the input internally, so there is no need to use a
	 * <code>BufferedInputStream</code>.
	 * 
	 * @param input
	 *            the <code>InputStream</code> to read from
	 * @param output
	 *            the <code>OutputStream</code> to write to
	 * @return the number of bytes copied
	 * @throws NullPointerException
	 *             if the input or output is null
	 * @throws IOException
	 *             if an I/O error occurs
	 * @since Commons IO 1.3
	 */
	public static long copyLarge(InputStream input, OutputStream output)
			throws IOException {
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		long count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

}
