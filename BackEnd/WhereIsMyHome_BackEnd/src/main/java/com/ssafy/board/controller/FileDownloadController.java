package com.ssafy.board.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
public class FileDownloadController {

	// 게시판 이미지 저장 경로(ref: application.properties)
	@Value("${file.path.board-images}")
	private String uploadImagePath;
	
	@GetMapping("/getImg/{saveFolder}/{originalName}/{saveName}")
	public ResponseEntity<Object> getImg(@PathVariable("saveFolder") String saveFolder, 
											@PathVariable("originalName") String originalName, 
											@PathVariable("saveName") String saveName)  {
		
		String file = uploadImagePath + File.pathSeparator + saveFolder + File.separator + saveName;
		
		try {
			Path filePath = Paths.get(file);
			Resource resource = new InputStreamResource(Files.newInputStream(filePath));
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(ContentDisposition.builder("attachment").filename(URLEncoder.encode(originalName, "UTF-8").replaceAll("//+", "%20")).build());
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			
			return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
			
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		
	}
	
}
