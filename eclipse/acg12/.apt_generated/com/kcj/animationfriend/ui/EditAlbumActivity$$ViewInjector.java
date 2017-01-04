// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class EditAlbumActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.EditAlbumActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131361979, "field 'takeLayout'");
    target.takeLayout = finder.castView(view, 2131361979, "field 'takeLayout'");
    view = finder.findRequiredView(source, 2131361974, "field 'pictureGridView'");
    target.pictureGridView = finder.castView(view, 2131361974, "field 'pictureGridView'");
    view = finder.findRequiredView(source, 2131361977, "field 'albumPic'");
    target.albumPic = finder.castView(view, 2131361977, "field 'albumPic'");
    view = finder.findRequiredView(source, 2131361969, "field 'tvChoosePalette'");
    target.tvChoosePalette = finder.castView(view, 2131361969, "field 'tvChoosePalette'");
    view = finder.findRequiredView(source, 2131361976, "field 'openLayout'");
    target.openLayout = finder.castView(view, 2131361976, "field 'openLayout'");
    view = finder.findRequiredView(source, 2131361968, "field 'choosePalette'");
    target.choosePalette = finder.castView(view, 2131361968, "field 'choosePalette'");
    view = finder.findRequiredView(source, 2131361941, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131361941, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131361971, "field 'content'");
    target.content = finder.castView(view, 2131361971, "field 'content'");
    view = finder.findRequiredView(source, 2131361973, "field 'rlSelector'");
    target.rlSelector = finder.castView(view, 2131361973, "field 'rlSelector'");
    view = finder.findRequiredView(source, 2131361980, "field 'takePic'");
    target.takePic = finder.castView(view, 2131361980, "field 'takePic'");
  }

  @Override public void reset(T target) {
    target.takeLayout = null;
    target.pictureGridView = null;
    target.albumPic = null;
    target.tvChoosePalette = null;
    target.openLayout = null;
    target.choosePalette = null;
    target.toolbar = null;
    target.content = null;
    target.rlSelector = null;
    target.takePic = null;
  }
}
