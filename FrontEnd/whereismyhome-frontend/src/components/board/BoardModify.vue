<script setup>
import { getModifyArticle, modifyArticle } from "@/api/board";
import { useRouter, useRoute } from "vue-router";
import { ref, onMounted } from "vue";

const router = useRouter();
const route = useRoute();

const { articleNo } = route.params;

const article = ref({});

onMounted(() => {
  getArticle();
});

// 게시글 번호로 게시물 얻어오기
const getArticle = () => {
  // API 호출
  getModifyArticle(
    articleNo,
    ({ data }) => {
      // console.log(data);
      article.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

// 글 수정
const ArticleModify = () => {
  // 입력한 데이터 체크
  if (article.value.userId === "") {
    alert("[오류] 사용자가 확인되지 않습니다. 로그인 해 주세요!");
    return;
  } else if (article.value.subject === "") {
    alert("[오류] 제목을 입력 해 주세요!");
    return;
  } else if (article.value.content === "") {
    alert("[오류] 내용을 입력 해 주세요!");
    return;
  } else if (article.value.boardType === "") {
    alert("[오류] 게시판 종류를 선택해 주세요!");
    return;
  }

  modifyArticle(
    article.value,
    ({ data }) => {
      console.log(data);
      let articleNo = article.articleNo;
      router.push({ name: "board-detail", params: { articleNo } });
    },
    (error) => {
      console.log(error);
    }
  );
};

// 게시물 목록으로 이동
function boardList(boardType) {
  router.push({ name: "board-list", params: { boardType } });
}
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">글수정</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <form id="form-modify" method="POST" action="">
        <div>
          <label for="boardType" class="form-label">{{ article.boardType }}</label>
        </div>
        <div class="mb-3">
          <label for="subject" class="form-label">제목 : </label>
          <input
            type="text"
            class="form-control"
            id="subject"
            name="subject"
            v-model="article.subject"
          />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용 : </label>
          <textarea
            class="form-control"
            id="content"
            name="content"
            rows="7"
            v-model="article.content"
          ></textarea>
        </div>
        <div class="col-auto text-center">
          <button
            type="button"
            id="btn-modify"
            class="btn btn-outline-primary mb-3"
            @click="ArticleModify"
          >
            글수정
          </button>
          <button
            type="button"
            id="btn-list"
            class="btn btn-outline-danger mb-3"
            @click="boardList(article.boardType)"
          >
            목록으로이동...
          </button>
        </div>

        <input type="hidden" name="articleNo" value="${article.articleNo}" />
        <input type="hidden" name="boardType" value="${article.boardType}" />
      </form>
    </div>
  </div>
</template>

<style scoped></style>
