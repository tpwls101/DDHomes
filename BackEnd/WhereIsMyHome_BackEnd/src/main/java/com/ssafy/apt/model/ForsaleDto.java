package com.ssafy.apt.model;

import java.util.List;

import com.ssafy.home.file.model.ImgInfoDto;

public class ForsaleDto {
	
	private int forsaleNo;
	private String aptCode;
	private String dongCode;
	private String apartmentName;
	private String sidoName;
	private String gugunName;
	private String dong;
	private String jibun;
	private String roadName;
	private String roadNameBonBun;
	private String userId;
	private String price;
	private int buildYear;
	private String lat;
	private String lng;
	private boolean isFavorite;
	private List<ImgInfoDto> imgInfos;
	
	
	public int getForsaleNo() {
		return forsaleNo;
	}
	public void setForsaleNo(int forsaleNo) {
		this.forsaleNo = forsaleNo;
	}
	public String getAptCode() {
		return aptCode;
	}
	public void setAptCode(String aptCode) {
		this.aptCode = aptCode;
	}
	public String getDongCode() {
		return dongCode;
	}
	public void setDongCode(String dongCode) {
		this.dongCode = dongCode;
	}
	public String getApartmentName() {
		return apartmentName;
	}
	public void setApartmentName(String apartmentName) {
		this.apartmentName = apartmentName;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public boolean isFavorite() {
		return isFavorite;
	}
	public void setFavorite(boolean isFavorite) {
		this.isFavorite = isFavorite;
	}
	public List<ImgInfoDto> getImgInfos() {
		return imgInfos;
	}
	public void setImgInfos(List<ImgInfoDto> imgInfos) {
		this.imgInfos = imgInfos;
	}
	
	
	@Override
	public String toString() {
		return "ForsaleDto [forsaleNo=" + forsaleNo + ", aptCode=" + aptCode + ", dongCode=" + dongCode
				+ ", apartmentName=" + apartmentName + ", sidoName=" + sidoName + ", gugunName=" + gugunName + ", dong="
				+ dong + ", jibun=" + jibun + ", roadName=" + roadName + ", roadNameBonBun=" + roadNameBonBun
				+ ", userId=" + userId + ", price=" + price + ", buildYear=" + buildYear + ", lat=" + lat + ", lng="
				+ lng + ", isFavorite=" + isFavorite + ", imgInfos=" + imgInfos + "]";
	}
	
}
