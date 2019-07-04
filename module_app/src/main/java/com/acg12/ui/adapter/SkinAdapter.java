package com.acg12.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.lib.ui.adapter.CommonRecyclerAdapter;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.CacheUtils;
import com.acg12.lib.utils.ToastUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.skin.SkinManager;
import com.acg12.lib.utils.skin.entity.Skin;
import com.acg12.lib.utils.skin.listener.ILoaderListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created with Android Studio.
 * User: KCJ
 * Date: 2019-07-04 17:27
 * Description:
 */
public class SkinAdapter extends CommonRecyclerAdapter<Skin> {

    public SkinAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public RecyclerView.ViewHolder createView(ViewGroup parent, int viewType) {
        return new ViewHolder(getItemView(R.layout.item_skin, parent));
    }

    @Override
    public void bindView(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(mContext, getList(), position);
    }

    public class ViewHolder extends CommonRecyclerViewHolder {

        ImageView skin_bg;
        ImageView skin_select;
        TextView skin_name;
        TextView skin_switch;

        public ViewHolder(View itemView) {
            super(itemView);
            skin_bg = itemView.findViewById(R.id.skin_bg);
            skin_select = itemView.findViewById(R.id.skin_select);
            skin_name = itemView.findViewById(R.id.skin_name);
            skin_switch = itemView.findViewById(R.id.skin_switch);
        }

        @Override
        public void bindData(Context context, List list, int position) {
            super.bindData(context, list, position);
            Skin skin = (Skin) list.get(position);
            skin_bg.setBackgroundColor(skin.getColor());
            skin_name.setTextColor(skin.getColor());
            ViewUtil.setText(skin_name, skin.getName());

            boolean isOfficalSelected = !SkinManager.getInstance().isExternalSkin();
            //Log.e("isOfficalSelected",isOfficalSelected+"===");
            if (isOfficalSelected) {
                if (position == 0) {
                    ViewUtil.setText(skin_switch, "使用中");
                    skin_select.setVisibility(View.VISIBLE);
                    skin_switch.setSelected(true);
                } else {
                    ViewUtil.setText(skin_switch, "使用");
                    skin_select.setVisibility(View.GONE);
                    skin_switch.setSelected(false);
                }
            } else {
                String path = SkinManager.getInstance().getSkinPath();
                if (path == null) {
                    path = "default";
                }
                if (path.contains(skin.getPath())) {
                    ViewUtil.setText(skin_switch, "使用中");
                    skin_select.setVisibility(View.VISIBLE);
                    skin_switch.setSelected(true);
                } else {
                    ViewUtil.setText(skin_switch, "使用");
                    skin_select.setVisibility(View.GONE);
                    skin_switch.setSelected(false);
                }
            }

            skin_switch.setOnClickListener(new SwitchBtn(position));
        }

        class SwitchBtn implements View.OnClickListener {
            int position;

            public SwitchBtn(int position) {
                this.position = position;
            }

            @Override
            public void onClick(View v) {
                if (position == 0) {
                    SkinManager.getInstance().restoreDefaultTheme();
                    notifyDataSetChanged();
                } else {
                    String name = mList.get(position).getPath();
                    if (name == null || name.isEmpty()) {
                        return;
                    } else {
                        skinLoading(copySkinAssetsToDir(mContext, name));
                    }
                }
            }
        }

        public void skinLoading(String path) {
            File skin = new File(path);
            SkinManager.getInstance().load(skin.getAbsolutePath(), new ILoaderListener() {
                @Override
                public void onStart() {
                }

                @Override
                public void onSuccess() {
                    ToastUtil.showShort("切换成功");
                    notifyDataSetChanged();
                }

                @Override
                public void onFailed() {
                    ToastUtil.showShort("切换失败 /(ㄒoㄒ)/~~");
                }
            });
        }

        /**
         * file:///android_asset/skin_pink.skin
         */
        public String copySkinAssetsToDir(Context context, String name) {
            String toFile = CacheUtils.getCacheDirectory(context, true, "skin") + File.separator + name;
            Log.e("toFile", toFile + "");
            try {
                InputStream is = context.getAssets().open(name);
//            File fileDir = new File(toDir);
//            if (!fileDir.exists()) {
//                fileDir.mkdirs();
//            }
                OutputStream os = new FileOutputStream(toFile);
                int byteCount;
                byte[] bytes = new byte[1024];

                while ((byteCount = is.read(bytes)) != -1) {
                    os.write(bytes, 0, byteCount);
                }
                os.close();
                is.close();

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("IOException", e.toString());
            }
            return toFile;
        }
    }
}
