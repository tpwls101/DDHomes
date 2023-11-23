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
	List<ForsaleDto> forsaleList(Map<String, String> params) throws SQLException;
	/** 동 이름, 아파트 이름으로 아파트 검색하기 */
	List<HouseInfoDto> searchHouseInfo(Map<String, String> params) throws SQLException;
	/** 아파트 코드로 최근 2년 평균 거래가 검색하기 */
	Integer getAvgDealAmount(String aptCode) throws SQLException; 
	/** 가격 수정 */
	void modifyPrice(Map<String, String> params) throws SQLException;
	/** 매물 삭제 */
	void deleteForsale(int forsaleNo) throws SQLException;
	/** 이미 찜이 되어있는지 확인 */
	int[] checkFavorite(String userId) throws SQLException;
	/** 매물 찜 등록 */
	void favorite(Map<String, String> params) throws SQLException;
	/** 매물 찜 삭제 */
	void deleteFavorite(Map<String, String> params) throws SQLException;
	/**  최근 2년간 xx동의 평균 거래 가격 구하기 */
	Integer getAvgPrice(String dongCode) throws SQLException;
}
