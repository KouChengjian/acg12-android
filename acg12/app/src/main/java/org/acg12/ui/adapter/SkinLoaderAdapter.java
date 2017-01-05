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
            String path = mList.get(position).getPath();
            if (path == null || path.isEmpty()) {
                SkinManager.getInstance().restoreDefaultTheme();
                notifyDataSetChanged();
            } else {
                if(path.contains("_skin")){
                    String files = CacheUtils.getCacheDirectory(mContext, true, "skin") + "/"+mList.get(position).getColor()+".skin";
                    File file = new File(files);
                    if (file.exists()) {
                        file.delete();
                    }
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    AssetManager asset = mContext.getAssets();
                    InputStream input = null;
                    try {
                        input = asset.open(path.split("_skin")[0]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //writeBytesToFile(input ,file);
                } else{
                    skinLoading(path);
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

    public void writeBytesToFile(InputStream is, File file) {
        FileOutputStream fos = null;
        try {
            byte[] data = new byte[2048];
            int nbread = 0;
            fos = new FileOutputStream(file);
            while ((nbread = is.read(data)) > -1) {
                fos.write(data, 0, nbread);
            }
            fos.close();
            skinLoading(file.getPath());
        } catch (IOException ex) {
            Log.e("IOException", ex.toString() + "");
        }
    }
}
