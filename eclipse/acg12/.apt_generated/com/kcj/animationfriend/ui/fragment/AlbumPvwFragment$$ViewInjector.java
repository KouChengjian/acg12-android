// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AlbumPvwFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.AlbumPvwFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362309, "field 'contentImage'");
    target.contentImage = finder.castView(view, 2131362309, "field 'contentImage'");
    view = finder.findRequiredView(source, 2131362310, "field 'contentText'");
    target.contentText = finder.castView(view, 2131362310, "field 'contentText'");
    view = finder.findRequiredView(source, 2131362106, "field 'commentList'");
    target.commentList = finder.castView(view, 2131362106, "field 'commentList'");
    view = finder.findRequiredView(source, 2131362308, "field 'llPicContainer'");
    target.llPicContainer = finder.castView(view, 2131362308, "field 'llPicContainer'");
    view = finder.findRequiredView(source, 2131362103, "field 'scrollView'");
    target.scrollView = finder.castView(view, 2131362103, "field 'scrollView'");
  }

  @Override public void reset(T target) {
    target.contentImage = null;
    target.contentText = null;
    target.commentList = null;
    target.llPicContainer = null;
    target.scrollView = null;
  }
}
