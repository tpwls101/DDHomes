<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { registArticle } from "@/api/board";

const router = useRouter();
const route = useRoute();

onMounted(() => {
  boardDto.value.boardType = route.params.boardType;
});

// 파라미터로 넘길 반응형 데이터
const boardDto = ref({
  userId: "",
  subject: "",
  content: "",
  boardType: "",
});

const write = () => {
  // console.log(boardDto.value);
  registArticle(
    boardDto.value,
    ({ data }) => {
      // console.log(data);
      // 게시물 등록 성공 시 게시물 리스트로 이동
      let boardType = boardDto.value.boardType;
      router.push({ name: "board-list", params: { boardType } });
    },
    (error) => {
      console.log(error);
    }
  );
};
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">글쓰기</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <form id="form-register" method="POST" action="">
        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="radio"
            name="boardType"
            id="announcement"
            value="announcement"
            v-model="boardDto.boardType"
          />
          <label class="form-check-label" for="inlineRadio1">공지사항</label>
        </div>
        <div class="form-check form-check-inline">
          <input
            class="form-check-input"
            type="radio"
            name="boardType"
            id="information"
            value="information"
            v-model="boardDto.boardType"
          />
          <label class="form-check-label" for="inlineRadio2">정보</label>
        </div>

        <!-- 아래 userId는 로그인 기능 구현 시 바꿀것!!!!!!!!!!!!!!!!!!!!!!1 -->
        <div class="mb-3">
          <label for="userId" class="form-label">작성자 : </label>
          <input
            type="text"
            class="form-control"
            id="userId"
            name="userId"
            placeholder="작성자..."
            v-model="boardDto.userId"
          />
        </div>

        <div class="mb-3">
          <label for="subject" class="form-label">제목 : </label>
          <input
            type="text"
            class="form-control"
            id="subject"
            name="subject"
            placeholder="제목..."
            v-model="boardDto.subject"
          />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용 : </label>
          <textarea
            class="form-control"
            id="content"
            name="content"
            rows="7"
            v-model="boardDto.content"
          ></textarea>
        </div>
        <div class="col-auto text-center">
          <button
            type="button"
            id="btn-register"
            class="btn btn-outline-primary mb-3"
            @click="write"
          >
            글작성
          </button>
          <button type="reset" class="btn btn-outline-danger mb-3">초기화</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped></style>
