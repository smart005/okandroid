package com.cloud.core.characterencoding;

import android.text.TextUtils;

import com.cloud.core.logger.Logger;

import java.io.UnsupportedEncodingException;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/18
 * @Description:首字符读取
 * @Modifier:
 * @ModifyContent:
 */
public class FirstLetters {
    private static final int GB_SP_DIFF = 160;
    private static final int[] secPosValueList = {1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086, 4390, 4558, 4684, 4925, 5249, 5600};
    private static final char[] firstLetter = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x', 'y', 'z'};

    /**
     * 获取字符串每个字符拼音的首字母
     *
     * @param oriString 原字符串
     * @return
     */
    public static String getStringFirstLetters(String oriString) {
        try {
            if (TextUtils.isEmpty(oriString)) {
                return "";
            }
            StringBuffer sbletter = new StringBuffer();
            for (int i = 0; i < oriString.length(); i++) {
                char letter = oriString.charAt(i);
                Character gletter = getFirstLetter(letter);
                if (gletter != null) {
                    sbletter.append(gletter.toString());
                }
            }
            return sbletter.toString();
        } catch (Exception e) {
            Logger.L.error("get string first letters error:", e);
            return "";
        }
    }

    /**
     * 获取字符串第一个字符拼音的首字母
     *
     * @param oriString 原字符串
     * @return
     */
    public static String getStringFirstCharacterFirstLetter(String oriString) {
        try {
            if (TextUtils.isEmpty(oriString)) {
                return "";
            }
            char letter = oriString.charAt(0);
            Character gletter = getFirstLetter(letter);
            if (gletter == null) {
                return "";
            }
            return gletter.toString();
        } catch (Exception e) {
            Logger.L.error("get string first character first letter error:", e);
            return "";
        }
    }

    private static Character getFirstLetter(char ch) {
        byte[] uniCode = null;
        try {
            uniCode = String.valueOf(ch).getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
        //非汉字
        if ((uniCode[0] & 0xff) < 128 && (uniCode[0] & 0xff) > 0) {
            return null;
        } else {
            return convert(uniCode);
        }
    }

    private static char convert(byte[] bytes) {
        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }
}
