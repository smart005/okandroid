视差列表控件——头部自定义布局,中间有tab分页,tab分页里有滚动列表如ListView ScrollView RecyclerView等
------------
先来看个效果图吧


要使用此控件需经过以下几个步骤：
###### 1.添加控件布局
	<?xml version="1.0" encoding="utf-8"?>
	<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
	    android:layout_width="match_parent"
	    android:layout_height="match_parent"
	    android:background="@color/color_f6f6f6">

	    <com.cloud.resources.parallax.ParallaxTabListView
	        android:id="@+id/test_ptlv"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        android:background="@color/color_f6f6f6" />
	</RelativeLayout>

	public class ParallaxTest extends BaseFragmentActivity implements HostView {
		......
		@Override
    	protected void onCreate(Bundle bundle) {
    		init(bundle);
    	}

    	private void init(Bundle bundle) {
	        parallaxTest = this;
	        //设置默认显示的tab项
	        testPtlv.setDefTabPosition(0);
	        //保存页面bundle状态
	        testPtlv.setSaveInstanceState(bundle);
	        //设置头部内容与Tabs(分页控件)间的偏移量,一般指的是Tabs控件高度;
	        testPtlv.setTopContentOffset(PixelUtils.dip2px(this, 46));
	        //添加tab选项
	        final List<TagsItem> tagsItems = testPtlv.getTagsItems();
	        tagsItems.add(new TagsItem(1, "活动中心"));
	        tagsItems.add(new TagsItem(2, "专题"));
	        //创建每个tab内容
	        testPtlv.setOnParallaxViewContentFragmentListener(new OnParallaxViewContentFragmentListener() {
	            @Override
	            public Fragment onBuildItem(int position) {
	                ParallaxContentFragment contentFragment = ParallaxContentFragment.newInstance();
	                Bundle mb = new Bundle();
	                mb.putInt("POSITION", position);
	                mb.putInt("PLACEHODLE_HEIGHT", testPtlv.getTopContentHeight());
	                contentFragment.setArguments(mb);
	                return contentFragment;
	            }
	        });
	        testPtlv.setOnParallaxTabListViewListener(new OnParallaxTabListViewListener() {
	            @Override
	            public IPagerTitleView getTitleView(Context context, final int index, final ViewPager viewPager) {
	                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
	                colorTransitionPagerTitleView.setNormalColor(Color.parseColor("#262626"));
	                colorTransitionPagerTitleView.setSelectedColor(Color.parseColor("#f24949"));
	                colorTransitionPagerTitleView.setText(tagsItems.get(index).getName());
	                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
	                    @Override
	                    public void onClick(View view) {
	                        viewPager.setCurrentItem(index);
	                    }
	                });
	                return colorTransitionPagerTitleView;
	            }

	            @Override
	            public IPagerIndicator getIndicator(Context context) {
	                LinePagerIndicator indicator = new LinePagerIndicator(context);
	                indicator.setColors(Color.parseColor("#ef2e35"));
	                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
	                indicator.setLineHeight(0);
	                return indicator;
	            }
	        });
	        View inflate = View.inflate(this, R.layout.parallax_top_content_view, null);
	        RelativeLayout.LayoutParams vparam = new RelativeLayout.LayoutParams(
	                RelativeLayout.LayoutParams.MATCH_PARENT,
	                RelativeLayout.LayoutParams.WRAP_CONTENT
	        );
	        inflate.setLayoutParams(vparam);
	        testPtlv.setTopContentView(inflate);
	        testPtlv.initializa(getSupportFragmentManager(), bundle);
	    }
	}