package com.ssafy.apt.model;

public class FavoriteDto {
	private int favoriteNo;
	private String userId;
	private int forsaleNo;
	
	
	public int getFavoriteNo() {
		return favoriteNo;
	}
	public void setFavoriteNo(int favoriteNo) {
		this.favoriteNo = favoriteNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getForsaleNo() {
		return forsaleNo;
	}
	public void setForsaleNo(int forsaleNo) {
		this.forsaleNo = forsaleNo;
	}
	
	
	@Override
	public String toString() {
		return "FavoriteDto [favoriteNo=" + favoriteNo + ", userId=" + userId + ", forsaleNo=" + forsaleNo + "]";
	}
	
}
