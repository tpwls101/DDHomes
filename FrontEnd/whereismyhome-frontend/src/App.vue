<script setup>
import TheHeader from "@/components/layout/TheHeader.vue";
import TheFooter from "@/components/layout/TheFooter.vue";
</script>

<template>
  <!-- 헤더 -->
  <TheHeader></TheHeader>
  <!-- 라우터 뷰 -->
  <router-view class="router-view-class"></router-view>
  <!-- 푸터 -->
  <TheFooter class="footer-class"></TheFooter>
</template>

<style scoped>
/* .router-view-class {
  height: auto;
  min-height: 100%;
  padding-bottom: 100px;
} */

/* .footer-class {
  height: 100px;
  position: relative;
  transform: translateY(-100%);
} */
</style>
