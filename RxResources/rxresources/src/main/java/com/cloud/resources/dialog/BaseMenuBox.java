package com.cloud.resources.dialog;

import android.content.Context;
import android.view.View;

import com.cloud.core.beans.CmdItem;
import com.cloud.core.beans.MenuDialogItem;
import com.cloud.resources.R;
import com.cloud.resources.popuppanel.BaseUpwardMenu;

import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016-2-4 下午3:32:20
 * @Description: 菜单提醒对话框
 * @Modifier:
 * @ModifyContent:
 */
public class BaseMenuBox {

    protected void onItemListener(View v, CmdItem entity) {

    }

    public BaseMenuBox() {
        mbmenu.setCancelClickOutLayout(false);
        mbmenu.setFirstItemBackgroundResid(R.drawable.pic_menu_first_item_bg);
        mbmenu.setItemBackgroundResid(R.drawable.pic_menu_item_bg);
        mbmenu.setEndItemBackgroundResid(R.drawable.pic_menu_end_item_bg);
        mbmenu.setMenuBackgroundResid(R.drawable.pic_menu_bg);
        mbmenu.setSplitLineColor(R.color.pic_menu_split_line_color);
    }

    private BaseUpwardMenu mbmenu = new BaseUpwardMenu() {
        @Override
        public void onClickItemListener(View v, CmdItem entity) {
            onItemListener(v, entity);
        }
    };

    protected List<MenuDialogItem> buildItems(Context context) {
        return null;
    }

    public void show(Context context, View parent) {
        mbmenu.setDataItems(buildItems(context));
        mbmenu.setCancelClickOutLayout(false);
        mbmenu.show(context, parent);
    }
}
