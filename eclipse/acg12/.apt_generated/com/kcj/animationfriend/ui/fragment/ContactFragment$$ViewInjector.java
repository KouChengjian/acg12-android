// Generated code from Butter Knife. Do not modify!
package com.kcj.animationfriend.ui.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ContactFragment$$ViewInjector<T extends com.kcj.animationfriend.ui.fragment.ContactFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296574, "field 'dialog'");
    target.dialog = finder.castView(view, 2131296574, "field 'dialog'");
    view = finder.findRequiredView(source, 2131296573, "field 'list_friends'");
    target.list_friends = finder.castView(view, 2131296573, "field 'list_friends'");
    view = finder.findRequiredView(source, 2131296571, "field 'layout_list'");
    target.layout_list = finder.castView(view, 2131296571, "field 'layout_list'");
    view = finder.findRequiredView(source, 2131296576, "field 'rl_contact_empty'");
    target.rl_contact_empty = finder.castView(view, 2131296576, "field 'rl_contact_empty'");
    view = finder.findRequiredView(source, 2131296575, "field 'right_letter'");
    target.right_letter = finder.castView(view, 2131296575, "field 'right_letter'");
    view = finder.findRequiredView(source, 2131296572, "field 'mClearEditText'");
    target.mClearEditText = finder.castView(view, 2131296572, "field 'mClearEditText'");
  }

  @Override public void reset(T target) {
    target.dialog = null;
    target.list_friends = null;
    target.layout_list = null;
    target.rl_contact_empty = null;
    target.right_letter = null;
    target.mClearEditText = null;
  }
}
