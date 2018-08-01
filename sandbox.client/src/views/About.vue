<template>
  <div class="about">
    <h1>This is an about page</h1>
    <button @click="onClick()">Click me</button>
  </div>
</template>

<script lang="ts">
  import {Component, Vue} from 'vue-property-decorator';

  @Component({
    components: {},
  })
  export default class About extends Vue {
    onClick() {
      console.log("Clicked")
    }
  }
</script>
