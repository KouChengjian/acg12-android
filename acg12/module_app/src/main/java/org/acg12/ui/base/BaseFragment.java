package org.acg12.ui.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.acg12.common.dao.DaoBaseImpl;
import com.acg12.common.entity.User;
import com.acg12.kk.ui.IView;
import com.acg12.kk.ui.base.PresenterFragmentImpl;

import org.acg12.listener.IDynamicNewView;
import org.acg12.utlis.skin.entity.DynamicAttr;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */
public class BaseFragment<T extends IView> extends PresenterFragmentImpl<T> implements IDynamicNewView {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initSkin(getActivity());
    }

    // 用于接口请求获取用户，不能用于判断是否登入
    public User currentUser(){
        User user = DaoBaseImpl.getInstance(getContext()).getCurrentUser();
        if(user == null){
            return new User(mContext);
        } else {
            return user;
        }
    }

    private IDynamicNewView mIDynamicNewView;

    public void initSkin(Activity activity){
        try{
            mIDynamicNewView = (IDynamicNewView)activity;
        }catch(ClassCastException e){
            mIDynamicNewView = null;
        }
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        if(mIDynamicNewView == null){
            throw new RuntimeException("IDynamicNewView should be implements !");
        }else{
            mIDynamicNewView.dynamicAddView(view, pDAttrs);
        }
    }
}
