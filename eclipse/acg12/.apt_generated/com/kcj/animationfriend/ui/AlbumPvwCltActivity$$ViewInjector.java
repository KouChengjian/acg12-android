// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AlbumPvwCltActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.AlbumPvwCltActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296421, "field 'tvChoosePalette'");
    target.tvChoosePalette = finder.castView(view, 2131296421, "field 'tvChoosePalette'");
    view = finder.findRequiredView(source, 2131296405, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296405, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296417, "field 'albumPic'");
    target.albumPic = finder.castView(view, 2131296417, "field 'albumPic'");
    view = finder.findRequiredView(source, 2131296418, "field 'content'");
    target.content = finder.castView(view, 2131296418, "field 'content'");
    view = finder.findRequiredView(source, 2131296422, "field 'tvCollectSave'");
    target.tvCollectSave = finder.castView(view, 2131296422, "field 'tvCollectSave'");
    view = finder.findRequiredView(source, 2131296419, "field 'choosePalette'");
    target.choosePalette = finder.castView(view, 2131296419, "field 'choosePalette'");
  }

  @Override public void reset(T target) {
    target.tvChoosePalette = null;
    target.toolbar = null;
    target.albumPic = null;
    target.content = null;
    target.tvCollectSave = null;
    target.choosePalette = null;
  }
}
