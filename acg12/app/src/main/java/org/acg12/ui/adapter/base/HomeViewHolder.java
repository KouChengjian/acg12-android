package org.acg12.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kcj.animationfriend.R;

import org.acg12.utlis.ViewUtil;


/**
 * Created by DELL on 2016/12/7.
 */
public class HomeViewHolder extends RecyclerView.ViewHolder{

    TextView tv_num;
    LinearLayout layout_content;

    public HomeViewHolder(View itemView) {
        super(itemView);
        tv_num = (TextView)itemView.findViewById(R.id.tv_num);
        //layout_content = (LinearLayout)itemView.findViewById(R.id.layout_content);
    }

    public void bindData(int position){
        ViewUtil.setText(tv_num ,"sdasda" + position);
    }
}
