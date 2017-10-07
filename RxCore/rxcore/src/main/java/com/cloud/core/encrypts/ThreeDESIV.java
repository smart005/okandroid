package com.cloud.core.encrypts;

import android.text.TextUtils;

import com.cloud.core.logger.Logger;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/1/5
 * @Description:带iv的3DES加密
 * @Modifier:
 * @ModifyContent:
 */
public class ThreeDESIV {

    // 加解密统一使用的编码方式
    private final static String encoding = "utf-8";

    /**
     * 3DES加密
     *
     * @param secretKey 密钥
     * @param iv        向量
     * @param plainText 普通文本
     * @return
     */
    public static String encode(String secretKey, String iv, String plainText) {
        try {
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
            byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
            return Base64.encode(encryptData);
        } catch (Exception e) {
            Logger.L.error("encode error:", e);
        }
        return "";
    }

    /**
     * 3DES解密
     *
     * @param secretKey   密钥
     * @param iv          向量
     * @param encryptText 加密文本
     * @return
     */
    public static String decode(String secretKey, String iv, String encryptText) {
        try {
            if (TextUtils.isEmpty(encryptText)) {
                return "";
            }
            encryptText = encryptText.replace(" ", "");
            encryptText = encryptText.replace("\n", "");
            encryptText = encryptText.trim();
            Key deskey = null;
            DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
            SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
            deskey = keyfactory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
            byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
            return new String(decryptData, encoding);
        } catch (Exception e) {
            Logger.L.error("decode error:", e);
        }
        return "";
    }
}
