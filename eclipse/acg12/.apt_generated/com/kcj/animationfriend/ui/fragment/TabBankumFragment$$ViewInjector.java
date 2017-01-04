// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class TabBankumFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.TabBankumFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362147, "field 'rankListView'");
    target.rankListView = finder.castView(view, 2131362147, "field 'rankListView'");
    view = finder.findRequiredView(source, 2131362146, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131362146, "field 'myRefreshListView'");
  }

  @Override public void reset(T target) {
    target.rankListView = null;
    target.myRefreshListView = null;
  }
}
