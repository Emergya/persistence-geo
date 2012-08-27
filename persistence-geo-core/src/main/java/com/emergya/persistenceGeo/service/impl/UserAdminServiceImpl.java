/*
 * UserAdminServiceImpl.java
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
package com.emergya.persistenceGeo.service.impl;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.AbstractGenericDao;
import com.emergya.persistenceGeo.dao.AuthorityEntityDao;
import com.emergya.persistenceGeo.dao.UserEntityDao;
import com.emergya.persistenceGeo.dto.AuthorityDto;
import com.emergya.persistenceGeo.dto.UserDto;
import com.emergya.persistenceGeo.model.AuthorityEntity;
import com.emergya.persistenceGeo.model.UserEntity;
import com.emergya.persistenceGeo.service.UserAdminService;

/**
 * Implementacion transacional de UserAdminService basada en el uso de daos
 * {@link AbstractGenericDao}
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
@Repository
@Transactional
public class UserAdminServiceImpl extends AbstractServiceImpl<UserDto, UserEntity> implements UserAdminService {

	@Resource
	private UserEntityDao userDao;
	@Resource
	private AuthorityEntityDao authorityDao;

	public UserAdminServiceImpl() {
		super();
	}

	/**
	 * Obtiene un usuario por nombre
	 * 
	 * @param name
	 *            del usuario
	 * 
	 * @return si no existia lo crea sin grupo de usuarios
	 */
	public UserDto obtenerUsuario(String name) {
		UserDto dto = entityToDto(userDao.getUser(name));
		return dto;
	}

	/**
	 * Obtiene un usuario por nombre
	 * 
	 * @param name
	 *            del usuario
	 * @param password
	 *            del usuario
	 * 
	 * @return si no existia lo crea sin grupo de usuarios
	 */
	public UserDto obtenerUsuario(String name, String password) {
		UserDto dto = entityToDto(userDao.getUser(name, password));
		if (dto == null) {
			dto = entityToDto(userDao.createUser(name, password));
		}
		return dto;
	}

	/**
	 * Obtiene todos los usuarios del sistema
	 * 
	 * @return usuarios del sistema
	 */
	public List<UserDto> obtenerUsuarios() {
		List<UserDto> result = new LinkedList<UserDto>();
		for (UserEntity entity : userDao.findAll()) {
			result.add(entityToDto(entity));
		}
		return result;
	}

	/**
	 * Obtiene todos los grupos de usuario del sistema
	 * 
	 * @return grupos de usuario del sistema
	 */
	public List<AuthorityDto> obtenerGruposUsuarios() {
		List<AuthorityDto> result = new LinkedList<AuthorityDto>();
		for (AuthorityEntity entity : authorityDao.findAll()) {
			result.add(entityGPToDto(entity));
		}
		return result;
	}

	/**
	 * Obtiene el grupo de usuarios por id
	 * 
	 * @param id
	 *            del grupo
	 * 
	 * @return grupo de usuarios asociado al id o null si no lo encuentra
	 */
	public AuthorityDto obtenerGrupoUsuarios(Long id) {
		return entityGPToDto(authorityDao.findById(id, false));
	}

	/**
	 * Crea un nuevo grupo de usuarios con los datos pasados por argumento
	 * 
	 * @param dto
	 * 
	 * @return id
	 */
	public Long crearGrupoUsuarios(AuthorityDto dto) {
		return authorityDao.save(dtoToEntity(dto));
	}

	/**
	 * Asocia un usuario a un grupo en particular
	 * 
	 * @param idGrupo
	 * @param usuario
	 */
	public void addUsuarioAGrupo(Long idGrupo, String usuario) {
		AuthorityEntity authorityEntity = authorityDao.findById(idGrupo, false);
		List<UserEntity> usuarios = authorityEntity.getPeople();
		if (usuarios == null) {
			usuarios = new LinkedList<UserEntity>();
		}
		boolean enc = false;
		for (UserEntity usuarioEntity : usuarios) {
			if (usuarioEntity.getUsername().equals(usuario)) {
				enc = true;
				break;
			}
		}
		if (!enc) {
			usuarios.add(userDao.getUser(usuario));
			authorityEntity.setPeople(usuarios);
			authorityDao.save(authorityEntity);
		}
	}

	/**
	 * Elimina un usuario de un grupo en particular
	 * 
	 * @param idGrupo
	 * @param usuario
	 */
	public void eliminaUsuarioDeGrupo(Long idGrupo, String usuario) {
		AuthorityEntity authorityEntity = authorityDao.findById(idGrupo, false);
		List<UserEntity> usuarios = authorityEntity.getPeople();
		if (usuarios == null) {
			usuarios = new LinkedList<UserEntity>();
		}
		boolean enc = false;
		for (UserEntity usuarioEntity : usuarios) {
			if (usuarioEntity.getUsername().equals(usuario)) {
				enc = true;
				usuarios.remove(usuarioEntity);
				break;
			}
		}
		if (enc) {
			authorityEntity.setPeople(usuarios);
			authorityDao.save(authorityEntity);
		}
	}

	/**
	 * Elimina un grupo de usuarios
	 * 
	 * @param idgrupo
	 */
	public void eliminarGrupoUsuarios(Long idgrupo) {
		authorityDao.delete(idgrupo);
	}

	/**
	 * Modifica un grupo de usuarios
	 * 
	 * @param dto
	 */
	public void modificarGrupoUsuarios(AuthorityDto dto) {
		authorityDao.save(dtoToEntity(dto));
	}

	protected UserDto entityToDto(UserEntity user) {
		UserDto dto = null;
		if (user != null) {
			dto = new UserDto();
			dto.setId(user.getUser_id());
			dto.setUsername(user.getUsername());
			dto.setAdmin(user.getAdmin());
			dto.setApellidos(user.getApellidos());
			dto.setEmail(user.getEmail());
			dto.setFechaActualizacion(user.getFechaActualizacion());
			dto.setFechaCreacion(user.getFechaCreacion());
			dto.setNombreCompleto(user.getNombreCompleto());
			dto.setPassword(user.getPassword());
			dto.setTelefono(user.getTelefono());
			dto.setValid(user.getValid());
			//Grupos
			List<String> grupos = new LinkedList<String>();
			List<AuthorityEntity> authorities = authorityDao.findByUser(user
					.getUser_id());
			if (authorities != null) {
				for (AuthorityEntity authority : authorities) {
					grupos.add(authority.getAuthority());
				}
			}
			dto.setGrupos(grupos);
		}
		return dto;
	}

	private AuthorityDto entityGPToDto(AuthorityEntity entity) {
		AuthorityDto dto = null;
		if (entity != null) {
			dto = new AuthorityDto();
			dto.setNombre(entity.getAuthority());
			dto.setId(entity.getId());
			List<String> usuarios = new LinkedList<String>();
			if (entity.getPeople() != null) {
				for (UserEntity user : entity.getPeople()) {
					usuarios.add(user.getUsername());
				}
			}
			dto.setUsuarios(usuarios);
		}
		return dto;
	}

	private AuthorityEntity dtoToEntity(AuthorityDto dto) {
		AuthorityEntity entity = null;
		if (dto != null) {
			if (dto.getId() != null) {
				entity = authorityDao.findById(dto.getId(), false);
			} else {
				entity = new AuthorityEntity();
			}
			entity.setAuthority(dto.getNombre());

			// People
			List<UserEntity> people = new LinkedList<UserEntity>();
			if (dto.getUsuarios() != null) {
				for (String userName : dto.getUsuarios()) {
					people.add(userDao.getUser(userName));
				}
			}
			entity.setPeople(people);
		}
		return entity;
	}

	@Override
	protected UserEntity dtoToEntity(UserDto dto) {
		UserEntity entity = null;
		
		if(dto != null){
			Date now = new Date();
			
			if(dto.getId() != null && dto.getId() > 0){
				entity = (UserEntity) userDao.findById(dto.getId(), true);
				//Grupos
				authorityDao.clearUser(dto.getId());
			}else{
				entity =  userDao.createUser(dto.getUsername(), dto.getPassword());
				entity.setFechaCreacion(now);
			}
			entity.setAdmin(dto.getAdmin());
			entity.setApellidos(dto.getApellidos());
			entity.setEmail(dto.getEmail());
			entity.setFechaActualizacion(now);
			entity.setPassword(dto.getPassword());
			entity.setTelefono(dto.getTelefono());
			entity.setUsername(dto.getUsername());
			entity.setNombreCompleto(dto.getNombreCompleto());
			entity.setValid(dto.getValid());
			
			//Grupos
			List<String> grupos = dto.getGrupos();
			if (grupos != null) {
				List<AuthorityEntity> authorities = authorityDao.findByName(grupos);
				for (AuthorityEntity authority: authorities){
					this.addUsuarioAGrupo(authority.getId(), dto.getUsername());
				}
			}
		}
		
		return entity;
	}

}
