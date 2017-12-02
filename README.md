android开发框架
============

*该框架主要分为三个基础包 RxCore、RxResource、RxBasicFun*
<div align=center>![images](/rxcrb.png)</div>

### 工程配置
###### 1.在项目的build.gradle中做以下配置添加以下三个主要引用:
```gradle
compile 'com.cloud:rxcore-release:1.0.75@aar'
compile 'com.cloud:rxresources-release:1.0.81@aar'
compile 'com.cloud:basicfun-release:1.0.32@aar'
```
*[Gradle完整配置](/docs/gradle_all_config.md)*
###### 2.项目初始配置
*注:代码文档中如果有继承相关基类的必须继承!!!*
* [AndroidManifest.xml配置](/docs/android_manifest_config.md)
* [Application配置](/docs/application_config.md)
* [其他配置](/docs/app_other_config.md)
###### 3.[混淆配置](/docs/confounding.md)

### RxCRB框架组件
* [应用程序进入后台或前台监听](/docs/front_back_listening.md)
* [广播使用](/docs/receive_use.md)
* [rxcore数据库使用]
	* [1.0.74版本之前使用方式](/docs/db_use.md)
	* [从1.0.75版本之后改用greenDao方式](/docs/db_use2.md)
* [文件操作](/docs/file_operation.md)
* [图片(文件)上传](/docs/file_upload.md)
* [网络]
	* [验证工具类](/docs/network.md)
	* [请求配置——OkRx]
		* [初始化配置](/docs/okrx_init.md)
		* [使用方式](/docs/okrx_use.md)
* [提示类、加载类、视图类、消息类对话框]
	* [ToastUtils工具类](/docs/toast_doc.md)
* [应用程序MQ缓存](/docs/app_mq_cache.md)
* [页面统计——变得简单](/docs/statistics_pager.md)
* [文本/标签]
	* [文本道行缩进-TextIndentation](/docs/text_indentation.md)
* [组件]
	* [视差列表控件——头部自定义布局,中间有tab分页,tab分页里有滚动列表如ListView ScrollView RecyclerView等](/docs/parallax_list2.md)
	* [XRefreshRecyclerView[带刷新和加载]](/docs/xrecyclerview.md)
	* [FlowEditItemsLayout[流式]标签列表](/docs/tag_list.md)
	* [View任何视图下拉刷新与上拉加载控件](/docs/view_refresh_load.md)
	* [TabLayoutindicator选择指示器](/docs/tab_layout_indicator.md)
	* [竖直方向自定义流程视图,类似物流进度](/docs/vertical_flow_track.md)
	* [图片(预览)选择编辑控件](/docs/picture_select_editor.md)
	* [滑动加载或销毁视图](/docs/silding_load_finish_view.md)
	* [H5Webview控件使用和示例](/docs/h5webview_api_demo.md)
* [处理方案、协议]
	* [H5调用APP任意API接口协议](/docs/h5_call_api_protrol.md)
* [控件使用问题及注意事项]
	* [RoundedImageView 实现圆形、圆角矩形的注意事项](/docs/attention.md)
	* [具有共同头部的 ViewPager](https://github.com/jeasonlzy/HeaderViewPager)
	* [MagicMirror](https://github.com/KingJA/MagicMirror)

-------
希望对该框架多多提建议一起来完善。如有任何问题请加QQ：715837375