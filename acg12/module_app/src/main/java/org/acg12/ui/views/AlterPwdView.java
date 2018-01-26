package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.acg12.lib.ui.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.DeletableEditText;

import org.acg12.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/20.
 */
public class AlterPwdView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_right)
    TextView title_right;
    @BindView(R.id.et_input_pwd_old)
    DeletableEditText et_input_pwd_old;
    @BindView(R.id.et_input_pwd_new)
    DeletableEditText et_input_pwd_new;

    @Override
    public int getLayoutId() {
        return R.layout.activity_alter_pwd;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("修改密码");
        ViewUtil.setText(title_right , "保存");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar , title_right);
    }

    public String getOldPwd(){
        return et_input_pwd_old.getText().toString();
    }

    public String getNewPwd(){
        return et_input_pwd_new.getText().toString();
    }
}
