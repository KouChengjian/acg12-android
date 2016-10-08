// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class VideoPlayActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.VideoPlayActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230994, "field 'mVideoLoadingLayout'");
    target.mVideoLoadingLayout = view;
    view = finder.findRequiredView(source, 2131230993, "field 'mDanmakuView'");
    target.mDanmakuView = finder.castView(view, 2131230993, "field 'mDanmakuView'");
    view = finder.findRequiredView(source, 2131231001, "field 'mSubtitleText'");
    target.mSubtitleText = finder.castView(view, 2131231001, "field 'mSubtitleText'");
    view = finder.findRequiredView(source, 2131230997, "field 'startVideo'");
    target.startVideo = view;
    view = finder.findRequiredView(source, 2131230998, "field 'biliAnim'");
    target.biliAnim = finder.castView(view, 2131230998, "field 'biliAnim'");
    view = finder.findRequiredView(source, 2131230999, "field 'startVideoInfo'");
    target.startVideoInfo = finder.castView(view, 2131230999, "field 'startVideoInfo'");
    view = finder.findRequiredView(source, 2131231002, "field 'mSubtitleImage'");
    target.mSubtitleImage = finder.castView(view, 2131231002, "field 'mSubtitleImage'");
    view = finder.findRequiredView(source, 2131231000, "field 'mSubtitleContainer'");
    target.mSubtitleContainer = view;
    view = finder.findRequiredView(source, 2131230988, "field 'rl_video_ctl'");
    target.rl_video_ctl = finder.castView(view, 2131230988, "field 'rl_video_ctl'");
    view = finder.findRequiredView(source, 2131231190, "field 'video_load_progress'");
    target.video_load_progress = finder.castView(view, 2131231190, "field 'video_load_progress'");
    view = finder.findRequiredView(source, 2131230996, "field 'mVideoLoadingText'");
    target.mVideoLoadingText = finder.castView(view, 2131230996, "field 'mVideoLoadingText'");
    view = finder.findRequiredView(source, 2131230992, "field 'mVideoView'");
    target.mVideoView = finder.castView(view, 2131230992, "field 'mVideoView'");
    view = finder.findRequiredView(source, 2131230991, "field 'mViewRoot'");
    target.mViewRoot = finder.castView(view, 2131230991, "field 'mViewRoot'");
  }

  @Override public void reset(T target) {
    target.mVideoLoadingLayout = null;
    target.mDanmakuView = null;
    target.mSubtitleText = null;
    target.startVideo = null;
    target.biliAnim = null;
    target.startVideoInfo = null;
    target.mSubtitleImage = null;
    target.mSubtitleContainer = null;
    target.rl_video_ctl = null;
    target.video_load_progress = null;
    target.mVideoLoadingText = null;
    target.mVideoView = null;
    target.mViewRoot = null;
  }
}
