View任何视图下拉刷新与上拉加载控件
--------

![images](/docs/images/v_rl_refresh.jpg)

![images](/docs/images/v_rl_more.jpg)

###### 1.在布局文件中添加以下控件
	<com.cloud.resources.vrllayout.VRefreshLoadLayout
        android:id="@+id/rlm"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="center"
            android:text="测试"
            android:textColor="@color/color_323232" />
    </com.cloud.resources.vrllayout.VRefreshLoadLayout>

###### 2.代码部分
	//是否启用刷新
	rlm.canRefresh(true);
	//是否启用加载更多
    rlm.canLoadMore(true);
    //初始化控件
    rlm.initializa();
    rlm.setOnXListViewListener(new OnXListViewListener() {
        @Override
        public void onRefresh() {
        	//刷新回调
        }

        @Override
        public void onLoadMore() {
        	//加载更多回调
        }
    });