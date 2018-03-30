高德地图定位及SDK定位功能
------
###### 需要引用库
```gradle
compile 'com.alibaba:fastjson:1.2.41'
compile 'com.android.support:recyclerview-v7:22+'
compile 'com.lzy.net:okgo:3.0.4'
compile 'org.greenrobot:greendao:3.2.2'
compile 'org.greenrobot:greendao-generator:3.2.2'
compile 'com.android.support:support-v4:26+'
compile 'com.cloud:rxcore:1.0.332@aar'
```

###### 1.定位
```java
private AMapUtils aMapUtils = new AMapUtils() {
    @Override
    protected void onLocationSuccess(LocationInfo locationInfo) {
    	//返回定位结果
    }
};
//获取定位
aMapUtils.getLocation(this);
//判断是否有效的经纬度
AMapUtils.isAMapDataAvailable(Context context, double latitude, double longitude);
//坐标转换,支持GPS/Mapbar/Baidu经纬度
AMapUtils.toDPoint(Context context, LatLng sourceLatLng)
//计算两点间距离,单位：米
AMapUtils.getDistance(LatLng startLatlng, LatLng endLatlng)
```

### 2.地图SDK
###### 布局添加MapView
```xml
<com.amap.api.maps.MapView
android:id="@+id/map_mv"
android:layout_width="match_parent"
android:layout_height="match_parent" />
```
###### 初始化地图
```java
private AmapViewUtils amapViewUtils = new AmapViewUtils() {
    @Override
    protected void onMapLoadSuccess() {
    	//地图加载成功后处理,以下操作将尽可能多的地址展现在地图中
        List<LatLng> latLngs = new ArrayList<LatLng>();
        latLngs.add(Constants.XIAN);
        latLngs.add(Constants.CHENGDU);
        latLngs.add(Constants.BEIJING);
        latLngs.add(new LatLng(36.061, 103.834));
        amapViewUtils.setLatLngBounds(latLngs);
    }

    @Override
    protected void onInfoWindowClickCall(Marker marker) {
    	//在InfoWindow被单击时回调
    }

    @Override
    protected View onBuildInfoWindow(Marker marker) {
    	//InfoWindow构建视图
        View infoWindow = getLayoutInflater().inflate(
                R.layout.custom_info_window, null);
        render(marker, infoWindow);
        return infoWindow;
    }
};
//初始化地图
amapViewUtils.initializeMap(mapMv, savedInstanceState);
```
###### 向地图中添加标记
```java
//标记图片为网络地址
amapViewUtils.addMarker(new MarkerInfo(
        "成都市",
        "成都市:30.679879, 104.064855",
        Constants.CHENGDU,
        "https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D450%2C600/sign=4532e9544836acaf59b59ef849e9a126/f603918fa0ec08fadfe0b9ca5aee3d6d54fbdaff.jpg"
));
//标记图片为本地地址
amapViewUtils.addMarker(new MarkerInfo(
        "西安市",
        "西安市：34.341568, 108.940174",
        Constants.XIAN,
        R.drawable.location_marker
));
```
###### 构建地图时必须要重载以下这些方法
```java
@Override
protected void onResume() {
    super.onResume();
    amapViewUtils.onResume();
}

@Override
protected void onPause() {
    super.onPause();
    amapViewUtils.onPause();
}

@Override
protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    amapViewUtils.onSaveInstanceState(outState);
}

@Override
protected void onDestroy() {
    super.onDestroy();
    amapViewUtils.onDestroy();
}
```


