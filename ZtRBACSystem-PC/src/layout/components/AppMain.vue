<template>
  <section class="app-main">
    <!-- <keep-alive>
      <router-view v-if="this.$route.meta.$keepAlive" />
    </keep-alive>
    <router-view v-if="!this.$route.meta.$keepAlive" /> -->
    <transition name="fade-transform" mode="out-in">
      <!-- <keep-alive :include="cachedViews" :exclude="noCache">
        <router-view :key="key" />
      </keep-alive> -->
      <keep-alive :include="cachedViews">
        <router-view :key="key" v-if="this.$route.meta.keepAlive" />
      </keep-alive>
    </transition>

    <transition name="fade-transform" mode="out-in">
      <router-view :key="key" v-if="!this.$route.meta.keepAlive" />
    </transition>

  </section>
</template>

<script>
  export default {
    name: 'AppMain',
    computed: {
      cachedViews() {
        // 页面缓存
        // console.log('页面缓存:' + this.$store.state.tagsView.cachedViews)
        return this.$store.state.tagsView.cachedViews
      },
      key() {
        // console.log('页面key:')
        // console.log(this.$route)
        return this.$route.path
      }
    }
  }
</script>

<style lang="scss" scoped>
  .app-main {
    /* 50= navbar  50  */
    min-height: calc(100vh - 50px);
    width: 100%;
    position: relative;
    overflow: hidden;
  }

  .fixed-header+.app-main {
    padding-top: 50px;
  }

  .hasTagsView {
    .app-main {
      /* 84 = navbar + tags-view = 50 + 34 */
      min-height: calc(100vh - 84px);
    }

    .fixed-header+.app-main {
      padding-top: 84px;
    }
  }
</style>

<style lang="scss">
  // fix css style bug in open el-dialog
  .el-popup-parent--hidden {
    .fixed-header {
      padding-right: 15px;
    }
  }
</style>
