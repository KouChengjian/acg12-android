// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class BlackListActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.BlackListActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361946, "field 'listview'");
    target.listview = finder.castView(view, 2131361946, "field 'listview'");
    view = finder.findRequiredView(source, 2131361941, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131361941, "field 'toolbar'");
  }

  @Override public void reset(T target) {
    target.listview = null;
    target.toolbar = null;
  }
}
