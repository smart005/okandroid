package com.cloud.basicfun.stickynav;

import android.support.v4.app.Fragment;

import com.cloud.resources.beans.TagsItem;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/25
 * @Description:构建Fragment
 * @Modifier:
 * @ModifyContent:
 */
public interface OnStickyNavFragments {

    public Fragment getItem(int position, TagsItem tagsItem);
}