package com.ssafy.apt.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.apt.model.ForsaleDto;
import com.ssafy.apt.model.HouseInfoDto;
import com.ssafy.apt.model.mapper.ForsaleMapper;
import com.ssafy.home.file.model.ImgInfoDto;
import com.ssafy.home.file.model.mapper.FileMapper;

@Service
public class ForsaleServiceImpl implements ForsaleService {

	@Autowired
	ForsaleMapper forsaleMapper;
	
	@Autowired
	FileMapper fileMapper;
	
	@Override
	public void registForsale(ForsaleDto forsaleDto) throws Exception {

		forsaleMapper.registForsale(forsaleDto);
		// forsale.xml에서 selectKey해서 forsaleNo가 채워진다.
		// 그걸로 이미지 파일을 테이블에 넣는다.
		List<ImgInfoDto> imgInfos = forsaleDto.getImgInfos();
		if(imgInfos != null && !imgInfos.isEmpty()) {
			for(int i = 0; i < imgInfos.size(); i++) {
				imgInfos.get(i).setForsaleNo(forsaleDto.getForsaleNo());
			}
//			System.out.println(imgInfos);
			fileMapper.uploadBoardForsale(imgInfos);
		}
	}

	@Override
	public List<ForsaleDto> forsaleList(Map<String, String> params) throws Exception {
		List<ForsaleDto> list = forsaleMapper.forsaleList(params);
		return list;
	}
	
	public List<HouseInfoDto> searchHouseInfo(Map<String, String> params) throws Exception {
		return forsaleMapper.searchHouseInfo(params);
	}

	@Override
	public Integer getAvgDealAmount(String aptCode) throws Exception {
		return forsaleMapper.getAvgDealAmount(aptCode);
	}

}
