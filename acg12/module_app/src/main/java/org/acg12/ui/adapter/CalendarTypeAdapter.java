package org.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.acg12.R;
import org.acg12.entity.Calendar;
import org.acg12.entity.Video;
import org.acg12.ui.adapter.view.CalendarTypeViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2018/7/3 15:26
 * Description:
 */
public class CalendarTypeAdapter extends RecyclerView.Adapter<CalendarTypeViewHolder>{

    private Context mContext;
    private List<Calendar> mList;

    public CalendarTypeAdapter(Context mContext){
        this(mContext , new ArrayList<Calendar>());
    }

    public CalendarTypeAdapter(Context mContext, List<Calendar> mList){
        this.mContext = mContext;
        this.mList = mList;
    }

    public void addAll(List<Calendar> mList){
        this.mList.addAll(mList);
    }

    public void add(Calendar home){
        this.mList.add(home);
    }

    public void setList(List<Calendar> mList){
        this.mList =  mList;
    }

    public List<Calendar> getList(){
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public CalendarTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_calendar, parent, false);
        return new CalendarTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarTypeViewHolder holder, int position) {
        holder.bindData(mContext , mList.get(position));
    }
}
