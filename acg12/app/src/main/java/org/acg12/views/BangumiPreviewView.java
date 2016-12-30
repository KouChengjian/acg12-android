package org.acg12.views;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.bean.Video;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.adapter.BangumiEpisodeAdapter;
import org.acg12.ui.adapter.BangumiSeasonAdapter;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.PixelUtil;
import org.acg12.utlis.ViewUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by DELL on 2016/12/28.
 */
public class BangumiPreviewView extends ViewImpl {

    @BindView(R.id.bangumi_swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.layout_content)
    LinearLayout layoutContent;
    @BindView(R.id.iv_video_icon)
    ImageView ivVideoIcon;
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
        return R.layout.activity_bangumi_preview;
    }

    @Override
    public void created() {
        super.created();

        episodeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        seasonRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        bangumiEpisodeAdapter = new BangumiEpisodeAdapter(getContext());
        episodeRecyclerView.setAdapter(bangumiEpisodeAdapter);

        bangumiSeasonAdapter = new BangumiSeasonAdapter(getContext());
        seasonRecyclerView.setAdapter(bangumiSeasonAdapter);

        mSwipeRefreshLayout.setColorSchemeResources(R.color.theme_primary);
        mSwipeRefreshLayout.setProgressViewOffset(false, -PixelUtil.dp2px(50), PixelUtil.dp2px(24));
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, ivVideoArrow);
    }

    public void bindData(Video video) {
        if (video == null) return;
        String url = video.getPic();
        if (url != null && !url.isEmpty()) {
            ImageLoadUtils.glideLoading(getContext(), url, ivVideoIcon);
        }
        ViewUtil.setText(tvVideoTitle, video.getTitle());
        ViewUtil.setText(tvVideoLabel, video.getSbutitle());
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
