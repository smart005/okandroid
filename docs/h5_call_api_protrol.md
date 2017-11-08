H5调用APP任意API接口协议
-----
![images](/docs/images/h5_call_api_protrol_img.png)

```java
调用方法及参数说明(示例)：
注：以下所涉及到的字段类型只支持string、int、double三种类型,其它类型暂不支持；
jrjr.getAPIMethod(String extras)——调用应用本地api方法
extras格式：

{
  "apiName": "login",
  "target": "区分是哪个接口回调",
  "args": [
    {
      "fieldName": "userName",
      "fieldValue": "test",
      "fieldType": "string"
    },
    {
      "fieldName": "password",
      "fieldValue": "123456",
      "fieldType": "string"
    }
  ]
}

JRJR.returnAPIResultMethod(String target,String apiResult)——APP调用H5方法
apiResult：api接口返回的结果，最终以json形式返回给H5 
示例:
{
  "response": "返回数据",
  "target": "区分是哪个接口回调"
}
```