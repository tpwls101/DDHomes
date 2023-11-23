<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useAptStore } from "@/stores/apt";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { getForsaleList } from "@/api/forsale";
import SelectArea from "@/components/common/SelectArea.vue";

const aptStore = useAptStore();
const memberStore = useMemberStore();
const router = useRouter();

const { isLogin } = storeToRefs(memberStore);

const { dongCode, forsaleList, forsaleNo } = storeToRefs(aptStore);

// 동이름으로 input
const searchKeyword = ref("");

onMounted(() => {
  dongCode.value = "";
  forsaleList.value = [];
  forsaleNo.value = "";
});

// 검색어로 검색 버튼 클릭시
function searchForsaleByDongNameBtnClicked() {
  // 로그인 확인
  if (!isLogin.value) {
    alert("로그인 후 이용 가능한 서비스입니다.");
    return;
  }

  // 입력값 확인
  if (searchKeyword.value == "") {
    alert("검색어를 입력하세요!");
    return;
  }

  // 파라미터 만들어서 해당하는 매물리스트 가져오기
  // 동 이름으로 검색 결과
  let params = {
    condition: "dong",
    value: searchKeyword.value,
  };
  getForsaleList(
    params,
    ({ data }) => {
      // pinia에 검색한 결과 리스트 올리기
      console.log(data);
      forsaleList.value = data;

      router.push({ name: "apt-bundle" });
    },
    (error) => {
      console.log(error);
    }
  );

  // 아파트 이름으로 검색 결과
  // params.condition = "apartmentName";
  // getForsaleList(
  //   params,
  //   ({ data }) => {
  //     // pinia에 검색한 결과 리스트 올리기
  //     console.log(data);
  //     forsaleList.value = data;

  //     router.push({ name: "apt-bundle" });
  //   },
  //   (error) => {
  //     console.log(error);
  //   }
  // )
}
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
      <div
        class="card-img-overlay text-light md-5"
        style="background-color: rgba(0, 0, 0, 0.6); height: auto; margin-top: auto"
      >
        <div class="card-img-overlay text-light" style="height: 350px; margin-top: 100px">
          <!-- main search start -->
          <div class="container mt-5">
            <!-- filter start -->
            <SelectArea></SelectArea>
            <!-- filter end -->

            <!-- search start -->
            <div class="row mt-5 mb-5">
              <form action="" class="text-center">
                <input
                  v-model="searchKeyword"
                  type="text"
                  class="form-control form-control-lg mx-auto"
                  placeholder="검색어를 입력하세요!(예. 수완동)"
                  style="max-width: 400px; display: inline"
                />
                <input
                  @click="searchForsaleByDongNameBtnClicked"
                  type="button"
                  class="btn btn-dark btn ms-2 mb-2"
                  style="outline: 1px solid white"
                  value="검색"
                />
              </form>
            </div>
            <!-- search end -->
          </div>
          <!-- main search end -->
          <div class="my-5"><br /><br /><br /></div>
          <!-- introduce start -->
          <div class="container text-center introduce-box">
            <p style="font-size: 60px">구해줘 HOMEs!!</p>
            <p style="font-size: 20px">
              간편하게 지역 필터 혹은 검색어로 원하는 매물을 찾아보세요!
            </p>
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
