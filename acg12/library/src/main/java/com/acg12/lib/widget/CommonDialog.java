package com.acg12.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.acg12.lib.R;


/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2018/7/28 14:02
 * Description:
 */
public class CommonDialog extends Dialog {

    Context context;
    TextView tvTitle;
    TextView tvContent;
    BGButton cancel;
    BGButton commit;
    private Callback callback;

    public CommonDialog(Context context, String title, String content) {
        this(context, title, content, true);
    }

    public CommonDialog(Context context, String title, String content, boolean falg) {
        super(context, R.style.Dialog);
        this.context = context;
        this.setCanceledOnTouchOutside(falg);
        setContentView(R.layout.include_dialog_common);

        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvContent = (TextView) findViewById(R.id.tv_content);
        cancel = (BGButton) findViewById(R.id.cancel);
        commit = (BGButton) findViewById(R.id.commit);

        tvTitle.setText(title);
        tvContent.setText(content);


        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.commit();
                }
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.cancle();
                }
            }
        });
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void showBtn() {
        super.show();
        commit.setVisibility(View.GONE);
        cancel.setText("退出");
    }

    public void setBtnText(String commitTxt, String cancelTxt) {
        commit.setText(commitTxt);
        cancel.setText(cancelTxt);
    }

    public void setBtnTextColor(int commitColor, int cancelColor) {
        commit.setTextColor(commitColor);
        cancel.setTextColor(cancelColor);

    }

    public interface Callback {
        void commit();

        void cancle();
    }
}
