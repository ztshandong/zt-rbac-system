package com.zhangzhuorui.framework.rbacsystem.entity;

public class ZtMetaVo {
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/assets/icons/svg
     */
    private String icon;

    /**
     * 设置为false，则不会被 <keep-alive>缓存
     */
    private Boolean cacheFlag;

    /**
     * 如果设置为false，则不会在breadcrumb面包屑中显示
     */
    private Boolean breadcrumb = true;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getCacheFlag() {
        return cacheFlag;
    }

    public void setCacheFlag(Boolean cacheFlag) {
        this.cacheFlag = cacheFlag;
    }

    public Boolean getBreadcrumb() {
        return breadcrumb;
    }

    public void setBreadcrumb(Boolean breadcrumb) {
        this.breadcrumb = breadcrumb;
    }
}
