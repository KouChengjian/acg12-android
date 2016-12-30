// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class FindFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.FindFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361944, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131361944, "field 'myRefreshListView'");
    view = finder.findRequiredView(source, 2131362114, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131362114, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131361998, "field 'footView'");
    target.footView = view;
    view = finder.findRequiredView(source, 2131362115, "field 'headerView'");
    target.headerView = view;
  }

  @Override public void reset(T target) {
    target.myRefreshListView = null;
    target.recyclerView = null;
    target.footView = null;
    target.headerView = null;
  }
}
