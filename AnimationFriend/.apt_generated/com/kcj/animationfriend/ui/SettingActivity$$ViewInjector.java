// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SettingActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.SettingActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230958, "field 'pushShakeSwitch'");
    target.pushShakeSwitch = finder.castView(view, 2131230958, "field 'pushShakeSwitch'");
    view = finder.findRequiredView(source, 2131230965, "field 'settings_about'");
    target.settings_about = finder.castView(view, 2131230965, "field 'settings_about'");
    view = finder.findRequiredView(source, 2131230961, "field 'update'");
    target.update = finder.castView(view, 2131230961, "field 'update'");
    view = finder.findRequiredView(source, 2131230869, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230869, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131230959, "field 'cleanCache'");
    target.cleanCache = finder.castView(view, 2131230959, "field 'cleanCache'");
    view = finder.findRequiredView(source, 2131230952, "field 'pushSwitch'");
    target.pushSwitch = finder.castView(view, 2131230952, "field 'pushSwitch'");
    view = finder.findRequiredView(source, 2131230953, "field 'settings_push_voice'");
    target.settings_push_voice = finder.castView(view, 2131230953, "field 'settings_push_voice'");
    view = finder.findRequiredView(source, 2131230956, "field 'settings_push_shake'");
    target.settings_push_shake = finder.castView(view, 2131230956, "field 'settings_push_shake'");
    view = finder.findRequiredView(source, 2131230963, "field 'feedbackLayout'");
    target.feedbackLayout = finder.castView(view, 2131230963, "field 'feedbackLayout'");
    view = finder.findRequiredView(source, 2131230967, "field 'logout'");
    target.logout = finder.castView(view, 2131230967, "field 'logout'");
    view = finder.findRequiredView(source, 2131230955, "field 'pushVoiceSwitch'");
    target.pushVoiceSwitch = finder.castView(view, 2131230955, "field 'pushVoiceSwitch'");
  }

  @Override public void reset(T target) {
    target.pushShakeSwitch = null;
    target.settings_about = null;
    target.update = null;
    target.toolbar = null;
    target.cleanCache = null;
    target.pushSwitch = null;
    target.settings_push_voice = null;
    target.settings_push_shake = null;
    target.feedbackLayout = null;
    target.logout = null;
    target.pushVoiceSwitch = null;
  }
}
