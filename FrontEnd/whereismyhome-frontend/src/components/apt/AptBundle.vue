<script setup>
import { onMounted } from "vue";
import { useAptStore } from "../../stores/apt";
import { storeToRefs } from "pinia";
import { getForsaleList } from "@/api/forsale";
import AptList from "@/components/apt/AptList.vue";
import AptMap from "@/components/apt/AptMap.vue";
import AptDetail from "@/components/apt/AptDetail.vue";

const aptStore = useAptStore();

const { dongCode } = storeToRefs(aptStore);
const { forsaleList } = storeToRefs(aptStore);

console.log(dongCode);
console.log(forsaleList);

onMounted(() => {
  getForsaleList(
    dongCode.value,
    ({ data }) => {
      console.log("success");
      console.log(data);
      forsaleList.value = data;
      console.log(forsaleList.value);
    },
    (error) => {
      console.log("fail");
    }
  );
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
