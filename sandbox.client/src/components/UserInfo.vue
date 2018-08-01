<template>
  <div class="user-info">
    <button @click="onGetUserInfo()">Получить данные пользователя</button>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';

  @Component
  export default class UserInfo extends Vue {
    onGetUserInfo() {
      console.log("asd")
    }
  }
</script>

<style scoped lang="scss">
  button {
    padding: .5rem;
    color: #314050;
  }
</style>
