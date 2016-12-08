// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SettingActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.SettingActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296488, "field 'pushSwitch'");
    target.pushSwitch = finder.castView(view, 2131296488, "field 'pushSwitch'");
    view = finder.findRequiredView(source, 2131296489, "field 'settings_push_voice'");
    target.settings_push_voice = finder.castView(view, 2131296489, "field 'settings_push_voice'");
    view = finder.findRequiredView(source, 2131296501, "field 'settings_about'");
    target.settings_about = finder.castView(view, 2131296501, "field 'settings_about'");
    view = finder.findRequiredView(source, 2131296494, "field 'pushShakeSwitch'");
    target.pushShakeSwitch = finder.castView(view, 2131296494, "field 'pushShakeSwitch'");
    view = finder.findRequiredView(source, 2131296491, "field 'pushVoiceSwitch'");
    target.pushVoiceSwitch = finder.castView(view, 2131296491, "field 'pushVoiceSwitch'");
    view = finder.findRequiredView(source, 2131296492, "field 'settings_push_shake'");
    target.settings_push_shake = finder.castView(view, 2131296492, "field 'settings_push_shake'");
    view = finder.findRequiredView(source, 2131296499, "field 'feedbackLayout'");
    target.feedbackLayout = finder.castView(view, 2131296499, "field 'feedbackLayout'");
    view = finder.findRequiredView(source, 2131296495, "field 'cleanCache'");
    target.cleanCache = finder.castView(view, 2131296495, "field 'cleanCache'");
    view = finder.findRequiredView(source, 2131296503, "field 'logout'");
    target.logout = finder.castView(view, 2131296503, "field 'logout'");
    view = finder.findRequiredView(source, 2131296497, "field 'update'");
    target.update = finder.castView(view, 2131296497, "field 'update'");
    view = finder.findRequiredView(source, 2131296405, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296405, "field 'toolbar'");
  }

  @Override public void reset(T target) {
    target.pushSwitch = null;
    target.settings_push_voice = null;
    target.settings_about = null;
    target.pushShakeSwitch = null;
    target.pushVoiceSwitch = null;
    target.settings_push_shake = null;
    target.feedbackLayout = null;
    target.cleanCache = null;
    target.logout = null;
    target.update = null;
    target.toolbar = null;
  }
}
