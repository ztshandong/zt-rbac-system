package com.zhangzhuorui.framework.rbacsystem.entity;

import com.zhangzhuorui.framework.rbacsystem.enums.ZtButtonCodeEnum;
import com.zhangzhuorui.framework.rbacsystem.enums.ZtComponentTypeEnum;
import com.zhangzhuorui.framework.rbacsystem.extenduse.ZtRbacBasicEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.LinkedList;
import java.util.List;

/**
 * @author :  张涛 zhangtao
 * @createDate :  2017-01-01
 * @description :
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version :  1.0
 */

/**
 * 组件信息表（模块，菜单，按钮）
 */
@ApiModel(value = "ZtComponentInfo")
public class ZtComponentInfo extends ZtRbacBasicEntity<Long> implements Comparable<ZtComponentInfo> {
    /**
     * 菜单编号（唯一，不可修改）
     */
    @ApiModelProperty(value = "菜单编号（唯一，不可修改）")
    private String thisCode;

    /**
     * 上级菜单编号
     */
    @ApiModelProperty(value = "上级菜单编号")
    private String parentCode;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String thisName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer componentSort;

    /**
     * 路由地址
     */
    @ApiModelProperty(value = "路由地址")
    private String componentRoute;

    /**
     * 组件地址，和路由拼接使用
     */
    @ApiModelProperty(value = "组件地址，和路由拼接使用")
    private String componentPath;

    /**
     * 是否为外链（false否，true是）
     */
    @ApiModelProperty(value = "是否为外链（false否，true是）")
    private Boolean frameFlag;

    /**
     * 状态（true启用，false禁用）
     */
    @ApiModelProperty(value = "状态（true启用，false禁用）")
    private Boolean enableFlag;

    /**
     * 是否是叶子结点（true是，false否）
     */
    @ApiModelProperty(value = "是否是叶子结点（true是，false否）")
    private Boolean leafFlag;

    /**
     * 是否启用缓存（false不缓存，true缓存）
     */
    @ApiModelProperty(value = "是否启用缓存（false不缓存，true缓存）")
    private Boolean cacheFlag;

    /**
     * 菜单类型 模块/菜单/按钮
     */
    @ApiModelProperty(value = "菜单类型 模块/菜单/按钮")
    private ZtComponentTypeEnum menuType;

    /**
     * 操作类型，对应button_info的this_code
     */
    @ApiModelProperty(value = "操作类型，对应button_info的this_code")
    private ZtButtonCodeEnum buttonCode;

    /**
     * 图标URL
     */
    @ApiModelProperty(value = "图标URL")
    private String iconUrl;

    /**
     * 资源URL
     */
    @ApiModelProperty(value = "资源URL")
    private String resourceUrl;

    //---------------分割线以下的字段数据库不存在-----------------

    /**
     * 子组件
     */
    @ApiModelProperty(value = "子组件")
    private List<ZtComponentInfo> children = new LinkedList<>();

    public String getThisCode() {
        return thisCode;
    }

