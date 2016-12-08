// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class EditAlbumActivity$$ViewInjector<T extends com.kcj.animationfriend.ui.EditAlbumActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296405, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296405, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296443, "field 'takeLayout'");
    target.takeLayout = finder.castView(view, 2131296443, "field 'takeLayout'");
    view = finder.findRequiredView(source, 2131296441, "field 'albumPic'");
    target.albumPic = finder.castView(view, 2131296441, "field 'albumPic'");
    view = finder.findRequiredView(source, 2131296432, "field 'choosePalette'");
    target.choosePalette = finder.castView(view, 2131296432, "field 'choosePalette'");
    view = finder.findRequiredView(source, 2131296444, "field 'takePic'");
    target.takePic = finder.castView(view, 2131296444, "field 'takePic'");
    view = finder.findRequiredView(source, 2131296437, "field 'rlSelector'");
    target.rlSelector = finder.castView(view, 2131296437, "field 'rlSelector'");
    view = finder.findRequiredView(source, 2131296435, "field 'content'");
    target.content = finder.castView(view, 2131296435, "field 'content'");
    view = finder.findRequiredView(source, 2131296440, "field 'openLayout'");
    target.openLayout = finder.castView(view, 2131296440, "field 'openLayout'");
    view = finder.findRequiredView(source, 2131296438, "field 'pictureGridView'");
    target.pictureGridView = finder.castView(view, 2131296438, "field 'pictureGridView'");
    view = finder.findRequiredView(source, 2131296433, "field 'tvChoosePalette'");
    target.tvChoosePalette = finder.castView(view, 2131296433, "field 'tvChoosePalette'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.takeLayout = null;
    target.albumPic = null;
    target.choosePalette = null;
    target.takePic = null;
    target.rlSelector = null;
    target.content = null;
    target.openLayout = null;
    target.pictureGridView = null;
    target.tvChoosePalette = null;
  }
}
