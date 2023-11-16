<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registComment, modifyComment } from "@/api/board";

const route = useRoute();
const router = useRouter();

// 댓글이 작성될 게시물 번호
const { articleNo } = route.params;

// 댓글 수정인 경우 받아온다
const props = defineProps({ comment: Object });

const param = ref({
  articleNo: articleNo,
  // 로그인한 유저로 변경 필요!!!!!!!!!!!!!!!!!!
  userId: "ssafy",
  content: "",
});

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
      location.reload();
    },
    (error) => {
      console.log(error);
    }
  );

  console.log(params);
}

// console.log(props.comment.commentNo);
</script>

<template>
  <!-- <h1>{{ props.comment.commentNo }}</h1> -->
  <div class="comment-item-container">
    <p>
      <span class="fw-bold">작성자: {{ param.userId }}</span> <br />
    </p>
    <textarea
      v-if="props.comment == null"
      id="comment-textarea"
      placeholder="내용을 입력하세요"
      v-model="param.content"
    ></textarea>
    <textarea
      v-else
      id="comment-textarea"
      placeholder="내용을 입력하세요"
      v-model="props.comment.content"
    ></textarea>
    <input
      v-if="props.comment == null"
      id="btn-regist"
      type="button"
      value="등록"
      @click="commentRegist"
    />
    <input v-else id="btn-modify" type="button" value="수정" @click="commentModify" />
  </div>
</template>

<style scoped>
.comment-item-container {
  width: 80%;
  padding: 20px;
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
