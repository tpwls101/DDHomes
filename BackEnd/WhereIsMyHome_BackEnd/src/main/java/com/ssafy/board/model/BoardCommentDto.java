package com.ssafy.board.model;

public class BoardCommentDto {
	
	private int commentNo;
	private int articleNo;
	private String userId;
	private String content;
	private String registerTime;
	private int like;
	
	
	public int getCommentNo() {
		return commentNo;
	}
	public void setCommentNo(int commentNo) {
		this.commentNo = commentNo;
	}
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public int getLike() {
		return like;
	}
	public void setLike(int like) {
		this.like = like;
	}
	
	
	@Override
	public String toString() {
		return "BoardCommentDto [commentNo=" + commentNo + ", articleNo=" + articleNo + ", userId=" + userId
				+ ", content=" + content + ", registerTime=" + registerTime + ", like=" + like + "]";
	}
	
}
