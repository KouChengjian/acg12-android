// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserResCltFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.UserResCltFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361998, "field 'footView'");
    target.footView = view;
    view = finder.findRequiredView(source, 2131361997, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131361997, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131361944, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131361944, "field 'myRefreshListView'");
  }

  @Override public void reset(T target) {
    target.footView = null;
    target.recyclerView = null;
    target.myRefreshListView = null;
  }
}
