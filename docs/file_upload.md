图片(文件)上传
--------
###### 1.文件上传至oss使用
```java
FileUploadUtils fileUploadUtils = new FileUploadUtils() {
    @Override
    protected void onUploadSuccess(int position, String url, String updateType, Object extra) {
        //上传成功回调
    }

    @Override
    protected void onCompleted() {
        //上传完成回调
    }
};

fileUploadUtils.setActivity(getActivity());
//该url地址需要调用后台接口相应的授权信息
String url = OssAssumeRoleUrlType.headImg.getUrl();
fileUploadUtils.upload("上传文件名（示例:xxxxxx.png）", 上传文件, "上传时显示文字", url);
```
###### 2.Api返回的授权信息
```java
//此属性已在框架中定义无需额外添加;
public class ALiYunOssRole extends BaseBean {

    private String accessKeyId = "";
    private String accessKeySecret = "";
    private String securityToken = "";
    private String endpoint = "";
    private String bucket = "";
    private String expiration = "";
    //上传目录名称(如:userimg)
    private String dir = "";
    ...
}
```

*[上传前后进度及等待操作无需添加，框架中已经处理.]*