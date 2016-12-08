// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class VideoPlayActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.VideoPlayActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296533, "field 'startVideo'");
    target.startVideo = view;
    view = finder.findRequiredView(source, 2131296530, "field 'mVideoLoadingLayout'");
    target.mVideoLoadingLayout = view;
    view = finder.findRequiredView(source, 2131296534, "field 'biliAnim'");
    target.biliAnim = finder.castView(view, 2131296534, "field 'biliAnim'");
    view = finder.findRequiredView(source, 2131296527, "field 'mViewRoot'");
    target.mViewRoot = finder.castView(view, 2131296527, "field 'mViewRoot'");
    view = finder.findRequiredView(source, 2131296726, "field 'video_load_progress'");
    target.video_load_progress = finder.castView(view, 2131296726, "field 'video_load_progress'");
    view = finder.findRequiredView(source, 2131296537, "field 'mSubtitleText'");
    target.mSubtitleText = finder.castView(view, 2131296537, "field 'mSubtitleText'");
    view = finder.findRequiredView(source, 2131296535, "field 'startVideoInfo'");
    target.startVideoInfo = finder.castView(view, 2131296535, "field 'startVideoInfo'");
    view = finder.findRequiredView(source, 2131296532, "field 'mVideoLoadingText'");
    target.mVideoLoadingText = finder.castView(view, 2131296532, "field 'mVideoLoadingText'");
    view = finder.findRequiredView(source, 2131296528, "field 'mVideoView'");
    target.mVideoView = finder.castView(view, 2131296528, "field 'mVideoView'");
    view = finder.findRequiredView(source, 2131296529, "field 'mDanmakuView'");
    target.mDanmakuView = finder.castView(view, 2131296529, "field 'mDanmakuView'");
    view = finder.findRequiredView(source, 2131296524, "field 'rl_video_ctl'");
    target.rl_video_ctl = finder.castView(view, 2131296524, "field 'rl_video_ctl'");
    view = finder.findRequiredView(source, 2131296538, "field 'mSubtitleImage'");
    target.mSubtitleImage = finder.castView(view, 2131296538, "field 'mSubtitleImage'");
    view = finder.findRequiredView(source, 2131296536, "field 'mSubtitleContainer'");
    target.mSubtitleContainer = view;
  }

  @Override public void reset(T target) {
    target.startVideo = null;
    target.mVideoLoadingLayout = null;
    target.biliAnim = null;
    target.mViewRoot = null;
    target.video_load_progress = null;
    target.mSubtitleText = null;
    target.startVideoInfo = null;
    target.mVideoLoadingText = null;
    target.mVideoView = null;
    target.mDanmakuView = null;
    target.rl_video_ctl = null;
    target.mSubtitleImage = null;
    target.mSubtitleContainer = null;
  }
}
