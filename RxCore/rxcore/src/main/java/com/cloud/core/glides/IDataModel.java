package com.cloud.core.glides;

import com.cloud.core.enums.ImgRuleType;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/30
 * @Description:图片数据接口
 * @Modifier:
 * @ModifyContent:
 */
public interface IDataModel {
    String buildDataModelUrl(ImgRuleType ruleType, int width, int height, int corners);
}
