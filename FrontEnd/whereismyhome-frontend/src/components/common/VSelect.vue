<script setup>
import { ref } from "vue";
defineProps({ selectOption: Array });
const emit = defineEmits(["onKeySelect"]);

const key = ref("");

const onSelect = () => {
  // console.log(key.value + "선택!!!");
  emit("onKeySelect", key.value);
};
</script>

<template>
  <div>
    <select v-model="key" class="form-select form-select-sm mx-auto w-25" @change="onSelect">
      <option
        v-for="option in selectOption"
        :key="option.value"
        :value="option.value"
        :disabled="option.value === '' ? true : false"
      >
        {{ option.text }}
      </option>
    </select>
  </div>
</template>

<style scoped></style>
