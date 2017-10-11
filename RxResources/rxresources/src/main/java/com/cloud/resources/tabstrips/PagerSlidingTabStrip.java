/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloud.resources.tabstrips;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PagerSlidingTabStrip extends HorizontalScrollView {

    public interface IconTabProvider {
        public int getPageIconResId(int position);
    }

    // @formatter:off
    private static final int[] ATTRS = new int[]{android.R.attr.textSize,
            android.R.attr.textColor};
    // @formatter:on

    private LinearLayout.LayoutParams defaultTabLayoutParams;
    private LinearLayout.LayoutParams expandedTabLayoutParams;

    private final PageListener pageListener = new PageListener();
    public OnPageChangeListener delegatePageListener;

    private LinearLayout tabsContainer;
    private ViewPager pager;

    private int currentPosition = 0;
    private float currentPositionOffset = 0f;

    private Paint rectPaint;
    private Paint dividerPaint;

    private int indicatorColor = 0xFF666666;
    private int underlineColor = 0x1A000000;
    private int dividerColor = 0x1A000000;

    private boolean shouldExpand = false;
    private boolean textAllCaps = true;

    private int scrollOffset = 52;
    private int indicatorHeight = 8;
    private int underlineHeight = 2;
    private int dividerPadding = 12;
    private int tabPadding = 24;
    private int dividerWidth = 1;

    private int tabTextSize = 12;
    private int tabTextColor = 0xFF666666;
    private int selectedTabTextColor = 0xFF45c01a;
    private Typeface tabTypeface = null;
    private int tabTypefaceStyle = Typeface.NORMAL;

    private int lastScrollX = 0;

    private Locale locale;
    private Context context;
    private int indicatorType = 0;
    private final int TAB_ITEM_TEXT_ID = 13073841;
    private final int TAB_ITEM_SUB_TEXT_ID = 685523082;
    private List<TabItem> tabItems = new ArrayList<TabItem>();
    private boolean isInited = false;

    private int subtitleTextSize = 12;
    private int cPaddingLeft = 8;
    private int cPaddingRight = 8;

    public PagerSlidingTabStrip(Context context) {
        this(context, null);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingTabStrip(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        try {
            this.context = context;
            setFillViewport(true);
            setWillNotDraw(false);
            tabsContainer = new LinearLayout(context);
            tabsContainer.setOrientation(LinearLayout.HORIZONTAL);
            tabsContainer.setGravity(Gravity.CENTER_VERTICAL);
            tabsContainer.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            addView(tabsContainer);
            DisplayMetrics dm = getResources().getDisplayMetrics();
            scrollOffset = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, scrollOffset, dm);
            indicatorHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, indicatorHeight, dm);
            underlineHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, underlineHeight, dm);
            dividerPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerPadding, dm);
            tabPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, tabPadding, dm);
            dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dividerWidth, dm);
            tabTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, tabTextSize, dm);
            TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
            tabTextSize = a.getDimensionPixelSize(0, tabTextSize);
            tabTextColor = a.getColor(1, tabTextColor);
            a.recycle();
            a = context.obtainStyledAttributes(attrs, R.styleable.PagerSlidingTabStrip);
            indicatorColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsIndicatorColor, indicatorColor);
            underlineColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsUnderlineColor, underlineColor);
            dividerColor = a.getColor(R.styleable.PagerSlidingTabStrip_pstsDividerColor, dividerColor);
            indicatorHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsIndicatorHeight, indicatorHeight);
            underlineHeight = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsUnderlineHeight, underlineHeight);
            dividerPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsDividerPadding, dividerPadding);
            tabPadding = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsTabPaddingLeftRight, tabPadding);
            shouldExpand = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsShouldExpand, shouldExpand);
            scrollOffset = a.getDimensionPixelSize(R.styleable.PagerSlidingTabStrip_pstsScrollOffset, scrollOffset);
            textAllCaps = a.getBoolean(R.styleable.PagerSlidingTabStrip_pstsTextAllCaps, textAllCaps);
            indicatorType = a.getInt(R.styleable.PagerSlidingTabStrip_pstsIndicatorType, indicatorType);
            a.recycle();
            rectPaint = new Paint();
            rectPaint.setAntiAlias(true);
            rectPaint.setStyle(Style.FILL);
            dividerPaint = new Paint();
            dividerPaint.setAntiAlias(true);
            dividerPaint.setStrokeWidth(dividerWidth);
            defaultTabLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            expandedTabLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
            if (locale == null) {
                locale = getResources().getConfiguration().locale;
            }
        } catch (Exception e) {
            Logger.L.error("pager sliding tab strip init error:", e);
        }
    }

    public void setTabItems(List<TabItem> tabItems) {
        try {
            if (ObjectJudge.isNullOrEmpty(tabItems)) {
                return;
            }
            for (TabItem tabItem : tabItems) {
                if (!TextUtils.isEmpty(tabItem.getName())) {
                    this.tabItems.add(tabItem);
                }
            }
            this.tabItems = tabItems;
        } catch (Exception e) {
            Logger.L.error("set tab items error:", e);
        }
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;
        if (pager == null || pager.getAdapter() == null) {
            return;
        }
        pager.setOnPageChangeListener(pageListener);
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {
        try {
            if (isInited) {
                return;
            }
            if (tabsContainer.getChildCount() > 0) {
                isInited = true;
            }
            for (int i = 0; i < tabItems.size(); i++) {
                if (pager.getAdapter() instanceof IconTabProvider) {
                    addIconTab(i, ((IconTabProvider) pager.getAdapter()).getPageIconResId(i));
                } else {
                    TabItem tabItem = tabItems.get(i);
                    addTextTab(i, tabItem.getName(), tabItem.getSubtitle());
                }
            }
            getViewTreeObserver().addOnGlobalLayoutListener(
                    new OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            currentPosition = pager.getCurrentItem();
                            scrollToChild(currentPosition, 0);
                            updateTabStyles();
                        }
                    });
            updateTabStyles();
        } catch (Exception e) {
            Logger.L.error("notify dataset error:", e);
        }
    }

    public void setCPaddingLeft(int paddingLeft) {
        this.cPaddingLeft = paddingLeft;
    }

    public void setCPaddingRight(int paddingRight) {
        this.cPaddingRight = paddingRight;
    }

    /**
     * 获取当前索引
     *
     * @return
     */
    public int getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * 设置padding left top right bottom
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    public void setPstsPadding(int left, int top, int right, int bottom) {
        try {
            if (tabsContainer.getChildCount() <= 0 || (left <= 0 && top <= 0 && right <= 0 && bottom <= 0)) {
                return;
            }
            int pleft = PixelUtils.dip2px(getContext(), cPaddingLeft);
            int pright = PixelUtils.dip2px(getContext(), cPaddingRight);
            int tabcount = tabsContainer.getChildCount();
            for (int i = 0; i < tabcount; i++) {
                FrameLayout frameLayout = (FrameLayout) tabsContainer.getChildAt(i);
                if (frameLayout == null) {
                    return;
                }
                int flcount = frameLayout.getChildCount();
                for (int j = 0; j < flcount; j++) {
                    View itemView = frameLayout.getChildAt(j);
                    if (itemView instanceof TabItemView) {
                        TabItemView tabItemView = (TabItemView) itemView;
                        if (i == 0) {
                            tabItemView.setPadding(pleft > left ? pleft : left, top, right, bottom);
                        } else if ((i + 1) == flcount) {
                            tabItemView.setPadding(left, top, pright > right ? pright : right, bottom);
                        } else {
                            tabItemView.setPadding(left, top, right, bottom);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("set psts padding error:", e);
        }
    }

    /**
     * 更新tab item 视图
     *
     * @param position tab 索引
     * @param tabItem  tab 数据
     */
    public void updateTabItem(int position, TabItem tabItem) {
        try {
            if (tabsContainer.getChildCount() <= 0 || position < 0 || tabItem == null) {
                return;
            }
            FrameLayout frameLayout = (FrameLayout) tabsContainer.getChildAt(position);
            if (frameLayout == null) {
                return;
            }
            int flcount = frameLayout.getChildCount();
            for (int i = 0; i < flcount; i++) {
                View itemView = frameLayout.getChildAt(i);
                if (itemView instanceof TabItemView) {
                    TabItemView tabItemView = (TabItemView) itemView;
                    TextView contentView = (TextView) tabItemView.findViewById(TAB_ITEM_TEXT_ID);
                    contentView.setText(tabItem.getName());
                    TextView subContentView = (TextView) tabItemView.findViewById(TAB_ITEM_SUB_TEXT_ID);
                    subContentView.setText(tabItem.getSubtitle());
                    RelativeLayout.LayoutParams tvparam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                    tvparam.addRule(RelativeLayout.BELOW, TAB_ITEM_TEXT_ID);
                    tvparam.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    tvparam.setMargins(0, 1, 0, 0);
                    subContentView.setLayoutParams(tvparam);
                }
            }
        } catch (Exception e) {
            Logger.L.error("update tab item view error:", e);
        }
    }

    private class TabItemView extends RelativeLayout {
        public TabItemView(Context context, CharSequence title, CharSequence subtitle) {
            super(context);
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getHeight());
            this.setLayoutParams(vgparam);
            this.addView(createText(title));
            this.addView(createSubText(subtitle));
            this.setGravity(Gravity.CENTER_VERTICAL);
            this.setPadding(0, PixelUtils.dip2px(getContext(), 8), 0, PixelUtils.dip2px(getContext(), 8));
        }

        private TextView createText(CharSequence title) {
            LayoutParams tvparam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            tvparam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(tvparam);
            tv.setId(TAB_ITEM_TEXT_ID);
            tv.setText(title);
            tv.setGravity(Gravity.CENTER);
            tv.setSingleLine();
            return tv;
        }

        private TextView createSubText(CharSequence subtitle) {
            LayoutParams tvparam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            tvparam.addRule(RelativeLayout.BELOW, TAB_ITEM_TEXT_ID);
            tvparam.addRule(RelativeLayout.CENTER_HORIZONTAL);
            if (!TextUtils.isEmpty(subtitle)) {
                tvparam.setMargins(0, 1, 0, 0);
            }
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(tvparam);
            tv.setId(TAB_ITEM_SUB_TEXT_ID);
            tv.setText(subtitle);
            tv.setGravity(Gravity.CENTER);
            tv.setSingleLine();
            return tv;
        }
    }

    private void addTextTab(int position, CharSequence title, CharSequence subtitle) {
        TabItemView tab = new TabItemView(getContext(), title, subtitle);
        TabItemView tab2 = new TabItemView(getContext(), title, subtitle);
        addTab(position, tab, tab2);
    }

    private void addIconTab(final int position, int resId) {
        ImageButton tab = new ImageButton(getContext());
        tab.setImageResource(resId);
        addTab(position, tab, null);
    }

    private void addTab(final int position, View tab, View tab2) {
        try {
            tab.setFocusable(true);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFadeEnabled = false;
                    pager.setCurrentItem(position);
                }
            });
            tab.setPadding(tabPadding, 0, tabPadding, 0);
            tab2.setFocusable(true);
            tab2.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFadeEnabled = false;
                    pager.setCurrentItem(position);
                }
            });
            tab2.setPadding(tabPadding, 0, tabPadding, 0);
            FrameLayout framelayout = new FrameLayout(context);
            framelayout.addView(tab, 0, defaultTabLayoutParams);
            framelayout.addView(tab2, 1, defaultTabLayoutParams);
            tabsContainer.addView(framelayout, position, shouldExpand ? expandedTabLayoutParams : defaultTabLayoutParams);
            Map<String, View> map = new HashMap<String, View>();
