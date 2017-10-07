package com.cloud.core.encrypts;

import android.text.TextUtils;

import com.cloud.core.utils.GlobalUtils;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-12 下午1:26:15
 * @Description: 字符串压缩与反压缩算法
 * @Modifier:
 * @ModifyContent:
 */
public class ReduceArithmetic {

    private static String getReduce(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        StringBuffer result = new StringBuffer();
        int count = content.length();
        char c1 = content.charAt(0);
        int sum = 1;
        for (int i = 1; i < count; i++) {
            char c2 = content.charAt(i);
            // 把前一个字符和当前字符比较
            if (c1 == c2) {
                sum++;
                continue;
            }
            // 拼接字符
            result.append(sum).append(c1);
            // 当前字符变为前一字符
            c1 = c2;
            // 个数初始
            sum = 1;
        }
        // 加上最后一个字符及个数
        result.append(sum).append(c1);
        return result.toString();
    }

    /**
     * 获取压缩字符串
     *
     * @param content 要处理的内容
     * @return
     */
    public static String getReduceString(String content) {
        content = getReduce(content);
        if (TextUtils.isEmpty(content)) {
            return "";
        }
        int count = content.length();
        int halflenght = count / 2;
        String bstr = content.substring(0, halflenght);
        String astr = content.substring((count % 2 == 0) ? halflenght
                : (halflenght + 1));
        String mstr = count % 2 == 0 ? "" : content.substring(halflenght, 1);
        bstr = GlobalUtils.reverse(bstr);
        astr = GlobalUtils.reverse(astr);
        return astr + mstr + bstr;
    }
}
