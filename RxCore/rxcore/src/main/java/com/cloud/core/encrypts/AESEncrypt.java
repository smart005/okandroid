package com.cloud.core.encrypts;

import com.cloud.core.logger.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/7/16
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class AESEncrypt {
    private static AESEncrypt instance = null;

    public static AESEncrypt getInstance() {
        if (instance == null) {
            instance = new AESEncrypt();
        }
        return instance;
    }

    public String encrypt(String content, String key, String ivs) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(content.getBytes("utf-8"));
            // 此处使用BASE64做转码。
            return Base64.encode(encrypted);
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return "";
    }

    public String decrypt(String content, String key, String ivs) {
        try {
            byte[] raw = key.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = Base64.decode(content);
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception e) {
            Logger.L.error(e);
        }
        return "";
    }
}
