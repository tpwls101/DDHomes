<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAptStore } from "../../stores/apt";
import { storeToRefs } from "pinia";
import { getForsaleList } from "@/api/forsale";
import { useMemberStore } from "@/stores/member";
import AptList from "@/components/apt/AptList.vue";
import AptMap from "@/components/apt/AptMap.vue";
import AptDetail from "@/components/apt/AptDetail.vue";

const aptStore = useAptStore();
const memberStore = useMemberStore();
const router = useRouter();

const { userInfo } = storeToRefs(memberStore);
const { dongCode } = storeToRefs(aptStore);
const { forsaleList } = storeToRefs(aptStore);
// const { forsaleNo } = storeToRefs(aptStore);

// const forsaleNo = "";

const showForsaleDetail = ref(false);

onMounted(() => {
  // 셀렉터에서 값 선택시 동코드로 아파트 리스트 가져오기
  if (forsaleList.value.length === 0) {
    // 리스트 호출시 필요한 파라미터 만들기
    let params = {
      condition: "dongCode",
      value: dongCode.value,
      userId: userInfo.value.userId,
    };
    // 리스트 받아오기
    getForsaleList(
      params,
      ({ data }) => {
        if (data.length === 0) {
          alert("해당 키워드에 해당하는 매물이 존재하지 않습니다. 다른 키워드를 입력해 주세요!");
          router.push({ name: "main" });
        }
        // console.log("success");
        // console.log("data : ");
        // console.log(data);
        forsaleList.value = data;
        // console.log("AptBundle의 forsaleList");
        // console.log(forsaleList.value);
      },
      (error) => {
        // console.log("fail");
      }
    );
  }
});

function ShowForsaleDetail(sfd) {
  showForsaleDetail.value = sfd;
  console.log(showForsaleDetail.value);
}
</script>

<template>
  <div class="in-a-row">
    <AptList @show-forsale-detail="ShowForsaleDetail" />
    <AptMap />
    <AptDetail v-show="showForsaleDetail == true" />
  </div>
</template>

<style scoped>
.in-a-row {
  display: flex;
  flex-direction: row;
  height: 800px;
  /* height: 100%; */
}
</style>
