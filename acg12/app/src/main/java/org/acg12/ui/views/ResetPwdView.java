package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.widget.DeletableEditText;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/23.
 */
public class ResetPwdView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_input_name)
    DeletableEditText et_input_name;
    @BindView(R.id.et_input_password)
    DeletableEditText et_input_password;
    @BindView(R.id.iv_pwd_show)
    ImageView iv_pwd_show;
    @BindView(R.id.et_input_verify)
    DeletableEditText et_input_verify;
    @BindView(R.id.tv_verify)
    TextView tv_verify;
    @BindView(R.id.btn_resetpwd)
    Button btn_resetpwd;

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("重置密码");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar ,iv_pwd_show ,tv_verify ,btn_resetpwd);
    }

    public void edtShowOrHide(boolean flag){
        if (!flag) {
            et_input_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else {
            et_input_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        iv_pwd_show.setSelected(flag);
        et_input_password.postInvalidate();
        CharSequence text = et_input_password.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable)text;
            Selection.setSelection(spanText, text.length());
        }
    }

    public String getUsername(){
        return et_input_name.getText().toString();
    }

    public String getPassword(){
        return et_input_password.getText().toString();
    }

    public String getVerify(){
        return et_input_verify.getText().toString();
    }

    public TextView getBtnVerify(){
        return tv_verify;
    }
}
