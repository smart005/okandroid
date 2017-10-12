package com.geek.mibao.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloud.basicfun.BaseAdjustFragment;
import com.cloud.resources.xrecyclerview.FamiliarRecyclerView;
import com.cloud.resources.xrecyclerview.XRefreshRecyclerView;
import com.geek.mibao.BR;
import com.geek.mibao.R;
import com.geek.mibao.ui.ParallaxTest;
import com.geek.mibao.utils.Cheeses;
import com.geek.mibao.viewModels.TestModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/11
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ParallaxContentFragment extends BaseAdjustFragment {

    @Bind(R.id.list_srlv)
    XRefreshRecyclerView listSrlv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parallax_content_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public static ParallaxContentFragment newInstance() {
        return ParallaxContentFragment.newInstance(new ParallaxContentFragment());
    }

    @Override
    protected void onAttachToContext(Context context) {
//        super.setParentActivity(ParallaxTest.getParallaxTest());
        super.setParentFragment(DiscoverFragment.getDisFragment());
        super.onAttachToContext(context);
    }

    private void init() {
        listSrlv.setPullRefreshEnable(false);
        FamiliarRecyclerView recyclerView = listSrlv.getFamiliarRecyclerView();
        recyclerView.setLayoutManager(linearLayoutManager);
        List<TestModel> datalist = new ArrayList<>();
        for (String cheeseString : Cheeses.sCheeseStrings) {
            TestModel model = new TestModel();
            model.setName(cheeseString);
            datalist.add(model);
        }
        CheeseRvAdapter cheeseRvAdapter = new CheeseRvAdapter(getContext(), datalist, R.layout.item_cheese_list, BR.itemModel);
        cheeseRvAdapter.setParallaxPlaceholderHeight(getIntBundle("PLACEHODLE_HEIGHT"));
        listSrlv.setAdapter(cheeseRvAdapter);
        recyclerView.addOnScrollListener(buildRecyclerViewOnScrollListener());
    }
}
