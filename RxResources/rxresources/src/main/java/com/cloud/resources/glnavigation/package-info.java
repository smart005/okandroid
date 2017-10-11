/**
 * @Title: package-info.java
 * @Description: 分组或字母导航
 * @author: lijinghuan
 * @data: 2014-12-27 下午12:58:18
 * <p/>
 * layout view:
 * <RelativeLayout
 * android:layout_width="match_parent"
 * android:layout_height="match_parent" >
 * <p/>
 * <ListView
 * android:id="@+id/listView"
 * android:layout_width="fill_parent"
 * android:layout_height="fill_parent" />
 * <p/>
 * viewOverlay布局可略
 * <LinearLayout
 * android:id="@+id/viewOverlay"
 * android:layout_width="fill_parent"
 * android:layout_height="wrap_content" >
 * <p/>
 * <TextView
 * android:id="@+id/tvOverlay"
 * android:layout_width="fill_parent"
 * android:layout_height="45dp"
 * android:background="#CCBCC5"
 * android:clickable="true"
 * android:gravity="center_vertical"
 * android:paddingLeft="15dp"
 * android:text="A"
 * android:textColor="#3399ff"
 * android:textSize="28sp" />
 * </LinearLayout>
 * <p/>
 * <com.tnt.decoration.common.glnavigation.LetterView
 * android:id="@+id/letterView"
 * android:layout_width="45dp"
 * android:layout_height="match_parent"
 * android:layout_alignParentRight="true" />
 * </RelativeLayout>
 * list item layout:
 * <?xml version="1.0" encoding="utf-8"?>
 * <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:orientation="vertical" >
 * <p/>
 * <TextView
 * android:id="@+id/tvLetter"
 * android:layout_width="fill_parent"
 * android:layout_height="45dp"
 * android:paddingLeft="15dp"
 * android:background="#CCBCC5"
 * android:text="A"
 * android:textColor="#3399ff"
 * android:textSize="28sp"
 * android:gravity="center_vertical"
 * android:clickable="true"
 * />
 * <p/>
 * <TextView
 * android:id="@+id/tvCompanyName_item"
 * android:layout_width="fill_parent"
 * android:layout_height="60dp"
 * android:gravity="center_vertical"
 * android:paddingLeft="20dp"
 * android:singleLine="true"
 * android:text="申通快递"
 * android:textColor="#000000"
 * android:textSize="20sp" />
 * <p/>
 * </LinearLayout>
 * one eg.:数据源为Cursor类型
 * ①private GLNavigation<Cursor> glncursor = new GLNavigation<Cursor>() {
 * @Override protected View AbsLVGetviewListener(int position, int sectionIndex,
 * View convertView, ViewGroup parent, Cursor cursor) {
 * .
 * .
 * .
 * if (position == sectionIndex) {
 * holder.tvLetter.setVisibility(View.VISIBLE);
 * holder.tvLetter.setText(当前显示的分类名称);
 * } else {
 * holder.tvLetter.setVisibility(View.GONE);
 * }
 * return convertView;
 * }
 * };
 * ②[可略]glncursor.setSubjectOverlayView((ViewGroup) findViewById(R.id.viewOverlay));
 * ③glncursor.instance(letterView, listView, mcursor,分类列名);
 * <p/>
 * two eg.:数据源为List<?>类型
 * ①private GLNavigation<List<T>> gln = new GLNavigation<List<T>>() {
 * @Override protected View AbsLVGetviewListener(int position, int sectionIndex,
 * View convertView, ViewGroup parent, List<T> dataobj) {
 * .
 * .
 * .
 * if (position == sectionIndex) {
 * holder.tvLetter.setVisibility(View.VISIBLE);
 * holder.tvLetter.setText(当前显示的分类名称);
 * } else {
 * holder.tvLetter.setVisibility(View.GONE);
 * }
 * return convertView;
 * }
 * };
 * ②[可略]gln.setSubjectOverlayView((ViewGroup) findViewById(R.id.viewOverlay));
 * ③gln.instance(letterView, listView, datalist, 分类列名);
 * <p/>
 * <p/>
 * <core.frong.resources.glnavigation.PinnedSectionListView
 * android:id="@+id/payment_detail_lv"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:divider="@color/payment_detail_item_split_line_color"
 * android:dividerHeight="1dp"
 * android:scrollbars="none" >
 * </core.frong.resources.glnavigation.PinnedSectionListView>
 * mpaymentdetaillv = (PinnedSectionListView) findViewById(R.id.payment_detail_lv);
 * mgnavigation.instance(mpaymentdetaillv, datalist);
 * mgnavigation.getCurrAdapter().notifyDataSetChanged();
 * //设置字母列表视图
 * mgnavigation.setLetterView(gpLetterLv);
 * //设置分组名称(与setLetterView配合使用)
 * mgnavigation.setGroupColumnName("groupName");
 * private GroupNavigation<PaymentDetailsItem> mgnavigation = new GroupNavigation<PaymentDetailsItem>() {
 * @Override public View getItemView(int position, View convertView,
 * PaymentDetailsItem item) {
 * // 构造项视图
 * if (item.getItemType() == getGroupType()) {
 * //组
 * } else {
 * //项
 * }
 * return convertView;
 * }
 * };
 * <p/>
 * layout view:
 * <RelativeLayout
 * android:layout_width="match_parent"
 * android:layout_height="match_parent" >
 * <p/>
 * <ListView
 * android:id="@+id/listView"
 * android:layout_width="fill_parent"
 * android:layout_height="fill_parent" />
 * <p/>
 * viewOverlay布局可略
 * <LinearLayout
 * android:id="@+id/viewOverlay"
 * android:layout_width="fill_parent"
 * android:layout_height="wrap_content" >
 * <p/>
 * <TextView
 * android:id="@+id/tvOverlay"
 * android:layout_width="fill_parent"
 * android:layout_height="45dp"
 * android:background="#CCBCC5"
 * android:clickable="true"
 * android:gravity="center_vertical"
 * android:paddingLeft="15dp"
 * android:text="A"
 * android:textColor="#3399ff"
 * android:textSize="28sp" />
 * </LinearLayout>
 * <p/>
 * <com.tnt.decoration.common.glnavigation.LetterView
 * android:id="@+id/letterView"
 * android:layout_width="45dp"
 * android:layout_height="match_parent"
 * android:layout_alignParentRight="true" />
 * </RelativeLayout>
 * list item layout:
 * <?xml version="1.0" encoding="utf-8"?>
 * <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
 * android:layout_width="match_parent"
 * android:layout_height="match_parent"
 * android:orientation="vertical" >
 * <p/>
 * <TextView
 * android:id="@+id/tvLetter"
 * android:layout_width="fill_parent"
 * android:layout_height="45dp"
 * android:paddingLeft="15dp"
 * android:background="#CCBCC5"
 * android:text="A"
 * android:textColor="#3399ff"
 * android:textSize="28sp"
 * android:gravity="center_vertical"
 * android:clickable="true"
 * />
 * <p/>
 * <TextView
 * android:id="@+id/tvCompanyName_item"
 * android:layout_width="fill_parent"
 * android:layout_height="60dp"
 * android:gravity="center_vertical"
 * android:paddingLeft="20dp"
 * android:singleLine="true"
 * android:text="申通快递"
 * android:textColor="#000000"
 * android:textSize="20sp" />
 * <p/>
 * </LinearLayout>
 * one eg.:数据源为Cursor类型
 * ①private GLNavigation<Cursor> glncursor = new GLNavigation<Cursor>() {
 * @Override protected View AbsLVGetviewListener(int position, int sectionIndex,
 * View convertView, ViewGroup parent, Cursor cursor) {
 * .
 * .
 * .
 * if (position == sectionIndex) {
 * holder.tvLetter.setVisibility(View.VISIBLE);
 * holder.tvLetter.setText(当前显示的分类名称);
 * } else {
 * holder.tvLetter.setVisibility(View.GONE);
 * }
 * return convertView;
 * }
 * };
 * ②[可略]glncursor.setSubjectOverlayView((ViewGroup) findViewById(R.id.viewOverlay));
 * ③glncursor.instance(letterView, listView, mcursor,分类列名);
 * <p/>
 * two eg.:数据源为List<?>类型
 * ①private GLNavigation<List<T>> gln = new GLNavigation<List<T>>() {
 * @Override protected View AbsLVGetviewListener(int position, int sectionIndex,
 * View convertView, ViewGroup parent, List<T> dataobj) {
 * .
 * .
 * .
 * if (position == sectionIndex) {
 * holder.tvLetter.setVisibility(View.VISIBLE);
 * holder.tvLetter.setText(当前显示的分类名称);
 * } else {
 * holder.tvLetter.setVisibility(View.GONE);
 * }
 * return convertView;
 * }
 * };
 * ②[可略]gln.setSubjectOverlayView((ViewGroup) findViewById(R.id.viewOverlay));
 * ③gln.instance(letterView, listView, datalist, 分类列名);
 * <p/>
 * <p/>
 * <core.frong.resources.glnavigation.PinnedSectionListView
 * android:id="@+id/payment_detail_lv"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:divider="@color/payment_detail_item_split_line_color"
 * android:dividerHeight="1dp"
 * android:scrollbars="none" >
 * </core.frong.resources.glnavigation.PinnedSectionListView>
 * mpaymentdetaillv = (PinnedSectionListView) findViewById(R.id.payment_detail_lv);
 * mgnavigation.instance(mpaymentdetaillv, datalist);
 * mgnavigation.getCurrAdapter().notifyDataSetChanged();
 * //设置字母列表视图
 * mgnavigation.setLetterView(gpLetterLv);
 * //设置分组名称(与setLetterView配合使用)
 * mgnavigation.setGroupColumnName("groupName");
 * //更新分组索引(与setLetterView配合使用)
 * mgnavigation.updateGroupIndex();
 * private GroupNavigation<PaymentDetailsItem> mgnavigation = new GroupNavigation<PaymentDetailsItem>() {
 * @Override public View getItemView(int position, View convertView,
 * PaymentDetailsItem item) {
 * // 构造项视图
 * if (item.getItemType() == getGroupType()) {
 * //组
 * } else {
 * //项
 * }
 * return convertView;
 * }
 * };
 */
