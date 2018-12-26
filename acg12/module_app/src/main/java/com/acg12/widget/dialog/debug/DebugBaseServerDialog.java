package com.acg12.widget.dialog.debug;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;


import com.acg12.conf.AppConfig;

import com.acg12.R;
import com.acg12.conf.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class DebugBaseServerDialog implements View.OnClickListener {

    private static final String serverSP = "serverSP";
//    private static final String SERVER_LIST = "serverList";

    private Activity context;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private LinearLayout layout;
    private RecyclerView recyclerView;
    private List<ServerUrl> serverList;
    private int position;

    public DebugBaseServerDialog(final Activity context) {
        this.context = context;
        builder = new AlertDialog.Builder(context);

        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.include_dialog_debug_base_server, null);


        serverList = new ArrayList<>();
        serverList.addAll(AppConfig.DefaultServerUrlList);
        position = AppConfig.getServerUrl(context);

        int sysIndex = 0;
        int selectedIndex = position;

        recyclerView = (RecyclerView) layout.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DebugServerDialogAdapter adapter = new DebugServerDialogAdapter(serverList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DebugServerDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ServerUrl url) {
                DebugBaseServerDialog.this.position = position;
                DebugServerDialogAdapter tmpAdapter = (DebugServerDialogAdapter) recyclerView.getAdapter();
                tmpAdapter.setSelectPosition(position);
                tmpAdapter.notifyDataSetChanged();
            }
        });

        adapter.setSysPosition(sysIndex);
        adapter.setSelectPosition(selectedIndex);

        Button btnNext = (Button) layout.findViewById(R.id.btn_set);
        btnNext.setOnClickListener(this);
        Button btnClose = (Button) layout.findViewById(R.id.btn_close_dialog);
        btnClose.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_set){
            onSetServerUrl();
        } else if(view.getId() == R.id.btn_close_dialog){
            onAddServerUrl();
        }
    }

    private void onAddServerUrl() {
        dismiss();
    }

    private void onSetServerUrl() {
        // 创建构建器
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // 设置参数
        builder.setTitle("重启APP?")
                .setMessage(" 重启配置生效 ")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppConfig.setServerUrl(context , position);
                        Intent mStartActivity = new Intent("android.intent.action.MAIN");
                        mStartActivity.setClassName("com.shangyizhijia.marke","com.shangyizhijia.marke.ui.activity.SplashNullActivity");
                        int mPendingIntentId = 99999999;
                        PendingIntent mPendingIntent = PendingIntent.getActivity(context, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                        System.exit(0);
                    }
                }).
                setNegativeButton("不要", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });
        builder.create().show();
        dismiss();
    }

    public void show() {
        if (dialog == null) {
            dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
        }
        dialog.show();
        dialog.getWindow().setContentView(layout);
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
