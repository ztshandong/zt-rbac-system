<template>
  <view>
    <web-view :webview-styles="webviewStyles" :src="articleurl"></web-view>
  </view>
</template>

<script>
  export default {
    data() {
      return {
        articleurl: '',
        webviewStyles: {
          progress: {
            color: '#FF7200'
          }
        }
      };
    },
    onLoad(options) {
      this.articleurl = 'http://show.hengshan-tech.com/v2.0/';
    }
  }
</script>

<style>

</style>
