package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.InjectView;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.ui.base.BaseActivity;

public class WebInfoActivity extends BaseActivity implements OnClickListener{

	@InjectView(R.id.backBtn)
	protected ImageView backBtn; // 回退按钮
	@InjectView(R.id.comment)
	protected ImageView commentBtn; // 评论按钮
	@InjectView(R.id.blogContentPro)
	protected ProgressBar progressBar; // 进度条
	@InjectView(R.id.reLoadImage)
	protected ImageView reLoadImageView; // 重新加载的图片
	@InjectView(R.id.BiliWebView)
	protected WebView biliWebView;//网页控件
	
	public static String url;
	private String filename;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_web);
		initViews();
		initEvent();
		initDatas();
	}
	
	@Override
	public void initViews() {
		super.initViews();
		url = getIntent().getExtras().getString("bannerLink");
		filename = url.substring(url.lastIndexOf("/") + 1);
	}
	
	@Override
	public void initEvent() {
		super.initEvent();
		backBtn.setOnClickListener(this);
		commentBtn.setOnClickListener(this);
		reLoadImageView.setOnClickListener(this);
	}
	
	@Override
	public void initDatas() {
		super.initDatas();
		biliWebView.getSettings().setJavaScriptEnabled(true);
		biliWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		if(url != null && !url.isEmpty())
		    biliWebView.loadUrl(url);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backBtn:
			finish();
			break;
        case R.id.comment:
			
			break;
        case R.id.reLoadImage:
        	reLoadImageView.setVisibility(View.INVISIBLE);
			progressBar.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}
}
