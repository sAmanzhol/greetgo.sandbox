<template>
  <div class="home">
    <img src="../assets/sandbox.svg" width="200px">
    <VueLogin/>
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
