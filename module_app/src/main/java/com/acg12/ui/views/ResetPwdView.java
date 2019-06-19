package com.acg12.ui.views;

import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.R;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.DeletableEditText;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/23.
 */
public class ResetPwdView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    private DeletableEditText et_input_name;
    private DeletableEditText et_input_password;
    private ImageView iv_pwd_show;
    private DeletableEditText et_input_verify;
    private TextView tv_verify;
    private Button btn_resetpwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    public void created() {
        super.created();
        et_input_name = findViewById(R.id.et_input_name);
        et_input_password = findViewById(R.id.et_input_password);
        iv_pwd_show = findViewById(R.id.iv_pwd_show);
        et_input_verify = findViewById(R.id.et_input_verify);
        tv_verify = findViewById(R.id.tv_verify);
        btn_resetpwd = findViewById(R.id.btn_resetpwd);

        toolBarView.setNavigationOrBreak("重置密码");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolBarView.getToolbar(), iv_pwd_show, tv_verify, btn_resetpwd);
    }

    public void edtShowOrHide(boolean flag) {
        if (!flag) {
            et_input_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            et_input_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        iv_pwd_show.setSelected(flag);
        et_input_password.postInvalidate();
        CharSequence text = et_input_password.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    public String getUsername() {
        return et_input_name.getText().toString();
    }

    public String getPassword() {
        return et_input_password.getText().toString();
    }

    public String getVerify() {
        return et_input_verify.getText().toString();
    }

    public TextView getBtnVerify() {
        return tv_verify;
    }
}
