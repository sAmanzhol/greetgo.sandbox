<template>
  <div class="login">
    Войти
    <input type="text" placeholder="Enter Username" name="name"
           :value="username" @input="updateUsername($event)"/>
    :
    <input type="password" placeholder="Enter Password" name="password"
           :value="password" @input="updatePassword($event)"/>
    <button class="button__enter" @click="onEnter" :disabled="!username||!password">Войти</button>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';

  @Component
  export default class VueLogin extends Vue {
    username: string = '';
    password: string = '';

    updateUsername($event) {
      this.username = $event.target.value
    }

    updatePassword($event) {
      this.password = $event.target.value
    }

    onEnter() {
      console.log("username = ", this.username, "password = ", this.password)
    }
  }
</script>

<style scoped lang="scss">
  .button__enter {
  }

  button {
    background: #4B99AD;
    padding: 8px 15px 8px 15px;
    border: none;
    color: #fff;
  }

  button:hover {
    background: #4691A4;
    box-shadow: none;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
  }

  button:active {
    color: #c0c0c0;
  }

  button:disabled {
    background: #dadada;
    color: #c0c0c0;
  }

  input {
    box-sizing: border-box;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    border: 1px solid #BEBEBE;
    padding: 7px;
    margin: 0px;
    -webkit-transition: all 0.30s ease-in-out;
    -moz-transition: all 0.30s ease-in-out;
    -ms-transition: all 0.30s ease-in-out;
    -o-transition: all 0.30s ease-in-out;
    outline: none;
  }

  input:focus {
    -moz-box-shadow: 0 0 8px #88D5E9;
    -webkit-box-shadow: 0 0 8px #88D5E9;
    box-shadow: 0 0 8px #88D5E9;
    border: 1px solid #88D5E9;
  }
</style>
