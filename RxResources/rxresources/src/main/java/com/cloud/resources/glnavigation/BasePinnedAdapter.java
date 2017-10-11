package com.cloud.resources.glnavigation;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cloud.core.ObjectJudge;

import java.util.ArrayList;
import java.util.List;

import com.cloud.resources.beans.BasePinnedSectionItem;

/**
 * @Author lijinghuan
 * @Email:ljh0576123@163.com
 * @CreateTime:2015-11-17 下午2:39:14
 * @Description:
 * @Modifier:
 * @ModifyContent:
 */
public abstract class BasePinnedAdapter<T extends BasePinnedSectionItem>
        extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {

    private List<T> datalist = new ArrayList<T>();
    private int SECTION = 0;

    public void setDataList(List<T> lst) {
        this.datalist = lst;
    }

    public void setGroupType(int type) {
        this.SECTION = type;
    }

    protected abstract View getListItemView(int position, View convertView,
                                            ViewGroup parent, T item);

    @Override
    public int getCount() {
        return ObjectJudge.isNullOrEmpty(datalist) ? 0 : datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return ObjectJudge.isNullOrEmpty(datalist) ? null : datalist
                .get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getListItemView(position, convertView, parent,
                datalist.get(position));
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == SECTION;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        T mitem = datalist.get(position);
        if (mitem == null) {
            return 0;
        } else {
            return mitem.getItemType();
        }
    }
}
