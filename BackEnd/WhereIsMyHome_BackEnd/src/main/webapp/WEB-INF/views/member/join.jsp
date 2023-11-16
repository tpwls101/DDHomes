<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>

<!-- header -->
<%@ include file="/WEB-INF/views/common/header.jsp" %>

<div class="container">
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="orange">회원가입</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <form id="form-join" method="POST" action="">
        <!-- <input type="hidden" name="action" value="join"/> -->
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
          <label for="userid" class="form-label">아이디 : </label>
          <input
            type="text"
            class="form-control"
            id="userid"
            name="userId"
            placeholder="아이디..."
          />
        </div>
        <div id="result-view" class="mb-3"></div>
        <div class="mb-3">
          <label for="userpwd" class="form-label">비밀번호 : </label>
          <input
            type="password"
            class="form-control"
            id="userpwd"
            name="userPwd"
            placeholder="비밀번호..."
          />
        </div>
        <div class="mb-3">
          <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
          <input type="password" class="form-control" id="pwdcheck" placeholder="비밀번호확인..." />
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
          <button type="button" id="btn-join" class="btn btn-outline-primary mb-3">회원가입</button>
        </div>
      </form>
    </div>
  </div>
</div>


<!-- footer -->
<%@ include file="/WEB-INF/views/common/footer.jsp" %>

<script>
    let isUseId = false; // 아이디 사용 가능 여부 (false : 사용 불가 / true : 사용 가능)
    let userId = document.querySelector("#userid"); // 입력받은 아이디
    let resultDiv = document.querySelector("#result-view"); // 사용 가능 여부를 보여줄 div

    userId.addEventListener("keyup", function () {
        let checkId = userId.value; // 입력받은 아이디 (사용가능 여부를 확인해볼 아이디)
        let len = checkId.length;
        if (len < 4 || len > 16) {
            isUseId = false;
            resultDiv.setAttribute("class", "mb-3 fw-bold text-dark");
            resultDiv.innerHTML = "아이디는 4자이상 16자이하입니다.";
        } else {
        	// CSV
            fetch("${root}/member/" + checkId)
	   		.then(response => response.text())
	   		.then(data => {
	   			console.log(data);
	   			// data : 중복되는 아이디의 개수
		 		if(data == 0) {
		   			resultDiv.setAttribute("class", "mb-3 text-primary");
	       			resultDiv.textContent = checkId + "는 사용할 수 있습니다.";
	       			isUseId = true;
		 		} else {
		   			resultDiv.setAttribute("class", "mb-3 text-danger");
 		       		resultDiv.textContent = checkId + "는 사용할 수 없습니다.";
 		     		isUseId = false;
		 		}
  		   });
            
            // JSON
            // fetch(url)
            //   .then((response) => response.json())
            //   .then((data) => resultViewJSON(data));
        }

    });

    /* function resultViewText(data) {
        let val = data.split(",");
        let id = val[0];
        let cnt = val[1];

        if (cnt == 0) {
            isUseId = true;
            resultDiv.setAttribute("class", "mb-3 text-success");
            resultDiv.innerHTML = "<span class='fw-bold'>" + id + "</span>은 사용할 수 있습니다.";
        } else {
            isUseId = false;
            resultDiv.setAttribute("class", "mb-3 text-danger");
            resultDiv.innerHTML = "<span class='fw-bold'>" + id + "</span>은 사용할 수 없습니다.";
        }
    } */

    // function resultViewJSON(data) {
    //   if (data.cnt == 0) {
    //     isUseId = true;
    //     resultDiv.setAttribute("class", "mb-3 text-primary");
    //     resultDiv.innerHTML =
    //       "<span class='fw-bold'>" + data.checkid + "</span>은 사용할 수 있습니다.";
    //   } else {
    //     isUseId = false;
    //     resultDiv.setAttribute("class", "mb-3 text-warning");
    //     resultDiv.innerHTML =
    //       "<span class='fw-bold'>" + data.checkid + "</span>은 사용할 수 없습니다.";
    //   }
    // }

    document.querySelector("#btn-join").addEventListener("click", function () {
        <%--let form = document.querySelector("#form-join");--%>
        <%--form.setAttribute("action", "${root}/member?action=join");--%>
        <%--form.submit();--%>

        if (!document.querySelector("#username").value) {
            alert("이름 입력!!");
            return;
        } else if (!document.querySelector("#userid").value) {
            alert("아이디 입력!!");
            return;
        } else if (!isUseId) {
            alert("아이디 중복 확인!!");
            return;
        } else if (!document.querySelector("#userpwd").value) {
            alert("비밀번호 입력!!");
            return;
        } else if (
            document.querySelector("#userpwd").value != document.querySelector("#pwdcheck").value
        ) {
            alert("비밀번호 확인!!");
            return;
        } else {
            let form = document.querySelector("#form-join");
            form.setAttribute("action", "${root}/member/join");
            form.submit();
        }
    });
</script>
</body>
</html>
