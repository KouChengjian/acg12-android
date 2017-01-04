// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class WebInfoActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.WebInfoActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362075, "field 'backBtn'");
    target.backBtn = finder.castView(view, 2131362075, "field 'backBtn'");
    view = finder.findRequiredView(source, 2131362080, "field 'reLoadImageView'");
    target.reLoadImageView = finder.castView(view, 2131362080, "field 'reLoadImageView'");
    view = finder.findRequiredView(source, 2131362079, "field 'progressBar'");
    target.progressBar = finder.castView(view, 2131362079, "field 'progressBar'");
    view = finder.findRequiredView(source, 2131362078, "field 'commentBtn'");
    target.commentBtn = finder.castView(view, 2131362078, "field 'commentBtn'");
    view = finder.findRequiredView(source, 2131362081, "field 'biliWebView'");
    target.biliWebView = finder.castView(view, 2131362081, "field 'biliWebView'");
  }

  @Override public void reset(T target) {
    target.backBtn = null;
    target.reLoadImageView = null;
    target.progressBar = null;
    target.commentBtn = null;
    target.biliWebView = null;
  }
}
