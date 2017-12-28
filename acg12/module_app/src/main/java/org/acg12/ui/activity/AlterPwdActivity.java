package org.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.acg12.common.dao.DaoBaseImpl;
import com.acg12.common.entity.User;
import com.acg12.common.net.UserHttpRequestImpl;
import com.acg12.kk.listener.HttpRequestListener;
import com.acg12.kk.utils.LogUtil;

import org.acg12.R;
import org.acg12.conf.Config;
import org.acg12.ui.base.BaseActivity;
import org.acg12.ui.views.AlterPwdView;

public class AlterPwdActivity extends BaseActivity<AlterPwdView> implements View.OnClickListener{

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view.getId() == R.id.title_right){
            alterPwd();
        }
    }

    private void alterPwd(){
        String oldpwd = mView.getOldPwd();
        String newPwd = mView.getNewPwd();

        if (TextUtils.isEmpty(oldpwd)) {
            ShowToastView("请输入旧密码");
            return;
        }

        if (oldpwd.length() < 6 ) {
            ShowToastView(R.string.toast_error_password);
            return;
        }

        if (TextUtils.isEmpty(newPwd)) {
            ShowToastView("请输入旧密码");
            return;
        }

        if (newPwd.length() < 6 ) {
            ShowToastView(R.string.toast_error_password);
            return;
        }

        if(newPwd.equals(oldpwd)){
            ShowToastView("新密码不能和旧密码相同");
            return;
        }

        final ProgressDialog progress = new ProgressDialog(mContext);
        progress.setMessage("正在修改密码...");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.show();

        User user = currentUser();
        user.setPassword(oldpwd);
        user.setNewPassword(newPwd);

        UserHttpRequestImpl.getInstance(mContext).alterPwd(user, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                progress.dismiss();
                DaoBaseImpl.getInstance(mContext).delTabUser();
                Config.userEventBus().post(new User(mContext));
                finish();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                progress.dismiss();
                LogUtil.e(msg);
                ShowToastView(msg);
            }
        });
    }
}
