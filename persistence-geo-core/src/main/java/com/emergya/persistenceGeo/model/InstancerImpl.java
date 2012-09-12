/**
 * 
 */
package com.emergya.persistenceGeo.model;

import com.emergya.persistenceGeo.metaModel.AbstractAuthorityEntity;
import com.emergya.persistenceGeo.metaModel.AbstractAuthorityTypeEntity;
import com.emergya.persistenceGeo.metaModel.AbstractFolderEntity;
import com.emergya.persistenceGeo.metaModel.AbstractLayerEntity;
import com.emergya.persistenceGeo.metaModel.AbstractPermissionEntity;
import com.emergya.persistenceGeo.metaModel.AbstractRuleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractStyleEntity;
import com.emergya.persistenceGeo.metaModel.AbstractUserEntity;
import com.emergya.persistenceGeo.metaModel.AbstractZoneEntity;
import com.emergya.persistenceGeo.metaModel.Instancer;

/**
 * @author adiaz
 *
 */
public class InstancerImpl implements Instancer {

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.metaModel.Instancer#createAuthority()
	 */
	@Override
	public AbstractAuthorityEntity createAuthority() {
		return new AuthorityEntity();
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.metaModel.Instancer#createAuthorityTypeEntity()
	 */
	@Override
	public AbstractAuthorityTypeEntity createAuthorityTypeEntity() {
		return new AuthorityTypeEntity();
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.metaModel.Instancer#createFolder()
	 */
	@Override
	public AbstractFolderEntity createFolder() {
		return new FolderEntity();
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.metaModel.Instancer#createLayer()
	 */
	@Override
	public AbstractLayerEntity createLayer() {
		return new LayerEntity();
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.metaModel.Instancer#createPermission()
	 */
	@Override
	public AbstractPermissionEntity createPermission() {
		return new PermissionEntity();
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.metaModel.Instancer#createRule()
	 */
	@Override
	public AbstractRuleEntity createRule() {
		return new RuleEntity();
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.metaModel.Instancer#createStyle()
	 */
	@Override
	public AbstractStyleEntity createStyle() {
		return new StyleEntity();
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.metaModel.Instancer#createUser()
	 */
	@Override
	public AbstractUserEntity createUser() {
		return new UserEntity();
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.metaModel.Instancer#createZone()
	 */
	@Override
	public AbstractZoneEntity createZone() {
		return new ZoneEntity();
	}

}
