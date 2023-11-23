<script setup>
import { ref } from "vue";
import { modifyPrice, deleteForsale, favorite, deleteFavorite } from "@/api/forsale";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

// 수정 버튼 토글
const isModifyState = ref(false);
// 수정 매매가
const modifiedPrice = ref("");

const props = defineProps({ forsale: Object, type: String });

const emit = defineEmits(["reloadForsaleList, favoriteForsaleClicked"]);

// 리스트 클릭 시
function favoriteForsaleClicked() {
  emit("favoriteForsaleClicked");
}

// 수정 버튼 클릭 시
function modifyPriceBtnClicked(forsaleNo) {
  isModifyState.value = !isModifyState.value;

  // 수정 완료시에만 아래 실행
  if (!isModifyState.value) {
    // 입력값 확인
    if (modifiedPrice.value === "") {
      alert("[오류] 매매가를 입력해 주세요.");
      isModifyState.value = !isModifyState.value;
      return;
    }

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
    } else {
      isModifyState.value = !isModifyState.value;
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

// 찜 버튼 클릭 시
function favoriteBtnClicked(forsaleNo) {
  const params = {
    // userId 수정할것!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    userId: userInfo.value.userId,
    forsaleNo: forsaleNo,
  };

  // 사용자가 찜하지 않은 매물이라면 매물 찜 등록
  if (!props.forsale.favorite) {
    favorite(
      params,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
  // 사용자가 찜 한 매물이라면 찜 목록에서 제거
  else {
    deleteFavorite(
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
</script>

<template>
  <tr>
    <td scope="col">{{ forsale.forsaleNo }}</td>
    <td @click="favoriteForsaleClicked" scope="col">{{ forsale.apartmentName }}</td>
    <td @click="favoriteForsaleClicked" scope="col">
      {{ forsale.sidoName }} {{ forsale.gugunName }} {{ forsale.dong }} {{ forsale.jibun }}<br />
      {{ forsale.roadName }} {{ forsale.roadNameBonBun }}
    </td>
    <td v-if="isModifyState" scope="col">
      <input type="number" class="input-price" v-model="modifiedPrice" />
    </td>
    <td v-else scope="col">{{ forsale.price }}</td>
    <td scope="col">{{ forsale.userId }}</td>
    <td scope="col">
      <div v-if="props.type === 'forsale'">
        <button
          type="button"
          class="btn btn-sm btn-outline-success"
          @click="modifyPriceBtnClicked(forsale.forsaleNo)"
        >
          수정
        </button>
        <div>
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
        </div>
      </div>
      <div v-else>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          height="1em"
          viewBox="0 0 448 512"
          @click="favoriteBtnClicked(forsale.forsaleNo)"
        >
          <path
            d="M170.5 51.6L151.5 80h145l-19-28.4c-1.5-2.2-4-3.6-6.7-3.6H177.1c-2.7 0-5.2 1.3-6.7 3.6zm147-26.6L354.2 80H368h48 8c13.3 0 24 10.7 24 24s-10.7 24-24 24h-8V432c0 44.2-35.8 80-80 80H112c-44.2 0-80-35.8-80-80V128H24c-13.3 0-24-10.7-24-24S10.7 80 24 80h8H80 93.8l36.7-55.1C140.9 9.4 158.4 0 177.1 0h93.7c18.7 0 36.2 9.4 46.6 24.9zM80 128V432c0 17.7 14.3 32 32 32H336c17.7 0 32-14.3 32-32V128H80zm80 64V400c0 8.8-7.2 16-16 16s-16-7.2-16-16V192c0-8.8 7.2-16 16-16s16 7.2 16 16zm80 0V400c0 8.8-7.2 16-16 16s-16-7.2-16-16V192c0-8.8 7.2-16 16-16s16 7.2 16 16zm80 0V400c0 8.8-7.2 16-16 16s-16-7.2-16-16V192c0-8.8 7.2-16 16-16s16 7.2 16 16z"
          />
        </svg>
      </div>
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

.btn {
  width: 46px;
  height: 30px;
}
</style>
