<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { registComment, modifyComment } from "@/api/board";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const route = useRoute();
const memberStore = useMemberStore();

const { userInfo, isLogin } = storeToRefs(memberStore);

// 댓글이 작성될 게시물 번호
const { articleNo } = route.params;

// 댓글 수정인 경우 받아온다
const props = defineProps({ comment: Object });

// 댓글 textarea 기본값
const textAreaPlaceholder = ref("내용을 입력하세요");

// 댓글 등록, 수정 시 사용할 파라미터
const param = ref({});

onMounted(() => {
  if (userInfo.value == null) {
    textAreaPlaceholder.value = "댓글기능을 사용하려면 로그인 하세요!";
  } else {
    param.value = {
      articleNo: articleNo,
      userId: userInfo.value.userId,
      content: "",
    };
  }
});

// 댓글 등록버튼 클릭 시
function commentRegist() {
  // console.log(param.value);
  if (!isLogin.value) {
    alert("댓글 기능을 사용하려면 로그인 하세요!");
    return;
  }

  if (param.value.content === "") {
    alert("[오류] 댓글을 입력해 주세요!");
    return;
  }

  registComment(
    param.value,
    ({ data }) => {
      // console.log(data);
      param.value.content = "";
      // location.reload();
      clickRegistButton();
    },
    (error) => {
      console.log(error);
    }
  );
}

// 댓글 수정버튼 클릭 시
function commentModify() {
  // 수정 위한 파라미터 만들기
  let params = {
    commentNo: props.comment.commentNo,
    content: props.comment.content,
  };

  modifyComment(
    params,
    ({ data }) => {
      // console.log(data);
      // location.reload();
      // 수정 완료 후 컴포넌트 전환
      document
        .querySelector("#comment" + props.comment.commentNo)
        .classList.remove("comment-hidden");
      document.querySelector("#regist" + props.comment.commentNo).classList.add("comment-hidden");
    },
    (error) => {
      console.log(error);
    }
  );
}

const emit = defineEmits(["cancelModify", "clickRegistButton"]);

// 댓글 수정 취소시 해당 댓글의 번호를 넘겨줌
function cancelModify() {
  emit("cancelModify", props.comment.commentNo);
}

// 댓글 등록시 댓글 목록 다시 불러오게 하기
function clickRegistButton() {
  emit("clickRegistButton");
}
</script>

<template>
  <div class="comment-item-container">
    <p>
      <span v-if="isLogin" class="fw-bold">작성자: {{ param.userId }}</span> <br />
    </p>
    <textarea
      v-if="props.comment == null"
      id="comment-textarea"
      :placeholder="textAreaPlaceholder"
      v-model="param.content"
      :readonly="!isLogin"
    ></textarea>
    <textarea
      v-else
      id="comment-textarea"
      placeholder="textAreaPlaceholder"
      v-model="props.comment.content"
      :readonly="!isLogin"
    ></textarea>
    <div v-if="props.comment == null" class="comment-btn-container">
      <input id="btn-regist" type="button" value="등록" @click="commentRegist" />
    </div>
    <div v-else class="comment-btn-container">
      <input id="btn-modify" type="button" value="수정" @click="commentModify" />
      <input id="btn-cancel" type="button" value="취소" @click="cancelModify" />
    </div>
  </div>
</template>

<style scoped>
.comment-item-container {
  width: 80%;
  padding: 20px;
}

.comment-btn-container {
  display: flex;
  justify-content: right;
}

.comment-btn-container > * {
  margin: 5px;
}

#comment-textarea {
  resize: none;
  width: 100%;
  height: 80px;
}

#btn-regist {
  float: right;
}
</style>
