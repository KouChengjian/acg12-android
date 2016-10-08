// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HomeFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.HomeFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230872, "field 'mRefreshLayout'");
    target.mRefreshLayout = finder.castView(view, 2131230872, "field 'mRefreshLayout'");
    view = finder.findRequiredView(source, 2131231046, "field 'listView'");
    target.listView = finder.castView(view, 2131231046, "field 'listView'");
  }

  @Override public void reset(T target) {
    target.mRefreshLayout = null;
    target.listView = null;
  }
}
