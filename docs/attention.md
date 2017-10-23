RoundedImageView 实现圆形、圆角矩形的注意事项
-------
[gitHub上面的一个开源组件](https://github.com/vinc3m1/RoundedImageView)

     app:riv_corner_radius
     app:riv_border_color
     app:riv_border_width
     app:riv_tile_mode
     app:riv_oval
     app:riv_mutate_background

其中，riv_corner_radius是指圆角的大小，简单来说，等于宽度一半时，就是圆形了；小于宽度的一半就是圆角矩形

###### 这里特别要注意的几点：
1、riv_tile_mode 有三种clamp，repeat，mirror，分别是指缩放、重复、镜像，实现后两种的效果，最后是src的分辨率小于RoundedImageView的分辨率才比较直观显示（如这里121x121---》70x70）：
![images](/docs/images/round_image_demo.jpg)

2、src原图与RoundedImageView的比例关系，使用时要注意设置android:scaleType=""，一般来说，有fitCenter,centerCrop，CenterInside,fitXY等几种（[具体区别可以看这里](http://www.cnblogs.com/chq3272991/p/5710498.html)）

如要获得一个圆形，如果src的长宽比为4:3，如果设置fitCenter，把原图按比例扩大或缩小到ImageView的高度，居中显示，那么效果如下：

![images](/docs/images/round_image_2.jpg)

显然是得不到一个圆形的，那么试试改成fitXY、center、centerCrop:
![images](/docs/images/round_image_3.jpg) ![images](/docs/images/round_image_4.jpg) ![images](/docs/images/round_image_5.jpg)

这过程也就是说，要先通过scaleType来调整（裁剪）原图，然后再进行切圆角、加边框处理，这里用centerCrop才符合要求