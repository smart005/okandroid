/**
 * 1.布局
 * <com.rongxun.resources.tabstrips.PagerSlidingTabStrip
 * android:id="@+id/financial_circle_psts"
 * android:layout_width="match_parent"
 * android:layout_height="38dp"
 * android:background="@color/white_color"></com.rongxun.resources.tabstrips.PagerSlidingTabStrip>
 * <p/>
 * <android.support.v4.view.ViewPager
 * android:id="@+id/financial_circle_vp"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:background="@color/white_color" />
 * <p/>
 * 2.属性设置
 * curradapter = new FinancialPagerAdapter(getSupportFragmentManager());
 * financialCircleVp.setAdapter(curradapter);
 * financialCirclePsts.setViewPager(financialCircleVp);
 * // 设置Tab是自动填充满屏幕的
 * financialCirclePsts.setShouldExpand(true);
 * // 设置Tab的分割线是透明的
 * financialCirclePsts.setDividerColor(Color.TRANSPARENT);
 * // 设置Tab底部线的高度
 * financialCirclePsts.setUnderlineHeight(1);
 * financialCirclePsts.setUnderlineColor(R.color.split_line);
 * // 设置Tab Indicator的高度
 * financialCirclePsts.setIndicatorHeight(4);
 * // 设置Tab标题文字的大小
 * DisplayMetrics dm = ObjectManager.getDisplayMetrics(this);
 * financialCirclePsts.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
 * // 设置Tab Indicator的颜色
 * financialCirclePsts.setIndicatorColor(Color.parseColor("#ff0000"));
 * // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
 * financialCirclePsts.setSelectedTextColor(Color.parseColor("#f24949"));
 * //设置正常Tab文字的颜色 (这是我自定义的一个方法)
 * financialCirclePsts.setTextColor(Color.parseColor("#262626"));
 * // 取消点击Tab时的背景色
 * financialCirclePsts.setIndicatorType(IndicatorType.Shade);
 * financialCircleVp.setCurrentItem(0);
 * financialCirclePsts.setFadeEnabled(true);
 * financialCirclePsts.setTabItems(financialTabs);
 * financialCirclePsts.notifyDataSetChanged();
 */
package com.cloud.resources.tabstrips;