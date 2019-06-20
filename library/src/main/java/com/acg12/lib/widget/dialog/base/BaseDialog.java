package com.acg12.lib.widget.dialog.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.DimenRes;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.acg12.lib.R;
import com.acg12.lib.utils.ToastUtil;


public class BaseDialog extends Dialog {

    private View mDialogView;
//    private ImageLoader imageLoader;

    public BaseDialog(Context context) {
        this(context, 0);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    /**
     * 设置点击事件
     */
    public BaseDialog setOnClickListener(int viewId, View.OnClickListener listener) {
        mDialogView.findViewById(viewId).setOnClickListener(listener);
        return this;
    }

    public BaseDialog setImage(int viewId, Object path) {
        ImageView imageView = mDialogView.findViewById(viewId);
//        imageLoader.displayImage(imageView.getContext(), path, imageView);
        return this;
    }

    /**
     * 设置TextView 文本
     */
    public BaseDialog setText(int viewId, CharSequence text) {
        ((TextView) mDialogView.findViewById(viewId)).setText(text);
        return this;
    }

    /**
     * 设置TextView 文本
     */
    public BaseDialog setTextSize(int viewId, @DimenRes int resId) {
        ((TextView) mDialogView.findViewById(viewId))
                .setTextSize(TypedValue.COMPLEX_UNIT_PX, Math.round(getContext().getResources().getDimension(resId)));
        return this;
    }

    public BaseDialog setVisibility(int viewId, int visibility) {
        mDialogView.findViewById(viewId).setVisibility(visibility);
        return this;
    }

    public BaseDialog cancelable(boolean flag) {
        super.setCancelable(flag);
        return this;
    }

    public BaseDialog dismissDialog() {
        super.dismiss();
        return this;
    }

    public BaseDialog canceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public BaseDialog showDialog() {
        super.show();
        return this;
    }

    public void showMsg(String text) {
        ToastUtil.show(text, 3);
    }

    /**
     * 绑定和设置参数
     */
    private void apply(Builder.DialogParams P) {
        mDialogView = P.mView;// 资源  View

        if (mDialogView == null && P.mViewLayoutResId == 0) {
            throw new NullPointerException("Please set layout resource");
        }

        mDialogView = View.inflate(P.context, P.mViewLayoutResId, null);

//        imageLoader = P.imageLoader;

        // 设置布局
        setContentView(mDialogView);


        // 设置基本参数
        Window window = getWindow();
        assert window != null;
        window.setLayout(P.mWidth, P.mHeight);
        window.getDecorView().setPadding(P.left, P.top, P.right, P.bottom);
        window.setGravity(P.mGravity);
        if (P.mAnimation != 0) {
            window.setWindowAnimations(P.mAnimation);
        }

    }

    public static class Builder {

        private DialogParams P;

        public Builder(Context context) {
            // 如果没有传默认的就是自定义的主题
            this(context, R.style.Dialog);
        }

        public Builder(Context context, int themeResId) {
            P = new DialogParams(context, themeResId);
        }

        public Builder setView(int layoutResId) {
            P.mView = null;
            P.mViewLayoutResId = layoutResId;
            return this;
        }

//        public Builder setImageLoader(ImageLoader imageLoader) {
//            P.imageLoader = imageLoader;
//            return this;
//        }

        public Builder setView(View view) {
            P.mView = view;
            P.mViewLayoutResId = 0;
            return this;
        }

        /**
         * 设置宽度
         */
        public Builder setWidth(int width) {
            P.mWidth = width;
            return this;
        }

        /**
         * 设置高度
         */
        public Builder setHeight(int height) {
            P.mHeight = height;
            return this;
        }

        public Builder setGravity(int gravity) {
            P.mGravity = gravity;
            return this;
        }

        /**
         * 显示动画  可以自定动画
         */
        public Builder showAnimation(int styleResId) {
            P.mAnimation = styleResId;
            return this;
        }

        /**
         * 设置间距
         *
         * @param left
         * @param top
         * @param right
         * @param bottom
         */
        public Builder setPadding(int left, int top, int right, int bottom) {
            P.left = left;
            P.top = top;
            P.right = right;
            P.bottom = bottom;
            return this;
        }

        public BaseDialog create() {
            // Context has already been wrapped with the appropriate theme.
            final BaseDialog dialog = new BaseDialog(P.context, P.themeResId);
            // 绑定一些参数
            dialog.apply(P);
            return dialog;
        }

        public static class DialogParams {
            public Context context;
            public int themeResId;
            public View mView;
            public int mViewLayoutResId;
            public int mHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
            public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
            public int mAnimation = 0;
            public int mGravity = Gravity.CENTER;
            public int left = 0;
            public int top = 0;
            public int right = 0;
            public int bottom = 0;
//            public ImageLoader imageLoader;

            public DialogParams(Context context, int themeResId) {
                this.context = context;
                this.themeResId = themeResId;
//                imageLoader = new GlideImageLoader();
            }
        }
    }

/*        new BaseDialog.Builder(context)
            .setView(R.layout.include_dialog_loading)
                .create()
                .cancelable(false)
                .canceledOnTouchOutside(false)
                .setText(R.id.tv_content, message)
                .setVisibility(R.id.tv_content, TextTools.isEmpty(message) ? View.GONE : View.VISIBLE)
            .showDialog();*/
}
