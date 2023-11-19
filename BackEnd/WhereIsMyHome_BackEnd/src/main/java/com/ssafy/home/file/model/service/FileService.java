package com.ssafy.home.file.model.service;

import java.util.List;

import com.ssafy.home.file.model.ImgInfoDto;

public interface FileService {

	public List<ImgInfoDto> getBoardImgInfo(int articleNo) throws Exception;
}
