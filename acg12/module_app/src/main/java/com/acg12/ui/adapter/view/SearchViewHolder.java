package com.acg12.ui.adapter.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import com.acg12.R;
import com.acg12.entity.Search;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class SearchViewHolder extends CommonRecyclerViewHolder {

    ImageView iv_search_tag;
    TextView tv_search_tag;
    TextView tv_search_type;

    public SearchViewHolder(View itemView) {
        super(itemView);
        iv_search_tag = itemView.findViewById(R.id.iv_search_tag);
        tv_search_tag = itemView.findViewById(R.id.tv_search_tag);
        tv_search_type = itemView.findViewById(R.id.tv_search_type);
    }

    @Override
    public void bindData(Context mContext, List list, int position) {
        Search search = ((List<Search>)list).get(position);
        ImageLoadUtils.glideLoading(mContext ,search.getSource() , iv_search_tag);
        if(search.getNameCn() != null &&!search.getNameCn().isEmpty()){
            ViewUtil.setText(tv_search_tag , search.getNameCn());
        } else {
            ViewUtil.setText(tv_search_tag , search.getTitle());
        }
        ViewUtil.setText(tv_search_type , search.getTypeName());
    }
}
