<script setup>
import { ref, onMounted, watch } from "vue";
import { useAptStore } from "../../stores/apt";
import { storeToRefs } from "pinia";

const aptStore = useAptStore();

var map;
const positions = ref([]);
const markers = ref([]);

const { forsaleList } = storeToRefs(aptStore);

// autoload=false
// 비동기 로딩은 당장 페이지에서 필요 없는 지도 관련 스크립트 전체를 미리 로딩하지 않고 필요한 경우에만 로딩하기 위해 사용
onMounted(() => {
  // const { forsaleList } = storeToRefs(aptStore);
  console.log("AptMap에서 onMounted 중!!! -> forsaleList 확인!");
  console.log(forsaleList.value);
  // forsaleList2.value = forsaleList.value;
  // console.log("forsaleList2");
  // console.log(forsaleList2.value);
  // forsaleList.value = forsaleList.value;

  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
      }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

function checkData() {
  positions.value = [];
  forsaleList.value.forEach((list) => {
    let obj = {};
    obj.latlng = new kakao.maps.LatLng(list.lat, list.lng);
    obj.title = list.apartmentName;

    positions.value.push(obj);
  });

  loadMarkers();
}

// 스크립트가 로드되기 전에 watch가 먼저 실행돼서 kakao 객체를 인식 못하므로 0.5초 늦게 실행시켜줬음
// setTimeout(() => {
// 매물 위치 세팅
watch(
  () => forsaleList.value,
  () => {
    if (window.kakao && window.kakao.maps) {
      checkData();
    }
  },
  { deep: true }
);

// watch(
//   () => props.selectStation.value,
//   () => {
//     // 이동할 위도 경도 위치를 생성합니다
//     var moveLatLon = new kakao.maps.LatLng(props.selectStation.lat, props.selectStation.lng);

//     // 지도 중심을 부드럽게 이동시킵니다
//     // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
//     map.panTo(moveLatLon);
//   },
//   { deep: true }
// );
// }, 1000);

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  checkData();
};

// 시군동 선택 후 지도 페이지로 넘어왔을 때 아파트 매물 목록들 지도에 마커 찍어주기
const loadMarkers = () => {
  console.log("loadMarkers 진입!");

  // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
  // deleteMarkers();

  // 마커 이미지를 생성합니다
  //   const imgSrc = require("@/assets/map/markerStar.png");
  // 마커 이미지의 이미지 크기 입니다
  //   const imgSize = new kakao.maps.Size(24, 35);
  //   const markerImage = new kakao.maps.MarkerImage(imgSrc, imgSize);

  // 마커를 생성합니다
  markers.value = [];
  positions.value.forEach((position) => {
    const marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: position.latlng, // 마커를 표시할 위치
      title: position.title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됨.
      clickable: true, // // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
      // image: markerImage, // 마커의 이미지
    });
    console.log("marker의 position : " + marker.position);
    markers.value.push(marker);
  });
  console.log("markers 확인!");
  console.log(markers.value);

  // 4. 지도를 이동시켜주기
  // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
  // const bounds = positions.value.reduce(
  //   (bounds, position) => bounds.extend(position.latlng),
  //   new kakao.maps.LatLngBounds()
  // );

  // map.setBounds(bounds);
};
</script>

<template>
  <div class="container" id="map"></div>
</template>

<style scoped>
.container {
  /* background-color: lightgoldenrodyellow; */
  width: 55%;
  height: 100%;
  /* border-right: 1px solid; */
}

#map {
  width: 100%;
  height: 100%;
}
</style>
