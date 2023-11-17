<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle, listComment } from "@/api/board";
import BoardCommentItem from "@/components/board/item/BoardCommentItem.vue";
import BoardCommentRegistItem from "@/components/board/item/BoardCommentRegistItem.vue";

const route = useRoute();
const router = useRouter();

const { articleNo } = route.params;

// 게시판 상세정보
const article = ref({});
// 게시판 댓글들
const comments = ref([]);
// 이미지
const imgSrc = ref(new URL(`C:/ddhomes/upload/boardImage/231117/1495b9bd-23cb-4786-875a-b8b7cb786406.png`, import.meta.url).href);

onMounted(() => {
  getArticle();
  getComments();
});

// 게시글 번호로 게시물 얻어오기
const getArticle = () => {
  // API 호출
  detailArticle(
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

// 게시물 댓글 얻어오기
const getComments = () => {
  // console.log(articleNo);
  listComment(
    articleNo,
    ({ data }) => {
      // console.log(data);
      comments.value = data;
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
// 게시물 수정
function modifyArticle(articleNo) {
  router.push({ name: "board-modify", params: { articleNo } });
}
// 게시물 삭제
function articleDelete(articleNo) {
  deleteArticle(
    articleNo,
    ({ data }) => {
      console.log(data);
      boardList(article.value.boardType);
    },
    (error) => {
      console.log(error);
    }
  );
}

// BoardCommentItem에서 수정버튼 클릭시 발생하는 이벤트 처리
// BoardCommentItem을 숨기고 BoardCommentRegistItem을 보여줌
function commentModify(commentNo) {
  document.querySelector("#comment" + commentNo).classList.add("comment-hidden");
  document.querySelector("#regist" + commentNo).classList.remove("comment-hidden");
}
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">글보기</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <div class="row my-2">
        <h2 class="text-secondary px-5">{{ article.articleNo }}. {{ article.subject }}</h2>
      </div>
      <div class="row">
        <div class="col-md-8">
          <div class="clearfix align-content-center">
            <img class="avatar me-2 float-md-start bg-light p-2"
              src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg" />
            <p>
              <span class="fw-bold">{{ article.userId }}</span> <br />
              <span class="text-secondary fw-light">
                {{ article.registerTime }} 조회 : {{ article.hit }}
              </span>
            </p>
          </div>
        </div>
        <div class="col-md-4 align-self-center text-end">댓글 : {{ comments.length }}</div>
        <div class="divider mb-3"></div>
        <!-- <img :src="require(`C:/ddhomes/upload/boardImage/231117/1495b9bd-23cb-4786-875a-b8b7cb786406.png`)" /> -->
        <div v-html="article.content"></div>
        <div class="divider mt-3 mb-3"></div>
        <div class="d-flex justify-content-end">
          <button type="button" id="btn-list" class="btn btn-outline-primary mb-3" @click="boardList(article.boardType)">
            글목록
          </button>
          <!-- <c:if test="${userinfo.userId eq article.userId}"> -->
          <button type="button" id="btn-mv-modify" class="btn btn-outline-success mb-3 ms-1"
            @click="modifyArticle(articleNo)">
            글수정
          </button>
          <button type="button" id="btn-delete" class="btn btn-outline-danger mb-3 ms-1"
            @click="articleDelete(articleNo)">
            글삭제
          </button>
          <!-- </c:if> -->
        </div>
      </div>
    </div>
    <hr />
    <hr />
    <!-- 댓글 등록 -->
    <BoardCommentRegistItem :articleNo="articleNo"></BoardCommentRegistItem>
    <!-- 댓글 목록 -->
    <hr />
    <BoardCommentItem v-for="comment in comments" :key="comment.commentNo" :comment="comment"
      @commentModify="commentModify">
    </BoardCommentItem>
  </div>
</template>

<style scoped>
hr {
  width: 90%;
}
</style>
