import axios from "axios";

const { VITE_VUE_API_URL } = import.meta.env;

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

// 파일(이미지) 업로드 위한 axios 설정으로, content type이 다름
// 이는 FormData형태를 보내기 위함
function fileAxios() {
  const instance = axios.create({
    baseURL: VITE_VUE_API_URL,
    headers: {
      "Content-Type": "multipart/form-data",
      charset: "utf-8",
    },
  });
  return instance;
}

export { localAxios, fileAxios };
