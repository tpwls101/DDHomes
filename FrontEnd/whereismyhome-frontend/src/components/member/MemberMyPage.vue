<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { updateMyPage, deleteMember } from "@/api/member";
import { useMemberStore } from "@/stores/member";

const router = useRouter();
const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);
const { userLogout } = memberStore;

// 회원 정보 수정할 때 보낼 dto
const memberDto = ref({
  userId: userInfo.value.userId,
  userName: userInfo.value.userName,
  userPwd: "",
  userPwdChk: "",
  emailId: userInfo.value.emailId,
  emailDomain: userInfo.value.emailDomain,
});

onMounted(() => {
  getMyPage();
});

// 주의사항 : 메서드 이름과 axios API의 이름이 동일하면 에러!!
// 마이페이지 호출
const getMyPage = () => {
  console.log(userInfo.value);

  if (userInfo.value == null) {
    alert("접근할 수 없는 페이지입니다.");
    router.push({ name: "main" });
  }

  console.log("서버에서 마이페이지 정보 얻어오자!");
  // axios API 호출
  // myPage(
  //   //param.value,
  //   ({ data }) => {
  //     memberDto.value = data;
  //     console.log(memberDto.value);
  //     console.log("success");
  //   },
  //   (error) => {
  //     console.log(error);
  //   }
  // );
};

// 마이페이지 - 회원 정보 수정
const modifyMyPage = () => {
  if (memberDto.value.userName == "") {
    alert("이름을 입력해주세요.");
    return;
  } else if (memberDto.value.userPwd == "") {
    alert("비밀번호를 입력해주세요.");
    return;
  } else if (memberDto.value.userPwdChk == "") {
    alert("비밀번호 확인을 위해 다시 한 번 입력해주세요.");
    return;
  } else if (memberDto.value.userPwd != memberDto.value.userPwdChk) {
    alert("비밀번호가 같지 않습니다. 다시 입력해주세요.");
    return;
  } else if (memberDto.value.emailId == "") {
    alert("이메일을 입력해주세요.");
    return;
  } else if (memberDto.value.emailDomain == "") {
    alert("이메일을 입력해주세요.");
    return;
  }

  console.log("마이페이지에서 회원 정보 수정할게요!");
  console.log("memberDto 확인 !!");
  console.log(memberDto.value);

  // axios API 호출
  updateMyPage(
    memberDto.value,
    ({ data }) => {
      console.log("userinfo 확인!!!");
      console.log(userInfo.value);
      console.log("success");
      alert("[성공] 회원 정보가 성공적으로 수정되었습니다.");

      router.push({ name: "member-mypage" });
      console.log("회원 정보 수정 후 마이페이지로 돌아오기");
    },
    (error) => {
      console.log(error);
      alert("[오류] 회원 정보 변경에 실패했습니다.");
    }
  );
};

// 마이페이지 - 회원 탈퇴
const memberDelete = () => {
  console.log("회원 탈퇴할게요!");
  if (confirm("탈퇴하시겠습니까?")) {
    // axios API 호출
    deleteMember(
      userInfo.value.userId,
      ({ data }) => {
        console.log("success");
        alert("[성공] 회원 탈퇴가 완료되었습니다.");
        // userInfo, access/refresh Token, memberStore의 데이터 삭제하기 위해 로그아웃 진행
        userLogout();
        router.push({ name: "main" });
      },
      (error) => {
        console.log(error);
        alert("[실패] 회원 탈퇴에 실패하였습니다.");
        router.push({ name: "member-mypage" });
      }
    );
  } else {
    console.log("회원 탈퇴 취소");
    router.push({ name: "member-mypage" });
  }
};

const goBack = () => {
  router.push({ name: "main" });
};
</script>

<template>
  <div class="container" v-if="userInfo != null">
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">마이페이지</mark>
        </h2>
      </div>
      <h6 class="text-center">마이페이지에서 회원 정보를 수정할 수 있습니다.</h6>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <form id="form-update" method="POST" action="">
          <div>
            <label for="grade" class="form-label">현재 등급 : {{ userInfo.grade }}</label>
          </div>
          <div class="mb-3">
            <label for="userid" class="form-label">아이디 : </label>
            <input
              type="text"
              class="form-control"
              id="userid"
              name="userId"
              placeholder="아이디..."
              v-model="userInfo.userId"
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
              v-model="memberDto.userName"
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
              v-model="memberDto.userPwd"
            />
          </div>
          <div class="mb-3">
            <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
            <input
              type="password"
              class="form-control"
              id="pwdcheck"
              placeholder="비밀번호확인..."
              v-model="memberDto.userPwdChk"
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
                v-model="memberDto.emailId"
              />
              <span class="input-group-text">@</span>
              <select
                class="form-select"
                id="emaildomain"
                name="emailDomain"
                aria-label="이메일 도메인 선택"
                v-model="memberDto.emailDomain"
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
            <button
              type="button"
              id="btn-update"
              class="btn btn-outline-primary mb-3"
              @click="modifyMyPage"
            >
              정보수정
            </button>
            <button
              type="button"
              id="btn-go-back"
              class="btn btn-outline-success mb-3"
              @click="goBack"
            >
              돌아가기
            </button>
            <button
              type="button"
              id="btn-withdraw"
              class="btn btn-outline-danger mb-3"
              @click="memberDelete"
            >
              탈퇴하기
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
