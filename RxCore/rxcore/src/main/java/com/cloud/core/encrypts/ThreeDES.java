package com.cloud.core.encrypts;

import android.text.TextUtils;

import com.cloud.core.logger.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * //    public static void main(String[] args) throws UnsupportedEncodingException {
 * //        final byte[] keyBytes = {0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,
 * //                0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD,
 * //                0x55, 0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36,
 * //                (byte) 0xE2}; // 24字节的密钥
 * //        String szSrc = "123456";
 * ////		String key = "123456789012345612345678";
 * //        System.out.println("秘钥:" + key);
 * //
 * //        System.out.println("加密前的字符串:" + szSrc);
 * //
 * //        byte[] encoded = encryptMode(key.getBytes("utf-8"), szSrc.getBytes());
 * //        Base64 enc = new BASE64Encoder();
 * //        String cipherString = enc.encode(encoded);
 * //        System.out.println("加密后的字符串:" + cipherString);// "/9f7nCBH5nI="
 * //
 * //        byte[] srcBytes = decryptMode(key.getBytes("utf-8"), encoded);
 * //        System.out.println("解密后的字符串:" + (new String(srcBytes)));
 * //
 * //String key = "123456789012345612345678";
 * //    String szSrc = "123456";
 * //    byte[] encoded = ThreeDES.encryptMode(key.getBytes("utf-8"), szSrc.getBytes());
 * //    String cipherString = Base64.encode(encoded);
 * //    byte[] decodeed = ThreeDES.decryptMode(key.getBytes("utf-8"), Base64.decode(cipherString));
 * //    String destr = new String(decodeed);
 * //    }
 */
public class ThreeDES {

//    private static final String Algorithm = "DESede"; // 定义 加密算法,可用
    private static final String Algorithm = "AES/CBC/PKCS5Padding"; // 定义 加密算法,可用
    // DES,DESede,Blowfish

    // keybyte为加密密钥，长度为24字节
    // src为被加密的数据缓冲区（源）
    private static byte[] encryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // keybyte为加密密钥，长度为24字节
    // src为加密后的缓冲区
    private static byte[] decryptMode(byte[] keybyte, byte[] src) {
        try {
            // 生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);
            // 解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    // 转换成十六进制字符串
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1)
                hs = hs + "0" + stmp;
            else
                hs = hs + stmp;
            if (n < b.length - 1)
                hs = hs + ":";
        }
        return hs.toUpperCase();
    }

    /**
     * 加密
     *
     * @param key     　密钥
     * @param content 　加密内容
     * @return
     */
    public static String encrypt(String key, String content) {
        try {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(content)) {
                byte[] keybyte = key.getBytes("utf-8");
                byte[] encoded = ThreeDES.encryptMode(keybyte, content.getBytes());
                if (encoded != null) {
                    String cipherString = Base64.encode(encoded);
                    return cipherString;
                }
            }
        } catch (Exception e) {
            Logger.L.error("encrypt error:", e);
        }
        return "";
    }

    /**
     * 解密
     *
     * @param key    　密钥
     * @param cipher 　密文
     * @return
     */
    public static String decrypt(String key, String cipher) {
        try {
            if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(cipher)) {
                byte[] keybyte = key.getBytes("utf-8");
                byte[] cipherbyte = Base64.decode(cipher);
                byte[] decodeed = ThreeDES.decryptMode(keybyte, cipherbyte);
                String destr = decodeed == null ? "" : new String(decodeed);
                return destr;
            }
        } catch (Exception e) {
            Logger.L.error("decrypt error:", e);
        }
        return "";
    }
}
