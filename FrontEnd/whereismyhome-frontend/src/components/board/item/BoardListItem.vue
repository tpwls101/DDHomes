<script setup>
// const { article } = defineProps(['article']);
defineProps({ article: Object });
</script>

<template>
  <tr class="text-center">
    <!-- 글번호 -->
    <th scope="row">{{ article.articleNo }}</th>
    <!-- 제목 -->
    <td class="text-start">
      <!-- <a href="#" class="article-title link-dark" :data-no="article.articleNo" style="text-decoration: none">
        {{ article.subject }}
      </a> -->
      <router-link
        :to="{name: 'board-detail', params: { articleNo: article.articleNo } }"
        class="article-title link-dark"
      >
      {{ article.subject }}
      </router-link>
    </td>
    <!-- 작성자 -->
    <td>{{ article.userId }}</td>
    <!-- 조회수 -->
    <td>{{ article.hit }}</td>
    <!-- 작성일 -->
    <td>{{ article.registerTime }}</td>
  </tr>
</template>

<style scoped>
.article-title {
  text-decoration: none;
}
</style>