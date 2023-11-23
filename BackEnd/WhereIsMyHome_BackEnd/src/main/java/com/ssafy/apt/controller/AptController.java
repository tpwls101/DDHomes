package com.ssafy.apt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.apt.model.HouseDealDto;
import com.ssafy.apt.model.HouseInfoDto;
import com.ssafy.apt.model.service.AptService;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/apt")
public class AptController {

	@Autowired
	AptService aptService;
	
//	@GetMapping("/getSelector")
//	@ResponseBody
//	public String getSelector(String selectorId, String selectedValue) {
//		String jsonStr = null;
//		try {
//			jsonStr = aptService.getSelectorItems(selectorId, selectedValue);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return jsonStr;
//	}
	
	/**
	 * 내가 선택한 select와 option에 대한 json 결과값 받기
	 */
	@GetMapping("/getSelector")
	@ResponseBody
	public String getSelector(@RequestParam Map<String, String> map) {
		// selectorId: 설정할 셀렉터 id 속성
		// previousSelectedValue: 이전 셀렉터 선택값
		System.out.println("넘어온 parameter 확인 : " + map.toString());
		
		String jsonStr = null;
		try {
			jsonStr = aptService.getSelectorItems(map);
			System.out.println(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonStr;
	}
	
	
	/**
	 * 선택한 아파트의 거래 내역 구하기
	 */
	@GetMapping("/getHouseDealInfo/{aptCode}")
	public ResponseEntity<?> getHouseDealInfo(@PathVariable String aptCode) {
		try {
			System.out.println("?????????!?!?!?!?");
			List<HouseDealDto> list = aptService.getHouseDealInfo(aptCode);
			return new ResponseEntity<List<HouseDealDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
//	@GetMapping("/info")
//	public String list(String selectedValue, Model model) {
//		try {
//			List<HouseInfoDto> list = aptService.getHouseInfo(selectedValue);
//			model.addAttribute("selectedValue", selectedValue);
//			model.addAttribute("houseinfoList", list);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return "apt/info";
//	}
	
	
//	@GetMapping("/deal")
//	public String deal(String aptCode, Model model) {
//		try {
//			List<HouseDealDto> list = aptService.getHouseDeal(aptCode);
//			model.addAttribute("aptCode", aptCode);
//			model.addAttribute("housedealList", list);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return "apt/deal";
//	}
	
//	@GetMapping("/infolist/{dongCode}")
//	@ResponseBody
//	public ResponseEntity<?> infolist(@PathVariable("dongCode") String dongCode) {
//		System.out.println(dongCode);
//		
//		return new ResponseEntity<String>(dongCode, HttpStatus.OK);
//	}
}
