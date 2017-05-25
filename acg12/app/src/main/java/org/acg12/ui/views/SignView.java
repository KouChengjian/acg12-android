package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.acg12.R;
import org.acg12.ui.ViewImpl;
import org.acg12.ui.base.PresenterHelper;
import org.acg12.utlis.ViewUtil;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/25.
 */
public class SignView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_right)
    TextView title_right;
    @BindView(R.id.edt_sign)
    EditText edt_sign;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("修改签名");
        ViewUtil.setText(title_right,"保存");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar,title_right);
    }

    public String getSign(){
        return edt_sign.getText().toString();
    }

    public void setSign(String sign){
        if(sign!=null){
            edt_sign.setText(sign);
            edt_sign.setSelection(sign.length());
        }
    }
}
