滑动加载或销毁视图
-----
```java
自定义可以滑动的RelativeLayout, 可以右滑退出activity ，左滑进入新的activity
内部组件可以是webview，listview,scrollview
```
###### 1.添加布局视图
```xml
<com.cloud.core.widgets.SildingLoadFinishView
    android:id="@+id/test_slfv"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    //添加子视图
</com.cloud.core.widgets.SildingLoadFinishView>
```
###### 2.设置监听事件
```java
testSlfv.setOnSildingFinishListener(new OnSildingFinishListener() {
	@Override
	public void onSildingFinish() {
		//右滑结束视图回调
	}

	@Override
	public void onLeftScroll() {
		//左滑加载视图回调
	}
});
```
*[好了对视图左滑、右滑的视图就这样简单的实现了.]*