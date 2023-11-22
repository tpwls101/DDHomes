<script setup>
import { ref, watch } from "vue";
import { useAptStore } from "../../stores/apt";
import { storeToRefs } from "pinia";

const aptStore = useAptStore();

const { forsaleList } = storeToRefs(aptStore);
const { forsaleNo } = storeToRefs(aptStore);

const forsaleInfo = ref({
  apartmentName: "",
  sidoName: "",
  gugunName: "",
  dong: "",
  jibun: "",
  roadName: "",
  roadNameBonBun: "",
  // userId: "",
  price: "",
  buildYear: "",
  imgInfos: "",
});

watch(
  () => forsaleNo.value,
  () => {
    for (let i = 0; i < forsaleList.value.length; i++) {
      if (forsaleList.value[i].forsaleNo == forsaleNo.value) {
        console.log(forsaleList.value[i]);
        forsaleInfo.value.apartmentName = forsaleList.value[i].apartmentName;
        forsaleInfo.value.sidoName = forsaleList.value[i].sidoName;
        forsaleInfo.value.gugunName = forsaleList.value[i].gugunName;
        forsaleInfo.value.dong = forsaleList.value[i].dong;
        forsaleInfo.value.jibun = forsaleList.value[i].jibun;
        forsaleInfo.value.roadName = forsaleList.value[i].roadName;
        forsaleInfo.value.roadNameBonBun = forsaleList.value[i].roadNameBonBun;
        // 도로명주소 가공
        forsaleInfo.value.roadNameBonBun = forsaleInfo.value.roadNameBonBun.replaceAll("0", "");
        forsaleInfo.value.price = forsaleList.value[i].price;
        forsaleInfo.value.buildYear = forsaleList.value[i].buildYear;
        forsaleInfo.value.imgInfos = forsaleList.value[i].imgInfos;
      }
    }
  }
);
</script>

<template>
  <div>
    <h4>매물번호 {{ forsaleNo }}</h4>
    <!-- <div><img src=""></div> -->
    <p>아파트명 : {{ forsaleInfo.apartmentName }}</p>
    <p>매매가 : {{ forsaleInfo.price }}</p>
    <p>
      도로명주소 : {{ forsaleInfo.sidoName }} {{ forsaleInfo.gugunName }}
      {{ forsaleInfo.roadName }} {{ forsaleInfo.roadNameBonBun }}
    </p>
    <p>
      지번주소 : {{ forsaleInfo.sidoName }} {{ forsaleInfo.gugunName }} {{ forsaleInfo.dong }}
      {{ forsaleInfo.jibun }}
    </p>
    <p>준공년도 : {{ forsaleInfo.buildYear }}</p>
  </div>
</template>

<style scoped>
div {
  background-color: lightblue;
  width: 25%;
  height: 100%;
  /* border-right: 1px solid; */
}

h4 {
  margin: 5px 10px;
}
</style>
