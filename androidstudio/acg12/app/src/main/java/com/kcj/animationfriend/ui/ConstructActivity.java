package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.ui.base.BaseActivity;


/**
 * @ClassName: ConstructActivity
 * @Description: 施工中
 * @author: KCJ
 * @date: 
 */
public class ConstructActivity extends BaseActivity  {

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_construct);
		setTitle("正在施工中~");
		setSupportActionBar(toolbar);
	}
	
}
