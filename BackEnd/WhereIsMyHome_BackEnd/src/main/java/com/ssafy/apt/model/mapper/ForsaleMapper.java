package com.ssafy.apt.model.mapper;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.apt.model.ForsaleDto;

@Mapper
public interface ForsaleMapper {
	void registForsale(ForsaleDto forsaleDto) throws SQLException;
}
