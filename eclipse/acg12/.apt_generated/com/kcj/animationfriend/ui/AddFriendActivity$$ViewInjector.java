// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AddFriendActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.AddFriendActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361941, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131361941, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131361942, "field 'et_find_name'");
    target.et_find_name = finder.castView(view, 2131361942, "field 'et_find_name'");
    view = finder.findRequiredView(source, 2131361945, "field 'mListView'");
    target.mListView = finder.castView(view, 2131361945, "field 'mListView'");
    view = finder.findRequiredView(source, 2131361944, "field 'myRefreshListView'");
    target.myRefreshListView = finder.castView(view, 2131361944, "field 'myRefreshListView'");
    view = finder.findRequiredView(source, 2131361943, "field 'btn_search'");
    target.btn_search = finder.castView(view, 2131361943, "field 'btn_search'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.et_find_name = null;
    target.mListView = null;
    target.myRefreshListView = null;
    target.btn_search = null;
  }
}
