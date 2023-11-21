<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "../../stores/member";
import { storeToRefs } from "pinia";
import { searchAptName, registForsale, avgDealAmount } from "@/api/forsale";
import FileUpload from "@/components/common/FileUpload.vue";

const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

// 매물 등록시 파라미터로 넘길 반응형 데이터
const forsaleDto = ref({
  aptCode: "",
  userId: "",
  price: "",
});

// 가격 입력 시 단위 표시해주는 데이터
const unitPrice = ref("");

// 아파트 검색시 파라미터로 넘길 반응형 데이터
const dongName = ref("");
const apartmentName = ref("");

// 아파트 검색 결과 담을 반응형 변수
const searchResult = ref([]);
const avgDealAmounMsg = ref("아파트를 선택하면 최근 거래가가 표시돼요!");

// 아파트 검색 결과 아이템 선택 시 클래스 변경 위한 변수
const prevSelectedItemId = ref("");

onMounted(() => {
  if (userInfo.value != null) {
    forsaleDto.value.userId = userInfo.value.userId;
  }
});

// 가격 입력 시 작동하는 이벤트
function checkUnit() {
  if (forsaleDto.value.price === "") {
    unitPrice.value = "";
  } else {
    // console.log(forsaleDto.value.price.length);
    let temp = forsaleDto.value.price;
    console.log(temp);
    unitPrice.value = forsaleDto.value.price + "원";
  }
}

// 등록버튼 입력값 체크
function isValidInput() {
  if (forsaleDto.value.aptCode === "") {
    alert("[오류] 아파트를 선택해 주세요.");
    return false;
  } else if (forsaleDto.value.price === "") {
    alert("[오류] 가격을 입력해 주세요.");
    return false;
  } else if (document.querySelector("#upload-img").files.length === 0) {
    alert("[오류] 사진은 한 장 이상 등록해 주세요.");
    return false;
  }
  return true;
}

// 매물 등록 버튼
function registForsaleBtnClicked() {
  if (!isValidInput()) {
    return;
  }
  // 파일 확인
  const imgInfos = document.querySelector("#upload-img").files;

  // 게시판 등록 위한 boardDto와 파일인 imgInfos를 하나의 파라미터로 등록
  const params = {
    forsaleDto: forsaleDto.value,
    imgInfos: imgInfos,
  };
  // console.log(params);

  registForsale(
    params,
    ({ data }) => {},
    (error) => {
      alert("[오류] 매물 등록에 문제가 발생했습니다.");
      console.log(error);
    }
  );
}

// 아파트 검색 버튼
function searchAptNameBtnClicked() {
  // console.log(apartmentName.value);

  const params = {
    dongName: dongName.value,
    apartmentName: apartmentName.value,
  };

  searchAptName(
    params,
    ({ data }) => {
      searchResult.value = data;
      // console.log(data);
    },

    (error) => {
      console.log(error);
    }
  );
}

