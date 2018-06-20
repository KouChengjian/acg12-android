package org.acg12.ui.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import org.acg12.dao.DaoBaseImpl;
import com.acg12.lib.entity.User;
import com.acg12.lib.ui.base.IView;
import com.acg12.lib.ui.fragment.PresenterFragmentImpl;
import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.lib.utils.skin.listener.IDynamicNewView;

import java.util.List;

/**
 * Created by Administrator on 2017/12/13.
 */
public class SkinBaseFragment<T extends IView> extends PresenterFragmentImpl<T> implements IDynamicNewView {

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
