<script setup>
import { ref, onMounted } from "vue";
import { useAptStore } from "@/stores/apt";
import { storeToRefs } from "pinia";
import SelectArea from '@/components/common/SelectArea.vue';

const aptStore = useAptStore();

const { dongCode, forsaleList } = storeToRefs(aptStore);

onMounted(() => {
  dongCode.value = "";
  forsaleList.value = [];
})

</script>

<template>
  <!-- main menu start -->
  <main>
    <!-- <div class="main-box container-fluid card" style="
      background: url(@/assets/apt3.jpg);
      background-size: cover;
      background-position: 50% 20%;
      width: 100%;
      height: 900px;
    "> -->
    <div class="main-box container-fluid card">
      <div class="card-img-overlay text-light md-5"
        style="background-color: rgba(0, 0, 0, 0.6); height: auto; margin-top: auto">
        <div class="card-img-overlay text-light" style="height: 350px; margin-top: 100px">
          <!-- main search start -->
          <div class="container mt-5">

            <!-- filter start -->
            <SelectArea></SelectArea>
            <!-- filter end -->

            <!-- search start -->
            <div class="row mt-5 mb-5">
              <form action="" class="text-center">
                <input type="text" class="form-control form-control-lg mx-auto" placeholder="검색어를 입력하세요!(예. 수완동)"
                  style="max-width: 400px; display: inline" />
                <input type="submit" class="btn btn-dark btn ms-2 mb-2" style=" outline: 1px solid white;" value="검색" />
              </form>
            </div>
            <!-- search end -->

          </div>
          <!-- main search end -->
          <div class="my-5"><br /><br /><br /></div>
          <!-- introduce start -->
          <div class="container text-center introduce-box">
            <p style="font-size: 60px">구해줘 HOMEs!!</p>
            <p style="font-size: 20px">간편하게 지역 필터 혹은 검색어로 원하는 매물을 찾아보세요!</p>
          </div>
          <!-- introduce end -->
        </div>
      </div>
    </div>
  </main>
  <!-- main menu end -->
</template>

<style scoped>
.main-box {
  background: url(@/assets/apt3.jpg);
  background-size: cover;
  background-position: 50% 20%;
  width: 100%;
  height: 900px;
}
</style>