// 아파트 검색결과 선택 시
function selectApt(aptCode) {
  // 아파트 코드로 평균 2년 거래가격 얻어오기
  avgDealAmount(
    aptCode,
    ({ data }) => {
      // console.log(data);
      // 최근 2년 거래내역이 없는 경우
      if (data === 0) {
        avgDealAmounMsg.value = "최근 2년동안 거래 내역이 없어요!";
      }
      // 거래내역 성공적으로 가져온 경우
      else {
        avgDealAmounMsg.value = "최근 2년 거래가는 " + data / 10000 + "억원이예요.";
      }
    },
    (error) => {
      console.log(error);
    }
  );

  forsaleDto.value.aptCode = aptCode;

  // 선택된 아이템 배경색 바꾸기
  for (let i = 0; i < searchResult.value.length; i++) {
    if (searchResult.value[i].aptCode == aptCode) {
      document.getElementById(aptCode).classList.add("selected-item");
    }
  }
  if (prevSelectedItemId.value != "") {
    document.getElementById(prevSelectedItemId.value).classList.remove("selected-item");
  }
  prevSelectedItemId.value = aptCode;
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
        <form id="form-register" method="POST" action="" onsubmit="return false;">
          <div class="mb-3">
            <p>작성자 : {{ forsaleDto.userId }}</p>
          </div>

          <div class="mb-3">
            <label for="apt-code" class="form-label">아파트 코드 : </label>
            <input
              type="text"
              class="form-control"
              id="apt-code"
              name="apt-code"
              placeholder="아파트 코드"
              v-model="forsaleDto.aptCode"
              readonly="readonly"
            />
          </div>
          <div class="mb-3">
            <label for="price" class="form-label">가격 : </label>
            <input
              type="number"
              class="form-control"
              id="price"
              name="price"
              placeholder="가격을 입력하세요!"
              v-model="forsaleDto.price"
              @keyup="checkUnit"
            />
            <p v-text="avgDealAmounMsg"></p>
            <p v-text="unitPrice"></p>
          </div>
          <div>
            <p>이미지 :</p>
            <FileUpload></FileUpload>
          </div>
          <div class="col-auto text-center">
            <button
              type="button"
              id="btn-register"
              class="btn btn-outline-primary mb-3"
              @click="registForsaleBtnClicked"
            >
              매물 등록 요청하기
            </button>
          </div>
        </form>
      </div>
    </div>

    <div class="col-12 regist-search">
      <h6>아파트 검색하기</h6>
      <hr />
      <form id="form-search" method="POST" action="" onsubmit="return false;">
        <div class="search-box">
          <input
            type="text"
            class="form-control"
            id="dongName"
            name="dongName"
            placeholder="동 입력"
            v-model="dongName"
          />
          <input
            type="text"
            class="form-control"
            id="apartmentName"
            name="apartmentName"
            placeholder="아파트명 입력"
            v-model="apartmentName"
          />

          <button
            type="button"
            class="regist-search-btn btn btn-outline-success"
            @click="searchAptNameBtnClicked"
          >
            검색
          </button>
        </div>
      </form>
      <div class="search-result-container">
        <div
          v-for="item in searchResult"
          :key="item.aptCode"
          :id="item.aptCode"
          class="result-item"
          @click="selectApt(item.aptCode)"
        >
          <p>
            <svg xmlns="http://www.w3.org/2000/svg" height="1em" viewBox="0 0 384 512">
              <!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
              <path
                d="M64 48c-8.8 0-16 7.2-16 16V448c0 8.8 7.2 16 16 16h80V400c0-26.5 21.5-48 48-48s48 21.5 48 48v64h80c8.8 0 16-7.2 16-16V64c0-8.8-7.2-16-16-16H64zM0 64C0 28.7 28.7 0 64 0H320c35.3 0 64 28.7 64 64V448c0 35.3-28.7 64-64 64H64c-35.3 0-64-28.7-64-64V64zm88 40c0-8.8 7.2-16 16-16h48c8.8 0 16 7.2 16 16v48c0 8.8-7.2 16-16 16H104c-8.8 0-16-7.2-16-16V104zM232 88h48c8.8 0 16 7.2 16 16v48c0 8.8-7.2 16-16 16H232c-8.8 0-16-7.2-16-16V104c0-8.8 7.2-16 16-16zM88 232c0-8.8 7.2-16 16-16h48c8.8 0 16 7.2 16 16v48c0 8.8-7.2 16-16 16H104c-8.8 0-16-7.2-16-16V232zm144-16h48c8.8 0 16 7.2 16 16v48c0 8.8-7.2 16-16 16H232c-8.8 0-16-7.2-16-16V232c0-8.8 7.2-16 16-16z"
              />
            </svg>
            아파트명: {{ item.apartmentName }}
          </p>
          <p>지번주소: {{ item.sidoName }} {{ item.gugunName }} {{ item.dong }} {{ item.jibun }}</p>
          <p>도로명주소: {{ item.roadName }} {{ item.roadNameBonBun }}</p>
          <p>준공년도: {{ item.buildYear }}</p>
        </div>
      </div>
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

.search-result-container {
  border: 1px solid lightgray;
  margin-top: 10px;
  height: 475px;
  white-space: nowrap;
  overflow: auto;
}

.result-item {
  border: 1px solid lightgray;
  border-radius: 5px;
  margin: 5px;
  padding: 5px;
}

.selected-item {
  background-color: lightgray;
}

.result-item > * {
  margin: 0;
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
