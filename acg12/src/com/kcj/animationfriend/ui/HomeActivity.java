package com.kcj.animationfriend.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.TabAlbumAdapter;
import com.kcj.animationfriend.adapter.TabBankumAdapter;
import com.kcj.animationfriend.adapter.TabDonghuaAdapter;
import com.kcj.animationfriend.adapter.TabPaletteAdapter;
import com.kcj.animationfriend.adapter.TabRankAdapter;
import com.kcj.animationfriend.bean.Palette;
import com.kcj.animationfriend.ui.base.BaseActivity;

/**
 * @ClassName: AreaActivity
 * @Description: 内容显示主界面
 * @author: KCJ
 * @date: 2015-10-15 
 */
public class HomeActivity extends BaseActivity{

	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	@InjectView(R.id.pager)
	protected ViewPager pager;
	@InjectView(R.id.indicator)
	protected TabLayout indicator;
	protected FragmentPagerAdapter adapter = null;
	
	public int mAreaType;
	public Palette palette;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_home);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		setSystemBarTintManager();
		setTitle(R.string.home);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mAreaType = getIntent().getIntExtra("AreaType", 1);
		palette = (Palette)getIntent().getSerializableExtra("data");
		initDatas();
		initViews();
		initEvent();
	}
	
	@Override
	public void initDatas() {
		switch (mAreaType){
		case 1:
			setTitle(R.string.home_album);
			indicator.setVisibility(View.GONE);
			adapter = new TabAlbumAdapter(getSupportFragmentManager(),palette);
			break;
		case 2:
			setTitle(R.string.home_palette);
			indicator.setVisibility(View.GONE);
			adapter = new TabPaletteAdapter(getSupportFragmentManager());
			break;
		case 3:
			setTitle(R.string.home_rank);
			adapter = new TabRankAdapter(getSupportFragmentManager());
			break;
		case 4:
			setTitle(R.string.home_bankun);
			adapter = new TabBankumAdapter(getSupportFragmentManager());
			break;
		case 5:
			setTitle(R.string.home_dongman);
			adapter = new TabDonghuaAdapter(getSupportFragmentManager());
			break;
		}
	}
	
	@Override
	public void initViews() {
		super.initViews();
		pager.setOffscreenPageLimit(adapter.getCount());
		pager.setAdapter(adapter);
		indicator.setupWithViewPager(pager);
		indicator.setTabMode(TabLayout.MODE_SCROLLABLE);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
