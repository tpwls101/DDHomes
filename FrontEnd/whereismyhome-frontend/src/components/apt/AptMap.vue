<script setup>
import { onMounted } from "vue";

var map;

// autoload=false
// 비동기 로딩은 당장 페이지에서 필요 없는 지도 관련 스크립트 전체를 미리 로딩하지 않고 필요한 경우에만 로딩하기 위해 사용
onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${
      import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
    }&libraries=services,clusterer`;
    /* global kakao */
    script.onload = () => kakao.maps.load(() => initMap());
    document.head.appendChild(script);
  }
});

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  // loadMarkers();
};
</script>

<template>
  <div class="container" id="map"></div>

  <!-- <div id="map"></div> -->
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
