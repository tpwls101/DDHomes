package com.ssafy.apt.model.service;

import java.util.List;
import java.util.Map;

import com.ssafy.apt.model.ForsaleDto;
import com.ssafy.apt.model.HouseInfoDto;

public interface ForsaleService {
	/** 매물 등록하기 */
	void registForsale(ForsaleDto forsaleDto) throws Exception;
	/** 등록된 매물 리스트 조회 */
	List<ForsaleDto> forsaleList(Map<String, String> params) throws Exception;
	/** 동 이름, 아파트 이름으로 아파트 검색하기 */
	List<HouseInfoDto> searchHouseInfo(Map<String, String> params) throws Exception;
	/** 아파트 코드로 최근 2년 평균 거래가 검색하기 */
	Integer getAvgDealAmount(String aptCode) throws Exception;
}
