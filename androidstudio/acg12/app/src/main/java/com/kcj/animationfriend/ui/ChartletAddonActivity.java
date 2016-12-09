package com.kcj.animationfriend.ui;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.ui.base.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;


/**
 * @ClassName: ChartletAddonActivity
 * @Description: 贴图插件处理
 * @author: KCJ
 * @date:
 */
public class ChartletAddonActivity extends BaseActivity implements OnClickListener{

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_chartlet_addon);
		initViews();
		initEvent();
		initDatas();
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}

}
