<script setup>
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { registComment } from "@/api/board";

const route = useRoute();
const router = useRouter();

const { articleNo } = route.params;

const param = ref({
  articleNo: articleNo,
  // 로그인한 유저로 변경 필요!!!!!!!!!!!!!!!!!!
  userId: "testuser",
  content: "",
});

function commentRegist() {
  // console.log(param.value);

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
</script>

<template>
  <div class="comment-item-container">
    <p>
      <span class="fw-bold">작성자: {{ param.userId }}</span> <br />
    </p>
    <textarea
      id="comment-textarea"
      placeholder="내용을 입력하세요"
      v-model="param.content"
    ></textarea>
    <input id="btn-regist" type="button" value="등록" @click="commentRegist" />
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
