/**
 *  <core.frong.resources.flows.FlowEditItemsLayout
 *       android:id="@+id/sel_item_fl"
 *       android:layout_width="match_parent"
 *       android:layout_height="wrap_content" >
 *   </core.frong.resources.flows.FlowEditItemsLayout>
 *   
 *   private FlowEditItemsLayout mfleil = null;
 *   mfleil = (FlowEditItemsLayout) findViewById(R.id.sel_item_fl);
 *		mfleil.setOnFlowLayoutListener(new OnFlowLayoutListener() {
 *			@Override
 *			public FlowLayoutInstance getFlowLayoutInstance(
 *					FlowLayoutInstance mfli) {
 *				//组件背景
 *				mfli.setFlowBackground();
 *				//是否启用编辑
 *				mfli.setEnableEdit(false);
 *				//项背景
 *				mfli.setFlowItemBackground();
 *				//标题文本颜色
 *				mfli.setTitleTextColor();
 *				//是否启用删除
 *				mfli.setEnableDelete(false);
 *				//是否启用选择处理
 *				mfli.setEnableCheck(true);
 *				//是否启用单选
 *				mfli.setSingleChecked(true);
 *				//选中项背景
 *				mfli.setSelectedItemBackground();
 *				//左右间距
 *				mfli.setOrSoSpacing();
 *				//上下间距
 *				mfli.setUpAndDownSpacing();
 *				//左边距
 *				mfli.setFlowItemPaddingLeft();
 *				//上边距
 *				mfli.setFlowItemPaddingTop();
 *				//右边距
 *				mfli.setFlowItemPaddingRight();
 *				//下边距
 *				mfli.setFlowItemPaddingBottom();
 *				return mfli;
 *			}
 *
 *			@Override
 *			public void deleteItem(String tag) {
 *
 *			}
 *		});
 *
 *	mfleil.addItem("项文本", "项标记数据(如json)");
 *	mfleil.deleteItem(项视图);//删除项
 *	mfleil.deleteItem(项索引);//删除项
 *	mfleil.deleteItem();//删除最后一项
 *	mfleil.findItem(项索引);//查找项视图
 *	mfleil.getSelectedItems();//获取选中项
 */
/**
 * @author lijinghuan
 *
 */
package com.cloud.resources.flows;

