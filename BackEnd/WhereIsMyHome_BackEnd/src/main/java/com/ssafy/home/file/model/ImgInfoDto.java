package com.ssafy.home.file.model;

public class ImgInfoDto {
	private int imgInfoNo;
	private int forsaleNo;
	private int articleNo;
	private String saveFolder;
	private String originalName;
	private String saveName;
	
	
	public int getImgInfoNo() {
		return imgInfoNo;
	}
	public void setImgInfoNo(int imgInfoNo) {
		this.imgInfoNo = imgInfoNo;
	}
	public int getForsaleNo() {
		return forsaleNo;
	}
	public void setForsaleNo(int forsaleNo) {
		this.forsaleNo = forsaleNo;
	}
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getSaveFolder() {
		return saveFolder;
	}
	public void setSaveFolder(String saveFolder) {
		this.saveFolder = saveFolder;
	}
	public String getOriginalName() {
		return originalName;
	}
	public void setOriginalName(String originalName) {
		this.originalName = originalName;
	}
	public String getSaveName() {
		return saveName;
	}
	public void setSaveName(String saveName) {
		this.saveName = saveName;
	}
	
	
	@Override
	public String toString() {
		return "ImgInfoDto [imgInfoNo=" + imgInfoNo + ", forsaleNo=" + forsaleNo + ", articleNo=" + articleNo
				+ ", saveFolder=" + saveFolder + ", originalName=" + originalName + ", saveName=" + saveName + "]";
	}
	
}
