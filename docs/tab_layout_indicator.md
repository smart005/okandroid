TabLayoutindicator选择指示器
--------
###### 1.简单指示器

![images](/docs/images/tab_layout_indicator_1.jpg)

	<com.cloud.resources.tabindicator.TabLayoutIndicator
    android:layout_width="match_parent"
    android:layout_height="@dimen/spacing_42"
    android:background="@color/color_ffffff"
    app:tabIndicatorHeight="@dimen/spacing_2"
    //默认指示器颜色
    app:tabIndicatorColor="@color/color_e7effe"
    //选择指示器颜色
    app:tabIndicatorSelectedColor="@color/color_4c5b71"
    //指示器之间的间距
    app:tabIndicatorSpacing="@dimen/spacing_6"
    //指示器类型:选择指示器
    app:tabIndicatorType="instructions"
    //指示器宽度
    app:tabIndicatorWidth="@dimen/spacing_8" />

    public void setTabIndicatorNumber(int tabIndicatorNumber) {
    	//设置指示器个数
    }

    public void setCurrentItem(int position) {
    	//设置当前指示器索引，即高亮显示的索引
    }

    public void build() {
    	//构建指示器
    }

###### 2.Tab类型指示器(屏幕等分)

![images](/docs/images/tab_layout_indicator.jpg)

	<com.cloud.resources.tabindicator.TabLayoutIndicator
    android:id="@+id/test_tli"
    android:layout_width="match_parent"
    android:layout_height="@dimen/spacing_42"
    android:background="@color/color_ffffff"
    //tab指示器颜色
    app:tabIndicatorColor="@color/color_ff0000"
    //内容布局类型
    app:tabIndicatorContentType="frameLayout"
    //tab指示器高度
    app:tabIndicatorHeight="@dimen/spacing_2"
    //指示器类型:Tab类型
    app:tabIndicatorType="options"
    //指示器宽度
    app:tabIndicatorWidth="@dimen/spacing_60"
    //文本是否加粗
    app:tabItemTextBlod="true"
    //文本颜色
    app:tabItemTextColor="@color/color_4c5b71"
    //文本选中颜色
    app:tabItemTextSelectedColor="@color/color_ff0000"
    //文本大小
    app:tabItemTextSize="@dimen/font_size_14"
    //控件背影颜色
    app:tabLayoutBackgroundColor="@color/color_ffffff"
    //Item分隔线颜色
    app:tabSplitLineColor="@color/color_eeeeee"
    //Item分隔线高度
    app:tabSplitLineHeight="@dimen/spacing_20" />

    testTli.setFragmentManager(getSupportFragmentManager());
    testTli.setViewPager(testVp);//testTli.setContentFrameLayoutId(R.id.order_content_fl);
    testTli.getTabItems().add(new TabItem("1", "Android"));
    testTli.getTabItems().add(new TabItem("2", "IOS"));
    testTli.getTabItems().add(new TabItem("3", "前端"));
    testTli.setOnTablayoutIndicatorListener(tablayoutIndicatorListener);
    testTli.build();

    private OnTablayoutIndicatorListener tablayoutIndicatorListener = new OnTablayoutIndicatorListener() {
        @Override
        public Fragment onBuildFragment(int position, TabItem tabItem, Bundle bundle) {
        	//fragment自定义实现
            //return TestFragment.newInstance();
        }
    };

    ==============================================
    <FrameLayout
    android:id="@+id/order_content_fl"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_below="@+id/info_bar_split_v"
    android:background="@color/color_ffffff" />
    或
    <android.support.v4.view.ViewPager
    android:id="@+id/order_content_vp"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_below="@+id/info_bar_split_v"
    android:background="@color/color_ffffff" />
