// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RecentFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.RecentFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131231048, "field 'listview'");
    target.listview = finder.castView(view, 2131231048, "field 'listview'");
    view = finder.findRequiredView(source, 2131231036, "field 'mClearEditText'");
    target.mClearEditText = finder.castView(view, 2131231036, "field 'mClearEditText'");
  }

  @Override public void reset(T target) {
    target.listview = null;
    target.mClearEditText = null;
  }
}
