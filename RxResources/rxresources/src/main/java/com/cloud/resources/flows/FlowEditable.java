package com.cloud.resources.flows;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloud.core.utils.PixelUtils;

import com.cloud.resources.R;
import com.cloud.resources.beans.FlowLayoutInstance;


/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-19 上午11:30:44
 * @Description:数据项
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class FlowEditable {

	private Context context;
	private View itemView = null;
	private TextView titleView = null;
	private LinearLayout deleteView = null;
	private FlowLayoutInstance mfli = new FlowLayoutInstance();

	public FlowEditable(Context context, FlowLayoutInstance mfli) {
		this.context = context;
		this.mfli = mfli;
		init();
	}

	private void init() {
		itemView = View.inflate(context, R.layout.flow_item_editable, null);
		itemView.setPadding(
				0,
				mfli.getUpAndDownSpacing() > 0 ? mfli.getUpAndDownSpacing() : 0,
				mfli.getOrSoSpacing() > 0 ? mfli.getOrSoSpacing() : 0, 0);
		View contentview = itemView.findViewById(R.id.flow_edit_content_rl);
		int orsosp = PixelUtils.dip2px(context, 5);
		int udsp = PixelUtils.dip2px(context, 3);
		int left = mfli.getFlowItemPaddingLeft() > 0 ? PixelUtils.dip2px(
				context, mfli.getFlowItemPaddingLeft()) : orsosp;
		int top = mfli.getFlowItemPaddingTop() > 0 ? PixelUtils.dip2px(context,
				mfli.getFlowItemPaddingTop()) : udsp;
		int right = mfli.getFlowItemPaddingRight() > 0 ? PixelUtils.dip2px(
				context, mfli.getFlowItemPaddingRight()) : orsosp;
		int bottom = mfli.getFlowItemPaddingBottom() > 0 ? PixelUtils.dip2px(
				context, mfli.getFlowItemPaddingBottom()) : udsp;
		contentview.setPadding(left, top, right, bottom);
		contentview.setBackgroundResource(mfli.getFlowItemBackground());
		titleView = (TextView) contentview.findViewById(R.id.flow_title);
		titleView.setTextColor(mfli.getTitleTextColor());
		deleteView = (LinearLayout) contentview.findViewById(R.id.flow_delete);
	}

	/**
	 * @return 获取itemView
	 */
	public View getItemView() {
		return itemView;
	}

	/**
	 * @return 获取titleView
	 */
	public TextView getTitleView() {
		return titleView;
	}

	/**
	 * @return 获取deleteView
	 */
	public LinearLayout getDeleteView() {
		return deleteView;
	}
}
