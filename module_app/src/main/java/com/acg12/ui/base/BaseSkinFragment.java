package com.acg12.ui.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.acg12.lib.utils.skin.entity.DynamicAttr;
import com.acg12.lib.utils.skin.listener.IDynamicNewView;

import java.util.List;

/**
 * Created with Android Studio.
 * UserEntity: KCJ
 * Date: 2019-06-19 17:16
 * Description:
 */
public abstract class BaseSkinFragment extends BaseDaggerFragment implements IDynamicNewView {

    private IDynamicNewView mIDynamicNewView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        initSkin(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        if(mIDynamicNewView == null){
            throw new RuntimeException("IDynamicNewView should be implements !");
        }else{
            mIDynamicNewView.dynamicAddView(view, pDAttrs);
        }
    }

    public void initSkin(Activity activity){
        try{
            mIDynamicNewView = (IDynamicNewView)activity;
        }catch(ClassCastException e){
            mIDynamicNewView = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
