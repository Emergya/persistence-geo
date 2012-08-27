/**
 * 
 */
package com.emergya.persistenceGeo.service.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.emergya.persistenceGeo.dao.GenericDAO;
import com.emergya.persistenceGeo.service.AbstractService;

/**
 * @author adiaz
 *
 */
@Transactional
public abstract class AbstractServiceImpl<DTO extends Serializable, ENTITY extends Serializable> implements AbstractService {
	
	@Resource(name="userEntityDao")
	private GenericDAO<ENTITY, Long> dao; 

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.service.AbstractService#getAll()
	 */
	@Override
	public List<? extends Serializable> getAll() {
		List<ENTITY> entities = dao.findAll();
		return entitiesToDtos(entities);
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.service.AbstractService#getFromTo(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<? extends Serializable> getFromTo(Integer first, Integer last) {
		List<ENTITY> entities = dao.findAllFromTo(first, last);
		return entitiesToDtos(entities);
	}

	/* (non-Javadoc)
	 * @see com.emergya.persistenceGeo.service.AbstractService#getResults()
	 */
	@Override
	public Long getResults() {
		return dao.getResults();
	}
	
	protected List<? extends Serializable> entitiesToDtos(List<ENTITY> entities) {
		List<DTO> dtos = new LinkedList<DTO>();
		for (ENTITY entity: entities){
			dtos.add(entityToDto(entity));
		}
		return dtos;
	}
	
	@SuppressWarnings("unchecked")
	public Serializable create(Serializable dto){
		return entityToDto(dao.makePersistent(dtoToEntity((DTO) dto)));
	}
	
	@SuppressWarnings("unchecked")
	public Serializable update(Serializable dto){
		return entityToDto(dao.makePersistent(dtoToEntity((DTO) dto)));
	}
	
	@SuppressWarnings("unchecked")
	public void delete(Serializable dto){
		dao.makeTransient((dtoToEntity((DTO) dto)));
	}
	
	protected abstract DTO entityToDto(ENTITY entity);
	
	protected abstract ENTITY dtoToEntity(DTO dto);

}
