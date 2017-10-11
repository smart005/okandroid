package com.cloud.core.databinding;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BindingViewHolder<BT extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private BT mBinding;
    private int viewType = 0;

    public BindingViewHolder(BT binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public BindingViewHolder(View view) {
        super(view);
    }

    public BT getBinding() {
        return mBinding;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
