export const tabbars = [{
    iconPath: "/static/tabbar/home.png",
    selectedIconPath: "/static/tabbar/home-active.png",
    text: '首页',
    count: 2,
    isDot: true,
    pagePath: "/pages/tabbar/index/index"
},
    {
        iconPath: "/static/tabbar/user.png",
        selectedIconPath: "/static/tabbar/user-active.png",
        text: '我的',
        midButton: true,
        pagePath: "/pages/tabbar/user/user"
    },
    // {
    // 	iconPath: "/static/tabbar/user.png",
    // 	selectedIconPath: "/static/tabbar/user-active.png",
    // 	text: '其他',
    // 	pagePath: "/pages/tabbar/center/center"
    // },
    {
        iconPath: "/static/tabbar/user.png",
        selectedIconPath: "/static/tabbar/user-active.png",
        text: '消息',
        pagePath: "/pages/tabbar/msg-center/msg-center"
    }
]
