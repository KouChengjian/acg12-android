package org.acg12.ui.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skin.loader.listener.ILoaderListener;
import com.skin.loader.loader.SkinManager;
import com.skin.loader.utils.L;

import org.acg12.R;
import org.acg12.bean.Skin;
import org.acg12.ui.adapter.base.SkinLoaderViewHolder;
import org.acg12.utlis.CacheUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/29.
 */
public class SkinLoaderAdapter extends RecyclerView.Adapter<SkinLoaderViewHolder> {

    private Context mContext;
    private List<Skin> mList;
    private final LayoutInflater mInflater;

    public SkinLoaderAdapter(Context mContext) {
        this(mContext, new ArrayList<Skin>());
    }

    public SkinLoaderAdapter(Context mContext, List<Skin> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(mContext);
    }

    public void addAll(List<Skin> mList) {
        this.mList.addAll(mList);
    }

    public void add(Skin home) {
        this.mList.add(home);
    }

    public void setList(List<Skin> mList) {
        this.mList = mList;
    }

    public List<Skin> getList() {
        return mList;
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public SkinLoaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_skin_loader, parent, false);
        return new SkinLoaderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SkinLoaderViewHolder holder, int position) {
        holder.bindData(position, mList.get(position), new SwitchBtn(position));
    }


    class SwitchBtn implements View.OnClickListener {
        int position;

        public SwitchBtn(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if(position == 0){
                SkinManager.getInstance().restoreDefaultTheme();
                notifyDataSetChanged();
            } else {
                String name = mList.get(position).getPath();
                if (name == null || name.isEmpty()) {
                    return;
                } else {
                    skinLoading(copySkinAssetsToDir(mContext , name ));
                }
            }
        }
    }

    public void skinLoading(String path) {
        File skin = new File(path);
        SkinManager.getInstance().load(skin.getAbsolutePath(), new ILoaderListener() {
            @Override
            public void onStart() {
                L.e("startloadSkin");
            }

            @Override
            public void onSuccess() {
                L.e("loadSkinSuccess");
                notifyDataSetChanged();
            }

            @Override
            public void onFailed() {
                L.e("loadSkinFail");
            }
        });
    }

    /**
     *  file:///android_asset/skin_pink.skin
     */
    public String copySkinAssetsToDir(Context context, String name) {
        String toFile = CacheUtils.getCacheDirectory(context, true, "skin") + File.separator + name;
        Log.e("toFile",toFile+"");
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
            Log.e("IOException",e.toString());
        }
        return toFile;
    }

//    public void resetSkin(int position){
//        for (int i = 0 ; i < mList.size() ; i++) {
//            Skin skin = mList.get(position);
//            if(i == position){
//                skin.setHasSelector(true);
//            } else {
//                skin.setHasSelector(false);
//            }
//        }
//    }

}
