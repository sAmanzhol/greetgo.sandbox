<template>
  <div class="login">
    <input type="text" placeholder="Enter Username" name="name"
           :value="username" @input="updateUsername($event)"
           v-on:keyup.13="$refs.password.focus()"
    />
    :
    <input type="password" placeholder="Enter Password" name="password"
           :value="password" @input="updatePassword($event)" ref="password"
           v-on:keyup.13="onEnter()"
    />
    <button class="button__enter" @click="onEnter" :disabled="!username||!password">Войти</button>
    <div v-if="authError" class="error">
      {{authError}}
    </div>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import axios from 'axios'

  @Component
  export default class VueLogin extends Vue {
    username: string = '';
    password: string = '';
    authError: string | null = null;

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
      axios.post('/auth/login', {
        username: this.username,
        password: this.password,
      }).then(response => {
        console.log(response)
      }).catch(error => {
        this.authError = error.response.data;
        console.log(error.response);
      })
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
