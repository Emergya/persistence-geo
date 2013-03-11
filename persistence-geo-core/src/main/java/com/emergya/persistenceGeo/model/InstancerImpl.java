/*
 * InstancerImpl.java
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
package com.emergya.persistenceGeo.model;

import com.emergya.persistenceGeo.metaModel.AbstractAuthorityEntity;
import com.emergya.persistenceGeo.metaModel.AbstractAuthorityTypeEntity;
import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;
import com.emergya.persistenceGeo.metaModel.AbstractFolderTypeEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerPropertyEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerTypeEntity;
import com.emergya.persistenceGeo.metaModel.AbstractMapConfigurationEntity;
import com.emergya.persistenceGeo.metaModel.AbstractPermissionEntity;
import com.emergya.persistenceGeo.metaModel.AbstractResourceEntity;
import com.emergya.persistenceGeo.metaModel.AbstractRuleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractRulePropertyEntity;
import com.emergya.persistenceGeo.metaModel.AbstractStyleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;
import com.emergya.persistenceGeo.metaModel.AbstractZoneEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * Interface implementation for example mapping
 * 
 * @author <a href="mailto:adiaz@emergya.com">adiaz</a>
 * 
 */
public class InstancerImpl implements Instancer {

	public AbstractAuthorityEntity createAuthority() {
		return new AuthorityEntity();
	}

	public AbstractAuthorityTypeEntity createAuthorityTypeEntity() {
		return new AuthorityTypeEntity();
	}

	public AbstractFolderEntity createFolder() {
		return new FolderEntity();
	}

	public AbstractLayerEntity createLayer() {
		return new LayerEntity();
	}

	public AbstractPermissionEntity createPermission() {
		return new PermissionEntity();
	}

	public AbstractRuleEntity createRule() {
		return new RuleEntity();
	}

	public AbstractStyleEntity createStyle() {
		return new StyleEntity();
	}

	public AbstractUserEntity createUser() {
		return new UserEntity();
	}
	
	public AbstractZoneEntity createZone() {
		return new ZoneEntity();
	}

	public AbstractLayerPropertyEntity createLayerProperty() {
		return new LayerPropertyEntity();
	}
	

	public AbstractLayerTypeEntity createLayerType(){
		return new LayerTypeEntity();
	}

	
	public AbstractMapConfigurationEntity createMapConfiguration(){
		return new MapConfigurationEntity();
	}
	
	public AbstractRulePropertyEntity createRulePropertyEntity(){
		return new RulePropertyEntity();
	}
	
	public AbstractResourceEntity createResourceEntity(){
		return new ResourceEntity();
	}

	public AbstractFolderTypeEntity createFolderType() {
		return new FolderTypeEntity();
	}
}