//            ViewHelper.setAlpha(tab, 1);
            map.put("normal", tab);
            if (position == currentPosition) {
                ViewGroup vg = (ViewGroup) tab2;
                TextView tv = (TextView) vg.findViewById(TAB_ITEM_TEXT_ID);
                tv.setTextColor(getSelectedTextColor());
                TextView subtv = (TextView) vg.findViewById(TAB_ITEM_SUB_TEXT_ID);
                subtv.setTextColor(getSelectedTextColor());
            }
//            ViewHelper.setAlpha(tab2, 0);
            map.put("selected", tab2);
            tabViews.add(position, map);
        } catch (Exception e) {
            Logger.L.error("add tab item error:", e);
        }
    }

    private void updateTabStyles() {
        try {
            if (tabsContainer.getChildCount() <= 0) {
                return;
            }
            for (int i = 0; i < tabItems.size(); i++) {
                if (tabsContainer.getChildCount() < i) {
                    break;
                }
                FrameLayout frameLayout = (FrameLayout) tabsContainer.getChildAt(i);
                for (int j = 0; j < frameLayout.getChildCount(); j++) {
                    if (frameLayout.getChildCount() < j) {
                        continue;
                    }
                    ViewGroup container = (ViewGroup) frameLayout.getChildAt(j);
                    View v = container.findViewById(TAB_ITEM_TEXT_ID);
                    TextView subtv = (TextView) container.findViewById(TAB_ITEM_SUB_TEXT_ID);
                    if (v instanceof TextView) {
                        TextView tab = (TextView) v;
                        tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, tabTextSize);
                        tab.setTypeface(tabTypeface, tabTypefaceStyle);
                        tab.setTextColor(tabTextColor);
                        subtv.setTextSize(TypedValue.COMPLEX_UNIT_PX, subtitleTextSize);
                        subtv.setTypeface(tabTypeface, tabTypefaceStyle);
                        subtv.setTextColor(tabTextColor);
                        Map<String, View> map = tabViews.get(i);
                        if (map.containsKey("normal")) {
                            ViewHelper.setAlpha(map.get("normal"), 1);
                        }
                        if (map.containsKey("selected")) {
                            ViewHelper.setAlpha(map.get("selected"), 0);
                        }
//                        ViewHelper.setPivotX(frameLayout, frameLayout.getMeasuredWidth() * 0.5f);
//                        ViewHelper.setPivotY(frameLayout, frameLayout.getMeasuredHeight() * 0.5f);
//                        ViewHelper.setScaleX(frameLayout, 1f);
//                        ViewHelper.setScaleY(frameLayout, 1f);
                        if (textAllCaps) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                                tab.setAllCaps(true);
                            } else {
                                tab.setText(tab.getText().toString().toUpperCase(locale));
                            }
                        }
                        if (i == currentPosition) {
                            if (map.containsKey("normal")) {
                                ViewHelper.setAlpha(map.get("normal"), 1);
                            }
                            if (map.containsKey("selected")) {
                                ViewHelper.setAlpha(map.get("selected"), 0);
                            }
//                            ViewHelper.setPivotX(frameLayout, frameLayout.getMeasuredWidth() * 0.5f);
//                            ViewHelper.setPivotY(frameLayout, frameLayout.getMeasuredHeight() * 0.5f);
//                            ViewHelper.setScaleX(frameLayout, 1 + ZOOM_MAX);
//                            ViewHelper.setScaleY(frameLayout, 1 + ZOOM_MAX);
                            tab.setTextColor(selectedTabTextColor);
                            subtv.setTextColor(selectedTabTextColor);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("update tab style error:", e);
        }
    }

    private void scrollToChild(int position, int offset) {
        try {
            if (tabItems.size() == 0) {
                return;
            }
            if (tabsContainer.getChildCount() >= position) {
                int newScrollX = tabsContainer.getChildAt(position).getLeft() + offset;
                if (position > 0 || offset > 0) {
                    newScrollX -= scrollOffset;
                }
                if (newScrollX != lastScrollX) {
                    lastScrollX = newScrollX;
                    scrollTo(newScrollX, 0);
                }
            }
        } catch (Exception e) {
            Logger.L.error("scroll process error:", e);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            if (isInEditMode() || tabItems.size() == 0) {
                return;
            }
            final int height = getHeight();
            rectPaint.setColor(underlineColor);
            canvas.drawRect(0, height - underlineHeight, tabsContainer.getWidth(), height, rectPaint);
            rectPaint.setColor(indicatorColor);
            if (tabsContainer.getChildCount() > currentPosition) {
                ViewGroup currentTab = (ViewGroup) tabsContainer.getChildAt(currentPosition);
                float lineLeft = currentTab.getLeft();
                float lineRight = currentTab.getRight();
                float lineTop = currentTab.getTop();
                float lineButtom = currentTab.getBottom();
                String title = "";
                if (indicatorType == 1) {
                    TextView textView = (TextView) currentTab.findViewById(TAB_ITEM_TEXT_ID);
                    title = textView.getText().toString();
                    int contentWidth = (this.getTextSize() + 4) * (title.length() + 1);
                    int contentHeight = (this.getTextSize() + 4);
                    int lineWidth = currentTab.getWidth();
                    int lineHeight = currentTab.getHeight();
                    lineLeft = lineLeft + (lineWidth - contentWidth) / 2;
                    lineRight = lineLeft + contentWidth;
                    lineTop = (lineHeight - contentHeight) / 2;
                    lineButtom = lineTop + contentHeight;
                }
                if (currentPositionOffset > 0f && currentPosition < tabItems.size() - 1) {
                    View nextTab = tabsContainer.getChildAt(currentPosition + 1);
                    final float nextTabLeft = nextTab.getLeft();
                    final float nextTabRight = nextTab.getRight();
                    lineLeft = (currentPositionOffset * nextTabLeft + (1f - currentPositionOffset) * lineLeft);
                    lineRight = (currentPositionOffset * nextTabRight + (1f - currentPositionOffset) * lineRight);
                }
                if (indicatorType == 1) {
                    RectF r1 = new RectF();
                    r1.left = lineLeft - 10;
                    r1.top = lineTop - 12;
                    r1.right = lineRight + 10;
                    r1.bottom = lineButtom + 12;
                    canvas.drawRoundRect(r1, 16, 16, rectPaint);
                } else {
                    canvas.drawRect(lineLeft, height - indicatorHeight, lineRight, height, rectPaint);
                }
                dividerPaint.setColor(dividerColor);
                for (int i = 0; i < tabItems.size() - 1; i++) {
                    View tab = tabsContainer.getChildAt(i);
                    canvas.drawLine(tab.getRight(), dividerPadding, tab.getRight(), height - dividerPadding, dividerPaint);
                }
            }
        } catch (Exception e) {
            Logger.L.error("pager sliding tab on draw error:", e);
        }
    }

    private class PageListener implements OnPageChangeListener {
        private int oldPosition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            try {
                currentPosition = position;
                currentPositionOffset = positionOffset;
                if (tabsContainer.getChildCount() >= position) {
                    scrollToChild(position, (int) (positionOffset * tabsContainer.getChildAt(position).getWidth()));
                }
                if (delegatePageListener != null) {
                    delegatePageListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
                invalidate();
                if (mState == State.IDLE && positionOffset > 0) {
                    oldPage = pager.getCurrentItem();
                    mState = position == oldPage ? State.GOING_RIGHT : State.GOING_LEFT;
                }
                boolean goingRight = position == oldPage;
                if (mState == State.GOING_RIGHT && !goingRight) {
                    mState = State.GOING_LEFT;
                } else if (mState == State.GOING_LEFT && goingRight) {
                    mState = State.GOING_RIGHT;
                }
                float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;
                if (effectOffset == 0) {
                    mState = State.IDLE;
                }
                if (tabsContainer.getChildCount() > position) {
                    View mLeft = tabsContainer.getChildAt(position);
                    View mRight = tabsContainer.getChildAt(position + 1);
                    if (mFadeEnabled) {
                        animateFadeScale(mLeft, mRight, effectOffset, position);
                    }
                }
            } catch (Exception e) {
                Logger.L.error("tab pager scrolled process error:", e);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                scrollToChild(pager.getCurrentItem(), 0);
                mFadeEnabled = true;
            }
            if (delegatePageListener != null) {
                delegatePageListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            try {
                currentPosition = position;
                Map<String, View> map = tabViews.get(oldPosition);
                if (map.containsKey("normal")) {
                    ViewHelper.setAlpha(map.get("normal"), 1);
                }
                if (map.containsKey("selected")) {
                    ViewHelper.setAlpha(map.get("selected"), 0);
                }
                View v_old = tabsContainer.getChildAt(oldPosition);
                ViewHelper.setPivotX(v_old, v_old.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(v_old, v_old.getMeasuredHeight() * 0.5f);
                ViewHelper.setScaleX(v_old, 1f);
                ViewHelper.setScaleY(v_old, 1f);
                Map<String, View> map2 = tabViews.get(position);
                if (map2.containsKey("normal")) {
                    ViewHelper.setAlpha(map2.get("normal"), 0);
                }
                if (map2.containsKey("selected")) {
                    ViewHelper.setAlpha(map2.get("selected"), 1);
                }
                View v_new = tabsContainer.getChildAt(position);
                ViewHelper.setPivotX(v_new, v_new.getMeasuredWidth() * 0.5f);
                ViewHelper.setPivotY(v_new, v_new.getMeasuredHeight() * 0.5f);
                ViewHelper.setScaleX(v_new, 1 + ZOOM_MAX);
                ViewHelper.setScaleY(v_new, 1 + ZOOM_MAX);
                if (delegatePageListener != null) {
                    delegatePageListener.onPageSelected(position);
                }
                oldPosition = currentPosition;
                for (int i = 0; i < tabViews.size(); i++) {
                    Map<String, View> item = tabViews.get(i);
                    if (i == currentPosition) {
                        ViewGroup vg = (ViewGroup) item.get("selected");
                        TextView tvitem = (TextView) vg.findViewById(TAB_ITEM_TEXT_ID);
                        tvitem.setTextColor(getSelectedTextColor());
                        TextView subtvitem = (TextView) vg.findViewById(TAB_ITEM_SUB_TEXT_ID);
                        subtvitem.setTextColor(getSelectedTextColor());
                    } else {
                        ViewGroup vg = (ViewGroup) item.get("normal");
                        TextView tvitem = (TextView) vg.findViewById(TAB_ITEM_TEXT_ID);
                        tvitem.setTextColor(getTextColor());
                        TextView subtvitem = (TextView) vg.findViewById(TAB_ITEM_SUB_TEXT_ID);
                        subtvitem.setTextColor(getTextColor());
                    }
                }
            } catch (Exception e) {
                Logger.L.error("tab pager selected process error:", e);
            }
        }
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return indicatorHeight;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return underlineHeight;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return dividerPadding;
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return scrollOffset;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
    }

    public boolean getShouldExpand() {
        return shouldExpand;
    }

    public boolean isTextAllCaps() {
        return textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
    }

    public int getTextSize() {
        return tabTextSize;
    }

    public void setSubtitleTextSize(int subtitleTextSize) {
        this.subtitleTextSize = subtitleTextSize;
    }

    public int getSubtitleTextSize() {
        return this.subtitleTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
    }

    public int getTextColor() {
        return tabTextColor;
    }

    public void setSelectedTextColor(int textColor) {
        this.selectedTabTextColor = textColor;
    }

    public void setSelectedTextColorResource(int resId) {
        this.selectedTabTextColor = getResources().getColor(resId);
    }

    public int getSelectedTextColor() {
        return selectedTabTextColor;
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
    }

    public int getTabPaddingLeftRight() {
        return tabPadding;
    }

    public void setIndicatorType(IndicatorType indicatorType) {
        this.indicatorType = indicatorType.ordinal();
        invalidate();
    }

    public int getIndicatorType() {
        return this.indicatorType;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        currentPosition = savedState.currentPosition;
        requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.currentPosition = currentPosition;
        return savedState;
    }

    static class SavedState extends BaseSavedState {
        int currentPosition;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            currentPosition = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(currentPosition);
        }

        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    List<Map<String, View>> tabViews = new ArrayList<Map<String, View>>();
    private boolean mFadeEnabled = false;

    public void setFadeEnabled(boolean enabled) {
        mFadeEnabled = enabled;
    }

    public boolean getFadeEnabled() {
        return mFadeEnabled;
    }

    private boolean isSmall(float positionOffset) {
        return Math.abs(positionOffset) < 0.0001;
    }

    private static final float ZOOM_MAX = 0.3f;
    private State mState;

    private enum State {
        IDLE, GOING_LEFT, GOING_RIGHT
    }

    private int oldPage;

    protected void animateFadeScale(View left, View right, float positionOffset, int position) {
        try {
            if (mState != State.IDLE) {
                if (left != null) {
                    Map<String, View> map = tabViews.get(position + 1);
                    if (map.containsKey("normal")) {
                        ViewHelper.setAlpha(map.get("normal"), positionOffset);
                    }
                    if (map.containsKey("selected")) {
                        ViewHelper.setAlpha(map.get("selected"), 1 - positionOffset);
                    }
                    float mScale = 1 + ZOOM_MAX - ZOOM_MAX * positionOffset;
                    ViewHelper.setPivotX(left, left.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(left, left.getMeasuredHeight() * 0.5f);
                    ViewHelper.setScaleX(left, mScale);
                    ViewHelper.setScaleY(left, mScale);
                }
                if (right != null) {
                    Map<String, View> map = tabViews.get(position + 1);
                    if (map.containsKey("normal")) {
                        ViewHelper.setAlpha(map.get("normal"), 1 - positionOffset);
                    }
                    if (map.containsKey("selected")) {
                        ViewHelper.setAlpha(map.get("selected"), positionOffset);
                    }
                    float mScale = 1 + ZOOM_MAX * positionOffset;
                    ViewHelper.setPivotX(right, right.getMeasuredWidth() * 0.5f);
                    ViewHelper.setPivotY(right, right.getMeasuredHeight() * 0.5f);
                    ViewHelper.setScaleX(right, mScale);
                    ViewHelper.setScaleY(right, mScale);
                }
            }
        } catch (Exception e) {
            Logger.L.error("tab animate process error:", e);
        }
    }
}
