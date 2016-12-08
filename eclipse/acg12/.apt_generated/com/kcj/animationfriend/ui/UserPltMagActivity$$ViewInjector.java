// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserPltMagActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.UserPltMagActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296460, "field 'paletteListView'");
    target.paletteListView = finder.castView(view, 2131296460, "field 'paletteListView'");
    view = finder.findRequiredView(source, 2131296405, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296405, "field 'toolbar'");
  }

  @Override public void reset(T target) {
    target.paletteListView = null;
    target.toolbar = null;
  }
}
