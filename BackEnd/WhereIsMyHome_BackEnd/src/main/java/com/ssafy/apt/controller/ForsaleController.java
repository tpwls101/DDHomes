package com.ssafy.apt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.apt.model.ForsaleDto;
import com.ssafy.apt.model.HouseInfoDto;
import com.ssafy.apt.model.service.ForsaleService;
import com.ssafy.home.file.model.ImgInfoDto;
import com.ssafy.util.FileUtil;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/forsale")
public class ForsaleController {
	
	@Autowired
	private ForsaleService forsaleService;
	
	@Autowired
	private FileUtil fileUtil;

	/**
	 * 글쓰기 + 파일 업로드
	 */
	@PostMapping("/regist")
	public ResponseEntity<?> write(MultipartHttpServletRequest formData) {
		try {
			// forsaleDto(JSON문자열)를 ForsaleDto객체로 변환(jackson)
			ObjectMapper objectMapper = new ObjectMapper();
			ForsaleDto forsaleDto = objectMapper.readValue(formData.getParameter("forsaleDto"), ForsaleDto.class);
			// 파일들 받아오기
			List<MultipartFile> multipartFiles = formData.getFiles("imgInfos");
			
			// 파일들 저장 및 dto 리스트로 변환
			List<ImgInfoDto> imgInfos = fileUtil.storeImgs(multipartFiles);
			
			// dto에 파일 dto 리스트 추가
			forsaleDto.setImgInfos(imgInfos);
			
			// 서비스 호출
			forsaleService.registForsale(forsaleDto);
	
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("forsale uploadImg Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 아파트 검색 - 아파트 이름으로 HouseInfoDto 불러옴
	 */
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam Map<String, String> params) {
		try {
			List<HouseInfoDto> list = forsaleService.searchHouseInfo(params);
			
			return new ResponseEntity<List<HouseInfoDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("forsale search Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 아파트 검색 - 아파트 코드로 최근 2년 거래가격 평균 불러옴
	 */
	@GetMapping("/avgDealAmount/{aptCode}")
	public ResponseEntity<?> getAvgDealAmount(@PathVariable String aptCode) {
		try {
			Integer avgDealAmount = forsaleService.getAvgDealAmount(aptCode);
			if(avgDealAmount == null) {
				avgDealAmount = 0;
			}
			
			return new ResponseEntity<Integer>(avgDealAmount, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("forsale avgDealAmount Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
