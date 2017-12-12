package com.acg12.cc.utils.premission;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.acg12.cc.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 可以使用 Manifest.permission.CAMERA来获取具体权限
 * 1、android.permission-group.CALENDAR            // 允许程序读取用户的日程信息
 *    android.permission.READ_CALENDAR
 *    android.permission.WRITE_CALENDAR
 * 2、android.permission-group.CAMERA              // 摄像机
 *    android.permission.CAMERA
 * 3、android.permission-group.CONTACTS           // 获取联系人
 *    android.permission.READ_CONTACTS
 *    android.permission.WRITE_CONTACTS
 *    android.permission.GET_ACCOUNTS
 * 4、android.permission-group.LOCATION            // 获取位置
 *    android.permission.ACCESS_FINE_LOCATION
 *    android.permission.ACCESS_COARSE_LOCATION
 * 5、android.permission-group.MICROPHONE         // 获取语言
 *    android.permission.RECORD_AUDIO
 * 6、android.permission-group.PHONE              // 获取手机信息 如状态码 打电话
 *    android.permission.READ_PHONE_STATE
 *    android.permission.CALL_PHONE
 *    android.permission.READ_CALL_LOG
 *    android.permission.WRITE_CALL_LOG
 *    com.android.voicemail.permission.ADD_VOICEMAIL
 *    android.permission.USE_SIP
 *    android.permission.PROCESS_OUTGOING_CALLS
 * 7、android.permission-group.SENSORS            // 短信传感器
 *    android.permission.BODY_SENSORS
 * 8、android.permission-group.SMS               // 短信服务
 *    android.permission.SEND_SMS
 *    android.permission.RECEIVE_SMS
 *    android.permission.READ_SMS
 *    android.permission.RECEIVE_WAP_PUSH
 *    android.permission.RECEIVE_MMS
 *    android.permission.READ_CELL_BROADCASTS
 * 9、android.permission-group.STORAGE            // 访问sd卡
 *    android.permission.READ_EXTERNAL_STORAGE
 *    android.permission.WRITE_EXTERNAL_STORAGE
 */
public class ApplyPermission {
	private String[] mPermissions;
	private int mRequestCode;
	private Object object;
	
	private static final String PACKAGE_URL_SCHEME = "package:";

	private ApplyPermission(Object object) {
		this.object = object;
	}

	public static ApplyPermission with(Activity activity) {
		return new ApplyPermission(activity);
	}

	public static ApplyPermission with(Fragment fragment) {
		return new ApplyPermission(fragment);
	}

	public ApplyPermission permissions(String... permissions) {
		this.mPermissions = permissions;
		return this;
	}

	public ApplyPermission addRequestCode(int requestCode) {
		this.mRequestCode = requestCode;
		return this;
	}

	@TargetApi(value = Build.VERSION_CODES.M)
	public void request() {
		requestPermissions(object, mRequestCode, mPermissions);
	}

	public static void needPermission(Activity activity, int requestCode, String[] permissions) {
		requestPermissions(activity, requestCode, permissions);
	}

	public static void needPermission(Fragment fragment, int requestCode, String[] permissions) {
		requestPermissions(fragment, requestCode, permissions);
	}

	public static void needPermission(Activity activity, int requestCode, String permission) {
		needPermission(activity, requestCode, new String[] { permission });
	}

	public static void needPermission(Fragment fragment, int requestCode, String permission) {
		needPermission(fragment, requestCode, new String[] { permission });
	}

