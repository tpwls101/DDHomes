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

import com.ssafy.home.file.model.ImgInfoDto;

/**
 * 파일 저장 위한 유틸
 */
@Component
public class FileUtil {

	// 게시판 이미지 저장 경로(ref: application.properties)
	@Value("${file.path}")
	private String uploadImagePath;

	// 이미지 저장 및 dto변환
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
	
	// 이미지 저장 및 dto변환
	public List<ImgInfoDto> storeImgs(List<MultipartFile> multipartFiles) throws IOException {
		List<ImgInfoDto> fileInfos = new ArrayList<ImgInfoDto>();
		for(MultipartFile multipartFile : multipartFiles) {
			if(!multipartFile.isEmpty()) {
				fileInfos.add(storeImg(multipartFile));
			}
		}
		
		return fileInfos;
	}
	
	// 파일 삭제
	public void deleteImg(List<ImgInfoDto> imgInfos) {
		for(ImgInfoDto imgInfo : imgInfos) {
			File file = new File(uploadImagePath + File.separator + imgInfo.getSaveFolder() + File.separator + imgInfo.getSaveName());
			if(file.exists()) {
				file.delete();
			}
		}
		
	}
	
//	private String extractExt(String originalFilename) {
//		int pos = originalFilename.lastIndexOf(".");
//		return originalFilename.substring(pos + 1);
//	}
	
}
