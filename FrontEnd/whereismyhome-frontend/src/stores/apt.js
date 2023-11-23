import { ref } from "vue";
import { defineStore } from "pinia";

export const useAptStore = defineStore(
  "aptStore",
  () => {
    // 아파트메인에서 아파트매매정보 버튼을 눌렀을 때 넘어가는 dongCode
    const dongCode = ref("");

    // 아파트 매물 목록
    const forsaleList = ref([]);

    // 매물 목록에서 하나 선택 할 때 쓸 매물번호
    const forsaleNo = ref("");

    // 매물 정보
    const forsaleInfo = ref({});

    // 아파트 코드
    const aptCode = ref("");

    return { dongCode, forsaleList, forsaleNo, forsaleInfo, aptCode };
  },
  {
    // 새로고침 해도 데이터가 유지되도록 해줌
    persist: {
      storage: sessionStorage,
    },
  }
);
