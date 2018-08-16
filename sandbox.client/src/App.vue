<template>
  <div id="app">
    <div id="nav">
      <span>
        <router-link class="link" to="/">Home</router-link>
        |
      </span>
      <span v-if="viewUsers">
        <router-link class="link" to="/persons">Users</router-link>
        |
      </span>
      <span v-if="viewAbout">
        <router-link class="link" to="/about">About</router-link>
        |
      </span>
      <VueLogin/>
    </div>
    <router-view/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import VueLogin from '@/components/VueLogin.vue';
  import login from "@/store/login";

  @Component({
    components: {
      VueLogin,
    },
  })
  export default class App extends Vue {
    get viewAbout(): boolean {
      return login.isViewAbout();
    }

    get viewUsers(): boolean {
      return login.isViewUsers();
    }
  }
</script>


<style lang="scss">
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    text-align: center;
    color: #2c3e50;
  }

  #nav {
    padding: 30px;
    a.link {
      width: 5rem;
      display: inline-block;
      text-align: center;
      color: #627e92;
      &.router-link-exact-active {
        color: #0b1c13;
        font-weight: bold;
        text-decoration: none;
        cursor: default;
      }
    }
  }
</style>
