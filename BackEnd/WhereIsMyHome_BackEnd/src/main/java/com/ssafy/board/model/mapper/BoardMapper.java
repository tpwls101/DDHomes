package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardCommentDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.home.file.model.ImgInfoDto;

@Mapper
public interface BoardMapper {
	
	int writeArticle(BoardDto boardDto) throws SQLException;
	List<BoardDto> listArticle(Map<String, String> map) throws SQLException;
	int getTotalArticleCount(Map<String, String> map) throws SQLException;
	BoardDto getArticle(int articleNo) throws SQLException;
	int updateHit(int articleNo) throws SQLException;
	int modifyArticle(BoardDto boardDto) throws SQLException;
	int deleteArticle(int articleNo) throws SQLException;
	String getBoardType(int articleNo) throws SQLException;
	
	int writeComment(BoardCommentDto boardCommentDto) throws SQLException;
	List<BoardCommentDto> listComment(int articleNo) throws SQLException;
	int likeComment(Map<String, Object> map) throws SQLException;
	int modifyComment(Map<String, String> map) throws SQLException;
	int deleteComment(int commentNo) throws SQLException;
	
}
