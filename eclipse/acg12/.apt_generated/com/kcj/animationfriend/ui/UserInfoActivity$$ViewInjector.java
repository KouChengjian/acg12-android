// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserInfoActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.UserInfoActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296514, "field 'accountUser'");
    target.accountUser = finder.castView(view, 2131296514, "field 'accountUser'");
    view = finder.findRequiredView(source, 2131296518, "field 'signLayout'");
    target.signLayout = finder.castView(view, 2131296518, "field 'signLayout'");
    view = finder.findRequiredView(source, 2131296509, "field 'nickLayout'");
    target.nickLayout = finder.castView(view, 2131296509, "field 'nickLayout'");
    view = finder.findRequiredView(source, 2131296405, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296405, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296517, "field 'sexSwitch'");
    target.sexSwitch = finder.castView(view, 2131296517, "field 'sexSwitch'");
    view = finder.findRequiredView(source, 2131296512, "field 'accountLayout'");
    target.accountLayout = finder.castView(view, 2131296512, "field 'accountLayout'");
    view = finder.findRequiredView(source, 2131296506, "field 'iconLayout'");
    target.iconLayout = finder.castView(view, 2131296506, "field 'iconLayout'");
    view = finder.findRequiredView(source, 2131296511, "field 'nickName'");
    target.nickName = finder.castView(view, 2131296511, "field 'nickName'");
    view = finder.findRequiredView(source, 2131296520, "field 'signature'");
    target.signature = finder.castView(view, 2131296520, "field 'signature'");
    view = finder.findRequiredView(source, 2131296508, "field 'userIcon'");
    target.userIcon = finder.castView(view, 2131296508, "field 'userIcon'");
  }

  @Override public void reset(T target) {
    target.accountUser = null;
    target.signLayout = null;
    target.nickLayout = null;
    target.toolbar = null;
    target.sexSwitch = null;
    target.accountLayout = null;
    target.iconLayout = null;
    target.nickName = null;
    target.signature = null;
    target.userIcon = null;
  }
}
