<script setup>
import { ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { registArticle } from "@/api/board";
import { useMemberStore } from "../../stores/member";
import { storeToRefs } from "pinia";
import FileUpload from "@/components/common/FileUpload.vue";

const router = useRouter();
const route = useRoute();
const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

// admin등급 회원의 경우 글 작성시 게시판 타입 라디오버튼 활성화
const isAdmin = ref(false);

onMounted(() => {
  // 게시물 타입 설정
  boardDto.value.boardType = route.params.boardType;
  // 게시자 아이디 설정
  if (userInfo.value != null) {
    boardDto.value.userId = userInfo.value.userId;

    if (userInfo.value.grade === "admin") {
      isAdmin.value = true;
    }
  }
});

// 파라미터로 넘길 반응형 데이터
const boardDto = ref({
  userId: "",
  subject: "",
  content: "",
  boardType: "",
});

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

// 글 작성
const write = () => {
  // 입력 데이터 누락 확인
  if (!isValidInput()) {
    return;
  }

  // 파일 확인
  const imgInfos = document.querySelector("#upload-img").files;

  // 게시판 등록 위한 boardDto와 파일인 imgInfos를 하나의 파라미터로 등록
  const params = {
    boardDto: boardDto.value,
    imgInfos: imgInfos,
  };

  // axios
  registArticle(
    params,
    ({ data }) => {
      // console.log(data);
      // 리스트로 이동
      let boardType = boardDto.value.boardType;
      router.push({ name: "board-list", params: { boardType } });
    },
    (error) => {
      alert("[오류] 게시물 업로드에 문제가 발생했습니다.");
      console.log(error);
    }
  );
};

// 파일 업로드 진행
// function imgUpload() {
//   // 파일 확인
//   const imgInfos = document.querySelector("#upload-img").files;

//   // 파일이 존재할 경우 파일 먼저 업로드 진행
//   if (imgInfos.length > 0) {
//     uploadImg(
//       imgInfos,
//       ({ data }) => {
//         console.log(data);
//       },
//       (error) => {
//         alert("[오류] 파일 업로드에 문제가 발생했습니다.");
//         console.log(error);
//       }
//     )
//   }
// }
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
          <input class="form-check-input" type="radio" name="boardType" id="announcement" value="announcement"
            v-model="boardDto.boardType" :disabled="!isAdmin" />
          <label class="form-check-label" for="inlineRadio1">공지사항</label>
        </div>
        <div class="form-check form-check-inline">
          <input class="form-check-input" type="radio" name="boardType" id="information" value="information"
            v-model="boardDto.boardType" />
          <label class="form-check-label" for="inlineRadio2">정보</label>
        </div>

        <div class="mb-3">
          <p>작성자 : {{ boardDto.userId }}</p>
        </div>

        <div class="mb-3">
          <label for="subject" class="form-label">제목 : </label>
          <input type="text" class="form-control" id="subject" name="subject" placeholder="제목..."
            v-model="boardDto.subject" />
        </div>
        <div class="mb-3">
          <label for="content" class="form-label">내용 : </label>
          <textarea class="form-control" id="content" name="content" rows="7" v-model="boardDto.content"></textarea>
        </div>
        <FileUpload></FileUpload>
        <div class="col-auto text-center">
          <button type="button" id="btn-register" class="btn btn-outline-primary mb-3" @click="write">
            글작성
          </button>
          <!-- <button type="reset" class="btn btn-outline-danger mb-3">초기화</button> -->
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
#content {
  resize: none;
}
</style>
