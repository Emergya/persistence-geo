/*
 * SQLGenerateUtils.java
 * 
 * Copyright (C) 2011
 * 
 * This file is part of Proyecto persistenceGeo
 * 
 * This software is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 * As a special exception, if you link this library with other files to produce
 * an executable, this library does not by itself cause the resulting executable
 * to be covered by the GNU General Public License. This exception does not
 * however invalidate any other reasons why the executable file might be covered
 * by the GNU General Public License.
 * 
 * Authors:: Alejandro Díaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.utils;

/**
 * Clase para generar ficheros de inicializacion sql
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 * @see data.sql
 *
 */
public class SQLGenerateUtils {
	
	public static final String[] params = { "solFinTra", "empAse", "empFor",
			"empAlo", "supEmpAlo", "horForm", "numCur", "empAut",
			"planesEmpresa", "horPro", "accExc", "proApe", "proCop",
			"usuariosAtentidos", "proPueMar", "proAmp", "proyInnMod",
			"empAten", "AseGesCon", "anaPos", "parFin", "for", "her", "accSei",
			"accUno", "accDos", "accTre", "accCua", "accCin", "resUnocan",
			"resDoscan", "resTrecan", "resCuacan", "resCincan", "resSeican" };
	public static final String separator = ", ";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i = 14;
		String all = new String();
		for(String param: params){
			String insert = "INSERT INTO ae_soa_report.data(\n"+
            "id, code, description, \"name\", origin)\n" + 
            "VALUES (" + (i++) + ", '"+param+"', '"+param + "', '"+param + "', 'Automático');\n";
			all += insert;
		}
		
		for(i = 12; i < 48 ; i++){
			String insert1 = "INSERT INTO ae_soa_report.data_children_data(\n"+
					"parent_id, data_id)\n"+
					"VALUES (" + i + ", 1);\n";
			String insert2 = "INSERT INTO ae_soa_report.data_children_data(\n"+
					"parent_id, data_id)\n"+
					"VALUES (" + i + ", 2);\n";
			all += insert1 + insert2;
		}
		System.out.println(all);
		
	}

}
