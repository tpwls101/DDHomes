package com.ssafy.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.board.model.BoardCommentDto;
import com.ssafy.board.model.BoardDto;
import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.util.BoardUtil;
import com.ssafy.util.PageNavigation;
import com.ssafy.util.SizeConstant;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper boardMapper;
	
	@Override
	public int writeArticle(BoardDto boardDto) throws Exception{
		return boardMapper.writeArticle(boardDto);
	}

	@Override
	public List<BoardDto> listArticle(Map<String, String> map, String sortCondition) throws Exception{
		Map<String, String> param = new HashMap<String, String>();
		int pgno = Integer.parseInt(map.get("pgno"));
		int start = pgno * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		int end = start + SizeConstant.LIST_SIZE;
		
		param.put("boardType", map.get("boardType"));
		
		// 정렬
		List<BoardDto> list = boardMapper.listArticle(param);
		
		BoardUtil.getInstance().sortBoard(list, sortCondition);
		
		// 페이지 넘버에 따라 조절
		if(end > list.size()) {
			end = list.size();
		}
		if(start > end) {
			start = end = 0;
		}
		return list.subList(start, end);
	}
	
	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception{
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, String> param = new HashMap<String, String>();
		param.put("boardType", map.get("boardType"));
		
		int totalCount = boardMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public BoardDto getArticle(int articleNo) throws Exception{
		BoardDto boardDto = boardMapper.getArticle(articleNo);
		return boardDto;
	}

	@Override
	public int updateHit(int articleNo) throws Exception{
		return boardMapper.updateHit(articleNo);
	}

	@Override
	public int modifyArticle(BoardDto boardDto) throws Exception{
		return boardMapper.modifyArticle(boardDto);
	}

	@Override
	public int deleteArticle(int articleNo) throws Exception{
		return boardMapper.deleteArticle(articleNo);
	}

	@Override
	public String getBoardType(int articleNo) throws Exception {
		return boardMapper.getBoardType(articleNo);
	}

	@Override
	public int writeComment(BoardCommentDto boardCommentDto) throws Exception {
		return boardMapper.writeComment(boardCommentDto);
	}

	@Override
	public List<BoardCommentDto> listComment(int articleNo) throws Exception {
		return boardMapper.listComment(articleNo);
	}

	@Override
	public int likeComment(Map<String, Object> map) throws Exception {
		return boardMapper.likeComment(map);
	}
	
	@Override
	public int modifyComment(Map<String, String> map) throws Exception {
		return boardMapper.modifyComment(map);
	}

	@Override
	public int deleteComment(int commentNo) throws Exception {
		return boardMapper.deleteComment(commentNo);
	}
}
