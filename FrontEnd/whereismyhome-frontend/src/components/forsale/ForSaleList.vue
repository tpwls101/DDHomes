<script setup>
import { ref, onMounted } from "vue";
import { getForsaleList } from "@/api/forsale";

const forsaleList = ref([]); // 등록된 매물 리스트

onMounted(() => {

});
</script>

<template>
  <div class="row justify-content-center">
    <div class="col-lg-8 col-md-10 col-sm-12">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">매물 관리</mark>
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
          </tr>
        </thead>
        <tbody>
          <!-- 아이템 -->
        </tbody>
      </table>
    </div>
  </div>
</template>

<style scoped></style>
