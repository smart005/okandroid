package com.cloud.basicfun.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cloud.basicfun.BaseActivity;
import com.cloud.basicfun.BaseApplication;
import com.cloud.basicfun.R;
import com.cloud.basicfun.beans.SelectImageProperties;
import com.cloud.basicfun.dialogs.ImageSelectDialog;
import com.cloud.basicfun.enums.ComRequestUrlType;
import com.cloud.basicfun.utils.FileUploadUtils;
import com.cloud.core.ObjectJudge;
import com.cloud.core.enums.DateFormatEnum;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.DateUtils;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.core.utils.JsonUtils;
import com.cloud.core.utils.StorageUtils;
import com.cloud.core.utils.ToastUtils;
import com.cloud.resources.PicasaView;
import com.cloud.resources.RedirectUtils;
import com.cloud.resources.beans.BaseImageItem;

import java.io.File;
import java.util.List;

import rx.functions.Func0;
import rx.functions.Func1;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/14
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class PreviewImageActivity extends BaseActivity implements View.OnClickListener {

    private PicasaView imgViewerPv;
    private FrameLayout saveImageFl;
    private TextView subjectTv;
    private TextView cancelTv;
    private ImageButton returnIb;
    private TextView submitTv;
    private ImageButton submitIb;
    private LinearLayout llHead;
    private List<BaseImageItem> imgUrls = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview_image_view);
        init();
    }

    private void init() {
        try {
            llHead = (LinearLayout) findViewById(R.id.ll_head);
            imgViewerPv = (PicasaView) findViewById(R.id.img_viewer_pv);
            saveImageFl = (FrameLayout) findViewById(R.id.save_image_fl);
            subjectTv = (TextView) findViewById(R.id.subject_tv);
            cancelTv = (TextView) findViewById(R.id.cancel_tv);
            returnIb = (ImageButton) findViewById(R.id.return_ib);
            submitTv = (TextView) findViewById(R.id.submit_tv);
            submitIb = (ImageButton) findViewById(R.id.submit_ib);
            if (getStringBundle("isUserInfo").equals("isUserInfo")) {
                llHead.setVisibility(View.VISIBLE);
                returnIb.setVisibility(View.VISIBLE);
                subjectTv.setText("个人信息");
                submitIb.setVisibility(View.VISIBLE);
                submitIb.setImageResource(R.drawable.sharemore);
            } else {
                llHead.setVisibility(View.GONE);
            }

            int position = getIntBundle("POSITION");
            String imgUrlsJson = getStringBundle("IMG_URLS", "[]");
            imgUrls = JsonUtils.parseArray(imgUrlsJson, BaseImageItem.class);
            for (BaseImageItem imgUrl : imgUrls) {
                if (TextUtils.isEmpty(imgUrl.getUrl())) {
                    imgUrls.remove(imgUrl);
                } else {
                    imgUrl.setUrl(imgUrl.getUrl());
                }
            }
            imgViewerPv.setImgUrls(imgUrls);
            imgViewerPv.setCurrentPosition(position);
            imgViewerPv.setDisplayDotView(getBooleanBundle("DISPLAY_DOT_VIEW"));
            imgViewerPv.setOnPhotoViewClickListener(photoViewClickListener);
            imgViewerPv.lazyLoad();
        } catch (Exception e) {
            Logger.L.error("preview image init error:", e);
        }
    }

    private PicasaView.OnPhotoViewClickListener photoViewClickListener = new PicasaView.OnPhotoViewClickListener() {
        @Override
        public void onPhotoViewClick() {
            RedirectUtils.finishActivity(PreviewImageActivity.this);
        }

        @Override
        public void onOutsidePhotoTap() {
            RedirectUtils.finishActivity(PreviewImageActivity.this);
        }
    };


    public void saveImage() {
        try {
            if (ObjectJudge.isNullOrEmpty(imgUrls)) {
                return;
            }
            int position = imgViewerPv.getCurrentPosition();
            BaseImageItem item = imgUrls.get(position);
            File dir = StorageUtils.getImageDir();
            String suffix = item.getUrl().substring(item.getUrl().lastIndexOf(".") + 1);
            String filename = String.format("%s.%s", GlobalUtils.getGuidNoConnect(), suffix);
            File imgfile = new File(dir, filename);
        } catch (Exception e) {
            Logger.L.error("save image process error:", e);
        }
    }

    private FileUploadUtils fileUploadUtils = new FileUploadUtils() {
        @Override
        protected Activity getActivity() {
            return PreviewImageActivity.this;
        }

        @Override
        protected String getEndpoint() {
            Func1<ComRequestUrlType, String> urlAction = BaseApplication.getInstance().getUrlAction();
            if (urlAction == null) {
                return "";
            } else {
                return urlAction.call(ComRequestUrlType.Endpoint);
            }
        }

        @Override
        protected String getBucket() {
            Func1<ComRequestUrlType, String> urlAction = BaseApplication.getInstance().getUrlAction();
            if (urlAction == null) {
                return "";
            } else {
                return urlAction.call(ComRequestUrlType.Bucket);
            }
        }

        @Override
        protected void onUploadSuccess(int position, String relativeUrl, String updateType, Object extra) {
            if (TextUtils.equals("PREVIEW_IMAGE_UPLOAD_FLAG", updateType)) {
                Intent intent = new Intent();
                intent.putExtra("HEADIMGURL", relativeUrl);
                setResult(RESULT_OK, intent);
                RedirectUtils.finishActivity(PreviewImageActivity.this);
            }
        }

        @Override
        protected void onCompleted() {

        }
    };

    private ImageSelectDialog imageSelectDialog = new ImageSelectDialog() {
        @Override
        protected void onSelectCompleted(List<SelectImageProperties> selectImageProperties, Object extra) {
            if (ObjectJudge.isNullOrEmpty(selectImageProperties)) {
                return;
            }
            SelectImageProperties selimageitem = selectImageProperties.get(0);
            File imgfile = new File(selimageitem.getImagePath());
            String fileName = String.format("%s.jpg", GlobalUtils.getGuidNoConnect());
            Func0<String> getTokenAction = BaseApplication.getInstance().getGetTokenAction();
            if (getTokenAction == null || TextUtils.isEmpty(getTokenAction.call())) {
                ToastUtils.showLong(getActivity(), "上传照片需要先登录");
            } else {
                fileUploadUtils.setUploadDirectoryFormat(String.format("userimg/%s/", DateUtils.getDateTime(DateFormatEnum.YYYYMMNC)));
//                fileUploadUtils.setUpdateType("PREVIEW_IMAGE_UPLOAD_FLAG");
                fileUploadUtils.upload(fileName, imgfile, "上传第1张图片", getTokenAction.call());
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            imageSelectDialog.onActivityResult(this, requestCode, resultCode, data);
        } catch (Exception e) {
            Logger.L.error("implement info onActivityResult process error:", e);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.save_image_fl) {
            saveImage();
        } else if (id == R.id.return_ib) {
            RedirectUtils.finishActivity(getActivity());
        } else if (id == R.id.submit_ib) {
            imageSelectDialog.setMaxFileSize(1024);
            imageSelectDialog.setMaxSelectNumber(1);
            imageSelectDialog.setShowTakingPictures(false);
            imageSelectDialog.setTailoring(false);
            imageSelectDialog.show(this, v);
        }
    }
}
