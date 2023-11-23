package com.ssafy.apt.model.service;

import java.sql.SQLException;
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
	 * 선택한 아파트의 거래 내역 구하기
	 */
	public List<HouseDealDto> getHouseDealInfo(String aptCode) throws Exception;
}
