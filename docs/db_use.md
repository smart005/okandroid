数据库使用
-------
###### 1.在Application中注册静态方法进行初始化
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
###### 2.创建数据表
```java
@Table(name = "指定数据库表名")
public class TestBean {
	//name:字段名
	//isId:是否主键
	//isIndex:是否索引
	//autoGen:该字段的值是否递增（数据类型必须为int或long）
    @Column(name = "id",isId = true,isIndex = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "gender")
    private String gender;
}
```
###### 3.使用
```java
//查询
DbUtils.getInstance().getDbManager().findxxxx()
//添加或修改
DbUtils.getInstance().getDbManager().addOrUpdate()或DbUtils.getInstance().getDbManager().addxxxx()
//删除
DbUtils.getInstance().getDbManager().deletexxxx()
```