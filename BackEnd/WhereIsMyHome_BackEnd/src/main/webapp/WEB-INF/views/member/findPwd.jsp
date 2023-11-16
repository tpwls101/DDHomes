<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- header -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-sm-12">
            <h2 class="my-3 py-3 shadow-sm bg-light text-center">
                <mark class="orange">비밀번호 찾기</mark>
            </h2>
        </div>
        
        <div class="col-lg-8 col-md-10 col-sm-12">
            <form id="form-find-password" method="POST" action="">
                <div class="mb-3">
                    <label for="userid" class="form-label">아이디 : </label>
                    <input
                            type="text"
                            class="form-control"
                            id="userid"
                            name="userId"
                            placeholder="아이디..."
                    />
                </div>
                <div class="mb-3">
                    <label for="username" class="form-label">이름 : </label>
                    <input
                            type="text"
                            class="form-control"
                            id="username"
                            name="userName"
                            placeholder="이름..."
                    />
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
                        />
                        <span class="input-group-text">@</span>
                        <select
                                class="form-select"
                                id="emaildomain"
                                name="emailDomain"
                                aria-label="이메일 도메인 선택"
                        >
                            <option selected>선택</option>
                            <option value="ssafy.com">싸피</option>
                            <option value="google.com">구글</option>
                            <option value="naver.com">네이버</option>
                            <option value="kakao.com">카카오</option>
                        </select>
                    </div>
                </div>
                <div class="col-auto text-center">
                    <button type="button" id="btn-find-password" class="btn btn-outline-primary mb-3">
                        비밀번호찾기
                    </button>
                    <button type="button" id="btn-go-back" class="btn btn-outline-success mb-3">돌아가기</button>
                </div>
            </form>
        </div>
    </div>
</div>

<!-- footer -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

<script>
    // 아이디 찾기 버튼 이벤트 처리
    document.querySelector("#btn-find-password").addEventListener("click", function () {
        if (!document.querySelector("#userid").value) {
            alert("아이디 입력!!");
            return;
        } else if (!document.querySelector("#username").value) {
            alert("이름 입력!!");
            return;
        } else if (!document.querySelector("#emailid").value) {
            alert("이메일 입력!!");
            return;
        } else if ("선택" == document.querySelector("#emaildomain").value) {
            alert("이메일 도메인 선택!!");
        } else {
            let form = document.querySelector("#form-find-password");
            // FrontController 반영: 회원의 모든 요청을 하나의 서블릿에서 받아서 처리: MemberController : @WebServlet("/member")
            form.setAttribute("action", "${root}/member/findPwd");
            form.submit();
        }
    });

    // 돌아가기 버튼 이벤트 처리
    document.querySelector("#btn-go-back").addEventListener("click", function () {
        location.href = "${root}/";
    });
</script>
</body>
</html>
