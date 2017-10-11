package com.cloud.resources.flows;


import com.cloud.resources.beans.FlowLayoutInstance;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-10-19 下午3:19:34
 * @Description: 监听处理
 * @Modifier:
 * @ModifyContent:
 */
public interface OnFlowLayoutListener {

    public FlowLayoutInstance getFlowLayoutInstance(FlowLayoutInstance mfli);

    public void deleteItem(Object tag);

    public void onItemClickListener(Object tag);

}
