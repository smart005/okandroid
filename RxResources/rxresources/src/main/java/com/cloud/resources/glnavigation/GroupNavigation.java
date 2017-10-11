package com.cloud.resources.glnavigation;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cloud.core.ObjectJudge;
import com.cloud.core.utils.GlobalUtils;
import com.cloud.resources.beans.BasePinnedSectionItem;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-17 下午3:21:47
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public class GroupNavigation<T extends BasePinnedSectionItem, TView extends PinnedSectionListView> {

    private CharSequence alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private List<T> datalist = new ArrayList<T>();
    private GroupNavigationAdapter curradapter = null;
    private int groupType = 1;
    private TView mpslv = null;
    private LetterView letterView = null;
    private HashMap<String, Integer> gpNameIndex = new HashMap<String, Integer>();
    private String groupColumnName = "groupName";

    public void instance(TView pslview, List<T> lst) {
        datalist = lst;
        mpslv = pslview;
        curradapter = new GroupNavigationAdapter();
        pslview.setOnPinnedSectionListener(new OnPinnedSectionListener() {
            @Override
            public void onFullScreenListener(boolean flag) {
//                mpslv.setFastScrollEnabled(flag);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
//                    mpslv.setFastScrollAlwaysVisible(flag);
//                }
            }
        });
        pslview.setOnPinnedScrollListener(pinnedScrollListener);
        curradapter.setGroupType(groupType);
        curradapter.setDataList(datalist);
        pslview.setAdapter(curradapter);
    }

    /**
     * 设置字母视图
     *
     * @param letterView
     */
    public void setLetterView(LetterView letterView) {
        this.letterView = letterView;
        if (this.letterView != null) {
            this.letterView.setOnLetterChangeListener(lclistener);
        }
    }

    /**
     * 设置组列名
     *
     * @param groupColumnName 组列名
     */
    public void setGroupColumnName(String groupColumnName) {
        this.groupColumnName = groupColumnName;
    }

    protected View getItemView(int position, int sectionIndex, View convertView, T item) {
        return null;
    }

    protected View getItemView(int position, View convertView, T item) {
        return null;
    }

    /**
     * @return 获取groupType
     */
    public int getGroupType() {
        return groupType;
    }

    public class GroupNavigationAdapter extends BasePinnedAdapter<T> {
        @Override
        protected View getListItemView(int position, View convertView, ViewGroup parent, T item) {
            if (letterView != null) {
                int section = getSectionForPosition(position);
                int pos = getPositionForSection(section);
                return getItemView(position, pos, convertView, item);
            } else {
                return getItemView(position, convertView, item);
            }
        }
    }

    public GroupNavigationAdapter getCurrAdapter() {
        return curradapter;
    }

    private OnPinnedScrollListener pinnedScrollListener = new OnPinnedScrollListener() {
        @Override
        public void onPinnedScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (letterView == null || ObjectJudge.isNullOrEmpty(datalist)) {
                return;
            }
            int pos = 0, section = 0;
            section = getSectionForPosition(firstVisibleItem);
            letterView.setSelectedIndex(section);
        }
    };

    private LetterView.OnLetterChangeListener lclistener = new LetterView.OnLetterChangeListener() {
        @Override
        public void onLetterChange(int selectedIndex) {
            int position = 0;
            String mcstr = Character.toString(alphabet.charAt(selectedIndex));
            Integer idx = gpNameIndex.get(mcstr);
            position = (idx == null ? 0 : idx);
            mpslv.setSelection(position);
        }

        @Override
        public void onLetterChanged() {

        }
    };

    private int getSectionForPosition(int position) {
        T t = datalist.get(position);
        String value = String.valueOf(GlobalUtils.getSuperPropertiesValue(t,this.groupColumnName));
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

    public void updateGroupIndex() {
        gpNameIndex.clear();
        for (int i = 0; i < datalist.size(); i++) {
            T t = datalist.get(i);
            String curstr = String.valueOf(GlobalUtils.getSuperPropertiesValue(t,this.groupColumnName));
            if (!gpNameIndex.containsKey(curstr)) {
                gpNameIndex.put(curstr, i);
            }
        }
    }
}
