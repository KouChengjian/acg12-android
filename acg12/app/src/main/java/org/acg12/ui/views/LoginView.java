package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.widget.Button;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.ViewUtil;
import org.acg12.widget.DeletableEditText;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/23.
 */
public class LoginView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_input_name)
    DeletableEditText et_input_name;
    @BindView(R.id.et_input_password)
    DeletableEditText et_input_password;
    @BindView(R.id.btn_login)
    Button btn_login;


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.login));
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar ,btn_login);
    }

    public String getUsername(){
        return et_input_name.getText().toString();
    }

    public String getPassword(){
        return et_input_password.getText().toString();
    }
}
