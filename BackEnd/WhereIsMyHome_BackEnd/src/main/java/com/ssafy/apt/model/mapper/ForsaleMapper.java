package com.ssafy.apt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.model.ForsaleDto;
import com.ssafy.apt.model.HouseInfoDto;

@Mapper
public interface ForsaleMapper {
	void registForsale(ForsaleDto forsaleDto) throws SQLException;
	List<HouseInfoDto> searchHouseInfo(Map<String, String> params) throws SQLException;
	Integer getAvgDealAmount(String aptCode) throws SQLException; 
}
