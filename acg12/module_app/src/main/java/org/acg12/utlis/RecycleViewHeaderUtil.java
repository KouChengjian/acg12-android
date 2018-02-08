package org.acg12.utlis;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.utils.LogUtil;

import org.acg12.entity.News;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/1/29.
 */

public class RecycleViewHeaderUtil extends RecyclerView.OnScrollListener {

    private LinearLayoutManager linearLayoutManager;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private int mSuspensionHeight;
    private int mCurrentPosition = 0;
    private List list;

    public RecycleViewHeaderUtil(LinearLayoutManager linearLayoutManager, RelativeLayout mSuspensionBar, TextView mSuspensionTv) {
        this.linearLayoutManager = linearLayoutManager;
        this.mSuspensionBar = mSuspensionBar;
        this.mSuspensionTv = mSuspensionTv;
    }

    public void setList(List list){
        this.list = list;
    }

    public void addList(List list){
        this.list.addAll(list);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        mSuspensionHeight = mSuspensionBar.getHeight();

    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
        LogUtil.e(mCurrentPosition+"==");
        if(list == null){
            return;
        }

        News curNews = (News)list.get(mCurrentPosition);
        int curTime = curNews.getCreateTime();
        News lastNews = (News)list.get(mCurrentPosition + 1);
        int lastTime = lastNews.getCreateTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate = sdf.format(new Date(curTime* 1000l));
        String lastDate = sdf.format(new Date(lastTime* 1000l));

        if(curDate.equals(lastDate)){
            updateSuspensionBar();
            return;
        }

        View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
        if (view != null) {
            if (view.getTop() <= mSuspensionHeight) {
                mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
            } else {
                mSuspensionBar.setY(0);
            }
        }

        if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
            mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
            mSuspensionBar.setY(0);

            updateSuspensionBar();
        }
    }

    private void updateSuspensionBar() {
        News curNews = (News)list.get(mCurrentPosition);
        SimpleDateFormat sdf = new SimpleDateFormat("MM年dd月");
        String curDate = sdf.format(new Date(curNews.getCreateTime() * 1000l));
        mSuspensionTv.setText(curDate);
    }
}
