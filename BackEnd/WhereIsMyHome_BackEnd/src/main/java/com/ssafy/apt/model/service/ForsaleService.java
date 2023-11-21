package com.ssafy.apt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.apt.model.ForsaleDto;
import com.ssafy.apt.model.HouseInfoDto;

public interface ForsaleService {
	void registForsale(ForsaleDto forsaleDto) throws Exception;
	List<HouseInfoDto> searchHouseInfo(Map<String, String> params) throws Exception;
	Integer getAvgDealAmount(String aptCode) throws Exception;
}
