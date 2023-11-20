package com.ssafy.home.file.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.home.file.model.ImgInfoDto;

public interface FileService {

	public List<ImgInfoDto> getBoardImgInfo(int articleNo) throws Exception;
	void deleteImg(int imginfoNo) throws Exception;
}
