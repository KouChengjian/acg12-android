package com.acg12.common.utils.skin;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.View;

import com.acg12.common.utils.skin.entity.DynamicAttr;
import com.acg12.common.utils.skin.entity.SkinAttr;
import com.acg12.common.utils.skin.entity.SkinItem;
import com.acg12.kk.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Supply {@link SkinInflaterFactory} to be called when inflating from a LayoutInflater.
 *
 * <p>Use this to collect the {skin:enable="true|false"} views availabled in our XML layout files.
 *
 */
public class SkinInflaterFactory implements LayoutInflaterFactory {

    private static final boolean DEBUG = true;

    /**
     * Store the view item that need skin changing in the activity
     */
    private List<SkinItem> mSkinItems = new ArrayList<SkinItem>();

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // if this is NOT enable to be skined , simplly skip it
        //Log.e("onCreateView","onCreateView = " +name);
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);
        if (!isSkinEnable){
            return null;
        }

        AppCompatDelegate delegate = ((AppCompatActivity)context).getDelegate();
        View view = delegate.createView(parent, name, context, attrs);
        if(view == null){
//            Log.e("v","null");
        } else {
            LogUtil.e(view.getClass().getName()+"================");
        }

        //View view = createView(context, name, attrs);

        if (view == null){
            return null;
        }

        parseSkinAttr(context, attrs, view);

        return view;
    }

    /**
     * Collect skin able tag such as background , textColor and so on
     *
     * @param context
     * @param attrs
     * @param view
     */
    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();

        LogUtil.e("attrs",attrs.getAttributeCount()+"");
        for (int i = 0; i < attrs.getAttributeCount(); i++){
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            LogUtil.e("attrName",attrName+"==="+attrValue);
            if(!AttrFactory.isSupportedAttr(attrName)){
                continue;
            }

            if(attrValue.startsWith("@")){
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    LogUtil.e("ResourceEntry",id+"==="+entryName +"==="+typeName);
                    SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
                    if (mSkinAttr != null) {
                        viewAttrs.add(mSkinAttr);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
            }
        }

        if(viewAttrs != null && viewAttrs.size() != 0 ){
            SkinItem skinItem = new SkinItem();
            skinItem.view = view;
            skinItem.attrs = viewAttrs;

            mSkinItems.add(skinItem);

            if(SkinManager.getInstance().isExternalSkin()){
                skinItem.apply();
            }
        }
    }

    public void applySkin(){
        if(mSkinItems == null || mSkinItems.size() == 0 ){
            return;
        }
        for(SkinItem si : mSkinItems){
            if(si.view == null){
                continue;
            }
            si.apply();
        }
    }

    public void dynamicAddSkinEnableView(Context context, View view, List<DynamicAttr> pDAttrs){
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;

        for(DynamicAttr dAttr : pDAttrs){
            int id = dAttr.refResId;
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            SkinAttr mSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mSkinAttr);
        }

        skinItem.attrs = viewAttrs;
        addSkinView(skinItem);
    }

    public void dynamicAddSkinEnableView(Context context, View view, String attrName, int attrValueResId){
        int id = attrValueResId;
        String entryName = context.getResources().getResourceEntryName(id);
        String typeName = context.getResources().getResourceTypeName(id);
        SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        viewAttrs.add(mSkinAttr);
        skinItem.attrs = viewAttrs;
        addSkinView(skinItem);
    }

    public void addSkinView(SkinItem item){
        mSkinItems.add(item);
    }

    public void clean(){
        if(mSkinItems == null && mSkinItems.size() == 0){
            return;
        }

        for(SkinItem si : mSkinItems){
            if(si.view == null){
                continue;
            }
            si.clean();
        }
    }
}
