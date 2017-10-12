package com.cloud.basicfun.beans;

import com.cloud.core.beans.BaseBean;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/2/23
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */

public class ALiYunOssRole extends BaseBean {

    private String status;
    private String accessKeyId;
    private String accessKeySecret;
    private String securityToken;
    private String bucket;
    private String Expiration;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getExpiration() {
        return Expiration;
    }

    public void setExpiration(String expiration) {
        Expiration = expiration;
    }
}
