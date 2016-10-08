package com.kcj.animationfriend.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import butterknife.InjectView;
import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.im.bean.BmobRecent;
import cn.bmob.im.db.BmobDB;

import com.kcj.animationfriend.R;
import com.kcj.animationfriend.adapter.MessageRecentAdapter;
import com.kcj.animationfriend.ui.ChatActivity;
import com.kcj.animationfriend.ui.base.BaseFragment;
import com.kcj.animationfriend.view.AlertDialogView;
import com.kcj.animationfriend.view.ClearEditText;

/**
 * @ClassName: ConversationFragment
 * @Description: 最近会话
 * @author:
 * @date: 2015-09-02
 */
public class RecentFragment extends BaseFragment implements OnItemClickListener,OnItemLongClickListener ,TextWatcher{

	@InjectView(R.id.list)
	protected ListView listview;
	@InjectView(R.id.et_msg_search)
	protected ClearEditText mClearEditText;
	protected MessageRecentAdapter adapter;
	private boolean hidden;
	
	public static BaseFragment newInstance() {
    	BaseFragment fragment = new RecentFragment();
    	return fragment;
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_recent, container, false);
		setContentView(view);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
		initEvents();
	}

	public void initViews() {
		adapter = new MessageRecentAdapter(getActivity(), R.layout.item_conversation, BmobDB.create(getActivity()).queryRecents());
		listview.setAdapter(adapter);
	}

	public void initEvents() {
		listview.setOnItemClickListener(this);
		listview.setOnItemLongClickListener(this);
		mClearEditText.addTextChangedListener(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if(!hidden){
			refresh();
		}
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		this.hidden = hidden;
		if(!hidden){
			refresh();
		}
	}
	
	public void refresh(){
		try {
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					adapter = new MessageRecentAdapter(getActivity(), R.layout.item_conversation, BmobDB.create(getActivity()).queryRecents());
					listview.setAdapter(adapter);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position,long arg3) {
		BmobRecent recent = adapter.getItem(position);
		showDeleteDialog(recent);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		BmobRecent recent = adapter.getItem(position);
		//重置未读消息
		BmobDB.create(getActivity()).resetUnread(recent.getTargetid());
		//组装聊天对象
		BmobChatUser user = new BmobChatUser();
		user.setAvatar(recent.getAvatar());
		user.setNick(recent.getNick());
		user.setUsername(recent.getUserName());
		user.setObjectId(recent.getTargetid());
		Intent intent = new Intent(getActivity(), ChatActivity.class);
		intent.putExtra("user", user);
		startAnimActivity(intent);
	}

	@Override
	public void afterTextChanged(Editable arg0) {}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,int arg3) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before,int count) {
		adapter.getFilter().filter(s);
	}
	
	public void showDeleteDialog(final BmobRecent recent) {
		final AlertDialogView alertDialog = new AlertDialogView(getActivity(),"");
		alertDialog.setContent1("标记已读", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				BmobDB.create(getActivity()).resetUnread(recent.getTargetid());
				adapter.notifyDataSetChanged();
				alertDialog.cancel();
			}
		});
		alertDialog.setContent2("删除会话", new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				deleteRecent(recent);
				alertDialog.cancel();
			}
		});
//		DialogTips dialog = new DialogTips(getActivity(),recent.getUserName(),"删除会话", "确定",true,true);
//		// 设置成功事件
//		dialog.SetOnSuccessListener(new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialogInterface, int userId) {
//				deleteRecent(recent);
//			}
//		});
//		// 显示确认对话框
//		dialog.show();
//		dialog = null;
	}
	
	/** 删除会话
	  * deleteRecent
	  * @param @param recent 
	  * @return void
	  * @throws
	  */
	private void deleteRecent(BmobRecent recent){
		adapter.remove(recent);
		BmobDB.create(getActivity()).deleteRecent(recent.getTargetid());
		BmobDB.create(getActivity()).deleteMessages(recent.getTargetid());
	}

}
