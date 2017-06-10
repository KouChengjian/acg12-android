package org.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import org.acg12.R;
import org.acg12.bean.User;
import org.acg12.conf.Config;
import org.acg12.conf.Constant;
import org.acg12.listener.HttpRequestListener;
import org.acg12.net.HttpRequestImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.SignView;
import org.acg12.utlis.LogUtil;
import org.acg12.utlis.Network;

public class SignActivity extends PresenterActivityImpl<SignView> implements View.OnClickListener {

    User user;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        user = (User)getIntent().getExtras().getSerializable("user");
        mView.setSign(user.getSignature());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == Constant.TOOLBAR_ID){
            finish();
        } else if(id == R.id.title_right){
            sign();
        }
    }

    public void sign(){
        boolean isNetConnected = Network.isConnected(mContext);
        if (!isNetConnected) {
            ShowToastView(R.string.network_tips);
            return;
        }

        String sign = mView.getSign();
        if(sign.isEmpty()){
            ShowToastView("签名不能为空");
            return;
        }
        LogUtil.e(sign.length()+"==");
        if(sign.length() > 20){
            ShowToastView("签名不能超过20位");
            return;
        }

        final ProgressDialog progress = new ProgressDialog(mContext);
        progress.setMessage("正在更新签名...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        user.setSignature(sign);
        HttpRequestImpl.getInstance().sign(user, new HttpRequestListener<User>() {
            @Override
            public void onSuccess(User result) {
                ShowToastView("更新成功");
                Config.userEventBus().post(result);
                progress.dismiss();
                finish();
            }

            @Override
            public void onFailure(int errorcode, String msg) {
                LogUtil.e(msg);
                ShowToastView(msg);
                progress.dismiss();
            }
        });
    }
}
