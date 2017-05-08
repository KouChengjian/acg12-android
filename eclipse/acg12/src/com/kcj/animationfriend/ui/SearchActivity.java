package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;
import com.kcj.animationfriend.ui.fragment.SearchFragment;

/**
 * @ClassName: SearchActivity
 * @Description: 搜索
 * @author: KCJ
 * @date: 2015-11-26  13：56 
 */
public class SearchActivity extends BaseSwipeBackActivity{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	protected SearchFragment searchFragment;
	protected String title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resource);
		setTitle(R.string.home_search);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		initViews();
	}
	
	@Override
	public void initViews() {
		title = getIntent().getExtras().getString("title");
		searchFragment = (SearchFragment) SearchFragment.newInstance(title);
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(R.id.fragment_container, searchFragment).commit();
	}
}
