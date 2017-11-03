android开发框架
============
# 该框架主要分为三个基础包 RxCore、RxResource、RxBasicFun [需在Android Studio使用]

### 工程配置
###### 1.在项目的build.gradle中做以下配置添加以下三个主要引用:
```gradle
compile 'com.cloud:rxcore-release:1.0.61@aar'
compile 'com.cloud:rxresources-release:1.0.42@aar'
compile 'com.cloud:basicfun-release:1.0.23@aar'
```
*[Gradle完整配置](/docs/gradle_all_config.md)*
###### 2.项目初始配置
*注:代码文档中如果有继承相关基类的必须继承!!!*
* [AndroidManifest.xml配置](/docs/android_manifest_config.md)
* [Application配置](/docs/application_config.md)
###### 3.[混淆配置](/docs/confounding.md)

### RxCRB框架组件
* [数据库使用](/docs/db_use.md)
* [文件操作](/docs/file_operation.md)
* [网络]
	* [验证工具类](/docs/network.md)
	* [请求配置——OkRx]
		* [初始化配置](/docs/okrx_init.md)
		* [使用方式](/docs/okrx_use.md)
* [控件使用问题及注意事项]
	* [RoundedImageView 实现圆形、圆角矩形的注意事项](/docs/attention.md)
* [组件]
	* [视差列表控件——头部自定义布局,中间有tab分页,tab分页里有滚动列表如ListView ScrollView RecyclerView等](/docs/parallax_list.md)
	* [XRefreshRecyclerView[带刷新和加载]](/docs/xrecyclerview.md)
	* [FlowEditItemsLayout[流式]标签列表](/docs/tag_list.md)
	* [View任何视图下拉刷新与上拉加载控件](/docs/view_refresh_load.md)
	* [TabLayoutindicator选择指示器](/docs/tab_layout_indicator.md)
	* [竖直方向自定义流程视图,类似物流进度](/docs/vertical_flow_track.md)
	* [图片(预览)选择编辑控件](/docs/picture_select_editor.md)
	* [图片(文件)上传](/docs/file_upload.md)