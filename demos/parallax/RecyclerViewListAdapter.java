package com.geek.mibao.adapters;

import android.app.Activity;

import com.cloud.core.databinding.BaseListAdapter;
import com.cloud.core.databinding.BaseRecycleAdapter;
import com.cloud.core.databinding.BindingViewHolder;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.PixelUtils;
import com.geek.mibao.databinding.DiscoverThematicItemViewBinding;
import com.geek.mibao.viewModels.ThematicItemModel;

import java.util.List;


/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/9/25
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class RecyclerViewListAdapter extends BaseRecycleAdapter<ThematicItemModel, DiscoverThematicItemViewBinding> {

    private Activity activity = null;
    private int listHeight = 0;

    public RecyclerViewListAdapter(Activity activity,
                                   List<ThematicItemModel> datalist,
                                   int layoutItemId,
                                   int variableId) {
        super(activity, datalist, layoutItemId, variableId);
        this.activity = activity;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<DiscoverThematicItemViewBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        super.onBindViewHolder(holder, position);
        int imgHeight = (GlobalUtils.getScreenHeight(activity) -
                PixelUtils.dip2px(activity, 30)) * 104 / 343;
        holder.getBinding().productIv.getLayoutParams().height = imgHeight;
    }
}
