竖直方向自定义流程视图,类似物流进度
-------

###### 简单模式视图
![images](/docs/images/ver_simple_flow_img.jpg)

	<com.cloud.resources.flowtrack.VerticalFlowTrackView
    android:id="@+id/friends_progress_vftv"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="@dimen/spacing_6"
    android:layout_marginLeft="@dimen/spacing_60"
    android:layout_marginRight="@dimen/spacing_15"
    //默认节点图片
    app:defNodeImageResourceId="@mipmap/progress_normal"
    //默认节点文本颜色
    app:defNodeTextColor="@color/color_999999"
    //默认节点文本大小
    app:defNodeTextSize="@dimen/font_size_14"
    //节点连接线颜色
    app:nodeCableLineColor="@color/color_e7effe"
    //节点大小
    app:nodeSize="@dimen/spacing_11"
    //两个节点之间距离
    app:nodeSpacing="@dimen/spacing_52"
    //节点与文本之间距离
    app:nodeTextSpacing="@dimen/spacing_18"
    //选中节点图片资源
    app:selNodeImageResourceId="@mipmap/progress_press"
    //选中节点文本大小
    app:selNodeTextSize="@dimen/font_size_14" />

    //传入数据集构建视图
    public void build(List<FlowTrackNode> list) {
    	...
    }

###### 自定义模式视图 
![images](/docs/images/ver_cus_flow_img.jpg)

	<com.cloud.resources.flowtrack.VerticalCusFlowTrackView
    android:id="@+id/test_vcft"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center_horizontal"
    android:visibility="gone"
    //默认节点图片资源
    app:vcft_defNodeImageResourceId="@mipmap/progress_normal"
    //默认节点左边布局
    app:vcft_defNodeLeftLayoutId=""
    //默认节点右边布局
    app:vcft_defNodeRightLayoutId=""
    //两个节点连接线颜色
    app:vcft_nodeCableLineColor="@color/color_e7effe"
    //节点与左边视图距离
    app:vcft_nodeLeftViewSpacing="@dimen/spacing_10"
    //节点与右边视图距离
    app:vcft_nodeRightViewSpacing="@dimen/spacing_10"
    //节点大小
    app:vcft_nodeSize="@dimen/spacing_11"
    //两个节点之间距离
    app:vcft_nodeSpacing="@dimen/spacing_52"
    //选中节点图片资源
    app:vcft_selNodeImageResourceId="@mipmap/progress_press"
    //选中节点左边布局
    app:vcft_selNodeLeftLayoutId=""
    //选中节点右边布局
    app:vcft_selNodeRightLayoutId="" />

    //item数据实体
    private class NodeTest extends FlowTrackNode {
    	...
    }

    //设置事件绑定数据项
    testVcft.setOnVerticalFlowTrackListener(new OnVerticalFlowTrackListener<NodeTest>() {
        @Override
        public void onBindItem(View leftView, View rightView, NodeTest node, int position) {

        }
    });

    //调用build方法构建视图
    public <T extends FlowTrackNode> void build(List<T> list) {
    	...
    }