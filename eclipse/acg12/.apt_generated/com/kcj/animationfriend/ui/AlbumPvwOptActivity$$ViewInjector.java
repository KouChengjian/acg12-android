// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AlbumPvwOptActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.AlbumPvwOptActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296423, "field 'pager'");
    target.pager = finder.castView(view, 2131296423, "field 'pager'");
    view = finder.findRequiredView(source, 2131296405, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296405, "field 'toolbar'");
  }

  @Override public void reset(T target) {
    target.pager = null;
    target.toolbar = null;
  }
}
