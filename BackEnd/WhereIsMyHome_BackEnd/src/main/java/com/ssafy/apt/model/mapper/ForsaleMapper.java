package com.ssafy.apt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.model.ForsaleDto;
import com.ssafy.apt.model.HouseInfoDto;

@Mapper
public interface ForsaleMapper {
	/** 매물 등록하기 */
	void registForsale(ForsaleDto forsaleDto) throws SQLException;
	/** 등록된 매물 리스트 조회 */
	List<ForsaleDto> forsaleList(String dongCode) throws SQLException;
	/** 동 이름, 아파트 이름으로 아파트 검색하기 */
	List<HouseInfoDto> searchHouseInfo(Map<String, String> params) throws SQLException;
	/** 아파트 코드로 최근 2년 평균 거래가 검색하기 */
	Integer getAvgDealAmount(String aptCode) throws SQLException; 
}
