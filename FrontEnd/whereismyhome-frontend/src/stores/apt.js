import { ref } from "vue";
import { defineStore } from 'pinia';

export const useAptStore = defineStore('aptStore', () => {
  const dongCode = ref("");

  return {dongCode}
})