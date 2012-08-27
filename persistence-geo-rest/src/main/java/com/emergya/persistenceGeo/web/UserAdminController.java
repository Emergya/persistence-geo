/*
 * UserAdminController.java
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
package com.emergya.persistenceGeo.web;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.emergya.persistenceGeo.dto.AuthorityDto;
import com.emergya.persistenceGeo.dto.UserDto;
import com.emergya.persistenceGeo.service.UserAdminService;
import com.emergya.persistenceGeo.webCore.AbstractController;
import com.emergya.persistenceGeo.webCore.PaginationController;

/**
 * Simple index page controller for user admin
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 */
public class UserAdminController extends AbstractController 
		implements Serializable, PaginationController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3301952158744971663L;

	private static Log LOG = LogFactory.getLog(UserAdminController.class);
	
	protected int first = 0;
	protected long numPages = 0;
	protected long numElements = 10;
	protected int last = 9;

	@Resource
	private UserAdminService userAdminService;
	
	private List<AuthorityDto> grupos = null;
	private List<UserDto> usuarios = null;

	//Esto incluye las instituciones en la lista de grupos disponibles
	public List<AuthorityDto> getGrupos(boolean actualizar) {
		if(actualizar || grupos == null){
			grupos = userAdminService.obtenerGruposUsuarios();
		}
		return grupos;
	}

	/**
	 * Pagina inicial de la administracion de usuarios
	 * 
	 * @param model
	 * 
	 * @return "usuarios"
	 */
	@RequestMapping(value = "/admin/usuarios", method = RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public String getUsers(Model model){
		LOG.info("Consultando los usuarios del sistema");
		first = 0;
		numElements = userAdminService.getResults();
		if(numElements<10){
			if(numElements > 1){
				last = (int) (numElements-1);
			}
			usuarios = (List<UserDto>) userAdminService.getAll();
		}else{
			last = 9;
			usuarios = (List<UserDto>) userAdminService.getFromTo(first, last);
		}
		copyDefaultModel(model);
		LOG.info("Redirigiendo a la vista");
		return "admin/usuarios/usuarios";
	}


	/**
	 * Pagina para la creacion de un nuevo usuario
	 * 
	 * @param model
	 * 
	 * @return "nuevoUsuario"
	 */
	@RequestMapping(value = "/admin/nuevoUsuario", method = RequestMethod.GET)
	public String createUser(Model model){
		model.addAttribute("usuario", new UserDto());
		return "admin/usuarios/nuevoUsuario";
	}
	
	private final String BOOL_SI = "Si";
	private final String CHK_SI = "on"; 

	/**
	 * Pagina para guardar o actualizar
	 * 
	 * @param model
	 * 
	 * @return "usuarios"
	 */
	@RequestMapping(value = "/admin/salvarUsuario", method = RequestMethod.POST)
	public String saveUser(
			@RequestParam String username,
			@RequestParam String nombreCompleto,
		    @RequestParam String password,
			@RequestParam String apellidos,
		    @RequestParam String email,
		    @RequestParam String telefono,
		    @RequestParam(required=false) String admin,
		    @RequestParam(required=false) String valid,
		    @RequestParam(required=false) String id,
			Model model){
		UserDto usuario = new UserDto();
		usuario.setId(id != null ? Long.decode(id): null);
		usuario.setAdmin(CHK_SI.equals(admin) ? Boolean.TRUE : Boolean.FALSE);
		usuario.setApellidos(apellidos);
		usuario.setEmail(email);
		usuario.setNombreCompleto(nombreCompleto);
		usuario.setPassword(password);
		usuario.setTelefono(telefono);
		usuario.setUsername(username);
		usuario.setValid(BOOL_SI.equals(valid) ? Boolean.TRUE : Boolean.FALSE);
		
		//TODO
		usuario.setGrupos(null);
		
		if(usuario.getId() != null){
			usuario = (UserDto) userAdminService.update(usuario);
		}else{
			usuario = (UserDto) userAdminService.create(usuario);
		}
		return getUsers(model);
	}
	
	@RequestMapping(value = "/admin/borrarUsuario", method = RequestMethod.POST)
	public String deleteUser(@RequestParam String username,Model model){
		UserDto usuario = (UserDto) userAdminService.obtenerUsuario(username);
		userAdminService.delete(usuario);
		return getUsers(model);
	}

	
	@RequestMapping(value = "/admin/editarUsuario", method = RequestMethod.POST)
	public String modifyUser(@RequestParam String username, Model model){
		UserDto usuario = (UserDto) userAdminService.obtenerUsuario(username);
		model.addAttribute("usuario", usuario);
		return "/admin/usuarios/editarUsuario";
	}


	/**
	 * Pagina inicial de la administracion de usuarios paginada. Necesaria para la paginacion.
	 * 
	 * @param first
	 * @param last
	 * @param model
	 * 
	 * @see AbstractController
	 * @see WB-INF/jsp/decaorators/footerPagination.jsp
	 * 
	 * @return "usuarios"
	 */
	@RequestMapping(value = "/admin/usuarios/{first}/{last}", method = RequestMethod.GET)
	@SuppressWarnings("unchecked")
	public String getUsersFromTo(
			@PathVariable Integer first, 
			@PathVariable Integer last, Model model){
		this.first = first;
		this.last = last;
		usuarios = (List<UserDto>) userAdminService.getFromTo(first, last);
		copyDefaultModel(model);
		return "admin/usuarios/usuarios";
	}

	/**
	 * Referencia al enlace a la paginacion de usuarios
	 * 
	 * @return 'admin/usuarios'
	 */
	public String getDefaultPaginationUrl() {
		return "/admin/usuarios";
	}
	
	/**
	 * Incluye en el modelo los parametros por defecto de la administracion de usuarios/grupos
	 * 
	 * @param model
	 * @param update si actualiza los parametros actualizables
	 */
	protected void copyDefaultModel(boolean update, Model model) {
		calculatePagination(model);
		model.addAttribute("usuarios", usuarios);
	}
	
	public Integer getFirst() {
		return first;
	}

	public void setFirst(Integer first) {
		this.first = first;
	}

	public Long getNumPages() {
		return numPages;
	}

	public void setNumPages(Long numPages) {
		this.numPages = numPages;
	}

	public Long getNumElements() {
		return numElements;
	}

	public void setNumElements(Long numElements) {
		this.numElements = numElements;
	}

	public Integer getLast() {
		return last;
	}

	public void setLast(Integer last) {
		this.last = last;
	}

}
