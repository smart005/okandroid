package com.cloud.resources.xlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

public class XRefreshView extends RelativeLayout {

	private XRefreshEmptyView mScrollView;
	private XRefreshListView mListView;

	public XRefreshView(Context context) {
		super(context);
		initView(context, null);
	}

	public XRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}

	public XRefreshView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs) {
		LayoutParams mainparam = new LayoutParams(
				LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		this.setLayoutParams(mainparam);
		mListView = new XRefreshListView(context, attrs);
		mListView.setOnScrollListener(listener);
		mScrollView = new XRefreshEmptyView(context, attrs);
		this.addView(mListView);
		this.addView(mScrollView);
		mListView.setVisibility(View.VISIBLE);
		mScrollView.setVisibility(View.INVISIBLE);
	}

	public void setEmptyDataView(View v) {
		if (v != null) {
			mScrollView.addContentView(v);
		}
	}

	private OnScrollListener listener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {

		}

		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			mListView.getTop();
			mListView.getFirstVisiblePosition();
		}
	};

	public XRefreshListView getRefreshListView() {
		return mListView;
	}

	public XRefreshEmptyView getRefreshScrollView() {
		return mScrollView;
	}

	public void showListView() {
		// mScrollView.stopRefresh();
		mListView.setVisibility(View.VISIBLE);
		mScrollView.setVisibility(View.INVISIBLE);
	}

	public void showScrollView() {
		// mListView.stopRefresh();
		mScrollView.setVisibility(View.VISIBLE);
		mListView.setVisibility(View.INVISIBLE);
	}

	public void setAutoPullDown(boolean auto) {
		showListView();
		mListView.setAutoPullDown(auto);
	}

	public void setPullLoadEnable(boolean enable) {
		mListView.setPullLoadEnable(enable);
	}

	public void setPullRefreshEnable(boolean enable) {
		mListView.setPullRefreshEnable(enable);
	}

	public void setEnableSliding(boolean mEnableSliding) {
		mListView.setEnableSliding(mEnableSliding);
	}

	public void setAdapter(ListAdapter adapter) {
		mListView.setAdapter(adapter);
	}

	public void setXListViewListener(OnXListViewListener l) {
		mListView.setXListViewListener(l);
		mScrollView.setXRefreshListener(l);
	}

	public void setSelectionFromTop() {
		mListView.setSelectionFromTop();
	}

	public void setOnXListViewItemClickListener(
			OnXListViewItemClickListener listener) {
		mListView.setOnXListViewItemClickListener(listener);
	}

	public void setOnXListViewScrollListener(OnXListViewScrollListener listener) {
		mListView.setOnXListViewScrollListener(listener);
	}
}
