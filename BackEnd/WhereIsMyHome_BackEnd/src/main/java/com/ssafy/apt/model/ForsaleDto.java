package com.ssafy.apt.model;

public class ForsaleDto {
	private int forsaleNo;
	private String aptCode;
	private String userId;
	private String dealAmount;
	
	
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
	public String getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(String dealAmount) {
		this.dealAmount = dealAmount;
	}
	
	
	@Override
	public String toString() {
		return "ForsaleDto [forsaleNo=" + forsaleNo + ", aptCode=" + aptCode + ", userId=" + userId + ", dealAmount="
				+ dealAmount + "]";
	}
	
}
