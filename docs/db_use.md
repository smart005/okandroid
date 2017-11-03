数据库使用
-------
###### 1.在Application中注册静态方法
```java
public class xxxxx extends BaseApplication {
...
static {
        //目录配置
        StorageUtils.setOnStorageInitListener(new StorageUtils.OnStorageInitListener() {
            @Override
            public StorageInitParam getStorageInit() {
                StorageInitParam storageInitParam = new StorageInitParam();
                storageInitParam.setAppDir("mibao");
                return storageInitParam;
            }
        });
    }
...
}
```