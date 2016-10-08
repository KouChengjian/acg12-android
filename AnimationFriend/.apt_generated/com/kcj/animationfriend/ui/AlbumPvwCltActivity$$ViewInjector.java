// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AlbumPvwCltActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.AlbumPvwCltActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230881, "field 'albumPic'");
    target.albumPic = finder.castView(view, 2131230881, "field 'albumPic'");
    view = finder.findRequiredView(source, 2131230886, "field 'tvCollectSave'");
    target.tvCollectSave = finder.castView(view, 2131230886, "field 'tvCollectSave'");
    view = finder.findRequiredView(source, 2131230882, "field 'content'");
    target.content = finder.castView(view, 2131230882, "field 'content'");
    view = finder.findRequiredView(source, 2131230883, "field 'choosePalette'");
    target.choosePalette = finder.castView(view, 2131230883, "field 'choosePalette'");
    view = finder.findRequiredView(source, 2131230869, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230869, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131230885, "field 'tvChoosePalette'");
    target.tvChoosePalette = finder.castView(view, 2131230885, "field 'tvChoosePalette'");
  }

  @Override public void reset(T target) {
    target.albumPic = null;
    target.tvCollectSave = null;
    target.content = null;
    target.choosePalette = null;
    target.toolbar = null;
    target.tvChoosePalette = null;
  }
}
