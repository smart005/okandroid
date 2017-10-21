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
	//获取新的配置属性(可动态改变后使用)
	commentTagFeil.getFlowConfig();
	//添加数据项
	commentTagFeil.addItem(String title, Object tagobj);
	commentTagFeil.addItem(String title,Object dataObj,boolean check);
	commentTagFeil.addItem(String title, Object tagobj, FlowLayoutInstance flowLayoutInstance, boolean check);
	//重置指定索引标签的属性
	commentTagFeil.resetItem(int position, FlowLayoutInstance flowLayoutInstance);

	//找标签项视图
	public View findItem(int position)
	//根据标签项视图删除标签
	public void deleteItem(View v)
	//获取所有标签项FlowEditBean
	public List<FlowEditBean> getAllItems()
	//删除所有标签项
	public void deleteItems()
	//根据索引删除标签
	public void deleteItem(int position)
	//删除最后一项
	public void deleteItem()
	//获取所有选中标签项FlowEditBean
	public List<FlowEditBean> getFlowEditBeanSelectedItems()
	//设置标签选中项
	public void setSelectedItems(List<String> selectedTitles)
	public void setSelectedItem(String selectedTitle)
	//清空所有标签选中项
	public void clearSelectItems()
	//获取所有标签选中项数据列表(addItem中的Object tagobj集合)
	public <T> List<T> getSelectedItems()

###### 3.另外还可以通过控件commentTagFeil来动态改变标签属性
	//是否启用选择
	public void setEnableCheck(boolean isEnableCheck)
	//是否启用删除
	public void setEnableDelete(boolean isEnableDelete)
	//是否启用编辑
	public void setEnableEdit(boolean isEnableEdit)
	//是否启用单选
	public void setSingleChecked(boolean isSingleChecked)
	//item左边距
	public void setFlowItemPaddingLeft(int flowItemPaddingLeft)
	//item右边距
	public void setFlowItemPaddingRight(int flowItemPaddingRight)
	//item下边距
	public void setFlowItemPaddingBottom(int flowItemPaddingBottom)
	//item上边距
	public void setFlowItemPaddingTop(int flowItemPaddingTop)
	//控件背影色
	public void setFlowBackground(int flowBackground)
	//item背影
	public void setFlowItemBackground(int flowItemBackground)
	//选中项背影
	public void setSelectedItemBackground(int selectedItemBackground)
	//文本颜色
	public void setTitleTextColor(int titleTextColor)
	//选中项文本颜色
	public void setSelectedTitleTextColor(int selectedTitleTextColor)
	//item左右内边距
	public void setOrSoSpacing(int orSoSpacing)
	//item上下内边距
	public void setUpAndDownSpacing(int upAndDownSpacing)

	//设置属性后需调用以下方法来重置所有标签项
	public void commentTagFeil.resetItems()