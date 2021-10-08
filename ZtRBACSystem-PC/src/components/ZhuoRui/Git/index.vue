<template>
  <div>
    <svg-icon icon-class="github" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'ZhuoRuiGit',
  data() {
    return {
      url: 'https://github.com/ztshandong/zt-rbac-system.git'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
