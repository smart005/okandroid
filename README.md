Android开发框架
============

<div align=center>

*该框架主要分为三个基础包 RxCore、RxResource、RxBasicFun*
![images](/rxcrb.png)

</div>

### 版本引用
<a href="https://github.com/smart005/RxCore">

![images](https://img.shields.io/badge/Rxcore-1.1.0-brightgreen.svg)
</a><a href="https://github.com/smart005/RxResource">
![images](https://img.shields.io/badge/RxResource-1.1.0-brightgreen.svg)
</a><a href="https://github.com/smart005/RxBasicFun">
![images](https://img.shields.io/badge/RxBasicFun-1.0.46-brightgreen.svg)
</a>
###### 1.在project的build.gradle中分别引用下面库:
```gradle
//网络请求、数据缓存、资源 SP 验证等工具包
compile 'com.cloud:rxcore:1.1.0@aar'
//与rxcore类似,部分带资源的组件在该库中封装
compile 'com.cloud:rxresource:1.1.0@aar'
//项目开发工具包：版本更新、基类、图片压缩、dialogs、阿里云图片上传组件(二次封装调用简单)等
compile 'com.cloud:basicfun:1.0.46@aar'
```
<font style="font-size: 10px;">*(以上三个包已发布至JCenter,引用后同步即可下载)*</font>

<font face="#FF7F50">æ库引用的其它关联引用请看具体版本引用里配置</font>
### 联系方式
* email:smartljh@aliyun.com
* QQ群:122607882
* 如果遇到问题欢迎在群里提问,尽快回应解决;同时也欢迎大家一起来提建议;

### 文档
###### 1.项目初始配置
*注:代码文档中如果有继承相关基类的必须继承!!!*
* [AndroidManifest.xml配置](/docs/android_manifest_config.md)
* [Application配置](/docs/application_config.md)
* [其他配置](/docs/app_other_config.md)

### 涉及相关库的混淆
```text
æ fastjson
æ glide
æ butterknife
æ okhttp
æ rxjava
æ banner
æ eventbus
æ aliyun oss
æ greenDao
æ x5webview
```
[完整混淆,请点击这里](/docs/confounding.md)

### Licenses
```text
Copyright 2017 chester(李敬欢)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```