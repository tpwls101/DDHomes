package com.ssafy.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.apt.model.ImgInfoDto;

/**
 * 파일 저장 위한 유틸
 */
@Component
public class FileStore {

	// 게시판 이미지 저장 경로(ref: application.properties)
	@Value("${file.path.board-images}")
	private String uploadImagePath;
	
	public ImgInfoDto storeImg(MultipartFile multipartFile) throws IOException {
		if(multipartFile.isEmpty()) {
			return null;
		}
		
		String today = new SimpleDateFormat("yyMMdd").format(new Date());
		String saveFolder = uploadImagePath + File.separator + today;
		File folder = new File(saveFolder);
		if (!folder.exists())
			folder.mkdirs();
		
		ImgInfoDto imgInfoDto = new ImgInfoDto();
		String originalFileName = multipartFile.getOriginalFilename();
		if (!originalFileName.isEmpty()) {
			String saveFileName = UUID.randomUUID().toString()
					+ originalFileName.substring(originalFileName.lastIndexOf('.'));
			imgInfoDto.setSaveFolder(today);
			imgInfoDto.setOriginalName(originalFileName);
			imgInfoDto.setSaveName(saveFileName);
			// 파일 실제 저장
			multipartFile.transferTo(new File(folder, saveFileName));
		}
		
		return imgInfoDto;
	}
	
	public List<ImgInfoDto> storeImgs(List<MultipartFile> multipartFiles) throws IOException {
		List<ImgInfoDto> fileInfos = new ArrayList<ImgInfoDto>();
		for(MultipartFile multipartFile : multipartFiles) {
			if(!multipartFile.isEmpty()) {
				fileInfos.add(storeImg(multipartFile));
			}
		}
		
		return fileInfos;
	}
	
//	private String extractExt(String originalFilename) {
//		int pos = originalFilename.lastIndexOf(".");
//		return originalFilename.substring(pos + 1);
//	}
	
}
