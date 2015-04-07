package com.emergya.persistenceGeo.dao;

public interface RegionDatabasesContextHolder {

	/**
	 * Sets the MultiSirRegionDatabase to be used for Multi-SIR persistence
	 * layer
	 * 
	 * @param multiSirRegionDatabase
	 *            MultiSirRegionDatabase
	 */
	public void setMultiSirRegionDatabase(
			MultiSirRegionDatabase multiSirRegionDatabase);

	/**
	 * Gets the MultiSirRegionDatabase to use for Multi-SIR persistence layer
	 * 
	 * @return MultiSirRegionDatabase
	 */
	public MultiSirRegionDatabase getMultiSirRegionDatabase();

	/**
	 * Clears this context holder
	 */
	public void clearMultiSirRegionDatabase();

	/**
	 * Sets the MultiSirRegionDatabase to be used for Multi-SIR persistence
	 * layer
	 * 
	 * @param idRegion
	 *            Long
	 */
	public void setMultiSirRegionBeanDatabase(Long idRegion);
}
