package org.acg12.widget.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.acg12.R;


public class AlertDialogView {

	AlertDialog dlg;
	Window window;

	public AlertDialogView(Activity mContext, String title) {
		dlg = new AlertDialog.Builder(mContext).create();
		dlg.show();
		window = dlg.getWindow();
		// *** 主要就是在这里实现这种效果的.
		// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		window.setContentView(R.layout.include_dialog_alert);
		if (title != null && !title.equals("")) {
			LinearLayout ll_title = (LinearLayout) window
					.findViewById(R.id.ll_title);
			ll_title.setVisibility(View.VISIBLE);
			TextView tv_title = (TextView) window.findViewById(R.id.tv_title);
			tv_title.setText(title);
		}
	}

	public void setContent1(String text, View.OnClickListener onClickListener) {
		TextView tv_content = (TextView) window.findViewById(R.id.tv_content1);
		tv_content.setText(text);
		tv_content.setOnClickListener(onClickListener);
	}

	public void setContent2(String text, View.OnClickListener onClickListener) {
        LinearLayout ll_content = (LinearLayout) window.findViewById(R.id.ll_content2);
        ll_content.setVisibility(View.VISIBLE);
		TextView tv_content = (TextView) window.findViewById(R.id.tv_content2);
		tv_content.setText(text);
		tv_content.setOnClickListener(onClickListener);
	}

	public void setContent3(String text, View.OnClickListener onClickListener) {
		LinearLayout ll_content = (LinearLayout) window.findViewById(R.id.ll_content3);
        ll_content.setVisibility(View.VISIBLE);
		TextView tv_content1 = (TextView) window.findViewById(R.id.tv_content3);
		tv_content1.setText(text);
		tv_content1.setOnClickListener(onClickListener);
	}

	public void setContent4(String text, View.OnClickListener onClickListener) {
		LinearLayout ll_content = (LinearLayout) window.findViewById(R.id.ll_content4);
        ll_content.setVisibility(View.VISIBLE);
		TextView tv_content1 = (TextView) window.findViewById(R.id.tv_content4);
		tv_content1.setText(text);
		tv_content1.setOnClickListener(onClickListener);
	}

	public void setContent5(String text, View.OnClickListener onClickListener) {
		LinearLayout ll_content = (LinearLayout) window.findViewById(R.id.ll_content5);
        ll_content.setVisibility(View.VISIBLE);
		TextView tv_content = (TextView) window.findViewById(R.id.tv_content5);
		tv_content.setText(text);
		tv_content.setOnClickListener(onClickListener);
	}

	public void show() {
		dlg.show();
	}

	public void dismiss() {
		dlg.dismiss();
	}
	
	public void cancel(){
		dlg.cancel();
	}

}
