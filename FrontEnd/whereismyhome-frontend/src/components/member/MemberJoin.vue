<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { join, idCheck } from "@/api/member";

const router = useRouter();

// 회원가입 폼의 input 태그로 입력받은 값(파라미터)
const param = ref({
  userId: "",
  userName: "",
  userPwd: "",
  userPwdChk: "",
  emailId: "",
  emailDomain: "",
  grade: "basic",
});

// 아이디 중복 검사
const possibleId = ref(false); // 사용할 수 있는지 여부
const resultDiv = ref(""); // 사용 가능 여부를 보여주는 div
const resultDivClass = ref("mb-3"); // 사용 가능 여부를 보여주는 div

const memberJoin = () => {
  if (param.value.userName == "") {
    alert("이름을 입력해주세요.");
    return;
  } else if (param.value.userId == "") {
    alert("아이디를 입력해주세요.");
    return;
  } else if (possibleId.value == false) {
    alert("아이디 중복 검사를 해주세요.");
    return;
  } else if (param.value.userPwd == "") {
    alert("비밀번호를 입력해주세요.");
    return;
  } else if (param.value.userPwdChk == "") {
    alert("비밀번호 확인을 위해 다시 한 번 입력해주세요.");
    return;
  } else if (param.value.userPwd != param.value.userPwdChk) {
    alert("비밀번호가 같지 않습니다. 다시 입력해주세요.");
    return;
  } else if (param.value.emailId == "") {
    alert("이메일을 입력해주세요.");
    return;
  } else if (param.value.emailDomain == "") {
    alert("이메일을 입력해주세요.");
    return;
  }

  console.log("회원가입 할게요!");
  // axios API 호출
  join(
    param.value,
    ({ data }) => {
      console.log("success");
      alert("[성공] 회원가입이 정상 처리되었습니다.");
      router.push({ name: "main" });
    },
    (error) => {
      console.log(error);
      alert("[오류] 회원가입이 정상 처리되지 않았습니다.");
    }
  );
};

const goIdCheck = () => {
  let checkId = param.value.userId;
  console.log(checkId);
  let len = checkId.length;
  // console.log(len);
  if (len < 4 || len > 16) {
    possibleId.value = false;
    resultDivClass.value = "mb-3 fw-bold text-dark";
    resultDiv.value = "아이디는 4자이상 16자이하입니다.";
  } else {
    idCheck(
      checkId,
      ({ data }) => {
        // console.log("data : " + data);
        if (data == 0) {
          // 중복된 아이디가 없을 경우
          console.log("data : " + data);
          possibleId.value = true;
          resultDivClass.value = "mb-3 text-success";
          resultDiv.value = "<span class='fw-bold'>" + checkId + "</span>은 사용할 수 있습니다.";
        } else {
          // 중복된 아이디가 있을 경우
          possibleId.value = false;
          resultDivClass.value = "mb-3 text-danger";
          resultDiv.value = "<span class='fw-bold'>" + checkId + "</span>은 사용할 수 없습니다.";
        }
      },
      (error) => {
        console.log("fail");
      }
    );
  }
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">회원가입</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <form id="form-join" method="POST" action="">
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="radio"
              name="grade"
              id="basic"
              value="basic"
              checked="true"
              v-model="param.grade"
            />
            <label class="form-check-label" for="inlineRadio1">일반회원</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="radio"
              name="grade"
              id="enter"
              value="enter"
              v-model="param.grade"
            />
            <label class="form-check-label" for="inlineRadio2">기업회원</label>
          </div>
          <div class="form-check form-check-inline">
            <input
              class="form-check-input"
              type="radio"
              name="grade"
              id="admin"
              value="admin"
              v-model="param.grade"
            />
            <label class="form-check-label" for="inlineRadio2">관리자</label>
          </div>
          <div class="mb-3">
            <label for="username" class="form-label">이름 : </label>
            <input
              type="text"
              class="form-control"
              id="username"
              name="userName"
              placeholder="이름..."
              v-model="param.userName"
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
              v-model="param.userId"
              @keyup="goIdCheck"
            />
          </div>
          <!-- 아이디 중복 검사 결과 -->
          <div id="result-view" :class="resultDivClass" v-html="resultDiv"></div>
          <div class="mb-3">
            <label for="userpwd" class="form-label">비밀번호 : </label>
            <input
              type="password"
              class="form-control"
              id="userpwd"
              name="userPwd"
              placeholder="비밀번호..."
              v-model="param.userPwd"
            />
          </div>
          <div class="mb-3">
            <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
            <input
              type="password"
              class="form-control"
              id="pwdcheck"
              placeholder="비밀번호확인..."
              v-model="param.userPwdChk"
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
                v-model="param.emailId"
              />
              <span class="input-group-text">@</span>
              <select
                class="form-select"
                id="emaildomain"
                name="emailDomain"
                aria-label="이메일 도메인 선택"
                v-model="param.emailDomain"
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
            <button
              type="button"
              id="btn-join"
              class="btn btn-outline-primary mb-3"
              @click="memberJoin"
            >
              회원가입
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.form-check {
  margin-top: 10px;
  margin-bottom: 15px;
}
</style>
