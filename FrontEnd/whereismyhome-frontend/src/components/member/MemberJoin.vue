<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { join } from "@/api/member";

const router = useRouter();

// 회원가입 폼의 input 태그로 입력받은 값(파라미터)
const param = ref({
  userId: "",
  userName: "",
  userPwd: "",
  emailId: "",
  emailDomain: "",
});

const memberJoin = () => {
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
            <input class="form-check-input" type="radio" name="basic" id="basic" value="basic" />
            <label class="form-check-label" for="inlineRadio1">일반회원</label>
          </div>
          <div class="form-check form-check-inline">
            <input class="form-check-input" type="radio" name="enter" id="enter" value="enter" />
            <label class="form-check-label" for="inlineRadio2">정보</label>
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

<style scoped></style>
