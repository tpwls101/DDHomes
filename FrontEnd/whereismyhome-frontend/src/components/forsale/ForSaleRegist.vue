<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "../../stores/member";
import { storeToRefs } from "pinia";
import { searchAptName, registForSale } from "@/api/forsale";
import FileUpload from "@/components/common/FileUpload.vue";

const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

// 매물 등록시 파라미터로 넘길 반응형 데이터
const forsaleDto = ref({
  aptCode: "",
  userId: "",
  price: "",
});

// 아파트 검색시 파라미터로 넘길 반응형 데이터
const apartmentName = ref("");

onMounted(() => {
  forsaleDto.value.userId = userInfo.value.userId;
})

// 매물 등록 버튼
function registForSaleBtnClicked() {
  // 파일 확인
  const imgInfos = document.querySelector("#upload-img").files;

  // 게시판 등록 위한 boardDto와 파일인 imgInfos를 하나의 파라미터로 등록
  const params = {
    forsaleDto: forsaleDto.value,
    imgInfos: imgInfos,
  };
  // console.log(params);

  registForSale(
    params,
    ({ data }) => {
    },
    (error) => {
      alert("[오류] 매물 등록에 문제가 발생했습니다.");
      console.log(error);
    }
  );
}

// 아파트 검색 버튼
function searchAptNameBtnClicked() {
  console.log(apartmentName.value);

  // searchAptName();
}

</script>

<template>
  <div class="col-10 regist-title">
    <h2 class="my-3 py-3 shadow-sm bg-light text-center">
      <mark class="sky">매물등록</mark>
    </h2>
  </div>

  <div style="text-align: center; margin: 30px 0">
    아파트 검색으로 간단하게 매물을 등록해 보세요.
  </div>
  <div class="box-wrap col-10">

    <div class="row regist-input">
      <div class="col-12">
        <form id="form-register" method="POST" action="">

          <div class="mb-3">
            <p>작성자 : {{ forsaleDto.userId }}</p>
          </div>

          <div class="mb-3">
            <label for="apt-code" class="form-label">아파트 코드 : </label>
            <input type="text" class="form-control" id="apt-code" name="apt-code" placeholder="아파트 코드..."
              v-model="forsaleDto.aptCode" />
          </div>
          <div class="mb-3">
            <label for="price" class="form-label">가격 : </label>
            <input type="number" class="form-control" id="price" name="price" v-model="forsaleDto.price" />
          </div>
          <div>
            <p>이미지 :</p>
            <FileUpload></FileUpload>
          </div>
          <div class="col-auto text-center">
            <button type="button" id="btn-register" class="btn btn-outline-primary mb-3" @click="registForSaleBtnClicked">
              매물 등록 요청하기
            </button>
          </div>
        </form>
      </div>
    </div>

    <div class="col-12 regist-search">
      <h6>아파트 검색하기</h6>
      <hr />
      <form id="form-search" method="POST" action="">
        <label for="apartmentName" class="form-label">아파트 이름 : </label>
        <div class="search-box">
          <input type="text" class="form-control" id="apartmentName" name="apartmentName" v-model="apartmentName">
          <button type="button" class="regist-search-btn btn btn-outline-success"
            @click="searchAptNameBtnClicked">검색</button>
        </div>
      </form>
      <hr />
    </div>

  </div>
</template>

<style scoped>
/* input type number 버튼 없애기 */
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.regist-title {
  margin: 10px auto;
}

.regist-input {
  width: 50%;
  margin-left: 20px;
  margin-right: 20px;
  /* border: 1px solid black; */
}

.regist-search {
  width: 50%;
  height: 600px;
  margin-left: 20px;
  margin-right: 20px;
  padding: 10px;
  border: 1px solid lightgray;
}

.search-box {
  display: inline-flex;
  width: 100%;
  height: 40px;
}

.regist-search-btn {
  min-width: 70px;
}

.box-wrap {
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 0 auto;
}
</style>