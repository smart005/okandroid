package com.geek.mibao.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;

import com.cloud.basicfun.BaseFragmentActivity;
import com.cloud.basicfun.enums.ListStateEnum;
import com.cloud.resources.xlistview.OnXListViewListener;
import com.cloud.resources.xrecyclerview.XRefreshRecyclerView;
import com.geek.mibao.BR;
import com.geek.mibao.R;
import com.geek.mibao.adapters.RecyclerViewListAdapter;
import com.geek.mibao.beans.ThematicListBean;
import com.geek.mibao.databinding.RecyclerviewTestBinding;
import com.geek.mibao.services.DiscoverService;
import com.geek.mibao.viewModels.ThematicItemModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/9
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class RecyclerViewTest extends BaseFragmentActivity {

    @Bind(R.id.list_srlv)
    XRefreshRecyclerView listSrlv;

    private List<ThematicItemModel> datalist = new ArrayList<ThematicItemModel>();
    private RecyclerViewListAdapter recyclerViewListAdapter = null;
    private RecyclerviewTestBinding binding = null;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        binding = DataBindingUtil.setContentView(this, R.layout.recyclerview_test);
        recyclerViewListAdapter = new RecyclerViewListAdapter(getActivity(),
                datalist,
                R.layout.discover_thematic_item_view,
                BR.itemModel);
        binding.setAdapter(recyclerViewListAdapter);
        ButterKnife.bind(this, binding.getRoot());
        init();
    }

    private void init() {
        binding.listSrlv.setXListViewListener(new OnXListViewListener() {
            @Override
            public void onRefresh() {
                getCurrPageIndex();
                discoverService.requestThematicList(getActivity(),
                        currPageIndex,
                        ListStateEnum.Refresh.getValue());
            }

            @Override
            public void onLoadMore() {
                currPageIndex++;
                discoverService.requestThematicList(getActivity(),
                        currPageIndex,
                        "");
            }
        });
        binding.listSrlv.setPullRefreshEnable(true);
        binding.listSrlv.setPullLoadEnable(true);
        binding.listSrlv.setSurplusDataNumberEnableLoadMore(4);
        binding.listSrlv.refresh();
    }

    private DiscoverService discoverService = new DiscoverService() {

        @Override
        protected void onRequestThematicListSuccessful(ThematicListBean thematicListBean, String reqKey) {
            if (TextUtils.equals(reqKey, ListStateEnum.Refresh.getValue())) {
                datalist.clear();
            }
            List<ThematicItemModel> list = thematicListBean.getData().getList();
            ThematicItemModel itemModel1 = new ThematicItemModel();
            itemModel1.setTitle("1111");
            itemModel1.setIntroduction("aaaaaa");
            list.add(itemModel1);
            ThematicItemModel itemModel2 = new ThematicItemModel();
            itemModel2.setTitle("2222");
            itemModel2.setIntroduction("bbbb");
            list.add(itemModel2);
            ThematicItemModel itemModel3 = new ThematicItemModel();
            itemModel3.setTitle("3333");
            itemModel3.setIntroduction("cccc");
            list.add(itemModel3);
            datalist.addAll(list);
            recyclerViewListAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onRequestCompleted() {
            binding.listSrlv.initRL();
        }
    };
}
