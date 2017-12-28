package org.acg12.ui.base;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.acg12.common.dao.DaoBaseImpl;
import com.acg12.common.entity.User;
import com.acg12.kk.ui.IView;
import com.acg12.kk.ui.base.PresenterActivityImpl;

import org.acg12.listener.IDynamicNewView;
import org.acg12.listener.ISkinUpdate;
import org.acg12.utlis.skin.SkinInflaterFactory;
import org.acg12.utlis.skin.SkinManager;
import org.acg12.utlis.skin.entity.DynamicAttr;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */
public class BaseActivity<T extends IView> extends PresenterActivityImpl<T> implements ISkinUpdate, IDynamicNewView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initSkin();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
    }

    @Override
    public void onThemeUpdate() {
        if(!isResponseOnSkinChanging) return;
        mSkinInflaterFactory.applySkin();
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
        SkinManager.getInstance().load();
    }

    // 用于接口请求获取用户，不能用于判断是否登入
    public User currentUser() {
        User user = DaoBaseImpl.getInstance(this).getCurrentUser();
        if (user == null) {
            return new User(mContext);
        } else {
            return user;
        }
    }

    /** --------------------skin 切换-------------------------*/
    private boolean isResponseOnSkinChanging = true;

    private SkinInflaterFactory mSkinInflaterFactory;

    public void initSkin(){
        mSkinInflaterFactory = new SkinInflaterFactory();
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this) , mSkinInflaterFactory);
    }

    protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId){
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs){
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    final protected void enableResponseOnSkinChanging(boolean enable){
        isResponseOnSkinChanging = enable;
    }




}
