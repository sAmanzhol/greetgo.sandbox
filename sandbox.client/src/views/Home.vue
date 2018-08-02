<template>
  <div class="home">
    <img src="../assets/sandbox.svg" width="200px">
    <HelloWorld msg="Sandbox"/>
    <UserInfo/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import HelloWorld from '@/components/HelloWorld.vue';
  import UserInfo from '@/components/VueUserInfo.vue';

  @Component({
    components: {
      HelloWorld, UserInfo,
    },
  })
  export default class Home extends Vue {

  }
</script>
