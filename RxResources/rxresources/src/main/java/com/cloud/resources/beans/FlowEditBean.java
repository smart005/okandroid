package com.cloud.resources.beans;

import android.view.View;
import android.widget.TextView;

/**
 * 
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-19 上午11:29:22
 * @Description:
 * @Modifier:
 * @ModifyContent:
 * 
 */
public class FlowEditBean {
	public FlowEditBean(String name, Object tag, View view) {
		setName(name);
		setTag(tag);
		setView(view);
	}
	
	public FlowEditBean(String name, Object tag, View view, TextView textview) {
		this(name, tag, view);
		setTextview(textview);
	}

	private String name;
	private View view;
	private TextView textview;

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TextView getTextview() {
		return textview;
	}

	public void setTextview(TextView textview) {
		this.textview = textview;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}

	private Object tag;
}
