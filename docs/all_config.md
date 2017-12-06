RxCore功能完整配置
-----
*下面设置的值为配置默认值*
```xml
//注意在assets中的配置文件名称必须是:878a0aa6244a47e6a7e2ef94f2e4d422.json
{
  "imagePlatformType": "ALIBABA",
  "apiConfigs": {
    "apiMessagePromptFilter": [],
    "apiSpecificNameFilter": [],
    "apiSuccessRet": [
      "R0001",
      "200",
      "success"
    ],
    "apiUnauthorizedRet": []
  },
  "receiveAction": "662762580",
  "defaultBackgroundImage": {
    "name": "bg",
    "type": "drawable"
  },
  "appIcon": {
    "name": "icon1",
    "type": "drawable"
  },
  "updateCheckActivityNames": [
    "Main"
  ],
  "netStateRemindFilterActivityNames": [],
  "appVersionCheck": {
    "state": true,
    "urlType": "UPDATE_INFO_URL"
  },
  "themeColor": "#ffffff",
  "token": {
    "name": "token",
    "type": "apiToken"
  }
}
```