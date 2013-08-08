/*
 * SourceToolEntity.java Copyright (C) 2013. This file is part of persistenceGeo project
 * 
 * This software is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General public abstract License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * This software is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General public abstract License for more
 * details.
 * 
 * You should have received a copy of the GNU General public abstract License along with
 * this library; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 * 
 * As a special exception, if you link this library with other files to produce
 * an executable, this library does not by itself cause the resulting executable
 * to be covered by the GNU General public abstract License. This exception does not
 * however invalidate any other reasons why the executable file might be covered
 * by the GNU General public abstract License.
 * 
 * Authors:: Alejandro Diaz Torres (mailto:adiaz@emergya.com)
 */
package com.emergya.persistenceGeo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.emergya.persistenceGeo.metaModel.AbstractSourceToolEntity;

/**
 * Source tool entity
 * 
 * @author <a href="mailto:adiaz@emergya.com">Alejandro Diaz</a>
 *
 */
@Entity
@Table(name = "gis_source")
public class SourceToolEntity extends AbstractSourceToolEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8589367040814737687L;

	public SourceToolEntity(){
		
	}

	@Id
    @Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gis_source_seq")
    @SequenceGenerator(name="gis_source_seq", sequenceName = "gis_source_seq", 
    				initialValue=100)
	public Long getId() {
		return id;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	@Column(name = "url")
	public String getUrl() {
		return url;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	@Column(name = "ptype")
	public String getPtype() {
		return ptype;
	}

	@Column(name = "config")
	public String getConfig() {
		return config;
	}

}
