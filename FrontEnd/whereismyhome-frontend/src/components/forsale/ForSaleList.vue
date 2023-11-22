<script setup>
import { ref, onMounted } from "vue";
import { getForsaleList } from "@/api/forsale";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

const forsaleList = ref([]); // 등록된 매물 리스트

// 수정 버튼 토글
const isModifyState = ref(false);
// 수정 매매가
const modifiedPrice = ref();

onMounted(() => {
  let params = {};

  // 리스트 불러올 때 파라미터 설정하기
  if (userInfo.value.grade == "enter") {
    params = {
      condition: "enter",
      value: userInfo.value.userId,
    }
  }
  else {
    params = {
      condition: "",
      value: "",
    }
  }

  // 리스트 불러오기
  getForsaleList(
    params,
    ({ data }) => {
      // console.log(data);
      forsaleList.value = data;

      for (let i = 0; i < forsaleList.value.length; i++) {
        // 도로명주소 앞에 0 빼기
        forsaleList.value[i].roadNameBonBun = Number(forsaleList.value[i].roadNameBonBun);
        // 가격 콤마, 원 붙이기
        let temp = forsaleList.value[i].price;
        temp = temp.replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
        forsaleList.value[i].price = temp + " 원";
      }

    },
    (error) => {
      console.log(error);
    }
  )
});

// 수정 버튼 클릭 시
function modifyPriceBtnClicked(forsaleNo) {
  isModifyState.value = !isModifyState.value;
  // 수정 완료시에만 아래 실행
  if (!isModifyState.value) {
    alert("매매가를 수정합니까?");
    console.log(forsaleNo);
    console.log(modifiedPrice.value);
  }
}

// 취소 버튼 클릭 시
function cancelModifyBtnClicked() {
  modifiedPrice.value = "";
  isModifyState.value = !isModifyState.value;
}

// 삭제 버튼 클릭 시


</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">매물 관리</mark>
      </h2>
    </div>
    <div class="col-lg-8 col-md-10 col-sm-12">
      <table class="table">
        <thead>
          <tr class="text-center">
            <th scope="col">매물 번호</th>
            <th scope="col">아파트 이름</th>
            <th scope="col">주소</th>
            <th scope="col">매매가</th>
            <th scope="col">등록자</th>
            <th scope="col">비고</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="forsale in forsaleList" :key="forsale.forsaleNo" class="text-center">
            <td scope="col">{{ forsale.forsaleNo }}</td>
            <td scope="col">{{ forsale.apartmentName }}</td>
            <td scope="col">{{ forsale.sidoName }} {{ forsale.gugunName }} {{ forsale.dong }} {{ forsale.jibun }}<br>
              {{ forsale.roadName }} {{ forsale.roadNameBonBun }}</td>
            <td class="td-price-hidden" scope="col">
              <input type="number" class="input-price" v-model="modifiedPrice" />
            </td>
            <td class="" scope="col">{{ forsale.price }}</td>
            <td scope="col">{{ forsale.userId }}</td>
            <td scope="col">
              <button type="button" class="btn btn-sm btn-outline-success"
                @click="modifyPriceBtnClicked(forsale.forsaleNo)">수정</button>
              <button v-if="isModifyState" type="button" class="btn btn-sm btn-outline-secondary"
                @click="cancelModifyBtnClicked">취소</button>
              <button v-else type="button" class="btn btn-sm btn-outline-danger">삭제</button>
            </td>
          </tr>
        </tbody>
      </table>
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

.input-price {
  width: 150px;
}

.td-price-hidden {
  display: none;
}
</style>
