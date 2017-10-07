package com.cloud.core.reply;

import com.cloud.core.enums.ReplyLinkTarget;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/13
 * @Description:回复或@事件
 * @Modifier:
 * @ModifyContent:
 */
public interface ReplyLinkClickListener<T> {
    /**
     * 链接事件
     *
     * @param obj 实参对象
     */
    public void onReplyLinkClick(T obj, ReplyLinkTarget replyLinkTarget);
}
