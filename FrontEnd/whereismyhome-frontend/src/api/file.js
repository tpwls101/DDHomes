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

function getForsaleImgInfo(forsaleNo, success, fail) {
  local.get(`${url}/getForsaleImgInfo/${forsaleNo}`).then(success).catch(fail);
}

function deleteImg(imgInfoDto, success, fail) {
  let param = {
    imgInfoNo: imgInfoDto.imgInfoNo,
    forsaleNo: imgInfoDto.forsaleNo,
    articleNo: imgInfoDto.articleNo,
    saveFolder: imgInfoDto.saveFolder,
    originalName: imgInfoDto.originalName,
    saveName: imgInfoDto.saveName,
  };
  local.delete(`${url}/deleteImg`, { params: param }).then(success).catch(fail);
}

export { getImg, getBoardImgInfo, getForsaleImgInfo, deleteImg };