/**
 *layout view:
 *		  <RelativeLayout
 *        android:layout_width="match_parent"
 *        android:layout_height="match_parent" >
 *
 *        <ListView
 *            android:id="@+id/listView"
 *            android:layout_width="fill_parent"
 *            android:layout_height="fill_parent" />
 *
 *		  viewOverlay布局可略
 *        <LinearLayout
 *            android:id="@+id/viewOverlay"
 *            android:layout_width="fill_parent"
 *            android:layout_height="wrap_content" >
 *
 *            <TextView
 *                android:id="@+id/tvOverlay"
 *                android:layout_width="fill_parent"
 *                android:layout_height="45dp"
 *                android:background="#CCBCC5"
 *                android:clickable="true"
 *                android:gravity="center_vertical"
 *                android:paddingLeft="15dp"
 *                android:text="A"
 *                android:textColor="#3399ff"
 *                android:textSize="28sp" />
 *        </LinearLayout>
 *
 *        <com.tnt.decoration.common.glnavigation.LetterView
 *            android:id="@+id/letterView"
 *            android:layout_width="45dp"
 *            android:layout_height="match_parent"
 *            android:layout_alignParentRight="true" />
 *    	</RelativeLayout>
 *list item layout:
 *		<?xml version="1.0" encoding="utf-8"?>
 *		<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
 *	    android:layout_width="match_parent"
 *	    android:layout_height="match_parent"
 *	    android:orientation="vertical" >
 *
 *	     <TextView 
 *	        android:id="@+id/tvLetter"
 *	        android:layout_width="fill_parent"
 *	        android:layout_height="45dp"
 *	        android:paddingLeft="15dp"
 *	        android:background="#CCBCC5"
 *	        android:text="A"
 *	        android:textColor="#3399ff"
 *	        android:textSize="28sp"
 *	        android:gravity="center_vertical"
 *	        android:clickable="true"
 *	        />
 *
 *	    <TextView
 *	        android:id="@+id/tvCompanyName_item"
 *	        android:layout_width="fill_parent"
 *	        android:layout_height="60dp"
 *	        android:gravity="center_vertical"
 *	        android:paddingLeft="20dp"
 *	        android:singleLine="true"
 *	        android:text="申通快递"
 *	        android:textColor="#000000"
 *	        android:textSize="20sp" />
 *
 *		</LinearLayout>
 *	one eg.:数据源为Cursor类型
 *	①private GLNavigation<Cursor> glncursor = new GLNavigation<Cursor>() {
 *        @Override
 *		protected View AbsLVGetviewListener(int position, int sectionIndex,
 *				View convertView, ViewGroup parent, Cursor cursor) {
 *				.
 *				.
 *				.
 *			if (position == sectionIndex) {
 *				holder.tvLetter.setVisibility(View.VISIBLE);
 *				holder.tvLetter.setText(当前显示的分类名称);
 *			} else {
 *				holder.tvLetter.setVisibility(View.GONE);
 *			}
 *			return convertView;
 *		}
 *	};
 *	②[可略]glncursor.setSubjectOverlayView((ViewGroup) findViewById(R.id.viewOverlay));
 *	③glncursor.instance(letterView, listView, mcursor,分类列名);
 *
 *	two eg.:数据源为List<?>类型
 *	①private GLNavigation<List<T>> gln = new GLNavigation<List<T>>() {
 *		@Override
 *		protected View AbsLVGetviewListener(int position, int sectionIndex,
 *				View convertView, ViewGroup parent, List<T> dataobj) {
 *			.
 *			.
 *			.
 *			if (position == sectionIndex) {
 *				holder.tvLetter.setVisibility(View.VISIBLE);
 *				holder.tvLetter.setText(当前显示的分类名称);
 *			} else {
 *				holder.tvLetter.setVisibility(View.GONE);
 *			}
 *			return convertView;
 *		}
 *	};
 *	②[可略]gln.setSubjectOverlayView((ViewGroup) findViewById(R.id.viewOverlay));
 *	③gln.instance(letterView, listView, datalist, 分类列名);
 *
 *
 *	<core.frong.resources.glnavigation.PinnedSectionListView
 *       android:id="@+id/payment_detail_lv"
 *       android:layout_width="match_parent"
 *       android:layout_height="wrap_content"
 *       android:divider="@color/payment_detail_item_split_line_color"
 *       android:dividerHeight="1dp"
 *       android:scrollbars="none" >
 *   </core.frong.resources.glnavigation.PinnedSectionListView>
 *   mpaymentdetaillv = (PinnedSectionListView) findViewById(R.id.payment_detail_lv);
 *	 mgnavigation.instance(mpaymentdetaillv, datalist);
 *	 mgnavigation.getCurrAdapter().notifyDataSetChanged();
 *	 //设置字母列表视图
 *	 mgnavigation.setLetterView(gpLetterLv);
 *	 //设置分组名称(与setLetterView配合使用)
 *	 mgnavigation.setGroupColumnName("groupName");
 *	 //更新分组索引(与setLetterView配合使用)
 *	 mgnavigation.updateGroupIndex();
 *	 private GroupNavigation<PaymentDetailsItem> mgnavigation = new GroupNavigation<PaymentDetailsItem>() {
 *		@Override
 *		public View getItemView(int position, View convertView,
 *				PaymentDetailsItem item) {
 *			// 构造项视图
 *			if (item.getItemType() == getGroupType()) {
 *				//组
 *			} else {
 *			    //项
 *			}
 *			return convertView;
 *		}
 *	};
 */
package com.cloud.resources.glnavigation;


