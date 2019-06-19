package com.acg12.ui.adapter.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.entity.Album;
import com.acg12.lib.ui.adapter.CommonRecyclerViewHolder;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;
import com.acg12.lib.widget.BGButton;
import com.acg12.lib.widget.ScaleImageView;
import com.acg12.ui.adapter.SearchAlbumAdapter;

import java.util.List;

/**
 * Created with Android Studio.
 * User: mayn
 * Date: 2019/2/28 20:21
 * Description:
 */
public class SearchAlbumViewHolder extends CommonRecyclerViewHolder {

    public ScaleImageView imageView;
    public TextView tv_home_album_comment;
    public LinearLayout ll_home_album_attribute;
    public TextView tv_home_album_collection;
    public TextView tv_home_album_love;
    private BGButton btn_newest_album_collect;
    private SearchAlbumAdapter.SearchAlbumListener searchAlbumListener;

    public SearchAlbumViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.iv_album_pic);
        tv_home_album_comment = itemView.findViewById(R.id.tv_home_album_comment);
        ll_home_album_attribute = itemView.findViewById(R.id.ll_home_album_attribute);
        tv_home_album_collection = itemView.findViewById(R.id.tv_home_album_collection);
        tv_home_album_love = itemView.findViewById(R.id.tv_home_album_love);
        btn_newest_album_collect = itemView.findViewById(R.id.btn_newest_album_collect);
    }

    public void setSearchAlbumListener(SearchAlbumAdapter.SearchAlbumListener searchAlbumListener) {
        this.searchAlbumListener = searchAlbumListener;
    }

    @Override
    public void bindData(Context mContext, List list, final int position) {
        super.bindData(mContext, list, position);
        final Album album = (Album) list.get(position);
        String url = album.getImageUrl();
        if (url != null) {
            url = url.replace("_fw658", "_fw236");
            imageView.setImageWidth(album.getResWidth());
            imageView.setImageHeight(album.getResHight());
//            LogUtil.e("url = " + url);
            ImageLoadUtils.glideLoading(mContext, url, imageView);
        }

        // 内容
        String content = album.getContent();
        if (content != null && !content.isEmpty()) {
            ViewUtil.setText(tv_home_album_comment, content);
            tv_home_album_comment.setVisibility(View.VISIBLE);
        } else {
            tv_home_album_comment.setVisibility(View.GONE);
        }

        // 其他信息
        int collection = album.getFavorites();
        int love = album.getLove();
        if (collection == 0 && love == 0) {
            ll_home_album_attribute.setVisibility(View.GONE);
        } else {
            ll_home_album_attribute.setVisibility(View.VISIBLE);
            tv_home_album_collection.setText(collection + "");
            tv_home_album_love.setText(love + "");
        }
        if(album.getIsCollect() == 1){
            btn_newest_album_collect.setText("已收藏");
        } else {
            btn_newest_album_collect.setText("收藏");
        }

        btn_newest_album_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchAlbumListener != null) {
                    searchAlbumListener.onClickCollect(position);
                }
            }
        });
    }
}
