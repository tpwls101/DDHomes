package com.ssafy.apt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.model.HouseDealDto;
import com.ssafy.apt.model.HouseInfoDto;

@Mapper
public interface AptMapper {
	// 동적쿼리 사용, selector item 얻기
	public List<Map<String, Object>> getSelectorItems(Map<String, String> param) throws SQLException;
	// 선택한 dongCode로 해당 동의 house info 얻기
	public List<HouseInfoDto> getHouseInfo(String dongCode) throws SQLException;
	// 선택한 aptCode로 해당 아파트의 house deal 얻기
	public List<HouseDealDto> getHouseDeal(String aptCode) throws SQLException;
	// 선택한 아파트의 거래 내역 구하기
	public List<HouseDealDto> getHouseDealInfo(String aptCode) throws SQLException;
}
