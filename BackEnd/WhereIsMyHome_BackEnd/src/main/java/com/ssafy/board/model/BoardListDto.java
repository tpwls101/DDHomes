package com.ssafy.board.model;

import java.util.List;

import com.ssafy.util.PageNavigation;

public class BoardListDto {
	
	private List<BoardDto> articleList;
	private String boardType;
	private PageNavigation pageNavigation;
	
	
	public List<BoardDto> getArticleList() {
		return articleList;
	}
	public void setArticleList(List<BoardDto> articleList) {
		this.articleList = articleList;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	public PageNavigation getPageNavigation() {
		return pageNavigation;
	}
	public void setPageNavigation(PageNavigation pageNavigation) {
		this.pageNavigation = pageNavigation;
	}
	
	
	@Override
	public String toString() {
		return "BoardListDto [articleList=" + articleList + ", boardType=" + boardType + ", pageNavigation="
				+ pageNavigation + "]";
	}
	
}
