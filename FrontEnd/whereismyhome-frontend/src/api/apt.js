import { localAxios } from "@/util/http-commons";

// axios instance (local : axios 객체)
const local = localAxios();

const url = "/apt";

// 내가 선택한 select와 option에 대한 json 결과값 받기
// 파라미터로 map 전달
function setSelector(param, success, fail) {
  console.log(param);
  local.get(`${url}/getSelector`, { params: param }).then(success).catch(fail);
}

// 선택한 아파트의 거래 내역 구하기
function getHouseDealInfo(aptCode, success, fail) {
  local.get(`${url}/getHouseDealInfo/${aptCode}`).then(success).catch(fail);
}

export { setSelector, getHouseDealInfo };
