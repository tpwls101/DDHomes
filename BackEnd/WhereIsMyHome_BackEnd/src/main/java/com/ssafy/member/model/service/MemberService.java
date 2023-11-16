package com.ssafy.member.model.service;

import java.util.Map;

import com.ssafy.member.model.MemberDto;

/** 회원관리를 위한 업무 서비스 기능 선언 */
public interface MemberService {
	public int join(MemberDto memberDto);
	// 아이디 중복체크 위한 아이디 갯수 리턴
	public int idCheck(String userId);
	public MemberDto login(Map<String, String> map);
	public MemberDto findId(Map<String, String> map);
	/** 주어진 회원 정보로 비밀번호 찾아서 임시 비밀번호 발급 후 해당 임시 비밀번호 리턴 */
	public MemberDto findPwd(Map<String, String> map);
	public int updateMember(MemberDto memberDto);
	public int deleteMember(String userId);
}
