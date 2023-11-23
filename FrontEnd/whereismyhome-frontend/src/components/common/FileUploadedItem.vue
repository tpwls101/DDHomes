<script setup>
const props = defineProps({ filename: String });
const emit = defineEmits(["imgDeleteBtnClick"]);

// 삭제 버튼 클릭 시
function imgDeleteBtnClick() {
  emit("imgDeleteBtnClick", props.filename);
}
</script>

<template>
  <div class="imginfo-box">
    <!-- <div> -->
    <div class="imginfo">{{ filename }}</div>
    <!-- </div> -->
    <input class="btn-delete-img" type="button" value="삭제" @click="imgDeleteBtnClick" />
  </div>
</template>

<style scoped>
.imginfo-box {
  display: inline-flex;
  width: 100%;
  border: 1px solid black;
  height: 32px;
  margin: 10px;
}

.imginfo {
  overflow: hidden;
  text-overflow: ellipsis;
}

.btn-delete-img {
  margin-left: auto;
}
</style>
