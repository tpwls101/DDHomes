<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!-- header -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<%-- <c:set var="boardType" value="${requestScope.boardType}"/> --%>

<div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
            <c:if test="${boardType eq 'announcement'}">
                <mark class="sky">공지사항</mark>
            </c:if>
            <c:if test="${boardType eq 'information'}">
                <mark class="sky">정보 게시판</mark>
            </c:if>
        </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
        <div class="row align-self-center mb-2">
        	<c:if test="${userinfo.grade eq 'admin'}">
            <div class="col-md-2 text-start">
                <button type="button" id="btn-board-write" class="btn btn-outline-primary btn-sm">
                    글쓰기
                </button>
            </div>
            </c:if>
        </div>
        <table class="table table-hover">
            <thead>
            <tr class="text-center">
                <th scope="col">글번호</th>
                <th scope="col">제목</th>
                <th scope="col">작성자</th>
                <th scope="col">조회수</th>
                <th scope="col">작성일</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="article" items="${articles}">
                <tr class="text-center">
                    <th scope="row">${article.articleNo}</th>
                    <td class="text-start">
                        <a
                                href="#"
                                class="article-title link-dark"
                                data-no="${article.articleNo}"
                                style="text-decoration: none"
                        >
                                ${article.subject}
                        </a>
                    </td>
                    <td>${article.userId}</td>
                    <td>${article.hit}</td>
                    <td>${article.registerTime}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="row">
        ${navigation.navigator}
    </div>
    
	<!-- 정렬조건 start -->
	<div>
		<select id="sortSelector" class="form-select form-select-sm mx-auto w-25" aria-label="정렬조건">
			<option id="sortDefault" selected value="articleNo">정렬조건</option>
			<option id="sortHitDesc" value="hitDesc">조회수 높은 순</option>
			<option id="sortHitAsc" value="hitAsc">조회수 낮은 순</option>
			<option id="sortSubject" value="subjectAsc">제목 순</option>
			<option id="sortUserId" value="userIdAsc">작성자 순</option>
		</select>
		<!-- <form id="sortForm" method="GET">
            <div class="input-group input-group-sm">
                <input type="text" id="searchTxt" class="form-control" placeholder="검색어..." />
                <button id="btn-search" class="btn btn-dark" type="button">검색</button>
            </div>
		</form>  -->
	</div>
	<!-- 정렬조건 end -->
	
</div>

<form id="form-param" method="get" action="${root}/board/list">
    <input type="hidden" id="p-boardType" name="boardType" value="">
    <input type="hidden" id="p-pgno" name="inputPgno" value="">
    <input type="hidden" id="p-sortCondition" name="sortCondition" value="">
</form>
<script>
    let titles = document.querySelectorAll(".article-title");
    titles.forEach(function (title) {
        title.addEventListener("click", function () {
            console.log(this.getAttribute("data-no"));
            location.href = "${root}/board/view?articleNo=" + this.getAttribute("data-no") + "&boardType=${boardType}";
        });
    });

    <c:if test="${userinfo.grade eq 'admin'}">
    document.querySelector("#btn-board-write").addEventListener("click", function () {
        location.href = "${root}/board/write";
    });
    </c:if>

    // 정렬 조건 select 고정하기
    let selector = document.querySelector("#sortSelector");
    let URLSearch = new URLSearchParams(location.search);
    let sortCondition = URLSearch.get("sortCondition");
    for(let i = 0; i < selector.options.length; i++) {
    	if(selector.options[i].value == sortCondition) {
    		selector.options[i].selected = true;
    	}
    }
    
    // 페이지 내비게이션 클릭시
    let pages = document.querySelectorAll(".page-link");
    pages.forEach(function (page) {
        page.addEventListener("click", function () {
            // form의 boardtype
            document.querySelector("#p-boardType").value = "${boardType}";
            // 클릭한 페이지 넘버
            document.querySelector("#p-pgno").value = this.parentNode.getAttribute("data-pg");
            // 정렬조건
            document.querySelector("#p-sortCondition").value = sortCondition;
            
            document.querySelector("#form-param").submit();
        });
    });
    
    // 정렬 조건 선택시 이벤트
    let sortSelector = document.querySelector("#sortSelector");
    sortSelector.addEventListener("change", function() {
    	let data = sortSelector.options[sortSelector.selectedIndex].value;
    	let type = "${boardType}";
    	location.href = "${root}/board/list?boardType=" + type + "&sortCondition=" + data;
    });
    
</script>

<!-- footer -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

</body>
</html>