package com.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.DeletableEditText;

import com.acg12.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/23.
 */
public class LoginView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_right)
    TextView title_right;
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
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("登录");
        ViewUtil.setText(title_right, "忘记密码?");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolbar, title_right, btn_login, btn_register);
    }

    public String getUsername() {
        return et_input_name.getText().toString();
    }

    public String getPassword() {
        return et_input_password.getText().toString();
    }
}
