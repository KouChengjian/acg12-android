package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.listener.ParameCallBack;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.ui.fragment.UserResAllFragment;

/**
 * @ClassName: UserResActivity
 * @Description: 用户资源
 * @author: KCJ
 * @date: 2015-9-9
 */
public class UserResActivity extends BaseSwipeBackActivity implements
		ParameCallBack, Toolbar.OnMenuItemClickListener {

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	protected User user;
	protected UserResAllFragment userResAllFragment;
	protected MenuItem edit, add;

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_resource);
		setTitle(R.string.user_res);
		setSupportActionBar(toolbar);
		user = (User) getIntent().getSerializableExtra("data");
		initViews();
		initEvent();
	}

	public void initViews() {
		userResAllFragment = (UserResAllFragment) UserResAllFragment
				.newInstance(user, this);
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, userResAllFragment)
				.commit();
	}

	@Override
	public void initEvent() {
		toolbar.setOnMenuItemClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_user_res, menu);
		edit = menu.findItem(R.id.menu_user_res_edit);
		add = menu.findItem(R.id.menu_user_res_add);
		edit.setVisible(false);
		add.setVisible(false);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onMenuItemClick(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.menu_user_res_edit:
			startAnimActivity(UserPltMagActivity.class);
			break;
		case R.id.menu_user_res_add:
			startAnimActivity(EditAlbumActivity.class);
			break;
		}
		return true;
	}

	@Override
	public void onCall(Object object) {
		if (object instanceof Integer) {
			int position = (Integer) object;
			if (position == 1) {
				// edit.setVisible(true);
				add.setVisible(true);
			} else {
				edit.setVisible(false);
				add.setVisible(false);
			}
		}
	}
}
