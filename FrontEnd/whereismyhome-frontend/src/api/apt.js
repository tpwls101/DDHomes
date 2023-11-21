import { localAxios } from "@/util/http-commons";

// axios instance (local : axios 객체)
const local = localAxios();

const url = "/apt";

// 파라미터로 map 전달
function setSelector(param, success, fail) {
  console.log(param);
  local.get(`${url}/getSelector`, { params: param }).then(success).catch(fail);
}

export { setSelector };
