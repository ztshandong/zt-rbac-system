/*
 Navicat Premium Data Transfer

 Source Server         : dockermysql-1
 Source Server Type    : MySQL
 Source Server Version : 50735
 Source Host           : localhost:3306
 Source Schema         : zt_user_center

 Target Server Type    : MySQL
 Target Server Version : 50735
 File Encoding         : 65001

 Date: 02/05/2022 12:53:39
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for zt_button_info
-- ----------------------------
DROP TABLE IF EXISTS `zt_button_info`;
CREATE TABLE `zt_button_info`
(
    `id` bigint
(
    20
) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `this_code` varchar
(
    64
) NOT NULL COMMENT '按钮编号（唯一，不可修改）',
    `this_name` varchar
(
    64
) NOT NULL COMMENT '按钮名称',
    `enable_flag` int
(
    11
) NOT NULL DEFAULT '1' COMMENT '按钮状态（true启用，false禁用）',
    `icon_url` varchar
(
    255
) NOT NULL DEFAULT '' COMMENT '图标URL',
    `gmt_create` datetime NOT NULL COMMENT '创建时间',
    `gmt_update` datetime NOT NULL COMMENT '更新时间',
    `created_by` varchar
(
    32
) NOT NULL COMMENT '创建人',
    `updated_by` varchar
(
    32
) NOT NULL COMMENT '更新人',
    `remark` varchar
(
    255
) NOT NULL DEFAULT '' COMMENT '备注',
    `created_by_name` varchar
(
    32
) NOT NULL COMMENT '创建人姓名',
    `updated_by_name` varchar
(
    32
) NOT NULL COMMENT '更新人姓名',
    PRIMARY KEY
(`id`
) USING BTREE,
    UNIQUE KEY `uk_dept_code`
(`this_code`
)
  USING BTREE,
    KEY `index_dept_and_parent_code`
(`this_code`
)
  USING BTREE
    ) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COMMENT='按钮信息表-code就是权限';

-- ----------------------------
-- Records of zt_button_info
-- ----------------------------
BEGIN
    ;
    INSERT INTO `zt_button_info`
    VALUES (1, 'QUERY', '查询', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (2, 'ADD', '新增', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (3, 'EDIT', '编辑', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (4, 'DEL', '删除', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (5, 'APP', '审核', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (6, 'PRINT', '打印', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (7, 'IMPORT', '导入', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (8, 'EXPORT', '导出', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (9, 'UNAPP', '反审', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (10, 'UPLOAD', '上传', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    INSERT INTO `zt_button_info`
    VALUES (11, 'DOWNLOAD', '下载', 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '超级管理员', '超级管理员');
    COMMIT;

    -- ----------------------------
-- Table structure for zt_column_info_conf
-- ----------------------------
    DROP TABLE IF EXISTS `zt_column_info_conf`;
    CREATE TABLE `zt_column_info_conf`
    (
        `id` int
    (
        11
    ) NOT NULL AUTO_INCREMENT COMMENT '主键',
        `vxe_table_name` varchar
    (
        32
    ) DEFAULT NULL COMMENT '表名',
        `vxe_field_name` varchar
    (
        32
    ) DEFAULT NULL COMMENT '列字段名',
        `column_type` int
    (
        11
    ) DEFAULT NULL COMMENT '列类型（seq序号，checkbox复选框，radio单选框，expand展开行，html标签）',
        `edit_render` text COMMENT '对象。\r\n可编辑渲染器配置项。\r\nname\r\n渲染器名称\r\nString\r\ninput, textarea, select, $input, $select, $switch\r\n　\r\nenabled\r\n是否启用\r\nboolean\r\n　\r\ntrue\r\nv2.10.6\r\nprops\r\n渲染的参数（请查看目标渲染的 Props）\r\nObject\r\n　\r\n　\r\nattrs\r\n渲染的属性（请查看目标渲染的 Attribute）\r\nObject\r\n　\r\n　\r\noptions\r\n只对 name=select 有效，下拉选项列表\r\nArray\r\n　\r\n　\r\noptionProps\r\n只对 name=select 有效，下拉选项属性参数配置\r\nObject\r\n　\r\n{ value, label }\r\noptionGroups\r\n只对 name=select 有效，下拉分组选项列表\r\nArray\r\n　\r\n　\r\noptionGroupProps\r\n只对 name=select 有效，下拉分组选项属性参数配置\r\nObject\r\n　\r\n{ options, label }\r\n\r\nevents\r\n渲染组件的事件（请查看目标渲染的 Events）\r\nObject\r\n　\r\n{row,rowIndex,$rowIndex,column,columnIndex,$columnIndex}, ...[目标渲染的 arguments]\r\ncontent\r\n渲染组件的内容（仅用于特殊组件）\r\nString\r\n　\r\n　\r\nautofocus\r\n如果是自定义渲染可以指定聚焦的选择器，例如 .my-input\r\nString\r\n　\r\n　\r\nautoselect\r\n是否在激活编辑之后自动选中输入框内容\r\nBoolean\r\n　\r\nfalse\r\ndefaultValue\r\n默认值（插入数据时列的默认值）\r\nAny\r\n　\r\nnull\r\nv2.3.3\r\nimmediate\r\n输入值实时同步更新（默认情况下单元格编辑的值只会在被触发时同步，如果需要实时同步可以设置为 true）\r\nBoolean\r\n　\r\nfalse\r\n',
        `title` varchar
    (
        32
    ) DEFAULT NULL COMMENT '列标题（支持国际化）',
        `width` varchar
    (
        8
    ) DEFAULT NULL COMMENT 'vxe-table列宽度（px，%）',
        `min_width` varchar
    (
        8
    ) DEFAULT NULL COMMENT 'vxe-table最小列宽度；会自动将剩余空间按比例分配（px，%）',
        `span` int
    (
        11
    ) DEFAULT NULL COMMENT 'vxe-form-item 弹框新增界面，栅格占据的列数（共 24 分栏）',
        `vxe_visible` int
    (
        11
    ) NOT NULL DEFAULT '1' COMMENT '列是否显示',
        `add_readonly` int
    (
        11
    ) NOT NULL DEFAULT '0' COMMENT '新增是否只读',
        `add_disabled` int
    (
        11
    ) NOT NULL DEFAULT '0' COMMENT '新增是否禁用',
        `edit_readonly` int
    (
        11
    ) NOT NULL DEFAULT '0' COMMENT '编辑是否只读',
        `edit_disabled` int
    (
        11
    ) NOT NULL COMMENT '编辑是否只读',
        `query_field` int
    (
        11
    ) NOT NULL DEFAULT '1' COMMENT '是否是查询条件',
        `folding` int
    (
        11
    ) NOT NULL DEFAULT '0' COMMENT '查询form是否折叠',
        `vxe_fixed` int
    (
        11
    ) DEFAULT NULL COMMENT '将列固定在左侧或者右侧（注意：固定列应该放在左右两侧的位置）left（固定左侧）, right（固定右侧）',
        `required` int
    (
        11
    ) NOT NULL DEFAULT '0' COMMENT '是否必填',
        `validator` varchar
    (
        255
    ) DEFAULT NULL COMMENT '校验的正则表达式',
        `valid_msg` varchar
    (
        32
    ) DEFAULT NULL COMMENT '校验不通过的提示',
        `align` int
    (
        11
    ) DEFAULT NULL COMMENT '列对齐方式。left（左对齐）, center（居中对齐）, right（右对齐）',
        `content_render` text COMMENT '对象。\r\n内容渲染配置项。\r\nname\r\n渲染器名称\r\nString\r\ninput, textarea, select, $input, $textarea, $select\r\n　\r\nprops\r\n渲染的参数（请查看目标渲染的 Props）\r\nObject\r\n　\r\n　\r\nattrs\r\n渲染的属性（请查看目标渲染的 Attribute）\r\nObject\r\n　\r\n　\r\noptions\r\n只对 name=select 有效，下拉选项列表\r\nArray\r\n　\r\n　\r\noptionProps\r\n只对 name=select 有效，下拉选项属性参数配置\r\nObject\r\n　\r\n{ value, label }\r\noptionGroups\r\n只对 name=select 有效，下拉分组选项列表\r\nArray\r\n　\r\n　\r\noptionGroupProps\r\n只对 name=select 有效，下拉分组选项属性参数配置\r\nObject\r\n　\r\n{ options, label }\r\nevents\r\n渲染组件的事件（请查看目标渲染的 Events）\r\nObject\r\n　\r\n{data, property}, ...[目标渲染的 arguments]',
        `cell_render` text COMMENT '对象。\r\n默认的渲染器配置项。\r\nname\r\n渲染器名称\r\nString\r\ninput, textarea, select, $input, $select, $button, $buttons, $switch\r\n　\r\nprops\r\n渲染的参数（请查看目标渲染的 Props）\r\nObject\r\n　\r\nattrs\r\n渲染的属性（请查看目标渲染的 Attribute）\r\nObject\r\n　\r\noptions\r\n只对 name=select 有效，下拉选项列表\r\nArray\r\n　\r\noptionProps\r\n只对 name=select 有效，下拉选项属性参数配置\r\nObject\r\n　\r\n{ value, label }\r\noptionGroups\r\n只对 name=select 有效，下拉分组选项列表\r\nArray\r\n　\r\noptionGroupProps\r\n只对 name=select 有效，下拉分组选项属性参数配置\r\nObject\r\n　\r\n{ options, label }\r\nevents\r\n渲染组件的事件（请查看目标渲染的 Events）\r\nObject\r\n　\r\n{row,rowIndex,$rowIndex,column,columnIndex,$columnIndex}, ...[目标渲染的 arguments]\r\ncontent\r\n渲染组件的内容（仅用于特殊组件）\r\nString',
        `resizable` int
    (
        11
    ) NOT NULL DEFAULT '1' COMMENT '列是否允许拖动列宽调整大小',
        `header_align` int
    (
        11
    ) DEFAULT NULL COMMENT '表头列的对齐方式。left（左对齐）, center（居中对齐）, right（右对齐）',
        `footer_align` int
    (
        11
    ) DEFAULT NULL COMMENT '表尾列的对齐方式。left（左对齐）, center（居中对齐）, right（右对齐）',
        `show_overflow` int
    (
        11
    ) DEFAULT NULL COMMENT '当内容过长时显示为省略号。ellipsis（只显示省略号）, title（并且显示为原生 title）, tooltip（并且显示为 tooltip 提示）',
        `show_header_overflow` int
    (
        11
    ) DEFAULT NULL COMMENT '当表头内容过长时显示为省略号。ellipsis（只显示省略号）, title（并且显示为原生 title）, tooltip（并且显示为 tooltip 提示）',
        `show_footer_overflow` int
    (
        11
    ) DEFAULT NULL COMMENT '当表尾内容过长时显示为省略号。ellipsis（只显示省略号）,title（并且显示为原生 title）,tooltip（并且显示为 tooltip 提示）',
        `class_name` varchar
    (
        32
    ) DEFAULT NULL COMMENT '给单元格附加 className',
        `header_class_name` varchar
    (
        32
    ) DEFAULT NULL COMMENT '给表头的单元格附加 className',
        `footer_class_name` varchar
    (
        32
    ) DEFAULT NULL COMMENT '给表尾的单元格附加 className',
        `formatter` varchar
    (
        32
    ) DEFAULT NULL COMMENT '格式化显示内容',
        `seq_method` varchar
    (
        255
    ) DEFAULT NULL COMMENT '只对 type=seq 有效，自定义索引方法',
        `sortable` int
    (
        11
    ) NOT NULL DEFAULT '0' COMMENT '是否允许列排序',
        `sort_by` varchar
    (
        255
    ) DEFAULT NULL COMMENT '只对 sortable 有效，自定义排序的属性',
        `filters` varchar
    (
        255
    ) DEFAULT NULL COMMENT '数组。\r\n配置筛选条件（注：筛选只能用于列表，如果是树结构则过滤根节点）。\r\n\r\nlabel\r\n显示的值\r\nString\r\n　\r\nvalue\r\n实际的值\r\nAny\r\n　\r\nchecked\r\n默认是否选中\r\nBoolean\r\n　\r\nfalse\r\nresetValue\r\n重置时的默认值\r\nAny\r\n　\r\ndata\r\n自定义渲染的数据值（当使用自定义模板时可能会用到）\r\nAny',
        `filter_multiple` int
    (
        11
    ) NOT NULL DEFAULT '1' COMMENT '只对 filters 有效，筛选是否允许多选',
        `filter_method` varchar
    (
        255
    ) DEFAULT NULL COMMENT '只对 filters 有效，列的筛选方法，该方法的返回值用来决定该行是否显示',
        `filter_render` text COMMENT '对象\r\n筛选渲染器配置项。\r\nname\r\n渲染器名称\r\nString\r\ninput, textarea, select, $input, $textarea, $select\r\n　\r\nprops\r\n渲染的参数（请查看目标渲染的 Props）\r\nObject\r\n　\r\nattrs\r\n渲染的属性（请查看目标渲染的 Attribute）\r\nObject\r\n　\r\nevents\r\n渲染组件的事件（请查看目标渲染的 Events）\r\nObject\r\n　\r\n{row,rowIndex,$rowIndex,column,columnIndex,$columnIndex}, ...[目标渲染的 arguments]\r\n\r\ncontent\r\n渲染组件的内容（仅用于特殊组件）\r\nString',
        `export_method` varchar
    (
        255
    ) DEFAULT NULL COMMENT '自定义单元格数据导出方法，该方法 Function({ row, column }) 的返回值将会被导出',
        `footer_export_method` varchar
    (
        255
    ) DEFAULT NULL COMMENT '自定义表尾单元格数据导出方法，该方法 Function({ items, _columnIndex }) 的返回值将会被导出',
        `title_help` varchar
    (
        255
    ) DEFAULT NULL COMMENT '对象\r\n标题帮助图标配置项\r\nmessage\r\n提示消息（支持开启国际化）\r\nstring\r\n　\r\nicon\r\n自定义图标\r\nstring',
        `cell_type` int
    (
        11
    ) NOT NULL DEFAULT '1' COMMENT '只对特定功能有效，单元格值类型（例如：导出数据类型设置）\r\nauto（默认自动转换），number（数值）, string（字符串）',
        `tree_node` int
    (
        11
    ) NOT NULL DEFAULT '0' COMMENT '只对 tree-config 配置时有效，指定为树节点',
        `params` text COMMENT '额外的参数（可以用来存放一些私有参数）',
        `children` text COMMENT '表头分组  Array<ColumnConfig>',
        `link` text COMMENT '超链接',
        `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
        `gmt_update` datetime DEFAULT NULL COMMENT '修改时间',
        `created_by` varchar
    (
        32
    ) DEFAULT NULL COMMENT '创建人',
        `updated_by` varchar
    (
        32
    ) DEFAULT NULL COMMENT '修改人',
        `remark` varchar
    (
        255
    ) DEFAULT NULL COMMENT '备注',
        `created_by_name` varchar
    (
        32
    ) DEFAULT NULL COMMENT '创建人姓名',
        `updated_by_name` varchar
    (
        32
    ) DEFAULT NULL COMMENT '更新人姓名',
        PRIMARY KEY
    (`id`
    ) USING BTREE
        ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='vxe-grid动态列配置表';

    -- ----------------------------
-- Records of zt_column_info_conf
-- ----------------------------
    BEGIN
        ;
        INSERT INTO `zt_column_info_conf`
        VALUES (1, 'ztTest', 'id', 2, '{name:\' $ input\'}', 'id', '40', NULL, NULL, 1, 1, 1, 1, 1, 0, 0, 1, 0, NULL, NULL, 2, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, 1, 0, NULL, NULL, NULL, '2021-01-15 09:28:00', '2021-01-15 09:28:00', 'admin', 'admin', NULL, NULL, NULL);
        INSERT INTO `zt_column_info_conf`
        VALUES (2, 'ztTest', 'ztEnum2', NULL, '{name:\' $ select\',props:{placeholder:\' 选择\'}}', '枚举', NULL, NULL, NULL, 1, 0, 0, 0, 1, 1, 0, 1, 1, NULL, '格式错误', NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 1, NULL, NULL, NULL, NULL, '{\"icon\":\"\",\"message\":\"枚举\"}', 1, 0, NULL, NULL, NULL, '2021-01-15 09:28:00', '2021-01-15 09:28:00', 'admin', 'admin', NULL, NULL, NULL);
        INSERT INTO `zt_column_info_conf`
        VALUES (3, 'ztTest', 'remark', NULL, '{name:\' $ input\',props:{placeholder:\' 请输入备注\'}}', '备注', '50%', NULL, NULL, 1, 0, 1, 0, 0, 1, 1, 1, 1, NULL, '格式错误', NULL, NULL, NULL, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 1, NULL, NULL, NULL, NULL, '{\"icon\":\"\",\"message\":\"备注\"}', 1, 0, NULL, NULL, NULL, '2021-01-15 09:28:00', '2021-01-15 09:28:00', 'admin', 'admin', NULL, NULL, NULL);
        COMMIT;

        -- ----------------------------
-- Table structure for zt_component_info
-- ----------------------------
        DROP TABLE IF EXISTS `zt_component_info`;
        CREATE TABLE `zt_component_info`
        (
            `id` bigint
        (
            20
        ) NOT NULL AUTO_INCREMENT COMMENT '主键',
            `this_code` varchar
        (
            64
        ) NOT NULL COMMENT '菜单编号（唯一，不可修改）',
            `parent_code` varchar
        (
            64
        ) NOT NULL DEFAULT '' COMMENT '上级菜单编号',
            `this_name` varchar
        (
            64
        ) NOT NULL COMMENT '菜单名称',
            `component_sort` int
        (
            11
        ) NOT NULL DEFAULT '1' COMMENT '排序',
            `component_route` varchar
        (
            255
        ) NOT NULL DEFAULT '' COMMENT '路由地址',
            `component_path` varchar
        (
            255
        ) NOT NULL DEFAULT '' COMMENT '组件地址，和路由拼接使用',
            `frame_flag` int
        (
            11
        ) NOT NULL DEFAULT '0' COMMENT '是否为外链（false否，true是）',
            `enable_flag` int
        (
            11
        ) NOT NULL DEFAULT '1' COMMENT '状态（true启用，false禁用）',
            `leaf_flag` int
        (
            11
        ) NOT NULL DEFAULT '1' COMMENT '是否是叶子结点（true是，false否）',
            `cache_flag` int
        (
            11
        ) NOT NULL DEFAULT '1' COMMENT '是否启用缓存（false不缓存，true缓存）',
            `menu_type` varchar
        (
            32
        ) NOT NULL DEFAULT 'MODEL' COMMENT '菜单类型 模块/菜单/按钮',
            `button_code` varchar
        (
            64
        ) DEFAULT '' COMMENT '操作类型，对应button_info的this_code',
            `icon_url` varchar
        (
            255
        ) NOT NULL DEFAULT '' COMMENT '图标URL',
            `resource_url` varchar
        (
            255
        ) NOT NULL DEFAULT '' COMMENT '资源URL',
            `gmt_create` datetime NOT NULL COMMENT '创建时间',
            `gmt_update` datetime NOT NULL COMMENT '更新时间',
            `created_by` varchar
        (
            32
        ) NOT NULL COMMENT '创建人',
            `created_by_name` varchar
        (
            32
        ) NOT NULL COMMENT '创建人姓名',
            `updated_by` varchar
        (
            32
        ) NOT NULL COMMENT '更新人',
            `updated_by_name` varchar
        (
            32
        ) NOT NULL COMMENT '更新人姓名',
            `remark` varchar
        (
            255
        ) NOT NULL DEFAULT '' COMMENT '备注',
            PRIMARY KEY
        (`id`
        ) USING BTREE,
            UNIQUE KEY `uk_menu_code`
        (`this_code`
        )
          USING BTREE,
            KEY `index_menu_and_parent_code`
        (
            `
            this_code
            `,
            `
            parent_code
            `
        )
          USING BTREE
            ) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COMMENT='组件信息表（模块，菜单，按钮）';

        -- ----------------------------
-- Records of zt_component_info
-- ----------------------------
        BEGIN
            ;
            INSERT INTO `zt_component_info`
            VALUES (1, 'CONFIG_MANAGE', 'ROOT', '配置管理', 0, '/config', 'ParentView', 0, 1, 0, 1, 'MODEL', NULL, 'shezhi1', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (2, 'SYSTEM_MANAGE', 'CONFIG_MANAGE', '系统管理', 0, '/system', 'Layout', 0, 1, 0, 1, 'MENU', NULL, 'weixiu', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (3, 'ROLE_MANAGE', 'SYSTEM_MANAGE', '角色管理', 1, '/role', 'system/role/index', 0, 1, 0, 1, 'MENU', NULL, 'draw', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (4, 'COMPONENT_MANAGE', 'SYSTEM_MANAGE', '组件管理', 2, '/menu', 'system/menu/index', 0, 1, 0, 1, 'MENU', NULL, 'excel', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (5, 'DEPT_MANAGE', 'SYSTEM_MANAGE', '部门管理', 3, '/dept', 'system/dept/index', 0, 1, 0, 1, 'MENU', NULL, 'chengshi', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (6, 'USER_MANAGE', 'SYSTEM_MANAGE', '用户管理', 4, '/user', 'system/user/index', 0, 1, 0, 1, 'MENU', NULL, 'tongxunlu', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (7, 'POST_MANAGE', 'SYSTEM_MANAGE', '职位管理', 5, '/post', 'system/post/index', 0, 1, 0, 1, 'MENU', NULL, 'word2', '', '2021-01-03 18:24:54', '2021-10-17 21:15:31', 'admin', '', 'admin', '', 'stringasdf');
            INSERT INTO `zt_component_info`
            VALUES (8, 'ROLE_MANAGE:QUERY', 'ROLE_MANAGE', '角色查询', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'QUERY', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (9, 'ROLE_MANAGE:ADD', 'ROLE_MANAGE', '角色新增', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ADD', '', '', '2021-01-03 18:24:54', '2021-10-17 21:37:48', 'admin', '', 'admin', '', 'string222');
            INSERT INTO `zt_component_info`
            VALUES (10, 'ROLE_MANAGE:EDIT', 'ROLE_MANAGE', '角色编辑', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EDIT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (11, 'ROLE_MANAGE:DEL', 'ROLE_MANAGE', '角色删除', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DEL', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (12, 'ROLE_MANAGE:APP', 'ROLE_MANAGE', '角色审核', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'APP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (13, 'ROLE_MANAGE:UNAPP', 'ROLE_MANAGE', '角色反审', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UNAPP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (14, 'ROLE_MANAGE:UPLOAD', 'ROLE_MANAGE', '角色上传', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UPLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (15, 'ROLE_MANAGE:DOWNLOAD', 'ROLE_MANAGE', '角色下载', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DOWNLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (16, 'ROLE_MANAGE:IMPORT', 'ROLE_MANAGE', '角色导入', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'IMPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (17, 'ROLE_MANAGE:EXPORT', 'ROLE_MANAGE', '角色导出', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EXPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (18, 'ROLE_MANAGE:PRINT', 'ROLE_MANAGE', '角色打印', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'PRINT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (19, 'ROLE_MANAGE:ENABLE', 'ROLE_MANAGE', '角色启用', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ENABLE', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (20, 'COMPONENT_MANAGE:QUERY', 'COMPONENT_MANAGE', '组件查询', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'QUERY', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (21, 'COMPONENT_MANAGE:ADD', 'COMPONENT_MANAGE', '组件新增', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ADD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (22, 'COMPONENT_MANAGE:EDIT', 'COMPONENT_MANAGE', '组件编辑', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EDIT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (23, 'COMPONENT_MANAGE:DEL', 'COMPONENT_MANAGE', '组件删除', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DEL', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (24, 'COMPONENT_MANAGE:APP', 'COMPONENT_MANAGE', '组件审核', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'APP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (25, 'COMPONENT_MANAGE:UNAPP', 'COMPONENT_MANAGE', '组件反审', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UNAPP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (26, 'COMPONENT_MANAGE:UPLOAD', 'COMPONENT_MANAGE', '组件上传', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UPLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (27, 'COMPONENT_MANAGE:DOWNLOAD', 'COMPONENT_MANAGE', '组件下载', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DOWNLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (28, 'COMPONENT_MANAGE:IMPORT', 'COMPONENT_MANAGE', '组件导入', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'IMPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (29, 'COMPONENT_MANAGE:EXPORT', 'COMPONENT_MANAGE', '组件导出', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EXPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (30, 'COMPONENT_MANAGE:PRINT', 'COMPONENT_MANAGE', '组件打印', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'PRINT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (31, 'COMPONENT_MANAGE:ENABLE', 'COMPONENT_MANAGE', '组件启用', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ENABLE', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (32, 'DEPT_MANAGE:QUERY', 'DEPT_MANAGE', '部门查询', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'QUERY', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (33, 'DEPT_MANAGE:ADD', 'DEPT_MANAGE', '部门新增', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ADD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (34, 'DEPT_MANAGE:EDIT', 'DEPT_MANAGE', '部门编辑', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EDIT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (35, 'DEPT_MANAGE:DEL', 'DEPT_MANAGE', '部门删除', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DEL', '', '', '2021-01-03 18:24:54', '2021-10-17 21:23:12', 'admin', '', 'admin', '', 'string222');
            INSERT INTO `zt_component_info`
            VALUES (36, 'DEPT_MANAGE:APP', 'DEPT_MANAGE', '部门审核', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'APP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (37, 'DEPT_MANAGE:UNAPP', 'DEPT_MANAGE', '部门反审', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UNAPP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (38, 'DEPT_MANAGE:UPLOAD', 'DEPT_MANAGE', '部门上传', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UPLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (39, 'DEPT_MANAGE:DOWNLOAD', 'DEPT_MANAGE', '部门下载', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DOWNLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (40, 'DEPT_MANAGE:IMPORT', 'DEPT_MANAGE', '部门导入', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'IMPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (41, 'DEPT_MANAGE:EXPORT', 'DEPT_MANAGE', '部门导出', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EXPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (42, 'DEPT_MANAGE:PRINT', 'DEPT_MANAGE', '部门打印', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'PRINT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (43, 'DEPT_MANAGE:ENABLE', 'DEPT_MANAGE', '部门启用', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ENABLE', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (44, 'USER_MANAGE:QUERY', 'USER_MANAGE', '用户查询', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'QUERY', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (45, 'USER_MANAGE:ADD', 'USER_MANAGE', '用户新增', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ADD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (46, 'USER_MANAGE:EDIT', 'USER_MANAGE', '用户编辑', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EDIT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (47, 'USER_MANAGE:DEL', 'USER_MANAGE', '用户删除', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DEL', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (48, 'USER_MANAGE:APP', 'USER_MANAGE', '用户审核', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'APP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (49, 'USER_MANAGE:UNAPP', 'USER_MANAGE', '用户反审', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UNAPP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (50, 'USER_MANAGE:UPLOAD', 'USER_MANAGE', '用户上传', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UPLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (51, 'USER_MANAGE:DOWNLOAD', 'USER_MANAGE', '用户下载', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DOWNLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (52, 'USER_MANAGE:IMPORT', 'USER_MANAGE', '用户导入', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'IMPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (53, 'USER_MANAGE:EXPORT', 'USER_MANAGE', '用户导出', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EXPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (54, 'USER_MANAGE:PRINT', 'USER_MANAGE', '用户打印', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'PRINT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (55, 'USER_MANAGE:ENABLE', 'USER_MANAGE', '用户启用', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ENABLE', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (56, 'POST_MANAGE:QUERY', 'POST_MANAGE', '职位查询', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'QUERY', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (57, 'POST_MANAGE:ADD', 'POST_MANAGE', '职位新增', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ADD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (58, 'POST_MANAGE:EDIT', 'POST_MANAGE', '职位编辑', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EDIT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (59, 'POST_MANAGE:DEL', 'POST_MANAGE', '职位删除', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DEL', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (60, 'POST_MANAGE:APP', 'POST_MANAGE', '职位审核', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'APP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (61, 'POST_MANAGE:UNAPP', 'POST_MANAGE', '职位反审', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UNAPP', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (62, 'POST_MANAGE:UPLOAD', 'POST_MANAGE', '职位上传', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'UPLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (63, 'POST_MANAGE:DOWNLOAD', 'POST_MANAGE', '职位下载', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'DOWNLOAD', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (64, 'POST_MANAGE:IMPORT', 'POST_MANAGE', '职位导入', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'IMPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (65, 'POST_MANAGE:EXPORT', 'POST_MANAGE', '职位导出', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'EXPORT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (66, 'POST_MANAGE:PRINT', 'POST_MANAGE', '职位打印', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'PRINT', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            INSERT INTO `zt_component_info`
            VALUES (67, 'POST_MANAGE:ENABLE', 'POST_MANAGE', '职位启用', 0, '', '', 0, 1, 1, 1, 'BUTTON', 'ENABLE', '', '', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', '', 'admin', '', 'string');
            COMMIT;

            -- ----------------------------
-- Table structure for zt_dept_info
-- ----------------------------
            DROP TABLE IF EXISTS `zt_dept_info`;
            CREATE TABLE `zt_dept_info`
            (
                `id` bigint
            (
                20
            ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                `this_code` varchar
            (
                64
            ) NOT NULL COMMENT '部门编号（唯一，不可修改）',
                `parent_code` varchar
            (
                64
            ) NOT NULL DEFAULT '' COMMENT '上级部门编号（同公司）',
                `root_code` varchar
            (
                64
            ) NOT NULL DEFAULT '' COMMENT '上级部门编号（集团化。例如集团信息部可以管控所有子公司信息部）',
                `this_name` varchar
            (
                64
            ) NOT NULL COMMENT '部门名称',
                `enable_flag` int
            (
                11
            ) NOT NULL DEFAULT '1' COMMENT '部门状态（true启用，false禁用）',
                `dept_level` int
            (
                11
            ) NOT NULL DEFAULT '1000' COMMENT '部门层级',
                `dept_leader_code` varchar
            (
                255
            ) NOT NULL COMMENT '部门负责人编号',
                `gmt_create` datetime NOT NULL COMMENT '创建时间',
                `gmt_update` datetime NOT NULL COMMENT '更新时间',
                `created_by` varchar
            (
                32
            ) NOT NULL COMMENT '创建人',
                `updated_by` varchar
            (
                32
            ) NOT NULL COMMENT '更新人',
                `remark` varchar
            (
                255
            ) NOT NULL DEFAULT '' COMMENT '备注',
                `created_by_name` varchar
            (
                32
            ) NOT NULL COMMENT '创建人姓名',
                `updated_by_name` varchar
            (
                32
            ) NOT NULL COMMENT '更新人姓名',
                PRIMARY KEY
            (`id`
            ) USING BTREE,
                UNIQUE KEY `uk_dept_code`
            (`this_code`
            )
              USING BTREE,
                KEY `index_dept_and_parent_code`
            (
                `
                this_code
                `,
                `
                parent_code
                `
            )
              USING BTREE
                ) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COMMENT='部门信息表';

            -- ----------------------------
-- Records of zt_dept_info
-- ----------------------------
            BEGIN
                ;
                INSERT INTO `zt_dept_info`
                VALUES (1, 'allroot', '', '', '锦睿集团总部', 1, 1, '', '2021-01-03 13:47:32', '2021-01-03 13:47:32', 'admin', 'admin', 'string', '', '');
                INSERT INTO `zt_dept_info`
                VALUES (2, 'rootdevelop', 'allroot', '', '锦睿集团开发部', 1, 2, '', '2021-01-03 13:47:32', '2021-10-14 12:03:21', 'admin', 'admin', 'string234sdfaaa', '', '');
                INSERT INTO `zt_dept_info`
                VALUES (3, 'shanghairoot', 'allroot', '', '上海分公司总部', 1, 2, '', '2021-01-03 13:47:32', '2021-10-16 10:21:42', 'admin', 'admin', 'stringsdasd', '', '');
                INSERT INTO `zt_dept_info`
                VALUES (4, 'beijingroot', 'allroot', '', '北京分公司总部', 1, 2, '', '2021-01-03 13:47:32', '2021-10-15 17:29:46', 'admin', 'admin', 'stringxxx', '', '');
                INSERT INTO `zt_dept_info`
                VALUES (5, 'shanghaihr', 'shanghairoot', '', '上海分公司人力资源部', 1, 3, '', '2021-01-03 13:47:32', '2021-10-14 17:27:43', 'admin', 'admin', 'stringshassdsdd史蒂', '', '');
                INSERT INTO `zt_dept_info`
                VALUES (6, 'shanghaidevelop', 'shanghairoot', '', '上海分公司开发部', 1, 3, '', '2021-01-03 13:47:32', '2021-10-17 21:16:46', 'admin', 'admin', 'stringsadfsafds', '', '');
                COMMIT;

                -- ----------------------------
-- Table structure for zt_exclude_info
-- ----------------------------
                DROP TABLE IF EXISTS `zt_exclude_info`;
                CREATE TABLE `zt_exclude_info`
                (
                    `id` bigint
                (
                    20
                ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                    `user_code` varchar
                (
                    64
                ) NOT NULL COMMENT '用户编号',
                    `exclude_code` varchar
                (
                    64
                ) NOT NULL COMMENT '排除的权限编号',
                    `exclude_type` varchar
                (
                    32
                ) NOT NULL COMMENT '排除的权限类型（部门，职位，角色，菜单）',
                    `gmt_create` datetime NOT NULL COMMENT '创建时间',
                    `gmt_update` datetime NOT NULL COMMENT '更新时间',
                    `created_by` varchar
                (
                    32
                ) NOT NULL COMMENT '创建人',
                    `updated_by` varchar
                (
                    32
                ) NOT NULL COMMENT '更新人',
                    `remark` varchar
                (
                    32
                ) NOT NULL DEFAULT '' COMMENT '备注',
                    `created_by_name` varchar
                (
                    32
                ) NOT NULL COMMENT '创建人姓名',
                    `updated_by_name` varchar
                (
                    32
                ) NOT NULL COMMENT '更新人姓名',
                    PRIMARY KEY
                (`id`
                ) USING BTREE,
                    UNIQUE KEY `uk_user_dept_code`
                (
                    `
                    user_code
                    `,
                    `
                    exclude_code
                    `
                )
                  USING BTREE
                    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='排除的权限-只针对到用户';

                -- ----------------------------
-- Records of zt_exclude_info
-- ----------------------------
                BEGIN
                    ;
                    COMMIT;

                    -- ----------------------------
-- Table structure for zt_frame_use1
-- ----------------------------
                    DROP TABLE IF EXISTS `zt_frame_use1`;
                    CREATE TABLE `zt_frame_use1`
                    (
                        `id` bigint
                    (
                        20
                    ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `udf_1` varchar
                    (
                        2000
                    ) CHARACTER SET utf8 DEFAULT '[]' COMMENT '字段1',
                        `udf_2` bigint
                    (
                        20
                    ) DEFAULT NULL COMMENT '字段2',
                        `gmt_create` datetime NOT NULL COMMENT '创建时间',
                        `gmt_update` datetime NOT NULL COMMENT '更新时间',
                        `created_by` varchar
                    (
                        32
                    ) DEFAULT NULL COMMENT '创建人',
                        `updated_by` varchar
                    (
                        32
                    ) DEFAULT NULL COMMENT '更新人',
                        `remark` varchar
                    (
                        255
                    ) DEFAULT '' COMMENT '备注',
                        `created_by_name` varchar
                    (
                        32
                    ) DEFAULT NULL COMMENT '创建人姓名',
                        `updated_by_name` varchar
                    (
                        32
                    ) DEFAULT NULL COMMENT '更新人姓名',
                        `enable_flag` int
                    (
                        20
                    ) DEFAULT '1' COMMENT '启用',
                        `del_flag` int
                    (
                        20
                    ) DEFAULT '0' COMMENT '删除',
                        `enum_1` varchar
                    (
                        32
                    ) DEFAULT NULL COMMENT '枚举',
                        `dept_code` varchar
                    (
                        255
                    ) DEFAULT NULL,
                        PRIMARY KEY
                    (`id`
                    ) USING BTREE
                        ) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COMMENT='zt通用框架demo';

                    -- ----------------------------
-- Records of zt_frame_use1
-- ----------------------------
                    BEGIN
                        ;
                        INSERT INTO `zt_frame_use1`
                        VALUES (3, 'string', 0, '2021-09-27 00:21:25', '2021-09-28 11:49:32', 'string', 'string', 'string', 'string', 'string', 0, 0, 'ONE1', NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (4, 'string', 0, '2021-09-27 00:21:25', '2021-09-27 00:21:25', 'string', 'string', 'string', 'string', 'string', 0, 0, 'ONE1', NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (5, 'string', 0, '2021-09-27 00:23:13', '2021-09-27 00:23:13', 'string', 'string', 'string', 'string', 'string', 0, 0, 'ONE1', NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (6, 'string', 0, '2021-09-27 00:23:13', '2021-09-27 00:23:13', 'string', 'string', 'string', 'string', 'string', 0, 0, 'ONE1', NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (7, 'string', 0, '2021-09-27 00:27:05', '2021-09-27 00:27:05', 'string', 'string', 'string', 'string', 'string', 0, 0, 'ONE1', NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (8, 'string', 0, '2021-09-27 00:27:05', '2021-09-27 00:27:05', 'string', 'string', 'string', 'string', 'string', 0, 0, 'ONE1', NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (9, 'ok', 1, '2021-09-27 17:23:12', '2021-09-27 17:23:12', NULL, NULL, 'hello world', NULL, NULL, 0, 1, 'THREE1', NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (10, 'ok', 1, '2021-09-27 17:58:38', '2021-09-27 17:58:38', NULL, NULL, 'hello world', NULL, NULL, 0, 1, 'THREE1', NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (11, 'ok', 1, '2021-09-27 17:58:38', '2021-09-27 17:58:38', NULL, NULL, 'hello world', NULL, NULL, 0, 1, 'THREE1', NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (12, 'string', 0, '2021-09-28 08:57:45', '2021-09-28 08:57:45', NULL, 'string', '', NULL, 'string', NULL, NULL, NULL, NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (13, 'string', 0, '2021-09-28 08:57:45', '2021-09-28 08:57:45', NULL, 'string', '', NULL, 'string', NULL, NULL, NULL, NULL);
                        INSERT INTO `zt_frame_use1`
                        VALUES (14, 'string', 0, '2021-09-28 08:57:45', '2021-09-28 08:57:45', NULL, 'string', '', NULL, 'string', NULL, NULL, NULL, NULL);
                        COMMIT;

                        -- ----------------------------
-- Table structure for zt_frame_use2
-- ----------------------------
                        DROP TABLE IF EXISTS `zt_frame_use2`;
                        CREATE TABLE `zt_frame_use2`
                        (
                            `id` int
                        (
                            20
                        ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                            `udf_1` varchar
                        (
                            2000
                        ) CHARACTER SET utf8 DEFAULT '[]' COMMENT '字段1',
                            `gmt_create` datetime NOT NULL COMMENT '创建时间',
                            `gmt_update` datetime NOT NULL COMMENT '更新时间',
                            `created_by` varchar
                        (
                            32
                        ) DEFAULT NULL COMMENT '创建人',
                            `updated_by` varchar
                        (
                            32
                        ) DEFAULT NULL COMMENT '更新人',
                            `remark` varchar
                        (
                            255
                        ) DEFAULT '' COMMENT '备注',
                            `created_by_name` varchar
                        (
                            32
                        ) DEFAULT NULL COMMENT '创建人姓名',
                            `updated_by_name` varchar
                        (
                            32
                        ) DEFAULT NULL COMMENT '更新人姓名',
                            `user_code` varchar
                        (
                            255
                        ) DEFAULT NULL,
                            PRIMARY KEY
                        (`id`
                        ) USING BTREE,
                            KEY `idx_remark`
                        (`remark`
                        )
                          USING BTREE
                            ) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COMMENT='zt通用框架demo';

                        -- ----------------------------
-- Records of zt_frame_use2
-- ----------------------------
                        BEGIN
                            ;
                            INSERT INTO `zt_frame_use2`
                            VALUES (1, 'string', '2021-08-20 10:44:57', '2021-08-20 10:44:57', 'string2', 'string', 'string', 'string2', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (3, 'string', '2021-09-02 09:36:55', '2021-09-02 09:36:55', 'string', 'string', 'string2', 'string', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (5, 'string', '2021-09-02 09:36:55', '2021-09-02 09:36:55', 'string2', 'string', 'string4', 'string2', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (7, 'string', '2021-09-02 09:36:55', '2021-09-02 09:36:55', 'string', 'string', 'string6', 'string', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (9, 'string2', '2021-08-20 10:44:57', '2021-08-20 10:44:57', 'string2', 'string', 'string', 'string2', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (11, 'string', '2021-09-02 09:36:55', '2021-09-02 09:36:55', 'string', 'string', 'string2', 'string', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (13, 'string', '2021-09-02 09:36:55', '2021-09-02 09:36:55', 'string', 'string', 'string4', 'string', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (15, 'string2', '2021-09-02 09:36:55', '2021-09-02 09:36:55', 'string', 'string', 'string6', 'string', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (16, 'string', '2021-09-27 14:04:09', '2021-09-27 14:04:09', 'string', 'string', '', 'string', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (17, 'string', '2021-09-27 14:04:51', '2021-09-27 14:04:51', 'string', 'string', '', 'string', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (18, 'string', '2021-09-27 14:04:51', '2021-09-27 14:04:51', 'string', 'string', '', 'string', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (19, 'string', '2021-09-27 14:04:51', '2021-09-27 14:04:51', 'string', 'string', '', 'string', 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (20, 'string', '2021-09-28 00:00:03', '2021-09-28 00:00:03', NULL, 'string', 'string', NULL, 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (21, 'string', '2021-09-28 00:00:38', '2021-09-28 00:00:38', NULL, 'string', 'string', NULL, 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (22, 'string', '2021-09-28 00:00:38', '2021-09-28 00:00:38', NULL, 'string', 'string', NULL, 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (23, 'string', '2021-09-28 00:02:40', '2021-09-28 00:02:40', NULL, 'string', 'string', NULL, 'string', NULL);
                            INSERT INTO `zt_frame_use2`
                            VALUES (24, 'string', '2021-09-28 00:02:40', '2021-09-28 00:02:40', NULL, 'string', 'string', NULL, 'string', NULL);
                            COMMIT;

                            -- ----------------------------
-- Table structure for zt_frame_use3
-- ----------------------------
                            DROP TABLE IF EXISTS `zt_frame_use3`;
                            CREATE TABLE `zt_frame_use3`
                            (
                                `id` varchar
                            (
                                64
                            ) NOT NULL COMMENT '主键',
                                `udf_1` varchar
                            (
                                2000
                            ) CHARACTER SET utf8 DEFAULT '[]' COMMENT '字段1',
                                `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
                                `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
                                `created_by` varchar
                            (
                                32
                            ) DEFAULT NULL COMMENT '创建人',
                                `updated_by` varchar
                            (
                                32
                            ) DEFAULT NULL COMMENT '更新人',
                                `remark` varchar
                            (
                                255
                            ) DEFAULT '' COMMENT '备注',
                                `enum_1` varchar
                            (
                                255
                            ) DEFAULT NULL COMMENT '枚举1',
                                `version` bigint
                            (
                                20
                            ) DEFAULT '1' COMMENT '乐观锁',
                                `created_by_name` varchar
                            (
                                32
                            ) DEFAULT NULL COMMENT '创建人姓名',
                                `updated_by_name` varchar
                            (
                                32
                            ) DEFAULT NULL COMMENT '更新人姓名',
                                `udf_2` varchar
                            (
                                32
                            ) DEFAULT '' COMMENT '字段2',
                                `delete_flag` tinyint
                            (
                                1
                            ) DEFAULT '0' COMMENT '逻辑删除  0未删除。1已删除',
                                `biz_user_code` varchar
                            (
                                255
                            ) DEFAULT NULL,
                                `biz_dept_code` varchar
                            (
                                255
                            ) DEFAULT NULL,
                                PRIMARY KEY
                            (`id`
                            ) USING BTREE
                                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='zt通用框架demo';

                            -- ----------------------------
-- Records of zt_frame_use3
-- ----------------------------
                            BEGIN
                                ;
                                INSERT INTO `zt_frame_use3`
                                VALUES ('0string', 'string', '2021-09-12 09:32:00', '2021-09-27 14:13:18', 'platform', 'platform', 'string', 'TWO1', 4, '平台', '平台', '', 1, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('1string', 'string', '2021-09-12 09:32:00', '2021-09-12 09:32:41', 'platform', 'platform', 'aaa', 'ONE1', 2, '平台', '平台', '', 1, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('2string', 'string', '2021-09-12 09:30:40', '2021-09-12 09:30:40', 'platform', 'platform', 'string', 'ONE1', 1, '平台', '平台', '', 1, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('3string', 'string', '2021-09-12 09:00:50', '2021-09-28 00:13:44', 'string', 'platform', 'aaa', 'ONE1', 3, 'string', '平台', '', 1, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('s1tring', 'string', '2021-09-27 14:01:45', '2021-10-02 10:36:15', 'string', 'string', 'string', 'ONE1', 2, 'string', 'string', '1', 1, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('s2tring', 'string', '2021-09-27 14:01:45', '2021-09-27 14:01:45', 'string', 'string', 'string', 'TWO1', 1, 'string', 'string', '2', 0, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('s3tring', 'string', '2021-09-27 14:03:27', '2021-09-27 14:03:27', NULL, 'string', '', NULL, 1, 'string', 'string', 'string', 0, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('stringa', 'string', '2021-08-27 14:47:30', '2021-08-27 14:47:30', 'string', NULL, NULL, NULL, 1, 'string', NULL, '1', 0, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('stringb', 'string', '2021-08-27 14:47:30', '2021-08-27 14:47:30', NULL, NULL, NULL, 'ONE1', 1, NULL, NULL, '啊', 0, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('stringc', 'string', '2021-08-27 14:47:30', '2021-08-27 14:47:30', NULL, NULL, 'string', NULL, 1, NULL, NULL, 'b', 0, NULL, NULL);
                                INSERT INTO `zt_frame_use3`
                                VALUES ('stringd', 'string', '2021-08-27 14:47:30', '2021-08-27 14:47:30', NULL, 'string', NULL, NULL, 1, NULL, 'string', '', 0, NULL, NULL);
                                COMMIT;

                                -- ----------------------------
-- Table structure for zt_post_info
-- ----------------------------
                                DROP TABLE IF EXISTS `zt_post_info`;
                                CREATE TABLE `zt_post_info`
                                (
                                    `id` bigint
                                (
                                    20
                                ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                    `this_code` varchar
                                (
                                    64
                                ) NOT NULL COMMENT '职位编号（唯一，不可修改）',
                                    `parent_code` varchar
                                (
                                    64
                                ) NOT NULL DEFAULT '' COMMENT '上级职位编号',
                                    `this_name` varchar
                                (
                                    64
                                ) NOT NULL COMMENT '职位名称',
                                    `enable_flag` int
                                (
                                    11
                                ) NOT NULL DEFAULT '1' COMMENT '职位状态（true启用，false禁用）',
                                    `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                    `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                    `created_by` varchar
                                (
                                    32
                                ) NOT NULL COMMENT '创建人',
                                    `updated_by` varchar
                                (
                                    32
                                ) NOT NULL COMMENT '更新人',
                                    `remark` varchar
                                (
                                    255
                                ) NOT NULL DEFAULT '' COMMENT '备注',
                                    `created_by_name` varchar
                                (
                                    32
                                ) NOT NULL COMMENT '创建人姓名',
                                    `updated_by_name` varchar
                                (
                                    32
                                ) NOT NULL COMMENT '更新人姓名',
                                    PRIMARY KEY
                                (`id`
                                ) USING BTREE,
                                    UNIQUE KEY `uk_post_code`
                                (`this_code`
                                )
                                  USING BTREE,
                                    KEY `index_post_and_parent_code`
                                (
                                    `
                                    this_code
                                    `,
                                    `
                                    parent_code
                                    `
                                )
                                  USING BTREE
                                    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='职位信息表';

                                -- ----------------------------
-- Records of zt_post_info
-- ----------------------------
                                BEGIN
                                    ;
                                    INSERT INTO `zt_post_info`
                                    VALUES (1, 'owner', '', '创始人', 1, '2021-01-03 18:27:10', '2021-01-03 18:27:10', 'admin', 'admin', 'string', '', '');
                                    INSERT INTO `zt_post_info`
                                    VALUES (2, 'chairman', '', '董事长', 1, '2021-01-03 18:27:10', '2021-01-03 18:27:10', 'admin', 'admin', 'string', '', '');
                                    INSERT INTO `zt_post_info`
                                    VALUES (3, 'shanghaimanager', '', '上海分公司总经理', 1, '2021-01-03 18:27:10', '2021-01-03 18:27:10', 'admin', 'admin', 'string', '', '');
                                    COMMIT;

                                    -- ----------------------------
-- Table structure for zt_role_component_info
-- ----------------------------
                                    DROP TABLE IF EXISTS `zt_role_component_info`;
                                    CREATE TABLE `zt_role_component_info`
                                    (
                                        `id` bigint
                                    (
                                        20
                                    ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                        `role_code` varchar
                                    (
                                        64
                                    ) NOT NULL COMMENT '角色编号',
                                        `component_code` varchar
                                    (
                                        64
                                    ) NOT NULL COMMENT '组件编号',
                                        `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                        `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                        `created_by` varchar
                                    (
                                        32
                                    ) NOT NULL COMMENT '创建人',
                                        `updated_by` varchar
                                    (
                                        32
                                    ) NOT NULL COMMENT '更新人',
                                        `remark` varchar
                                    (
                                        32
                                    ) NOT NULL DEFAULT '' COMMENT '备注',
                                        `created_by_name` varchar
                                    (
                                        32
                                    ) NOT NULL COMMENT '创建人姓名',
                                        `updated_by_name` varchar
                                    (
                                        32
                                    ) NOT NULL COMMENT '更新人姓名',
                                        PRIMARY KEY
                                    (`id`
                                    ) USING BTREE,
                                        UNIQUE KEY `uk_role_menu_code`
                                    (
                                        `
                                        role_code
                                        `,
                                        `
                                        component_code
                                        `
                                    )
                                      USING BTREE
                                        ) ENGINE=InnoDB AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COMMENT='角色组件关联表（权限）';

                                    -- ----------------------------
-- Records of zt_role_component_info
-- ----------------------------
                                    BEGIN
                                        ;
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (1, 'superdeveloper', 'ROLE_MANAGE:QUERY', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (2, 'superdeveloper', 'ROLE_MANAGE:ADD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (3, 'superdeveloper', 'ROLE_MANAGE:EDIT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (4, 'superdeveloper', 'ROLE_MANAGE:DEL', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (5, 'superdeveloper', 'ROLE_MANAGE:APP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (6, 'superdeveloper', 'ROLE_MANAGE:UNAPP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (7, 'superdeveloper', 'ROLE_MANAGE:UPLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (8, 'superdeveloper', 'ROLE_MANAGE:DOWNLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (9, 'superdeveloper', 'ROLE_MANAGE:IMPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (10, 'superdeveloper', 'ROLE_MANAGE:EXPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (11, 'superdeveloper', 'ROLE_MANAGE:PRINT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (12, 'superdeveloper', 'ROLE_MANAGE:ENABLE', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (13, 'superdeveloper', 'COMPONENT_MANAGE:QUERY', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (14, 'superdeveloper', 'COMPONENT_MANAGE:ADD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (15, 'superdeveloper', 'COMPONENT_MANAGE:EDIT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (16, 'superdeveloper', 'COMPONENT_MANAGE:DEL', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (17, 'superdeveloper', 'COMPONENT_MANAGE:APP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (18, 'superdeveloper', 'COMPONENT_MANAGE:UNAPP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (19, 'superdeveloper', 'COMPONENT_MANAGE:UPLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (20, 'superdeveloper', 'COMPONENT_MANAGE:DOWNLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (21, 'superdeveloper', 'COMPONENT_MANAGE:IMPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (22, 'superdeveloper', 'COMPONENT_MANAGE:EXPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (23, 'superdeveloper', 'COMPONENT_MANAGE:PRINT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (24, 'superdeveloper', 'COMPONENT_MANAGE:ENABLE', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (25, 'superdeveloper', 'DEPT_MANAGE:QUERY', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (26, 'superdeveloper', 'DEPT_MANAGE:ADD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (27, 'superdeveloper', 'DEPT_MANAGE:EDIT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (28, 'superdeveloper', 'DEPT_MANAGE:DEL', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (29, 'superdeveloper', 'DEPT_MANAGE:APP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (30, 'superdeveloper', 'DEPT_MANAGE:UNAPP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (31, 'superdeveloper', 'DEPT_MANAGE:UPLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (32, 'superdeveloper', 'DEPT_MANAGE:DOWNLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (33, 'superdeveloper', 'DEPT_MANAGE:IMPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (34, 'superdeveloper', 'DEPT_MANAGE:EXPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (35, 'superdeveloper', 'DEPT_MANAGE:PRINT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (36, 'superdeveloper', 'DEPT_MANAGE:ENABLE', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (37, 'superdeveloper', 'USER_MANAGE:QUERY', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (38, 'superdeveloper', 'USER_MANAGE:ADD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (39, 'superdeveloper', 'USER_MANAGE:EDIT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (40, 'superdeveloper', 'USER_MANAGE:DEL', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (41, 'superdeveloper', 'USER_MANAGE:APP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (42, 'superdeveloper', 'USER_MANAGE:UNAPP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (43, 'superdeveloper', 'USER_MANAGE:UPLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (44, 'superdeveloper', 'USER_MANAGE:DOWNLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (45, 'superdeveloper', 'USER_MANAGE:IMPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (46, 'superdeveloper', 'USER_MANAGE:EXPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (47, 'superdeveloper', 'USER_MANAGE:PRINT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (48, 'superdeveloper', 'USER_MANAGE:ENABLE', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (49, 'superdeveloper', 'POST_MANAGE:QUERY', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (50, 'superdeveloper', 'POST_MANAGE:ADD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (51, 'superdeveloper', 'POST_MANAGE:EDIT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (52, 'superdeveloper', 'POST_MANAGE:DEL', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (53, 'superdeveloper', 'POST_MANAGE:APP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (54, 'superdeveloper', 'POST_MANAGE:UNAPP', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (55, 'superdeveloper', 'POST_MANAGE:UPLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (56, 'superdeveloper', 'POST_MANAGE:DOWNLOAD', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (57, 'superdeveloper', 'POST_MANAGE:IMPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (58, 'superdeveloper', 'POST_MANAGE:EXPORT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (59, 'superdeveloper', 'POST_MANAGE:PRINT', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (60, 'superdeveloper', 'POST_MANAGE:ENABLE', '2021-01-03 18:24:54', '2021-01-03 18:24:54', 'admin', 'admin', '', 'admin', 'admin');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (89, 'test1', 'USER_MANAGE:QUERY', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (90, 'test1', 'USER_MANAGE:ADD', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (91, 'test1', 'USER_MANAGE:EDIT', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (92, 'test1', 'USER_MANAGE:DEL', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (93, 'test1', 'USER_MANAGE:APP', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (94, 'test1', 'USER_MANAGE:UNAPP', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (95, 'test1', 'USER_MANAGE:UPLOAD', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (96, 'test1', 'USER_MANAGE:DOWNLOAD', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (97, 'test1', 'USER_MANAGE:IMPORT', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (98, 'test1', 'USER_MANAGE:EXPORT', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (99, 'test1', 'USER_MANAGE:PRINT', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (100, 'test1', 'USER_MANAGE:ENABLE', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (101, 'test1', 'ROLE_MANAGE:QUERY', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (102, 'test1', 'ROLE_MANAGE:ADD', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (103, 'test1', 'ROLE_MANAGE:EDIT', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (104, 'test1', 'ROLE_MANAGE:DEL', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (105, 'test1', 'ROLE_MANAGE:APP', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (106, 'test1', 'ROLE_MANAGE:UNAPP', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (107, 'test1', 'ROLE_MANAGE:UPLOAD', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (108, 'test1', 'ROLE_MANAGE:DOWNLOAD', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (109, 'test1', 'ROLE_MANAGE:IMPORT', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (110, 'test1', 'ROLE_MANAGE:EXPORT', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (111, 'test1', 'ROLE_MANAGE:PRINT', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (112, 'test1', 'ROLE_MANAGE:ENABLE', '2021-10-18 21:53:52', '2021-10-18 21:53:52', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        INSERT INTO `zt_role_component_info`
                                        VALUES (113, 'testrole1', 'ROLE_MANAGE:QUERY', '2021-10-31 10:23:03', '2021-10-31 10:23:03', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                        COMMIT;

                                        -- ----------------------------
-- Table structure for zt_role_dept_info
-- ----------------------------
                                        DROP TABLE IF EXISTS `zt_role_dept_info`;
                                        CREATE TABLE `zt_role_dept_info`
                                        (
                                            `id` bigint
                                        (
                                            20
                                        ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                            `role_code` varchar
                                        (
                                            64
                                        ) NOT NULL COMMENT '角色编号',
                                            `dept_code` varchar
                                        (
                                            64
                                        ) NOT NULL COMMENT '部门编号',
                                            `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                            `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                            `created_by` varchar
                                        (
                                            32
                                        ) NOT NULL COMMENT '创建人',
                                            `updated_by` varchar
                                        (
                                            32
                                        ) NOT NULL COMMENT '更新人',
                                            `remark` varchar
                                        (
                                            32
                                        ) NOT NULL DEFAULT '' COMMENT '备注',
                                            `created_by_name` varchar
                                        (
                                            32
                                        ) NOT NULL COMMENT '创建人姓名',
                                            `updated_by_name` varchar
                                        (
                                            32
                                        ) NOT NULL COMMENT '更新人姓名',
                                            PRIMARY KEY
                                        (`id`
                                        ) USING BTREE,
                                            UNIQUE KEY `uk_role_dept_code`
                                        (
                                            `
                                            role_code
                                            `,
                                            `
                                            dept_code
                                            `
                                        )
                                          USING BTREE
                                            ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='部门角色关联表';

                                        -- ----------------------------
-- Records of zt_role_dept_info
-- ----------------------------
                                        BEGIN
                                            ;
                                            INSERT INTO `zt_role_dept_info`
                                            VALUES (2, 'test1', 'shanghaidevelop', '2021-10-18 21:35:35', '2021-10-18 21:35:35', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                            INSERT INTO `zt_role_dept_info`
                                            VALUES (3, 'test1', 'shanghaihr', '2021-10-18 21:35:35', '2021-10-18 21:35:35', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                            COMMIT;

                                            -- ----------------------------
-- Table structure for zt_role_info
-- ----------------------------
                                            DROP TABLE IF EXISTS `zt_role_info`;
                                            CREATE TABLE `zt_role_info`
                                            (
                                                `id` bigint
                                            (
                                                20
                                            ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                `this_code` varchar
                                            (
                                                64
                                            ) NOT NULL COMMENT '角色编号（唯一，不可修改）',
                                                `parent_code` varchar
                                            (
                                                64
                                            ) NOT NULL DEFAULT '' COMMENT '上级角色编号',
                                                `this_name` varchar
                                            (
                                                64
                                            ) NOT NULL COMMENT '角色名称',
                                                `role_type` varchar
                                            (
                                                64
                                            ) NOT NULL COMMENT '角色类型（菜单角色-读取zt_role_component_info表，数据角色-读取data_scope_type字段，综合角色-菜单角色与数据角色）',
                                                `enable_flag` int
                                            (
                                                11
                                            ) NOT NULL DEFAULT '1' COMMENT '角色状态（禁用，启用）',
                                                `admin_flag` int
                                            (
                                                11
                                            ) NOT NULL DEFAULT '0' COMMENT '是否是管理员角色',
                                                `with_children_flag` int
                                            (
                                                11
                                            ) NOT NULL DEFAULT '1' COMMENT '菜单权限：是否包含子角色。默认包含',
                                                `data_scope_type` varchar
                                            (
                                                32
                                            ) NOT NULL DEFAULT 'SELF' COMMENT '数据权限类型',
                                                `data_scope_opt_type` varchar
                                            (
                                                32
                                            ) NOT NULL DEFAULT 'AND' COMMENT 'data_scope_type专用',
                                                `role_custom` varchar
                                            (
                                                2000
                                            ) NOT NULL DEFAULT '[]' COMMENT '数据权限类型为自定义时使用。DEPT_CUSTOM存部门编号，POST_CUSTOM存职位编号，USER_CUSTOM存用户编号',
                                                `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                                `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                                `created_by` varchar
                                            (
                                                32
                                            ) NOT NULL COMMENT '创建人',
                                                `updated_by` varchar
                                            (
                                                32
                                            ) NOT NULL COMMENT '更新人',
                                                `remark` varchar
                                            (
                                                255
                                            ) NOT NULL DEFAULT '' COMMENT '备注',
                                                `created_by_name` varchar
                                            (
                                                32
                                            ) NOT NULL COMMENT '创建人姓名',
                                                `updated_by_name` varchar
                                            (
                                                32
                                            ) NOT NULL COMMENT '更新人姓名',
                                                PRIMARY KEY
                                            (`id`
                                            ) USING BTREE,
                                                UNIQUE KEY `uk_role_code`
                                            (`this_code`
                                            )
                                              USING BTREE,
                                                KEY `index_role_and_parent_code`
                                            (
                                                `
                                                this_code
                                                `,
                                                `
                                                parent_code
                                                `
                                            )
                                              USING BTREE
                                                ) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

                                            -- ----------------------------
-- Records of zt_role_info
-- ----------------------------
                                            BEGIN
                                                ;
                                                INSERT INTO `zt_role_info`
                                                VALUES (1, 'superadmin', '', '超级管理员', 'MULTIPLE', 1, 0, 1, 'ALL', 'OR', '[]', '2021-01-03 18:28:32', '2021-01-03 18:28:32', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (2, 'superdeveloper', 'superadmin', '架构师', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-01-03 18:28:32', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (3, 'test1', 'superadmin', '架构师1', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-10-11 18:35:30', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (4, 'test2', 'superadmin', '架构师2', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-10-11 18:03:05', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (5, 'test3', 'superadmin', '架构师3', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-10-17 21:38:10', 'admin', 'admin', 'stringqw', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (6, 'test4', 'superadmin', '架构师4', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-10-17 21:22:32', 'admin', 'admin', 'stringaaaa', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (7, 'test5', 'superadmin', '架构师5', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-10-17 21:08:13', 'admin', 'admin', 'stringasas', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (8, 'test6', 'superadmin', '架构师', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-10-17 21:09:32', 'admin', 'admin', 'stringaaa', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (9, 'test7', 'superadmin', '架构师77', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-10-11 18:34:44', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (10, 'test88', 'superadmin', '架构师', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-10-15 12:31:19', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (11, 'test9', 'superadmin', '架构师', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-01-03 18:28:32', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (12, 'test10', 'superadmin', '架构师', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-01-03 18:28:32', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (13, 'test11', 'superadmin', '架构师', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-01-03 18:28:32', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (14, 'test12', 'superadmin', '架构师', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-01-03 18:28:32', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (15, 'test13', 'superadmin', '架构师', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-01-03 18:28:32', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (16, 'test14', 'superadmin', '架构师', 'COMPONENT', 1, 0, 1, 'DEPT_CUSTOM', 'OR', '[\"rootdevelop\"]', '2021-01-03 18:28:32', '2021-01-03 18:28:32', 'admin', 'admin', 'string', '', '');
                                                INSERT INTO `zt_role_info`
                                                VALUES (17, 'testrole1', '', '测试角色1', 'COMPONENT', 1, 0, 1, 'SELF', 'AND', '[]', '2021-10-31 10:21:45', '2021-10-31 10:21:45', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                                COMMIT;

                                                -- ----------------------------
-- Table structure for zt_role_post_info
-- ----------------------------
                                                DROP TABLE IF EXISTS `zt_role_post_info`;
                                                CREATE TABLE `zt_role_post_info`
                                                (
                                                    `id` bigint
                                                (
                                                    20
                                                ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                    `role_code` varchar
                                                (
                                                    64
                                                ) NOT NULL COMMENT '角色编号',
                                                    `post_code` varchar
                                                (
                                                    64
                                                ) NOT NULL COMMENT '职位编号',
                                                    `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                                    `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                                    `created_by` varchar
                                                (
                                                    32
                                                ) NOT NULL COMMENT '创建人',
                                                    `updated_by` varchar
                                                (
                                                    32
                                                ) NOT NULL COMMENT '更新人',
                                                    `remark` varchar
                                                (
                                                    32
                                                ) NOT NULL DEFAULT '' COMMENT '备注',
                                                    `created_by_name` varchar
                                                (
                                                    32
                                                ) NOT NULL COMMENT '创建人姓名',
                                                    `updated_by_name` varchar
                                                (
                                                    32
                                                ) NOT NULL COMMENT '更新人姓名',
                                                    PRIMARY KEY
                                                (`id`
                                                ) USING BTREE,
                                                    UNIQUE KEY `uk_role_post_code`
                                                (
                                                    `
                                                    role_code
                                                    `,
                                                    `
                                                    post_code
                                                    `
                                                )
                                                  USING BTREE
                                                    ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='职位角色关联表';

                                                -- ----------------------------
-- Records of zt_role_post_info
-- ----------------------------
                                                BEGIN
                                                    ;
                                                    INSERT INTO `zt_role_post_info`
                                                    VALUES (2, 'test1', 'shanghaimanager', '2021-10-18 21:37:33', '2021-10-18 21:37:33', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                                    COMMIT;

                                                    -- ----------------------------
-- Table structure for zt_role_user_info
-- ----------------------------
                                                    DROP TABLE IF EXISTS `zt_role_user_info`;
                                                    CREATE TABLE `zt_role_user_info`
                                                    (
                                                        `id` bigint
                                                    (
                                                        20
                                                    ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                        `role_code` varchar
                                                    (
                                                        64
                                                    ) NOT NULL COMMENT '角色编号',
                                                        `user_code` varchar
                                                    (
                                                        64
                                                    ) NOT NULL COMMENT '用户编号',
                                                        `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                                        `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                                        `created_by` varchar
                                                    (
                                                        32
                                                    ) NOT NULL COMMENT '创建人',
                                                        `updated_by` varchar
                                                    (
                                                        32
                                                    ) NOT NULL COMMENT '更新人',
                                                        `remark` varchar
                                                    (
                                                        32
                                                    ) NOT NULL DEFAULT '' COMMENT '备注',
                                                        `created_by_name` varchar
                                                    (
                                                        32
                                                    ) NOT NULL COMMENT '创建人姓名',
                                                        `updated_by_name` varchar
                                                    (
                                                        32
                                                    ) NOT NULL COMMENT '更新人姓名',
                                                        PRIMARY KEY
                                                    (`id`
                                                    ) USING BTREE,
                                                        UNIQUE KEY `uk_role_user_code`
                                                    (
                                                        `
                                                        role_code
                                                        `,
                                                        `
                                                        user_code
                                                        `
                                                    )
                                                      USING BTREE
                                                        ) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

                                                    -- ----------------------------
-- Records of zt_role_user_info
-- ----------------------------
                                                    BEGIN
                                                        ;
                                                        INSERT INTO `zt_role_user_info`
                                                        VALUES (1, 'superdeveloper', 'zhangtao', '2021-01-03 19:23:54', '2021-01-03 19:23:54', 'admin', 'admin', 'string', '', '');
                                                        INSERT INTO `zt_role_user_info`
                                                        VALUES (3, 'test1', 'zhangtao', '2021-10-18 21:37:09', '2021-10-18 21:37:09', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                                        INSERT INTO `zt_role_user_info`
                                                        VALUES (4, 'testrole1', 'testuser1', '2021-10-31 10:23:11', '2021-10-31 10:23:11', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                                        COMMIT;

                                                        -- ----------------------------
-- Table structure for zt_spec_hide
-- ----------------------------
                                                        DROP TABLE IF EXISTS `zt_spec_hide`;
                                                        CREATE TABLE `zt_spec_hide`
                                                        (
                                                            `id` bigint
                                                        (
                                                            20
                                                        ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                            `vxe_table_name` varchar
                                                        (
                                                            32
                                                        ) NOT NULL COMMENT '表名',
                                                            `vxe_field_name` varchar
                                                        (
                                                            32
                                                        ) NOT NULL COMMENT '列字段名',
                                                            `spec_type` varchar
                                                        (
                                                            32
                                                        ) NOT NULL COMMENT 'RoleTypeEnum权限类型',
                                                            `spec_code` varchar
                                                        (
                                                            255
                                                        ) NOT NULL COMMENT '编号（用户编号，角色编号等）',
                                                            `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                                            `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                                            `created_by` varchar
                                                        (
                                                            32
                                                        ) NOT NULL COMMENT '创建人',
                                                            `updated_by` varchar
                                                        (
                                                            32
                                                        ) NOT NULL COMMENT '更新人',
                                                            `remark` varchar
                                                        (
                                                            32
                                                        ) NOT NULL DEFAULT '' COMMENT '备注',
                                                            `created_by_name` varchar
                                                        (
                                                            32
                                                        ) NOT NULL COMMENT '创建人姓名',
                                                            `updated_by_name` varchar
                                                        (
                                                            32
                                                        ) NOT NULL COMMENT '更新人姓名',
                                                            PRIMARY KEY
                                                        (`id`
                                                        ) USING BTREE
                                                            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='特殊隐藏的字段。这个表中记录的字段默认所有人都可见，只有指定的用户（或者角色，部门等）不可查看';

                                                        -- ----------------------------
-- Records of zt_spec_hide
-- ----------------------------
                                                        BEGIN
                                                            ;
                                                            COMMIT;

                                                            -- ----------------------------
-- Table structure for zt_spec_show
-- ----------------------------
                                                            DROP TABLE IF EXISTS `zt_spec_show`;
                                                            CREATE TABLE `zt_spec_show`
                                                            (
                                                                `id` bigint
                                                            (
                                                                20
                                                            ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                                `vxe_table_name` varchar
                                                            (
                                                                32
                                                            ) NOT NULL COMMENT '表名',
                                                                `vxe_field_name` varchar
                                                            (
                                                                32
                                                            ) NOT NULL COMMENT '列字段名',
                                                                `spec_type` varchar
                                                            (
                                                                32
                                                            ) NOT NULL COMMENT 'RoleTypeEnum权限类型',
                                                                `spec_code` varchar
                                                            (
                                                                255
                                                            ) NOT NULL COMMENT '编号（用户编号，角色编号等，根据spec_type决定）',
                                                                `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
                                                                `gmt_update` datetime DEFAULT NULL COMMENT '更新时间',
                                                                `created_by` varchar
                                                            (
                                                                32
                                                            ) DEFAULT NULL COMMENT '创建人',
                                                                `updated_by` varchar
                                                            (
                                                                32
                                                            ) DEFAULT NULL COMMENT '更新人',
                                                                `remark` varchar
                                                            (
                                                                32
                                                            ) DEFAULT '' COMMENT '备注',
                                                                `created_by_name` varchar
                                                            (
                                                                32
                                                            ) DEFAULT NULL COMMENT '创建人姓名',
                                                                `updated_by_name` varchar
                                                            (
                                                                32
                                                            ) DEFAULT NULL COMMENT '更新人姓名',
                                                                PRIMARY KEY
                                                            (`id`
                                                            ) USING BTREE
                                                                ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='特殊显示的字段。这个表中记录的字段默认所有人都不可见（管理员除外），只有指定的用户（或者角色，部门等）可以查看';

                                                            -- ----------------------------
-- Records of zt_spec_show
-- ----------------------------
                                                            BEGIN
                                                                ;
                                                                INSERT INTO `zt_spec_show`
                                                                VALUES (2, 'zt_user_info', 'password', 'USER', 'admin', NULL, NULL, NULL, NULL, '', NULL, NULL);
                                                                COMMIT;

                                                                -- ----------------------------
-- Table structure for zt_user_dept_info
-- ----------------------------
                                                                DROP TABLE IF EXISTS `zt_user_dept_info`;
                                                                CREATE TABLE `zt_user_dept_info`
                                                                (
                                                                    `id` bigint
                                                                (
                                                                    20
                                                                ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                                    `user_code` varchar
                                                                (
                                                                    64
                                                                ) NOT NULL COMMENT '用户编号',
                                                                    `dept_code` varchar
                                                                (
                                                                    64
                                                                ) NOT NULL COMMENT '部门编号',
                                                                    `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                                                    `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                                                    `created_by` varchar
                                                                (
                                                                    32
                                                                ) NOT NULL COMMENT '创建人',
                                                                    `updated_by` varchar
                                                                (
                                                                    32
                                                                ) NOT NULL COMMENT '更新人',
                                                                    `remark` varchar
                                                                (
                                                                    32
                                                                ) NOT NULL DEFAULT '' COMMENT '备注',
                                                                    `created_by_name` varchar
                                                                (
                                                                    32
                                                                ) NOT NULL COMMENT '创建人姓名',
                                                                    `updated_by_name` varchar
                                                                (
                                                                    32
                                                                ) NOT NULL COMMENT '更新人姓名',
                                                                    PRIMARY KEY
                                                                (`id`
                                                                ) USING BTREE,
                                                                    UNIQUE KEY `uk_user_dept_code`
                                                                (
                                                                    `
                                                                    user_code
                                                                    `,
                                                                    `
                                                                    dept_code
                                                                    `
                                                                )
                                                                  USING BTREE
                                                                    ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='用户部门关联表';

                                                                -- ----------------------------
-- Records of zt_user_dept_info
-- ----------------------------
                                                                BEGIN
                                                                    ;
                                                                    INSERT INTO `zt_user_dept_info`
                                                                    VALUES (1, 'zhangtao', 'shanghairoot', '2021-01-03 13:57:08', '2021-01-03 13:57:08', 'admin', 'admin', 'string', '', '');
                                                                    INSERT INTO `zt_user_dept_info`
                                                                    VALUES (2, 'admin', 'rootdevelop', '2021-01-03 13:57:08', '2021-01-03 13:57:08', 'admin', 'admin', 'string', '', '');
                                                                    INSERT INTO `zt_user_dept_info`
                                                                    VALUES (3, 'zhangtao', 'beijingroot', '2021-01-03 13:57:08', '2021-01-03 13:57:08', 'admin', 'admin', 'string', '', '');
                                                                    COMMIT;

                                                                    -- ----------------------------
-- Table structure for zt_user_info
-- ----------------------------
                                                                    DROP TABLE IF EXISTS `zt_user_info`;
                                                                    CREATE TABLE `zt_user_info`
                                                                    (
                                                                        `id` varchar
                                                                    (
                                                                        64
                                                                    ) NOT NULL COMMENT '主键，与code相同，uid',
                                                                        `user_code` varchar
                                                                    (
                                                                        64
                                                                    ) NOT NULL COMMENT '用户编号（唯一，不可修改，作为其他表外键）',
                                                                        `username` varchar
                                                                    (
                                                                        64
                                                                    ) NOT NULL COMMENT '用户名（唯一，暂定不可修改）',
                                                                        `nickname` varchar
                                                                    (
                                                                        64
                                                                    ) NOT NULL DEFAULT '' COMMENT '昵称',
                                                                        `password` varchar
                                                                    (
                                                                        64
                                                                    ) NOT NULL COMMENT '密码',
                                                                        `gender` int
                                                                    (
                                                                        11
                                                                    ) NOT NULL DEFAULT '0' COMMENT '用户性别：0 未知 1 男性 2 女性',
                                                                        `status` int
                                                                    (
                                                                        11
                                                                    ) NOT NULL DEFAULT '0' COMMENT '用户状态：0 正常，1 禁用，2 审核中，3 审核拒绝，4 已注销',
                                                                        `mobile` varchar
                                                                    (
                                                                        11
                                                                    ) DEFAULT NULL COMMENT '手机号（唯一，可修改）',
                                                                        `mobile_confirmed` int
                                                                    (
                                                                        11
                                                                    ) NOT NULL DEFAULT '0' COMMENT '手机号验证状态：0 未验证 1 已验证，未验证用户不可登录',
                                                                        `email` varchar
                                                                    (
                                                                        64
                                                                    ) DEFAULT NULL COMMENT '邮箱地址',
                                                                        `email_confirmed` int
                                                                    (
                                                                        11
                                                                    ) NOT NULL DEFAULT '0' COMMENT '邮箱验证状态：0 未验证 1 已验证，未验证用户不可登录',
                                                                        `avatar` varchar
                                                                    (
                                                                        255
                                                                    ) NOT NULL DEFAULT '' COMMENT '头像',
                                                                        `wx_unionid` varchar
                                                                    (
                                                                        64
                                                                    ) DEFAULT NULL COMMENT '微信unionid',
                                                                        `wx_openid` varchar
                                                                    (
                                                                        2000
                                                                    ) DEFAULT '{}' COMMENT '微信各个平台openid',
                                                                        `admin_flag` int
                                                                    (
                                                                        11
                                                                    ) NOT NULL DEFAULT '0' COMMENT '是否是管理员',
                                                                        `block_flag` int
                                                                    (
                                                                        11
                                                                    ) NOT NULL DEFAULT '0' COMMENT '是否被锁定',
                                                                        `default_dept_code` varchar
                                                                    (
                                                                        255
                                                                    ) NOT NULL DEFAULT '' COMMENT '默认所属部门',
                                                                        `default_post_code` varchar
                                                                    (
                                                                        255
                                                                    ) NOT NULL DEFAULT '' COMMENT '默认职位',
                                                                        `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                                                        `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                                                        `created_by` varchar
                                                                    (
                                                                        32
                                                                    ) NOT NULL COMMENT '创建人',
                                                                        `updated_by` varchar
                                                                    (
                                                                        32
                                                                    ) NOT NULL COMMENT '更新人',
                                                                        `remark` varchar
                                                                    (
                                                                        255
                                                                    ) NOT NULL DEFAULT '' COMMENT '备注',
                                                                        `created_by_name` varchar
                                                                    (
                                                                        32
                                                                    ) NOT NULL COMMENT '创建人姓名',
                                                                        `updated_by_name` varchar
                                                                    (
                                                                        32
                                                                    ) NOT NULL COMMENT '更新人姓名',
                                                                        PRIMARY KEY
                                                                    (`id`
                                                                    ) USING BTREE,
                                                                        UNIQUE KEY `uk_user_info_code`
                                                                    (`user_code`
                                                                    )
                                                                      USING BTREE,
                                                                        UNIQUE KEY `uk_user_info_name`
                                                                    (`username`
                                                                    )
                                                                      USING BTREE,
                                                                        UNIQUE KEY `uk_user_info_mobile`
                                                                    (`mobile`
                                                                    )
                                                                      USING BTREE
                                                                        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户信息表';

                                                                    -- ----------------------------
-- Records of zt_user_info
-- ----------------------------
                                                                    BEGIN
                                                                        ;
                                                                        INSERT INTO `zt_user_info`
                                                                        VALUES ('admin', 'admin', 'admin', '管理员', '123456', 0, 0, '18264399670', 0, NULL, 0, '', NULL, '{}', 1, 0, '', '', '2020-12-31 11:47:48', '2020-12-31 11:47:48', 'admin', 'admin', '默认账号', '', '');
                                                                        INSERT INTO `zt_user_info`
                                                                        VALUES ('testuser1', 'testuser1', 'testuser1', '测试用户', 'testuser1', 0, 0, '13333333333', 0, NULL, 0, '', NULL, '{}', 0, 0, '', '', '2021-10-31 10:20:35', '2021-10-31 10:20:35', 'zhangtao', 'zhangtao', '', 'zhangtao', 'zhangtao');
                                                                        INSERT INTO `zt_user_info`
                                                                        VALUES ('zhangtao', 'zhangtao', 'zhangtao', '张涛', '123456', 0, 0, '17091648421', 1, NULL, 0, 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTIwOQibbpkkOM73M0buZYvgqrkZgyQZpYsLt8oSkqanh6Nm3smic5iaHgsictSBQSQt9j6HzRwZhRtPkg/132', NULL, '{}', 0, 0, 'shanghairoot', 'shanghaimanager', '2020-12-31 11:47:48', '2020-12-31 11:47:48', 'admin', 'admin', '默认账号', '', '');
                                                                        COMMIT;

                                                                        -- ----------------------------
-- Table structure for zt_user_post_info
-- ----------------------------
                                                                        DROP TABLE IF EXISTS `zt_user_post_info`;
                                                                        CREATE TABLE `zt_user_post_info`
                                                                        (
                                                                            `id` bigint
                                                                        (
                                                                            20
                                                                        ) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                                                            `user_code` varchar
                                                                        (
                                                                            64
                                                                        ) NOT NULL COMMENT '用户编号',
                                                                            `post_code` varchar
                                                                        (
                                                                            64
                                                                        ) NOT NULL COMMENT '岗位编号',
                                                                            `gmt_create` datetime NOT NULL COMMENT '创建时间',
                                                                            `gmt_update` datetime NOT NULL COMMENT '更新时间',
                                                                            `created_by` varchar
                                                                        (
                                                                            32
                                                                        ) NOT NULL COMMENT '创建人',
                                                                            `updated_by` varchar
                                                                        (
                                                                            32
                                                                        ) NOT NULL COMMENT '更新人',
                                                                            `remark` varchar
                                                                        (
                                                                            32
                                                                        ) NOT NULL DEFAULT '' COMMENT '备注',
                                                                            `created_by_name` varchar
                                                                        (
                                                                            32
                                                                        ) NOT NULL COMMENT '创建人姓名',
                                                                            `updated_by_name` varchar
                                                                        (
                                                                            32
                                                                        ) NOT NULL COMMENT '更新人姓名',
                                                                            PRIMARY KEY
                                                                        (`id`
                                                                        ) USING BTREE,
                                                                            UNIQUE KEY `uk_user_post_code`
                                                                        (
                                                                            `
                                                                            user_code
                                                                            `,
                                                                            `
                                                                            post_code
                                                                            `
                                                                        )
                                                                          USING BTREE
                                                                            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户职位关联表';

                                                                        -- ----------------------------
-- Records of zt_user_post_info
-- ----------------------------
                                                                        BEGIN
                                                                            ;
                                                                            COMMIT;

                                                                            SET FOREIGN_KEY_CHECKS = 1;
