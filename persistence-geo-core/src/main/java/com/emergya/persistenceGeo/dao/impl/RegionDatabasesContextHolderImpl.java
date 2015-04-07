package com.emergya.persistenceGeo.dao.impl;

import com.emergya.persistenceGeo.dao.MultiSirRegionDatabase;
import com.emergya.persistenceGeo.dao.RegionDatabasesContextHolder;

public class RegionDatabasesContextHolderImpl implements
		RegionDatabasesContextHolder {

	private static final ThreadLocal<MultiSirRegionDatabase> contextHolder = new ThreadLocal<MultiSirRegionDatabase>();

	@Override
	public void setMultiSirRegionDatabase(
			MultiSirRegionDatabase multiSirRegionDatabase) {
		contextHolder.set(multiSirRegionDatabase);
	}

	@Override
	public MultiSirRegionDatabase getMultiSirRegionDatabase() {
		return (MultiSirRegionDatabase) contextHolder.get();
	}

	@Override
	public void clearMultiSirRegionDatabase() {
		contextHolder.remove();
	}

	@Override
	public void setMultiSirRegionBeanDatabase(final Long idRegion) {

		if (idRegion != null) {

			switch (idRegion.intValue()) {

			case 1:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.TARAPACA);
				break;
			case 2:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.ANTOFAGASTA);
				break;
			case 3:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.ATACAMA);
				break;
			case 4:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.COQUIMBO);
				break;
			case 5:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.VALPARAISO);
				break;
			case 6:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.OHIGGINS);
				break;
			case 7:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.MAULE);
				break;
			case 8:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.BIOBIO);
				break;
			case 9:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.ARAUCANIA);
				break;
			case 10:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.LAGOS);
				break;
			case 11:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.AISEN);
				break;
			case 12:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.MAGALLANES);
				break;
			case 13:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.SANTIAGO);
				break;
			case 14:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.RIOS);
				break;
			case 15:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.ARICA);
				break;
			default:
				this.setMultiSirRegionDatabase(MultiSirRegionDatabase.DEFAULT);
				break;
			}
		} else {
			this.setMultiSirRegionDatabase(MultiSirRegionDatabase.DEFAULT);
		}
	}
}
