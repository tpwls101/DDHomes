package com.ssafy.apt.model;

public class HouseInfoDto {
	private String aptCode;
	private String sigunguCode;
	private String eubmyundongCode;
	private String dongCode;
	private String sidoName;
	private String gugunName;
	private String dong;
	private String jibun;
	private String roadName;
	private String roadNameBonBun;
	private String apartmentName;
	private int buildYear;
	private String lat;
	private String lng;
	
	
	public String getAptCode() {
		return aptCode;
	}
	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}
	public String getSigunguCode() {
		return sigunguCode;
	}
	public void setSigunguCode(String sigunguCode) {
		this.sigunguCode = sigunguCode;
	}
	public String getEubmyundongCode() {
		return eubmyundongCode;
	}
	public void setEubmyundongCode(String eubmyundongCode) {
		this.eubmyundongCode = eubmyundongCode;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getSidoName() {
		return sidoName;
	}
	public void setSidoName(String sidoName) {
		this.sidoName = sidoName;
	}
	public String getGugunName() {
		return gugunName;
	}
	public void setGugunName(String gugunName) {
		this.gugunName = gugunName;
	}
	public String getDong() {
		return dong;
	}
	public void setDong(String dong) {
		this.dong = dong;
	}
	public String getJibun() {
		return jibun;
	}
	public void setJibun(String jibun) {
		this.jibun = jibun;
	}
	public String getRoadName() {
		return roadName;
	}
	public void setRoadName(String roadName) {
		this.roadName = roadName;
	}
	public String getRoadNameBonBun() {
		return roadNameBonBun;
	}
	public void setRoadNameBonBun(String roadNameBonBun) {
		this.roadNameBonBun = roadNameBonBun;
	}
	public String getApartmentName() {
		return apartmentName;
	}
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
	}
	public int getBuildYear() {
		return buildYear;
	}
	public void setBuildYear(int buildYear) {
		this.buildYear = buildYear;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	
	
	@Override
	public String toString() {
		return "HouseInfoDto [aptCode=" + aptCode + ", sigunguCode=" + sigunguCode + ", eubmyundongCode="
				+ eubmyundongCode + ", dongCode=" + dongCode + ", sidoName=" + sidoName + ", gugunName=" + gugunName
				+ ", dong=" + dong + ", jibun=" + jibun + ", roadName=" + roadName + ", roadNameBonBun="
				+ roadNameBonBun + ", apartmentName=" + apartmentName + ", buildYear=" + buildYear + ", lat=" + lat
				+ ", lng=" + lng + "]";
	}
	
}
