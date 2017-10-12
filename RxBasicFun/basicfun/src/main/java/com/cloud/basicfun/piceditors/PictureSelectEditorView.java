package com.cloud.basicfun.piceditors;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.cloud.basicfun.R;
import com.cloud.basicfun.beans.SelectImageProperties;
import com.cloud.basicfun.dialogs.ImageSelectDialog;
import com.cloud.core.ObjectJudge;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.ConvertUtils;
import com.cloud.core.utils.PixelUtils;
import com.cloud.resources.GlideProcess;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2016/6/15
 * @Description:图片选择编辑器 <com.rongxun.basicfun.piceditors.PictureSelectEditorView
 * android:id="@+id/certification_file_psev"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:background="@color/white_color"
 * android:paddingBottom="@dimen/spacing_8"
 * android:paddingTop="@dimen/spacing_8"
 * appext:eachRowNumber="4"
 * appext:isAlignMiddle="true"
 * appext:isAllowModify="true"
 * appext:maxImageCount="2"></com.rongxun.basicfun.piceditors.PictureSelectEditorView>
 */
public class PictureSelectEditorView extends LinearLayout {

    private int IMAGE_ITEM_IV = 1424857564;
    private int eachRowNumber = 4;
    private int containerWidth = 0;
    private int imgSize = 0;
    private int maxImageCount = 1;
    private boolean isAlignMiddle = false;
    private boolean isAllowModify = false;
    private boolean isModify = false;
    private boolean isOnlyRead = false;
    private boolean isAllowDel = false;
    private int modifyPosition = 0;
    private int SEL_IMG_INDEX_TAG = 771916386;
    private int STATUS_TEXT_ID = 1684646507;
    private int DEL_ID = 1861546519;
    private int DEL_ADD_FLAG_KEY = 1667332261;
    private int addBackgoundResource = 0;
    private TreeMap<Integer, Uri> imgUrls = new TreeMap<Integer, Uri>();
    private HashMap<Integer, Integer> nocompletelst = new HashMap<Integer, Integer>();

    private Activity activity = null;
    private OnPictureSelectChangedListener onPictureSelectChangedListener = null;
    private OnPictureSelectReviewOriginalImageListener onPictureSelectReviewOriginalImageListener = null;
    private OnPictureSelectDeleteListener onPictureSelectDeleteListener = null;
    private OnPictureSelectEditorInitListener onPictureSelectEditorInitListener = null;
    private OnPictureSelectEditorItemChangeListener onPictureSelectEditorItemChangeListener = null;

    public PictureSelectEditorView(Context context) {
        super(context);
        init(true, null);
    }

