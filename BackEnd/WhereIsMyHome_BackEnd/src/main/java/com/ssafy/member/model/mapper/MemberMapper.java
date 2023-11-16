package com.ssafy.member.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.MemberDto;

@Mapper
public interface MemberMapper {
	
	/** 회원가입 */
	public int join(MemberDto memberDto);
	/** 아이디 중복 체크 위한 아이디 갯수 리턴 */
	public int idCheck(String userId);
	/** 로그인 */
	public MemberDto login(Map<String, String> map);
	/** 파라미터 정보로 회원 dto 얻기 (중복정의: 아이디찾기) */
	public MemberDto findId(Map<String, String> map);
	/** 파라미터 정보로 회원 dto 얻기 (중복정의: 비밀번호 찾기) */
	public MemberDto findPwd(Map<String, String> map);
	/** 회원 dto로 회원 정보 수정 */
	public int updateMember(MemberDto memberDto);
	/** 회원 탈퇴 */
	public int deleteMember(String userId);
	
}
