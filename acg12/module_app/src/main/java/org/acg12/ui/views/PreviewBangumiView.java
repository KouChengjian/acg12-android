package org.acg12.ui.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.lib.listener.ItemClickSupport;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.PixelUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import org.acg12.R;
import org.acg12.entity.Video;
import org.acg12.ui.adapter.BangumiEpisodeAdapter;
import org.acg12.ui.adapter.BangumiSeasonAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/28.
 */
public class PreviewBangumiView extends ViewImpl {

    @BindView(R.id.bangumi_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_video_icon)
    ImageView ivVideoIcon;
    @BindView(R.id.video_click_play)
    ImageView video_click_play;
    @BindView(R.id.tv_video_title)
    TextView tvVideoTitle;
    @BindView(R.id.iv_video_arrow)
    ImageView ivVideoArrow;
    @BindView(R.id.tv_video_label)
    TextView tvVideoLabel;
    @BindView(R.id.tv_video_duration)
    TextView tvVideoDuration;
    @BindView(R.id.episode_recyclerView)
    RecyclerView episodeRecyclerView;
    @BindView(R.id.season_recyclerView)
    RecyclerView seasonRecyclerView;
    @BindView(R.id.layout_load_null)
    ViewStub layoutLoadNull;
    ImageView loadNullImageview;
    TextView loadNullTextview;

    BangumiEpisodeAdapter bangumiEpisodeAdapter;
    BangumiSeasonAdapter bangumiSeasonAdapter;
    @BindView(R.id.layout_season)
    LinearLayout layoutSeason;
    @BindView(R.id.episode_null)
    TextView episodeNull;


    @Override
    public int getLayoutId() {
        return R.layout.activity_preview_bangumi;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back_press);

        episodeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        seasonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        bangumiEpisodeAdapter = new BangumiEpisodeAdapter(getContext());
        episodeRecyclerView.setAdapter(bangumiEpisodeAdapter);
        bangumiSeasonAdapter = new BangumiSeasonAdapter(getContext());
        seasonRecyclerView.setAdapter(bangumiSeasonAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(getContext() , 50), PixelUtil.dp2px(getContext() ,24));
        mSwipeRefreshLayout.setRefreshing(true);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, getStatusBarHeight(), 0, 0);
        toolbar.setLayoutParams(layoutParams);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar, ivVideoArrow);
        ItemClickSupport.addTo(episodeRecyclerView).setOnItemClickListener((ItemClickSupport.OnItemClickListener)mPresenter);
    }

    public String getAvId(int position){
        return bangumiEpisodeAdapter.getList().get(position).getAid();
    }

    public Video getVideo(int position){
        return bangumiEpisodeAdapter.getList().get(position);
    }

    public void setPlayer(boolean bool , Video video){
        if (bool) {
//            danmakuVideo.setVisibility(View.VISIBLE);
            ivVideoIcon.setVisibility(View.GONE);
            video_click_play.setVisibility(View.GONE);

//            danmakuVideo.setUp(video.getPlayUrl() , true , "");
        } else {
//            danmakuVideo.setVisibility(View.GONE);
            ivVideoIcon.setVisibility(View.VISIBLE);
            video_click_play.setVisibility(View.VISIBLE);
        }
    }

    public void bindData(Video video) {
        if (video == null) return;
        String url = video.getPic();
        if (url != null && !url.isEmpty()) {
            ImageLoadUtils.glideLoading(getContext(), url, ivVideoIcon);
        }
        ViewUtil.setText(tvVideoTitle, video.getTitle());
        if(TextUtils.isEmpty(video.getSbutitle())){
            tvVideoLabel.setVisibility(View.GONE);
        } else {
            tvVideoLabel.setVisibility(View.VISIBLE);
            ViewUtil.setText(tvVideoLabel, video.getSbutitle());
        }
        ViewUtil.setText(tvVideoDuration, video.getDescription());

        bangumiEpisodeAdapter.setList(video.getEpisodeList());
        bangumiEpisodeAdapter.notifyDataSetChanged();

        bangumiSeasonAdapter.setList(video.getSeasonList());
        bangumiSeasonAdapter.notifyDataSetChanged();
    }

    public void hasShowDuration(boolean bool) {
        if (bool) {
            tvVideoDuration.setMaxLines(tvVideoDuration.getLineCount());
            ivVideoArrow.setSelected(true);
        } else {
            tvVideoDuration.setMaxLines(2);
            ivVideoArrow.setSelected(false);
        }
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void stopRefreshLoadMore(boolean refresh) {
        mSwipeRefreshLayout.setEnabled(false);
        mSwipeRefreshLayout.setRefreshing(false);
        if (refresh) {
            layoutContent.setVisibility(View.VISIBLE);
            List<Video> episodeList = bangumiEpisodeAdapter.getList();
            if (episodeList == null || episodeList.isEmpty()) {
                episodeRecyclerView.setVisibility(View.GONE);
                episodeNull.setVisibility(View.VISIBLE);
            } else {
                episodeRecyclerView.setVisibility(View.VISIBLE);
                episodeNull.setVisibility(View.GONE);
            }

            List<Video> seasonList = bangumiSeasonAdapter.getList();
            if (seasonList == null || seasonList.isEmpty()) {
                layoutSeason.setVisibility(View.GONE);
            } else {
                if (seasonList.size() == 1) {
                    layoutSeason.setVisibility(View.GONE);
                } else {
                    layoutSeason.setVisibility(View.VISIBLE);
                }
            }
        } else {
            layoutContent.setVisibility(View.GONE);
            View view = layoutLoadNull.inflate();
            loadNullImageview = (ImageView) view.findViewById(R.id.iv_load_null);
            loadNullTextview = (TextView) view.findViewById(R.id.tv_load_null);
            //loadNullImageview.setImageResource(R.mipmap.ic_error);
            ViewUtil.setText(loadNullTextview, "暂时没有更多该视频信息");
            if (loadNullImageview.getVisibility() == View.GONE) {
                loadNullImageview.setVisibility(View.VISIBLE);
            }
        }

    }
}
