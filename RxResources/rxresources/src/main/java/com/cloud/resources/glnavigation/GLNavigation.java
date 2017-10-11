/**
 * @Title: GLNavigation.java
 * @Description: 分组或字母导航
 * @author: lijinghuan
 * @data: 2014-12-27 下午1:27:42
 */
package com.cloud.resources.glnavigation;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AlphabetIndexer;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cloud.core.utils.PixelUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

public abstract class GLNavigation<T> {

    private CharSequence alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private Toast toast = null;
    private TextView toastdisplaytv = null;
    protected AlphabetIndexer indexer;
    private GLAdapter adapter = null;
    private AbsListView abslv = null;
    private LetterView mlv = null;
    private View viewOverlayView = null;
    private TextView tvOverlay = null;
    private T dataobj = null;
    private String sortedColumnName = "";
    private HashMap<String, Integer> gpNameIndex = new HashMap<String, Integer>();

    /**
     * 定位字母显示文本大小(长、宽)
     */
    private int POSITION_LETTER_DISPLAY_TEXT_SIZE = 45;

    public void instance(LetterView lv, AbsListView alv, T dataobj,
                         String sortedColumnName) {
        this.abslv = alv;
        this.mlv = lv;
        this.dataobj = dataobj;
        this.sortedColumnName = sortedColumnName;
        if (lv != null) {
            initToast(lv.getContext());
            lv.setOnLetterChangeListener(lclistener);
        }
        if (alv != null) {
            if (dataobj instanceof Cursor) {
                Cursor cursor = (Cursor) dataobj;
                int sortedColumnIndex = cursor.getColumnIndex(sortedColumnName);
                indexer = new AlphabetIndexer(cursor, sortedColumnIndex,
                        alphabet);
            } else if (dataobj instanceof List<?>) {
                List<?> lst = (List<?>) dataobj;
                for (int i = 0; i < lst.size(); i++) {
                    String curstr = getPropertiesValue(lst.get(i));
                    if (!gpNameIndex.containsKey(curstr)) {
                        gpNameIndex.put(curstr, i);
                    }
                }
            }
            adapter = new GLAdapter();
            alv.setOnScrollListener(scrollListener);
            alv.setAdapter(adapter);
        }
    }

    /**
     * 设置主题覆盖层
     *
     * @param viewOverlay 覆盖层视图
     * @return void
     */
    public void setSubjectOverlayView(ViewGroup v) {
        this.viewOverlayView = v;
        if (v != null) {
            int count = v.getChildCount();
            for (int i = 0; i < count; i++) {
                View cv = v.getChildAt(0);
                if (cv instanceof TextView) {
                    tvOverlay = (TextView) cv;
                    break;
                }
            }
        }
    }

    protected View AbsLVGetviewListener(int position, int sectionIndex,
                                        View convertView, ViewGroup parent, Cursor cursor) {
        return convertView;
    }

    protected View AbsLVGetviewListener(int position, int sectionIndex,
                                        View convertView, ViewGroup parent, T dataobj) {
        return convertView;
    }

    ;

    private void initToast(Context context) {
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(new ToastView(context));
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    private class ToastView extends LinearLayout {
        public ToastView(Context context) {
            super(context);
            LayoutParams tparam = new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.MATCH_PARENT);
            setOrientation(VERTICAL);
            setLayoutParams(tparam);
            toastdisplaytv = new DisplayText(context);
            addView(toastdisplaytv);
        }

        private class DisplayText extends TextView {
            public DisplayText(Context context) {
                super(context);
                int size = PixelUtils.dip2px(context,
                        POSITION_LETTER_DISPLAY_TEXT_SIZE);
                LayoutParams tvparam = new LayoutParams(
                        size, size);
                this.setGravity(Gravity.CENTER);
                this.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
                this.setBackgroundDrawable(createDisplayTextBackground(context));
                this.setLayoutParams(tvparam);
            }

            private GradientDrawable createDisplayTextBackground(Context context) {
                GradientDrawable gd = new GradientDrawable();
                gd.setShape(GradientDrawable.OVAL);
                gd.setColor(Color.parseColor("#99305B9D"));
                return gd;
            }
        }
    }

