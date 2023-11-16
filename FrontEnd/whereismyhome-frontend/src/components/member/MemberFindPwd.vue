<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { findPwd } from "@/api/member";

const router = useRouter();

const userId = ref("");
const userName = ref("");
const emailId = ref("");
const emailDomain = ref("");
const userPwd = ref("");

// 아이디찾기 폼의 input 태그로 입력받은 값(파라미터)
const param = ref({
  userId: "",
  userName: "",
  emailId: "",
  emailDomain: "",
});

const memberFindPwd = () => {
  console.log("비밀번호 찾아주세요!");
  console.log("param : " + param);
  console.log(param.value.userId);

  // axios API 호출
  findPwd(
    param.value,
    ({ data }) => {
      userId.value = data.userId;
      userName.value = data.userName;
      emailId.value = data.emailId;
      emailDomain.value = data.emailDomain;
      userPwd.value = data.userPwd;
      console.log("success");
      alert(
        "[성공] " +
          userName.value +
          "님의 임시 비밀번호는 " +
          userPwd.value +
          "입니다. 로그인 후 비밀번호를 변경하세요."
      );
      router.push({ name: "main" });
    },
    (error) => {
      console.log(error);
      alert("[오류] 해당하는 회원 정보가 없습니다.");
    }
  );
};
</script>

<template>
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
              v-model="param.userId"
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
              v-model="param.userName"
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
              id="btn-find-password"
              class="btn btn-outline-primary mb-3"
              @click="memberFindPwd"
            >
              비밀번호찾기
            </button>
            <button type="button" id="btn-go-back" class="btn btn-outline-success mb-3">
              돌아가기
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
