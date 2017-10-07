/**
 * <com.rongxun.common.tagtext.TagTextView
 * android:id="@+id/ttview"
 * android:layout_width="match_parent"
 * android:layout_height="wrap_content"
 * android:paddingLeft="10dp"
 * android:paddingTop="10dp" >
 * </com.rongxun.common.tagtext.TagTextView>
 * <p>
 * private TagTextView mttview = null;
 * <p>
 * mttview = (TagTextView) findViewById(R.id.ttview);
 * List<TagItemProperties> tags = new ArrayList<TagItemProperties>();
 * tags.add(new TagItemProperties("标签名称"));
 * mttview.setTagItems(tags);
 * //标签显示位置（文本最前面或最后面）
 * mttview.setTagPosition(TagPosition.Start);
 * //标签项背景色
 * mttview.setTagItemBackgroundResource(R.drawable.tag_bg_selector);
 * //标签与文本之间的间距
 * mttview.setTagAndTextSpacing(10);
 * //标签之间间距
 * mttview.setTagSpacing(8);
 * //文本颜色
 * mttview.setTextColor(Color.parseColor("#323232"));
 * //文本大小
 * mttview.setTextSize(12);
 * //标签文本颜色
 * mttview.setTagTextColor(Color.parseColor("#ffffff"));
 * //标签文本大小
 * mttview.setTagTextSize(12);
 * //行高
 * mttview.setLineHeight(16);
 * mttview.setText("文本");
 */
package com.cloud.core.tagtext;

class TagTextPkg {
    public static final int TAG_TEXT_ID = 199882027;
    public static final int TAG_ITEM_VIEW_TAG = 614535073;
}