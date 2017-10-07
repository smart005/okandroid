/**
 *
 */
package com.cloud.core.tagtext;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cloud.core.ObjectJudge;
import com.cloud.core.ObjectManager;
import com.cloud.core.logger.Logger;
import com.cloud.core.utils.PixelUtils;
import com.cloud.core.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LIJINGHUAN
 * @Email:ljh0576123@163.com
 * @CreateTime:2016年2月29日 下午7:44:46
 * @Description: 标签文本视图
 * @Modifier:
 * @ModifyContent:
 */
public class TagTextView extends RelativeLayout {

    private List<TagItemProperties> tags = new ArrayList<TagItemProperties>();
    private TagPosition tagPosition = TagPosition.Start;
    private DisplayMetrics dm = null;

    private OnTagItemClickListener onTagItemClickListener;

    public void setOnTagItemClickListener(OnTagItemClickListener onTagItemClickListener) {
        this.onTagItemClickListener = onTagItemClickListener;
    }

    /**
     * 标签项背景
     */
    private int tagItemBackgroundResource = 0;
    /**
     * 标签与文本间距(单位dp)
     */
    private int tagAndTextSpacing = 0;
    /**
     * 标签之间间距(单位dp)
     */
    private int tagSpacing = 0;
    /**
     * 文本颜色
     */
    private ColorStateList colors = null;
    /**
     * 标签文本颜色
     */
    private int tagTextColor = 0;
    /**
     * 字体大小(单位sp)
     */
    private int textSize = 0;
    /**
     * 标签字体大小(单位sp)
     */
    private int tagTextSize = 0;
    /**
     * 行高(单位dp)
     */
    private int lineHeight = 0;

    /**
     * @param context
     * @param attrs
     */
    public TagTextView(Context context) {
        super(context);
        init(true);
    }

