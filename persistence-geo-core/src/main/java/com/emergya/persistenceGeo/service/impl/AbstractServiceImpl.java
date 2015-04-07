/**
 * 
 */
package com.emergya.persistenceGeo.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.MultiSirDatabaseGenericDAO;
import com.emergya.persistenceGeo.service.AbstractService;

/**
 * @author adiaz
 *
 */
@Transactional(value = "multiSIRDatabaseTransactionManager")
public abstract class AbstractServiceImpl<DTO extends Serializable, ENTITY extends Serializable>
		implements AbstractService {

	protected abstract MultiSirDatabaseGenericDAO<ENTITY, Long> getDao();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emergya.persistenceGeo.service.AbstractService#getAll()
	 */
	@Override
	public List<? extends Serializable> getAll() {
		List<ENTITY> entities = getDao().findAll();
		return entitiesToDtos(entities);
	}

	public Serializable getById(Long id) {
		return entityToDto(getDao().findById(id, false));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.emergya.persistenceGeo.service.AbstractService#getFromTo(java.lang
	 * .Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends Serializable> getFromTo(Integer first, Integer last) {
		List<ENTITY> entities = getDao().findAllFromTo(first, last);
		return entitiesToDtos(entities);
	}

	@Override
	public List<? extends Serializable> getOrdered(Integer first, Integer last,
			String fieldName, boolean asc) {
		List<ENTITY> entities = getDao().findOrdered(first, last, fieldName,
				asc);
		return entitiesToDtos(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.emergya.persistenceGeo.service.AbstractService#getResults()
	 */
	@Override
	public Long getResults() {
		return getDao().getResults();
	}

	protected List<? extends Serializable> entitiesToDtos(List<ENTITY> entities) {
		List<DTO> dtos = new ArrayList<DTO>(entities.size());
		for (ENTITY entity : entities) {
			dtos.add(entityToDto(entity));
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	public Serializable create(Serializable dto) {
		return entityToDto(getDao().makePersistent(dtoToEntity((DTO) dto)));
	}

	@SuppressWarnings("unchecked")
	public Serializable update(Serializable dto) {
		return entityToDto(getDao().makePersistent(dtoToEntity((DTO) dto)));
	}

	@SuppressWarnings("unchecked")
	public void delete(Serializable dto) {
		getDao().makeTransient((dtoToEntity((DTO) dto)));
	}

	protected abstract DTO entityToDto(ENTITY entity);

	protected abstract ENTITY dtoToEntity(DTO dto);

}
