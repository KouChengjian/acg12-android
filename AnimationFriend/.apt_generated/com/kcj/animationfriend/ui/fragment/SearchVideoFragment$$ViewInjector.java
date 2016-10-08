// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SearchVideoFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.SearchVideoFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131231075, "field 'rankListView'");
    target.rankListView = finder.castView(view, 2131231075, "field 'rankListView'");
    view = finder.findRequiredView(source, 2131231074, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131231074, "field 'myRefreshListView'");
  }

  @Override public void reset(T target) {
    target.rankListView = null;
    target.myRefreshListView = null;
  }
}
