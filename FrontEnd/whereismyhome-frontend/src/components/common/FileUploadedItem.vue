<script setup>
const props = defineProps({ filename: String });
const emit = defineEmits(["imgDeleteBtnClick"]);

// 삭제 버튼 클릭 시
function imgDeleteBtnClick() {
  emit("imgDeleteBtnClick", props.filename);
}

</script>

<template>
  <div>
    <div class="imginfo">
      {{ filename }}<input class="btn-delete-img" type="button" value="삭제" @click="imgDeleteBtnClick">
    </div>
  </div>
</template>

<style scoped>
.imginfo {
  border: 1px solid black;
  height: 32px;
  margin: 10px;
}

.btn-delete-img {
  float: right;
}
</style>