package com.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.DeletableEditText;

import com.acg12.R;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/6/20.
 */
public class AlterPwdView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
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
        toolBarView.setNavigationOrBreak("修改密码");
        toolBarView.setRightText("保存");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolBarView.getToolbar() , toolBarView.getTitleRight());
    }

    public String getOldPwd(){
        return et_input_pwd_old.getText().toString();
    }

    public String getNewPwd(){
        return et_input_pwd_new.getText().toString();
    }
}