    private LetterView.OnLetterChangeListener lclistener = new LetterView.OnLetterChangeListener() {
        @Override
        public void onLetterChange(int selectedIndex) {
            int position = 0;
            String mcstr = Character.toString(alphabet.charAt(selectedIndex));
            if (dataobj instanceof Cursor) {
                position = indexer.getPositionForSection(selectedIndex);
            } else if (dataobj instanceof List<?>) {
                Integer idx = gpNameIndex.get(mcstr);
                position = (idx == null ? 0 : idx);
            }
            abslv.setSelection(position);
            toastdisplaytv.setText(mcstr);
            toast.show();
        }

        @Override
        public void onLetterChanged() {
            toast.cancel();
        }
    };

    private String getPropertiesValue(Object ent) {
        String value = "";
        try {
            Field field = ent.getClass().getDeclaredField(sortedColumnName);
            value = field.get(ent) + "";
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
    }

    private int getSectionForPosition(int position) {
        String value = getPropertiesValue(((List<?>) dataobj).get(position));
        for (int i = 0; i < alphabet.length(); i++) {
            if (TextUtils.equals(Character.toString(alphabet.charAt(i)), value)) {
                return i;
            }
        }
        return 0;
    }

    private int getPositionForSection(int selectedIndex) {
        String mcstr = Character.toString(alphabet.charAt(selectedIndex));
        return gpNameIndex.get(mcstr);
    }

    private OnScrollListener scrollListener = new OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            int pos = 0, section = 0;
            if (dataobj instanceof Cursor) {
                section = indexer.getSectionForPosition(firstVisibleItem);
                if (mlv != null) {
                    mlv.setSelectedIndex(section);
                }
                int nextSection = section + 1;
                pos = indexer.getPositionForSection(nextSection);
            } else if (dataobj instanceof List<?>) {
                section = getSectionForPosition(firstVisibleItem);
                if (mlv != null) {
                    mlv.setSelectedIndex(section);
                }
                int nextSection = section + 1;
                pos = getSectionForPosition(nextSection);
            }
            if (pos == firstVisibleItem + 1) {
                View v = view.getChildAt(0);
                if (v == null) {
                    return;
                }
                int dex = v.getBottom()
                        - (viewOverlayView == null ? 0 : viewOverlayView
                        .getMeasuredHeight());
                if (dex <= 0) {
                    if (viewOverlayView != null) {
                        viewOverlayView.setPadding(0, dex, 0, 0);
                    }
                    if (tvOverlay != null) {
                        tvOverlay.setText(alphabet.charAt(section) + "");
                    }
                } else {
                    if (viewOverlayView != null) {
                        viewOverlayView.setPadding(0, 0, 0, 0);
                    }
                    if (tvOverlay != null) {
                        tvOverlay.setText(alphabet.charAt(section) + "");
                    }
                }
            } else {
                if (viewOverlayView != null) {
                    viewOverlayView.setPadding(0, 0, 0, 0);
                }
                if (tvOverlay != null) {
                    tvOverlay.setText(alphabet.charAt(section) + "");
                }
            }
        }
    };

    private class GLAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (dataobj instanceof Cursor) {
                return dataobj == null ? 0 : ((Cursor) dataobj).getCount();
            } else if (dataobj instanceof List<?>) {
                return dataobj == null ? 0 : ((List<?>) dataobj).size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            if (dataobj == null) {
                return null;
            } else {
                if (dataobj instanceof Cursor) {
                    ((Cursor) dataobj).moveToPosition(position);
                    return dataobj;
                } else if (dataobj instanceof List<?>) {
                    return ((List<?>) dataobj).get(position);
                } else {
                    return null;
                }
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (dataobj instanceof Cursor) {
                Cursor cursor = (Cursor) dataobj;
                cursor.moveToPosition(position);
                int section = indexer.getSectionForPosition(position);
                int pos = indexer.getPositionForSection(section);
                return AbsLVGetviewListener(position, pos, convertView, parent,
                        cursor);
            } else if (dataobj instanceof List<?>) {
                int section = getSectionForPosition(position);
                int pos = getPositionForSection(section);
                return AbsLVGetviewListener(position, pos, convertView, parent,
                        dataobj);
            } else {
                return convertView;
            }
        }
    }
}
