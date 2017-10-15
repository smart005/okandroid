package com.geek.mibao.ui;

import android.os.Bundle;

import com.cloud.basicfun.BaseActivity;
import com.cloud.resources.vrllayout.VRefreshLoadLayout;
import com.cloud.resources.xlistview.OnXListViewListener;
import com.geek.mibao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/10/15
 * @Description:视图刷新与加载
 * @Modifier:
 * @ModifyContent:
 */
public class ViewRefreshLoadActivity extends BaseActivity {

    @Bind(R.id.rlm)
    VRefreshLoadLayout rlm;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.view_refresh_load_view);
        ButterKnife.bind(this);
        rlm.canRefresh(true);
        rlm.canLoadMore(true);
        rlm.initializa();
        rlm.setOnXListViewListener(new OnXListViewListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
    }
}
