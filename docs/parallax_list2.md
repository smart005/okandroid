视差列表控件——头部自定义布局,中间有tab分页,tab分页里有滚动列表如ListView ScrollView RecyclerView等
------------
先来看个效果图吧

![image](/docs/images/parallax.gif)

该控件一般与[TabLayoutIndicator](/docs/tab_layout_indicator.md)控件一起使用按以下简单的几个步骤即可以完成：
###### 1.添加控件布局
```xml
<com.cloud.resources.hvlayout.HeaderTabsViewLayout
        android:id="@+id/discover_htvl"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_below="@id/head_hv"
        android:orientation="vertical">
        //添加子视图
</com.cloud.resources.hvlayout.HeaderTabsViewLayout>
注：
a).无论控件具有多少个子View,只有第一个子View会被自定义控件按头部解析，所以，
如果头部有多个View，可以使用ViewGroup包裹，例如下面实例使用的是LinearLayout包裹（头部是
一个ViewPager和Indicator指示器），除了第一个View会被滑出去外，其余布局均不会被滑出。
b).如果滑动时，想让布局滑动到一定距离后停止么可以在xml布局中加入自定义属性
app:htvp_topOffset="50dp"，值的大小表示距离顶部多少距离停止滑动
```
###### 2.当Fragment初始化完成或Tab切换时调用如下来进行滚动事件分发：
```java
discoverHtvl.setCurrentScrollableContainer((BaseFragment) fragment);
```
###### 3.另外每个Fragment在继承BaseFragment后，需要重载以下方法：
```java
@Override
public View getScrollableView() {
    //此处返回Fragment中任意的滚动视图
}
```
###### 4.如果需要对滑动过程进行监听，可以使用如下代码,currentY 表示当前滑过的距离，maxY表示当前可以滑动的最大距离，有了这两个参数，就可以对任意布局，做任何动画了。例如如下代码就是实现 视差动画效果的代码
```java
discoverHtvl.setOnScrollListener(new HeaderViewPagerLayout.OnScrollListener() {
    @Override
    public void onScroll(int currentY, int maxY) {
        image.setTranslationY(currentY / 2);
    }
});
```
*[简单吧，几步即可达到你想要的效果!]*