<script setup>
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
// const { changeMenuState } = useMenuStore();

// 사용자가 입력한 로그인 정보
const loginUser = ref({
  userId: "",
  userPwd: "",
});

// 로그인 버튼 클릭 시 실행되는 함수
const login = async () => {
  console.log("login ing!!!!!!!");
  await userLogin(loginUser.value); // memberStore에 있는 userLogin() 실행
  let token = sessionStorage.getItem("accessToken"); // accessToken
  console.log("1. session storage에 담긴 accessToken : ", token);
  console.log("isLogin: ", isLogin);
  if (isLogin) {
    console.log("로그인 성공!!!");
    getUserInfo(token); // accessToken을 가지고 사용자 정보 가져오기
  }
};

const findId = () => {
  router.push({ name: "member-findid" });
};

const findPwd = () => {
  router.push({ name: "member-findpwd" });
};
</script>

<template>
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
                  <input
                    type="text"
                    name="userId"
                    id="userId"
                    autofocus="autofocus"
                    v-model="loginUser.userId"
                  />
                </td>
              </tr>
              <tr>
                <td>비밀번호</td>
                <td>
                  <input type="password" name="userPwd" id="userPwd" v-model="loginUser.userPwd" />
                  <input type="checkbox" name="isShow" id="isShow" /><span id="showUserPwd"
                    >비밀번호 보이기</span
                  >
                </td>
              </tr>
            </table>
          </form>
        </div>
        <!-- Modal footer start -->
        <div class="modal-footer">
          <div class="me-auto">
            <button
              type="button"
              id="findIdBtn"
              class="btn btn-outline-dark btn-sm"
              @click="findId"
              data-bs-dismiss="modal"
            >
              아이디 찾기
            </button>
            <button
              type="button"
              id="findPwdBtn"
              class="btn btn-outline-dark btn-sm"
              @click="findPwd"
              data-bs-dismiss="modal"
            >
              비밀번호 찾기
            </button>
          </div>
          <div>
            <button
              type="button"
              id="loginSubmitBtn"
              class="btn btn-success"
              data-bs-dismiss="modal"
              @click="login"
            >
              로그인
            </button>
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
          </div>
        </div>
        <!-- Modal footer end -->
      </div>
    </div>
  </div>
</template>

<style scoped>
.close-modal {
  display: none;
}
</style>
