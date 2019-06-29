package com.acg12.lib.ui.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acg12.lib.ui.IView;
import com.acg12.lib.utils.AppStartUtil;
import com.acg12.lib.utils.DoubleClickUtil;
import com.acg12.lib.utils.ToastUtil;
import com.acg12.lib.utils.ViewUtil;
import com.acg12.lib.widget.dialog.base.DialogLoader;
import com.acg12.lib.widget.dialog.LoadingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2019/1/7 13:47
 * Description:
 */
public abstract class BaseFragment extends Fragment implements IView, View.OnClickListener {

    protected Context mContext;
    protected DialogLoader dialogLoader;
    protected Unbinder unbinder;
    protected ProgressDialog mProgressDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        create(savedInstanceState);
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getLayoutId() != 0) {
            return inflater.inflate(getLayoutId(), container, false);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        created(savedInstanceState);
        bindEvent();
    }

    @Override
    public void onClick(View v) {
        if (DoubleClickUtil.isFastDoubleClick()) {
            return;
        }
    }

    @Override
    public Context context() {
        return mContext;
    }

    @Override
    public void setLoadingDialog(DialogLoader dialog) {
        dialogLoader = dialog;
    }

    @Override
    public void showProgressDialog(String msg) {
//        if (null == dialogLoader) dialogLoader = LoadingDialog.get();
//        dialogLoader.showDialog(mContext, null);
        if (mProgressDialog == null) {
            mProgressDialog = ViewUtil.startLoading(mContext, msg);
        } else {
            mProgressDialog.setMessage(msg);
            mProgressDialog.show();
        }
    }

    @Override
    public void dismissProgressDialog() {
//        if (null == dialogLoader) dialogLoader = LoadingDialog.get();
//        dialogLoader.dismissDialog(mContext);
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showMsg(String msg) {
        ToastUtil.showShort(msg);
    }

    /**
     * Dagger2 use in your application module(not used in 'base' module)
     */
    protected abstract void create(Bundle savedInstanceState);

    /**
     * override this method to return content view id of the fragment
     */
    protected abstract int getLayoutId();

    /**
     * override this method to do operation in the fragment
     */
    protected abstract void created(Bundle savedInstanceState);

    /**
     * override this method to do operation in the fragment
     */
    protected void bindEvent() {
    }

    protected void startAnimActivity(Class<?> cls) {
        startAnimActivity(cls, null, 0);
    }

    protected void startAnimActivity(Class<?> cls, int code) {
        startAnimActivity(cls, null, code);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle) {
        startAnimActivity(cls, bundle, 0);
    }

    protected void startAnimActivity(Class<?> cls, Bundle bundle, int code) {
        AppStartUtil.startAnimActivity(this, cls, bundle, -1, code);
    }

    @Override
    public void onDestroyView() {
        if (null != unbinder) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }
}
