/*
 * ZoneAdminService.java
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

import com.emergya.persistenceGeo.dto.ZoneDto;


/**
 * Interfaz de administracion de usuarios y grupos de usuarios 
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 *
 */
public interface ZoneAdminService extends AbstractService{

    public List<ZoneDto> findByType(String type);

    public List<ZoneDto> findByType(String type, Boolean isEnabled);
    
    public List<ZoneDto> findAllEnabled();
    
    public List<ZoneDto> findByParent(Long idParent);
}