    public PictureSelectEditorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(false, attrs);
    }

    public PictureSelectEditorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(false, attrs);
    }

    public void setOnPictureSelectEditorInitListener(OnPictureSelectEditorInitListener listener) {
        this.onPictureSelectEditorInitListener = listener;
    }

    public void setOnPictureSelectEditorItemChangeListener(OnPictureSelectEditorItemChangeListener listener) {
        this.onPictureSelectEditorItemChangeListener = listener;
    }

    /**
     * 设置Activity
     *
     * @param activity
     */
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setOnPictureSelectChangedListener(OnPictureSelectChangedListener listener) {
        this.onPictureSelectChangedListener = listener;
    }

    public void setOnPictureSelectReviewOriginalImageListener(OnPictureSelectReviewOriginalImageListener listener) {
        this.onPictureSelectReviewOriginalImageListener = listener;
    }

    public void setOnPictureSelectDeleteListener(OnPictureSelectDeleteListener listener) {
        this.onPictureSelectDeleteListener = listener;
    }

    private void init(boolean flag, AttributeSet attrs) {
        if (flag) {
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(vgparam);
        } else {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PictureSelectEditorView);
            isAlignMiddle = a.getBoolean(R.styleable.PictureSelectEditorView_isAlignMiddle, false);
            maxImageCount = a.getInt(R.styleable.PictureSelectEditorView_maxImageCount, 1);
            eachRowNumber = a.getInt(R.styleable.PictureSelectEditorView_eachRowNumber, 4);
            isAllowModify = a.getBoolean(R.styleable.PictureSelectEditorView_isAllowModify, false);
            isAllowDel = a.getBoolean(R.styleable.PictureSelectEditorView_isAllowDel, true);
            addBackgoundResource = a.getResourceId(R.styleable.PictureSelectEditorView_addBackgoundResource, R.drawable.add_image_bg);
            a.recycle();
        }
        this.setOrientation(VERTICAL);
        this.post(new Runnable() {
            @Override
            public void run() {
                containerWidth = getWidth();
                int splitSize = PixelUtils.dip2px(getContext(), 8);
                imgSize = (containerWidth - splitSize * (eachRowNumber + 1) - eachRowNumber * PixelUtils.dip2px(getContext(), 7)) / eachRowNumber;
                LinearLayout row = buildRow();
                RelativeLayout item = buildItem(imgSize, true, null, -1);
                row.addView(item);
                addView(row);
                if (onPictureSelectEditorInitListener != null) {
                    onPictureSelectEditorInitListener.onPictureSelectEditorInit();
                }
            }
        });
    }

    public void setOnlyRead(boolean isOnlyRead) {
        this.isOnlyRead = isOnlyRead;
    }

    private LinearLayout buildRow() {
        LayoutParams rowparam = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        LinearLayout row = new LinearLayout(getContext());
        row.setLayoutParams(rowparam);
        row.setOrientation(HORIZONTAL);
        if (isAlignMiddle) {
            row.setGravity(Gravity.CENTER);
        }
        return row;
    }

    private RelativeLayout buildItem(int imgSize, boolean isAdd, Uri uri, int imgIndex) {
        LayoutParams rlparam = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rlparam.setMargins(PixelUtils.dip2px(getContext(), 8), 0, 0, 0);
        if (isAlignMiddle) {
            rlparam.gravity = Gravity.CENTER_HORIZONTAL;
        }
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setLayoutParams(rlparam);
        //图片
        RelativeLayout.LayoutParams ivparam = new RelativeLayout.LayoutParams(imgSize, imgSize);
        ivparam.addRule(RelativeLayout.CENTER_VERTICAL);
        ivparam.setMargins(0, PixelUtils.dip2px(getContext(), 14), 0, 0);
        final ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(ivparam);
        imageView.setId(IMAGE_ITEM_IV);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (isAdd) {
            imageView.setImageResource(addBackgoundResource != 0 ? addBackgoundResource : R.drawable.add_image_bg);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (activity != null) {
                        imageSelectDialog.setMaxFileSize(1024);
                        imageSelectDialog.setMaxSelectNumber(1);
                        imageSelectDialog.setShowTakingPictures(true);
                        imageSelectDialog.setExtra(imgUrls.size() > 0 ? (imgUrls.lastKey() + 1) : 0);
                        imageSelectDialog.show(activity, v);
                    }
                }
            });
        } else {
            imageView.setTag(SEL_IMG_INDEX_TAG, imgIndex);
            imageView.setImageResource(R.drawable.add_image_def_icon);
            GlideProcess.load(getContext(), uri, new GlideDrawableImageViewTarget(imageView) {
                @Override
                public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                    super.onResourceReady(drawable, anim);
                    imageView.invalidate();
                }
            });
            if (!isOnlyRead) {
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isAllowModify) {
                            if (activity != null) {
                                isModify = true;
                                modifyPosition = ConvertUtils.toInt(v.getTag(SEL_IMG_INDEX_TAG));
                                imageSelectDialog.setMaxFileSize(1024);
                                imageSelectDialog.setMaxSelectNumber(1);
                                imageSelectDialog.setShowTakingPictures(true);
                                imageSelectDialog.setExtra(modifyPosition);
                                imageSelectDialog.show(activity, v);
                            }
                        } else {
                            if (onPictureSelectReviewOriginalImageListener != null) {
                                int index = ConvertUtils.toInt(v.getTag(SEL_IMG_INDEX_TAG), 0);
                                Uri imguri = imgUrls.get(index);
                                onPictureSelectReviewOriginalImageListener.OnPictureSelectReviewOriginalImage(imguri, index);
                            }
                        }
                    }
                });
            }
        }
        relativeLayout.addView(imageView);
        //删除图标
        RelativeLayout.LayoutParams delparam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        delparam.addRule(RelativeLayout.ALIGN_TOP, IMAGE_ITEM_IV);
        delparam.addRule(RelativeLayout.RIGHT_OF, IMAGE_ITEM_IV);
        delparam.setMargins(PixelUtils.dip2px(getContext(), -8), PixelUtils.dip2px(getContext(), -14), 0, 0);
        ImageView del = new ImageView(getContext());
        del.setId(DEL_ID);
        del.setLayoutParams(delparam);
        del.setImageResource(R.drawable.delete_icon);
        del.setVisibility((isAdd || isOnlyRead || !isAllowDel) ? View.GONE : View.VISIBLE);
        del.setTag(imgIndex);
        del.setTag(DEL_ADD_FLAG_KEY, isAdd ? 1 : 0);
        del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                delImageView(v);
            }
        });
        relativeLayout.addView(del);
        //状态文本
        if (!isAdd) {
            RelativeLayout.LayoutParams statetvparam = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            statetvparam.addRule(RelativeLayout.CENTER_IN_PARENT);
            TextView statutv = new TextView(getContext());
            statutv.setLayoutParams(statetvparam);
            statutv.setTextColor(Color.WHITE);
            statutv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            statutv.setId(STATUS_TEXT_ID);
            statutv.setSingleLine(true);
            statutv.setVisibility(View.GONE);
            relativeLayout.addView(statutv);
        }
        return relativeLayout;
    }

    private void appendImage(Uri imgUri, int imgIndex) {
        try {
            if (imgUri == null || containerWidth <= 0 || imgUrls.size() > maxImageCount) {
                return;
            }
            if (!imgUrls.containsValue(imgUri)) {
                imgUrls.put(imgIndex, imgUri);
            }
            LinearLayout row = (LinearLayout) this.getChildAt(this.getChildCount() - 1);
            if (row.getChildCount() == eachRowNumber) {
                row.removeViewAt(eachRowNumber - 1);
                RelativeLayout item = buildItem(imgSize, false, imgUri, imgIndex);
                row.addView(item);
                if (imgUrls.size() < maxImageCount) {
                    row = buildRow();
                    row.addView(buildItem(imgSize, true, null, imgIndex));
                    this.addView(row);
                }
                return;
            }
            if (row == null) {
                return;
            }
            RelativeLayout item = buildItem(imgSize, false, imgUri, imgIndex);
            row.addView(item, row.getChildCount() - 1);
            if (imgUrls.size() == maxImageCount) {
                row.removeViewAt(row.getChildCount() - 1);
            }
        } catch (Exception e) {
            Logger.L.error("add picture select editor item error:", e);
        }
    }

    private ImageSelectDialog imageSelectDialog = new ImageSelectDialog() {
        @Override
        protected void onSelectCompleted(List<SelectImageProperties> selectImageProperties, Object extra) {
            SelectImageProperties seimgitem = selectImageProperties.get(0);
            File selimgfile = new File(seimgitem.getImagePath());
            Uri imguri = Uri.fromFile(selimgfile);
            if (isModify) {
                imgUrls.remove(modifyPosition);
                imgUrls.put(modifyPosition, imguri);
                removeAllViews();
                LinearLayout row = buildRow();
                RelativeLayout item = buildItem(imgSize, true, null, -1);
                row.addView(item);
                addView(row);
                for (Map.Entry<Integer, Uri> integerUriEntry : imgUrls.entrySet()) {
                    appendImage(integerUriEntry.getValue(), integerUriEntry.getKey());
                }
            } else {
                appendImage(imguri, imgUrls.size() > 0 ? (imgUrls.lastKey() + 1) : 0);
            }
            if (onPictureSelectChangedListener != null) {
                int imagePosition = ConvertUtils.toInt(extra);
                onPictureSelectChangedListener.onPictureSelectChanged(imguri, seimgitem.getImageFileName(), imagePosition);
            }
            if (onPictureSelectEditorItemChangeListener != null) {
                onPictureSelectEditorItemChangeListener.onPictureSelectEditorItemChange(PictureSelectEditorView.this, Arrays.asList(getAllImgUris()));
            }
            isModify = false;
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        imageSelectDialog.onActivityResult(activity, requestCode, resultCode, data);
    }

    private void delImageView(View delView) {
        try {
            this.removeAllViews();
            int position = ConvertUtils.toInt(delView.getTag());
            imgUrls.remove(position);
            LinearLayout row = buildRow();
            RelativeLayout item = buildItem(imgSize, true, null, -1);
            row.addView(item);
            addView(row);
            for (Map.Entry<Integer, Uri> integerUriEntry : imgUrls.entrySet()) {
                appendImage(integerUriEntry.getValue(), integerUriEntry.getKey());
            }
            if (onPictureSelectDeleteListener != null) {
                onPictureSelectDeleteListener.onPictureSelectDelete(position);
            }
            if (onPictureSelectEditorItemChangeListener != null) {
                onPictureSelectEditorItemChangeListener.onPictureSelectEditorItemChange(this, Arrays.asList(getAllImgUris()));
            }
        } catch (Exception e) {
            Logger.L.error("del picture select editor item error:", e);
        }
    }

    /**
     * 获取全部图片列表
     *
     * @return
     */
    public Uri[] getAllImgUris() {
        return imgUrls.values().toArray(new Uri[0]);
    }

    /**
     * 获取已(上传)完成图片列表
     *
     * @return
     */
    public List<Uri> getCompletedImgUris() {
        List<Uri> lst = new ArrayList<Uri>();
        for (Map.Entry<Integer, Uri> entry : imgUrls.entrySet()) {
            if (!nocompletelst.containsKey(entry.getKey())) {
                lst.add(entry.getValue());
            }
        }
        return lst;
    }

    /**
     * 是否全部处理完成
     *
     * @return
     */
    public boolean isAllProcessCompleted() {
        return !(nocompletelst.size() > 0);
    }

    /**
     * 绑定图片列表
     *
     * @param imgs
     */
    public void bindImages(List<String> imgs) {
        try {
            if (ObjectJudge.isNullOrEmpty(imgs)) {
                return;
            }
            if (imgs.size() > maxImageCount) {
                for (int i = maxImageCount; i < imgs.size(); i++) {
                    imgs.remove(i);
                }
            }
            for (int i = 0; i < imgs.size(); i++) {
                Uri uri = Uri.parse(imgs.get(i));
                appendImage(uri, i);
            }
            if (onPictureSelectEditorItemChangeListener != null) {
                onPictureSelectEditorItemChangeListener.onPictureSelectEditorItemChange(this, Arrays.asList(getAllImgUris()));
            }
        } catch (Exception e) {
            Logger.L.error("bind images error:", e);
        }
    }

    private TextView getItemText(int position) {
        int rowindex = position / eachRowNumber;
        int itemindex = 0;
        if (position % eachRowNumber == 0) {
            if ((rowindex + 1) * eachRowNumber == (position + 1)) {
                itemindex = eachRowNumber - 1;
            } else {
                itemindex = 0;
            }
        } else {
            itemindex = position % eachRowNumber;
        }
        LinearLayout rowview = (LinearLayout) this.getChildAt(rowindex);
        if (rowview != null) {
            View itemview = rowview.getChildAt(itemindex);
            if (itemview != null) {
                TextView tv = (TextView) itemview.findViewById(STATUS_TEXT_ID);
                return tv;
            }
        }
        return null;
    }

    /**
     * 显示处理中状态
     *
     * @param position 图片索引
     */
    public void showProcessing(int position) {
        try {
            if (!nocompletelst.containsKey(position)) {
                nocompletelst.put(position, position);
            }
            TextView tv = getItemText(position);
            if (tv != null) {
                tv.setVisibility(View.VISIBLE);
                tv.setText("上传中...");
            }
        } catch (Exception e) {
            Logger.L.error("show image process error:", e);
        }
    }

    /**
     * 隐藏处理中状态
     *
     * @param position 图片索引
     */
    public void hideProcessing(int position) {
        try {
            if (nocompletelst.containsKey(position)) {
                nocompletelst.remove(position);
            }
            TextView tv = getItemText(position);
            if (tv != null) {
                tv.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Logger.L.error("hide image process error:", e);
        }
    }

    /**
     * 设置是否允许删除
     *
     * @param isAllowDel
     */
    public void setAllowDel(boolean isAllowDel) {
        try {
            this.isAllowDel = isAllowDel;
            int rowCount = getChildCount();
            for (int i = 0; i < rowCount; i++) {
                ViewGroup row = (ViewGroup) getChildAt(i);
                if (row != null) {
                    int childcount = row.getChildCount();
                    for (int i1 = 0; i1 < childcount; i1++) {
                        ViewGroup childitem = (ViewGroup) row.getChildAt(i1);
                        if (childitem != null) {
                            View delview = childitem.findViewById(DEL_ID);
                            if (delview != null) {
                                int addflag = ConvertUtils.toInt(delview.getTag(DEL_ADD_FLAG_KEY));
                                if (addflag == 1) {
                                    delview.setVisibility(View.GONE);
                                } else {
                                    delview.setVisibility(isAllowDel ? View.VISIBLE : View.GONE);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            Logger.L.error("set allow del process error:", e);
        }
    }
}
