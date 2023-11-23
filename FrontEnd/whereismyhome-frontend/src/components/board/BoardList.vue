<script setup>
import { ref, watch, onUpdated, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import { listArticle } from "@/api/board";
import { useMemberStore } from "../../stores/member";
import { storeToRefs } from "pinia";
import PageNavigation from "@/components/common/PageNavigation.vue";
import VSelect from "@/components/common/VSelect.vue";
import BoardListItem from "@/components/board/item/BoardListItem.vue";

const router = useRouter();
const route = useRoute();
const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;

// 게시판 목록
const articles = ref([]);
// 총 페이지
const totalPageCount = ref(1);
// 페이지 내비게이션
const pageNavigation = ref();
// 게시판 목록 가져올 때 필요한 파라미터 목록
const param = ref({
  boardType: "",
  sortCondition: "",
  pgno: 1,
  spp: VITE_ARTICLE_LIST_SIZE,
});

onUpdated(() => {
  param.value.boardType = route.params.boardType;
});

watch(
  () => param.value.boardType,
  // param.boardType이 변경될 때마다 호출되는 함수
  () => {
    param.value.pgno = 1;
    getArticleList();
  }
);

onMounted(() => {
  // 초기에 한 번 데이터를 가져오도록 설정
  getArticleList();
});

// 게시물 목록 받아오기
const getArticleList = () => {
  // console.log("서버에서 글목록 얻어오자!!!", param.value);
  // API 호출
  listArticle(
    param.value,
    ({ data }) => {
      // console.log(data);
      articles.value = data.articleList;
      pageNavigation.value = data.pageNavigation;
      totalPageCount.value = data.pageNavigation.totalPageCount;
      // console.log(articles.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

// 페이지 내비게이션 페이지 이동
const onPageChange = (val) => {
  // console.log(val + "빈 페이지로 이동 준비 끝!!!");
  param.value.pgno = val;
  getArticleList();
};

// 정렬 조건 셀렉터 생성값
const selectOption = ref([
  { text: "정렬 조건", value: "" },
  { text: "글 번호", value: "articleNo" },
  { text: "조회수 높은 순", value: "hitDesc" },
  { text: "조회수 낮은 순", value: "hitAsc" },
  { text: "제목 순", value: "subjectAsc" },
  { text: "작성자 순", value: "userIdAsc" },
]);

// 정렬조건 선택시 호출
const changeKey = (val) => {
  // console.log("BoarList에서 선택한 조건 : " + val);
  param.value.sortCondition = val;
  getArticleList();
};

// 글쓰기 이동
function boardWrite() {
  let boardType = param.value.boardType;
  router.push({ name: "board-write", params: { boardType } });
}
</script>

<template>
  <div class="row justify-content-center" id="container">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark v-if="param.boardType == 'announcement'" class="sky">공지사항</mark>
        <mark v-else-if="param.boardType == 'information'" class="sky">정보 게시판</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <div class="row align-self-center mb-2">
        <div v-if="userInfo != null" class="col-md-2 text-start">
          <!-- 공지사항인 경우는 admin만 작성 가능 -->
          <template v-if="param.boardType == 'announcement'">
            <button
              v-if="userInfo.grade == 'admin'"
              type="button"
              id="btn-board-write"
              class="btn btn-outline-primary btn-sm"
              @click="boardWrite"
            >
              글쓰기
            </button>
          </template>
          <!-- 정보게시판인 경우는 로그인만 하면 작성 가능 -->
          <template v-else-if="param.boardType == 'information'">
            <button
              type="button"
              id="btn-board-write"
              class="btn btn-outline-primary btn-sm"
              @click="boardWrite"
            >
              글쓰기
            </button>
          </template>
        </div>
      </div>
      <table class="table table-hover">
        <thead>
          <tr class="text-center">
            <th scope="col">글번호</th>
            <th scope="col">제목</th>
            <th scope="col">작성자</th>
            <th scope="col">조회수</th>
            <th scope="col">작성일</th>
          </tr>
        </thead>
        <tbody>
          <BoardListItem
            v-for="article in articles"
            :key="article.articleNo"
            :article="article"
          ></BoardListItem>
        </tbody>
      </table>
    </div>
    <!-- 페이지 내비게이션 -->
    <div class="row">
      <PageNavigation
        :current-page="param.pgno"
        :total-page="totalPageCount"
        @pageChange="onPageChange"
      >
      </PageNavigation>
    </div>
    <!-- 정렬조건 -->
    <div>
      <VSelect
        :selectOption="selectOption"
        v-model="param.sortCondition"
        @onKeySelect="changeKey"
      />
    </div>
  </div>
</template>

<style scoped>
#container {
  min-height: 730px;
  overflow-x: hidden;
}
</style>
