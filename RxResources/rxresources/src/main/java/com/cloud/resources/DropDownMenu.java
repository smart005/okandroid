package com.cloud.resources;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.PixelUtils;


/**
 *
 */
public class DropDownMenu extends LinearLayout {

    //顶部菜单布局
    private LinearLayout tabMenuView;
    //底部容器，包含popupMenuViews，maskView
    private FrameLayout containerView;
    //弹出菜单父布局
    private FrameLayout popupMenuViews;
    //遮罩半透明View，点击可关闭DropDownMenu
    private View maskView;
    //tabMenuView里面选中的tab位置，-1表示未选中
    private int current_tab_position = -1;
    //分割线颜色
    private int dividerColor = 0xff999999;
    //tab选中颜色
    private int textSelectedColor = 0xff890c85;
    //tab未选中颜色
    private int textUnselectedColor = 0xff333333;
    //遮罩颜色
    private int maskColor = 0x50000000;
    //tab字体大小
    private int menuTextSize = 14;
    //tab选中图标
    private int menuSelectedIcon;
    //tab未选中图标
    private int menuUnselectedIcon;
    /**
     * tab距离左边像素
     */
    private int menuTabPaddingLeft = 0;
    /**
     * tab距离顶部像素
     */
    private int menuTabPaddingTop = 0;
    /**
     * tab距离右边像素
     */
    private int menuTabPaddingRight = 0;
    /**
     * tab距离下边像素
     */
    private int menuTabPaddingBottom = 0;
    /**
     * tab选中图标flag
     */
    private int TAB_SELECT_ICON_FLAG = 1395545867;
    /**
     * tab未选中图标flag
     */
    private int TAB_UNSELECT_ICON_FLAG = 1459241023;

    public DropDownMenu(Context context) {
        super(context, null);
    }

