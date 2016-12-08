// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class VideoHomeInfoIntroduceFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.VideoHomeInfoIntroduceFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296580, "field 'lv_video_info'");
    target.lv_video_info = finder.castView(view, 2131296580, "field 'lv_video_info'");
    view = finder.findRequiredView(source, 2131296581, "field 'pv_circular_inout'");
    target.pv_circular_inout = finder.castView(view, 2131296581, "field 'pv_circular_inout'");
  }

  @Override public void reset(T target) {
    target.lv_video_info = null;
    target.pv_circular_inout = null;
  }
}
