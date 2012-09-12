/*
 * UserAdminService.java
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
package com.emergya.persistenceGeo.service;

import java.util.List;

import com.emergya.persistenceGeo.dto.AuthorityDto;
import com.emergya.persistenceGeo.dto.UserDto;

/**
 * Interfaz de administracion de usuarios y grupos de usuarios 
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public interface UserAdminService extends AbstractService{

	/**
	 * Obtiene un usuario por nombre
	 * 
	 * @param name del usuario
	 * 
	 * @return si no existia lo crea sin grupo de usuarios
	 */
	public UserDto obtenerUsuario(String name);

	/**
	 * Obtiene un usuario por nombre y password
	 * 
	 * @param name del usuario
	 * @param password del usuario
	 * 
	 * @return si no existia lo crea sin grupo de usuarios
	 */
	public UserDto obtenerUsuario(String name, String password);

	/**
	 * Obtiene todos los usuarios del sistema
	 * 
	 * @return usuarios del sistema
	 */
	public List<UserDto> obtenerUsuarios();

	/**
	 * Obtiene todos los grupos de usuario del sistema
	 * 
	 * @return grupos de usuario del sistema
	 */
	public List<AuthorityDto> obtenerGruposUsuarios();
	
	/**
	 * Obtiene el grupo de usuarios por id
	 * 
	 * @param id del grupo 
	 * 
	 * @return grupo de usuarios asociado al id o null si no lo encuentra
	 */
	public AuthorityDto obtenerGrupoUsuarios(Long id);
	
	/**
	 * Crea un nuevo grupo de usuarios con los datos pasados por argumento
	 * 
	 * @param dto
	 * 
	 * @return id
	 */
	public Long crearGrupoUsuarios(AuthorityDto dto);
	
	/**
	 * Asocia un usuario a un grupo en particular
	 * 
	 * @param idGrupo
	 * @param usuario
	 */
	public void addUsuarioAGrupo(Long idGrupo, String usuario);
	
	/**
	 * Elimina un usuario de un grupo en particular
	 * 
	 * @param idGrupo
	 * @param usuario
	 */
	public void eliminaUsuarioDeGrupo(Long idGrupo, String usuario);
	
	/**
	 * Elimina un grupo de usuarios
	 * 
	 * @param idgrupo
	 */
	public void eliminarGrupoUsuarios(Long idgrupo);

	
	/**
	 * Modifica un grupo de usuarios
	 * 
	 * @param dto
	 */
	public void modificarGrupoUsuarios(AuthorityDto dto);
	
}
