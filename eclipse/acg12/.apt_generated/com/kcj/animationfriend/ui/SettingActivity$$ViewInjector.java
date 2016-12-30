// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SettingActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.SettingActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361941, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131361941, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131362025, "field 'settings_push_voice'");
    target.settings_push_voice = finder.castView(view, 2131362025, "field 'settings_push_voice'");
    view = finder.findRequiredView(source, 2131362024, "field 'pushSwitch'");
    target.pushSwitch = finder.castView(view, 2131362024, "field 'pushSwitch'");
    view = finder.findRequiredView(source, 2131362035, "field 'feedbackLayout'");
    target.feedbackLayout = finder.castView(view, 2131362035, "field 'feedbackLayout'");
    view = finder.findRequiredView(source, 2131362037, "field 'settings_about'");
    target.settings_about = finder.castView(view, 2131362037, "field 'settings_about'");
    view = finder.findRequiredView(source, 2131362028, "field 'settings_push_shake'");
    target.settings_push_shake = finder.castView(view, 2131362028, "field 'settings_push_shake'");
    view = finder.findRequiredView(source, 2131362030, "field 'pushShakeSwitch'");
    target.pushShakeSwitch = finder.castView(view, 2131362030, "field 'pushShakeSwitch'");
    view = finder.findRequiredView(source, 2131362027, "field 'pushVoiceSwitch'");
    target.pushVoiceSwitch = finder.castView(view, 2131362027, "field 'pushVoiceSwitch'");
    view = finder.findRequiredView(source, 2131362033, "field 'update'");
    target.update = finder.castView(view, 2131362033, "field 'update'");
    view = finder.findRequiredView(source, 2131362039, "field 'logout'");
    target.logout = finder.castView(view, 2131362039, "field 'logout'");
    view = finder.findRequiredView(source, 2131362031, "field 'cleanCache'");
    target.cleanCache = finder.castView(view, 2131362031, "field 'cleanCache'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.settings_push_voice = null;
    target.pushSwitch = null;
    target.feedbackLayout = null;
    target.settings_about = null;
    target.settings_push_shake = null;
    target.pushShakeSwitch = null;
    target.pushVoiceSwitch = null;
    target.update = null;
    target.logout = null;
    target.cleanCache = null;
  }
}
