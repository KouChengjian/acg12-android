// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class VideoPlayActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.VideoPlayActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362071, "field 'startVideoInfo'");
    target.startVideoInfo = finder.castView(view, 2131362071, "field 'startVideoInfo'");
    view = finder.findRequiredView(source, 2131362072, "field 'mSubtitleContainer'");
    target.mSubtitleContainer = view;
    view = finder.findRequiredView(source, 2131362262, "field 'video_load_progress'");
    target.video_load_progress = finder.castView(view, 2131362262, "field 'video_load_progress'");
    view = finder.findRequiredView(source, 2131362063, "field 'mViewRoot'");
    target.mViewRoot = finder.castView(view, 2131362063, "field 'mViewRoot'");
    view = finder.findRequiredView(source, 2131362066, "field 'mVideoLoadingLayout'");
    target.mVideoLoadingLayout = view;
    view = finder.findRequiredView(source, 2131362073, "field 'mSubtitleText'");
    target.mSubtitleText = finder.castView(view, 2131362073, "field 'mSubtitleText'");
    view = finder.findRequiredView(source, 2131362065, "field 'mDanmakuView'");
    target.mDanmakuView = finder.castView(view, 2131362065, "field 'mDanmakuView'");
    view = finder.findRequiredView(source, 2131362064, "field 'mVideoView'");
    target.mVideoView = finder.castView(view, 2131362064, "field 'mVideoView'");
    view = finder.findRequiredView(source, 2131362070, "field 'biliAnim'");
    target.biliAnim = finder.castView(view, 2131362070, "field 'biliAnim'");
    view = finder.findRequiredView(source, 2131362060, "field 'rl_video_ctl'");
    target.rl_video_ctl = finder.castView(view, 2131362060, "field 'rl_video_ctl'");
    view = finder.findRequiredView(source, 2131362068, "field 'mVideoLoadingText'");
    target.mVideoLoadingText = finder.castView(view, 2131362068, "field 'mVideoLoadingText'");
    view = finder.findRequiredView(source, 2131362069, "field 'startVideo'");
    target.startVideo = view;
    view = finder.findRequiredView(source, 2131362074, "field 'mSubtitleImage'");
    target.mSubtitleImage = finder.castView(view, 2131362074, "field 'mSubtitleImage'");
  }

  @Override public void reset(T target) {
    target.startVideoInfo = null;
    target.mSubtitleContainer = null;
    target.video_load_progress = null;
    target.mViewRoot = null;
    target.mVideoLoadingLayout = null;
    target.mSubtitleText = null;
    target.mDanmakuView = null;
    target.mVideoView = null;
    target.biliAnim = null;
    target.rl_video_ctl = null;
    target.mVideoLoadingText = null;
    target.startVideo = null;
    target.mSubtitleImage = null;
  }
}
