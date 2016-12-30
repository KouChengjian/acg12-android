// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AlbumPvwCltActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.AlbumPvwCltActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361941, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131361941, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131361958, "field 'tvCollectSave'");
    target.tvCollectSave = finder.castView(view, 2131361958, "field 'tvCollectSave'");
    view = finder.findRequiredView(source, 2131361953, "field 'albumPic'");
    target.albumPic = finder.castView(view, 2131361953, "field 'albumPic'");
    view = finder.findRequiredView(source, 2131361957, "field 'tvChoosePalette'");
    target.tvChoosePalette = finder.castView(view, 2131361957, "field 'tvChoosePalette'");
    view = finder.findRequiredView(source, 2131361955, "field 'choosePalette'");
    target.choosePalette = finder.castView(view, 2131361955, "field 'choosePalette'");
    view = finder.findRequiredView(source, 2131361954, "field 'content'");
    target.content = finder.castView(view, 2131361954, "field 'content'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.tvCollectSave = null;
    target.albumPic = null;
    target.tvChoosePalette = null;
    target.choosePalette = null;
    target.content = null;
  }
}
