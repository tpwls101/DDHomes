<script setup>
import { ref, watch, onMounted } from "vue";
import { useAptStore } from "../../stores/apt";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";
import { getImg, getForsaleImgInfo } from "@/api/file";
import { favorite, deleteFavorite, getAvgPrice } from "@/api/forsale";

const memberStore = useMemberStore();
const aptStore = useAptStore();

const { userInfo } = storeToRefs(memberStore);
const { dongCode } = storeToRefs(aptStore);
const { forsaleList } = storeToRefs(aptStore);
const { forsaleNo } = storeToRefs(aptStore);

const avgPrice = ref(""); // 선택한 매물에 해당하는 동의 최근 2년간 평균 거래 가격

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
  favorite: "",
});

onMounted(() => {
  makeData();
  getPriceAvg();
});

// 최근 2년간 xx동의 평균 거래 가격 구하기
function getPriceAvg() {
  getAvgPrice(
    dongCode.value,
    ({ data }) => {
      console.log("success");
      avgPrice.value = data;
      console.log("최근 2년간 xx동의 평균 거래 가격 : " + avgPrice.value);
    },
    (error) => {
      console.log("fail");
    }
  );
}

// 매물 상세정보 데이터 만들기
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
      forsaleInfo.value.favorite = forsaleList.value[i].favorite;

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

function favoriteRenewal() {
  for (let i = 0; i < forsaleList.value.length; i++) {
    if (forsaleList.value[i].forsaleNo == forsaleNo.value) {
      forsaleList.value[i].favorite = !forsaleList.value[i].favorite;
      forsaleInfo.value.favorite = forsaleList.value[i].favorite;
    }
  }
}

// 찜 버튼 클릭 시
function favoriteBtnClicked(forsaleNo) {
  const params = {
    // userId 수정할것!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    userId: userInfo.value.userId,
    forsaleNo: forsaleNo,
  };

  // 사용자가 찜하지 않은 매물이라면 매물 찜 등록
  if (!forsaleInfo.value.favorite) {
    favorite(
      params,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
  // 사용자가 찜 한 매물이라면 찜 목록에서 제거
  else {
    deleteFavorite(
      params,
      ({ data }) => {
        console.log(data);
      },
      (error) => {
        console.log(error);
      }
    );
  }
  favoriteRenewal();
}

</script>

<template>
  <div class="container">
    <h4>매물번호 {{ forsaleNo }}</h4>
    <div class="forsale-img" v-for="img in imgs" :key="img.imginfoNo">
      <img :src="img" style="width: 100px" />
    </div>
    <hr />
    <div>
      <p class="apt-name apt-name-and-favorite">{{ forsaleInfo.apartmentName }}</p>
      <svg v-if="!forsaleInfo.favorite" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor"
        class="bi bi-suit-heart apt-name-and-favorite" viewBox="0 0 16 16" @click="favoriteBtnClicked(forsaleNo)">
        <path
          d="m8 6.236-.894-1.789c-.222-.443-.607-1.08-1.152-1.595C5.418 2.345 4.776 2 4 2 2.324 2 1 3.326 1 4.92c0 1.211.554 2.066 1.868 3.37.337.334.721.695 1.146 1.093C5.122 10.423 6.5 11.717 8 13.447c1.5-1.73 2.878-3.024 3.986-4.064.425-.398.81-.76 1.146-1.093C14.446 6.986 15 6.131 15 4.92 15 3.326 13.676 2 12 2c-.777 0-1.418.345-1.954.852-.545.515-.93 1.152-1.152 1.595L8 6.236zm.392 8.292a.513.513 0 0 1-.784 0c-1.601-1.902-3.05-3.262-4.243-4.381C1.3 8.208 0 6.989 0 4.92 0 2.755 1.79 1 4 1c1.6 0 2.719 1.05 3.404 2.008.26.365.458.716.596.992a7.55 7.55 0 0 1 .596-.992C9.281 2.049 10.4 1 12 1c2.21 0 4 1.755 4 3.92 0 2.069-1.3 3.288-3.365 5.227-1.193 1.12-2.642 2.48-4.243 4.38z" />
      </svg>
      <svg v-else xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="rgb(255, 0, 0)"
        class="bi bi-suit-heart-fill" viewBox="0 0 16 16" @click="favoriteBtnClicked(forsaleNo)">
        <path
          d="M4 1c2.21 0 4 1.755 4 3.92C8 2.755 9.79 1 12 1s4 1.755 4 3.92c0 3.263-3.234 4.414-7.608 9.608a.513.513 0 0 1-.784 0C3.234 9.334 0 8.183 0 4.92 0 2.755 1.79 1 4 1z" />
      </svg>
      <!-- <img scr="@/assets/heart.svg" id="heart" /> -->
    </div>
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
    <p>최근 2년간 {{ forsaleInfo.dong }}의 평균 거래 가격은 {{ avgPrice / 10000 }}억원 입니다.</p>
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
  padding: 5px 0px 5px 0px;
  /* 상우하좌*/
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

.apt-name-and-favorite {
  display: inline-block;
}

.apt-name {
  font-size: large;
  font-weight: bold;
}

.bi-suit-heart {
  margin: 0px 0px 2px 7px;
  /* padding: 1px 1px; */
  width: 18px;
  height: 18px;
}

.bi-suit-heart-fill {
  margin: 0px 0px 2px 7px;
  /* padding: 1px 1px; */
  width: 18px;
  height: 18px;
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
