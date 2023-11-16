package com.ssafy.util;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.ssafy.board.model.BoardDto;

/**
 * <미완성>
 * 게시판 글 정렬 위한 알고리즘
 * 퀵소트를 사용하였다.
 * 
 * 정렬 조건에 따라 같은 메서드로 처리하기 위해 sortCondition을 활용한 메서드를 사용한다.(getNum())
 * 
 * @author bullie
 *
 */
@Component
public class BoardUtil {
	
	private String sc;
	
	public void sortBoard(List<BoardDto> list, String sortCondition) {
		sc = sortCondition;
		
		// 기본: 글번호(최근 작성한 순서)
		if(sc.equals("articleNo")) {
			Collections.sort(list, (o1, o2) -> o2.getArticleNo() - o1.getArticleNo());
		}
		// 조회수 높은 순
		else if(sc.equals("hitDesc")) {
			Collections.sort(list, (o1, o2) -> o2.getHit() - o1.getHit());
		}
		// 조회수 낮은 순
		else if(sc.equals("hitAsc")) {
			Collections.sort(list, (o1, o2) -> o1.getHit() - o2.getHit());
		}
		// 제목 순
		else if(sc.equals("subjectAsc")) {
			Collections.sort(list, (o1, o2) -> o1.getSubject().compareTo(o2.getSubject()));
		}
		// 작성자 순
		else if(sc.equals("userIdAsc")) {
			Collections.sort(list, (o1, o2) -> o1.getUserId().compareTo(o2.getUserId()));
		}
		
//		int size = list.size();
//		BoardDto[] arr = new BoardDto[size];
//		list.toArray(arr);
//		quickSort(arr, 0, arr.length-1);
//		
//		list = Arrays.asList(arr);
	}
	
	// 정렬 조건에 따라 dto에서 알맞은 값 추출
	private int getNum(BoardDto boardDto) {
		int result = 0;
		
		// 기본: 글번호
		if (sc.equals("articleNo")) {
			result = boardDto.getArticleNo();
		}
		// 조회수
		else if (sc.equals("hitDesc") || sc.equals("hitAsc")) {
			result = boardDto.getHit();
		}
		
		return result;
	}
	
	private int partition(BoardDto arr[], int left, int right) {
		int pivot = getNum(arr[(left + right) / 2]);
		
		while(left < right) {
			while((getNum(arr[left]) < pivot) && (left < right)) {
				left++;
			}
			while((getNum(arr[right]) < pivot) && (left < right)) {
				right--;
			}
			
			if(left < right) {
				BoardDto temp = arr[left];
				arr[left] = arr[right];
				arr[right] = temp;
			}
		}
		
		return left;
	}
	
	
	// list: 게시물 리스트
	private void quickSort(BoardDto arr[], int left, int right) {
		if(left < right) {
			int pivotNewIndex = partition(arr, left, right);
			
			quickSort(arr, left, pivotNewIndex - 1);
			quickSort(arr, pivotNewIndex + 1, right);
		}
	}
}
