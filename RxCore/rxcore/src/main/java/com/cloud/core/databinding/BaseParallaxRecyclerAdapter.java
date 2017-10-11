package com.cloud.core.databinding;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.ViewGroup;

import com.cloud.core.ObjectJudge;

import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/11
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class BaseParallaxRecyclerAdapter<T, BT extends ViewDataBinding> extends BaseRecycleAdapter<T, BT> {

    public final int TYPE_HEADER = 1;
    public final int TYPE_ITEM = 0;
    private List<T> datalist;
    private Context context = null;
    //视差占位视图高度
    private int parallaxPlaceholderHeight = 0;

    public BaseParallaxRecyclerAdapter(Context context, List<T> datalist, int layoutItemId, int variableId) {
        super(context, datalist, layoutItemId, variableId);
        this.context = context;
        this.datalist = datalist;
    }

    @Override
    public BindingViewHolder<BT> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View placeholderView = buildPlaceholderView();
            BindingViewHolder<BT> viewHolder = new BindingViewHolder<BT>(placeholderView);
            viewHolder.setViewType(viewType);
            return viewHolder;
        } else {
            return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<BT> holder, int position) {
        if (holder.getViewType() != TYPE_HEADER) {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return ObjectJudge.isNullOrEmpty(datalist) ? 1 : (datalist.size() + 1);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_HEADER : TYPE_ITEM;
    }

    public void setParallaxPlaceholderHeight(int height) {
        this.parallaxPlaceholderHeight = height;
    }

    private View buildPlaceholderView() {
        if (parallaxPlaceholderHeight <= 0) {
            parallaxPlaceholderHeight = 100;
        }
        ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                parallaxPlaceholderHeight
        );
        View view = new View(context);
        view.setLayoutParams(vgparam);
        return view;
    }
}
