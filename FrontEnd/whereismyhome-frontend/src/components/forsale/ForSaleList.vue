<script setup>
import { ref, onMounted } from "vue";
import { getForsaleList } from "@/api/forsale";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import FosaleListItem from "@/components/forsale/item/FosaleListItem.vue";

const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

// 매물 리스트 구할 때 필요한 파라미터
const params = ref({});

// 등록된 매물 리스트
const forsaleList = ref([]);

// 리스트 아이템 호출할 때 필요한 타입
const type = ref("forsale");

onMounted(() => {
  // 리스트 불러올 때 파라미터 설정하기
  if (userInfo.value.grade == "enter") {
    params.value = {
      condition: "enter",
      value: userInfo.value.userId,
      userId: userInfo.value.userId,
    };
  } else {
    params.value = {
      condition: "",
      value: "",
    };
  }

  loadForsaleList();
});

// 리스트 불러오기
function loadForsaleList() {
  getForsaleList(
    params.value,
    ({ data }) => {
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
  );
}
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
          <FosaleListItem v-for="forsale in forsaleList" :key="forsale.forsaleNo" :forsale="forsale" :type="type"
            @reloadForsaleList="loadForsaleList"></FosaleListItem>
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped></style>
