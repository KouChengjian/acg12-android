// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserInfoActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.UserInfoActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230972, "field 'userIcon'");
    target.userIcon = finder.castView(view, 2131230972, "field 'userIcon'");
    view = finder.findRequiredView(source, 2131230984, "field 'signature'");
    target.signature = finder.castView(view, 2131230984, "field 'signature'");
    view = finder.findRequiredView(source, 2131230976, "field 'accountLayout'");
    target.accountLayout = finder.castView(view, 2131230976, "field 'accountLayout'");
    view = finder.findRequiredView(source, 2131230970, "field 'iconLayout'");
    target.iconLayout = finder.castView(view, 2131230970, "field 'iconLayout'");
    view = finder.findRequiredView(source, 2131230981, "field 'sexSwitch'");
    target.sexSwitch = finder.castView(view, 2131230981, "field 'sexSwitch'");
    view = finder.findRequiredView(source, 2131230973, "field 'nickLayout'");
    target.nickLayout = finder.castView(view, 2131230973, "field 'nickLayout'");
    view = finder.findRequiredView(source, 2131230978, "field 'accountUser'");
    target.accountUser = finder.castView(view, 2131230978, "field 'accountUser'");
    view = finder.findRequiredView(source, 2131230869, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230869, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131230975, "field 'nickName'");
    target.nickName = finder.castView(view, 2131230975, "field 'nickName'");
    view = finder.findRequiredView(source, 2131230982, "field 'signLayout'");
    target.signLayout = finder.castView(view, 2131230982, "field 'signLayout'");
  }

  @Override public void reset(T target) {
    target.userIcon = null;
    target.signature = null;
    target.accountLayout = null;
    target.iconLayout = null;
    target.sexSwitch = null;
    target.nickLayout = null;
    target.accountUser = null;
    target.toolbar = null;
    target.nickName = null;
    target.signLayout = null;
  }
}
