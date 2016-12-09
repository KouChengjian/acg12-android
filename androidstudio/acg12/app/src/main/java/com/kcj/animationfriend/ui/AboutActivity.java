package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;


/**
 * @ClassName: AboutActivity
 * @Description: 关于
 * @author: KCJ
 * @date: 2014-12-25
 */
public class AboutActivity extends BaseSwipeBackActivity{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		//setSystemBarTintManager();
		initViews();
	}
	
	@Override
	public void initViews() {
		super.initViews();
		setTitle(R.string.about);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
}
