package org.acg12.ui.views;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.acg12.kk.ui.ViewImpl;
import com.acg12.kk.ui.base.PresenterHelper;
import com.acg12.kk.utils.ViewUtil;
import com.acg12.kk.widget.DeletableEditText;

import org.acg12.R;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/5/25.
 */
public class NickView extends ViewImpl {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_right)
    TextView title_right;
    @BindView(R.id.edt_nick)
    DeletableEditText edt_nick;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nick;
    }

    @Override
    public void created() {
        super.created();
        toolbar.setNavigationIcon(R.mipmap.ic_action_back);
        toolbar.setTitle("修改昵称");
        ViewUtil.setText(title_right,"保存");
    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        PresenterHelper.click(mPresenter ,toolbar,title_right);
    }

    public String getNick(){
        return edt_nick.getText().toString();
    }
}
