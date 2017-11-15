从1.0.75版本之后改用greenDao方式,初始化及操作步骤如下
-----
###### 1.在最外层的build.gradle下添加greenDao的classpath
```java
buildscript {
    dependencies {
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.2'
    }
}
```
###### 2.在项目的build.gradle下配置greenDao插件及引用
```java
apply plugin: 'org.greenrobot.greendao'

compile 'org.greenrobot:greendao:3.2.2'
compile 'org.greenrobot:greendao-generator:3.2.2'
```
###### 3.在项目的build.gradle下配置dao的生成路径
```java
android {
	buildTypes {
        greendao {
            schemaVersion 1
            daoPackage 'xxx.xxxx.xxxx.daos'
            targetGenDir 'src/main/java/'
        }
    }
}
//其中当表名、字段名、字段类型改变时必须对schemaVersion的值加1以此来改变数据库表结构
```
###### 4.在Application初始化
```java
//若需要调用FileUploadUtils对象进行文件上传的需要进行如下初始
DBManager.getInstance().initializeBaseDb(this, 
                "数据库名",
                AppendPositionBeanDao.class,
                BreakPointBeanDao.class
                ...);
//若【不】需要调用FileUploadUtils对象进行文件上传的可进行如下初始
DBManager.getInstance().initializeBaseDb(this, 
                "数据库名",
                ...);
```
###### 5.创建表结构
```java
@Entity(nameInDb = "表名")
public class TestBean {
	//主键并设置为自增(若设置为自增其类型必须为Long)
    @Id(autoincrement = true)
    //增加字段的索引
    @Index
    //设置字段名
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "age")
    private int age;
    @Property(nameInDb = "gender")
    private String gender;
}
```
###### 5.编译生成之后创建BaseDaoManager对象【此对象在实际项目中Copy即可,无需改动】
```java
public class BaseDaoManager {
    protected void getDao(final Action<DaoSession> action) {
        try {
            if (action == null) {
                return;
            }
            DBManager.getInstance().getHelper(new Action<DBManager.RxSqliteOpenHelper>() {
                @Override
                public void execute(DBManager.RxSqliteOpenHelper helper) {
                    DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
                    if (daoMaster == null) {
                        return;
                    }
                    DaoSession daoSession = daoMaster.newSession();
                    if (daoSession == null) {
                        return;
                    }
                    action.execute(daoSession);
                }
            });
        } catch (Exception e) {
            Logger.L.error(e);
        }
    }
}
```
###### 6.创建DaoManager继承BaseDaoManager
```java
public class DaoManager extends BaseDaoManager {

    private static DaoManager daoManager = null;

    public static DaoManager getInstance() {
        return daoManager == null ? daoManager = new DaoManager() : daoManager;
    }

    //**********************取对应Dao对象*****************************
    public void getTestBeanDao(final Action<TestBeanDao> action) {
        if (action == null) {
            return;
        }
        super.getDao(new Action<DaoSession>() {
            @Override
            public void execute(DaoSession daoSession) {
                TestBeanDao testBeanDao = daoSession.getTestBeanDao();
                if (testBeanDao == null) {
                    return;
                }
                TestBeanDao.createTable(daoSession.getDatabase(), true);
                action.execute(testBeanDao);
            }
        });
    }
    //**************************************************************
}
```
###### 7.数据对象操作
```java
DaoManager.getInstance().getTestBeanDao(new Action<TestBeanDao>() {
    @Override
    public void execute(TestBeanDao beanDao) {
        //增、删、改、查
    }
});
```