    /**
     * @param context
     * @param attrs
     */
    public TagTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(false);
    }

    private void init(boolean flag) {
        if (flag) {
            ViewGroup.LayoutParams vgparam = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setLayoutParams(vgparam);
        }
        if (dm == null) {
            dm = ObjectManager.getDisplayMetrics(getContext());
        }
    }

    private TextView buildTextView(Context context) {
        LayoutParams tvparam = new LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(context);
        tv.setId(TagTextPkg.TAG_TEXT_ID);
        tv.setLayoutParams(tvparam);
        return tv;
    }

    public void setTagItems(List<TagItemProperties> lst) {
        this.tags = ObjectJudge.isNullOrEmpty(lst) ? new ArrayList<TagItemProperties>()
                : lst;
    }

    /**
     * @param tagPosition 设置标签显示位置
     */
    public void setTagPosition(TagPosition tagPosition) {
        this.tagPosition = tagPosition;
    }

    /**
     * @param 标签项背景 要设置的 tagItemBackgroundResource
     */
    public void setTagItemBackgroundResource(int tagItemBackgroundResource) {
        this.tagItemBackgroundResource = tagItemBackgroundResource;
    }

    /**
     * @param 标签与文本间距 要设置的 tagAndTextSpacing
     */
    public void setTagAndTextSpacing(int tagAndTextSpacing) {
        if (tagAndTextSpacing > 0) {
            this.tagAndTextSpacing = PixelUtils.dip2px(getContext(),
                    tagAndTextSpacing);
        }
    }

    /**
     * @param 标签之间间距 要设置的 tagSpacing
     */
    public void setTagSpacing(int tagSpacing) {
        if (tagSpacing > 0) {
            this.tagSpacing = PixelUtils.dip2px(getContext(), tagSpacing);
        }
    }

    /**
     * @param 文本颜色 要设置的 colors
     */
    public void setTextColor(ColorStateList colors) {
        this.colors = colors;
    }

    /**
     * @param 标签文本颜色 要设置的 tagTextColor
     */
    public void setTagTextColor(int tagTextColor) {
        this.tagTextColor = tagTextColor;
    }

    /**
     * @param 字体大小 (单位dp) 要设置的 textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    /**
     * @param 标签字体大小 (单位dp) 要设置的 tagTextSize
     */
    public void setTagTextSize(int tagTextSize) {
        this.tagTextSize = tagTextSize;
    }

    /**
     * @param 行高 (单位dp) 要设置的 lineHeight
     */
    public void setLineHeight(int lineHeight) {
        this.lineHeight = PixelUtils.dip2px(getContext(), lineHeight);
    }

    public void setText(CharSequence text) {
        try {
            if (tagPosition == TagPosition.Start) {
                if (!ObjectJudge.isNullOrEmpty(tags)) {
                    addTagItemForStart(text);
                } else {
                    removeTags();
                    buildTextViewForStart(0, 0, text);
                }
            } else {
                removeTags();
                boolean existflag = false;
                View v = findViewById(TagTextPkg.TAG_TEXT_ID);
                TextView tv = v == null ? buildTextView(getContext())
                        : (TextView) v;
                if (v != null) {
                    existflag = true;
                }
                tv.post(new CusTextViewRunnable(tv));
                if (textSize > 0) {
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
                }
                if (colors != null) {
                    tv.setTextColor(colors);
                }
                if (lineHeight - textSize > 0) {
                    tv.setLineSpacing(lineHeight - textSize, 1);
                }
                tv.setText(text);
                if (!existflag) {
                    this.addView(tv);
                }
            }
        } catch (Exception e) {
            Logger.L.error("set TagTextView text error:", e);
        }
    }

    private void removeTags() {
        List<View> lst = ViewUtils.getViewsByTag(this,
                TagTextPkg.TAG_ITEM_VIEW_TAG);
        if (ObjectJudge.isNullOrEmpty(lst)) {
            return;
        }
        for (View view : lst) {
            removeView(view);
        }
    }

    private class CusTextViewRunnable implements Runnable {

        private TextView tv = null;

        public CusTextViewRunnable(TextView tv) {
            this.tv = tv;
        }

        @Override
        public void run() {
            if (tv != null) {
                buildEndTagsView(tv);
            }
        }
    }

    private void addTagItemForStart(CharSequence text) {
        int tagslength = tags.size();
        int left = 0;
        int top = 0;
        for (int position = 0; position < tagslength; position++) {
            TagItemProperties element = tags.get(position);
            int paddingLeft = (position < tagslength && position > 0) ? tagSpacing
                    : 0;
            int tagitembgresid = (element.getTagBackgroundResource() == 0) ? tagItemBackgroundResource : element.getTagBackgroundResource();
            TagItemView tiv = null;
            if (element.tagItemType.equals(TagItemType.TextView)) {
                tiv = new TagItemView(getContext(),
                        element.getTagName(), paddingLeft,
                        tagitembgresid, tagTextColor, tagTextSize);
            } else if (element.tagItemType.equals(TagItemType.ImageView)) {
                tiv = new TagItemView(getContext(), paddingLeft,
                        tagitembgresid);
            }

            LayoutParams tivparam = (LayoutParams) tiv
                    .getLayoutParams();
            int width = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            int height = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
            tiv.measure(width, height);
            if (position == 0) {
                tivparam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                tivparam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                this.addView(tiv);
            } else {
                if ((dm.widthPixels - left - paddingLeft) > tiv
                        .getMeasuredWidth()) {
                    View lastview = getChildAt(getChildCount() - 1);
                    int lastid = lastview.getId();
                    tivparam.addRule(RelativeLayout.RIGHT_OF, lastid);
                    tivparam.addRule(RelativeLayout.ALIGN_TOP, lastid);
                    this.addView(tiv);
                } else {
                    left = 0;
                    top = top + tiv.getMeasuredHeight() + lineHeight - textSize;
                    tivparam.setMargins(0, top, 0, 0);
                    this.addView(tiv);
                }
            }
            left = left + tiv.getMeasuredWidth() + paddingLeft;
            if ((position + 1) == tags.size()) {
                int tvtop = top;
                int tvleft = left;
                View lastview = getChildAt(position);
                lastview.post(new BuildTagItemTextForStartRunnable(tvleft,
                        tvtop, text));
            }

            final int finalPosition = position;
            tiv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTagItemClickListener!=null) {
                        onTagItemClickListener.onItemClick(finalPosition);
                    }
                }
            });
        }
    }

    private class BuildTagItemTextForStartRunnable implements Runnable {

        private int tvleft = 0;
        private int tvtop = 0;
        private CharSequence text = "";

        public BuildTagItemTextForStartRunnable(int tvleft, int tvtop,
                                                CharSequence text) {
            this.tvleft = tvleft;
            this.tvtop = tvtop;
            this.text = text;
        }

        @Override
        public void run() {
            buildTextViewForStart(tvleft, tvtop, text);
        }
    }

    private void addTagItemForEnd(int left, int top, int tagslength) {
        for (int position = 0; position < tagslength; position++) {
            TagItemProperties element = tags.get(position);
            int paddingLeft = (position < tagslength && position > 0) ? tagSpacing
                    : 0;
            int tagitembgresid = (element.getTagBackgroundResource() == 0) ? tagItemBackgroundResource : element.getTagBackgroundResource();
            TagItemView tiv = null;
            if (element.tagItemType.equals(TagItemType.TextView)) {
                tiv = new TagItemView(getContext(),
                        element.getTagName(), paddingLeft,
                        tagitembgresid, tagTextColor, tagTextSize);
            } else if (element.tagItemType.equals(TagItemType.ImageView)) {
                tiv = new TagItemView(getContext(), paddingLeft,
                        tagitembgresid);
            }
            LayoutParams tivparam = (LayoutParams) tiv
                    .getLayoutParams();
            if (position == 0) {
                tivparam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                tivparam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                if (left > 0) {
                    int emwidth = dm.widthPixels - left;
                    if (emwidth > tiv.getMeasuredWidth()) {
                        tivparam.setMargins(left, top, 0, 0);
                    } else {
                        left = 0;
                        top = top + tiv.getMeasuredHeight() + lineHeight
                                - textSize;
                        tivparam.setMargins(0, top, 0, 0);
                    }
                } else {
                    left = 0;
                    top = top + tiv.getMeasuredHeight() + lineHeight - textSize;
                    tivparam.setMargins(0, top, 0, 0);
                }
            } else {
                View lastview = getChildAt(getChildCount() - 1);
                int lastid = lastview.getId();
                if ((dm.widthPixels - left - paddingLeft) > tiv
                        .getMeasuredWidth()) {
                    tivparam.addRule(RelativeLayout.RIGHT_OF, lastid);
                    tivparam.addRule(RelativeLayout.ALIGN_TOP, lastid);
                } else {
                    left = 0;
                    top = top + tiv.getMeasuredHeight() + lineHeight - textSize;
                    tivparam.setMargins(0, top, 0, 0);
                }
            }
            left = left + tiv.getMeasuredWidth() + paddingLeft;
            this.addView(tiv);
            final int finalPosition = position;
            tiv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onTagItemClickListener!=null) {
                        onTagItemClickListener.onItemClick(finalPosition);
                    }
                }
            });
        }
    }

    private void buildEndTagsView(TextView tv) {
        try {
            if (ObjectJudge.isNullOrEmpty(tags)) {
                return;
            }
            Layout tvl = tv.getLayout();
            if (tvl == null) {
                return;
            }
            int lines = tv.getLineCount();
            int tvlastright = (int) tvl.getLineRight(lines - 1)
                    + tagAndTextSpacing;
            int tvlasttop = tvl.getLineTop(lines - 1);
            int tagslength = tags.size();
            addTagItemForEnd(tvlastright, tvlasttop, tagslength);
        } catch (Exception e) {
            Logger.L.error("build end tags view error:", e);
        }
    }

    private void buildTextViewForStart(int left, int top, CharSequence text) {
        try {
            left += tagAndTextSpacing;
            boolean existflag = false;
            TextView tv = (TextView) findViewById(TagTextPkg.TAG_TEXT_ID);
            if (tv == null) {
                tv = buildTextView(getContext());
            } else {
                existflag = true;
            }
            LayoutParams tvparam = (LayoutParams) tv
                    .getLayoutParams();
            tvparam.setMargins(0, top, 0, 0);
            if (textSize > 0) {
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
            }
            if (colors != null) {
                tv.setTextColor(colors);
            }
            if (lineHeight - textSize > 0) {
                tv.setLineSpacing(lineHeight - textSize, 1);
            }
            float textsize = tv.getTextSize();
            int num = 0;
            boolean flag = false;
            if (left % textsize != 0) {
                flag = true;
            }
            num = (int) (left / textsize);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < num; i++) {
                sb.append("\u3000");
            }
            if (flag) {
                sb.append("\u0020");
            }
            sb.append(text);
            tv.setText(sb.toString());
            if (!existflag) {
                this.addView(tv);
            }
        } catch (Exception e) {
            Logger.L.error("build text view error:", e);
        }
    }

}
