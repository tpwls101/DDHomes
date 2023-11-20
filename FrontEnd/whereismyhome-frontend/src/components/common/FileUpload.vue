<script setup>
import { ref } from "vue";
import FileUploadedItem from '@/components/common/FileUploadedItem.vue';

// 사용자가 업로드한 파일명
const filenames = ref([]);

// 사용자가 파일 업로드시 파일 체크
function changeFile() {
  filenames.value = [];
  const { files } = event.target;
  // 파일 첨부하지 않고 취소 누른 경우 에러 발생 방지
  if (files.length <= 0) {
    return;
  }

  for (let i = 0; i < files.length; i++) {
    const file = files[i];
    const fileType = file.type;
    if (!fileType.includes('image')) {
      alert("이미지(JPG, JPEG, PNG)를 업로드 해 주세요.");
      filenames.value = [];
      break;
    }
    const fileReader = new FileReader();
    fileReader.readAsDataURL(file);
    filenames.value.push(file.name);
  }
}

// 이미지 삭제 버튼 클릭 시
function imgDeleteBtnClick(delFilename) {
  const dataTransfer = new DataTransfer();
  let files = document.querySelector("#upload-img").files;
  let fileArray = Array.from(files);

  filenames.value = [];
  for (let i = fileArray.length - 1; i >= 0; i--) {
    // 삭제하는 아이템 발견시
    if (fileArray[i].name === delFilename) {
      fileArray.splice(i, 1);
      // break;
    }
    else {
      filenames.value.push(fileArray[i].name);
    }
  }

  fileArray.forEach(file => {
    dataTransfer.items.add(file);
  });
  document.querySelector("#upload-img").files = dataTransfer.files;
}

</script>

<template>
  <div>
    <div class="mb-3">
      <input type="file" accept="image/*" class="form-control border" id="upload-img" name="upload-img"
        multiple="multiple" @change="changeFile">
    </div>
    <p>첨부된 이미지</p>
    <FileUploadedItem v-for="filename in filenames" :key="filename" :filename="filename"
      @imgDeleteBtnClick="imgDeleteBtnClick"></FileUploadedItem>
  </div>
</template>

<style scoped></style>