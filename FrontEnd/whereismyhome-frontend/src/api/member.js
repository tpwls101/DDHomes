import { localAxios } from "@/util/http-commons";

// axios instance (local : axios 객체)
const local = localAxios();

const url = "/member";

// 파라미터를 dto로 전달
function join(memberDto, success, fail) {
  console.log("member join", memberDto);
  local.post(`${url}/join`, JSON.stringify(memberDto)).then(success).catch(fail); // 객체 -> JSON
}

// 파라미터를 map으로 전달
function findId(param, success, fail) {
  // console.log(param);
  // local.get(`${url}/findid`, { params: param }).then(success).catch(fail);
  local.get(`${url}/findId`, { params: param }).then(success).catch(fail); // REST API의 url은 백엔드의 url과 동일하게 맞춰줄 것!!
}

function findPwd(param, success, fail) {
  local.get(`${url}/findPwd`, { params: param }).then(success).catch(fail);
}

function myPage(success, fail) {
  local.get(`${url}/myPage`).then(success).catch(fail);
}

// 파라미터를 dto로 전달
function updateMyPage(memberDto, success, fail) {
  console.log("updateMyPage!!!");
  console.log(memberDto);
  local.put(`${url}/updateMember`, JSON.stringify(memberDto)).then(success).catch(fail); // 객체 -> JSON
}

function deleteMember(userId, success, fail) {
  console.log("userId : " + userId);
  local.delete(`${url}/deleteMember/${userId}`).then(success).catch(fail);
  console.log("d왜안돼,,,,,,,,,,,,,");
}

export { join, findId, findPwd, myPage, updateMyPage, deleteMember };
