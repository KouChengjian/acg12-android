package org.acg12.widget;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.acg12.R;

import org.acg12.conf.Constant;
import org.acg12.utlis.CacheUtils;
import org.acg12.utlis.premission.ApplyPermission;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by DELL on 2016/11/25.
 */
public class CommonPopupWindows {

    Context mContext;
    PopupWindow popupWindow;

    public static CommonPopupWindows with(Context mContext){
        return new CommonPopupWindows(mContext);
    }

    public CommonPopupWindows(Context mContext){
        this.mContext = mContext;

    }

    private void popupwindows(View view , View rootView) {
        //popupWindow = new PopupWindow(view, 0, 0);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 动画效果 从底部弹起
        popupWindow.setAnimationStyle(R.style.Animations_GrowFromBottom);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM, 0, 0);
    }

//    public interface OnUpdateChooseCity{
//        void updateCity(String sex);
//    }
//
//    public void setOnUpdateChooseCity(OnUpdateChooseCity onUpdateChooseCity){
//        this.onUpdateChooseCity = onUpdateChooseCity;
//    }
//
//    OnUpdateChooseCity onUpdateChooseCity;

    /**
     * 修改城市
     */
//    public void initCityChooseView(View rootView ,View view){
//        //View view = LayoutInflater.from(mContext).inflate(R.layout.common_pop_user_choose_city, null);
//        popupWindow = new PopupWindow(view, 0, 0);
//        view.findViewById(R.id.tv_dismiss).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//            }
//        });
//        view.findViewById(R.id.rl_dismiss).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//            }
//        });
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    popupWindow.dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });
//        view.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//                onUpdateChooseCity.updateCity("");
//            }
//        });
//        popupwindows(view , rootView);
//    }



//    public interface OnUpdateSex{
//        void updateSex(String sex, int i);
//    }
//
//    public void setOnUpdateSex(OnUpdateSex onUpdateSex){
//        this.onUpdateSex = onUpdateSex;
//    }
//
//    OnUpdateSex onUpdateSex;

    /**
     * 修改性别
     */
//    public void initSexChooseView(View rootView){
//        View view = LayoutInflater.from(mContext).inflate(R.layout.common_pop_user_sex, null);
//        popupWindow = new PopupWindow(view, 0, 0);
//        view.findViewById(R.id.tv_dismiss).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//            }
//        });
//        view.findViewById(R.id.rl_dismiss).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//            }
//        });
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    popupWindow.dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });
//        view.findViewById(R.id.rl_sex_privary).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//                onUpdateSex.updateSex("保密",0);
//            }
//        });
//        view.findViewById(R.id.rl_sex_male).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//                onUpdateSex.updateSex("男",1);
//            }
//        });
//        view.findViewById(R.id.rl_sex_female).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//                onUpdateSex.updateSex("女",2);
//            }
//        });
//        popupwindows(view , rootView);
//    }



    private String dateTime;
    private String iconUrl;
    OnUpdateAvatar onUpdateAvatar;
    /**
     * 相机
     */
    public void initOpenCamera(){
        final AlertDialog albumDialog = new AlertDialog.Builder(mContext).create();
        albumDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(mContext).inflate(R.layout.include_popup_user_avatar, null);
        albumDialog.show();
        albumDialog.setContentView(v);
        albumDialog.getWindow().setGravity(Gravity.CENTER);
        TextView albumPic = (TextView)v.findViewById(R.id.album_pic);
        TextView cameraPic = (TextView)v.findViewById(R.id.camera_pic);
        albumPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                albumDialog.dismiss();
                getOpenPhotoAlbum();
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                albumDialog.dismiss();
                ApplyPermission.with(((Activity)mContext)).addRequestCode(Constant.USER_APPLY_PERMISSION_CAMERE).permissions(Manifest.permission.CAMERA).request();
            }
        });

//        View view = LayoutInflater.from(mContext).inflate(R.layout.include_popup_user_avatar, null);
//        popupWindow = new PopupWindow(view, 0, 0);
//        view.findViewById(R.id.tv_dismiss).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//            }
//        });
//        view.findViewById(R.id.rl_dismiss).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//            }
//        });
//        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
//                    popupWindow.dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });
//        view.findViewById(R.id.rl_albumPic).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//                getOpenPhotoAlbum();
//            }
//        });
//        view.findViewById(R.id.rl_cameraPic).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                popupWindow.dismiss();
//                ApplyPermission.with(((Activity)mContext)).addRequestCode(Constant.PERMISSION_CAMERE).permissions(Manifest.permission.CAMERA).request();
//            }
//        });
//        popupwindows(view , rootView);
    }

    public interface OnUpdateAvatar{
        void updateAvatar(String url);
    }

    public void setOnUpdateAvatar(OnUpdateAvatar onUpdateAvatar){
        this.onUpdateAvatar = onUpdateAvatar;
    }

    public void updateIcon(String url){
        onUpdateAvatar.updateAvatar(url);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case 1:
                    String files = CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime;
                    File file = new File(files);
                    if(file.exists()&&file.length() > 0){
                        Uri uri = Uri.fromFile(file);
                        startPhotoZoom(uri);
                    }
                    break;
                case 2:
                    if (data == null) {
                        return;
                    }
                    startPhotoZoom(data.getData());
                    break;
                case 3:
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap bitmap = extras.getParcelable("data");
                            iconUrl = saveToSdCard(bitmap);
                            //Log.e("iconUrl",iconUrl+"====");
                            updateIcon(iconUrl);
                        }
                    }
                    break;
            }
        }
    }

    public void getOpenCamera(){
        Date date = new Date(System.currentTimeMillis());
        dateTime = date.getTime() + "";
        File f = new File(CacheUtils.getCacheDirectory(mContext, true, "icon") + dateTime);
        if (f.exists()) {
            f.delete();
        }
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri uri = Uri.fromFile(f);
        //Log.e("uri", uri + "");

        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camera.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        ((Activity)mContext).startActivityForResult(camera, 1);
    }

    private void getOpenPhotoAlbum(){
        Date date = new Date(System.currentTimeMillis());
        dateTime = date.getTime() + "";
        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("image/*");
//		intent2.putExtra("aspectX", 1);
//		intent2.putExtra("aspectY", 1);
//		intent2.putExtra("outputX", 80);
//		intent2.putExtra("outputY", 80);
//		intent2.putExtra("return-data", true);
        ((Activity)mContext).startActivityForResult(intent2, 2);
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String url = getPath(mContext,uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        }else{
            intent.setDataAndType(uri, "image/*");
        }
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 120);
        intent.putExtra("outputY", 120);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra("return-data", true);
        ((Activity)mContext).startActivityForResult(intent, 3);
    }

    public String saveToSdCard(Bitmap bitmap){
        String files = CacheUtils.getCacheDirectory(mContext, true, "icon") + "/"+dateTime+".png";
        File file=new File(files);
        try {
            FileOutputStream out=new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)){
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }

    // 以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

}
