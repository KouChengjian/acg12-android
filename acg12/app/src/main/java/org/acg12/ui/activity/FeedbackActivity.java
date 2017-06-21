package org.acg12.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import org.acg12.R;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.FeedbackView;
import org.acg12.utlis.Network;

public class FeedbackActivity extends PresenterActivityImpl<FeedbackView> implements View.OnClickListener{

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
        if(view.getId() == R.id.title_right){
            submit();
        }
    }

    private void submit(){
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

        if (feedback.length() >= 120 ) {
            ShowToastView("反馈意见不能超过120位");
            return;
        }


    }
}
