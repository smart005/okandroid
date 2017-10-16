android开发框架
============
# 该框架主要分为三个基础包 RxCore、RxResource、RxBasicFun [需在Android Studio使用]

## .项目开发中首先在工程的build.gradle中做以下配置:
	buildscript {
	    repositories {
	        jcenter()
	    }
	    dependencies {
	        classpath 'com.android.tools.build:gradle:2.3.3'
	    }
	}

	allprojects {
	    repositories {
	        maven { url 'http://www.slcore.com:8081/nexus/content/groups/mibao-group/' }
	    }
	}

	task clean(type: Delete) {
	    delete rootProject.buildDir
	}

	然后在项目工程中添加以下三个主要引用
	compile 'com.cloud:rxcore-release:1.0.36@aar'
	compile 'com.cloud:rxresources-release:1.0.22@aar'
	compile 'com.cloud:basicfun-release:1.0.13@aar'

	框架中所用到的其它第三方引用：
	compile 'com.android.support:support-v4:23.4.0'
	compile 'com.alibaba:fastjson:1.2.30'
	compile 'com.github.bumptech.glide:glide:3.7.0'
	compile 'com.jakewharton:butterknife:7.0.0'
	compile 'com.github.lzyzsd:circleprogress:1.2.1'
	compile 'com.lzy.net:okgo:2.1.4'
	compile 'com.lzy.net:okrx:0.1.2'
	compile 'com.umeng.analytics:analytics:latest.integration'
	compile 'com.github.lovetuzitong:MultiImageSelector:1.2'
	compile 'com.youth.banner:banner:1.4.9'
	compile 'org.greenrobot:eventbus:3.0.0'
	compile 'com.makeramen:roundedimageview:2.3.0'
	compile 'com.android.support:recyclerview-v7:22.2.1'

## .组件模块

[视差列表控件——头部自定义布局,中间有tab分页,tab分页里有滚动列表如ListView ScrollView RecyclerView等](/docs/parallax_list.md)

[XRefreshRecyclerView[带刷新和加载]](/docs/xrecyclerview.md)

[FlowEditItemsLayout[流式]标签列表](/docs/tag_list.md)

[View任何视图下拉刷新与上拉加载控件](/docs/view_refresh_load.md)


## .[项目混淆配置](/docs/confounding.md)