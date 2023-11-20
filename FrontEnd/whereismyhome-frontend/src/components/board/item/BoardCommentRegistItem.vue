<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { registComment, modifyComment } from "@/api/board";
import { useMemberStore } from '@/stores/member';
import { storeToRefs } from "pinia";

const route = useRoute();
const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

// 댓글이 작성될 게시물 번호
const { articleNo } = route.params;

// 댓글 수정인 경우 받아온다
const props = defineProps({ comment: Object });

const param = ref({
  articleNo: articleNo,
  userId: userInfo.value.userId,
  content: "",
});

// 댓글 등록버튼 클릭 시
function commentRegist() {
  // console.log(param.value);
  if (param.value.content === "") {
    alert("[오류] 댓글을 입력해 주세요!");
    return;
  }

  registComment(
    param.value,
    ({ data }) => {
      // console.log(data);
      param.value.content = "";
      location.reload();
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
      // 취소랑 똑같은거 호출하면 등록화면과 리스트 화면이 전환되게 함
      cancelModify();
    },
    (error) => {
      console.log(error);
    }
  );
}

const emit = defineEmits(["cancelModify"]);

// 댓글 수정 취소시 해당 댓글의 번호를 넘겨줌
function cancelModify() {
  emit("cancelModify", props.comment.commentNo);
}

// console.log(props.comment.commentNo);
</script>

<template>
  <!-- <h1>{{ props.comment.commentNo }}</h1> -->
  <div class="comment-item-container">
    <p>
      <span class="fw-bold">작성자: {{ param.userId }}</span> <br />
    </p>
    <textarea v-if="props.comment == null" id="comment-textarea" placeholder="내용을 입력하세요"
      v-model="param.content"></textarea>
    <textarea v-else id="comment-textarea" placeholder="내용을 입력하세요" v-model="props.comment.content"></textarea>
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

.comment-btn-container>* {
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
