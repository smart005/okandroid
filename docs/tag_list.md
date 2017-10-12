FlowEditItemsLayout[流式]标签列表
--------
效果图如下：

![image](https://github.com/smart005/okandroid/blob/master/docs/images/tag_list.jpg)

###### 1.布局
	<com.cloud.resources.flows.FlowEditItemsLayout
    android:id="@+id/comment_tag_feil"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="@dimen/spacing_10"
    android:layout_marginLeft="@dimen/spacing_10"
    android:layout_marginRight="@dimen/spacing_10"/>

###### 2.代码
	//设置监听
	commentTagFeil.setOnFlowLayoutListener(flowLayoutListener);
	private OnFlowLayoutListener flowLayoutListener = new OnFlowLayoutListener() {
        @Override
        public FlowLayoutInstance getFlowLayoutInstance(FlowLayoutInstance mfli) {
        	//是否启用选择
            mfli.setEnableCheck(true);
            //是否启用删除
            mfli.setEnableDelete(false);
            //是否启用编辑
            mfli.setEnableEdit(false);
            //是否启用单选
            mfli.setSingleChecked(true);
            //item左边距
            mfli.setFlowItemPaddingLeft(PixelUtils.dip2px(IssueCommentActivity.this, 8));
            //item右边距
            mfli.setFlowItemPaddingRight(PixelUtils.dip2px(IssueCommentActivity.this, 8));
            //item下边距
            //mfli.setFlowItemPaddingBottom();
            //item上边距
            //mfli.setFlowItemPaddingTop();
            //控件背影色
            mfli.setFlowBackground(R.color.transparent);
            //item背影
            mfli.setFlowItemBackground(R.drawable.tag_item_normal_bg);
            //选中项背影
            mfli.setSelectedItemBackground(R.drawable.tag_item_press_bg);
            //文本颜色
            mfli.setTitleTextColor(getResources().getColor(R.color.tag_item_normal_color));
            //选中项文本颜色
            mfli.setSelectedTitleTextColor(getResources().getColor(R.color.tag_item_press_color));
            //item左右内边距
            mfli.setOrSoSpacing(PixelUtils.dip2px(IssueCommentActivity.this, 8));
            //item上下内边距
            mfli.setUpAndDownSpacing(PixelUtils.dip2px(IssueCommentActivity.this, 8));
            return mfli;
        }

        @Override
        public void deleteItem(Object tag) {
        	//删除数据项时回调
        }

        @Override
        public void onItemClickListener(Object tag) {
        	//单击数据项时回调,其中tag为addItem()中的第二个参数
        }
    };
	//获取所有选中项
	commentTagFeil.getSelectedItems();
	//添加数据项
	commentTagFeil.addItem(String title,Object dataObj,boolean check);
