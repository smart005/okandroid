package com.cloud.core.databinding;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cloud.core.ObjectJudge;

import java.util.List;


/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/10
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class BaseRecycleAdapter<T, BT extends ViewDataBinding> extends RecyclerView.Adapter<BindingViewHolder<BT>> {

    private Context context;
    private List<T> datalist;
    /**
     * 列表项布局id
     */
    private int layoutItemId;
    /**
     * item 对象id
     */
    private int variableId;

    public BaseRecycleAdapter(Context context, List<T> datalist, int layoutItemId, int variableId) {
        this.context = context;
        this.datalist = datalist;
        this.layoutItemId = layoutItemId;
        this.variableId = variableId;
    }

    @Override
    public BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        BT binding = DataBindingUtil.inflate(inflater, layoutItemId, parent, false);
        BindingViewHolder<BT> viewHolder = new BindingViewHolder<BT>(binding);
        viewHolder.setViewType(viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BindingViewHolder<BT> holder, int position) {
        holder.getBinding().setVariable(variableId, datalist.get(position));
    }

    @Override
    public int getItemCount() {
        return ObjectJudge.isNullOrEmpty(datalist) ? 0 : datalist.size();
    }
}
