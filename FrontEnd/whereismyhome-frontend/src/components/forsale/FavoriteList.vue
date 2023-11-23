<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { getForsaleList } from "@/api/forsale";
import { useMemberStore } from "@/stores/member";
import { useAptStore } from "@/stores/apt";
import { storeToRefs } from "pinia";
import FosaleListItem from "@/components/forsale/item/FosaleListItem.vue";

const memberStore = useMemberStore();
const aptStore = useAptStore();
const router = useRouter();

const { userInfo } = storeToRefs(memberStore);
const { forsaleList } = storeToRefs(aptStore);

// 매물 리스트 구할 때 필요한 파라미터
const params = ref({});

// 등록된 매물 리스트
const favForsaleList = ref([]);

// 리스트 아이템 호출할 때 필요한 타입
const type = ref("favorite");

onMounted(() => {
  // 리스트 불러올 때 파라미터 설정하기
  params.value = {
    condition: "favorite",
    value: userInfo.value.userId,
    userId: userInfo.value.userId,
  };

  loadForsaleList();
});

// 리스트 불러오기
function loadForsaleList() {
  getForsaleList(
    params.value,
    ({ data }) => {
      favForsaleList.value = data;

      for (let i = 0; i < favForsaleList.value.length; i++) {
        // 도로명주소 앞에 0 빼기
        favForsaleList.value[i].roadNameBonBun = Number(favForsaleList.value[i].roadNameBonBun);
        // 가격 콤마, 원 붙이기
        let temp = favForsaleList.value[i].price;
        temp = temp.replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
        favForsaleList.value[i].price = temp + " 원";
      }
    },
    (error) => {
      console.log(error);
    }
  );
}

// 찜목록에서 항목 클릭 시
function favoriteForsaleClicked() {
  // 가격에 원 붙어있는거 되돌리기
  for (let i = 0; i < favForsaleList.value.length; i++) {
    let temp = favForsaleList.value[i].price;
    temp = temp.replace(/\,/g, ""); // 콤마 제거
    temp = temp.slice(0, -1); // 원 제거
    favForsaleList.value[i].price = temp;
  }

  // pinia에 찜목록 리스트 올리기
  forsaleList.value = favForsaleList.value;

  router.push({ name: "apt-bundle" });
}
</script>

<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">찜한 매물</mark>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <table class="table table-hover">
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
            <FosaleListItem
              v-for="forsale in favForsaleList"
              :key="forsale.forsaleNo"
              :forsale="forsale"
              :type="type"
              @reloadForsaleList="loadForsaleList"
              @favoriteForsaleClicked="favoriteForsaleClicked()"
            ></FosaleListItem>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
