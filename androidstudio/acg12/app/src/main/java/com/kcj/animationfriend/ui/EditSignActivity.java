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
import com.kcj.animationfriend.ui.base.BaseActivity;



/**
 * @ClassName: EditSignActivity
 * @Description: 编辑个性签名
 * @author kcj
 * @date 
 */
public class EditSignActivity extends BaseActivity{
	
	@InjectView(R.id.toolbar)
	protected Toolbar toolbar;
	private Button commit;
	private EditText input;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editsigns);
		setTitle(R.string.user_sign);
		setSupportActionBar(toolbar);
		
		input = (EditText)findViewById(R.id.sign_comment_content);
		commit = (Button)findViewById(R.id.sign_comment_commit);
        commit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(TextUtils.isEmpty(input.getText().toString().trim())){
					ShowToast( "请先输入。。。");
				}else{
					updateSign(input.getText().toString().trim());
				}
			}
		});
	}
	
	private void updateSign(String sign){
		User user = HttpProxy.getCurrentUser(mContext);
		if(user != null && sign != null){
			user.setSignature(sign);
			user.update(EditSignActivity.this, new UpdateListener() {
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
