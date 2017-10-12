package com.cloud.basicfun.dialogs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.cloud.basicfun.BaseFragment;
import com.cloud.basicfun.beans.SelectImageProperties;
import com.cloud.basicfun.cropimage.Crop;
import com.cloud.basicfun.utils.ImageSelectUtils;
import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import me.shaohui.advancedluban.Luban;
import me.shaohui.advancedluban.OnMultiCompressListener;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/12/13
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class ImageSelectDialog {

    private Context context = null;

    private int REQUEST_IMAGE = 10755;

    private int TAILORING_REQUEST_CODE = 32164;

    /**
     * 是否需要剪裁(只有单选时此属性才生效,默认为false)
     */
    private boolean isTailoring = false;
    /**
     * 最多选择数(最小可设为1)
     */
    private int maxSelectNumber = 1;
    /**
     * 是否显示拍照图片(默认为显示)
     */
    private boolean isShowTakingPictures = true;
    /**
     * 已选择图片
     */
    private ArrayList<String> selectedImages = null;
    /**
     * 图片路径
     */
    private List<SelectImageProperties> imagePaths = new ArrayList<SelectImageProperties>();
    /**
     * 最大文件大小
     */
    private int maxFileSize = 1024;
    /**
     * 最大图片宽度
     */
    private int maxImageWidth = 1080;
    /**
     * 最大图片高度
     */
    private int maxImageHeight = 1920;

    private int ASPECT_X = 0;
    private int ASPECT_Y = 0;
    private int MAX_X = 0;
    private int MAX_Y = 0;
    private int zoomWidth = 300;
    private int zoomHeight = 450;

    private final int PROCESS_COMPLETED_WITH = 1704312529;

    private Object extra = null;

    /**
     * 选择图片完成(在失败时也会回调此方法，此时参数值可能为空)
     *
     * @param selectImageProperties 选择图片属性
     */
    protected void onSelectCompleted(List<SelectImageProperties> selectImageProperties, Object extra) {

    }

    public void setExtra(Object extra) {
        this.extra = extra;
    }

    public Object getExtra() {
        return this.extra;
    }

    /**
     * 最大图片高度(默认为1920)
     *
     * @param maxImageHeight
     */
    public void setMaxImageHeight(int maxImageHeight) {
        this.maxImageHeight = maxImageHeight;
    }

    /**
     * 最大图片宽度(默认为1080)
     *
     * @param maxImageWidth
     */
    public void setMaxImageWidth(int maxImageWidth) {
        this.maxImageWidth = maxImageWidth;
    }

    /**
     * 最大文件大小(默认为1024KB)
     *
     * @param maxFileSize
     */
    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    /**
     * 设置已选择图片
     *
     * @param selectedImages
     */
    public void setSelectedImages(ArrayList<String> selectedImages) {
        this.selectedImages = selectedImages;
    }

    /**
     * 是否需要剪裁(只有单选时此属性才生效,默认为false)
     *
     * @param isTailoring
     */
    public void setTailoring(boolean isTailoring) {
        this.isTailoring = isTailoring;
    }

    /**
     * 最多选择数(最小可设为1)
     * 当值为1时自动为单选
     *
     * @param maxSelectNumber 最多选择数
     */
    public void setMaxSelectNumber(int maxSelectNumber) {
        if (maxSelectNumber < 1) {
            maxSelectNumber = 1;
        }
        this.maxSelectNumber = maxSelectNumber;
    }

    /**
     * 是否显示拍照图片(默认为显示)
     *
     * @param isShowTakingPictures true:显示;false:隐藏;
     */
    public void setShowTakingPictures(boolean isShowTakingPictures) {
        this.isShowTakingPictures = isShowTakingPictures;
    }

    /**
     * 设置裁剪框的大小
     *
     * @param width
     * @param height
     */
    public void setTailoringSize(int width, int height) {
        zoomWidth = width;
        zoomHeight = height;
    }

    /**
     * 设置裁剪宽高比
     *
     * @param x
     * @param y
     */
    public void withAspect(int x, int y) {
        this.ASPECT_X = x;
        this.ASPECT_Y = y;
    }

    public void withMaxSize(int width, int height) {
        this.MAX_X = width;
        this.MAX_Y = height;
    }

    private MultiImageSelector initShow(Context context) {
        MultiImageSelector multiImageSelector = MultiImageSelector.create(context).showCamera(isShowTakingPictures);
        if (maxSelectNumber > 1) {
            multiImageSelector.multi();
            multiImageSelector.count(maxSelectNumber);
            if (!ObjectJudge.isNullOrEmpty(selectedImages)) {
                multiImageSelector.origin(selectedImages);
            }
        } else {
            multiImageSelector.single();
        }
        return multiImageSelector;
    }

    public void show(Activity activity, View parent) {
        this.context = activity;
        imagePaths.clear();
        if (isTailoring && maxSelectNumber == 1) {
            Crop.pickImage(activity, TAILORING_REQUEST_CODE);
        } else {
            MultiImageSelector multiImageSelector = initShow(activity);
            multiImageSelector.start(activity, REQUEST_IMAGE);
        }
    }

    public void show(BaseFragment fragment, View parent) {
        this.context = fragment.getContext();
        imagePaths.clear();
        if (isTailoring && maxSelectNumber == 1) {
            Crop.pickImage(fragment.getActivity(), TAILORING_REQUEST_CODE);
        } else {
            MultiImageSelector multiImageSelector = initShow(fragment.getContext());
            multiImageSelector.start(fragment, REQUEST_IMAGE);
        }
    }

    /**
     * 需在Activity的onActivityResult回调
     *
     * @param activity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(final Activity activity, int requestCode, int resultCode, final Intent data) {
        try {
            if (requestCode == REQUEST_IMAGE) {
                if (resultCode == activity.RESULT_OK) {
                    if (data == null) {
                        return;
                    }
                    List<String> paths = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    if (ObjectJudge.isNullOrEmpty(paths)) {
                        mhandler.obtainMessage(PROCESS_COMPLETED_WITH, null).sendToTarget();
                    } else {
                        List<File> imgPaths = new ArrayList<File>();
                        for (String path : paths) {
                            imgPaths.add(new File(path));
                        }
                        Luban.compress(activity, imgPaths)
                                .putGear(Luban.CUSTOM_GEAR)
                                .setMaxSize(maxFileSize)
                                .setMaxWidth(maxImageWidth)
                                .setMaxHeight(maxImageHeight)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .launch(new OnMultiCompressListener() {
                                    @Override
                                    public void onStart() {
                                        imagePaths.clear();
                                    }

                                    @Override
                                    public void onSuccess(List<File> list) {
                                        try {
                                            if (ObjectJudge.isNullOrEmpty(list)) {
                                                mhandler.obtainMessage(PROCESS_COMPLETED_WITH, null).sendToTarget();
                                            } else {
                                                for (File file : list) {
                                                    SelectImageProperties selectImageProperties = new SelectImageProperties();
                                                    selectImageProperties.setImagePath(file.getAbsolutePath());
                                                    selectImageProperties.setImageFileName(file.getName());
                                                    imagePaths.add(selectImageProperties);
                                                }
                                                mhandler.obtainMessage(PROCESS_COMPLETED_WITH, imagePaths).sendToTarget();
                                            }
                                        } catch (Exception e) {
                                            mhandler.obtainMessage(PROCESS_COMPLETED_WITH, null).sendToTarget();
                                            Logger.L.error("mult compress process error:", e);
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable throwable) {
                                        mhandler.obtainMessage(PROCESS_COMPLETED_WITH, null).sendToTarget();
                                    }
                                });
                    }
                }
            } else if (requestCode == TAILORING_REQUEST_CODE) {
                if (data != null) {
                    imagePaths.clear();
                    String path = ImageSelectUtils.getPath(context, data.getData());
//                    String[] proj = {MediaStore.Images.Media.DATA};
//                    // 好像是android多媒体数据库的封装接口，具体的看Android文档
//                    Cursor cursor = context.getContentResolver().query(, proj, null, null, null);
//                    // 将光标移至开头 ，这个很重要，不小心很容易引起越界
//                    if (cursor!=null && cursor.moveToFirst()) {
//                        // 按我个人理解 这个是获得用户选择的图片的索引值
//                        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                        // 最后根据索引值获取图片路径
//                        String path = cursor.getString(column_index);
                    if (!TextUtils.isEmpty(path)) {
                        File imgfile = new File(path);
                        //压缩图片
                        Luban.compress(activity, imgfile)
                                .setMaxSize(maxFileSize)
                                .setMaxWidth(maxImageWidth)
                                .setMaxHeight(maxImageHeight)
                                .putGear(Luban.CUSTOM_GEAR)
                                .setCompressFormat(Bitmap.CompressFormat.JPEG)
                                .asObservable()
                                .subscribe(new Consumer<File>() {
                                    @Override
                                    public void accept(File file) {
                                        try {
                                            SelectImageProperties selectImageProperties = new SelectImageProperties();
                                            selectImageProperties.setImagePath(file.getAbsolutePath());
                                            selectImageProperties.setImageFileName(file.getName());
                                            imagePaths.add(selectImageProperties);
                                            Uri destination = Uri.fromFile(file);
                                            Crop crop = Crop.of(data.getData(), destination);
                                            if (ASPECT_X > 0 && ASPECT_Y > 0) {
                                                crop = crop.withAspect(ASPECT_X, ASPECT_Y);
                                            } else {
                                                crop = crop.asSquare();
                                            }
                                            if (MAX_X > 0 && MAX_Y > 0) {
                                                crop.withMaxSize(MAX_X, MAX_Y);
                                            }
                                            crop.start(activity);
                                        } catch (Exception e) {
                                            Logger.L.error("image call process error:", e);
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) {
                                        mhandler.obtainMessage(PROCESS_COMPLETED_WITH, null).sendToTarget();
                                    }
                                });
                    }
                } else {
                    mhandler.obtainMessage(PROCESS_COMPLETED_WITH, null).sendToTarget();
                }
            } else if (requestCode == Crop.REQUEST_CROP) {
                if (data != null) {
                    imagePaths.clear();
                    File imgfile = new File(Crop.getOutput(data).getPath());
                    SelectImageProperties selectImageProperties = new SelectImageProperties();
                    selectImageProperties.setImagePath(imgfile.getAbsolutePath());
                    selectImageProperties.setImageFileName(imgfile.getName());
                    selectImageProperties.setThumImagePath(imgfile.getAbsolutePath());
                    selectImageProperties.setThumImageFileName(getFileName(imgfile.getAbsolutePath()));
                    imagePaths.add(selectImageProperties);
                    mhandler.obtainMessage(PROCESS_COMPLETED_WITH, imagePaths).sendToTarget();
                } else {
                    mhandler.obtainMessage(PROCESS_COMPLETED_WITH, null).sendToTarget();
                }
            }
        } catch (
                Exception e)

        {
            mhandler.obtainMessage(PROCESS_COMPLETED_WITH, null).sendToTarget();
            Logger.L.error("image process error:", e);
        }

    }

    private String getFileName(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        int seindex = path.lastIndexOf("/");
        if (seindex >= 0) {
            return path.substring(seindex + 1);
        } else {
            return "";
        }
    }

    /**
     * 删除已选择图片
     */
    public void deleteSelecteImages() {
        try {
            if (!ObjectJudge.isNullOrEmpty(imagePaths)) {
                for (SelectImageProperties imagePath : imagePaths) {
                    if (!TextUtils.isEmpty(imagePath.getImagePath())) {
                        File imageFile = new File(imagePath.getImagePath());
                        if (imageFile.exists()) {
                            imageFile.delete();
                        }
                    }
                    if (!TextUtils.isEmpty(imagePath.getThumImagePath())) {
                        File thumImageFile = new File(imagePath.getThumImagePath());
                        if (thumImageFile.exists()) {
                            thumImageFile.delete();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("delete select images error:", e);
        }
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == PROCESS_COMPLETED_WITH) {
                List<SelectImageProperties> selectImageProperties = (List<SelectImageProperties>) msg.obj;
                onSelectCompleted(selectImageProperties, extra);
            }
        }
    };
}