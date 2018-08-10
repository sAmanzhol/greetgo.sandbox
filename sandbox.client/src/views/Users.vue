<template>
  <div class="users">
    <table>
      <tr>
        <th>Фио пользователя</th>
        <th>Акаунт пользователя</th>
        <th>День рождения</th>
      </tr>
      <tr v-for="user in userList">
        <td>{{user.fio}}</td>
        <td>{{user.accountName}}</td>
        <td>{{user.birthDate}}</td>
      </tr>
    </table>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import {UserRecord} from "../model/UserRecord";

  @Component({
    components: {},
  })
  export default class Users extends Vue {
    userList: UserRecord[] = [UserRecord.create({
      id: 'asd',
      fio: 'Пушкин А.С.',
      accountName: 'pushkin',
      birthDate: '1799-06-06',
    }), UserRecord.create({
      id: 'dsa',
      fio: 'Сталин И.В.',
      accountName: 'stalin',
      birthDate: '1878-12-18',
    })];
  }
</script>

<style lang="scss">

  .users {
    display: inline-block;

    table {

      border-spacing: 0;
      border-collapse: separate;

      text-align: left;

      td, th {
        border-bottom: gray 1px solid;
        padding-left: 1rem;
        padding-top: 0.3rem;
        padding-bottom: 0.3rem;
      }
      td:last-child, th:last-child {
        padding-right: 1rem;
      }

    }

  }

</style>