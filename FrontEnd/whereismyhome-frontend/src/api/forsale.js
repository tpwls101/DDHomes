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
function getForsaleList(dongCode, success, fail) {
  console.log(dongCode);
  local.get(`${url}/forsaleList/${dongCode}`).then(success).catch(fail);
}

function avgDealAmount(aptCode, success, fail) {
  local.get(`${url}/avgDealAmount/${aptCode}`).then(success).catch(fail);
}

export { searchAptName, registForsale, avgDealAmount, getForsaleList };
