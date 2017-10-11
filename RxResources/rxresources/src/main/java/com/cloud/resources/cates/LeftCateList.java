package com.cloud.resources.cates;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.cloud.core.logger.Logger;
import com.cloud.resources.R;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/8/23
 * @Description:左侧分类列表
 * @Modifier:
 * @ModifyContent:
 */
public class LeftCateList extends RelativeLayout {

    private ListView catelv = null;
    private View catesLastSplitLine = null;
    private RelativeLayout cateContentRl = null;

    public LeftCateList(Context context) {
        super(context);
        init(false, null);
    }

    public LeftCateList(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(true, attrs);
    }

    private void init(boolean flag, AttributeSet attrs) {
        try {
            if (flag) {

            } else {
                LayoutParams catesparam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                this.setLayoutParams(catesparam);
            }
            View v = View.inflate(getContext(), R.layout.cates_list_view, null);
            catelv = (ListView) v.findViewById(R.id.cates_lv);
            catesLastSplitLine = v.findViewById(R.id.cates_last_split_live_v);
            cateContentRl = (RelativeLayout) v.findViewById(R.id.cates_content_rl);
            this.addView(v);
        } catch (Exception e) {
            Logger.L.error("left cate list init error:", e);
        }
    }
}
