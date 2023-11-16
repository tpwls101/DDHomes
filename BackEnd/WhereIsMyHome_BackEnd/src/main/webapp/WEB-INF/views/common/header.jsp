<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
            crossorigin="anonymous"
    />
    <link href="${root}/assets/css/style.css" rel="stylesheet"/>
    <title>5반의 셜록 Homes</title>
</head>
<body>

<%-- request 객체에 msg가 들어있을 때 해당 내용 알림창 띄우기 --%>
<script>
	<c:if test="${!empty msg}">
		alert("${msg}");
	</c:if>
</script>

<!-- header start -->
<header>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
        <div class="container-fluid">
            <ul class="navbar-nav me-5">
                <li class="nav-item">
                    <a class="nav-link active" href="${root}/"><img src="${root}/assets/img/homes_logo.png"
                                                                             alt="" width='100px'></a>
                </li>
            </ul>
            <!-- board start-------------------------------------------------------------------------------- -->
            <ul class="navbar-nav ms-5 me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${root}/board/list?boardType=announcement">공지사항</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="${root}/board/list?boardType=information">게시판</a>
                </li>
            </ul>
            <!-- board end-------------------------------------------------------------------------------- -->
            <ul class="navbar-nav me-2">
                <%-- <c:choose> --%>
                    <%-- <c:when test="${not empty userinfo}"> --%>
                    <c:if test="${not empty userinfo}">
                        <li id="nav-myPage" class="nav-item">
                            <div class="dropdown dropstart">
                                <button class="nav-link btn btn-secondary dropdown-toggle" type="button"
                                        data-bs-toggle="dropdown" aria-expanded="false">
                                    <img src="${root}/assets/img/user.png" alt="" width='35px'>
                                </button>
                                <ul class="dropdown-menu">
                                    <li><h6 class="dropdown-header">${userinfo.userName}님, 안녕하세요.</h6></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <c:if test="${userinfo.grade eq 'admin'}">
                                    	<li>
                                    		<a id="write" class="dropdown-item" href="${root}/board/write">글쓰기</a></li>
                                    	</c:if>
                                    <li><a id="myPage" class="dropdown-item"
                                           href="${root}/member/myPage">마이페이지</a></li>
                                    <li><a id="logout" class="dropdown-item"
                                           href="${root}/member/logout">로그아웃</a></li>
                                </ul>
                            </div>
                        </li>
                    </c:if>
                    <%-- </c:when> --%>
                    <%-- <c:otherwise> --%>
                    <c:if test="${empty userinfo}">
                        <li id="nav-signUp" class="nav-item">
                            <a class="nav-link" href="${root}/member/join">회원가입</a>
                        </li>
                        <li id="nav-signIn" class="nav-item">
                            <a class="nav-link" data-bs-toggle="modal" data-bs-target="#loginModal">로그인</a>
                            <!-- 로그인 Modal -->
                            <div class="modal" id="loginModal">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <!-- Modal Header -->
                                        <div class="modal-header">
                                            <h4 class="modal-title">로그인</h4>
                                            <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                        </div>
                                        <!-- Modal body -->
                                        <div class="modal-body">
                                            <form action="" method="post" name="loginForm" id="loginForm">
                                                <table>
                                                    <tr>
                                                        <td>아이디</td>
                                                        <td>
                                                            <input type="text" name="userId" id="userId"
                                                                   autofocus="autofocus"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>비밀번호</td>
                                                        <td>
                                                            <input type="password" name="userPwd" id="userPwd"/>
                                                            <input type="checkbox" name="isShow" id="isShow"/><span
                                                                id="showUserPwd">비밀번호 보이기</span>
                                                        </td>
                                                    </tr>

                                                </table>
                                            </form>
                                        </div>
                                        <!-- Modal footer start -->
                                        <div class="modal-footer">
                                            <div class="me-auto">
                                                <button type="button" id="findIdBtn"
                                                        class="btn btn-outline-dark btn-sm">아이디 찾기
                                                </button>
                                                <button type="button" id="findPwdBtn" class="btn btn-outline-dark btn-sm">
                                                    비밀번호 찾기
                                                </button>
                                            </div>
                                            <div>
                                                <button type="button" id="loginSubmitBtn" class="btn btn-success">로그인
                                                </button>
                                                <button type="button" class="btn btn-danger" data-bs-dismiss="modal">
                                                    닫기
                                                </button>
                                            </div>
                                        </div>
                                        <!-- Modal footer end -->
                                    </div>
                                </div>
                            </div>
                        </li>
                        </c:if>
                    <%-- </c:otherwise> --%>
                <%-- </c:choose> --%>
            </ul>
        </div>
    </nav>
</header>
<!-- header end -->
<script>
    // 로그인 안했을 때
    <c:if test="${empty userinfo}">
    document.querySelector("#isShow").addEventListener("change", function () {
        let memberPwElement = document.querySelector("#userPwd");
        let showMemberPwElement = document.querySelector("#showUserPwd");

        // 비밀번호 보이기/감추기 체크박스
        let isShowElement = document.querySelector("#isShow");

        if (isShowElement.checked) {
            memberPwElement.type = "text";
            showMemberPwElement.innerText = "비밀번호 감추기";
        } else {
            memberPwElement.type = "password";
            showMemberPwElement.innerText = "비밀번호 보이기";
        }
    });

    // 아이디 찾기 버튼
    document.querySelector("#findIdBtn").addEventListener("click", function() {
        location.href = "${root}/member/findId";
    })

    // 비밀번호 찾기 버튼
    document.querySelector("#findPwdBtn").addEventListener("click", function() {
        location.href = "${root}/member/findPwd"
    })

    // 로그인 버튼 누르면
    document.querySelector("#loginSubmitBtn").addEventListener("click", function () {
        let form = document.querySelector("#loginForm");
        form.setAttribute("action", "${root}/member/login");
        form.submit();
    });
    </c:if>

</script>

