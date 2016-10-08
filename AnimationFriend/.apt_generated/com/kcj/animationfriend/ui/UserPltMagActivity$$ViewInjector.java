// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserPltMagActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.UserPltMagActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230869, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230869, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131230924, "field 'paletteListView'");
    target.paletteListView = finder.castView(view, 2131230924, "field 'paletteListView'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.paletteListView = null;
  }
}
