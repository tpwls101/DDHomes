<script setup>
import { ref, onMounted, watch } from "vue";
import { useAptStore } from "../../stores/apt";
import { storeToRefs } from "pinia";

const aptStore = useAptStore();

const positions = ref([]);
const markers = ref([]);

const { forsaleList } = storeToRefs(aptStore);
const { forsaleNo } = storeToRefs(aptStore);

const lat = ref("");
const lng = ref("");

var map;
// 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
var placeOverlay;
var contentNode; // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
var placeMarkers = []; // 마커를 담을 배열입니다
var currCategory = ""; // 현재 선택된 카테고리를 가지고 있을 변수입니다
// 장소 검색 객체를 생성합니다
var ps;

// autoload=false
// 비동기 로딩은 당장 페이지에서 필요 없는 지도 관련 스크립트 전체를 미리 로딩하지 않고 필요한 경우에만 로딩하기 위해 사용
onMounted(() => {
  // forsaleList.value = forsaleList.value;

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

const initMap = () => {
  const container = document.getElementById("map");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  map = new kakao.maps.Map(container, options);

  placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
  contentNode = document.createElement("div");
  ps = new kakao.maps.services.Places(map);

  // 지도에 idle 이벤트를 등록합니다
  kakao.maps.event.addListener(map, "idle", searchPlaces);

  // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
  contentNode.className = "placeinfo_wrap";

  // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
  // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
  addEventHandle(contentNode, "mousedown", kakao.maps.event.preventMap);
  addEventHandle(contentNode, "touchstart", kakao.maps.event.preventMap);

  // 커스텀 오버레이 컨텐츠를 설정합니다
  placeOverlay.setContent(contentNode);

  // 각 카테고리에 클릭 이벤트를 등록합니다
  addCategoryClickEvent();

  checkData();
};

// 시군동 선택 후 지도 페이지로 넘어왔을 때 아파트 매물 목록들 지도에 마커 찍어주기
const loadMarkers = () => {
  console.log("loadMarkers");

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
  const bounds = positions.value.reduce(
    (bounds, position) => bounds.extend(position.latlng),
    new kakao.maps.LatLngBounds()
  );

  map.setBounds(bounds);
};

// 매물번호를 반응형으로 감시해서 값이 바뀌면 매물을 선택한 것이므로
// 카카오 lat, lng 설정해서 지도에 해당 매물의 마커 보여주기
// + 해당 위치에 인포윈도우 보여주기
watch(
  () => forsaleNo.value,
  () => {
    // 마커 이동
    const index = ref(0); // markers 배열의 인덱스
    for (let i = 0; i < forsaleList.value.length; i++) {
      if (forsaleList.value[i].forsaleNo == forsaleNo.value) {
        lat.value = forsaleList.value[i].lat;
        lng.value = forsaleList.value[i].lng;
        index.value = i;
      }
    }
    var moveLatLon = new kakao.maps.LatLng(lat.value, lng.value);
    map.panTo(moveLatLon);

    const aptName = forsaleList.value[index.value].apartmentName;
    // 인포윈도우 보여주기
    // const iwContent = '<div style="padding:5px;">' + aptName.value + "</div>";
    const iwContent = "<div style='padding:5px;'>" + aptName + "</div>";
    console.log(typeof iwContent);
    const iwPosition = new kakao.maps.LatLng(lat.value, lng.value);
    const iwRemoveable = true; // removeable 속성을 true로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됨

    const infowindow = new kakao.maps.InfoWindow({
      position: iwPosition,
      content: iwContent,
      removable: iwRemoveable,
    });

    infowindow.open(map, markers[index.value]);
  }
);

// =======================================================
// 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
function addEventHandle(target, type, callback) {
  if (target.addEventListener) {
    target.addEventListener(type, callback);
  } else {
    target.attachEvent("on" + type, callback);
  }
}

// 카테고리 검색을 요청하는 함수입니다
function searchPlaces() {
  if (!currCategory) {
    return;
  }

  // 커스텀 오버레이를 숨깁니다
  placeOverlay.setMap(null);

  // 지도에 표시되고 있는 마커를 제거합니다
  removeMarker();

  ps.categorySearch(currCategory, placesSearchCB, { useMapBounds: true });
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
  if (status === kakao.maps.services.Status.OK) {
    // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
    displayPlaces(data);
  } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
  } else if (status === kakao.maps.services.Status.ERROR) {
    // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
  }
}

// 지도에 마커를 표출하는 함수입니다
function displayPlaces(places) {
  // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
  // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
  var order = document.getElementById(currCategory).getAttribute("data-order");

  for (var i = 0; i < places.length; i++) {
    // 마커를 생성하고 지도에 표시합니다
    var placeMarker = addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

    // 마커와 검색결과 항목을 클릭 했을 때
    // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
    (function (placeMarker, place) {
      kakao.maps.event.addListener(placeMarker, "click", function () {
        displayPlaceInfo(place);
      });
    })(placeMarker, places[i]);
  }
}

// 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, order) {
  var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
    imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
    imgOptions = {
      spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
      spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
      offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
    },
    markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
    placeMarker = new kakao.maps.Marker({
      position: position, // 마커의 위치
      image: markerImage,
    });

  placeMarkers.push(placeMarker); // 배열에 생성된 마커를 추가합니다
  placeMarker.setMap(map); // 지도 위에 마커를 표출합니다

  return placeMarker;
}

// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarker() {
  for (var i = 0; i < placeMarkers.length; i++) {
    placeMarkers[i].setMap(null);
  }
  placeMarkers = [];
}

// 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
function displayPlaceInfo(place) {
  var content =
    '<div class="placeinfo">' +
    '   <a class="title" href="' +
    place.place_url +
    '" target="_blank" title="' +
    place.place_name +
    '">' +
    place.place_name +
    "</a>";

  if (place.road_address_name) {
    content +=
      '    <span title="' +
      place.road_address_name +
      '">' +
      place.road_address_name +
      "</span>" +
      '  <span class="jibun" title="' +
      place.address_name +
      '">(지번 : ' +
      place.address_name +
      ")</span>";
  } else {
    content += '    <span title="' + place.address_name + '">' + place.address_name + "</span>";
  }

  content +=
    '    <span class="tel">' + place.phone + "</span>" + "</div>" + '<div class="after"></div>';

  contentNode.innerHTML = content;
  placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
  placeOverlay.setMap(map);
}

// 각 카테고리에 클릭 이벤트를 등록합니다
function addCategoryClickEvent() {
  var category = document.getElementById("category"),
    children = category.children;

  for (var i = 0; i < children.length; i++) {
    children[i].onclick = onClickCategory;
  }
}

// 카테고리를 클릭했을 때 호출되는 함수입니다
function onClickCategory() {
  var id = this.id,
    className = this.className;

  placeOverlay.setMap(null);

  if (className === "on") {
    currCategory = "";
    changeCategoryClass();
    removeMarker();
  } else {
    currCategory = id;
    changeCategoryClass(this);
    searchPlaces();
  }
}

// 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
function changeCategoryClass(el) {
  var category = document.getElementById("category"),
    children = category.children,
    i;

  for (i = 0; i < children.length; i++) {
    children[i].className = "";
  }

  if (el) {
    el.className = "on";
  }
}
</script>

<template>
  <div class="map_wrap map-container">
    <div id="map"></div>
    <ul id="category">
      <li id="BK9" data-order="0">
        <span class="category_bg bank"></span>
        은행
      </li>
      <li id="MT1" data-order="1">
        <span class="category_bg mart"></span>
        마트
      </li>
      <li id="AG2" data-order="2">
        <span class="category_bg pharmacy"></span>
        부동산
      </li>
      <li id="SW8" data-order="3">
        <span class="category_bg oil"></span>
        지하철
      </li>
      <li id="CE7" data-order="4">
        <span class="category_bg cafe"></span>
        카페
      </li>
      <li id="CS2" data-order="5">
        <span class="category_bg store"></span>
        편의점
      </li>
    </ul>
  </div>
</template>

<style>
.map-container {
  /* background-color: lightgoldenrodyellow; */
  width: 50%;
  height: 100%;
  /* width: 200px;
  height: 100px; */
  /* border-right: 1px solid; */
}

#map {
  width: 100%;
  height: 800px;
}

.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  height: 350px;
}
#category {
  position: absolute;
  top: 10px;
  left: 10px;
  border-radius: 5px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 2;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 0 auto 3px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -10px 0;
}
#category li .mart {
  background-position: -10px -36px;
}
#category li .pharmacy {
  background-position: -10px -72px;
}
#category li .oil {
  background-position: -10px -108px;
}
#category li .cafe {
  background-position: -10px -144px;
}
#category li .store {
  background-position: -10px -180px;
}
#category li.on .category_bg {
  background-position-x: -46px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}
.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050 url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
