import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: () => import("@/views/TheAptView.vue"),
    },
    {
      path: "/member",
      name: "member",
      component: () => import("@/views/TheMemberView.vue"),
      children: [
        {
          path: "join",
          name: "member-join",
          component: () => import("@/components/member/MemberJoin.vue"),
        },
        {
          path: "mypage",
          name: "member-mypage",
          component: () => import("@/components/member/MemberMyPage.vue"),
        },
        {
          path: "findid",
          // name: "member-findId",
          name: "member-findid",
          component: () => import("@/components/member/MemberFindId.vue"),
        },
        {
          path: "findpwd",
          name: "member-findpwd",
          component: () => import("@/components/member/MemberFindPwd.vue"),
        },
      ],
    },
    {
      path: "/board",
      name: "board",
      component: () => import("@/views/TheBoardView.vue"),
      children: [
        {
          path: "detail/:articleNo",
          name: "board-detail",
          component: () => import("@/components/board/BoardDetail.vue"),
        },
        {
          path: "list/:boardType",
          name: "board-list",
          component: () => import("@/components/board/BoardList.vue"),
        },
        {
          path: "modify/:articleNo",
          name: "board-modify",
          component: () => import("@/components/board/BoardModify.vue"),
        },
        {
          path: "write/:boardType",
          name: "board-write",
          component: () => import("@/components/board/BoardWrite.vue"),
        },
      ],
    },
    {
      path: "/apt",
      name: "apt",
      component: () => import("@/views/TheAptListView.vue"),
    }
  ],
});

export default router;
