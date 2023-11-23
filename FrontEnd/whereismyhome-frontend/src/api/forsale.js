import { localAxios, fileAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance
const file = fileAxios();

const url = "/forsale";

function searchAptName(params, success, fail) {
  local.get(`${url}/search`, { params: params }).then(success).catch(fail);
}

function registForsale(params, success, fail) {
  // 파일 데이터를 FormData로 변경
  const formData = new FormData();
  for (let i = 0; i < params.imgInfos.length; i++) {
    formData.append("imgInfos", params.imgInfos[i]);
  }
  // dto데이터를 FormData로 변경
  formData.append("forsaleDto", JSON.stringify(params.forsaleDto));

  // axios
  file.post(`${url}/regist`, formData).then(success).catch(fail);
}

// 파라미터로 변수 전달
function getForsaleList(params, success, fail) {
  local.get(`${url}/forsaleList`, { params: params }).then(success).catch(fail);
}

// 아파트 하나에 대한 평균 가격
function avgDealAmount(aptCode, success, fail) {
  local.get(`${url}/avgDealAmount/${aptCode}`).then(success).catch(fail);
}

function modifyPrice(params, success, fail) {
  local.put(`${url}/modifyPrice`, JSON.stringify(params)).then(success).catch(fail);
}

function deleteForsale(forsaleNo, success, fail) {
  local.delete(`${url}/deleteForsale/${forsaleNo}`).then(success).catch(fail);
}

// 매물 찜 등록(userId, forsaleNo)
function favorite(params, success, fail) {
  local.post(`${url}/favorite`, JSON.stringify(params)).then(success).catch(fail);
}

// 찜 삭제(userId, forsaleNo)
function deleteFavorite(params, success, fail) {
  local.delete(`${url}/deleteFavorite`, { params: params }).then(success).catch(fail);
}

// 최근 2년간 xx동의 평균 거래 가격
function getAvgPrice(dongCode, success, fail) {
  console.log("dongCode : " + dongCode);
  local.get(`${url}/getAvgPrice/${dongCode}`).then(success).catch(fail);
}


export {
  searchAptName,
  registForsale,
  avgDealAmount,
  getForsaleList,
  modifyPrice,
  deleteForsale,
  favorite,
  deleteFavorite,
  getAvgPrice,
};
