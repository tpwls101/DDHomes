package com.ssafy.apt.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.apt.model.HouseDealDto;
import com.ssafy.apt.model.HouseInfoDto;
import com.ssafy.apt.model.mapper.AptMapper;

@Service
public class AptServiceImpl implements AptService {
	
	@Autowired
	AptMapper aptMapper;

	@Override
	public String getSelectorItems(String selectorId, String selectedValue) throws Exception {
		// Mapper에게 넘길 파라미터
		Map<String, String> map = new HashMap<String, String>();
		String selector = "";
		
		switch(selectorId) {
		case "sido":
			selector = "sidoName";
			break;
		case "gugun":
			selector = "gugunName";
			selectedValue = selectedValue.substring(0, 2);
			break;
		case "dong":
			selector = "dongName";
			selectedValue = selectedValue.substring(0, 4);
			break;
		}
		
		map.put("selector", selector);
		map.put("selectedValue", selectedValue);
		
		// DB에서 리스트 받아오기
		List<Map<String, Object>> list = aptMapper.getSelectorItems(map);
		
		// json형태의 문자열로 변환
		ObjectMapper om = new ObjectMapper();
		String jsonStr = om.writeValueAsString(list);
		
		return jsonStr;
	}

	@Override
	public List<HouseInfoDto> getHouseInfo(String dongCode) throws Exception {
		return aptMapper.getHouseInfo(dongCode);
	}

	@Override
	public List<HouseDealDto> getHouseDeal(String aptCode) throws Exception {
		return aptMapper.getHouseDeal(aptCode);
	}
	
}
