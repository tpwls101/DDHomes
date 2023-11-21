<script setup>
import { ref, onMounted } from "vue";
import { getBoardImgInfo, deleteImg } from "@/api/file";
import FileUploadedItem from "@/components/common/FileUploadedItem.vue";

const props = defineProps({ articleNo: String });

// 파일 목록
const files = ref();
// 사용자가 업로드한 파일명
const filenames = ref([]);
// 이미 업로드된 파일명(수정시 사용)
const uploadedFileNames = ref([]);

// 글 수정인 경우 true
const isModify = ref(false);

onMounted(() => {
  loadImgInfo();
});

// 업로드된 이미지 정보 가져오기
function loadImgInfo() {
  if (props.articleNo != null) {
    getBoardImgInfo(
      props.articleNo,
      ({ data }) => {
        files.value = data;
        // console.log(files.value);
        uploadedFileNames.value = [];
        for (let i = 0; i < data.length; i++) {
          uploadedFileNames.value.push(data[i].originalName);
        }

        // 등록된 파일이 없는데 이미지 목록이 있다면 수정페이지의 첫 진입인 경우이다.
        if (uploadedFileNames.value.length != 0) {
          isModify.value = true;
        }
      },
      (error) => {
        console.log(error);
      }
    );
  }
}

// 사용자가 파일 업로드시 파일 체크
function changeFile() {
  // console.log(document.querySelector("#upload-img").files);
  filenames.value = [];
  const { files } = event.target;
  // 파일 첨부하지 않고 취소 누른 경우 에러 발생 방지
  if (files.length <= 0) {
    return;
  }

  // 현재 업로드한 파일 확장자 확인
  for (let i = 0; i < files.length; i++) {
    const file = files[i];
    const fileType = file.type;
    if (!fileType.includes("image")) {
      alert("이미지(JPG, JPEG, PNG)를 업로드 해 주세요.");
      filenames.value = [];
      document.querySelector("#upload-img").files = new DataTransfer().files;
      break;
    }
    const fileReader = new FileReader();
    fileReader.readAsDataURL(file);
    filenames.value.push(file.name);
  }

  // 이미 업로드된 파일 이름과 현재 올린 파일 이름 중복 확인
  if (isModify.value) {
    for (let i = 0; i < uploadedFileNames.value.length; i++) {
      for (let j = 0; j < files.length; j++) {
        if (files[j].name === uploadedFileNames.value[i]) {
          alert("업로드하는 파일의 이름은 중복될 수 없습니다.");
          filenames.value = [];
          document.querySelector("#upload-img").files = new DataTransfer().files;
          return;
        }
      }
    }
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
    } else {
      filenames.value.push(fileArray[i].name);
    }
  }
  filenames.value.reverse();

  fileArray.forEach((file) => {
    dataTransfer.items.add(file);
  });
  document.querySelector("#upload-img").files = dataTransfer.files;
}

// 등록된 이미지 삭제 버튼 클릭 시
function uploadedImgDeleteBtnClick(delFilename) {
  if (confirm(delFilename + " 이미지를 삭제하시겠습니까?")) {
    for (let i = 0; i < files.value.length; i++) {
      if (files.value[i].originalName === delFilename) {
        deleteImg(
          files.value[i],
          ({ data }) => {
            // console.log(data);
            loadImgInfo();
          },
          (error) => {
            console.log(error);
          }
        );
      }
    }
  }
}
</script>

<template>
  <div>
    <div class="mb-3">
      <input type="file" accept="image/*" class="form-control border" id="upload-img" name="upload-img"
        multiple="multiple" @change="changeFile" />
    </div>
    <div>
      <p>첨부한 이미지</p>
      <FileUploadedItem v-for="filename in filenames" :key="filename" :filename="filename"
        @imgDeleteBtnClick="imgDeleteBtnClick"></FileUploadedItem>
    </div>
    <div v-if="isModify">
      <p>등록된 이미지</p>
      <FileUploadedItem v-for="filename in uploadedFileNames" :key="filename" :filename="filename"
        @imgDeleteBtnClick="uploadedImgDeleteBtnClick"></FileUploadedItem>
    </div>
  </div>
</template>

<style scoped></style>
