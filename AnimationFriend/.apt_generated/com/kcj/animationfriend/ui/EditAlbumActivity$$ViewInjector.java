// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class EditAlbumActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.EditAlbumActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131230869, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131230869, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131230897, "field 'tvChoosePalette'");
    target.tvChoosePalette = finder.castView(view, 2131230897, "field 'tvChoosePalette'");
    view = finder.findRequiredView(source, 2131230908, "field 'takePic'");
    target.takePic = finder.castView(view, 2131230908, "field 'takePic'");
    view = finder.findRequiredView(source, 2131230899, "field 'content'");
    target.content = finder.castView(view, 2131230899, "field 'content'");
    view = finder.findRequiredView(source, 2131230901, "field 'rlSelector'");
    target.rlSelector = finder.castView(view, 2131230901, "field 'rlSelector'");
    view = finder.findRequiredView(source, 2131230896, "field 'choosePalette'");
    target.choosePalette = finder.castView(view, 2131230896, "field 'choosePalette'");
    view = finder.findRequiredView(source, 2131230907, "field 'takeLayout'");
    target.takeLayout = finder.castView(view, 2131230907, "field 'takeLayout'");
    view = finder.findRequiredView(source, 2131230904, "field 'openLayout'");
    target.openLayout = finder.castView(view, 2131230904, "field 'openLayout'");
    view = finder.findRequiredView(source, 2131230905, "field 'albumPic'");
    target.albumPic = finder.castView(view, 2131230905, "field 'albumPic'");
    view = finder.findRequiredView(source, 2131230902, "field 'pictureGridView'");
    target.pictureGridView = finder.castView(view, 2131230902, "field 'pictureGridView'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.tvChoosePalette = null;
    target.takePic = null;
    target.content = null;
    target.rlSelector = null;
    target.choosePalette = null;
    target.takeLayout = null;
    target.openLayout = null;
    target.albumPic = null;
    target.pictureGridView = null;
  }
}
