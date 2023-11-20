<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

const aptInfo = () => {
  router.push({ name: "apt" });
};
</script>

<template>
  <div class="row col-md-12 justify-content-center mb-2">
    <div class="form-group col-md-2">
      <select class="form-select bg-secondary text-light" id="sido">
        <option value="" selected="selected">시도선택</option>
      </select>
    </div>
    <div class="form-group col-md-2">
      <select class="form-select bg-secondary text-light" id="gugun">
        <option value="" selected="selected">구군선택</option>
      </select>
    </div>
    <div class="form-group col-md-2">
      <select class="form-select bg-secondary text-light" id="dong">
        <option value="" selected="selected">동선택</option>
      </select>
    </div>
    <!-- <div class="form-group col-md-2">
      <select class="form-select bg-dark text-light" id="year">
        <option value="" selected="selected">매매년도선택</option>
      </select>
    </div>
    <div class="form-group col-md-2">
      <select class="form-select bg-dark text-light" id="month">
        <option value="" selected="selected">매매월선택</option>
      </select>
    </div> -->
    <div class="form-group col-md-2">
      <button type="button" id="list-btn" class="btn btn-outline-primary" @click="aptInfo">
        아파트매매정보
      </button>
    </div>
  </div>
</template>

<style scoped></style>
