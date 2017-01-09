package com.skin.loader.entity.base;

import android.view.View;

import com.skin.loader.entity.base.SkinAttr;
import com.skin.loader.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2016/12/29.
 */
public class SkinItem {
    public View view;

    public List<SkinAttr> attrs;

    public SkinItem(){
        attrs = new ArrayList<SkinAttr>();
    }

    public void apply(){
        if(ListUtils.isEmpty(attrs)){
            return;
        }
        for(SkinAttr at : attrs){
            at.apply(view);
        }
    }

    public void clean(){
        if(ListUtils.isEmpty(attrs)){
            return;
        }
        for(SkinAttr at : attrs){
            at = null;
        }
    }

    @Override
    public String toString() {
        return "SkinItem [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
    }
}
