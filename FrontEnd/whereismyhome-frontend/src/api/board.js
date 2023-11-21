import { localAxios, fileAxios } from "@/util/http-commons";

const local = localAxios(); // axios instance
const file = fileAxios();

const url = "/board";

// ===================================================================
// Board

// function registArticle(boardDto, success, fail) {
//   // dto형태 객체로 파라미터 넘김
//   local.post(`${url}/write`, JSON.stringify(boardDto)).then(success).catch(fail);
// }

function registArticle(params, success, fail) {
  // 파일 데이터를 FormData로 변경
  const formData = new FormData();
  for (let i = 0; i < params.imgInfos.length; i++) {
    formData.append("imgInfos", params.imgInfos[i]);
  }
  // dto데이터를 FormData로 변경
  formData.append("boardDto", JSON.stringify(params.boardDto));

  // axios
  file.post(`${url}/write`, formData).then(success).catch(fail);
}

function listArticle(param, success, fail) {
  // map으로 파라미터 넘김
  local.get(`${url}/list`, { params: param }).then(success).catch(fail);
}

function detailArticle(articleNo, success, fail) {
  // int를 Path Variable로 파라미터 받음
  local.get(`${url}/detail/${articleNo}`).then(success).catch(fail);
}

// detailArticle로 상세정보 받지 않는 이유는 content의 줄바꿈 문자 때문
function getModifyArticle(articleNo, success, fail) {
  // int를 Path Variable로 파라미터 받음
  local.get(`${url}/modify/${articleNo}`).then(success).catch(fail);
}

// function modifyArticle(boardDto, success, fail) {
//   // dto형태 객체로 파라미터 넘김
//   local.put(`${url}/modify`, JSON.stringify(boardDto)).then(success).catch(fail);
// }

function modifyArticle(modifyParams, success, fail) {
  // 파일 데이터를 FormData로 변경
  const formData = new FormData();
  for (let i = 0; i < modifyParams.imgInfos.length; i++) {
    formData.append("imgInfos", modifyParams.imgInfos[i]);
  }
  // dto데이터를 FormData로 변경
  formData.append("boardDto", JSON.stringify(modifyParams.boardDto));

  // axios
  file.post(`${url}/modify`, formData).then(success).catch(fail);
}

function deleteArticle(articleNo, success, fail) {
  // int를 Path Variable로 파라미터 받음
  console.log(articleNo);
  local.delete(`${url}/delete/${articleNo}`).then(success).catch(fail);
}

// ===================================================================
// Comment

function registComment(boardCommentDto, success, fail) {
  // dto 형태로 파라미터 받기
  local.post(`${url}/comment/write`, JSON.stringify(boardCommentDto)).then(success).catch(fail);
}

function listComment(articleNo, success, fail) {
  // path variable로 파라미터 넘기기
  local.get(`${url}/comment/list/${articleNo}`).then(success).catch(fail);
}

function likeComment(param, success, fail) {
  // map 형태로 파라미터 넘기기
  local.put(`${url}/comment/like`, JSON.stringify(param)).then(success).catch(fail);
}

function modifyComment(param, success, fail) {
  // map 형태로 파라미터 넘기기
  local.put(`${url}/comment/modify`, JSON.stringify(param)).then(success).catch(fail);
}

function deleteComment(commentNo, success, fail) {
  // path variable로 파라미터 넘기기
  local.delete(`${url}/comment/delete/${commentNo}`).then(success).catch(fail);
}

export {
  listArticle,
  detailArticle,
  registArticle,
  getModifyArticle,
  modifyArticle,
  deleteArticle,
  registComment,
  listComment,
  likeComment,
  modifyComment,
  deleteComment,
};
