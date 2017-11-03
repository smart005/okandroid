ToastUtils工具类
-----
```java
//yOffset距离屏幕中心的偏移量
void ToastUtils.showShort(Context context, CharSequence message,int yOffset);
```
```java
void ToastUtils.showShort(Context context, CharSequence message);
```
```java
//yOffset距离屏幕中心的偏移量
void ToastUtils.showShort(Context context, int resId, int yOffset);
```
```java
void ToastUtils.showShort(Context context, int resId);
```
```java
//yOffset距离屏幕中心的偏移量
void ToastUtils.showLong(Context context, CharSequence message,int yOffset);
```
```java
void ToastUtils.showLong(Context context, CharSequence message);
```
```java
//yOffset距离屏幕中心的偏移量
void ToastUtils.showLong(Context context, int resId, int yOffset);
```
```java
void ToastUtils.showLong(Context context, int resId);
```
```java
//duration显示停留的时间
//yOffset距离屏幕中心的偏移量
void ToastUtils.show(Context context, CharSequence message,int duration, int yOffset);
```
```java
//duration显示停留的时间
void ToastUtils.show(Context context, CharSequence message, int duration);
```
```java
//duration显示停留的时间
//yOffset距离屏幕中心的偏移量
void ToastUtils.show(Context context, int resId, int duration,int yOffset);
```
```java
//duration显示停留的时间
void ToastUtils.show(Context context, int resId, int duration);
```
```java
//隐藏toast
void ToastUtils.hide();
```