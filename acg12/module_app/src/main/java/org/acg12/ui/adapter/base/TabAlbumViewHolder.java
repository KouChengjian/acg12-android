package org.acg12.ui.adapter.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.loadimage.ImageLoadUtils;
import com.acg12.lib.widget.ScaleImageView;

import org.acg12.R;
import org.acg12.entity.Album;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //存图片
    Map<String,Bitmap> bitmaps = new HashMap<String, Bitmap>();
    //存imageview
    Map<String,ScaleImageView> imageviews = new HashMap<String, ScaleImageView>();

    public TabAlbumViewHolder(View itemView) {
        super(itemView);
        imageView = (ScaleImageView) itemView.findViewById(R.id.iv_album_pic);
        tv_home_album_comment = (TextView) itemView.findViewById(R.id.tv_home_album_comment);
        ll_home_album_attribute = (LinearLayout) itemView.findViewById(R.id.ll_home_album_attribute);
        tv_home_album_collection = (TextView) itemView.findViewById(R.id.tv_home_album_collection);
        tv_home_album_love = (TextView) itemView.findViewById(R.id.tv_home_album_love);
    }

    public void bindData(final Context mContext , final List<Album> mList, final int position) {
        this.context = mContext;
        final Album album = mList.get(position);

        String url = album.getImageUrl();
        if (url != null) {
            url = url.replace("_fw658" , "_fw236");
            imageView.setImageWidth(album.getResWidth());
            imageView.setImageHeight(album.getResHight());
//            imageView.setImageResource(R.mipmap.kk_bg_loading_pic);
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

        bitmaps.put(album.getImageUrl(),ImageLoadUtils.ImageViewResouceBitmap(imageView));
        imageviews.put(album.getImageUrl(),imageView);

//        for (int i = 0, num = mList.size(); i < num; i++) {
//            //释放当前position 20条外的图片
//            int startPosition = position - 10;
//            int endPosition = position + 10;
//            if (i < startPosition || i > endPosition) {
//                String oldUrl = mList.get(i).getImageUrl();
//                Bitmap b = bitmaps.get(oldUrl);
//                ScaleImageView image = imageviews.get(oldUrl);
////                image.recycle();
//                if(b != null && !b.isRecycled()){
//                    image.setImageResource(R.mipmap.kk_bg_loading_pic);
//                    b.recycle();
//                    bitmaps.remove(oldUrl);
//                }
//            }
//        }
    }

}
