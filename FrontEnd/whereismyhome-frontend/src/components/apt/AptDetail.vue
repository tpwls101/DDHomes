<script setup>
import { ref, watch, onMounted } from "vue";
import { useAptStore } from "../../stores/apt";
import { storeToRefs } from "pinia";
import { getImg } from "@/api/file";
import { getForsaleImgInfo } from "@/api/file";

const aptStore = useAptStore();

const { forsaleList } = storeToRefs(aptStore);
const { forsaleNo } = storeToRefs(aptStore);

// 이미지 정보에 대한 파라미터
const param = ref([]);
// 이미지 경로
const imgs = ref([]);

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

onMounted(() => {
  makeData();
});

function makeData() {
  for (let i = 0; i < forsaleList.value.length; i++) {
    if (forsaleList.value[i].forsaleNo == forsaleNo.value) {
      console.log(forsaleList.value[i]);

      deleteImage();

      forsaleInfo.value.apartmentName = forsaleList.value[i].apartmentName;
      forsaleInfo.value.sidoName = forsaleList.value[i].sidoName;
      forsaleInfo.value.gugunName = forsaleList.value[i].gugunName;
      forsaleInfo.value.dong = forsaleList.value[i].dong;
      forsaleInfo.value.jibun = forsaleList.value[i].jibun;
      forsaleInfo.value.roadName = forsaleList.value[i].roadName;
      forsaleInfo.value.roadNameBonBun = forsaleList.value[i].roadNameBonBun;
      // 도로명주소 가공
      forsaleInfo.value.roadNameBonBun = Number(forsaleInfo.value.roadNameBonBun);
      forsaleInfo.value.price = forsaleList.value[i].price;
      forsaleInfo.value.buildYear = forsaleList.value[i].buildYear;
      forsaleInfo.value.imgInfos = forsaleList.value[i].imgInfos;

      getImage();
    }
  }
}

watch(
  () => forsaleNo.value,
  () => {
    // for (let i = 0; i < forsaleList.value.length; i++) {
    //   if (forsaleList.value[i].forsaleNo == forsaleNo.value) {
    //     console.log(forsaleList.value[i]);

    //     deleteImage();

    //     forsaleInfo.value.apartmentName = forsaleList.value[i].apartmentName;
    //     forsaleInfo.value.sidoName = forsaleList.value[i].sidoName;
    //     forsaleInfo.value.gugunName = forsaleList.value[i].gugunName;
    //     forsaleInfo.value.dong = forsaleList.value[i].dong;
    //     forsaleInfo.value.jibun = forsaleList.value[i].jibun;
    //     forsaleInfo.value.roadName = forsaleList.value[i].roadName;
    //     forsaleInfo.value.roadNameBonBun = forsaleList.value[i].roadNameBonBun;
    //     // 도로명주소 가공
    //     forsaleInfo.value.roadNameBonBun = Number(forsaleInfo.value.roadNameBonBun);
    //     forsaleInfo.value.price = forsaleList.value[i].price;
    //     forsaleInfo.value.buildYear = forsaleList.value[i].buildYear;
    //     forsaleInfo.value.imgInfos = forsaleList.value[i].imgInfos;

    //     getImage();
    //   }
    // }
    makeData();
  }
);

// 이미지 불러오기
const getImage = () => {
  getForsaleImgInfo(
    forsaleNo.value,
    ({ data }) => {
      param.value = data;
      // 이미지 불러오며 경로 설정
      for (let i = 0; i < param.value.length; i++) {
        getImg(
          param.value[i],
          ({ data }) => {
            imgs.value.push(
              "http://localhost/file/getImg/" +
                param.value[i].saveFolder +
                "/" +
                param.value[i].originalName +
                "/" +
                param.value[i].saveName
            );
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    (error) => {
      console.log(error);
    }
  );
};

const deleteImage = () => {
  imgs.value = [];
};
</script>

<template>
  <div class="container">
    <h4>매물번호 {{ forsaleNo }}</h4>
    <div class="forsale-img" v-for="img in imgs" :key="img.imginfoNo">
      <img :src="img" style="width: 100px" />
    </div>
    <hr />
    <!-- <p>아파트명 : {{ forsaleInfo.apartmentName }}</p> -->
    <p class="apt-name">{{ forsaleInfo.apartmentName }}</p>
    <p class="price">매매가 {{ forsaleInfo.price }}원</p>
    <p class="apt-info">
      ◈ 도로명주소 : {{ forsaleInfo.sidoName }} {{ forsaleInfo.gugunName }}
      {{ forsaleInfo.roadName }} {{ forsaleInfo.roadNameBonBun }}
    </p>
    <p class="apt-info">
      ◈ 지번주소 : {{ forsaleInfo.sidoName }} {{ forsaleInfo.gugunName }} {{ forsaleInfo.dong }}
      {{ forsaleInfo.jibun }}
    </p>
    <p class="apt-info">◈ 준공년도 : {{ forsaleInfo.buildYear }}</p>
    <hr />
  </div>
</template>

<style scoped>
.container {
  /* background-color: lightblue; */
  width: 25%;
  height: 100%;
  /* border-right: 1px solid; */
}
h4 {
  margin: 3px 0px;
  padding: 5px 0px 5px 0px; /* 상우하좌*/
  font-weight: bold;
}

.forsale-img {
  /* width: 100%; */
  /* height: 13%; */
  width: 250px;
  height: 100px;
  overflow: hidden;
  margin: 0 auto;
  /* margin: 2px auto; */
  /* background-color: lightpink; */
}

img {
  /* width: 100%; */
  /* height: 100%; */
  width: 300px;
  height: 100px;
  object-fit: cover;
}

/* p {
  margin: 1px 7px;
} */

.apt-name {
  font-size: large;
  font-weight: bold;
}

/* .price {
  border: 1px solid lightgrey;
  border-radius: 5px;
  padding: 5px 5px;
} */

.apt-info {
  margin: 2px 0px;
}
</style>
