package com.acg12.ui.views;

import android.widget.EditText;

import com.acg12.R;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/22.
 */
public class FeedbackView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.et_feedback)
    EditText et_feedback;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("意见反馈");
        toolBarView.setRightText("保存");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolBarView.getToolbar(), toolBarView.getTitleRight());
    }

    public String getFeedback() {
        return et_feedback.getText().toString();
    }
}
