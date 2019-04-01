package com.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.entity.User;

import com.acg12.cache.DaoBaseImpl;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;
import com.acg12.lib.utils.ViewUtil;

import com.acg12.R;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.FeedbackView;
import com.acg12.utlis.Network;

public class FeedbackActivity extends SkinBaseActivity<FeedbackView> implements View.OnClickListener {

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if (view.getId() == R.id.title_right) {
            submit();
        }
    }

    private void submit() {
        boolean isNetConnected = Network.isConnected(mContext);
        if (!isNetConnected) {
            ShowToastView(R.string.network_tips);
            return;
        }

        String feedback = mView.getFeedback();
        if (TextUtils.isEmpty(feedback)) {
            ShowToastView("反馈意见不能为空");
            return;
        }

        if (feedback.length() >= 120) {
            ShowToastView("反馈意见不能超过120位");
            return;
        }

        final ProgressDialog progress = ViewUtil.startLoading(mContext, "正在提交...");
        User user = DaoBaseImpl.getInstance(mContext).getCurrentUser();

        HttpRequestImpl.getInstance().feedback(user, feedback, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                progress.dismiss();
                ShowToast("提交成功");
                finish();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                progress.dismiss();
                LogUtil.e(msg);
                ShowToastView(msg);
            }
        });

    }
}
