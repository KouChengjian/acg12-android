package com.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.acg12.lib.conf.EventConfig;
import com.acg12.entity.User;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;

import com.acg12.R;
import com.acg12.lib.cons.Constant;
import com.acg12.net.impl.HttpRequestImpl;
import com.acg12.ui.base.SkinBaseActivity;
import com.acg12.ui.views.SignView;
import com.acg12.utlis.Network;

public class SignActivity extends SkinBaseActivity<SignView> implements View.OnClickListener {

    private User user;

    @Override
    public void create(Bundle savedInstance) {
        super.create(savedInstance);
        setTranslucentStatus();
    }

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
            aminFinish();
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
                EventConfig.get().getUserEvent().post(result);
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
