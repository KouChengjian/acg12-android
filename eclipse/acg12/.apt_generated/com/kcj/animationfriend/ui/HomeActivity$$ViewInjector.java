// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HomeActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.HomeActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361989, "field 'pager'");
    target.pager = finder.castView(view, 2131361989, "field 'pager'");
    view = finder.findRequiredView(source, 2131361941, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131361941, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131361988, "field 'indicator'");
    target.indicator = finder.castView(view, 2131361988, "field 'indicator'");
  }

  @Override public void reset(T target) {
    target.pager = null;
    target.toolbar = null;
    target.indicator = null;
  }
}
