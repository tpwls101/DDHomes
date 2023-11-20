import { localAxios } from "@/util/http-commons";

// axios instance (local : axios 객체)
const local = localAxios();

const url = "/member";

// 파라미터를 dto로 전달
function join(memberDto, success, fail) {
  console.log("member join", memberDto);
  local.post(`${url}/join`, JSON.stringify(memberDto)).then(success).catch(fail); // 객체 -> JSON
}

/** 로그인 */
// 입력한 로그인 정보가 디비에 저장되어 있는 정보인지 확인
async function userConfirm(param, success, fail) {
  console.log("param", param);
  await local.post(`${url}/login`, param).then(success).catch(fail);
  console.log("userConfirm ok");
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`${url}/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`${url}/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`${url}/logout/${userid}`).then(success).catch(fail);
}
/** */


function idCheck(checkId, success, fail) {
  local.get(`${url}/${checkId}`).then(success).catch(fail);
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

// function myPage(success, fail) {
//   local.get(`${url}/myPage`).then(success).catch(fail);
// }

// 파라미터를 dto로 전달
function updateMyPage(memberDto, success, fail) {
  console.log("updateMyPage!!!");
  console.log(memberDto);
  local.put(`${url}/updateMember`, JSON.stringify(memberDto)).then(success).catch(fail); // 객체 -> JSON
}

function deleteMember(userId, success, fail) {
  console.log("userId : " + userId);
  local.delete(`${url}/deleteMember/${userId}`).then(success).catch(fail);
}

export {
  join,
  userConfirm,
  findById,
  tokenRegeneration,
  logout,
  idCheck,
  findId,
  findPwd,
  // myPage,
  updateMyPage,
  deleteMember,
};
