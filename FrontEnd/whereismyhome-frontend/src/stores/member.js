import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";

import { userConfirm, findById, tokenRegeneration, logout } from "@/api/member";
import { httpStatusCode } from "@/util/http-status";

export const useMemberStore = defineStore(
  "memberStore",
  () => {
    const router = useRouter();

    const isLogin = ref(false);
    const isLoginError = ref(false);
    const userInfo = ref(null); // 사용자 정보(세션 느낌)
    const isValidToken = ref(false);

    // 폼에서 입력받은 아이디와 비밀번호를 매개변수로 넘겨 로그인
    const userLogin = async (loginUser) => {
      // 입력한 로그인 정보가 디비에 저장되어 있는 정보인지 확인
      await userConfirm(
        loginUser,
        (response) => {
          console.log("login ok!!!!", response.status); // 201
          console.log("login ok!!!!", httpStatusCode.CREATE); // 201
          if (response.status === httpStatusCode.CREATE) {
            let { data } = response;
            // console.log("data", data);
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            console.log("accessToken", accessToken);
            console.log("refreshToken", refreshToken);
            isLogin.value = true;
            isLoginError.value = false;
            isValidToken.value = true;
            sessionStorage.setItem("accessToken", accessToken);
            sessionStorage.setItem("refreshToken", refreshToken);
            console.log("sessiontStorage에 담았다", isLogin.value);
          } else {
            console.log("로그인 실패했다");
            isLogin.value = false;
            isLoginError.value = true;
            isValidToken.value = false;
          }
        },
        (error) => {
          console.error(error);
          alert("[로그인 실패] 회원 정보가 없습니다. 회원가입 후 이용해주세요.");
        }
      );
    };

    // 해당 accessToken을 가진 사용자 정보 얻기
    const getUserInfo = (token) => {
      let decodeToken = jwtDecode(token); // 디코딩된 토큰
      console.log("2. decodeToken", decodeToken);
      // 디코딩하여 얻은 사용자아이디로 사용자 정보 얻기
      findById(
        decodeToken.userId,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            userInfo.value = response.data.userInfo; // 세션을 가져온 느낌?
            // userInfo.value = response.data;
            // isLogin.value = response.data.isLogin;
            console.log("3. getUserInfo data >> ", response.data);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.error(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          isValidToken.value = false;

          await tokenRegenerate();
        }
      );
    };

    const tokenRegenerate = async () => {
      console.log("토큰 재발급 >> 기존 토큰 정보 : {}", sessionStorage.getItem("accessToken"));
      await tokenRegeneration(
        JSON.stringify(userInfo.value), // stringify : 객체 -> JSON 문자열 형태로
        (response) => {
          if (response.status === httpStatusCode.CREATE) {
            let accessToken = response.data["access-token"];
            console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
            sessionStorage.setItem("accessToken", accessToken);
            isValidToken.value = true;
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === httpStatusCode.UNAUTHORIZED) {
            console.log("갱신 실패");
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              userInfo.value.userid,
              (response) => {
                if (response.status === httpStatusCode.OK) {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                isLogin.value = false;
                userInfo.value = null;
                isValidToken.value = false;
                router.push({ name: "user-login" });
              },
              (error) => {
                console.error(error);
                isLogin.value = false;
                userInfo.value = null;
              }
            );
          }
        }
      );
    };

    const userLogout = async (userid) => {
      await logout(
        userid,
        (response) => {
          if (response.status === httpStatusCode.OK) {
            isLogin.value = false;
            userInfo.value = null;
            isValidToken.value = false;

            sessionStorage.clear();
            console.log("accessToken과 refreshToken 삭제 완료!");
          } else {
            console.error("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    };

    return {
      isLogin,
      isLoginError,
      userInfo,
      isValidToken,
      userLogin,
      getUserInfo,
      tokenRegenerate,
      userLogout,
    };
  },
  {
    // 새로고침 해도 데이터가 유지되도록 해줌
    persist: {
      storage: sessionStorage,
    },
  }
);
