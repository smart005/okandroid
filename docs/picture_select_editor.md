图片(上传)选择编辑控件
---------
* 示例

![images](/docs/images/picture_select_editor.png)

* 用法

###### 0.上传前准备
	private TreeMap<Integer, String> upimages = new TreeMap<Integer, String>();

###### 1.布局
	<com.cloud.basicfun.piceditors.PictureSelectEditorView
        android:id="@+id/pic_choose_psev"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="@color/white_color"
        android:paddingBottom="@dimen/spacing_8"
        android:paddingTop="@dimen/spacing_8"
        //添加(或上传)控件背影
        appext:addBackgoundResource="@drawable/up_image_bg_selector"
        //每行显示个数
        appext:eachRowNumber="4"
        //是否居中对齐
        appext:isAlignMiddle="false"
        //是否允许修改
        appext:isAllowModify="false"
        //是否允许删除
        appext:isAllowDel="true"
        //可上传的最大图片个数
        appext:maxImageCount="9"/>

    其中up_image_bg_selector文件内容如下
    <?xml version="1.0" encoding="utf-8"?>
	<selector xmlns:android="http://schemas.android.com/apk/res/android">
	    <item android:drawable="@drawable/up_image_icon_press" android:state_pressed="true"></item>
	    <item android:drawable="@drawable/up_image_icon_press" android:state_focused="true"></item>
	    <item android:drawable="@drawable/up_image_icon_normal"></item>
	</selector>

###### 2.添加控件事件
	//设置上下文
	picChoosePsev.setActivity(this);
	//设置选择监听事件(1)
    picChoosePsev.setOnPictureSelectChangedListener(pictureSelectChangedListener);
    //设置选择预览原图监听事件(2)
    picChoosePsev.setOnPictureSelectReviewOriginalImageListener(pictureSelectReviewOriginalImageListener);
    //设置删除监听事件(3)
    picChoosePsev.setOnPictureSelectDeleteListener(pictureSelectDeleteListener);

    //(1)
    private OnPictureSelectChangedListener pictureSelectChangedListener = new OnPictureSelectChangedListener() {
        @Override
        public void onPictureSelectChanged(Uri imgUri, String fileName, final int position) {
            if (imgUri == null) {
                return;
            }
            //显示对应图片上上传进度
            picChoosePsev.showProcessing(position);
            //......业务代码

            //图片上传成功调用以下代码初始化
            //隐藏对应进度
            picChoosePsev.hideProcessing(position);
	        if (!upimages.containsValue(s)) {
	            upimages.put(upimages.size() > 0 ? (upimages.lastKey() + 1) : 0, s);
	        }
        }
    };

    //(2)
    private OnPictureSelectReviewOriginalImageListener pictureSelectReviewOriginalImageListener = new OnPictureSelectReviewOriginalImageListener() {
        @Override
        public void OnPictureSelectReviewOriginalImage(Uri imguri, int position) {
        	//预览部分代码
            List<ImageItem> lst = new ArrayList<ImageItem>();
            ImageItem item = new ImageItem();
            item.setUrl(imguri.toString());
            lst.add(item);
            Bundle bundle = new Bundle();
            bundle.putString("IMG_URLS", JsonUtils.toStr(lst));
            bundle.putInt("POSITION", position);
            bundle.putBoolean("FULL_ADDRESS", true);
            RedirectUtils.startActivity(IssueCommentActivity.this, PreviewImageActivity.class, bundle);
        }
    };

    //(3)
    private OnPictureSelectDeleteListener pictureSelectDeleteListener = new OnPictureSelectDeleteListener() {
        @Override
        public void onPictureSelectDelete(int position) {
        	//从堆栈中移除对应图片
            if (!ObjectJudge.isNullOrEmpty(upimages) && upimages.size() > position) {
                upimages.remove(position);
            }
        }
    };

###### 3.ActivityResult回调
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        picChoosePsev.onActivityResult(requestCode, resultCode, data);
    }

###### 4.提交时使用方法
	//判断图片是否上传完成
	picChoosePsev.isAllProcessCompleted()
	//若未上传完成则提示