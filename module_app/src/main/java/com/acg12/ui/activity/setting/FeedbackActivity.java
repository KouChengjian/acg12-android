package com.acg12.ui.activity.setting;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.acg12.R;
import com.acg12.lib.utils.ClickUtil;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.FeedbackContract;
import com.acg12.ui.presenter.FeedbackPresenter;

import butterknife.BindView;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/07/04
 * Description: 自动生成
 */
public class FeedbackActivity extends BaseMvpActivity<FeedbackPresenter> implements FeedbackContract.View {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.et_feedback)
    EditText et_feedback;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        toolBarView.setNavigationOrBreak("意见反馈");
        toolBarView.setRightText("保存");
    }

    @Override
    protected void bindEvent() {
        super.bindEvent();
        ClickUtil.click(this, toolBarView.getToolbar(), toolBarView.getTitleRight());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.title_right) {
            submit();
        }
    }

    private void submit() {

        String feedback = et_feedback.getText().toString();
        if (TextUtils.isEmpty(feedback)) {
            showMsg("反馈意见不能为空");
            return;
        }

        if (feedback.length() >= 120) {
            showMsg("反馈意见不能超过120位");
            return;
        }

//        final ProgressDialog progress = ViewUtil.startLoading(mContext, "正在提交...");
//        User user = DaoBaseImpl.getInstance(mContext).getCurrentUser();
//
//        HttpRequestImpl.getInstance().feedback(user, feedback, new HttpRequestListener<User>() {
//            @Override
//            public void onSuccess(User result) {
//                progress.dismiss();
//                ShowToast("提交成功");
//                finish();
//            }
//
//            @Override
//            public void onFailure(int errorcode, String msg) {
//                progress.dismiss();
//                LogUtil.e(msg);
//                ShowToastView(msg);
//            }
//        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}