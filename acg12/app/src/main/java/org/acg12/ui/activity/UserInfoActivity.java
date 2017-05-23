package org.acg12.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import org.acg12.R;
import org.acg12.bean.User;
import org.acg12.config.Config;
import org.acg12.config.Constant;
import org.acg12.db.DaoBaseImpl;
import org.acg12.ui.base.PresenterActivityImpl;
import org.acg12.ui.views.UserInfoView;
import org.acg12.utlis.LogUtil;
import org.acg12.utlis.premission.ApplyPermission;
import org.acg12.utlis.premission.FailPermission;
import org.acg12.utlis.premission.SuccessPermission;
import org.acg12.widget.CommonPopupWindows;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class UserInfoActivity extends PresenterActivityImpl<UserInfoView> implements View.OnClickListener ,CommonPopupWindows.OnUpdateAvatar {

    User user;
    CommonPopupWindows commonPopupWindows;

    @Override
    public void created(Bundle savedInstance) {
        super.created(savedInstance);
        user = (User) getIntent().getExtras().getSerializable("user");
        mView.paddingDate(user);
        commonPopupWindows = new CommonPopupWindows(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        commonPopupWindows.onActivityResult(requestCode ,resultCode ,data );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        ApplyPermission.onRequestPermissionsResult(this, requestCode,permissions, grantResults);
    }

    @SuccessPermission(requestCode = Constant.USER_APPLY_PERMISSION_CAMERE)
    public void openCamera() {
        commonPopupWindows.getOpenCamera();
    }

    @FailPermission(requestCode = Constant.USER_APPLY_PERMISSION_CAMERE)
    public void failOpenCamera() {
        //ShowToast("需要摄像机权限，请前往设置权限管理设置权限");
        ApplyPermission.showMissingPermissionDialog(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == Constant.TOOLBAR_ID){
            finish();
        } else if(id == R.id.rl_user_avatar){
            commonPopupWindows.initOpenCamera();
            commonPopupWindows.setOnUpdateAvatar(this);
        } else if(id == R.id.rl_user_nick){

        } else if(id == R.id.rl_user_account){

        } else if(id == R.id.rl_user_sex){

        } else if(id == R.id.rl_user_sign){

        }
    }

    @Override
    public void updateAvatar(String url) {
        LogUtil.e(url);
        if (url == null || url.isEmpty())
            return;

        final ProgressDialog progress = new ProgressDialog(mContext);
        progress.setMessage("正在更新头像...");
        progress.setCanceledOnTouchOutside(false);
        progress.show();

        final BmobFile file = new BmobFile(new File(url));
//        file.upload(mContext, new UploadFileListener() {
//
//            @Override
//            public void onSuccess() {
//                progress.setMessage("同步数据...");
//                updateAvatar(progress , file);
//            }
//
//            @Override
//            public void onFailure(int i, String s) {
//                progress.dismiss();
////                LogUtil.e(s);
//                ShowToast(s);
//            }
//        });
    }

    public void updateAvatar(final ProgressDialog progress , BmobFile file){
//        final User currentUser = BmobUser.getCurrentUser(mContext, User.class);
//        currentUser.setAvatar(file.getFileUrl(mContext));
//        currentUser.update(mContext, new UpdateListener() {
//
//            @Override
//            public void onSuccess() {
//                progress.dismiss();
//                User user = DaoBaseImpl.getInstance().getCurrentUser();
//                user.setAvatar(currentUser.getAvatar());
//                DaoBaseImpl.getInstance().saveUser(user);
//                Config.userEventBus().post(user);
//
//                mView.paddingDate(user);
//            }
//
//            @Override
//            public void onFailure(int arg0, String arg1) {
//                progress.dismiss();
//                ShowToast("更新头像失败。请检查网络~");
//            }
//        });
    }


//        if(url!=null){

//            file.upload(mContext, new UploadFileListener() {
//                @Override
//                public void onSuccess() {
//                    User currentUser = HttpProxy.getCurrentUser(mContext);
//                    currentUser.setAvatar(file.getFileUrl(mContext));
//                    currentUser.update(mContext, new UpdateListener() {
//
//                        @Override
//                        public void onSuccess() {
//                            ShowToast("更改头像成功。");
//                        }
//
//                        @Override
//                        public void onFailure(int arg0, String arg1) {
//                            ShowToast("更新头像失败。请检查网络~");
//                        }
//                    });
//                }
//
//                @Override
//                public void onProgress(Integer arg0) {
//
//                }
//
//                @Override
//                public void onFailure(int arg0, String arg1) {
//                    ShowToast("上传头像失败。请检查网络~"+arg1);
//                }
//            });
//        }
//    }
}
