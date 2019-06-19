package com.acg12.lib.utils.skin.entity;

import android.view.View;


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
        if(attrs == null || attrs.size() == 0){
            return;
        }
        for(SkinAttr at : attrs){
            at.apply(view);
        }
    }

    public void clean(){
        if(attrs == null || attrs.size() == 0){
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
