package com.emergya.persistenceGeo.bean;

import java.io.Serializable;

import com.emergya.persistenceGeo.dto.RegionDto;

public class RegionBean implements Serializable {

	private static final long serialVersionUID = 3923356783881257196L;

	private Long id;
	private String name_region;
	private String prefix_wks;
	private String node_analytics;
	private String node_publicacion;

	public RegionBean() {
	}

	public RegionBean(Long id, String name_region, String prefix_wks,
			String node_analytics, String node_publicacion) {
		super();
		this.id = id;
		this.name_region = name_region;
		this.prefix_wks = prefix_wks;
		this.node_analytics = node_analytics;
		this.node_publicacion = node_publicacion;
	}

	public void loadRegion(RegionDto regionDto) {
		this.id = regionDto.getId();
		this.name_region = regionDto.getName_region();
		this.prefix_wks = regionDto.getPrefix_wks();
		this.node_analytics = regionDto.getNode_analytics();
		this.node_publicacion = regionDto.getNode_publicacion();
	}

	public static RegionDto getRegionDto(RegionBean regBean) {

		RegionDto regionDto = new RegionDto();

		regionDto.setId(regBean.getId());
		regionDto.setName_region(regBean.getName_region());
		regionDto.setPrefix_wks(regBean.getPrefix_wks());
		regionDto.setNode_analytics(regBean.getNode_analytics());
		regionDto.setNode_publicacion(regBean.getNode_publicacion());

		return regionDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName_region() {
		return name_region;
	}

	public void setName_region(String name_region) {
		this.name_region = name_region;
	}

	public String getPrefix_wks() {
		return prefix_wks;
	}

	public void setPrefix_wks(String prefix_wks) {
		this.prefix_wks = prefix_wks;
	}

	public String getNode_analytics() {
		return node_analytics;
	}

	public void setNode_analytics(String node_analytics) {
		this.node_analytics = node_analytics;
	}

	public String getNode_publicacion() {
		return node_publicacion;
	}

	public void setNode_publicacion(String node_publicacion) {
		this.node_publicacion = node_publicacion;
	}
}
