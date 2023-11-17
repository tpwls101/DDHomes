import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/file";

function getImg(param, success, fail) {
  local.get(`${url}/getImg/${param.saveFolder}/${param.originalName}/${param.saveName}`).then(success).catch(fail);
}

export {
  getImg,
};