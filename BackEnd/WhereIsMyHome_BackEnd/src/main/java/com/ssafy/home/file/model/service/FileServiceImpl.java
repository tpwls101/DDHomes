package com.ssafy.home.file.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.file.model.ImgInfoDto;
import com.ssafy.home.file.model.mapper.FileMapper;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	FileMapper fileMapper;

	@Override
	public List<ImgInfoDto> getBoardImgInfo(int articleNo) throws Exception {
		return fileMapper.getBoardImgInfo(articleNo);
	}
	
	@Override
	public List<ImgInfoDto> getForsaleImgInfo(int forsaleNo) throws Exception {
		return fileMapper.getForsaleImgInfo(forsaleNo);
	}

	@Override
	public void deleteImg(int imginfoNo) throws Exception {
		fileMapper.deleteImg(imginfoNo);
	}

}
