android开发框架
============
# 该框架主要分为三个基础包 RxCore、RxResource、RxBasicFun [需在Android Studio使用]

## .项目开发中首先在工程的build.gradle中做以下配置:
	添加以下三个主要引用
	compile 'com.cloud:rxcore-release:1.0.36@aar'
	compile 'com.cloud:rxresources-release:1.0.22@aar'
	compile 'com.cloud:basicfun-release:1.0.13@aar'

[完整配置](/docs/gradle_all_config.md)

## .[项目混淆配置](/docs/confounding.md)

## RxCRB框架组件
* [组件]
	* [视差列表控件——头部自定义布局,中间有tab分页,tab分页里有滚动列表如ListView ScrollView RecyclerView等](/docs/parallax_list.md)
	* [XRefreshRecyclerView[带刷新和加载]](/docs/xrecyclerview.md)
	* [FlowEditItemsLayout[流式]标签列表](/docs/tag_list.md)
	* [View任何视图下拉刷新与上拉加载控件](/docs/view_refresh_load.md)
	* [竖直方向自定义流程视图,类似物流进度](/docs/vertical_flow_track.md)
