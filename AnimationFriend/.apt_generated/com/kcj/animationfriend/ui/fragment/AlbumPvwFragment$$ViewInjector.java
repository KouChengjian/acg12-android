// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AlbumPvwFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.AlbumPvwFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131231238, "field 'contentText'");
    target.contentText = finder.castView(view, 2131231238, "field 'contentText'");
    view = finder.findRequiredView(source, 2131231031, "field 'scrollView'");
    target.scrollView = finder.castView(view, 2131231031, "field 'scrollView'");
    view = finder.findRequiredView(source, 2131231237, "field 'contentImage'");
    target.contentImage = finder.castView(view, 2131231237, "field 'contentImage'");
    view = finder.findRequiredView(source, 2131231236, "field 'llPicContainer'");
    target.llPicContainer = finder.castView(view, 2131231236, "field 'llPicContainer'");
    view = finder.findRequiredView(source, 2131231034, "field 'commentList'");
    target.commentList = finder.castView(view, 2131231034, "field 'commentList'");
  }

  @Override public void reset(T target) {
    target.contentText = null;
    target.scrollView = null;
    target.contentImage = null;
    target.llPicContainer = null;
    target.commentList = null;
  }
}
