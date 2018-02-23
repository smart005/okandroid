Android开发框架
============

<div align=center>

*该框架主要分为三个基础包 RxCore、RxResource、RxBasicFun*
![images](/rxcrb.png)

</div>

### 版本引用 [更新历史](/docs/update_history.md)
<a href="https://github.com/smart005/RxCore">![images](https://img.shields.io/badge/Rxcore-1.2.0-brightgreen.svg)</a> <a href="https://github.com/smart005/RxResource">![images](https://img.shields.io/badge/RxResource-1.2.1-brightgreen.svg)</a> <a href="https://github.com/smart005/RxBasicFun">![images](https://img.shields.io/badge/RxBasicFun-1.2.0-brightgreen.svg)</a>
###### 1.在project的build.gradle中分别引用下面库:
```gradle
compile 'com.cloud:rxcore:1.2.0@aar'
compile 'com.cloud:rxresource:1.2.1@aar'
compile 'com.cloud:basicfun:1.2.0@aar'
```
*[项目源代码可能不是最新的,请使用以上方式导入]*

<font style="font-size: 8px;color: gray;">*(以上三个包已发布至JCenter,引用后同步即可下载)*</font>

<font face="#FF7F50">æ库引用的其它关联引用请看具体版本引用里配置</font>
### 未来版本
###### [v1.3.0]
* 继续将框架中所有功能与资源配置分离出来，方便可自定义配置；同时提供一份默认配置；
* 提供框架使用的基础demo配置;
###### [v1.4.0]
* 提供多平台无埋点统计接入事件配置示例（简单配置即可）；
* 提供多维度scheme跳转及H5交互配置示例;
* 优化框架性能;
###### [v1.5.0]
* 提供框架中独有的加密体系;
###### [v1.6.0]
* 提供框架的各组件使用示例;
* 组件化封装，提供各功能及效果的封装;

### 联系方式
* email:smartljh@aliyun.com
* QQ群:122607882
* 如果遇到问题欢迎在群里提问,尽快回应解决;同时也欢迎大家一起来提建议;

### 文档
###### 1.初始化\基础功能
*注:代码文档中如果有继承相关基类的必须继承!!!*
* [AndroidManifest.xml配置](/docs/android_manifest_config.md)
* [Application配置](/docs/application_config.md)
* [初始化配置](/docs/app_other_config.md)
* [应用程序进入后台或前台监听](/docs/front_back_listening.md)
* [版本更新]

###### 2.[配置说明(功能完整配置)](/docs/all_config.md)
* [配置参数说明](/docs/config_params_instruction.md)

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

*[控件使用问题及引用地址](/docs/ctrol_use_refreners.md)*

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