// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MainActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.MainActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230869, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230869, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131231130, "field 'iv_setting_tips'");
    target.iv_setting_tips = finder.castView(view, 2131231130, "field 'iv_setting_tips'");
    view = finder.findRequiredView(source, 2131231069, "field 'iv_message_tips'");
    target.iv_message_tips = finder.castView(view, 2131231069, "field 'iv_message_tips'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.iv_setting_tips = null;
    target.iv_message_tips = null;
  }
}
