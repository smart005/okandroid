文件操作
-------
###### 1.在Application中注册静态方法
```java
public class xxxxx extends BaseApplication {
...
static {
        StorageUtils.setOnStorageInitListener(new StorageUtils.OnStorageInitListener() {
            @Override
            public StorageInitParam getStorageInit() {
                StorageInitParam storageInitParam = new StorageInitParam();
                //设置数据名称
                storageInitParam.setAppDir("xxxx");
                return storageInitParam;
            }
        });
    }
...
}
```
###### 2.StorageUtils工具类使用(以下方法获取的目录都是在sdcard创建的自定义目录下获取对应的目录)
```java
//获取根目录
File StorageUtils.getRootDir();
```
```java
//获取图片目录
File StorageUtils.getImageDir();
```
```java
//获取下载目录
File StorageUtils.getDownloadDir();
```
```java
//获取音频文件目录
File StorageUtils.getAudioDir();
```
```java
//获取视频目录
File StorageUtils.getVideoDir();
```
```java
//获取错误日志目录
File StorageUtils.getErrorDir();
```
```java
//获取debug日志目录
File StorageUtils.getDebugDir();
```
```java
//获取info日志目录
File StorageUtils.getInfoDir();
```
```java
//获取warning日志目录
File StorageUtils.getWarningDir();
```
```java
//获取插件目录
File StorageUtils.getPluginDir();
```
```java
//获取临时apk存放目录
File StorageUtils.getApksDir();
```
```java
//获取缓存目录
File StorageUtils.getCachesDir();
```
```java
//获取数据缓存目录
File StorageUtils.getDataCachesDir();
```
```java
//获取二维码存放目录
File StorageUtils.getQRCodeDir();
```
```java
//获取获取热补丁包存放目录
File StorageUtils.getApatchDir();
```
```java
//获取待处理目录
File StorageUtils.getTobeProcessed();
```
```java
//获取断点续传时oss文件上传临时存放目录
File StorageUtils.getOssRecord();
```
```java
/**
 * 创建目录
 *
 * @param dir  主目录
 * @param dest 需要创建的子目录
 * @return
 */
void StorageUtils.createDirectory(File dir, String dest);
```
```java
/**
 * 创建文件
 *
 * @param dir    主目录
 * @param name   文件名称
 * @param delete 如果存在是否删除原文件重新创建
 * @return
 */
void StorageUtils.createFile(File dir, String name, boolean delete);
```
```java
/**
 * 删除目录或者文件
 *
 * @param filepath 文件路径
 * @return
 */
void StorageUtils.deleteQuietly(String filePath);
```
```java
/**
 * 删除目录或者文件
 *
 * @param dir      主目录
 * @param filepath 文件名称
 * @return
 */
void StorageUtils.deleteQuietly(String dir, String fileName);
```
```java
/**
 * 删除目录或者文件。 目录则递归删除， 文件直接删除
 *
 * @param file
 * @return
 */
void StorageUtils.deleteQuietly(File file);
```
```java
/**
 * 删除目录中的文件及目录 支持递归删除 删除目录本身
 *
 * @param directory
 */
void StorageUtils.deleteDirectory(File directory);
```
```java
/**
 * 递归删除目录中的目录或者文件 不删除目录本身
 *
 * @param directory
 */
void StorageUtils.cleanDirectory(File directory);
```
```java
/**
 * 递归删除目录中的目录或者文件 删除目录本身
 *
 * @param file 目录或文件
 */
void StorageUtils.forceDelete(File file);
```
```java
//递归copy文件
void StorageUtils.copyFiles(String fromFile, String toFile);
```
```java
//递归copy文件
void StorageUtils.copyFiles(File fromFile, File toFile);
```
```java
//copy文件
void StorageUtils.copyFile(String fromFile, String toFile);
```
```java
//copy文件
void StorageUtils.copyFile(File fromFile, File toFile);
```
```java
//将字符串保存至文件
void StorageUtils.save(String content, File tofile);
```
```java
//将字符串追加到文件中
void StorageUtils.appendContent(String content, File tofile);
```
```java
//从文件中读取内容
String StorageUtils.readContent(File targetfile);
```
```java
//从流中读取内容
String StorageUtils.readContent(InputStream is);
```
```java
/**
 * 从assets取文本文件内容
 *
 * @param context
 * @param fileName 文件名称
 * @return
 */
String StorageUtils.readAssetsFileContent(Context context, String fileName);
```
```java
/**
 * 保存Bitmap
 *
 * @param dir      目录
 * @param filename 文件名
 * @param bt       图片
 * @return
 */
File StorageUtils.saveBitmap(File dir, String filename, Bitmap bt);
```
```java
//获取文件或目录大小(单位为字节)
long StorageUtils.getFileOrDirSize(File fileOrDirPath);
```
```java
//获取文件或目录大小(单位为字节)
long StorageUtils.getFileOrDirSize(String fileOrDirPath);
```

