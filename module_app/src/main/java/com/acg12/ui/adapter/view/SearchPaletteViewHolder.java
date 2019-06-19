package com.acg12.ui.adapter.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.Palette;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.BGButton;
import com.acg12.lib.widget.ScaleImageView;
import com.acg12.ui.adapter.SearchPaletteAdapter;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/28 20:35
 * Description:
 */
public class SearchPaletteViewHolder extends CommonRecyclerViewHolder {

    public TextView tv_palete_name;
    public TextView tv_palete_num;
    public ScaleImageView imageView;
    public ImageView iv_palete_icon_1;
    public ImageView iv_palete_icon_2;
    public ImageView iv_palete_icon_3;
    private BGButton btn_search_palete_collect;

    private SearchPaletteAdapter.SearchPaletteListener searchPaletteListener;

    public SearchPaletteViewHolder(View itemView) {
        super(itemView);
        tv_palete_name = itemView.findViewById(R.id.tv_fragment_palete_name);
        tv_palete_num = itemView.findViewById(R.id.tv_fragment_palete_num);
        imageView = itemView.findViewById(R.id.iv_fragment_palete);
        iv_palete_icon_1 = itemView.findViewById(R.id.iv_fragment_palete_icon_1);
        iv_palete_icon_2 = itemView.findViewById(R.id.iv_fragment_palete_icon_2);
        iv_palete_icon_3 = itemView.findViewById(R.id.iv_fragment_palete_icon_3);
        btn_search_palete_collect = itemView.findViewById(R.id.btn_search_palete_collect);
    }

    public void setSearchPaletteListener(SearchPaletteAdapter.SearchPaletteListener searchPaletteListener) {
        this.searchPaletteListener = searchPaletteListener;
    }

    @Override
    public void bindData(Context mContext, List list, final int position) {
        super.bindData(mContext, list, position);
        Palette palette = (Palette)list.get(position);

        ViewUtil.setText(tv_palete_name , palette.getName());
        if(palette.getNum() == 0){
            tv_palete_num.setVisibility(View.GONE);
        }else{
            tv_palete_num.setVisibility(View.VISIBLE);
            ViewUtil.setText(tv_palete_num , palette.getNum()+"");
        }

        List<String> paletteList = palette.getUrlAlbum();
        if(paletteList != null && !paletteList.isEmpty()) {
            imageView.setImageWidth(250);
            imageView.setImageHeight(350);
            for (int i = 0; i < paletteList.size(); i++) {
                if(i == 0){
                    ImageLoadUtils.glideLoading(mContext , paletteList.get(i).replace("_fw658" , "_fw236") , imageView);
                    iv_palete_icon_1.setImageResource(R.mipmap.bg_loading_pic);
                    iv_palete_icon_2.setImageResource(R.mipmap.bg_loading_pic);
                    iv_palete_icon_3.setImageResource(R.mipmap.bg_loading_pic);
                } else if(i == 1){
                    ImageLoadUtils.glideLoading(mContext , paletteList.get(i).replace("_fw658" , "_fw236") , iv_palete_icon_1);
                    iv_palete_icon_2.setImageResource(R.mipmap.bg_loading_pic);
                    iv_palete_icon_3.setImageResource(R.mipmap.bg_loading_pic);
                } else if(i == 2){
                    ImageLoadUtils.glideLoading(mContext , paletteList.get(i).replace("_fw658" , "_fw236") , iv_palete_icon_2);
                    iv_palete_icon_3.setImageResource(R.mipmap.bg_loading_pic);
                } else if(i == 3){
                    ImageLoadUtils.glideLoading(mContext , paletteList.get(i).replace("_fw658" , "_fw236") , iv_palete_icon_3);
                }
            }
        }

        if(palette.getIsCollect() == 1){
            btn_search_palete_collect.setText("已收藏");
        } else {
            btn_search_palete_collect.setText("收藏");
        }

        btn_search_palete_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchPaletteListener != null){
                    searchPaletteListener.onClickCollect(position);
                }
            }
        });
    }
}
