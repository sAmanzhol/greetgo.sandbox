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
      <a href="#" @click="onExit">Выйти</a>
    </div>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import axios from 'axios'
  import {LoginState} from "./LoginState";
  import {PersonDisplay} from "../model/PersonDisplay";

  @Component
  export default class VueLogin extends Vue {
    username: string = '';
    password: string = '';
    authError: string | null = null;
    personDisplay: PersonDisplay | null = null;

    state: LoginState = LoginState.WAITING;

    keyDown($event: any) {
      console.log($event);
    }

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
        this.refresh();
      }).catch(error => {
        this.authError = error.response.data;
        console.log(error.response);
      })
    }

    refresh() {
      this.state = LoginState.WAITING;
      this.personDisplay = null;

      axios.get('/auth/displayPerson').then(response => {
        this.personDisplay = PersonDisplay.create(response.data);
        this.state = LoginState.PERSON_DISPLAY;
      }).catch(() => {
        this.state = LoginState.LOGIN;
      })
    }

    isWaiting() {
      return this.state === LoginState.WAITING;
    }

    isLogin() {
      return this.state === LoginState.LOGIN;
    }

    created() {
      this.refresh();
    }

    onExit() {
      axios.get('/auth/exit').then(() => {
        this.state = LoginState.LOGIN;
        localStorage.setItem("token", null);
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

  .login {
    display: inline-block;
    border: 1px solid green;
    width: auto;
  }

  .error {
    border: 1px solid red;
    color: red;
    text-align: left;
    font-size: smaller;
  }
</style>
