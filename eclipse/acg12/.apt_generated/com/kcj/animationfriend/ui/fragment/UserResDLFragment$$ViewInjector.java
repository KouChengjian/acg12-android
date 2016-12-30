// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserResDLFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.UserResDLFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362110, "field 'dialog'");
    target.dialog = finder.castView(view, 2131362110, "field 'dialog'");
    view = finder.findRequiredView(source, 2131362109, "field 'downloadListView'");
    target.downloadListView = finder.castView(view, 2131362109, "field 'downloadListView'");
  }

  @Override public void reset(T target) {
    target.dialog = null;
    target.downloadListView = null;
  }
}
