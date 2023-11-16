package com.ssafy.apt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.apt.model.HouseDealDto;
import com.ssafy.apt.model.HouseInfoDto;
import com.ssafy.apt.model.service.AptService;

@Controller
@RequestMapping("/apt")
public class AptController {

	@Autowired
	AptService aptService;
	
	@GetMapping("/getSelector")
	@ResponseBody
	public String getSelector(String selectorId, String selectedValue) {
		String jsonStr = null;
		try {
			jsonStr = aptService.getSelectorItems(selectorId, selectedValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonStr;
	}
	
	@GetMapping("/info")
	public String list(String selectedValue, Model model) {
		try {
			List<HouseInfoDto> list = aptService.getHouseInfo(selectedValue);
			model.addAttribute("selectedValue", selectedValue);
			model.addAttribute("houseinfoList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "apt/info";
	}
	
	@GetMapping("/deal")
	public String deal(String aptCode, Model model) {
		try {
			List<HouseDealDto> list = aptService.getHouseDeal(aptCode);
			model.addAttribute("aptCode", aptCode);
			model.addAttribute("housedealList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "apt/deal";
	}
	
	@GetMapping("/infolist/{dongCode}")
	@ResponseBody
	public ResponseEntity<?> infolist(@PathVariable("dongCode") String dongCode) {
		System.out.println(dongCode);
		
		return new ResponseEntity<String>(dongCode, HttpStatus.OK);
	}
}