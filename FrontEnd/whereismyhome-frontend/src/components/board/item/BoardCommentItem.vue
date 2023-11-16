<script setup>
import { ref } from "vue";
import { likeComment, deleteComment } from "@/api/board";

const props = defineProps({ comment: Object });
const likeId = ref("like-no-click");
const param = ref({
  likeId: "",
  commentNo: "",
});

// 좋아요 버튼 클릭 시
// 좋아요 한번만 가능하게 바꾸기!!!!!!!!!!
function likeBtnClick() {
  // 없음 -> 좋아요
  if (likeId.value == "like-no-click") {
    likeId.value = "like-click";
    // console.log(props.comment.commentNo);
  }
  // 좋아요 -> 없음
  else {
    likeId.value = "like-no-click";
  }

  param.value.likeId = likeId.value;
  param.value.commentNo = props.comment.commentNo;
  likeComment(
    param.value,
    ({ data }) => {
      // console.log(data);
      if (likeId.value == "like-click") {
        props.comment.like += 1;
      } else {
        props.comment.like -= 1;
      }
    },
    (error) => {
      console.log(error);
    }
  );
}

// 삭제 버튼 클릭 시
function commentDelete(commentNo) {
  deleteComment(
    commentNo,
    ({ data }) => {
      console.log(data);
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
      <span class="fw-bold">작성자: {{ comment.userId }}</span> <br />
      <span class="text-secondary fw-light">게시일: {{ comment.registerTime }} </span>
      <span id="like" class="text-dark"
        >좋아요: {{ comment.like }}
        <svg
          :id="likeId"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 512 512"
          @click="likeBtnClick"
        >
          <path
            d="M313.4 32.9c26 5.2 42.9 30.5 37.7 56.5l-2.3 11.4c-5.3 26.7-15.1 52.1-28.8 75.2H464c26.5 0 48 21.5 48 48c0 18.5-10.5 34.6-25.9 42.6C497 275.4 504 288.9 504 304c0 23.4-16.8 42.9-38.9 47.1c4.4 7.3 6.9 15.8 6.9 24.9c0 21.3-13.9 39.4-33.1 45.6c.7 3.3 1.1 6.8 1.1 10.4c0 26.5-21.5 48-48 48H294.5c-19 0-37.5-5.6-53.3-16.1l-38.5-25.7C176 420.4 160 390.4 160 358.3V320 272 247.1c0-29.2 13.3-56.7 36-75l7.4-5.9c26.5-21.2 44.6-51 51.2-84.2l2.3-11.4c5.2-26 30.5-42.9 56.5-37.7zM32 192H96c17.7 0 32 14.3 32 32V448c0 17.7-14.3 32-32 32H32c-17.7 0-32-14.3-32-32V224c0-17.7 14.3-32 32-32z"
          />
        </svg>
      </span>
    </p>
    <p>{{ comment.content }}</p>
  </div>
  <div class="comment-btn-container">
    <input type="button" value="수정" />
    <input type="button" value="삭제" @click="commentDelete(comment.commentNo)" />
  </div>
  <hr />
</template>

<style scoped>
.comment-item-container {
  width: 80%;
  padding: 20px;
}

.comment-btn-container {
  display: flex;
  justify-content: right;
  padding-right: 10%;
}

.comment-btn-container > * {
  margin: 5px;
}

#like {
  float: right;
}

#like-no-click {
  height: 30px;
  width: 30px;
  fill: #ddd;
}
#like-click {
  height: 30px;
  width: 30px;
  fill: blue;
}

hr {
  width: 90%;
}
</style>
