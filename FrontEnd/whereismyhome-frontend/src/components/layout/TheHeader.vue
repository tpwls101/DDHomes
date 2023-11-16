<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import MemberLogin from "@/components/member/MemberLogin.vue";

const router = useRouter();

// 게시판 이동
function boardList(boardType) {
  router.push({ name: "board-list", params: { boardType } });
}

// 게시물 작성 이동
function writeArticle(boardType) {
  router.push({ name: "board-write", params: { boardType } });
}

// 회원가입 이동
function join() {
  router.push({ name: "member-join" });
}

// 마이페이지 이동
function myPage() {
  router.push({ name: "member-mypage" });
}
</script>

<template>
  <!-- header start -->
  <header>
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
      <div class="container-fluid">
        <ul class="navbar-nav me-5">
          <li class="nav-item">
            <a class="nav-link active" href="/"
              ><img id="home-img" src="@/assets/homes_logo.png"
            /></a>
          </li>
        </ul>
        <!-- board start-------------------------------------------------------------------------------- -->
        <ul class="navbar-nav ms-5 me-auto">
          <li class="nav-item">
            <!-- <a class="nav-link" href="/board/board-list">공지사항</a> -->
            <a class="nav-link" @click="boardList('announcement')">공지사항</a>
          </li>
          <li class="nav-item">
            <!-- <a class="nav-link" href="/board/board-list">게시판</a> -->
            <a class="nav-link" @click="boardList('information')">게시판</a>
          </li>
        </ul>
        <!-- board end-------------------------------------------------------------------------------- -->
        <ul class="navbar-nav me-2">
          <!-- <%-- <c:choose> --%> -->
          <!-- <%-- <c:when test="${not empty userinfo}"> --%> -->
          <!-- <c:if test="${not empty userinfo}"> -->
          <li id="nav-myPage" class="nav-item">
            <div class="dropdown dropstart">
              <button
                class="nav-link btn btn-secondary dropdown-toggle"
                type="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
              >
                <img id="user-img" src="@/assets/user.png" />
              </button>
              <ul class="dropdown-menu">
                <li>
                  <h6 class="dropdown-header">xx님, 안녕하세요.</h6>
                </li>
                <li>
                  <hr class="dropdown-divider" />
                </li>
                <!-- <c:if test="${userinfo.grade eq 'admin'}"> -->
                <li>
                  <a id="write" class="dropdown-item" @click="writeArticle('announcement')"
                    >글쓰기</a
                  >
                </li>
                <!-- </c:if> -->
                <li>
                  <a id="myPage" class="dropdown-item" @click="myPage()">마이페이지</a>
                </li>
                <li><a id="logout" class="dropdown-item">로그아웃</a></li>
              </ul>
            </div>
          </li>
          <!-- </c:if> -->
          <!-- <%-- </c:when> --%> -->
          <!-- <%-- <c:otherwise> --%> -->
          <!-- <c:if test="${empty userinfo}"> -->
          <li id="nav-signUp" class="nav-item">
            <a class="nav-link" href="#" @click="join()">회원가입</a>
          </li>
          <li id="nav-signIn" class="nav-item">
            <a class="nav-link" data-bs-toggle="modal" data-bs-target="#loginModal">로그인</a>
            <!-- 로그인 Modal -->
            <MemberLogin></MemberLogin>
          </li>
          <!-- </c:if> -->
          <!-- <%-- </c:otherwise> --%> -->
          <!-- <%-- </c:choose> --%> -->
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
