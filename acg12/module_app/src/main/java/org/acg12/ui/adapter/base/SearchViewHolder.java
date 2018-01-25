package org.acg12.ui.adapter.base;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.kk.ui.base.KKBaseRecyclerView;
import com.acg12.kk.utils.ViewUtil;
import com.acg12.kk.utils.loadimage.ImageLoadUtils;

import org.acg12.R;
import org.acg12.entity.Search;

import java.util.List;

/**
 * Created by Administrator on 2018/1/18.
 */

public class SearchViewHolder extends KKBaseRecyclerView {

    ImageView iv_search_tag;
    TextView tv_search_tag;

    public SearchViewHolder(View itemView) {
        super(itemView);
        iv_search_tag = (ImageView)itemView.findViewById(R.id.iv_search_tag);
        tv_search_tag = (TextView)itemView.findViewById(R.id.tv_search_tag);
    }

    @Override
    public void bindData(Context mContext, List list, int position) {
        Search search = ((List<Search>)list).get(position);
        ImageLoadUtils.glideLoading(search.getSource() , iv_search_tag);
        ViewUtil.setText(tv_search_tag , search.getTitle());

    }
}
