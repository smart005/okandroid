package com.cloud.basicfun.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cloud.basicfun.BaseActivity;
import com.cloud.basicfun.BaseApplication;
import com.cloud.basicfun.R;
import com.cloud.basicfun.utils.BaseCommonUtils;
import com.cloud.core.logger.Logger;
import com.cloud.resources.RedirectUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-23 下午2:35:11
 * @Description: 网络操作提示
 * @Modifier:
 * @ModifyContent:
 */
public class WirelessPromptActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wireless_prompt_view);
        init();
    }

    private void init() {
        try {
            BaseApplication currapp = BaseApplication.getInstance();
            View returnib = findViewById(R.id.return_ib);
            returnib.setVisibility(View.VISIBLE);
            if (currapp.getReturnIcon() != 0) {
                returnib.setBackgroundResource(currapp.getReturnIcon());
            }
            if (currapp.getThemeColorResId() != 0) {
                View headview = findViewById(R.id.head_layout_rl);
                headview.setBackgroundResource(currapp.getThemeColorResId());
            }
            returnib.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    RedirectUtils.finishActivity(WirelessPromptActivity.this);
                }
            });
            TextView nonetworkconntv = (TextView) findViewById(R.id.subject_tv);
            nonetworkconntv.setText(R.string.no_network_connection);
            TextView channelsourcetv = (TextView) findViewById(R.id.channel_source_tv);
            channelsourcetv.setText(BaseCommonUtils.getChannelName(""));
        } catch (Exception e) {
            Logger.L.error("wireless init error:", e);
        }
    }
}