    public void setThisCode(String thisCode) {
        this.thisCode = thisCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getThisName() {
        return thisName;
    }

    public void setThisName(String thisName) {
        this.thisName = thisName;
    }

    public Integer getComponentSort() {
        return componentSort;
    }

    public void setComponentSort(Integer componentSort) {
        this.componentSort = componentSort;
    }

    public String getComponentRoute() {
        return componentRoute;
    }

    public void setComponentRoute(String componentRoute) {
        this.componentRoute = componentRoute;
    }

    public String getComponentPath() {
        return componentPath;
    }

    public void setComponentPath(String componentPath) {
        this.componentPath = componentPath;
    }

    public Boolean getFrameFlag() {
        return frameFlag;
    }

    public void setFrameFlag(Boolean frameFlag) {
        this.frameFlag = frameFlag;
    }

    public Boolean getEnableFlag() {
        return enableFlag;
    }

    public void setEnableFlag(Boolean enableFlag) {
        this.enableFlag = enableFlag;
    }

    public Boolean getLeafFlag() {
        return leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public Boolean getCacheFlag() {
        return cacheFlag;
    }

    public void setCacheFlag(Boolean cacheFlag) {
        this.cacheFlag = cacheFlag;
    }

    public ZtComponentTypeEnum getMenuType() {
        return menuType;
    }

    public void setMenuType(ZtComponentTypeEnum menuType) {
        this.menuType = menuType;
    }

    public ZtButtonCodeEnum getButtonCode() {
        return buttonCode;
    }

    public void setButtonCode(ZtButtonCodeEnum buttonCode) {
        this.buttonCode = buttonCode;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public List<ZtComponentInfo> getChildren() {
        return children;
    }

    public void setChildren(List<ZtComponentInfo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(this.getId());
        sb.append(", thisCode=").append(thisCode);
        sb.append(", parentCode=").append(parentCode);
        sb.append(", thisName=").append(thisName);
        sb.append(", componentSort=").append(componentSort);
        sb.append(", componentRoute=").append(componentRoute);
        sb.append(", componentPath=").append(componentPath);
        sb.append(", frameFlag=").append(frameFlag);
        sb.append(", enableFlag=").append(enableFlag);
        sb.append(", leafFlag=").append(leafFlag);
        sb.append(", cacheFlag=").append(cacheFlag);
        sb.append(", menuType=").append(menuType);
        sb.append(", buttonCode=").append(buttonCode);
        sb.append(", iconUrl=").append(iconUrl);
        sb.append(", resourceUrl=").append(resourceUrl);
        sb.append(", gmtCreate=").append(this.getGmtCreate());
        sb.append(", gmtUpdate=").append(this.getGmtUpdate());
        sb.append(", createdBy=").append(this.getCreatedBy());
        sb.append(", createdByName=").append(this.getCreatedByName());
        sb.append(", updatedBy=").append(this.getUpdatedBy());
        sb.append(", updatedByName=").append(this.getUpdatedByName());
        sb.append(", remark=").append(this.getRemark());
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ZtComponentInfo other = (ZtComponentInfo) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getThisCode() == null ? other.getThisCode() == null : this.getThisCode().equals(other.getThisCode()))
                && (this.getParentCode() == null ? other.getParentCode() == null : this.getParentCode().equals(other.getParentCode()))
                && (this.getThisName() == null ? other.getThisName() == null : this.getThisName().equals(other.getThisName()))
                && (this.getComponentSort() == null ? other.getComponentSort() == null : this.getComponentSort().equals(other.getComponentSort()))
                && (this.getComponentRoute() == null ? other.getComponentRoute() == null : this.getComponentRoute().equals(other.getComponentRoute()))
                && (this.getComponentPath() == null ? other.getComponentPath() == null : this.getComponentPath().equals(other.getComponentPath()))
                && (this.getFrameFlag() == null ? other.getFrameFlag() == null : this.getFrameFlag().equals(other.getFrameFlag()))
                && (this.getEnableFlag() == null ? other.getEnableFlag() == null : this.getEnableFlag().equals(other.getEnableFlag()))
                && (this.getLeafFlag() == null ? other.getLeafFlag() == null : this.getLeafFlag().equals(other.getLeafFlag()))
                && (this.getCacheFlag() == null ? other.getCacheFlag() == null : this.getCacheFlag().equals(other.getCacheFlag()))
                && (this.getMenuType() == null ? other.getMenuType() == null : this.getMenuType().equals(other.getMenuType()))
                && (this.getButtonCode() == null ? other.getButtonCode() == null : this.getButtonCode().equals(other.getButtonCode()))
                && (this.getIconUrl() == null ? other.getIconUrl() == null : this.getIconUrl().equals(other.getIconUrl()))
                && (this.getResourceUrl() == null ? other.getResourceUrl() == null : this.getResourceUrl().equals(other.getResourceUrl()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtUpdate() == null ? other.getGmtUpdate() == null : this.getGmtUpdate().equals(other.getGmtUpdate()))
                && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
                && (this.getCreatedByName() == null ? other.getCreatedByName() == null : this.getCreatedByName().equals(other.getCreatedByName()))
                && (this.getUpdatedBy() == null ? other.getUpdatedBy() == null : this.getUpdatedBy().equals(other.getUpdatedBy()))
                && (this.getUpdatedByName() == null ? other.getUpdatedByName() == null : this.getUpdatedByName().equals(other.getUpdatedByName()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getThisCode() == null) ? 0 : getThisCode().hashCode());
        result = prime * result + ((getParentCode() == null) ? 0 : getParentCode().hashCode());
        result = prime * result + ((getThisName() == null) ? 0 : getThisName().hashCode());
        result = prime * result + ((getComponentSort() == null) ? 0 : getComponentSort().hashCode());
        result = prime * result + ((getComponentRoute() == null) ? 0 : getComponentRoute().hashCode());
        result = prime * result + ((getComponentPath() == null) ? 0 : getComponentPath().hashCode());
        result = prime * result + ((getFrameFlag() == null) ? 0 : getFrameFlag().hashCode());
        result = prime * result + ((getEnableFlag() == null) ? 0 : getEnableFlag().hashCode());
        result = prime * result + ((getLeafFlag() == null) ? 0 : getLeafFlag().hashCode());
        result = prime * result + ((getCacheFlag() == null) ? 0 : getCacheFlag().hashCode());
        result = prime * result + ((getMenuType() == null) ? 0 : getMenuType().hashCode());
        result = prime * result + ((getButtonCode() == null) ? 0 : getButtonCode().hashCode());
        result = prime * result + ((getIconUrl() == null) ? 0 : getIconUrl().hashCode());
        result = prime * result + ((getResourceUrl() == null) ? 0 : getResourceUrl().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtUpdate() == null) ? 0 : getGmtUpdate().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getCreatedByName() == null) ? 0 : getCreatedByName().hashCode());
        result = prime * result + ((getUpdatedBy() == null) ? 0 : getUpdatedBy().hashCode());
        result = prime * result + ((getUpdatedByName() == null) ? 0 : getUpdatedByName().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public int compareTo(ZtComponentInfo t2) {
        if (!this.getMenuType().equals(t2.getMenuType())) {
            return this.getMenuType().getIntValue().compareTo(t2.getMenuType().getIntValue());
        } else {
            return this.getComponentSort().compareTo(t2.getComponentSort());
        }
    }
}