package com.cloud.coretest;

import com.cloud.core.beans.BaseBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/7/2
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class KolListBean extends BaseBean {

    /**
     * 话题列表
     */
    private List<KolListItem> beanList = null;


    /**
     * @return 获取话题列表
     */
    public List<KolListItem> getBeanList() {
        if (beanList == null) {
            beanList = new ArrayList<KolListItem>();
        }
        return beanList;
    }

    /**
     * 设置话题列表
     *
     * @param beanList
     */
    public void setBeanList(List<KolListItem> beanList) {
        this.beanList = beanList;
    }
}
