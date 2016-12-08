// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class WebInfoActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.WebInfoActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296545, "field 'biliWebView'");
    target.biliWebView = finder.castView(view, 2131296545, "field 'biliWebView'");
    view = finder.findRequiredView(source, 2131296542, "field 'commentBtn'");
    target.commentBtn = finder.castView(view, 2131296542, "field 'commentBtn'");
    view = finder.findRequiredView(source, 2131296544, "field 'reLoadImageView'");
    target.reLoadImageView = finder.castView(view, 2131296544, "field 'reLoadImageView'");
    view = finder.findRequiredView(source, 2131296539, "field 'backBtn'");
    target.backBtn = finder.castView(view, 2131296539, "field 'backBtn'");
    view = finder.findRequiredView(source, 2131296543, "field 'progressBar'");
    target.progressBar = finder.castView(view, 2131296543, "field 'progressBar'");
  }

  @Override public void reset(T target) {
    target.biliWebView = null;
    target.commentBtn = null;
    target.reLoadImageView = null;
    target.backBtn = null;
    target.progressBar = null;
  }
}
