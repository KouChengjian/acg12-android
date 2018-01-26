package com.acg12.lib.ui.view;

import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.acg12.lib.R;
import com.acg12.lib.ui.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.DeletableEditText;

/**
 * Created by Administrator on 2017/5/23.
 */
public class LoginView extends ViewImpl {

    private Toolbar toolbar;
    private TextView title_right;
    private DeletableEditText et_input_name;
    private DeletableEditText et_input_password;
    private Button btn_login;
    private Button btn_register;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void created() {
        super.created();
        toolbar = findViewById(R.id.toolbar);
        title_right = findViewById(R.id.title_right);
        et_input_name = findViewById(R.id.et_input_name);
        et_input_password = findViewById(R.id.et_input_password);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("登录");
        ViewUtil.setText(title_right,"忘记密码?");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar , title_right , btn_login ,btn_register);
    }

    public String getUsername(){
        return et_input_name.getText().toString();
    }

    public String getPassword(){
        return et_input_password.getText().toString();
    }
}
