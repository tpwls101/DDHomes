<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

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
                  <input type="text" name="userId" id="userId" autofocus="autofocus" />
                </td>
              </tr>
              <tr>
                <td>비밀번호</td>
                <td>
                  <input type="password" name="userPwd" id="userPwd" />
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
            >
              아이디 찾기
            </button>
            <button
              type="button"
              id="findPwdBtn"
              class="btn btn-outline-dark btn-sm"
              @click="findPwd"
            >
              비밀번호 찾기
            </button>
          </div>
          <div>
            <button type="button" id="loginSubmitBtn" class="btn btn-success">로그인</button>
            <button type="button" class="btn btn-danger" data-bs-dismiss="modal">닫기</button>
          </div>
        </div>
        <!-- Modal footer end -->
      </div>
    </div>
  </div>
</template>

<style scoped></style>
