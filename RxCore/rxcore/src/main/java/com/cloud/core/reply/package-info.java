/**
 * 1.回复组件
 *<ReplyTextView
 *   android:id="@+id/comment_rtv"
 *   android:layout_width="match_parent"
 *   android:layout_height="wrap_content"
 *   android:textColor="@color/reply_content_color"
 *   android:textSize="@dimen/font_size_12" />
 *
 *   commentRtv.setFrom("from person");
 *   commentRtv.setTo("to person");
 *   commentRtv.setContent("回复内容", 任意回调对象);
 *   commentRtv.setOnReplyLinkClickListener(new ReplyLinkClickListener<回调对象类型>() {
 *  @Override
 *  public void onReplyLinkClick(回调对象类型 obj, ReplyLinkTarget replyLinkTarget) {
 *
 *  }
 *  });
 *  2.列表组件
 *<ReplyLinksView
 *  android:id="@+id/comment_rtv"
 *  android:layout_width="match_parent"
 *  android:layout_height="wrap_content"
 *  android:textColor="@color/reply_content_color"
 *  android:textSize="@dimen/font_size_12" />
 *
 *  //文本颜色
 *  commentRtv.setLinkColor("#576b95");
 *  commentRtv.setOnReplyLinkClickListener(new ReplyLinkClickListener<String>() {
 *  @Override
 *  public void onReplyLinkClick(String obj, ReplyLinkTarget replyLinkTarget) {
 *
 *  }
 *  });
 *  //追加链接项
 *  commentRtv.appendLink("显示内容", "属性对象或唯一标识（在onReplyLinkClick第一个参数返回）");
 */
package com.cloud.core.reply;
