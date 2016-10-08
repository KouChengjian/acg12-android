// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class WebInfoActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.WebInfoActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131231006, "field 'commentBtn'");
    target.commentBtn = finder.castView(view, 2131231006, "field 'commentBtn'");
    view = finder.findRequiredView(source, 2131231009, "field 'biliWebView'");
    target.biliWebView = finder.castView(view, 2131231009, "field 'biliWebView'");
    view = finder.findRequiredView(source, 2131231008, "field 'reLoadImageView'");
    target.reLoadImageView = finder.castView(view, 2131231008, "field 'reLoadImageView'");
    view = finder.findRequiredView(source, 2131231003, "field 'backBtn'");
    target.backBtn = finder.castView(view, 2131231003, "field 'backBtn'");
    view = finder.findRequiredView(source, 2131231007, "field 'progressBar'");
    target.progressBar = finder.castView(view, 2131231007, "field 'progressBar'");
  }

  @Override public void reset(T target) {
    target.commentBtn = null;
    target.biliWebView = null;
    target.reLoadImageView = null;
    target.backBtn = null;
    target.progressBar = null;
  }
}
