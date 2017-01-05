package org.acg12.ui.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skin.loader.loader.SkinManager;

import org.acg12.R;
import org.acg12.bean.Skin;
import org.acg12.utlis.ViewUtil;

/**
 * Created by DELL on 2016/12/29.
 */
public class SkinLoaderViewHolder extends RecyclerView.ViewHolder {

    ImageView skin_bg;
    ImageView skin_select;
    TextView skin_name;
    TextView skin_switch;

    public SkinLoaderViewHolder(View itemView) {
        super(itemView);
        skin_bg = (ImageView) itemView.findViewById(R.id.skin_bg);
        skin_select = (ImageView) itemView.findViewById(R.id.skin_select);
        skin_name = (TextView) itemView.findViewById(R.id.skin_name);
        skin_switch = (TextView) itemView.findViewById(R.id.skin_switch);
    }

    public void bindData(int position ,Skin skin, View.OnClickListener listener) {
        skin_bg.setBackgroundColor(skin.getColor());
        skin_name.setTextColor(skin.getColor());
        ViewUtil.setText(skin_name, skin.getName());

        boolean isOfficalSelected = !SkinManager.getInstance().isExternalSkin();
        if(isOfficalSelected){
            if(position == 0){
                ViewUtil.setText(skin_switch, "使用中");
                skin_select.setVisibility(View.VISIBLE);
                skin_switch.setSelected(true);
            }else {
                ViewUtil.setText(skin_switch, "使用");
                skin_select.setVisibility(View.GONE);
                skin_switch.setSelected(false);
            }
        }else {
            String path = SkinManager.getInstance().getSkinPath();
            Log.e("path", path + "");
            if (path == null) {
                path = "";
            }
            if (skin.getPath().equals(path)) {
                ViewUtil.setText(skin_switch, "使用中");
                skin_select.setVisibility(View.VISIBLE);
                skin_switch.setSelected(true);
            } else {
                ViewUtil.setText(skin_switch, "使用");
                skin_select.setVisibility(View.GONE);
                skin_switch.setSelected(false);
            }
        }

        skin_switch.setOnClickListener(listener);
    }

}
