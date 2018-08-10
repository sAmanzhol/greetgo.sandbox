<template>
  <div id="app">
    <div id="nav">
      <router-link class="link" to="/">Home</router-link>
      |
      <router-link class="link" to="/users">Users</router-link>
      |
      <router-link class="link" to="/about">About</router-link>
      |
      <VueLogin/>
    </div>
    <router-view/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import VueLogin from '@/components/VueLogin.vue';

  @Component({
    components: {
      VueLogin,
    },
  })
  export default class App extends Vue {

  }
</script>


<style lang="scss">
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
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

      }
    }
  }
</style>
