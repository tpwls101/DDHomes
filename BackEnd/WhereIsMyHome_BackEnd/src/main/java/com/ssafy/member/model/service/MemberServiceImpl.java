package com.ssafy.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;
import com.ssafy.util.Utility;

@Service
public class MemberServiceImpl implements MemberService {

	private MemberMapper memberMapper;

	@Autowired
	public MemberServiceImpl(MemberMapper memberMapper) {
		super();
		this.memberMapper = memberMapper;
	}
	
	@Override
	public int join(MemberDto memberDto) {
		return memberMapper.join(memberDto);
	}
	
	
	
	
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		return memberMapper.login(memberDto);
	}
	
	@Override
	public MemberDto userInfo(String userId) throws Exception {
		return memberMapper.userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberMapper.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}
	
	
	
	
	
	@Override
	public int idCheck(String userId) {
		return memberMapper.idCheck(userId);
	}

//	@Override
//	public MemberDto login(Map<String, String> map) {
//		return memberMapper.login(map);
//	}

	@Override
	public MemberDto findId(Map<String, String> map) {
		return memberMapper.findId(map);
	}

	@Override
	public MemberDto findPwd(Map<String, String> map) {
		MemberDto memberDto = memberMapper.findPwd(map);
		
		// 주어진 회원정보에 해당하는 회원이 존재한다면 비밀번호 임시 발급 후 해당 비밀번호로 회원정보 업데이트
		if(memberDto != null) {
			// Utility api 활용 랜덤 비밀번호 생성
			String tempPwd = Utility.getSecureCodeNumber(); // 임시 비밀번호
			String encryptedPwd = BCrypt.hashpw(tempPwd, BCrypt.gensalt()); // 임시 비밀번호를 암호화시킨 것
			memberDto.setUserPwd(encryptedPwd);
			
			// 해당 정보들로 회원 업데이트
			int cnt = updateMember(memberDto);
			if(cnt != 0) {	// 업데이트 성공 시
				memberDto.setUserPwd(tempPwd); // 업데이트한 랜덤 비밀번호 set해서 리턴
				return memberDto;
			}
		}
		return null;
	}

	@Override
	public int updateMember(MemberDto memberDto) {
		return memberMapper.updateMember(memberDto);
	}

	@Override
	public int deleteMember(String userId) {
		return memberMapper.deleteMember(userId);
	}

}
