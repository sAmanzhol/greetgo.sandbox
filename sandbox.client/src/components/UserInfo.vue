<template>
  <div class="user-info">
    <button @click="onGetUserInfo()">Получить данные пользователя</button>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import axios from 'axios'

  @Component
  export default class UserInfo extends Vue {
    onGetUserInfo() {
      console.log(process.env.VUE_APP_URL_PREFIX);
      axios.get("/api/auth/probe", {
        params: {
          param: 'err1'
        }
      }).then(response => {
        console.log('OK', response);
      }, error => {
        console.log('ERROR', error);
      });
    }
  }
</script>

<style scoped lang="scss">
  button {
    padding: .5rem;
    color: #314050;
  }
</style>
