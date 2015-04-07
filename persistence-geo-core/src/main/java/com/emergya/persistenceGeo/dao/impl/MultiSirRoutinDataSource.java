package com.emergya.persistenceGeo.dao.impl;

import javax.inject.Inject;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.emergya.persistenceGeo.dao.RegionDatabasesContextHolder;

public class MultiSirRoutinDataSource extends AbstractRoutingDataSource {

	@Inject
	private RegionDatabasesContextHolder regionDBContext;

	@Override
	protected Object determineCurrentLookupKey() {
		return regionDBContext.getMultiSirRegionDatabase();
	}
}
