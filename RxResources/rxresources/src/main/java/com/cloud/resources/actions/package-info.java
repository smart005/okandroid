/**
 * private QuickAction mqaction = null;
 *
 * mqaction = new QuickAction(getContext());
 * mqaction.setQuickActionBackgroundResource(R.drawable.quickaction_black_bg);
 * mqaction.setQuickActionItemBackgroundResource(R.drawable.quickaction_item_bg_selector);
 * mqaction.setOnClickQuickActionListener(quickActionListener);
 * mqaction.setQuickActionWidth(100);
 * mqaction.setQuickActionJustTriangleBackgroundColor(Color.parseColor("#323232"));
 * mqaction.setQuickActionTextColor(Color.WHITE);
 * mqaction.initQuickAction(getContext(), ThemeColorType.BLACK);
 *
 * if (mqaction != null) {
 * ActionItem createFlockGroupItem = new ActionItem(getContext(), getString(R.string.create_flock_group), "CREATE_FLOCK_GROUP", 0);
 * createFlockGroupItem.setIcon(getContext().getResources().getDrawable(R.mipmap.add_friend_begin));
 * mqaction.addQuickActionItem(getContext(), createFlockGroupItem, true);
 * ActionItem addFriendItem = new ActionItem(getContext(), getString(R.string.add_friend_text), "ADD_FRIEND", 0);
 * addFriendItem.setIcon(getContext().getResources().getDrawable(R.mipmap.group_chat_begin));
 * mqaction.addQuickActionItem(getContext(), addFriendItem, false);
 * mqaction.show(view, -PixelUtils.dip2px(getContext(), 3), 0);
 * }
 */
package com.cloud.resources.actions;

class QuickActionPkg {
    public static final int LAYOUT_QUICKACTION = 1340334808;
    public static final int QUICKACTION_ARROW_UP = 783394278;
    public static final int QUICKACTION_ARROW_BOTTOM = 328117505;
    public static final int QUICKACTION_ARROW_LEFT = 1379811175;
    public static final int QUICKACTION_ARROW_RIGHT = 1496007966;
    public static final int QUICKACTION_ICON_IV = 2073124040;
    public static final int QUICKACTION_TITLE_TV = 2101472547;
}
