<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!-- header -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
            <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                <mark class="orange">마이페이지</mark>
            </h2>
        </div>
        <h6 class="text-center">마이페이지에서 회원 정보를 수정할 수 있습니다.</h6>
        <div class="col-lg-8 col-md-10 col-sm-12">
            <form id="form-update" method="POST" action="">
            	<div><label for="grade" class="form-label">현재 등급 : ${userinfo.grade}</label></div>
                <div class="mb-3">
                    <label for="userid" class="form-label">아이디 : </label>
                    <input
                            type="text"
                            class="form-control"
                            id="userid"
                            name="userId"
                            placeholder="아이디..."
                            value="${userinfo.userId}"
                            readonly="readonly"
                    />
                </div>
                <div class="mb-3">
                    <label for="username" class="form-label">이름 : </label>
                    <input
                            type="text"
                            class="form-control"
                            id="username"
                            name="userName"
                            value="${userinfo.userName}"
                    />
                </div>
                <div class="mb-3">
                    <label for="userpwd" class="form-label">비밀번호 : </label>
                    <input
                            type="password"
                            class="form-control"
                            id="userpwd"
                            name="userPwd"
                            placeholder="비밀번호..."
                            value=""
                    />
                </div>
                <div class="mb-3">
                    <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
                    <input type="password" class="form-control" id="pwdcheck" placeholder="비밀번호확인..." value=""/>
                </div>
                <div class="mb-3">
                    <label for="emailid" class="form-label">이메일 : </label>
                    <div class="input-group">
                        <input
                                type="text"
                                class="form-control"
                                id="emailid"
                                name="emailId"
                                placeholder="이메일아이디"
                                value="${userinfo.emailId}"
                        />
                        <span class="input-group-text">@</span>
                        <select
                                class="form-select"
                                id="emaildomain"
                                name="emailDomain"
                                aria-label="이메일 도메인 선택"
                        >
                            <option value="">선택</option>
                            <option value="ssafy.com">싸피</option>
                            <option value="google.com">구글</option>
                            <option value="naver.com">네이버</option>
                            <option value="kakao.com">카카오</option>
                        </select>
                    </div>
                </div>
                <div class="col-auto text-center">
                    <button type="button" id="btn-update" class="btn btn-outline-primary mb-3">정보수정</button>
                    <button type="button" id="btn-go-back" class="btn btn-outline-success mb-3">돌아가기</button>
                    <button type="button" id="btn-withdraw" class="btn btn-outline-danger mb-3">탈퇴하기</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- footer -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

<script>
    // emailDomain 정보 띄우기
    window.onload = function () {
        let sel = document.querySelector("#emaildomain");
        console.log("sel : " + sel.value);
        let emailId = "${userinfo.emailDomain}";
        for (let i = 1; i < sel.options.length; i++) {
            if (sel.options[i].value == emailId) {
                sel.options[i].setAttribute("selected", "selected");
            }
        }
    }

    document.querySelector("#btn-update").addEventListener("click", function () {
        if (!document.querySelector("#username").value) {
            alert("이름 입력!!");
            return;
        } else if (!document.querySelector("#userid").value) {
            alert("아이디 입력!!");
            return;
        } else if (!document.querySelector("#userpwd").value) {
            alert("비밀번호 입력!!");
            return;
        } else if (document.querySelector("#userpwd").value != document.querySelector("#pwdcheck").value) {
            alert("비밀번호 확인!!");
            return;
        } else if (!document.querySelector("#emailid").value) {
            alert("이메일 입력!!");
            return;
        } else if ("선택" == document.querySelector("#emaildomain").value) {
            alert("이메일 도메인 선택!!");
        } else {
            let form = document.querySelector("#form-update");
            form.setAttribute("action", "${root}/member/updateMember");
            form.submit();
        }
    });

    document.querySelector("#btn-go-back").addEventListener("click", function () {
        location.href = "${root}/";
    });

    document.querySelector("#btn-withdraw").addEventListener("click", function () {
        let check = confirm("탈퇴 하시겠습니까?");
        if (check) {
            location.href = "${root}/member/deleteMember?userId=${userinfo.userId}";
        }
    });
</script>
</body>
</html>
