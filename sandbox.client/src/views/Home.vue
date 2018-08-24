<template>
  <div class="home">
///MODIFY replace sandbox {PROJECT_NAME}
    <img src="../assets/sandbox.svg" width="200px">
    <div>Home page</div>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';

  @Component({
    components: {},
  })
  export default class Home extends Vue {}
</script>

<style lang="scss">
</style>