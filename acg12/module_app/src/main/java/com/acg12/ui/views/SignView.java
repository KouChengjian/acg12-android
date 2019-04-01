package com.acg12.ui.views;

import android.widget.EditText;

import com.acg12.R;
import com.acg12.lib.ui.base.PresenterHelper;
import com.acg12.lib.ui.base.ViewImpl;
import com.acg12.lib.widget.ToolBarView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/25.
 */
public class SignView extends ViewImpl {

    @BindView(R.id.toolBarView)
    ToolBarView toolBarView;
    @BindView(R.id.edt_sign)
    EditText edt_sign;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    public void created() {
        super.created();
        toolBarView.setNavigationOrBreak("修改签名");
        toolBarView.setRightText("保存");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter, toolBarView.getToolbar(), toolBarView.getTitleRight());
    }

    public String getSign() {
        return edt_sign.getText().toString();
    }

    public void setSign(String sign) {
        if (sign != null) {
            edt_sign.setText(sign);
            edt_sign.setSelection(sign.length());
        }
    }
}
