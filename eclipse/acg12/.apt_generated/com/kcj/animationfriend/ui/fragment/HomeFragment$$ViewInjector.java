// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class HomeFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.HomeFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296582, "field 'listView'");
    target.listView = finder.castView(view, 2131296582, "field 'listView'");
    view = finder.findRequiredView(source, 2131296408, "field 'mRefreshLayout'");
    target.mRefreshLayout = finder.castView(view, 2131296408, "field 'mRefreshLayout'");
  }

  @Override public void reset(T target) {
    target.listView = null;
    target.mRefreshLayout = null;
  }
}
