<template>
  <div class="persons">
    <table>
      <tr>
        <th>Фио пользователя</th>
        <th>Акаунт пользователя</th>
        <th>День рождения</th>
      </tr>
      <tr v-for="p in personList">
        <td>{{p.fio}}</td>
        <td>{{p.username}}</td>
        <td>{{p.birthDate}}</td>
      </tr>
    </table>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import {PersonRecord} from '../model/PersonRecord';
  import axios from 'axios'

  @Component({
    components: {},
  })
  export default class Persons extends Vue {
    personList: PersonRecord[] = [];

    created() {
      this.personList = [];
      axios.get('/person/list').then(response => {
        this.personList = response.data.map(PersonRecord.create);
      }).catch(error => {
        console.log(error);
      })
    }
  }
</script>

<style lang="scss">

  .persons {
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