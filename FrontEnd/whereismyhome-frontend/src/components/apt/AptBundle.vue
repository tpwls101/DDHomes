<script setup>
import { useAptStore } from "../../stores/apt";
import { storeToRefs } from "pinia";
import AptList from "@/components/apt/AptList.vue";
import AptMap from "@/components/apt/AptMap.vue";
import AptDetail from "@/components/apt/AptDetail.vue";

const aptStore = useAptStore();

const { dongCode } = storeToRefs(aptStore);

console.log(dongCode);
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
  height: 600px;
}
</style>
