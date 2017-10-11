/**
 * XRefreshListView  ListView的下拉刷新控件
 * 1.在视图中添加列表组件
 *  <com.tz.decoration.resources.xlistview.XRefreshListView
 *       android:id="@+id/pull_refresh_list"
 *       android:layout_width="match_parent"
 *       android:layout_height="match_parent" >
 *  </com.tz.decoration.resources.xlistview.XRefreshListView>
 *2.定义对象
 *	private XRefreshListView mlistview = null;
 *	mlistview = (XRefreshListView) findViewById(R.id.pull_refresh_list);
 *	//是否启用加载更多功能
 *	mlistview.setPullLoadEnable(false);
 *	//是否启用项侧边栏操作
 *	mlistview.setEnableSliding(false);
 *	//对于适配器参考以下示例
 *	mlistview.setAdapter(curradapter);
 *	//监听回调
 *	mlistview.setXListViewListener(mlistener);
 *	private class TestListAdapter extends BaseXListAdapter<THolder> {
 *
 *		@Override
 *		public XListInstanceEntity<THolder> getInstanceView() {
 *			XListInstanceEntity<THolder> xlie = new XListInstanceEntity<THolder>();
 *			xlie.setContext(上下文);
 *			xlie.setLayoutItemId(项布局id);
 *			xlie.setHolder(new THolder());
 *			xlie.setHolderWidth(侧边栏宽度);
 *			xlie.setEnableSliding(false);//是否启用边栏
 *			return xlie;
 *		}
 *
 *		@Override
 *		public void buildViewHolder(XLViewHolder holder, View convertView) {
 *			//对viewholder进行赋值
 *		}
 *
 *		@Override
 *		public List<OperatorItemEntity> getItemView(int position,
 *				XLViewHolder holder, SlidingSlideView slideView) {
 *			//DataItem item = datalist.get(position);
 *			//如果启用侧边栏就必须的设置item.setSlideView(slideView);
 *			//数据项赋值
 *			//DataItem数据需实体继承BaseDataItem或添加属性
 *			//private SlidingSlideView slideView;
 *			//
 *			//public SlidingSlideView getSlideView() {
 *			//	return slideView;
 *			//}
 *			//
 *			//public void setSlideView(SlidingSlideView slideView) {
 *			//	this.slideView = slideView;
 *			//}
 *			//如果启用侧边栏需构建List<OperatorItemEntity>并返回否则返回null
 *			return null;
 *		}
 *	}
 *	加载或刷新回调函数最后调用mlistview.initRL()方法
 *
 *
 *XRefreshScrollView   ScrollView的下拉刷新(用于显示没数据界面)
 *1.在视图中添加列表组件
 *  <com.tz.decoration.resources.xlistview.XRefreshScrollView
 *       android:id="@+id/pull_scrolview"
 *       android:layout_width="match_parent"
 *       android:layout_height="match_parent" >
 *  </com.tz.decoration.resources.xlistview.XRefreshScrollView>
 *2.定义对象
 *  private XRefreshListView mScrollView = null;
 *  mScrollView = (XRefreshListView) findViewById(R.id.pull_scrolview);
 *  //监听回调
 *  mScrollView.setXRefreshListener(new IXListViewListener());
 *  //设置ScrollView显示的内容示图
 *  View content = View.inflate(this, R.layout.nodata, null);
 *  mScrollView.addContentView(content);
 *  加载或刷新回调函数最后调用mScrollView.initRL()方法
 * 
 * 
 * 
 *XRefreshView  集成ListView和ScrollView的下拉刷新控件，根据请求数据是否为空来判断显示
 *1.在视图中添加列表组件
 *  <com.tz.decoration.resources.xlistview.XRefreshView
 *       android:id="@+id/refreshView"
 *       android:layout_width="match_parent"
 *       android:layout_height="match_parent" >
 *  </com.tz.decoration.resources.xlistview.XRefreshView>
 *2.定义对象
 *  private XRefreshView mRefreshView = null;
 *  mRefreshView = (XRefreshView) findViewById(R.id.refreshView);
 *  //当请求数据为空时，显示ScrollView，配置ScrollView
 *  //为ScrollView设置监听回调
 *  mRefreshView.getRefreshScrollView().setXRefreshListener(new CouponsListItemListener());
 *  //设置无数据界面
 *  mRefreshView.setContentView(nodata);
 *  //显示ScrollView，隐藏ListView
 *  mRefreshView.showScrollView();
 *  //加载或刷新回调函数
 *  mRefreshView.getRefreshScrollView().initRL();
 *  
 *  //当请求数据不为空时，显示ListView，配置ListView
 *  //是否启用加载更多功能
 *  mRefreshView.setPullLoadEnable(true);
 *  //是否启用项侧边栏操作
 *  mRefreshView.setEnableSliding(false);
 *  //对于适配器参考以下示例
 *  mRefreshView.setAdapter(curradapter);
 *  //为ListView设置监听回调
 *  mRefreshView.setXListViewListener(new CouponsListItemListener());
 *  //显示ScrollView，隐藏ListView
 *  mRefreshView.showListView();
 *  //设置是否第一次自动刷新数据
 *  mcouponsxrlv.setAutoPullDown(true);
 *  //加载或刷新回调函数
 *  mRefreshView.initRL();
 *
 *  
 *  PinnedSectionRefreshView 集成PinnedSectionRefreshListView和ScrollView的下拉刷新控件，根据请求数据是否为空来判断显示
 *  1.在视图中添加列表组件
 *  <com.tz.decoration.resources.xlistview.PinnedSectionRefreshView
 *       android:id="@+id/"
 *       android:layout_width="match_parent"
 *       android:layout_height="wrap_content"
 *       android:divider="@color/"
 *       android:dividerHeight="0.6dp"
 *       android:scrollbars="none" >
 *  </com.tz.decoration.resources.xlistview.PinnedSectionRefreshView>
 *  2.定义对象并设置属性
 *  private PinnedSectionRefreshView mpaymentdetaillv = null;
 *  mpaymentdetaillv = (PinnedSectionRefreshView) findViewById();
 *  mpaymentdetaillv.setContentView(无数据视图);
 *  //是否启用加载更多功能
 *	mpaymentdetaillv.setPullLoadEnable(true);
 *	//注册监听
 *	mpaymentdetaillv
 *			.setOnPinnedSectionRefreshViewListener(mpsrvlistener);
 *	mgnavigation.instance(mpaymentdetaillv.getListView(), datalist[数据列表实现须继承BasePinnedSectionItem]);
 *	
 *	private PinnedSectionRefreshViewListener mpsrvlistener = new PinnedSectionRefreshViewListener() {
 *		@Override
 *		public void onScrollListener(int position) {
 *
 *		} 
 *
 *		@Override
 *		public void onRefresh() {
 *			getCurrPageIndex();
 *			requestDataList(ListStateEnum.Refresh.getValue());
 *		}
 *
 *		@Override
 *		public void onLoadMore() {
 *			currpageindex++;
 *			requestDataList("");
 *		}
 *
 *		@Override
 *		public void onItemClick(AdapterView<?> parent, View view, int position,
 *				long id) {
 *
 *		}
 *	};
 *	private GroupNavigation<T extends BasePinnedSectionItem, TView extends PinnedSectionListView> mgnavigation = new GroupNavigation<T extends BasePinnedSectionItem, TView extends PinnedSectionListView>() {
 *		@Override
 *		public View getItemView(int position, View convertView,
 *				<T extends BasePinnedSectionItem> item) {
 *			try {
 *				if (item.getItemType() == getGroupType()) {
 *					//group deal with
 *				} else {
 *					//item deal with
 *				}
 *			} catch (Exception e) {
 *				
 *			}
 *			return convertView;
 *		}
 *	};
 *	3.请求数据完成后
 *	@Override
 *	public void onPaymentDetailsListSuccessful(
 *			List<T extends BasePinnedSectionItem> lst, String reqkey) {
 *		if (ObjectJudge.isNullOrEmpty(lst) && currpageindex == 0) {
 *			//显示无数据关联视图
 *			mpaymentdetaillv.showScrollView();
 *			//清空数据
 *			datalist.clear();
 *			//通知数据列表
 *			mgnavigation.getCurrAdapter().notifyDataSetChanged();
 *			//初始化列表
 *			mpaymentdetaillv.getListView().initRL();
 *			//初始化无数据关联的视图
 *			mpaymentdetaillv.getRefreshScrollView().initRL();
 *			return;
 *		} else {
 *			//显示无数据关联视图
 *			mpaymentdetaillv.showListView();
 *		}
 *		// 此处写逻辑处理
 *		// 判断是否需要清除列表
 *		if (TextUtils.equals(reqkey, ListStateEnum.Refresh.getValue())) {
 *			datalist.clear();
 *		}
 *		//添加新的数据集合
 *		datalist.addAll(mlst);
 *		//通知数据列表
 *		mgnavigation.getCurrAdapter().notifyDataSetChanged();
 *		//初始化列表
 *		mpaymentdetaillv.getListView().initRL();
 *	}
 */
/**
 * @author lijinghuan
 *
 */
package com.cloud.resources.xlistview;

class XRefreshBaseMode {
	public static final int MODE_PULL_DOWN_TO_REFRESH = 0x1;
	public static final int MODE_PULL_UP_TO_REFRESH = 0x2;
	public static final int MODE_BOTH = 0x3;
}

class XRefreshBaseSate {
	public static final int PULL_TO_REFRESH = 0x0;
	public static final int RELEASE_TO_REFRESH = 0x1;
	public static final int REFRESHING = 0x2;
	public static final int MANUAL_REFRESHING = 0x3;
}
