文本道行缩进
----
###### 1.添加布局
```xml
<com.cloud.core.widgets.TextIndentation
android:id="@+id/test_ti"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:background="@color/white_color"
android:lineSpacingExtra="@dimen/spacing_4"
android:lineSpacingMultiplier="1" />
```
###### 2.代码设置
```java
//设置显示内容
testTi.setTextContent("显示内容");
//设置文本颜色
testTi.setTextContentColor(Color.parseColor("#666666"));
//设置是否首行缩进
testTi.setFirstLineIndentation(true);
//将文本绑定到控件上
testTi.setText(testTi.buildContent());
```
*[除了颜色与文本内容不可在布局中设置，其它与属性TextView用法一致]*