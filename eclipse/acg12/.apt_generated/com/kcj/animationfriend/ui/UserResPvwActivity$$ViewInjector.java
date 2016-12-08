// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserResPvwActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.UserResPvwActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296462, "field 'footView'");
    target.footView = view;
    view = finder.findRequiredView(source, 2131296405, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296405, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296408, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131296408, "field 'myRefreshListView'");
    view = finder.findRequiredView(source, 2131296461, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131296461, "field 'recyclerView'");
  }

  @Override public void reset(T target) {
    target.footView = null;
    target.toolbar = null;
    target.myRefreshListView = null;
    target.recyclerView = null;
  }
}
