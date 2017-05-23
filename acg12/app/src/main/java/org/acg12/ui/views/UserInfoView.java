package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.bean.User;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.ImageLoadUtils;
import org.acg12.utlis.ViewUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/23.
 */
public class UserInfoView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
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
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle(getContext().getString(R.string.userinfo));
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar);
        PresenterHelper.click(mPresenter ,rl_user_avatar ,rl_user_nick ,rl_user_account ,rl_user_sex ,rl_user_sign);
    }

    public void paddingDate(User user) {
        if (user == null )
            return;

        String avatar = user.getAvatar();
        if(avatar != null){
            ImageLoadUtils.universalLoading(avatar , iv_user_avatar );
            if(user.getSex().equals("male")){
                iv_user_sex.setSelected(false);
            }else {
                iv_user_sex.setSelected(true);
            }
            ViewUtil.setText(tv_user_account , user.getUsername());
            ViewUtil.setText(tv_user_nick , user.getNick());
            ViewUtil.setText(tv_user_sign , user.getSignature());
        }

    }
}
