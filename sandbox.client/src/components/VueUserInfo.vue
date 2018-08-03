<template>
  <div class="user-info">
    <button @click="onGetUserInfo()" v-if="!userInfo" class="button__user-info">
      Получить данные пользователя
    </button>

    <table v-if="userInfo">
      <tr>
        <td>Фамилия:</td>
        <td>{{userInfo.surname}}</td>
      </tr>
      <tr>
        <td>Имя:</td>
        <td>{{userInfo.name}}</td>
      </tr>
      <tr>
        <td>Отчество:</td>
        <td>{{userInfo.patronymic}}</td>
      </tr>
      <tr>
        <td>Аккаунт:</td>
        <td>{{userInfo.accountName}}</td>
      </tr>
      <tr>
        <td>Жёлтый:</td>
        <td>
          <span v-if="userInfo.yellow" class="yes">ДА</span>
          <span v-if="!userInfo.yellow" class="no">НЕТ</span>
        </td>
      </tr>
    </table>

  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import axios from 'axios'
  import {UserInfo} from "../model/UserInfo";

  @Component
  export default class VueUserInfo extends Vue {

    userInfo: UserInfo | null = null;

    onGetUserInfo() {
      axios.get("/api/auth/userInfo").then(response => {
        this.userInfo = UserInfo.create(response.data);
      }, error => {
        console.log('ERROR', error);
      });
    }
  }
</script>

<style scoped lang="scss">
  .button__user-info {
    padding: .5rem;
    color: #314050;
  }

  table {
    display: inline-block;

    tr {
      td:first-child {
        font-weight: bold;
        text-align: right;
      }

      td {
        text-align: left;
      }
    }

    span.yes {
      color: green;
      font-weight: bold;
    }
    span.no {
      color: red;
      font-weight: bold;
    }
  }
</style>
