<script setup>
import { getModifyArticle, modifyArticle } from "@/api/board";
import { useRouter, useRoute } from "vue-router";
import { ref, onMounted } from "vue";
import FileUpload from '@/components/common/FileUpload.vue';

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

// 입력한 데이터 체크
function isValidInput() {
  if (boardDto.value.userId === "") {
    alert("[오류] 사용자가 확인되지 않습니다. 로그인 해 주세요!");
    return false;
  } else if (boardDto.value.subject === "") {
    alert("[오류] 제목을 입력 해 주세요!");
    return false;
  } else if (boardDto.value.content === "") {
    alert("[오류] 내용을 입력 해 주세요!");
    return false;
  } else if (boardDto.value.boardType === "") {
    alert("[오류] 게시판 종류를 선택해 주세요!");
    return false;
  }

  return true;
}

// 글 수정
const ArticleModify = () => {
  // 입력 데이터 누락 확인
  if (!isValidInput()) {
    return;
  }

  // 파일 확인
  const imgInfos = document.querySelector("#upload-img").files;

  // 게시판 등록 위한 boardDto와 파일인 imgInfos를 하나의 파라미터로 등록
  const modifyParams = {
    boardDto: boardDto.value,
    imgInfos: imgInfos,
  };

  modifyArticle(
    modifyParams,
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
  if (confirm("목록으로 돌아가면 작성한 글이 모두 사라집니다.\n이동 하시겠습니까?")) {
    router.push({ name: "board-list", params: { boardType } });
  }
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
        <FileUpload :articleNo="articleNo"></FileUpload>
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
</style>
