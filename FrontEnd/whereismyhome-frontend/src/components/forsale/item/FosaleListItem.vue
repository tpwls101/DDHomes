<script setup>
import { ref } from "vue";
import { modifyPrice, deleteForsale } from "@/api/forsale";

// 수정 버튼 토글
const isModifyState = ref(false);
// 수정 매매가
const modifiedPrice = ref();

const props = defineProps({ forsale: Object });

const emit = defineEmits(["reloadForsaleList"]);

// 수정 버튼 클릭 시
function modifyPriceBtnClicked(forsaleNo) {
  isModifyState.value = !isModifyState.value;

  // 수정 완료시에만 아래 실행
  if (!isModifyState.value) {
    if (confirm("매매가를 수정하시겠습니까?")) {
      let params = {
        forsaleNo: forsaleNo,
        price: modifiedPrice.value,
      };

      modifyPrice(
        params,
        ({ data }) => {
          // console.log(data);
          emit("reloadForsaleList");
        },
        (error) => {
          console.log(error);
        }
      );
    }
  }
}

// 취소 버튼 클릭 시
function cancelModifyBtnClicked() {
  modifiedPrice.value = "";
  isModifyState.value = !isModifyState.value;
}

// 삭제 버튼 클릭 시
function deleteBtnClicked(forsaleNo) {
  if (confirm("해당 매물을 삭제하시겠습니까?")) {
    deleteForsale(
      forsaleNo,
      ({ data }) => {
        console.log(data);
        emit("reloadForsaleList");
      },
      (error) => {
        console.log(error);
      }
    );
  }
}
</script>

<template>
  <tr>
    <td scope="col">{{ forsale.forsaleNo }}</td>
    <td scope="col">{{ forsale.apartmentName }}</td>
    <td scope="col">
      {{ forsale.sidoName }} {{ forsale.gugunName }} {{ forsale.dong }} {{ forsale.jibun }}<br />
      {{ forsale.roadName }} {{ forsale.roadNameBonBun }}
    </td>
    <td v-if="isModifyState" scope="col">
      <input type="number" class="input-price" v-model="modifiedPrice" />
    </td>
    <td v-else scope="col">{{ forsale.price }}</td>
    <td scope="col">{{ forsale.userId }}</td>
    <td scope="col">
      <button
        type="button"
        class="btn btn-sm btn-outline-success"
        @click="modifyPriceBtnClicked(forsale.forsaleNo)"
      >
        수정
      </button>
      <button
        v-if="isModifyState"
        type="button"
        class="btn btn-sm btn-outline-secondary"
        @click="cancelModifyBtnClicked()"
      >
        취소
      </button>
      <button
        v-else
        type="button"
        class="btn btn-sm btn-outline-danger"
        @click="deleteBtnClicked(forsale.forsaleNo)"
      >
        삭제
      </button>
    </td>
  </tr>
</template>

<style scoped>
/* input type number 버튼 없애기 */
input[type="number"]::-webkit-outer-spin-button,
input[type="number"]::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

.input-price {
  width: 140px;
}
</style>
