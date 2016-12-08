// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class FindFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.FindFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296408, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131296408, "field 'myRefreshListView'");
    view = finder.findRequiredView(source, 2131296578, "field 'recyclerView'");
    target.recyclerView = finder.castView(view, 2131296578, "field 'recyclerView'");
    view = finder.findRequiredView(source, 2131296579, "field 'headerView'");
    target.headerView = view;
    view = finder.findRequiredView(source, 2131296462, "field 'footView'");
    target.footView = view;
  }

  @Override public void reset(T target) {
    target.myRefreshListView = null;
    target.recyclerView = null;
    target.headerView = null;
    target.footView = null;
  }
}
