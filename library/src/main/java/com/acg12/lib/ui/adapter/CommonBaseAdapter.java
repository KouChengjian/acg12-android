package com.acg12.lib.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2018/8/23 9:37
 * Description:
 */
public abstract class CommonBaseAdapter<T> extends BaseAdapter {

    protected Context context;
    private LayoutInflater inflater;
    protected List<T> data = new ArrayList<>();
    private int resouceId;

    public CommonBaseAdapter(Context context, int resourceId){
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.resouceId = resourceId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(resouceId, parent, false);
            newView(convertView,position);
        }
        holderView(convertView,data.get(position),position);
        return convertView;
    }

    public void setData(List<T> data){
        this.data = data;
    }

    public List<T> getData(){
        return data;
    }

    /**
     * 用于覆盖，在各个其他adapter里边返回id,默认返回position
     * @param position
     * @param object
     * @return
     */
    protected long convertId(int position, Object object){
        return position;
    }

    /**
     * 第一次创建的时�?调用
     * @param convertView
     */
    protected abstract void newView(View convertView, int position);

    /**
     * 用于数据赋�?等等
     * @param convertView
     * @param itemObject
     */
    protected abstract void holderView(View convertView, T itemObject, int position);
}
