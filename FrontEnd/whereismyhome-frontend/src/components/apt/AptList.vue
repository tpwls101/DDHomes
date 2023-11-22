<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { useAptStore } from "../../stores/apt";
import { getForsaleList } from "@/api/forsale";

const aptStore = useAptStore();

const { dongCode } = storeToRefs(aptStore);

const forsaleList = ref({}); // 등록된 매물 리스트

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
  <div>
    <h4>매물 목록</h4>
    <div class="forsale-item" v-for="list in forsaleList" :key="list.apartmentName">
      <ul>
        <li>아파트명 : {{ list.apartmentName }}</li>
        <li>매매가 : {{ list.price }}</li>
      </ul>
    </div>
  </div>
</template>

<style scoped>
div {
  background-color: lightpink;
  width: 20%;
  height: 100%;
  border-right: 1px solid;
  margin-left: 0 0;
}

h4 {
  margin: 5px 10px;
}

.forsale-item {
  border: 1px solid;
  width: 95%;
  height: 15%;
  margin: 5px auto;
}
</style>
