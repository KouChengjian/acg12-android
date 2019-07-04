package com.acg12.ui.activity.user;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.acg12.R;
import com.acg12.lib.utils.ClickUtil;
import com.acg12.lib.widget.ToolBarView;
import com.acg12.ui.base.BaseMvpActivity;
import com.acg12.ui.contract.AlterPwdContract;
import com.acg12.ui.presenter.AlterPwdPresenter;
import com.acg12.widget.DeletableEditText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created with Android Studio.
 * User kcj
 * Date 2019/06/29
 * Description: 自动生成
 */
public class AlterPwdActivity extends BaseMvpActivity<AlterPwdPresenter> implements AlterPwdContract.View {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.et_input_pwd_old)
    DeletableEditText etInputPwdOld;
    @BindView(R.id.et_input_pwd_new)
    DeletableEditText etInputPwdNew;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_alter_pwd;
    }

    @Override
    protected void created(Bundle savedInstanceState) {
        super.created(savedInstanceState);
        toolBarView.setNavigationOrBreak("修改密码");
        toolBarView.setRightText("保存");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        ClickUtil.click(this, toolBarView.getToolbar(), toolBarView.getTitleRight());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void alterPwdSucceed() {

    }

    @OnClick({R.id.title_right})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.title_right) {
            alterPwd();
        }
    }

    private void alterPwd() {
        String oldpwd = etInputPwdOld.getText().toString();
        String newPwd = etInputPwdNew.getText().toString();
        if (TextUtils.isEmpty(oldpwd)) {
            showMsg("请输入旧密码");
            return;
        }
        if (oldpwd.length() < 6) {
            showMsg("密码不少于6位，请重新输入");
            return;
        }
        if (TextUtils.isEmpty(newPwd)) {
            showMsg("请输入旧密码");
            return;
        }
        if (newPwd.length() < 6) {
            showMsg("密码不少于6位，请重新输入");
            return;
        }
        if (newPwd.equals(oldpwd)) {
            showMsg("新密码不能和旧密码相同");
            return;
        }

//        final ProgressDialog progress = new ProgressDialog(mContext);
//        progress.setMessage("正在修改密码...");
//        progress.setCanceledOnTouchOutside(false);
//        progress.setCancelable(false);
//        progress.show();
//
//        User user = currentUser();
//        user.setPassword(oldpwd);
//        user.setNewPassword(newPwd);
//
//        HttpRequestImpl.getInstance().alterPwd(user, new HttpRequestListener<User>() {
//            @Override
//            public void onSuccess(User result) {
//                progress.dismiss();
//                DaoBaseImpl.getInstance(mContext).delTabUser();
//                EventConfig.get().getUserEvent().post(new User(mContext));
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