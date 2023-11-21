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

// 아파트 검색시 파라미터로 넘길 반응형 데이터
const dongName = ref("");
const apartmentName = ref("");

// 아파트 검색 결과 담을 반응형 변수
const searchResult = ref([]);
const avgDealAmounMsg = ref("아파트를 선택하면 최근 거래가가 표시돼요!");

onMounted(() => {
  if (userInfo.value != null) {
    forsaleDto.value.userId = userInfo.value.userId;
  }
});

// 등록버튼 입력값 체크

// 매물 등록 버튼
function registForsaleBtnClicked() {
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
            />
            <p v-text="avgDealAmounMsg"></p>
            <p v-show="forsaleDto.price != 0">zz</p>
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
          <!-- <div> -->
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
          <!-- </div> -->
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
          class="result-item"
          @click="selectApt(item.aptCode)"
        >
          <ul>
            <li>아파트명: {{ item.apartmentName }}</li>
            <li>
              지번주소: {{ item.sidoName }} {{ item.gugunName }} {{ item.dong }} {{ item.jibun }}
            </li>
            <li>도로명주소: {{ item.roadName }} {{ item.roadNameBonBun }}</li>
            <li>준공년도: {{ item.buildYear }}</li>
          </ul>
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
