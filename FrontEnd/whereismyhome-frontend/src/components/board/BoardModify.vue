<script setup>
import { getModifyArticle, modifyArticle } from "@/api/board";
import { useRouter, useRoute } from "vue-router";
import { ref, onMounted } from "vue";

const router = useRouter();
const route = useRoute();

const { articleNo } = route.params;

const boardDto = ref({});

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
      boardDto.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

// 글 수정
const ArticleModify = () => {
  // 입력한 데이터 체크
  if (boardDto.value.userId === "") {
    alert("[오류] 사용자가 확인되지 않습니다. 로그인 해 주세요!");
    return;
  } else if (boardDto.value.subject === "") {
    alert("[오류] 제목을 입력 해 주세요!");
    return;
  } else if (boardDto.value.content === "") {
    alert("[오류] 내용을 입력 해 주세요!");
    return;
  } else if (boardDto.value.boardType === "") {
    alert("[오류] 게시판 종류를 선택해 주세요!");
    return;
  }

  modifyArticle(
    boardDto.value,
    ({ data }) => {
      console.log(data);
      let articleNo = boardDto.articleNo;
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
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="boardType" id="announcement" value="announcement"
            v-model="boardDto.boardType" />
          <label class="form-check-label" for="inlineRadio1">공지사항</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="boardType" id="information" value="information"
            v-model="boardDto.boardType" />
          <label class="form-check-label" for="inlineRadio2">정보</label>
        </div>
        <div class="mb-3">
          <label for="subject" class="form-label">제목 : </label>
          <input type="text" class="form-control" id="subject" name="subject" v-model="boardDto.subject" />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용 : </label>
          <textarea class="form-control" id="content" name="content" rows="7" v-model="boardDto.content"></textarea>
        </div>
        <div>
          <p>첨부된 이미지</p>
          <div class="imginfo">
            파일1<input class="btn-delete-img" type="button" value="삭제">
          </div>
          <div class="imginfo">
            파일2<input class="btn-delete-img" type="button" value="삭제">
          </div>
        </div>
        <div class="col-auto text-center">
          <button type="button" id="btn-modify" class="btn btn-outline-primary mb-3" @click="ArticleModify">
            글수정
          </button>
          <button type="button" id="btn-list" class="btn btn-outline-danger mb-3" @click="boardList(boardDto.boardType)">
            목록으로이동...
          </button>
        </div>

        <input type="hidden" name="articleNo" value="${boardDto.articleNo}" />
        <input type="hidden" name="boardType" value="${boardDto.boardType}" />
      </form>
    </div>
  </div>
</template>

<style scoped>
#content {
  resize: none;
}

.imginfo {
  border: 1px solid black;
  height: 32px;
  margin: 10px;
}

.btn-delete-img {
  float: right;
}
</style>
