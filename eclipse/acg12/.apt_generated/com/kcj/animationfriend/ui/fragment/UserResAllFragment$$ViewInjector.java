// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class UserResAllFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.UserResAllFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296452, "field 'tabs'");
    target.tabs = finder.castView(view, 2131296452, "field 'tabs'");
    view = finder.findRequiredView(source, 2131296586, "field 'pager'");
    target.pager = finder.castView(view, 2131296586, "field 'pager'");
    view = finder.findRequiredView(source, 2131296585, "field 'rl_personal_tab'");
    target.rl_personal_tab = finder.castView(view, 2131296585, "field 'rl_personal_tab'");
  }

  @Override public void reset(T target) {
    target.tabs = null;
    target.pager = null;
    target.rl_personal_tab = null;
  }
}
