package org.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.acg12.R;
import org.acg12.bean.Skin;
import org.acg12.ui.adapter.base.SkinLoaderViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/29.
 */
public class SkinLoaderAdapter extends RecyclerView.Adapter<SkinLoaderViewHolder> {

    private Context mContext;
    private List<Skin> mList;
    private final LayoutInflater mInflater;

    public SkinLoaderAdapter(Context mContext){
        this(mContext , new ArrayList<Skin>());
    }

    public SkinLoaderAdapter(Context mContext, List<Skin> mList){
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void addAll(List<Skin> mList){
        this.mList.addAll(mList);
    }

    public void add(Skin home){
        this.mList.add(home);
    }

    public void setList(List<Skin> mList){
        this.mList =  mList;
    }

    public List<Skin> getList(){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public SkinLoaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_skin_loader, parent, false);
        return new SkinLoaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SkinLoaderViewHolder holder, int position) {
//        holder.bindData(position);
    }
}
