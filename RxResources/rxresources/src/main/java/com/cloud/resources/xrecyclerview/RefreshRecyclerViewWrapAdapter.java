package com.cloud.resources.xrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 *  实现显示头部和尾部item的adapter,把头部尾部的事情交给这个adapter来做,其他的交给子adapter
 */
public class RefreshRecyclerViewWrapAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public ArrayList<View> headerViews=new ArrayList<>();
    public ArrayList<View> footViews=new ArrayList<>();
    public RecyclerView.Adapter adapter;

    public RefreshRecyclerViewWrapAdapter(RecyclerView.Adapter adapter, ArrayList<View> headerViews, ArrayList footViews){
        this.adapter=adapter;
        this.headerViews=headerViews;
        this.footViews=footViews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType== RecyclerView.INVALID_TYPE){
            //头部item
            return new RecyclerView.ViewHolder(headerViews.get(0)){};
        }else if(viewType== (RecyclerView.INVALID_TYPE-1)){
            //尾部item
            return new RecyclerView.ViewHolder(footViews.get(0)){};
        }
        return adapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(position>=0&&position<headerViews.size()){
            return;
        }

        if(adapter!=null){
            int p=position-headerViews.size();
            if(p<adapter.getItemCount()){
                adapter.onBindViewHolder(holder,p);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position>=0&&position<headerViews.size()){
            //如果是头部则返回一个不可用的标识,表示这是头部item
            return RecyclerView.INVALID_TYPE;
        }

        if(adapter!=null){
            int p=position-headerViews.size();
            if(p<adapter.getItemCount()){
                return adapter.getItemViewType(p);
            }
        }

        return RecyclerView.INVALID_TYPE-1;//默认返回表示是尾部的item
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    public int getCount(){
        int count=headerViews.size()+footViews.size();
        if(adapter!=null){
            count+=adapter.getItemCount();
        }
        return count;
    }

}
