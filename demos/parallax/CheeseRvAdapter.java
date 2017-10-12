package com.geek.mibao.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cloud.core.databinding.BaseParallaxRecyclerAdapter;
import com.cloud.core.databinding.BindingViewHolder;
import com.geek.mibao.R;
import com.geek.mibao.databinding.ItemCheeseListBinding;
import com.geek.mibao.utils.Cheeses;
import com.geek.mibao.viewModels.TestModel;

import java.util.List;

public class CheeseRvAdapter extends BaseParallaxRecyclerAdapter<TestModel, ItemCheeseListBinding> {

    public CheeseRvAdapter(Context context, List<TestModel> datalist, int layoutItemId, int variableId) {
        super(context, datalist, layoutItemId, variableId);
    }
}
