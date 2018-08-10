<template>
  <div class="home">
    <img src="../assets/sandbox.svg" width="200px">
    <div class="login-place">
    <VueLogin/>
    </div>
    <HelloWorld msg="Sandbox"/>
    <VueUserInfo/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import HelloWorld from '@/components/HelloWorld.vue';
  import VueUserInfo from '@/components/VueUserInfo.vue';
  import VueLogin from '@/components/VueLogin.vue';

  @Component({
    components: {
      HelloWorld, VueUserInfo, VueLogin,
    },
  })
  export default class Home extends Vue {

  }
</script>

<style lang="scss">
  .login-place {
    text-align: center;
  }
</style>