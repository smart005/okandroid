package com.cloud.coretest;

import com.cloud.core.annotations.BaseUrlTypeName;
import com.cloud.core.annotations.DELETE;
import com.cloud.core.annotations.DataParam;
import com.cloud.core.annotations.GET;
import com.cloud.core.annotations.Param;
import com.cloud.core.annotations.Path;
import com.cloud.core.beans.BaseBean;
import com.cloud.core.beans.RetrofitParams;
import com.cloud.coretest.beans.VersionBean;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2017/6/6
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
@BaseUrlTypeName(value = ApiUrlType.API_URL_TYPE_NAME, tokenName = "token")
public interface TestAPI {

    @GET("/kol/1.0/article")
    @BaseUrlTypeName(ApiUrlType.API_URL_TYPE_NAME)
    @DataParam(value = KolListBean.class)
    RetrofitParams requestVersion(
            @Param("searchType") int searchType
    );

    @DELETE("kol/1.0/follows/{friendId}")
    @DataParam(BaseBean.class)
    RetrofitParams cancelConcernFriends(
            @Path("friendId") int friendId,
            @Param("token") String frToken
    );

    @GET(value = "/rest/version", isFullUrl = false, isConfigBaseUrl = true)
    @BaseUrlTypeName(value = OkgoTest.baseUrl)
    @DataParam(value = VersionBean.class)
    RetrofitParams requestOutsideUrl();
}
