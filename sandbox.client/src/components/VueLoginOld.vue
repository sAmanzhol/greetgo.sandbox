<template>
  <div class="login">
    <div v-if="isWaiting()">
      WAITING...
    </div>
    <div v-if="isLogin()">
      <input type="text" placeholder="Enter Username" name="login_username"
             :value="username" @input="updateUsername($event)"
             v-on:keyup.13="$refs.password.focus()"
      />
      :
      <input type="password" placeholder="Enter Password" name="login_password" autocomplete="current-password"
             :value="password" @input="updatePassword($event)" ref="password"
             v-on:keyup.13="onEnter()"
      />
      <button @click="onEnter" :disabled="!username||!password">Войти</button>
      <div v-if="authError" class="error">
        {{authError}}
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
  import axios from 'axios'
  import {LoginStatus} from "./LoginStatus";
  import {PersonDisplay} from "../model/PersonDisplay";

  @Component
  export default class VueLoginOld extends Vue {
    username: string = '';
    password: string = '';
    authError: string | null = null;
    personDisplay: PersonDisplay | null = null;

    state: LoginStatus = LoginStatus.LOADING;

    updateUsername($event: any) {
      this.username = $event.target.value
    }

    updatePassword($event: any) {
      this.password = $event.target.value
    }

    onEnter() {
      const params = new URLSearchParams();
      params.append('username', this.username);
      params.append('password', this.password);
      this.username = '';
      this.password = '';

      axios.post('/auth/login', params).then(response => {
        localStorage.setItem("token", response.data);
        this.authError = null;
        this.refresh();
      }).catch(error => {
        this.authError = error.response.data;
        console.log(error.response);
      })
    }

    refresh() {
      this.state = LoginStatus.LOADING;
      this.personDisplay = null;

      axios.get('/auth/displayPerson').then(response => {
        this.personDisplay = PersonDisplay.create(response.data);
        this.state = LoginStatus.AUTH_OK;
      }).catch(() => {
        this.state = LoginStatus.LOGIN;
      })
    }

    isWaiting() {
      return this.state === LoginStatus.LOADING;
    }

    isLogin() {
      return this.state === LoginStatus.LOGIN;
    }

    // noinspection JSUnusedGlobalSymbols
    created() {
      this.refresh();
    }

    onExit() {
      axios.get('/auth/exit').then(() => {
        this.state = LoginStatus.LOGIN;

        localStorage.setItem("token", "");

        this.personDisplay = null;
      }).catch(error => {
        console.log(error)
      })
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
    border: 1px solid #BEBEBE;
    padding: 7px;
    margin: 0;
    outline: none;
  }

  input:focus {
    -moz-box-shadow: 0 0 8px #88D5E9;
    -webkit-box-shadow: 0 0 8px #88D5E9;
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
  }

  .exit {
    font-size: smaller;
    padding: .3em;
    text-decoration: none;
    border: 1px solid #acacac;
    display: inline-block;
  }
</style>