    public DropDownMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropDownMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            setOrientation(VERTICAL);
            //为DropDownMenu添加自定义属性
            int menuBackgroundColor = 0xffffffff;
            int underlineColor = 0xffcccccc;
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DropDownMenu);
            underlineColor = a.getColor(R.styleable.DropDownMenu_ddunderlineColor, underlineColor);
            dividerColor = a.getColor(R.styleable.DropDownMenu_dddividerColor, dividerColor);
            textSelectedColor = a.getColor(R.styleable.DropDownMenu_ddtextSelectedColor, textSelectedColor);
            textUnselectedColor = a.getColor(R.styleable.DropDownMenu_ddtextUnselectedColor, textUnselectedColor);
            menuBackgroundColor = a.getColor(R.styleable.DropDownMenu_ddmenuBackgroundColor, menuBackgroundColor);
            maskColor = a.getColor(R.styleable.DropDownMenu_ddmaskColor, maskColor);
            menuTextSize = a.getDimensionPixelSize(R.styleable.DropDownMenu_ddmenuTextSize, menuTextSize);
            menuSelectedIcon = a.getResourceId(R.styleable.DropDownMenu_ddmenuSelectedIcon, menuSelectedIcon);
            menuUnselectedIcon = a.getResourceId(R.styleable.DropDownMenu_ddmenuUnselectedIcon, menuUnselectedIcon);
            menuTabPaddingLeft = (int) a.getDimension(R.styleable.DropDownMenu_ddmenuTabPaddingLeft, menuTabPaddingLeft);
            menuTabPaddingTop = (int) a.getDimension(R.styleable.DropDownMenu_ddmenuTabPaddingTop, menuTabPaddingTop);
            menuTabPaddingRight = (int) a.getDimension(R.styleable.DropDownMenu_ddmenuTabPaddingRight, menuTabPaddingRight);
            menuTabPaddingBottom = (int) a.getDimension(R.styleable.DropDownMenu_ddmenuTabPaddingBottom, menuTabPaddingBottom);
            a.recycle();
            //初始化tabMenuView并添加到tabMenuView
            tabMenuView = new LinearLayout(context);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tabMenuView.setOrientation(HORIZONTAL);
            tabMenuView.setBackgroundColor(menuBackgroundColor);
            tabMenuView.setLayoutParams(params);
            addView(tabMenuView, 0);
            //为tabMenuView添加下划线
            View underLine = new View(getContext());
            underLine.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, PixelUtils.dip2px(getContext(), 1.0f)));
            underLine.setBackgroundColor(underlineColor);
            addView(underLine, 1);
            //初始化containerView并将其添加到DropDownMenu
            containerView = new FrameLayout(context);
            containerView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            addView(containerView, 2);
        } catch (Exception e) {
            Logger.L.error("drop down menu build error:", e);
        }
    }

    /**
     * 初始化DropDownMenu
     *
     * @param tabTexts
     * @param popupViews
     * @param contentView
     */
    public void initDropDownMenu(View contentView) {
        try {
            containerView.addView(contentView, 0);
            maskView = new View(getContext());
            maskView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            maskView.setBackgroundColor(maskColor);
            maskView.setClickable(true);
            maskView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeDropDownMenu();
                }
            });
            containerView.addView(maskView, 1);
            maskView.setVisibility(GONE);
            popupMenuViews = new FrameLayout(getContext());
            popupMenuViews.setVisibility(GONE);
            containerView.addView(popupMenuViews, 2);
        } catch (Exception e) {
            Logger.L.error("set drop down menu error:", e);
        }
    }

    /**
     * 添加tab项
     *
     * @param text            tab文本
     * @param isAddSplitLine  是否添加stab分隔线
     * @param popupView       popupView
     * @param tabSelectIcon   tab项选中图标
     * @param tabUnSelectIcon tab项选中图标
     */
    public void addTab(String text, boolean isAddSplitLine, View popupView, int tabSelectIcon, int tabUnSelectIcon) {
        try {
            final TextView tab = new TextView(getContext());
            tab.setSingleLine();
            tab.setEllipsize(TextUtils.TruncateAt.END);
            tab.setGravity(Gravity.CENTER);
            tab.setTextSize(TypedValue.COMPLEX_UNIT_PX, menuTextSize);
            tab.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
            tab.setTextColor(textUnselectedColor);
            tab.setTag(TAB_SELECT_ICON_FLAG, tabSelectIcon);
            tab.setTag(TAB_UNSELECT_ICON_FLAG, tabUnSelectIcon);
            Drawable icon = tabUnSelectIcon != 0 ? getResources().getDrawable(tabUnSelectIcon) : getResources().getDrawable(menuUnselectedIcon);
            tab.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
            tab.setText(text);
            tab.setPadding(menuTabPaddingLeft, menuTabPaddingTop, menuTabPaddingRight, menuTabPaddingBottom);
            tab.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    switchDropDownMenu(tab);
                }
            });
            tabMenuView.setClickable(true);
            tabMenuView.addView(tab);
            if (isAddSplitLine) {
                View view = new View(getContext());
                view.setLayoutParams(new LayoutParams(PixelUtils.dip2px(getContext(), 0.5f), ViewGroup.LayoutParams.MATCH_PARENT));
                view.setBackgroundColor(dividerColor);
                tabMenuView.addView(view);
            }
            popupView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            popupMenuViews.addView(popupView);
        } catch (Resources.NotFoundException e) {
            Logger.L.error("add tab error:", e);
        }
    }

    /**
     * 添加tab项
     *
     * @param text           tab文本
     * @param isAddSplitLine 是否添加stab分隔线
     * @param popupView      popupView
     */
    public void addTab(String text, boolean isAddSplitLine, View popupView) {
        addTab(text, isAddSplitLine, popupView, 0, 0);
    }

    /**
     * 设置当前tab文字
     *
     * @param text
     */
    public void setCurrTabText(String text) {
        try {
            if (current_tab_position != -1) {
                ((TextView) tabMenuView.getChildAt(current_tab_position)).setText(text);
            }
        } catch (Exception e) {
            Logger.L.error("set curr tab text error:", e);
        }
    }

    /**
     * 关闭菜单
     */
    public void closeDropDownMenu() {
        try {
            if (current_tab_position != -1) {
                TextView tab = (TextView) tabMenuView.getChildAt(current_tab_position);
                tab.setTextColor(textUnselectedColor);
                int tabUnSelectIcon = ConvertUtils.toInt(tab.getTag(TAB_UNSELECT_ICON_FLAG));
                Drawable icon = tabUnSelectIcon != 0 ? getResources().getDrawable(tabUnSelectIcon) : getResources().getDrawable(menuUnselectedIcon);
                tab.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                popupMenuViews.setVisibility(View.GONE);
                popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_out));
                maskView.setVisibility(GONE);
                maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_out));
                current_tab_position = -1;
            }
        } catch (Resources.NotFoundException e) {
            Logger.L.error("close drop down menu error:", e);
        }
    }

    /**
     * DropDownMenu是否处于可见状态
     *
     * @return
     */
    public boolean isShowing() {
        return current_tab_position != -1;
    }

    /**
     * 切换菜单
     *
     * @param target
     */
    private void switchDropDownMenu(View target) {
        try {
            for (int i = 0; i < tabMenuView.getChildCount(); i = i + 2) {
                if (target == tabMenuView.getChildAt(i)) {
                    if (current_tab_position == i) {
                        closeDropDownMenu();
                    } else {
                        if (current_tab_position == -1) {
                            popupMenuViews.setVisibility(View.VISIBLE);
                            popupMenuViews.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_menu_in));
                            maskView.setVisibility(VISIBLE);
                            maskView.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dd_mask_in));
                            popupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                        } else {
                            popupMenuViews.getChildAt(i / 2).setVisibility(View.VISIBLE);
                        }
                        current_tab_position = i;
                        TextView tab = (TextView) tabMenuView.getChildAt(i);
                        tab.setTextColor(textSelectedColor);
                        int tabSelectIcon = ConvertUtils.toInt(tab.getTag(TAB_SELECT_ICON_FLAG));
                        Drawable icon = tabSelectIcon != 0 ? getResources().getDrawable(tabSelectIcon) : getResources().getDrawable(menuSelectedIcon);
                        tab.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                    }
                } else {
                    TextView tab = (TextView) tabMenuView.getChildAt(i);
                    tab.setTextColor(textUnselectedColor);
                    int tabUnSelectIcon = ConvertUtils.toInt(tab.getTag(TAB_UNSELECT_ICON_FLAG));
                    Drawable icon = tabUnSelectIcon != 0 ? getResources().getDrawable(tabUnSelectIcon) : getResources().getDrawable(menuUnselectedIcon);
                    tab.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null);
                    popupMenuViews.getChildAt(i / 2).setVisibility(View.GONE);
                }
            }
        } catch (Resources.NotFoundException e) {
            Logger.L.error("switch drop down menu error:", e);
        }
    }
}
