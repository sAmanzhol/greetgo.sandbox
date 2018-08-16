<template>
  <div class="login">
    <div v-if="isWaiting()">
      WAITING...
    </div>
    <div v-if="isLogin()">
      <input type="text" placeholder="Enter Username" name="login_username"
             :value="username" @input="updateUsername($event)"
             v-on:keyup.13="$refs.password.focus()"
      /><input type="password" placeholder="Enter Password" name="login_password" autocomplete="current-password"
               :value="password" @input="updatePassword($event)" ref="password"
               v-on:keyup.13="onEnter"
    />
      <button @click="onEnter" :disabled="!username||!password">Войти</button>
      <div class="error">
        {{loginError}}
      </div>
    </div>
    <div v-if="personDisplay">
      {{personDisplay.fio}}
      <span v-if="personDisplay.role">({{personDisplay.role}})</span>
      <a href="#" class="exit" @click="onExit">Выйти</a>
    </div>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import {PersonDisplay} from "../model/PersonDisplay";
  import login from '../store/login';

  @Component
  export default class VueLogin extends Vue {

    get username(): string {
      return login.getPassword()
    }

    updateUsername($event: any) {
      login.commitUsername($event.target.value);
    }

    get password(): string {
      return login.getPassword();
    }

    updatePassword($event: any) {
      login.commitPassword($event.target.value);
    }

    get loginError(): string | null {
      return login.getLoginError();
    }

    get personDisplay(): PersonDisplay | null {
      return login.getDisplay();
    }

    async onEnter() {
      await login.dispatchLogin();
    }

    isWaiting() {
      return login.isLoading();
    }

    isLogin() {
      return login.isLogin();
    }

    // noinspection JSUnusedGlobalSymbols
    async created() {
      await login.dispatchReset();
    }

    async onExit() {
      await login.dispatchExit();
    }
  }
</script>

<style scoped lang="scss">
  button {
    background: #4B99AD;
    padding: 8px 15px 8px 15px;
    border: none;
    color: #fff;
  }

  button:hover {
    background: #4691A4;
    box-shadow: none;
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
    border: 1px solid #BEBEBE;
    padding: 7px;
    margin: 0;
    outline: none;
  }

  input:focus {
    box-shadow: 0 0 8px #88D5E9;
    border: 1px solid #88D5E9;
  }

  .login {
    display: inline-block;
    width: auto;
  }

  .error {
    color: red;
    text-align: left;
    font-size: smaller;
    display: inline-block;
    padding-left: .5rem;
  }

  .exit {
    font-size: smaller;
    padding: .3em;
    text-decoration: none;
    border: 1px solid #acacac;
    display: inline-block;
  }
</style>
