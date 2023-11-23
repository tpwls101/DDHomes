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
	public String getSelectorItems(Map<String, String> map) throws Exception {
		// Mapper에게 넘길 파라미터
		Map<String, String> param = new HashMap<String, String>();
		
		String selector = "";
		String selectorId = map.get("selectorId");
		String previousSelectedValue = map.get("previousSelectedValue");
		
		switch(selectorId) {
		case "sido":
			selector = "sidoName";
			break;
		case "gugun":
			selector = "gugunName";
			previousSelectedValue = previousSelectedValue.substring(0, 2);
			break;
		case "dong":
			selector = "dongName";
			previousSelectedValue = previousSelectedValue.substring(0, 4);
			break;
		}
		
		param.put("selector", selector);
		param.put("previousSelectedValue", previousSelectedValue);
		
		// DB에서 리스트 받아오기
		List<Map<String, Object>> list = aptMapper.getSelectorItems(param);
		
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
	public List<HouseDealDto> getHouseDealInfo(String aptCode) throws Exception {
		return aptMapper.getHouseDealInfo(aptCode);
	}
	
}
