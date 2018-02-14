配置参数说明
---
###### 1.图片规则类型配置
```text
/**
 * 
 * ALIBABA:图片地址后面带阿里云规则
 * QINIU:图片地址后面带七牛规则
 */
"imagePlatformType": "ALIBABA"
```
###### 2.API相关配置
```text
/**
 * 接口消息提醒过滤
 */
"apiMessagePromptFilter": []

/**
 * 在接口请求时对包含的特定的接口名称,返回时不做验证
 */
"apiSpecificNameFilter": []

/**
 * 接口请求成功返回码(以下三个为默认值)
 */
"apiSuccessRet": ["R0001","200","success"]

/**
 * 接口未授权返回码
 */
"apiUnauthorizedRet": []

/**
 * token
 */
"token": {
    "name": "token",//token名称
    "type": "apiToken"//token取值是来源于哪个微服务
}
```
###### 3.接收广播Action配置
```text
//框架中默认广播action
"receiveAction": "951762469"
//网络广播action
"receiveNetworkAction": "1659023507",
```
###### 4.资源配置
```text
/**
 * 默认背影图片
 */
"defaultBackgroundImageName": {
    "name": "bg",//图片名称
    "type": "drawable" //图片所在目录类型;drawable或mipmap
}

/**
 * app icon
 */
"appIcon": {
    "name": "icon1",//icon图标名称
    "type": "drawable"//图片所在目录类型;drawable或mipmap
}

/**
 * 主题颜色
 */
"themeColor": "#ffffff"
```
###### 5.版本更新配置
```text
/**
 * 更新检测Activity名称
 */
"updateCheckActivityNames": []

/**
 * 网络状态提醒需要过滤的Activity名称
 */
"netStateRemindFilterActivityNames": []

/**
 * app版本检测
 */
"appVersionCheck": {
    "state": true,//表示是否启用版本检测,false:即便手动调用也无效;
    "urlType": "UPDATE_INFO_URL"//url类型，此处获取版本更新信息的url
}
```
###### 6.若框架中需要用到url时或token取值时，需要在Application中设置监听并返回具体的地址;
```text
private OnConfigItemUrlListener configItemUrlListener = new OnConfigItemUrlListener() {
    @Override
    public String getUrl(String urlType) {
        //urlType从各配置项中的urlType取;
        return null;
    }
};
//添加监听;其中第一个参数为配置文件中的urlType;
addOrUpdateObjectValue("UPDATE_INFO_URL", configItemUrlListener);

private OnConfigTokenListener configTokenListener = new OnConfigTokenListener() {
    @Override
    public String getToken(String apiToken) {
        //apiToken从各配置项中的apiToken取
        return null;
    }
};
//添加监听;其中第一个参数为配置文件中的token的type值[如:apiToken];
addOrUpdateObjectValue("设置对应api token type", configTokenListener);
```