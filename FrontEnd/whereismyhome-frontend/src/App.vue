<script setup>
import TheHeader from "@/components/layout/TheHeader.vue";
import TheFooter from "@/components/layout/TheFooter.vue";
</script>

<template>
  <!-- 헤더 -->
  <TheHeader></TheHeader>
  <!-- 라우터 뷰 -->
  <router-view></router-view>
  <!-- 푸터 -->
  <TheFooter></TheFooter>
</template>

<style scoped></style>
