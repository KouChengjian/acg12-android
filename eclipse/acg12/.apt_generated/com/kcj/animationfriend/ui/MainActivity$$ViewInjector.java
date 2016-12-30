// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MainActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.MainActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362141, "field 'iv_message_tips'");
    target.iv_message_tips = finder.castView(view, 2131362141, "field 'iv_message_tips'");
    view = finder.findRequiredView(source, 2131361941, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131361941, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131362202, "field 'iv_setting_tips'");
    target.iv_setting_tips = finder.castView(view, 2131362202, "field 'iv_setting_tips'");
  }

  @Override public void reset(T target) {
    target.iv_message_tips = null;
    target.toolbar = null;
    target.iv_setting_tips = null;
  }
}
