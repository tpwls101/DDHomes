package com.ssafy.member.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.service.MemberService;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/member")
public class MemberController {
    
    private final Logger logger = LoggerFactory.getLogger(MemberController.class);
    
    private MemberService memberService;

    public MemberController(MemberService memberService) {
        super();
        this.memberService = memberService;
    }
    
    
    /** 로그인 */
//    @PostMapping("/login")
//    public String login(@RequestParam Map<String, String> map, RedirectAttributes redirectAttributes, HttpSession session) {
//    	logger.debug("login map : {}", map);
//        
//        MemberDto memberDto = memberService.login(map);
//        System.out.println("memberDto : " + memberDto);
//        
//        if(memberDto != null) { // 아이디가 존재할 경우
//            String userPwd = map.get("userPwd"); // 사용자가 입력한 비밀번호
//            System.out.println("사용자가 입력한 비밀번호 : " + userPwd);
//            String encryptedPwd = memberDto.getUserPwd(); // 디비에 저장되어 있는 암호화된 비밀번호
//            System.out.println("디비에 저장되어 있는 암호화된 비밀번호 : " + encryptedPwd);
//            
//            // 사용자가 입력한 패스워드(userPwd)와 디비에 저장되어 있는 암호화된 비밀번호(encryptedPw) 비교
//            boolean checkpw = BCrypt.checkpw(userPwd, encryptedPwd);
//            System.out.println("사용자가 입력한 패스워드와 암호화된 비밀번호가 같은가? : " + checkpw);
//            
//            if(checkpw) { // 비밀번호가 같은 경우
//                session.setAttribute("userinfo", memberDto);
//                System.out.println("로그인 성공");
//                return "index";
//            } else { // 비밀번호가 같지 않은 경우
//                redirectAttributes.addFlashAttribute("msg", "[오류]비밀번호가 잘못되었습니다.");
//                return "redirect:/";
//            }
//            
//        } else { // 아이디가 존재하지 않을 경우
//            redirectAttributes.addFlashAttribute("msg", "[오류]아이디가 잘못되었습니다.");
//            return "redirect:/";
//        }
//    }
    
    @PostMapping("/login")
    public ResponseEntity<MemberDto> login(@RequestParam Map<String, String> map, RedirectAttributes redirectAttributes, HttpSession session) {
    	logger.debug("login map : {}", map);
        
        MemberDto memberDto = memberService.login(map);
        System.out.println("memberDto : " + memberDto);
        
        if(memberDto != null) { // 아이디가 존재할 경우
            String userPwd = map.get("userPwd"); // 사용자가 입력한 비밀번호
            System.out.println("사용자가 입력한 비밀번호 : " + userPwd);
            String encryptedPwd = memberDto.getUserPwd(); // 디비에 저장되어 있는 암호화된 비밀번호
            System.out.println("디비에 저장되어 있는 암호화된 비밀번호 : " + encryptedPwd);
            
            // 사용자가 입력한 패스워드(userPwd)와 디비에 저장되어 있는 암호화된 비밀번호(encryptedPw) 비교
            boolean checkpw = BCrypt.checkpw(userPwd, encryptedPwd);
            System.out.println("사용자가 입력한 패스워드와 암호화된 비밀번호가 같은가? : " + checkpw);
            
            if(checkpw) { // 비밀번호가 같은 경우
                session.setAttribute("userinfo", memberDto);
                System.out.println("로그인 성공");
                System.out.println(new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK));
                return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
            }
            else { // 비밀번호가 같지 않은 경우
                redirectAttributes.addFlashAttribute("msg", "[오류]비밀번호가 잘못되었습니다.");
//                return "redirect:/";
                return null;
            }
            
        }
        else { // 아이디가 존재하지 않을 경우
            redirectAttributes.addFlashAttribute("msg", "[오류]아이디가 잘못되었습니다.");
//          return "redirect:/";
           	return null;
        }
    }
    
    
    
    /** 로그아웃 */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if(session != null && session.getAttribute("userinfo") != null) {
            // 세션에 설정한 정보 지우기
            session.removeAttribute("userinfo");
            // 세션객체 죽이기
            session.invalidate();
        }
        return "redirect:/";
    }
    
    
    
    
    /** 회원가입 */
//    @GetMapping("/join")
//    public String join() {
//        return "member/join1";
//    }
    
//    @PostMapping("join")
//    public String join(MemberDto memberDto, RedirectAttributes redirectAttributes) {
//        // 회원가입 시 비밀번호 암호화 처리
//        String userPwd = memberDto.getUserPwd(); // 사용자가 입력한 패스워드
//        String encryptedPwd = BCrypt.hashpw(userPwd, BCrypt.gensalt()); // gensalt() 메서드가 랜덤문자열 salt를 생성해서 userPwd에 붙여주고 해싱하여 저장
//        memberDto.setUserPwd(encryptedPwd);
//        
//        int cnt = memberService.join(memberDto);
//        
//        if(cnt != 0) { // 회원가입 성공
//            redirectAttributes.addFlashAttribute("msg", "[성공]회원가입이 정상 처리되었습니다.");
//            return "redirect:/";
//        } else { // 회원가입 실패
//            redirectAttributes.addFlashAttribute("msg", "[오류]회원가입이 정상 처리되지 않았습니다.");
//            return "member/join";
//        }
//    }
    
