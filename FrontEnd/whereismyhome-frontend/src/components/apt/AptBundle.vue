<script setup>
import { ref } from "vue";
import { useRoute } from "vue-router";
import { useAptStore } from "../../stores/apt";
import { storeToRefs } from "pinia";
import AptList from "@/components/apt/AptList.vue";
import AptMap from "@/components/apt/AptMap.vue";
import AptDetail from "@/components/apt/AptDetail.vue";

// const route = useRoute();

// const dongCode = ref(route.params.value);
// console.log("dongCode : " + dongCode.value);

const aptStore = useAptStore();

const { dongCode } = storeToRefs(aptStore);

console.log(dongCode);
</script>

<template>
  <div>
    <h1>AptBundle</h1>
    <div></div>
    <div>
      <AptList />
    </div>
    <div>
      <AptMap />
    </div>
    <div>
      <AptDetail />
    </div>
  </div>
</template>

<style scoped></style>
