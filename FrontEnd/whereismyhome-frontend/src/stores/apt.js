import { ref } from "vue";
import { defineStore } from 'pinia';

export const useAptStore = defineStore('aptStore', () => {
  // 아파트메인에서 아파트매매정보 버튼을 눌렀을 때 넘어가는 dongCode
  const dongCode = ref("");

  // 아파트 매물 목록
  const forsaleList = ref([]);

  // 매물 정보
  const forsaleInfo = ref({});

  return {dongCode, forsaleList, forsaleInfo}
})