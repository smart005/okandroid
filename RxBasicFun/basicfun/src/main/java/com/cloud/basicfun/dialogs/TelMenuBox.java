package com.cloud.basicfun.dialogs;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.cloud.basicfun.R;
import com.cloud.core.beans.CmdItem;
import com.cloud.core.beans.MenuDialogItem;
import com.cloud.resources.RedirectUtils;
import com.cloud.resources.popuppanel.BaseUpwardMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-6-19 下午10:29:47
 * @Description: 电话菜单
 * @Modifier:
 * @ModifyContent:
 */
public class TelMenuBox {

    private Context context = null;
    private String clientServerTel = "";
    private String CLIENT_SERVER_TEL_KEY = "423922026";

    public TelMenuBox() {
        mbmenu.setCancelClickOutLayout(false);
        mbmenu.setFirstItemBackgroundResid(R.drawable.pic_menu_first_item_bg);
        mbmenu.setItemBackgroundResid(R.drawable.pic_menu_item_bg);
        mbmenu.setEndItemBackgroundResid(R.drawable.pic_menu_end_item_bg);
        mbmenu.setMenuBackgroundResid(R.drawable.pic_menu_bg);
        mbmenu.setSplitLineColor(R.color.pic_menu_split_line_color);
    }

    public void setClientServerTel(String clientServerTel) {
        this.clientServerTel = clientServerTel;
    }

    private List<MenuDialogItem> buildItems(Context context) {
        List<MenuDialogItem> lst = new ArrayList<MenuDialogItem>();
        MenuDialogItem cstel = new MenuDialogItem();
        cstel.setCmdItem(new CmdItem(CLIENT_SERVER_TEL_KEY, clientServerTel));
        cstel.setTextColorResid(R.color.color_323232);
        cstel.setTextName(clientServerTel);
        lst.add(cstel);
        return lst;
    }

    private BaseUpwardMenu mbmenu = new BaseUpwardMenu() {
        @Override
        public void onClickItemListener(View v, CmdItem entity) {
            onItemListener(v, entity);
            dismiss();
        }
    };

    public void show(Context context, View parent) {
        this.context = context;
        mbmenu.setDataItems(buildItems(context));
        mbmenu.setCancelClickOutLayout(false);
        mbmenu.show(context, parent);
    }

    private void onItemListener(View v, CmdItem entity) {
        if (TextUtils.equals(entity.getCommandId(), CLIENT_SERVER_TEL_KEY)) {
            if (!TextUtils.isEmpty(clientServerTel)) {
                RedirectUtils.callTel(context, clientServerTel);
            }
        }
    }
}
