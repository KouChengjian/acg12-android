package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;

import org.acg12.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/29.
 */

public class WebviewView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview_progress)
    ProgressBar webview_progress;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar);
    }

    public void paddingData(String url){
        webview.loadUrl(url);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebChromeClient(new WebChromeClient() {//监听网页加载
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    webview_progress.setVisibility(View.GONE);
                } else {
                    // 加载中
                    webview_progress.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbar.setTitle(title);
            }
        });

//        webview.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                LogUtil.e(url);
//                return true;
//            }
//        });
    }
}
