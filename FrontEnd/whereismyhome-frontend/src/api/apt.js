import { localAxios } from "@/util/http-commons";

// axios instance (local : axios 객체)
const local = localAxios();

const url = "/apt";

// function join(memberDto, success, fail) {
//     console.log("member join", memberDto);
//     local.post(`${url}/join`, JSON.stringify(memberDto)).then(success).catch(fail); // 객체 -> JSON
//   }

// 파라미터로 map 전달
function setSelector(param, success, fail) {
  console.log(param);
  local.get(`${url}/getSelector`, { params: param }).then(success).catch(fail);
}

export { setSelector };
