应用程序MQ缓存
-----
```java
MQ即为该框架中的内存缓存对象，当进程kill后该对象列表中的所有值会消失或初始化；
其操作非常简单，具体如下：
```
*[前提是项目Application需要继承BaseApplication]*
```java
private XXXXApplication currapp = null;
```
###### 1.向MQ中添加或获取boolean类型值
```java
currapp.addOrUpdateFlagStatus(String key,boolean value);

currapp.hasFlagStatus(String key);
```
###### 2.向MQ中添加或获取Object类型值
```java
currapp.addOrUpdateObjectValue(String key,Object value);
//判断是否包含指定的key
currapp.hasObjectValue(String key);
//获取值
Object currapp.getObjectValue(String key);
```
###### 3.从MQ中移除指定key的值
```java
currapp.removeFlagStatus(String key);

currapp.removeObjectValue(String key);
```