	@TargetApi(value = Build.VERSION_CODES.M)
	private static void requestPermissions(Object object, int requestCode, String[] permissions) {
		if (!isOverMarshmallow()) {
			doExecuteSuccess(object, requestCode);
			return;
		}
		List<String> deniedPermissions = findDeniedPermissions(getActivity(object), permissions);
		if (deniedPermissions.size() > 0) {
			if (object instanceof Activity) {
				((Activity) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]),requestCode);
			} else if (object instanceof Fragment) {
				((Fragment) object).requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]),requestCode);
			} else {
				throw new IllegalArgumentException(object.getClass().getName()+ " is not supported");
			}
		} else {
			doExecuteSuccess(object, requestCode);
		}
	}

	private static void doExecuteSuccess(Object activity, int requestCode) {
		Method executeMethod = findMethodWithRequestCode(activity.getClass(), SuccessPermission.class, requestCode);
		executeMethod(activity, executeMethod);
	}

	private static void doExecuteFail(Object activity, int requestCode) {
		Method executeMethod = findMethodWithRequestCode(activity.getClass(), FailPermission.class, requestCode);
		executeMethod(activity, executeMethod);
	}

	private static void executeMethod(Object activity, Method executeMethod) {
		if (executeMethod != null) {
			try {
				if (!executeMethod.isAccessible())
					executeMethod.setAccessible(true);
				executeMethod.invoke(activity, new Object[]{});
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults) {
		requestResult(activity, requestCode, permissions, grantResults);
	}

	public static void onRequestPermissionsResult(Fragment fragment, int requestCode, String[] permissions, int[] grantResults) {
		requestResult(fragment, requestCode, permissions, grantResults);
	}

	private static void requestResult(Object obj, int requestCode, String[] permissions, int[] grantResults) {
		List<String> deniedPermissions = new ArrayList<String>();
		for (int i = 0; i < grantResults.length; i++) {
			if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
				deniedPermissions.add(permissions[i]);
			}
		}
		if (deniedPermissions.size() > 0) {
			doExecuteFail(obj, requestCode);
		} else {
			doExecuteSuccess(obj, requestCode);
		}
	}
	
	private static Dialog dlg;
	
	public static void showMissingPermissionDialog(final Activity activity){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.cc_permission_help);
        builder.setMessage(R.string.cc_permission_text);
        builder.setCancelable(false);
        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cc_permission_quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	dlg.dismiss();
            	dlg = null;
            }
        });

        builder.setPositiveButton(R.string.cc_permission_settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	dlg.dismiss();
                startAppSettings(activity);
                dlg = null;
            }
        });
        dlg = builder.show();
	}
	
	// 启动应用的设置
    private static void startAppSettings(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + activity.getPackageName()));
        activity.startActivity(intent);
    }
    
    
    /*--------------------内部类----------------------------*/
    
    private static Activity getActivity(Object object) {
		if (object instanceof Fragment) {
			return ((Fragment) object).getActivity();
		} else if (object instanceof Activity) {
			return (Activity) object;
		}
		return null;
	}
    
    @TargetApi(value = Build.VERSION_CODES.M)
    private static List<String> findDeniedPermissions(Activity activity, String... permission) {
   		List<String> denyPermissions = new ArrayList<String>();
   		for (String value : permission) {
   			if (activity.checkSelfPermission(value) != PackageManager.PERMISSION_GRANTED) {
   				denyPermissions.add(value);
   			}
   		}
   		return denyPermissions;
   	}
    
    @SuppressWarnings("rawtypes")
	private static <A extends Annotation> Method findMethodWithRequestCode(Class clazz, Class<A> annotation, int requestCode) {
		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(annotation)) {
				if (isEqualRequestCodeFromAnntation(method, annotation,requestCode)) {
					return method;
				}
			}
		}
		return null;
	}
    
    @SuppressWarnings("rawtypes")
	public static boolean isEqualRequestCodeFromAnntation(Method m, Class clazz, int requestCode) {
		if (clazz.equals(FailPermission.class)) {
			return requestCode == m.getAnnotation(FailPermission.class)
					.requestCode();
		} else if (clazz.equals(SuccessPermission.class)) {
			return requestCode == m.getAnnotation(SuccessPermission.class)
					.requestCode();
		} else {
			return false;
		}
	}

	public static boolean isOverMarshmallow() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
	}
    
}
