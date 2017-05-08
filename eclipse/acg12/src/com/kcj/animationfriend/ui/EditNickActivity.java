package com.kcj.animationfriend.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import butterknife.InjectView;
import cn.bmob.v3.listener.UpdateListener;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.bean.User;
import com.kcj.animationfriend.config.HttpProxy;
import com.kcj.animationfriend.config.UserProxy;
import com.kcj.animationfriend.ui.base.BaseSwipeBackActivity;


/**
 * @ClassName: EditNickActivity
 * @Description: 昵称
 * @author kcj
 * @date 
 */
public class EditNickActivity extends BaseSwipeBackActivity{
	
	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	private Button nickCommit;
	private EditText nickInput;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editnick);
        setTitle(R.string.user_nick);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		nickInput = (EditText)findViewById(R.id.nick_comment_content);
		nickCommit = (Button)findViewById(R.id.nick_comment_commit);
		nickCommit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(nickInput.getText().toString().trim())){
					ShowToast( "请先输入。。。");
				}else{
					updateNick(nickInput.getText().toString().trim());
				}
			}
		});
	}
	
	private void updateNick(String nick){
		User user = HttpProxy.getCurrentUser(mContext);
		if(user != null && nick != null){
			user.setNick(nick);
			user.update(EditNickActivity.this, new UpdateListener() {
				@Override
				public void onSuccess() {
					ShowToast("更改信息成功。");
					finish();
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					ShowToast( "更改信息失败。请检查网络");
				}
			});
		}
	}
}
