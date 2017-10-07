package com.cloud.core.utils;

import android.text.TextUtils;

import com.cloud.core.logger.Logger;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/11/15
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class PinyinUtil {

    /**
     * 取得给定所有汉字首字母,即声母
     *
     * @param str 给定汉字串
     * @return 声母串
     */
    public String getAllChineseFirstLetter(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            }
            String _str = "";
            for (int i = 0; i < str.length(); i++) {
                _str = _str + this.getChineseFirstLetter(str.substring(i, i + 1));
            }
            return _str;
        } catch (Exception e) {
            Logger.L.error("get all chinese first letter error:", e);
        }
        return "";
    }

    /**
     * 取得给定汉字的首字母,即声母
     *
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    public String getChineseFirstLetter(String chinese) {
        try {
            if (TextUtils.isEmpty(chinese)) {
                return "";
            }
            chinese = new String(chinese.getBytes("GB2312"), "ISO8859-1");
            if (chinese.length() > 1) {
                int[] li_SecPosValue = {1601, 1637, 1833, 2078, 2274,
                        2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
                        4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590};
                String[] lc_FirstLetter = {"a", "b", "c", "d", "e",
                        "f", "g", "h", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                        "t", "w", "x", "y", "z"};
                int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
                int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
                li_SectorCode = li_SectorCode - 160;
                li_PositionCode = li_PositionCode - 160;
                int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
                if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {
                    for (int i = 0; i < 23; i++) {
                        if (li_SecPosCode >= li_SecPosValue[i]
                                && li_SecPosCode < li_SecPosValue[i + 1]) {
                            chinese = lc_FirstLetter[i];
                            break;
                        }
                    }
                } else {
                    chinese = chinese.substring(0, 1);
                }
            }
            return chinese.trim().toUpperCase();
        } catch (Exception e) {
            Logger.L.error("get chinese first letter", e);
        }
        return "";
    }
}
