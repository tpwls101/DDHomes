package com.ssafy.board.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.board.model.BoardCommentDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.BoardListDto;
import com.ssafy.board.model.service.BoardService;
import com.ssafy.home.file.model.ImgInfoDto;
import com.ssafy.home.file.model.service.FileService;
import com.ssafy.util.FileUtil;
import com.ssafy.util.PageNavigation;
import com.ssafy.util.ParameterCheck;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE} , maxAge = 6000)
@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileUtil fileUtil;
	
	/**
	 * 글목록 가져오기
	 */
	@GetMapping("/list")
	public ResponseEntity<?> list(@RequestParam Map<String, String> map) {
//		System.out.println(map.keySet());
		// 페이지 넘버 체크
		map.put("pgno", ParameterCheck.notNumberToOne(map.get("pgno"))+"");
		
		// 정렬조건 체크
		if(map.get("sortCondition") == null || map.get("sortCondition") == "") {
			map.put("sortCondition", "articleNo");
		}

		try {
			// 정렬되고 페이지에 맞는 리스트 구해오기
			List<BoardDto> list = boardService.listArticle(map, map.get("sortCondition"));
			// 페이지 내비게이션 만들기
			PageNavigation pageNavigation = boardService.makePageNavigation(map);
			// header 만들기
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			// body 만들기
			BoardListDto boardListDto = new BoardListDto();
			boardListDto.setArticleList(list);
			boardListDto.setBoardType(map.get("boardType"));
			boardListDto.setPageNavigation(pageNavigation);
			// 리턴
//			System.out.println(ResponseEntity.ok().headers(header).body(boardListDto));
			return ResponseEntity.ok().headers(header).body(boardListDto);
		} catch (Exception e) {
			System.out.println("board list Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 글쓰기 + 파일 업로드
	 */
	@PostMapping("/write")
	public ResponseEntity<?> write(MultipartHttpServletRequest formData) {
		try {
			// boardDto(JSON문자열)를 BoardDto객체로 변환(jackson)
			ObjectMapper objectMapper = new ObjectMapper();
			BoardDto boardDto = objectMapper.readValue(formData.getParameter("boardDto"), BoardDto.class);
			// 파일들 받아오기
			List<MultipartFile> multipartFiles = formData.getFiles("imgInfos");
			
			// 파일들 저장 및 dto 리스트로 변환
			List<ImgInfoDto> imgInfos = fileUtil.storeImgs(multipartFiles);
			
			// dto에 파일 dto 리스트 추가
			boardDto.setImgInfos(imgInfos);
			
			// 서비스 호출
			boardService.writeArticle(boardDto);
	
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("board uploadImg Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 글쓰기 
	 */
//	@PostMapping("/write")
//	public ResponseEntity<?> write(@RequestBody BoardDto boardDto) {
//		try {
//			// 작성자 추가할것!!!
//			boardService.writeArticle(boardDto);
//			return new ResponseEntity<Void>(HttpStatus.OK);
//		} catch (Exception e) {
//			System.out.println("board write Controller Error");
//			e.printStackTrace();
//			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	/**
	 * 글 상세정보 확인
	 */
	// 파라미터를 PathVariable을 이용해 받음
	@GetMapping("/detail/{articleNo}")
	public ResponseEntity<?> detail(@PathVariable int articleNo) {
//		System.out.println(articleNo);
		
		try {
			// 상세정보 가져와서
			BoardDto boardDto = boardService.getArticle(articleNo);

			// 조회수 처리
			boardService.updateHit(articleNo);
			boardDto.setHit(boardDto.getHit()+1);

			// 줄바꿈 치환 후
			String content = boardDto.getContent();
//			content = "<p>" + content.replaceAll("\r\n", "</p><p>") + "</p>";
			content = content.replaceAll("\n", "<br>");
			boardDto.setContent(content);
			
			// 리턴
//			System.out.println(new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK));
			return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println("board view Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	/**
	 * 글 수정페이지 이동
	 */
	@GetMapping("/modify/{articleNo}")
	public ResponseEntity<?> modify(@PathVariable int articleNo) {
		try {
			// 작성된 내용물을 가지고 이동
			BoardDto boardDto = boardService.getArticle(articleNo);
			return new ResponseEntity<BoardDto>(boardDto, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("board list Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	/**
	 * 글 수정하기
	 */
	@PostMapping("/modify")
	public ResponseEntity<?> modify(MultipartHttpServletRequest formData) {
		try {
			// boardDto(JSON문자열)를 BoardDto객체로 변환(jackson)
			ObjectMapper objectMapper = new ObjectMapper();
			BoardDto boardDto = objectMapper.readValue(formData.getParameter("boardDto"), BoardDto.class);
			// 파일들 받아오기
			List<MultipartFile> multipartFiles = formData.getFiles("imgInfos");
			
			// 파일들 저장 및 dto 리스트로 변환
			List<ImgInfoDto> imgInfos = fileUtil.storeImgs(multipartFiles);
			
			// dto에 파일 dto 리스트 추가
			boardDto.setImgInfos(imgInfos);
			
			// 서비스 호출
			boardService.modifyArticle(boardDto);
	
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("board modify Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@PutMapping("/modify")
//	public ResponseEntity<?> modify(@RequestBody BoardDto boardDto) {
//		try {
//			boardService.modifyArticle(boardDto);
//			return new ResponseEntity<BoardDto>(HttpStatus.OK);
//		} catch (Exception e) {
//			System.out.println("board list Controller Error");
//			e.printStackTrace();
//			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	/**
	 * 글 삭제하기
	 */
	@DeleteMapping("/delete/{articleNo}") 
	public ResponseEntity<?> delete(@PathVariable int articleNo) {
		try {
			// 이미지 제거
			List<ImgInfoDto> imgInfos = fileService.getBoardImgInfo(articleNo);
			if(imgInfos != null && !imgInfos.isEmpty()) {
				fileUtil.deleteImg(imgInfos);
			}
			
			// 게시물 제거
			boardService.deleteArticle(articleNo);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("board delete Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// ====================================================================================================
	// Comment
	/**
	 * 댓글 등록하기
	 */
	@PostMapping("comment/write")
	public ResponseEntity<?> commentWrite(@RequestBody BoardCommentDto boardCommentDto) {
		try {
			// 작성자 추가할것!!!
			boardService.writeComment(boardCommentDto);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("board comment write Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("comment/list/{articleNo}")
	public ResponseEntity<?> commentList(@PathVariable int articleNo) {
		try {
			List<BoardCommentDto> list = boardService.listComment(articleNo);
			return new ResponseEntity<List<BoardCommentDto>>(list, HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println("board comment list Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("comment/like")
	public ResponseEntity<?> commentLike(@RequestBody Map<String, Object> params) {
		try {
			boardService.likeComment(params);
			return new ResponseEntity<Void>(HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println("board comment like Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("comment/modify")
	public ResponseEntity<?> commentModify(@RequestBody Map<String, String> params) {
		try {
			boardService.modifyComment(params);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch(Exception e) {
			System.out.println("board comment modify Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("comment/delete/{commentNo}")
	public ResponseEntity<?> commentDelete(@PathVariable int commentNo) {
		try {
			boardService.deleteComment(commentNo);
			return new ResponseEntity<Void>(HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println("board comment list Controller Error");
			e.printStackTrace();
			return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
