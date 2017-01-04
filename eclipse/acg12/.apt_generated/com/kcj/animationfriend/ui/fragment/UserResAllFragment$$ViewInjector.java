// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserResAllFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.UserResAllFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361988, "field 'tabs'");
    target.tabs = finder.castView(view, 2131361988, "field 'tabs'");
    view = finder.findRequiredView(source, 2131362121, "field 'rl_personal_tab'");
    target.rl_personal_tab = finder.castView(view, 2131362121, "field 'rl_personal_tab'");
    view = finder.findRequiredView(source, 2131362122, "field 'pager'");
    target.pager = finder.castView(view, 2131362122, "field 'pager'");
  }

  @Override public void reset(T target) {
    target.tabs = null;
    target.rl_personal_tab = null;
    target.pager = null;
  }
}
