package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;


import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.ViewUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/22.
 */
public class FeedbackView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_right)
    TextView title_right;
    @BindView(R.id.et_feedback)
    EditText et_feedback;

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("意见反馈");
        ViewUtil.setText(title_right , "保存");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter , toolbar , title_right);
    }

    public String getFeedback(){
        return et_feedback.getText().toString();
    }
}
