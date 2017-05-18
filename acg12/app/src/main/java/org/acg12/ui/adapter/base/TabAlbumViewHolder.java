package org.acg12.ui.adapter.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.bean.Album;
import org.acg12.ui.activity.PreviewAlbumActivity;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.ScaleImageView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DELL on 2016/12/23.
 */
public class TabAlbumViewHolder extends RecyclerView.ViewHolder {

    Context context;
    public ScaleImageView imageView;
    public TextView tv_home_album_comment;
    public LinearLayout ll_home_album_attribute;
    public TextView tv_home_album_collection;
    public TextView tv_home_album_love;

    public TabAlbumViewHolder(View itemView) {
        super(itemView);
        imageView = (ScaleImageView) itemView.findViewById(R.id.iv_album_pic);
        tv_home_album_comment = (TextView) itemView.findViewById(R.id.tv_home_album_comment);
        ll_home_album_attribute = (LinearLayout) itemView.findViewById(R.id.ll_home_album_attribute);
        tv_home_album_collection = (TextView) itemView.findViewById(R.id.tv_home_album_collection);
        tv_home_album_love = (TextView) itemView.findViewById(R.id.tv_home_album_love);
    }

    public void bindData(final Context mContext , final List<Album> mList, final int position){
        this.context = mContext;
        final Album album = mList.get(position);

        String url = album.getImageUrl();
        if(url != null){
            imageView.setImageWidth(album.getResWidth());
            imageView.setImageHeight(album.getResHight());
//            ImageLoadUtils.universalLoading(url , imageView);
            ImageLoadUtils.glideLoading(mContext , url , imageView);
        }

        // 内容
        String content = album.getContent();
        if(content != null && !content.isEmpty()){
            ViewUtil.setText(tv_home_album_comment , content);
            tv_home_album_comment.setVisibility(View.VISIBLE);
        } else {
            tv_home_album_comment.setVisibility(View.GONE);
        }

        // 其他信息
        int collection =  album.getFavorites();
        int love = album.getLove();
        if(collection == 0 && love == 0){
            ll_home_album_attribute.setVisibility(View.GONE);
        }else{
            ll_home_album_attribute.setVisibility(View.VISIBLE);
            tv_home_album_collection.setText(collection+"");
            tv_home_album_love.setText(love+"");
        }

    }
}
