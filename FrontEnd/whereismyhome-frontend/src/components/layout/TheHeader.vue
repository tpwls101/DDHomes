<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "../../stores/member";
import { storeToRefs } from "pinia";
import MemberLogin from "@/components/member/MemberLogin.vue";

const router = useRouter();

const memberStore = useMemberStore();

const { userInfo, isLogin } = storeToRefs(memberStore);
const { getUserInfo, userLogout } = memberStore;

// 마우스 오버 이벤트 관련
const mouseEventClass = ref("navbar navbar-expand-sm bg-dark navbar-dark");
const mouseEventImgSrc = ref(new URL(`@/assets/ddhomes_logo_dark.png`, import.meta.url).href);

function doMouseOver() {
  mouseEventClass.value = "navbar navbar-expand-sm bg-light navbar-light";
  mouseEventImgSrc.value = new URL(`@/assets/ddhomes_logo_light.png`, import.meta.url).href;
}

function doMouseLeave() {
  mouseEventClass.value = "navbar navbar-expand-sm bg-dark navbar-dark";
  mouseEventImgSrc.value = new URL(`@/assets/ddhomes_logo_dark.png`, import.meta.url).href;
}

// 게시판 이동
function boardList(boardType) {
  if (boardType === "information") {
    if (userInfo.value == null) {
      alert("로그인 후 이용해 주세요!");
    } else {
      router.push({ name: "board-list", params: { boardType } });
    }
  } else {
    router.push({ name: "board-list", params: { boardType } });
  }
}

// 게시물 작성 이동
function writeArticle(boardType) {
  router.push({ name: "board-write", params: { boardType } });
}

// 매물 등록 이동
function registForsale() {
  router.push({ name: "forsale-regist" });
}

// 매물 리스트 이동
function listForsale() {
  router.push({ name: "forsale-list" });
}

// 매물 찜목록 이동
function favorite() {
  router.push({ name: "forsale-favorite" });
}

// 회원가입 이동
function join() {
  router.push({ name: "member-join" });
}

// 로그아웃 버튼 클릭 시
function logout() {
  console.log(userInfo.value.userId + "님 로그아웃 하실게여");
  userLogout(userInfo.value.userId);
  router.push({ name: "main" });
}

// 마이페이지 이동
function myPage() {
  router.push({ name: "member-mypage" });

  // accessToken 유효기간 확인하기 위해 임시로 작성
  let token = sessionStorage.getItem("accessToken"); // accessToken
  getUserInfo(token);
  console.log("accessToken : " + sessionStorage.getItem("accessToken"));
  console.log("refreshToken : " + sessionStorage.getItem("refreshToken"));
}
</script>

<template>
  <!-- header start -->
  <header>
    <nav :class="mouseEventClass" @mouseover="doMouseOver" @mouseleave="doMouseLeave">
      <div class="container-fluid">
        <ul class="navbar-nav me-5">
          <li class="nav-item">
            <a class="nav-link active" href="/"><img id="home-img" :src="mouseEventImgSrc" /></a>
          </li>
        </ul>
        <!-- board start-------------------------------------------------------------------------------- -->
        <ul class="navbar-nav ms-5 me-auto">
          <li class="nav-item">
            <a class="nav-link" @click="boardList('announcement')">공지사항</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" @click="boardList('information')">게시판</a>
          </li>
          <template v-if="userInfo != null">
            <li v-if="userInfo.grade == 'admin' || userInfo.grade == 'enter'" class="nav-item">
              <a class="nav-link" @click="registForsale">매물 등록</a>
            </li>
          </template>
        </ul>
        <!-- board end-------------------------------------------------------------------------------- -->

        <!-- userInfo에 로그인 정보가 있을 경우 -->
        <ul class="navbar-nav me-2" v-if="userInfo != null">
          <li id="nav-myPage" class="nav-item">
            <div class="dropdown dropstart">
              <button class="nav-link btn btn-secondary dropdown-toggle" type="button" data-bs-toggle="dropdown"
                aria-expanded="false">
                <img id="user-img" src="@/assets/user.png" />
              </button>
              <ul class="dropdown-menu">
                <li>
                  <h6 class="dropdown-header">{{ userInfo.userName }}님, 안녕하세요.</h6>
                </li>
                <li>
                  <hr class="dropdown-divider" />
                </li>
                <li>
                  <a id="write" class="dropdown-item" @click="writeArticle('information')">글쓰기</a>
                </li>
                <li v-if="userInfo.grade == 'admin' || userInfo.grade == 'enter'" class="nav-item">
                  <a class="dropdown-item" @click="listForsale">매물 목록</a>
                </li>
                <li>
                  <a id="favorite" class="dropdown-item" @click="favorite">찜 목록</a>
                </li>
                <li>
                  <a id="myPage" class="dropdown-item" @click="myPage()">마이페이지</a>
                </li>
                <li><a id="logout" class="dropdown-item" @click="logout">로그아웃</a></li>
              </ul>
            </div>
          </li>
        </ul>
        <!-- userInfo에 로그인 정보가 없을 경우 -->
        <ul class="navbar-nav me-2" v-else>
          <li id="nav-signUp" class="nav-item">
            <a class="nav-link" @click="join()">회원가입</a>
          </li>
          <li id="nav-signIn" class="nav-item">
            <a class="nav-link" data-bs-toggle="modal" data-bs-target="#loginModal">로그인</a>
            <!-- 로그인 Modal -->
            <MemberLogin></MemberLogin>
          </li>
        </ul>
      </div>
    </nav>
  </header>
  <!-- header end -->
</template>

<style scoped>
#home-img {
  width: 100px;
}

#user-img {
  width: 35px;
}
</style>
