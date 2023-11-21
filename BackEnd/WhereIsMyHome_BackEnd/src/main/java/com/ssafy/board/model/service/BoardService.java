package com.ssafy.board.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.board.model.BoardCommentDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.home.file.model.ImgInfoDto;
import com.ssafy.util.PageNavigation;

public interface BoardService {
	
	void writeArticle(BoardDto boardDto) throws Exception;
	List<BoardDto> listArticle(Map<String, String> map, String sortCondition) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	BoardDto getArticle(int articleNo) throws Exception;
	int updateHit(int articleNo) throws Exception;
	void modifyArticle(BoardDto boardDto) throws Exception;
	int deleteArticle(int articleNo) throws Exception;
	String getBoardType(int articleNo) throws Exception;
	
	// comment
	int writeComment(BoardCommentDto boardCommentDto) throws Exception;
	List<BoardCommentDto> listComment(int articleNo) throws Exception;
	int likeComment(Map<String, Object> map) throws Exception;
	int modifyComment(Map<String, String> map) throws Exception;
	int deleteComment(int commentNo) throws Exception;
}
