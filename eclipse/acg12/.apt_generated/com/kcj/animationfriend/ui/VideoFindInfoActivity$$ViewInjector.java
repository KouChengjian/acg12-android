// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class VideoFindInfoActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.VideoFindInfoActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361988, "field 'tabLayout'");
    target.tabLayout = finder.castView(view, 2131361988, "field 'tabLayout'");
    view = finder.findRequiredView(source, 2131362058, "field 'll_info_header'");
    target.ll_info_header = finder.castView(view, 2131362058, "field 'll_info_header'");
    view = finder.findRequiredView(source, 2131361941, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131361941, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131361989, "field 'pager'");
    target.pager = finder.castView(view, 2131361989, "field 'pager'");
    view = finder.findRequiredView(source, 2131362057, "field 'top_view'");
    target.top_view = finder.castView(view, 2131362057, "field 'top_view'");
  }

  @Override public void reset(T target) {
    target.tabLayout = null;
    target.ll_info_header = null;
    target.toolbar = null;
    target.pager = null;
    target.top_view = null;
  }
}
