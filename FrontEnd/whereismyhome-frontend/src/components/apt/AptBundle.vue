<script setup>
import { onMounted } from "vue";
import { useAptStore } from "../../stores/apt";
import { storeToRefs } from "pinia";
import { getForsaleList } from "@/api/forsale";
import AptList from "@/components/apt/AptList.vue";
import AptMap from "@/components/apt/AptMap.vue";
import AptDetail from "@/components/apt/AptDetail.vue";

const aptStore = useAptStore();

// const { dongCode } = storeToRefs(aptStore);
// const { forsaleList } = storeToRefs(aptStore);

// console.log("AptBundle의 dongCode : " + dongCode.value);
// console.log(forsaleList);

onMounted(() => {
  // 리스트 호출시 필요한 파라미터 만들기
  // let params = {
  //   condition: "dongCode",
  //   value: dongCode.value,
  // };
  // // 리스트 받아오기
  // getForsaleList(
  //   params,
  //   ({ data }) => {
  //     console.log("success");
  //     console.log("data : ");
  //     console.log(data);
  //     forsaleList.value = data;
  //     console.log("AptBundle의 forsaleList");
  //     console.log(forsaleList.value);
  //   },
  //   (error) => {
  //     console.log("fail");
  //   }
  // );
  // getForsaleList(
  //   dongCode.value,
  //   ({ data }) => {
  //     console.log("success");
  //     console.log(data);
  //     forsaleList.value = data;
  //     console.log(forsaleList.value);
  //   },
  //   (error) => {
  //     console.log("fail");
  //   }
  // );
  // console.log("AptBundle의 forsaleList");
  // console.log(forsaleList.value);
});
</script>

<template>
  <div class="in-a-row">
    <AptList />
    <AptMap />
    <AptDetail />
  </div>
</template>

<style scoped>
.in-a-row {
  display: flex;
  flex-direction: row;
  height: 800px;
}
</style>
