package com.acg12.ui.views;

import android.widget.Button;

import com.acg12.R;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.DeletableEditText;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/23.
 */
public class LoginView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.et_input_name)
    DeletableEditText et_input_name;
    @BindView(R.id.et_input_password)
    DeletableEditText et_input_password;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.btn_register)
    Button btn_register;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("登录");
        toolBarView.setRightText("忘记密码?");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolBarView.getToolbar(), toolBarView.getTitleRight(), btn_login, btn_register);
    }

    public String getUsername() {
        return et_input_name.getText().toString();
    }

    public String getPassword() {
        return et_input_password.getText().toString();
    }
}
