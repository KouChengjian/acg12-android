// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserInfoActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.UserInfoActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361941, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131361941, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131362047, "field 'nickName'");
    target.nickName = finder.castView(view, 2131362047, "field 'nickName'");
    view = finder.findRequiredView(source, 2131362045, "field 'nickLayout'");
    target.nickLayout = finder.castView(view, 2131362045, "field 'nickLayout'");
    view = finder.findRequiredView(source, 2131362042, "field 'iconLayout'");
    target.iconLayout = finder.castView(view, 2131362042, "field 'iconLayout'");
    view = finder.findRequiredView(source, 2131362053, "field 'sexSwitch'");
    target.sexSwitch = finder.castView(view, 2131362053, "field 'sexSwitch'");
    view = finder.findRequiredView(source, 2131362056, "field 'signature'");
    target.signature = finder.castView(view, 2131362056, "field 'signature'");
    view = finder.findRequiredView(source, 2131362050, "field 'accountUser'");
    target.accountUser = finder.castView(view, 2131362050, "field 'accountUser'");
    view = finder.findRequiredView(source, 2131362044, "field 'userIcon'");
    target.userIcon = finder.castView(view, 2131362044, "field 'userIcon'");
    view = finder.findRequiredView(source, 2131362048, "field 'accountLayout'");
    target.accountLayout = finder.castView(view, 2131362048, "field 'accountLayout'");
    view = finder.findRequiredView(source, 2131362054, "field 'signLayout'");
    target.signLayout = finder.castView(view, 2131362054, "field 'signLayout'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.nickName = null;
    target.nickLayout = null;
    target.iconLayout = null;
    target.sexSwitch = null;
    target.signature = null;
    target.accountUser = null;
    target.userIcon = null;
    target.accountLayout = null;
    target.signLayout = null;
  }
}
