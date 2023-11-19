package com.ssafy.board.model;

import java.util.List;

import com.ssafy.home.file.model.ImgInfoDto;

public class BoardDto {
	private int articleNo;
	private String userId;
	private String subject;
	private String content;
	private int hit;
	private String boardType;
	private String registerTime;
	private List<ImgInfoDto> imgInfos;
	
	
	public int getArticleNo() {
		return articleNo;
	}
	public void setArticleNo(int articleNo) {
		this.articleNo = articleNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public List<ImgInfoDto> getImgInfos() {
		return imgInfos;
	}
	public void setImgInfos(List<ImgInfoDto> imgInfos) {
		this.imgInfos = imgInfos;
	}
	
	
	@Override
	public String toString() {
		return "BoardDto [articleNo=" + articleNo + ", userId=" + userId + ", subject=" + subject + ", content="
				+ content + ", hit=" + hit + ", boardType=" + boardType + ", registerTime=" + registerTime
				+ ", imgInfos=" + imgInfos + "]";
	}

}
