package org.acg12.ui.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.bean.Palette;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.ScaleImageView;

import java.util.List;

/**
 * Created by DELL on 2016/12/23.
 */
public class TabPaletteViewHolder extends RecyclerView.ViewHolder {

    public TextView tv_palete_name;
    public TextView tv_palete_num;
    public ScaleImageView imageView;
    public ImageView iv_palete_icon_1;
    public ImageView iv_palete_icon_2;
    public ImageView iv_palete_icon_3;

    public TabPaletteViewHolder(View itemView) {
        super(itemView);
        tv_palete_name = (TextView)itemView.findViewById(R.id.tv_fragment_palete_name);
        tv_palete_num = (TextView)itemView.findViewById(R.id.tv_fragment_palete_num);
        imageView = (ScaleImageView)itemView.findViewById(R.id.iv_fragment_palete);
        iv_palete_icon_1 = (ImageView)itemView.findViewById(R.id.iv_fragment_palete_icon_1);
        iv_palete_icon_2 = (ImageView)itemView.findViewById(R.id.iv_fragment_palete_icon_2);
        iv_palete_icon_3 = (ImageView)itemView.findViewById(R.id.iv_fragment_palete_icon_3);
    }

    public void bindData(Context context , Palette palette){
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
                    ImageLoadUtils.glideLoading(context , paletteList.get(i) , imageView);
                    iv_palete_icon_1.setImageResource(R.mipmap.bg_loading_pic);
                    iv_palete_icon_2.setImageResource(R.mipmap.bg_loading_pic);
                    iv_palete_icon_3.setImageResource(R.mipmap.bg_loading_pic);
                } else if(i == 1){
                    ImageLoadUtils.glideLoading(context , paletteList.get(i) , iv_palete_icon_1);
                    iv_palete_icon_2.setImageResource(R.mipmap.bg_loading_pic);
                    iv_palete_icon_3.setImageResource(R.mipmap.bg_loading_pic);
                } else if(i == 2){
                    ImageLoadUtils.glideLoading(context , paletteList.get(i) , iv_palete_icon_2);
                    iv_palete_icon_3.setImageResource(R.mipmap.bg_loading_pic);
                } else if(i == 3){
                    ImageLoadUtils.glideLoading(context , paletteList.get(i) , iv_palete_icon_3);
                }
            }
        }
    }
}
