<template>
  <div class="home">
    <img src="../assets/sandbox.svg" width="200px">
    <HelloWorld msg="Sandbox"/>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';
  import HelloWorld from '@/components/HelloWorld.vue'; // @ is an alias to /src

  @Component({
    components: {
      HelloWorld,
    },
  })
  export default class Home extends Vue {

  }
</script>
