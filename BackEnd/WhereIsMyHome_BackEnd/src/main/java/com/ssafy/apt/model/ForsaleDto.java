package com.ssafy.apt.model;

import java.util.List;

import com.ssafy.home.file.model.ImgInfoDto;

public class ForsaleDto {
	
	private int forsaleNo;
	private String aptCode;
	private String userId;
	private String price;
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
	public List<ImgInfoDto> getImgInfos() {
		return imgInfos;
	}
	public void setImgInfos(List<ImgInfoDto> imgInfos) {
		this.imgInfos = imgInfos;
	}
	
	
	@Override
	public String toString() {
		return "ForsaleDto [forsaleNo=" + forsaleNo + ", aptCode=" + aptCode + ", userId=" + userId + ", price=" + price
				+ ", imgInfos=" + imgInfos + "]";
	}
	
}