//    @PostMapping("/join")
//    public ResponseEntity<?> join(MemberDto memberDto, RedirectAttributes redirectAttributes) {
//        System.out.println("!!memberDto : " + memberDto);
//    	
//    	// 회원가입 시 비밀번호 암호화 처리
//        String userPwd = memberDto.getUserPwd(); // 사용자가 입력한 패스워드
//        String encryptedPwd = BCrypt.hashpw(userPwd, BCrypt.gensalt()); // gensalt() 메서드가 랜덤문자열 salt를 생성해서 userPwd에 붙여주고 해싱하여 저장
//        memberDto.setUserPwd(encryptedPwd);
//        
//        int cnt = memberService.join(memberDto);
//        
//        if(cnt != 0) { // 회원가입 성공
//            redirectAttributes.addFlashAttribute("msg", "[성공]회원가입이 정상 처리되었습니다.");
//            //return "redirect:/";
//            System.out.println(new ResponseEntity<Void>(HttpStatus.CREATED));
//            return new ResponseEntity<Void>(HttpStatus.CREATED);
//        } else { // 회원가입 실패
//            redirectAttributes.addFlashAttribute("msg", "[오류]회원가입이 정상 처리되지 않았습니다.");
////            return "member/join";
//            return null;
//        }
//    }
    
    @PostMapping("/join") // insert -> POST
    public ResponseEntity<?> join(@RequestBody MemberDto memberDto) {
        System.out.println("!!memberDto : " + memberDto);
    	
    	// 회원가입 시 비밀번호 암호화 처리
        String userPwd = memberDto.getUserPwd(); // 사용자가 입력한 패스워드
        String encryptedPwd = BCrypt.hashpw(userPwd, BCrypt.gensalt()); // gensalt() 메서드가 랜덤문자열 salt를 생성해서 userPwd에 붙여주고 해싱하여 저장
        memberDto.setUserPwd(encryptedPwd);
        
        int cnt = memberService.join(memberDto);
        
        if(cnt != 0) { // 회원가입 성공
            System.out.println(new ResponseEntity<Void>(HttpStatus.CREATED));
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } else { // 회원가입 실패
        	return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
    /** 아이디 중복 검사 */
    @GetMapping("/{checkId}")
    @ResponseBody
    public String idCheck(@PathVariable("checkId") String userId) throws Exception {
        logger.debug("idCheck userId : {}", userId);
        int cnt = memberService.idCheck(userId);
        return cnt + "";
    }
    
    
    
    
    /** 아이디 찾기 */
//    @GetMapping("/findId")
//    public String findId() {
//        return "member/findId";
//    }
    
//    @PostMapping("/findId")
//    public String findId(@RequestParam Map<String, String> map, RedirectAttributes redirectAttributes) {
//        MemberDto memberDto = memberService.findId(map);
//        System.out.println("memberDto : " + memberDto);
//        
//        if(memberDto != null) {
//        	System.out.println("아이디 찾기 성공");
//        	redirectAttributes.addFlashAttribute("msg", "[성공] " + memberDto.getUserName() + "님의 아이디는 " + memberDto.getUserId() + "입니다.");
//        	return "redirect:/";
//        } else {
//        	redirectAttributes.addFlashAttribute("msg", "[오류] 회원 정보에 해당하는 아이디가 존재하지 않습니다.");
//        	return "redirect:/member/findId";
//        }
//    }
    
    @GetMapping("/findId") // select -> GET
    public ResponseEntity<?> findId(@RequestParam Map<String, String> map) {
    	System.out.println(map.toString());
    	
    	MemberDto memberDto = memberService.findId(map);
        System.out.println("memberDto : " + memberDto);
        
        if(memberDto != null) {
        	System.out.println("아이디 찾기 성공");
        	System.out.println(new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK));
        	return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
        } else {
        	return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
    
    /** 비밀번호 찾기 */ 
//    @GetMapping("/findPwd")
//    public String findPwd() {
//        return "member/findPwd";
//    }
    
//    @PostMapping("/findPwd")
//    public String findPwd(@RequestParam Map<String, String> map, RedirectAttributes redirectAttributes) {
//        MemberDto memberDto = memberService.findPwd(map);
//        System.out.println("memberDto : " + memberDto);
//        
//        if(memberDto != null) {
//        	System.out.println("비밀번호 찾기 성공");
//        	redirectAttributes.addFlashAttribute("msg", "[성공] " + memberDto.getUserName() + "님의 임시 비밀번호는 " + memberDto.getUserPwd() + "입니다. 로그인 후 비밀번호를 변경하세요.");
//        	return "redirect:/";
//        } else {
//        	redirectAttributes.addFlashAttribute("msg", "[오류] 해당하는 회원 정보가 없습니다.");
//        	return "redirect:/member/findPwd";
//        }
//    }
    
    @GetMapping("/findPwd") // select -> GET
    public ResponseEntity<MemberDto> findPwd(@RequestParam Map<String, String> map) {
    	MemberDto memberDto = memberService.findPwd(map);
        System.out.println("memberDto : " + memberDto);
        
        if(memberDto != null) {
        	System.out.println("비밀번호 찾기 성공");
        	return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
        } else {
        	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
    
    
    
    /** 마이페이지 */
//    @GetMapping("/myPage")
//    public String myPage(HttpSession session) {
//    	MemberDto memberDto = (MemberDto)session.getAttribute("userinfo");
//    	System.out.println("마이페이지에 보이는 memberDto : " + memberDto);
//    	
//    	// 로그인이 되어있는 경우에만 마이페이지로 이동 가능
//    	if(session != null && memberDto != null) {
//    		return "member/myPage";
//    	}
//    	return "index";
//    }
    
    @GetMapping("/myPage")
    public ResponseEntity<MemberDto> myPage(HttpSession session) {
    	System.out.println("마이페이지 호출");
    	
    	MemberDto memberDto = new MemberDto();
    	memberDto.setUserId("ssafy");
    	memberDto.setUserName("김싸피");
    	memberDto.setUserPwd("1234");
    	memberDto.setEmailId("ssafy");
    	memberDto.setEmailDomain("ssafy.com");
    	memberDto.setJoinDate("2023-11-10 12:00:00");
    	memberDto.setGrade("basic");
    	
    	System.out.println(new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK));
    	return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
    }
    
    
    
    
    
    /** 마이페이지 - 회원 정보 수정 */
//    @PostMapping("/updateMember")
//    public String updateMember(MemberDto memberDto, HttpSession session, RedirectAttributes redirectAttributes) {
//        String userPwd = memberDto.getUserPwd(); // 사용자가 수정하기 위해 입력한 패스워드
//        String encryptedPwd = BCrypt.hashpw(userPwd, BCrypt.gensalt()); // 사용자가 입력한 패스워드 암호화
//        memberDto.setUserPwd(encryptedPwd);
//        
//        // 세션 정보 수정
//        MemberDto userinfo = (MemberDto)session.getAttribute("userinfo");
//        memberDto.setJoinDate(userinfo.getJoinDate());
//        memberDto.setGrade(userinfo.getGrade());
//        session.setAttribute("userinfo", memberDto);
//        
//        System.out.println("업데이트 할 memberDto : " + memberDto);
//        
//        int cnt = memberService.updateMember(memberDto);
//        if(cnt != 0) {
//            redirectAttributes.addFlashAttribute("msg", "[성공] 회원 정보가 성공적으로 수정되었습니다.");
//        } else {
//            redirectAttributes.addFlashAttribute("msg", "[오류] 회원 정보 변경에 실패했습니다.");
//        }
//        
//        return "redirect:/member/myPage";
//    }
    
    @PutMapping("/updateMember")
    public ResponseEntity<?> updateMember(@RequestBody MemberDto memberDto, HttpSession session) {
    	String userPwd = memberDto.getUserPwd(); // 사용자가 수정하기 위해 입력한 패스워드
        String encryptedPwd = BCrypt.hashpw(userPwd, BCrypt.gensalt()); // 사용자가 입력한 패스워드 암호화
        memberDto.setUserPwd(encryptedPwd);
        
        // 세션 정보 수정
        // 세션에 값 없어서 NullPointerException 발생 -> 일단 주석 처리
//        MemberDto userinfo = (MemberDto)session.getAttribute("userinfo");
//        memberDto.setJoinDate(userinfo.getJoinDate());
//        memberDto.setGrade(userinfo.getGrade());
//        session.setAttribute("userinfo", memberDto);
        
        System.out.println("업데이트 할 memberDto : " + memberDto);
        
        int cnt = memberService.updateMember(memberDto);
        if(cnt != 0) {
        	System.out.println(ResponseEntity.ok().build());
        	System.out.println("회원 정보 수정 성공");
//        	return new ResponseEntity<MemberDto>(memberDto, HttpStatus.OK);
//        	return new ResponseEntity<Void>(HttpStatus.OK);
        	return ResponseEntity.ok().build();
        } else {
        	System.out.println("회원 정보 수정 실패");
        	return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
        }
    }
    
    
    
    
    
    /** 마이페이지 - 회원 탈퇴 */
    @DeleteMapping("/deleteMember/{userId}")
    public ResponseEntity<?> deleteMember(@PathVariable String userId, HttpSession session) {
    	System.out.println("회원 탈퇴하자!!!");
    	System.out.println("userId : " + userId);
    	
    	int cnt = memberService.deleteMember(userId);
    	
    	if(session != null && session.getAttribute("userinfo") != null) {
            // 세션에 설정한 정보 지우기
            session.removeAttribute("userinfo");
            // 세션객체 죽이기
            session.invalidate();
        }
    	
    	if(cnt != 0) {
    		System.out.println("회원 탈퇴 성공");
    		System.out.println(ResponseEntity.ok().build());
    		return ResponseEntity.ok().build();
    	} else {
    		System.out.println("회원 탈퇴 실패");
    		return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
    	}
    }
    
}