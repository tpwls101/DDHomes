package com.ssafy.apt.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.apt.model.ForsaleDto;

public interface ForsaleService {
	void registForsale(ForsaleDto forsaleDto) throws Exception;
	
	/** 등록된 매물 리스트 조회 */
	List<Map<String, String>> forsaleList(String dongCode) throws Exception;
}
