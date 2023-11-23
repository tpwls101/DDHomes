<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { setSelector } from "@/api/apt";
import { useAptStore } from "@/stores/apt";
import { useMemberStore } from "@/stores/member";
import { storeToRefs } from "pinia";

const router = useRouter();

const aptStore = useAptStore();
const memberStore = useMemberStore();

const { dongCode } = storeToRefs(aptStore);
const { isLogin } = storeToRefs(memberStore);

const param = ref({
  selectorId: "sido", // 설정할 셀렉터 id 속성
  previousSelectedValue: "", // 이전 셀렉터 선택값
});

const sidoList = ref({}); // 시도 option들을 담을 리스트
const gugunList = ref({}); // 구군 option들을 담을 리스트
const dongList = ref({}); // 동 option들을 담을 리스트

const selector = ref(); // sido, gugun, dong
const selectedValue = ref(""); // 현재 선택된 값의 value

onMounted(() => {
  selectorSetting();
});

const selectorSetting = () => {
  // select 선택해서 option 값 불러오기
  // axios API 호출
  setSelector(
    param.value,
    ({ data }) => {
      console.log("success");
      console.log(data);
      addOption(param.value.selectorId, data);
    },
    (error) => {
      console.log("error");
    }
  );
};

//셀렉터 옵션 설정
const addOption = (selectorId, data) => {
  console.log("selectorId : " + selectorId);
  console.log("select에 option들을 추가해줍시다!!!");

  switch (selectorId) {
    case "sido":
      sidoList.value = data;
      break;
    case "gugun":
      gugunList.value = data;
      break;
    case "dong":
      dongList.value = data;
      break;
  }
};

// 앞의 selector 선택하면 뒤에 있는 selector의 옵션은 하나도 없게 한다.
const initOption = (selectorId) => {
  const options = document.querySelector("#" + selectorId);
  options.length = 1;
};

// 시도 값이 선택되면 구군 정보 얻기
const sidoChanged = () => {
  console.log("시도 선택 : 시도 값 바뀜");

  selector.value = document.getElementById("sido");
  // console.log(selector.value);

  // selectedValue.value = this[this.selectorIndex].value;
  selectedValue.value = selector.value.options[selector.value.selectedIndex].value;
  console.log("현재 선택된 시도의 value 값(dongCode) : " + selectedValue.value);

  param.value.selectorId = "gugun";
  param.value.previousSelectedValue = selectedValue.value;
  selectorSetting(param.value);

  initOption("gugun");
  initOption("dong");
};

// 구군 값이 선택되면 동 정보 얻기
const gugunChanged = () => {
  console.log("구군 선택 : 구군 값 바뀜");

  selector.value = document.getElementById("gugun");
  // console.log(selector.value);

  // selectedValue.value = this[this.selectorIndex].value;
  selectedValue.value = selector.value.options[selector.value.selectedIndex].value;
  console.log("현재 선택된 구군의 value 값(dongCode) : " + selectedValue.value);

  param.value.selectorId = "dong";
  param.value.previousSelectedValue = selectedValue.value;
  selectorSetting(param.value);

  initOption("dong");
};

// 아파트매매정보 버튼 클릭 시 호출되는 함수
const aptInfo = () => {
  // if (
  //   document.querySelector("#sido").value[document.querySelector("#sido").value.selectedIndex].value
  // ) {
  //   alert("시도를 선택해주세요.");
  // } else if (document.querySelector("#gugun") == "") {
  //   alert("구군을 선택해주세요.");
  // } else if (document.querySelector("#dong") == "") {
  //   alert("동을 선택해주세요.");
  // }

  if (!isLogin.value) {
    alert("로그인 후 이용 가능한 서비스입니다.");
    return;
  }

  if (
    document.querySelector("#sido").value != "" &&
    document.querySelector("#gugun").value != "" &&
    document.querySelector("#dong").value != ""
  ) {
    selector.value = document.querySelector("#dong");
    selectedValue.value = selector.value[selector.value.selectedIndex].value;
    console.log("현재 선택된 동의 value 값(dongCode) : " + selectedValue.value);

    // router.push({ name: "apt-bundle", param: selectedValue.value }); // dongCode를 매개변수로 전달
    // dongCode를 aptStore에 저장
    dongCode.value = selectedValue.value;

    router.push({ name: "apt-bundle" });
  } else {
    alert("모든 지역 필터를 선택해주세요!");
    return;
  }
};
</script>

<template>
  <div class="row col-md-12 justify-content-center mb-2">
    <div class="form-group col-md-2">
      <select class="form-select bg-secondary text-light" id="sido" @change="sidoChanged">
        <option value="" selected="selected">시도선택</option>
        <option v-for="item in sidoList" :key="item.dongCode" :value="item.dongCode">
          {{ item.sidoName }}
        </option>
      </select>
    </div>
    <div class="form-group col-md-2">
      <select class="form-select bg-secondary text-light" id="gugun" @change="gugunChanged">
        <option value="" selected="selected">구군선택</option>
        <option v-for="item in gugunList" :key="item.dongCode" :value="item.dongCode">
          {{ item.gugunName }}
        </option>
      </select>
    </div>
    <div class="form-group col-md-2">
      <select class="form-select bg-secondary text-light" id="dong" @change="dongChanged">
        <option value="" selected="selected">동선택</option>
        <option v-for="item in dongList" :key="item.dongCode" :value="item.dongCode">
          {{ item.dongName }}
        </option>
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
      <button
        type="button"
        id="list-btn"
        class="btn btn-dark btn"
        style="outline: 1px solid white"
        @click="aptInfo"
      >
        아파트매매정보
      </button>
    </div>
  </div>
</template>

<style scoped></style>
