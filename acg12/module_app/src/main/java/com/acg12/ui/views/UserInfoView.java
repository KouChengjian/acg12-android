package com.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.acg12.entity.User;

import com.acg12.entity.User;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.utils.glide.ImageLoadUtils;

import com.acg12.R;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/23.
 */
public class UserInfoView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.rl_user_avatar)
    RelativeLayout rl_user_avatar;
    @BindView(R.id.iv_user_avatar)
    ImageView iv_user_avatar;
    @BindView(R.id.rl_user_nick)
    RelativeLayout rl_user_nick;
    @BindView(R.id.tv_user_nick)
    TextView tv_user_nick;
    @BindView(R.id.rl_user_account)
    RelativeLayout rl_user_account;
    @BindView(R.id.tv_user_account)
    TextView tv_user_account;
    @BindView(R.id.rl_user_sex)
    RelativeLayout rl_user_sex;
    @BindView(R.id.iv_user_sex)
    ImageView iv_user_sex;
    @BindView(R.id.rl_user_sign)
    RelativeLayout rl_user_sign;
    @BindView(R.id.tv_user_sign)
    TextView tv_user_sign;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("用户信息");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolBarView.getToolbar());
        PresenterHelper.click(mPresenter ,rl_user_avatar ,rl_user_nick  ,iv_user_sex ,rl_user_sign);
    }

    public void paddingDate(User user) {
        if (user == null )
            return;

        String avatar = user.getAvatar();
        if(avatar != null){
            ImageLoadUtils.glideCircleLoading(getContext() ,avatar , iv_user_avatar );
            setSexSelector(user.getSex());
            ViewUtil.setText(tv_user_account , user.getUsername());
            ViewUtil.setText(tv_user_nick , user.getNick());
            ViewUtil.setText(tv_user_sign , user.getSignature());
        }

    }

    public void setSexSelector(int sexSelector){
        if(sexSelector == 0){
            iv_user_sex.setSelected(false);
        }else {
            iv_user_sex.setSelected(true);
        }
    }
}
