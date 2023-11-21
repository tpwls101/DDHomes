package com.ssafy.apt.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.model.ForsaleDto;

@Mapper
public interface ForsaleMapper {
	void registForsale(ForsaleDto forsaleDto) throws SQLException;
	
	/** 등록된 매물 리스트 조회 */
	List<Map<String, String>> forsaleList(String dongCode) throws SQLException;
}
