/*
 * AbstractController.java
 * 
 * Copyright (C) 2012
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
package com.emergya.persistenceGeo.webCore;

import java.io.Serializable;

import org.springframework.ui.Model;

/**
 * Clase abstracta a extender por los controles de la aplicacion
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
public abstract class AbstractController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2248980333660013583L;
	

	/**
	 * Incluye en el modelo los parametros por defecto del control
	 * 
	 * @param model
	 * @param update
	 *            si actualiza los parametros actualizables
	 */
	protected abstract void copyDefaultModel(boolean update, Model model);
	
	/**
	 * Modifica el modelo para incluir los parametros comunes de paginacion...
	 * 
	 * @param model
	 */
	protected void calculatePagination(Model model){
		if(this instanceof PaginationController){
			long numElements = ((PaginationController)this).getNumElements();
			long numPages = ((PaginationController)this).getNumPages();
			int first = ((PaginationController)this).getFirst();
			int last = ((PaginationController)this).getLast();
			if( numElements > 10){
				numPages = (long) (Math.floor(numElements / 10) + 1);
			}else{
				numPages = 1;
			}
			((PaginationController)this).setNumPages(numPages);
			model.addAttribute("first", first);
			model.addAttribute("numPages", numPages);
			model.addAttribute("numElements", numElements);
			model.addAttribute("last", last);
			model.addAttribute("paginationUrl", ((PaginationController)this).getDefaultPaginationUrl());
		}
	}

	
	/**
	 * Modifica el modelo para incluir los parametros comunes de subtabs
	 * 
	 * @param model
	 */
	protected void writeSubTabs(Model model){
		if(this instanceof SubTabsController){
			model.addAttribute("allSubTabs", ((SubTabsController)this).getAllSubTabs());
			model.addAttribute("selectedSubTab", ((SubTabsController)this).getSelectedSubTab());
		}
	}
	
	/**
	 * Copia el error al attribute error del modelo
	 * 
	 * @param error
	 * @param e
	 * 
	 * @param model
	 */
	public void errorToModel(String error, Exception e, Model model){
		String message = error + ".";
		if(e != null){
			message += "\n Causa: " + e.getMessage();
		}
		model.addAttribute("error", message);
	}
	
	/**
	 * Copia el error al attribute error del modelo
	 * 
	 * @param error
	 * 
	 * @param model
	 */
	public void errorToModel(String error, Model model){
		errorToModel(error, null, model);
	}
	
	/**
	 * Copia el mensaje al atributo info del modelo
	 * 
	 * @param message
	 * @param model
	 */
	public void infoToModel(String message, Model model){
		model.addAttribute("info", message);
	}

	/**
	 * Actualiza el modelo
	 * 
	 * @param model
	 */
	protected void copyDefaultModel(Model model) {
		copyDefaultModel(false, model);
		calculatePagination(model);
		writeSubTabs(model);
	}
}
