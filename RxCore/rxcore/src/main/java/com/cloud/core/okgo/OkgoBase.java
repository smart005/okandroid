package com.cloud.core.okgo;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.MemoryCookieStore;
import com.lzy.okgo.cookie.store.PersistentCookieStore;

import java.util.logging.Level;

/**
 * @Author Gs
 * @Email:gs_12@foxmail.com
 * @CreateTime:2017/6/1
 * @Description: OkGo基础
 * @Modifier:
 * @ModifyContent:
 */

public class OkgoBase {

    private static OkgoBase okgoBase = null;

    public static OkgoBase getInstance() {
        if (okgoBase == null) {
            okgoBase = new OkgoBase();
        }
        return okgoBase;
    }

    public <AT extends Application> void init(AT at) {
        init(at, new OkgoConfigBean());
    }

    public <AT extends Application> void init(AT at, OkgoConfigBean okgoConfigBean) {
        //必须调用初始化
        OkGo.init(at);
        if (okgoConfigBean != null) {
            OkGo okgo = OkGo.getInstance();
            //是否打开调试信息，信息级别INFO
            if (okgoConfigBean.isDebug()) {
                okgo.debug("JrJr_OkGo", Level.INFO, okgoConfigBean.isDebug());
            }
            //全局的连接超时时间
            if (okgoConfigBean.getConnectTimeout() > 0) {
                okgo.setConnectTimeout(okgoConfigBean.getConnectTimeout());
            }
            //全局的读取超时时间
            if (okgoConfigBean.getReadTimeOut() > 0) {
                okgo.setReadTimeOut(okgoConfigBean.getReadTimeOut());
            }
            //全局的写入超时时间
            if (okgoConfigBean.getWriteTimeOut() > 0) {
                okgo.setWriteTimeOut(okgoConfigBean.getWriteTimeOut());
            }
            //全局缓存模式,默认不缓存
            okgo.setCacheMode(okgoConfigBean.getCacheMode());
            //可以全局统一设置缓存时间,默认永久
            okgo.setCacheTime(okgoConfigBean.getCacheTime());
            //可以全局统一设置超时重连次数,默认为三次,那么最差的情况会请求4次(一次原始请求,三次重连请求),不需要可以设置为0
            okgo.setRetryCount(okgoConfigBean.getRetryCount());
            //框架管理cookie 0:内存缓存（app退出后，cookie消失）1:持久化存储，如果cookie不过期，则一直有效
            if (okgoConfigBean.isEnableCookieStore()) {
                okgo.setCookieStore(okgoConfigBean.getManageCookieType() == 0 ? new MemoryCookieStore() : new PersistentCookieStore());
            }
            //信任所有证书,不安全有风险  如果有证书就写入证书，具体介绍看github
            if (okgoConfigBean.getCertificates() == null) {
                okgo.setCertificates();
            } else {
                okgo.setCertificates(okgoConfigBean.getCertificates());
            }
            //可以添加全局拦截器，不需要就不要加入，错误写法直接导致任何回调不执行
            if (okgoConfigBean.getInterceptor() != null) {
                okgo.addInterceptor(okgoConfigBean.getInterceptor());
            }
            //添加公共头信息
            if (okgoConfigBean.getCommonHeaders() != null) {
                okgo.addCommonHeaders(okgoConfigBean.getCommonHeaders());
            }
            //添加公共参数信息
            if (okgoConfigBean.getCommonParams() != null) {
                okgo.addCommonParams(okgoConfigBean.getCommonParams());
            }
        }
    }
}
