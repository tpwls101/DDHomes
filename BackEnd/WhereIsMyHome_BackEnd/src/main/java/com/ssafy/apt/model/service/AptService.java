package com.ssafy.apt.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.apt.model.HouseDealDto;
import com.ssafy.apt.model.HouseInfoDto;

public interface AptService {

	/**
	 * 셀렉터 아이템 얻어오기
	 */
	public String getSelectorItems(Map<String, String> map) throws Exception;
	
	/**
	 * dongCode로 house info 얻기
	 */
	public List<HouseInfoDto> getHouseInfo(String dongCode) throws Exception;
	
	/**
	 * aptCode로 house deal 얻기
	 */
	public List<HouseDealDto> getHouseDeal(String aptCode) throws Exception;
}
