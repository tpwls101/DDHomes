import { localAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance

const url = "/file";

function getImg(param, success, fail) {
  local
    .get(`${url}/getImg/${param.saveFolder}/${param.originalName}/${param.saveName}`)
    .then(success)
    .catch(fail);
}

function getBoardImgInfo(articleNo, success, fail) {
  local.get(`${url}/getBoardImgInfo/${articleNo}`).then(success).catch(fail);
}

export { getBoardImgInfo, getImg };
