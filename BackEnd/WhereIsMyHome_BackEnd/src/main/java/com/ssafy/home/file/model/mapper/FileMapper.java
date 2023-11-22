package com.ssafy.home.file.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.BoardDto;
import com.ssafy.home.file.model.ImgInfoDto;

@Mapper
public interface FileMapper {

	List<ImgInfoDto> getBoardImgInfo(int articleNo) throws SQLException;
	List<ImgInfoDto> getForsaleImgInfo(int forsaleNo) throws SQLException;
	int uploadBoardImg(List<ImgInfoDto> imgInfos) throws SQLException;
	int uploadForsaleImg(List<ImgInfoDto> imgInfos) throws SQLException;
	void deleteImg(int imginfoNo) throws SQLException;
	
}
