/**
 * <com.rongxun.resources.arcs.ArcMenu
 *    android:id="@+id/footer_main_buy"
 *    android:layout_width="match_parent"
 *    android:layout_height="wrap_content"
 *    android:layout_above="@id/main_footer_bar"
 *    android:layout_centerHorizontal="true"
 *    android:layout_marginBottom="-146dp"
 *    appext:childSize="@dimen/mainArcMenuChildSize"
 *    appext:fromDegrees="@dimen/mainArcMenuFromDegrees"
 *    appext:toDegrees="@dimen/mainArcMenuToDegrees"></com.rongxun.resources.arcs.ArcMenu>
 *
 *    <item name="mainArcMenuFromDegrees" format="float" type="dimen">210.0</item>
 *    <item name="mainArcMenuToDegrees" format="float" type="dimen">330.0</item>
 *    <dimen name="mainArcMenuChildSize">36dp</dimen>
 *
 *   private int[] buyItemIcons = {R.drawable.composer_camera, R.drawable.composer_music, R.drawable.composer_place, R.drawable.composer_place};
 *
 *    arcMenu.setVertexBackgroundSelector(R.drawable.main_buy_vertex_bg_selector);
 *    arcMenu.setVertexHintImageResourceSelector(R.drawable.main_buy_vertex_hint_images_selector);
 *    arcMenu.setVertexSize(TypedValue.COMPLEX_UNIT_DIP, 0);
 *    arcMenu.setRadius(PixelUtils.dip2px(arcMenu.getContext(), 116));
 *    for (int i = 0; i < buyItemIcons.length; i++) {
 *        ImageView item = new ImageView(arcMenu.getContext());
 *        item.setImageResource(buyItemIcons[i]);
 *        arcMenu.addItem(item, new View.OnClickListener() {
 *           @Override
 *           public void onClick(View v) {
 *
 *           }
 *       });
 *    }
 */
package com.cloud.resources.arcs;