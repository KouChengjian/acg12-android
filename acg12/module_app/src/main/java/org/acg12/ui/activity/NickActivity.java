package org.acg12.ui.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import org.acg12.conf.EventConfig;
import org.acg12.entity.User;
import com.acg12.lib.listener.HttpRequestListener;
import com.acg12.lib.utils.LogUtil;

import org.acg12.R;
import org.acg12.conf.Config;
import org.acg12.constant.Constant;
import org.acg12.net.impl.HttpRequestImpl;
import org.acg12.ui.base.SkinBaseActivity;
import org.acg12.ui.views.NickView;

public class NickActivity extends SkinBaseActivity<NickView> implements View.OnClickListener {

    private User user;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        user = (User)getIntent().getExtras().getSerializable("user");

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
            nick();
        }
    }

    public void nick(){
        String nick = mView.getNick();
        if(nick.isEmpty()){
            ShowToastView("昵称不能为空");
            return;
        }

        if(nick.length() > 10){
            ShowToastView("昵称不能超过10位");
            return;
        }

        final ProgressDialog progress = new ProgressDialog(mContext);
        progress.setMessage("正在更新昵称...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        user.setNick(nick);
        HttpRequestImpl.getInstance().nick(user, new HttpRequestListener<User>() {
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
